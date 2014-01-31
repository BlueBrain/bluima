package ch.epfl.bbp.uima.txt2pmid;

import static ch.epfl.bbp.uima.txt2pmid.Txt2PubmedIdIndexer.CONTENT_FIELD;
import static ch.epfl.bbp.uima.txt2pmid.Txt2PubmedIdIndexer.PMID_FIELD;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.JULIE_TSD;
import static com.google.common.base.Splitter.on;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.partition;
import static com.google.common.collect.Maps.newHashMap;
import static java.lang.Integer.MAX_VALUE;
import static org.apache.commons.lang.StringUtils.join;
import static org.apache.lucene.util.Version.LUCENE_41;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.uima.jcas.JCas;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.uima.fit.factory.JCasFactory;

import ch.epfl.bbp.MapUtils;
import ch.epfl.bbp.uima.pdf.BlockHandler;
import ch.epfl.bbp.uima.pdf.cr.PdfCollectionReader;
import ch.epfl.bbp.uima.txt2pmid.Txt2PubmedId.PmId.Confidence;
import ch.epfl.bbp.uima.types.DocumentBlock;

import com.snowtide.pdf.PDFTextStream;

/**
 * Parses pdfs and map them to PubMed ids
 * 
 * @author renaud.richardet@fhnw.ch
 */
public class Txt2PubmedId {
    private static Logger LOG = LoggerFactory.getLogger(Txt2PubmedId.class);

    private static final float HIGH_CONFIDENCE_THRESHOLD = 5f;
    private static final float LOW_CONFIDENCE_THRESHOLD = 1.6f;

    private QueryParser parser;
    private IndexSearcher searcher;
    private DirectoryReader indexReader;

    public Txt2PubmedId(File indexPath) throws IOException {

        Directory dir = FSDirectory.open(indexPath);
        indexReader = DirectoryReader.open(dir);
        searcher = new IndexSearcher(indexReader);

        Analyzer analyzer = Txt2PubmedIdIndexer.getAnalyzer();

        parser = new QueryParser(LUCENE_41, CONTENT_FIELD, analyzer);
        BooleanQuery.setMaxClauseCount(MAX_VALUE);
    }

    public PmId extract(File pdfF) {
        try {
            // parse pdf
            JCas jCas = JCasFactory.createJCas(JULIE_TSD);
            PDFTextStream pdf = new PDFTextStream(pdfF);
            BlockHandler blueHandler = new BlockHandler();
            pdf.pipe(blueHandler);
            PdfCollectionReader.extractText(jCas, blueHandler.getDoc(),pdfF.getName());

            // crop: only first 2 pages of text
            String txt = "";
            for (DocumentBlock block : select(jCas, DocumentBlock.class)) {
                if (block.getPageId() < 2) {
                    txt += block.getCoveredText() + " ";
                }
            }

            // TODO
            // DocumentBlock largestBlock = null;
            // double maxFontSize=-2d;
            // for (DocumentBlock block : select(jCas, DocumentBlock.class)) {
            // if (block.getFontSize() > maxFontSize) {
            // largestBlock=block;
            // }
            // }
            // String largestText = largestBlock.getCoveredText();

            // filter: must have minimum text length
            if (txt.length() < 100) {
                LOG.debug("{}: text too small for extraction (l={}",
                        pdfF.getName(), txt.length());
                return null;
            }

            // cleanup: remove '"' --> important for building Lucene query later
            String cleanedText = txt.replaceAll("[^A-Za-z0-9]", " ");
            // cleanup: remove all double spaces
            cleanedText = cleanedText.replaceAll(" +", " ");

            // crop: if has "abstract", only keep subset
            // int abstractIndexOf =
            // cleanedText.toLowerCase().indexOf("abstract");
            // if (abstractIndexOf > -1) {
            // // / System.out.println("abstract");
            // cleanedText = cleanedText.substring(abstractIndexOf + 8,
            // min(abstractIndexOf + 1500, cleanedText.length()));
            // }
            // crop: if has "keywords", only keep subset
            // int keywords = cleanedText.toLowerCase().indexOf("keywords");
            // if (keywords > -1) {
            // // / System.out.println("keywords");
            // cleanedText = cleanedText.substring(0, keywords);
            // } else {
            // keywords = cleanedText.toLowerCase().indexOf("key words");
            // if (keywords > -1) {
            // // / System.out.println("keywords");
            // cleanedText = cleanedText.substring(0, keywords);
            // }
            // }

            // build Lucene query with 4-grams
            String queryString = getNGram(cleanedText, 4);

            Query query = parser.parse(queryString);
            TopDocs results = searcher.search(query, 10);
            ScoreDoc[] hits = results.scoreDocs;
            // System.out.println(hits.length + " hits");

            if (hits != null && hits.length > 1) {

                Map<Integer, String> queries = newHashMap();
                for (ScoreDoc hit : hits) {
                    Document doc = searcher.doc(hit.doc);
                    int pmid = new Integer(doc.get(PMID_FIELD));
                    String text = doc.get(Txt2PubmedIdIndexer.CONTENT_FIELD);

                    queries.put(pmid, text);
                }
                Map<Integer, Float> scores = LuceneHelper.computeSimilarity2(
                        queries, cleanedText, 4);

                // for (Entry<Integer, Float> sc : scores.entrySet()) {
                // System.out.println(sc);
                // }

                Integer pmId = MapUtils.keyOfHighestValue(scores);
                return new PmId(pmId, Confidence.HIGH);

            }
            // if (hits != null && hits.length > 1) {
            // Document doc = searcher.doc(hits[0].doc);
            // int pmidSYSTEM = new Integer(doc.get(PMID_FIELD));
            //
            // // String log = pdfF.getName() + "\t";
            // // for (ScoreDoc scoreDoc : hits) {
            // // int pmid = new Integer(searcher.doc(scoreDoc.doc).get(
            // // PMID_FIELD));
            // // log += scoreDoc.score + "\t" + pmid + "\t";
            // // }
            // // log += pmidSYSTEM + "\t";
            // // /System.out.println(log);
            //
            // float topResultScore = hits[0].score;
            // float secondResultScore = hits[1].score;
            // float score = topResultScore / secondResultScore;
            // // LOG.debug("{}: score {}", pdfF.getName(), score);
            //
            // if (score > HIGH_CONFIDENCE_THRESHOLD) {
            // return new PmId(new Integer(pmidSYSTEM), Confidence.HIGH);
            // } else if (score > LOW_CONFIDENCE_THRESHOLD) {
            // return new PmId(new Integer(pmidSYSTEM), Confidence.LOW);
            // // return reprocess(hits, cleanedText, searcher);
            // }
            // }
        } catch (Exception e) {
            LOG.warn(
                    "could not extract PubmedId from " + pdfF.getAbsolutePath(),
                    e);
        }
        return null;
    }

    public static String getNGram(String text, int size) {
        String q = "";
        List<String> splitted = newArrayList(on(" ").split(text));
        for (List<String> nGram : partition(splitted, size)) {
            q += "\"" + join(nGram, " ") + "\" ";
        }
        return q;
    }

    public void close() throws IOException {
        indexReader.close();
    }

    /** A bean to return results */
    public final static class PmId {
        enum Confidence {
            HIGH, LOW,
        };

        private final int pmId;
        private final Confidence confidence;

        private PmId(int pmId, Confidence confidence) {
            this.pmId = pmId;
            this.confidence = confidence;
        }

        public int getPmId() {
            return pmId;
        }

        public Confidence getConfidence() {
            return confidence;
        }
    }
}