package ch.epfl.bbp.uima.txt2pmid;

import static ch.epfl.bbp.uima.BlueCasUtil.getTitle;
import static ch.epfl.bbp.uima.BlueUima.PARAM_DB_CONNECTION;
import static ch.epfl.bbp.uima.ae.StatsAnnotatorPlus.PARAM_PRINT_EVERY;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import ch.epfl.bbp.uima.BlueCasUtil;
import ch.epfl.bbp.uima.ae.StatsAnnotatorPlus;
import ch.epfl.bbp.uima.cr.PubmedWholeDatabaseCR;

/**
 * @author renaud.richardet@epfl.ch
 */
public class Txt2PubmedIdIndexer {

    public static final String CONTENT_FIELD = "text";
    public static final String TITLE_FIELD = "title";
    public static final String PMID_FIELD = "pmid";
    public static final String INDEX_PATH = "index/index_pubmed_20130308";

    public static void main(String[] args) throws Exception {

        // cr
        CollectionReader cr = CollectionReaderFactory.createReader(
                PubmedWholeDatabaseCR.class, PARAM_DB_CONNECTION, new String[] {
                        "localhost", "bb_pubmed", "root", "" });

        SimplePipeline.runPipeline(
                cr,
                createEngineDescription(MyIndexer.class),
                createEngineDescription(StatsAnnotatorPlus.class,
                        PARAM_PRINT_EVERY, 50000));
    }

    public static Analyzer getAnalyzer() {
        return new StandardAnalyzer(Version.LUCENE_41);
    }

    public static class MyIndexer extends JCasAnnotator_ImplBase {
        private IndexWriter indexWriter;

        @Override
        public void initialize(UimaContext context)
                throws ResourceInitializationException {
            super.initialize(context);
            try {
                // create writer
                Directory dir;
                dir = FSDirectory.open(new File(INDEX_PATH));
                Analyzer analyzer = getAnalyzer();
                IndexWriterConfig iwc = new IndexWriterConfig(
                        Version.LUCENE_41, analyzer);
                iwc.setOpenMode(OpenMode.CREATE);
                indexWriter = new IndexWriter(dir, iwc);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void process(JCas jCas) throws AnalysisEngineProcessException {
            int pmid = BlueCasUtil.getHeaderIntDocId(jCas);
            if (!BlueCasUtil.isEmptyText(jCas)) {
                // System.out.println("indexing:: " + pmid);
                Document doc = new Document();
                doc.add(new IntField(PMID_FIELD, pmid, Store.YES));
                doc.add(new TextField(CONTENT_FIELD, jCas.getDocumentText(),
                        Store.YES));
                doc.add(new TextField(TITLE_FIELD, getTitle(jCas), Store.YES));
                try {
                    indexWriter.addDocument(doc);
                } catch (IOException e) {
                    throw new AnalysisEngineProcessException(e);
                }
            }
        }

        @Override
        public void collectionProcessComplete()
                throws AnalysisEngineProcessException {
            try {
                indexWriter.close();
            } catch (IOException e) {
                throw new AnalysisEngineProcessException(e);
            }
        }
    }
}
