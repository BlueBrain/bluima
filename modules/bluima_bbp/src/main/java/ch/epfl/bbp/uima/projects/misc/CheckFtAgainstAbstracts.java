package ch.epfl.bbp.uima.projects.misc;

import static ch.epfl.bbp.uima.BlueCasUtil.getHeaderIntDocId;
import static ch.epfl.bbp.uima.BlueUima.PARAM_DB_CONNECTION;
import static org.slf4j.LoggerFactory.getLogger;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReaderDescription;
import gov.nih.nlm.ncbi.www.soap.eutils.EFetchPubmedServiceStub.PubmedArticleType;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;

import ch.epfl.bbp.StringUtils;
import ch.epfl.bbp.uima.BlueCasUtil;
import ch.epfl.bbp.uima.ae.LanguageDetectionAnnotator;
import ch.epfl.bbp.uima.ae.PubmedArticleEntity;
import ch.epfl.bbp.uima.ae.StatsAnnotatorPlus;
import ch.epfl.bbp.uima.mongo.MongoCollectionReader;
import ch.epfl.bbp.uima.mongo.MongoConnection;
import ch.epfl.bbp.uima.mongo.MongoUtils;
import ch.epfl.bbp.uima.pubmed.PubmedSearch;
import ch.epfl.bbp.uima.pubmed.PubmedSearchUtil;
import ch.epfl.bbp.uima.txt2pmid.LuceneHelper;
import ch.epfl.bbp.uima.typesystem.TypeSystem;
import ch.epfl.bbp.uima.uimafit.CpeBuilder;

import com.google.common.collect.Lists;

/**
 * Language: should set in PUbmed db first
 * 
 * @author renaud.richardet@epfl.ch
 * 
 */
public class CheckFtAgainstAbstracts {
    private static final Logger LOG = getLogger(CheckFtAgainstAbstracts.class);

    private static int THREADS = 1;
    private static String[] db_connection = { "localhost", "bb_pubmed", "root",
            "" };
    // FIXME

    private static final String[] MONGO_FT_CONNECTION = { "128.178.187.248",
            // "pubmed_uima", "pm_ft", "", "" };
            "preprocessed_2", "20130327_preprocess_ft", "", "" };
    // FIXME

    public static void main(String[] args) throws Exception {
        LuceneHelper.subsumes("bla", "bli");// "checks" right jar versions

        CollectionReaderDescription crd = createReaderDescription(
                MongoCollectionReader.class, TypeSystem.JULIE_TSD,
                PARAM_DB_CONNECTION, MONGO_FT_CONNECTION);

        CpeBuilder pipeline = new CpeBuilder(THREADS, crd);
        pipeline.add(LanguageDetectionAnnotator.class);
        pipeline.add(ValidateFullText.class);
        pipeline.add(StatsAnnotatorPlus.class);
        pipeline.process();
        System.out.println("done :-)");
    }

    public static class ValidateFullText extends JCasAnnotator_ImplBase {

        private static final int MIN_TEXT_LENGHT = 2000;
        public static final String EMPTY_TXT = "empty_txt";
        public static final String TOO_SHORT_TXT = "too_short";
        public static final String WRONG_PDF = "wrong_pdf";
        public static final String NOT_ENGLISH = "not_english";
        public static final String NO_ABSTRACT = "no_abstract";

        private MongoConnection mongo;
        private PubmedSearch pubmedSearch;

        @Override
        public void initialize(UimaContext context)
                throws ResourceInitializationException {
            super.initialize(context);
            try {
                mongo = new MongoConnection(MONGO_FT_CONNECTION);
                pubmedSearch = new PubmedSearch();
            } catch (Exception e) {
                throw new ResourceInitializationException(e);
            }
        }

        // workaround: db conn attaches to a thread, but UIMA's init's thread is
        // different
        // from the process one.
        private boolean dbInitialized = false;

        @Override
        public void process(JCas jCas) throws AnalysisEngineProcessException {

            if (!dbInitialized) {
                Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://"
                        + db_connection[0] + "/" + db_connection[1] + "",
                        db_connection[2], db_connection[3]);
                dbInitialized = true;
            }

            int pmid = getHeaderIntDocId(jCas);

            // EMPTY_TXT ?
            if (BlueCasUtil.isEmptyText(jCas)) {
                MongoUtils.addFlag(pmid, EMPTY_TXT, mongo);
                LOG.info("{}\t EMPTY_TXT", pmid);

            } else if (jCas.getDocumentText().length() < MIN_TEXT_LENGHT) {
                // TOO_SHORT_TXT ?
                MongoUtils.addFlag(pmid, TOO_SHORT_TXT, mongo);
                LOG.info("{}\t TOO_SHORT_TXT: {}", pmid, jCas.getDocumentText()
                        .length());

            } else {

                // NOT_ENGLISH ?
                if (jCas.getDocumentLanguage() != null
                        && !(jCas.getDocumentLanguage().equals("en") || jCas
                                .getDocumentLanguage().equals("x-unspecified"))) {
                    MongoUtils.addFlag(pmid, NOT_ENGLISH, mongo);
                    LOG.info("{}\t NOT_ENGLISH: {}", pmid,
                            jCas.getDocumentLanguage());
                }

                // WRONG_PDF ?
                // get abstract from Pubmed (db or ws)
                String ft = jCas.getDocumentText();
                String abstrct = null, title = null;
                try {
                    PubmedArticleEntity aDb = PubmedArticleEntity
                            .getArticle(pmid);
                    abstrct = aDb.getString(PubmedArticleEntity.ABSTRACT);
                    title = aDb.getString(PubmedArticleEntity.TITLE);
                } catch (Exception e) {
                    LOG.debug("falling back on PubMed ws for {}, {}", pmid,
                            e.getMessage());
                    try {
                        PubmedArticleType article = Lists.newArrayList(
                                pubmedSearch.search("" + pmid)).get(0);
                        title = PubmedSearchUtil.getTitle(article);
                        abstrct = PubmedSearchUtil.getAbstract(article);
                    } catch (Exception e1) {
                        LOG.warn("could not pubmed ws for " + pmid, e1);
                        return;
                    }
                }

                // compare
                if (abstrct != null) {
                    float score = LuceneHelper.subsumes(ft, abstrct);
                    if (score < 0.01f) {
                        MongoUtils.addFlag(pmid, WRONG_PDF, mongo);
                        LOG.info("{}\t WRONG_PDF (score {})", pmid, score);
                        LOG.debug(pmid + " score:" + score
                                + " subsuming\nABSTRACT:{}\nFT:{}", abstrct,
                                StringUtils.snippetize(ft, 5000));
                    }
                } else if (title != null) {
                    float score = LuceneHelper.subsumes(ft, title);
                    if (score < 0.01f) {
                        MongoUtils.addFlag(pmid, WRONG_PDF, mongo);
                        LOG.info("{}\t WRONG_PDF (score {})", pmid, score);
                        LOG.debug(pmid + " score:" + score
                                + " subsuming\nTITLE:{}\nFT:{}", title,
                                StringUtils.snippetize(ft, 5000));
                    }
                } else {
                    LOG.info("could not check for subsuming for {}", pmid);
                }
            }
        }
    }
}
