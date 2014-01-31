package ch.epfl.bbp.uima.txt2pmid;

import static ch.epfl.bbp.uima.txt2pmid.Txt2PubmedId.getNGram;
import static com.google.common.collect.Maps.newHashMap;
import static org.apache.lucene.index.DirectoryReader.open;
import static org.apache.lucene.util.Version.LUCENE_41;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;

/**
 
 */
public class LuceneHelper {
    private static final Logger LOG = getLogger(LuceneHelper.class);

    private static final String CONTENT_FIELD = "text";
    private static final String ID_FIELD = "id";
    private static final Analyzer anal = new StandardAnalyzer(Version.LUCENE_41);
    private static final QueryParser parser = new QueryParser(LUCENE_41,
            CONTENT_FIELD, anal);

    /**
     * @return a map of scores that reflects how similar the needle is to the
     *         corpus
     */
    public static Map<Integer, Float> computeSimilarity(
            final Map<Integer, String> corpus, String needle, int gramLength) {

        if (corpus == null || corpus.isEmpty() || needle == null
                || needle.length() == 0)
            return null;

        try {
            // index the cleaned text
            RAMDirectory idx = new RAMDirectory();
            IndexWriterConfig iwc = new IndexWriterConfig(LUCENE_41, anal);
            IndexWriter writer = new IndexWriter(idx, iwc);

            // index
            for (Entry<Integer, String> entry : corpus.entrySet()) {
                Document doc = new Document();
                doc.add(new IntField(ID_FIELD, entry.getKey(), Store.YES));
                doc.add(new TextField(CONTENT_FIELD, entry.getValue(),
                        Store.YES));
                writer.addDocument(doc);
            }
            writer.close();

            // search
            IndexReader reader = open(idx);
            Query query = parser.parse(getNGram(needle, gramLength));
            IndexSearcher searcher = new IndexSearcher(reader);
            TopDocs results = searcher.search(query, 20);

            Map<Integer, Float> ret = newHashMap();
            for (ScoreDoc hit : results.scoreDocs) {
                Document doc = searcher.doc(hit.doc);
                int id = new Integer(doc.get(ID_FIELD));
                ret.put(id, hit.score);
            }
            reader.close();

            // ensure all keys are mapped, add 0 otherwise
            for (Integer corpusId : corpus.keySet()) {
                if (!ret.containsKey(corpusId))
                    ret.put(corpusId, 0f);
            }
            return ret;

        } catch (Exception e) {
            LOG.warn("could not computeSimilarity for " + needle, e);
        }
        return null;
    }

    /** @return a score that reflects how similar the queryStr is to the corpus */
    public static Map<Integer, Float> computeSimilarity2(
            final Map<Integer, String> queries, String haystack, int gramLength) {

        if (queries == null || queries.isEmpty() || haystack == null
                || haystack.length() == 0)
            return null;

        try {
            // index the cleaned text
            RAMDirectory idx = new RAMDirectory();
            IndexWriterConfig iwc = new IndexWriterConfig(LUCENE_41, anal);
            IndexWriter writer = new IndexWriter(idx, iwc);

            // index
            Document doc = new Document();
            doc.add(new TextField(CONTENT_FIELD, haystack, Store.YES));
            writer.addDocument(doc);
            writer.close();

            // search
            Map<Integer, Float> ret = newHashMap();
            IndexReader reader = open(idx);
            for (Entry<Integer, String> query : queries.entrySet()) {
                Query q = parser.parse(getNGram(
                        query.getValue().replaceAll("[^A-Za-z0-9]", " ")
                                .replaceAll(" +", " "), gramLength));
                // less discriminative
                // Query q = parser.parse(query.getValue()
                // .replaceAll("[^A-Za-z0-9]", " ").replaceAll(" +", " "));
                IndexSearcher searcher = new IndexSearcher(reader);
                TopDocs results = searcher.search(q, 1);
                if (results.totalHits == 1)
                    ret.put(query.getKey(), results.getMaxScore());
                else
                    ret.put(query.getKey(), 0f);
            }
            reader.close();

            return ret;

        } catch (Exception e) {
            LOG.warn("could not computeSimilarity", e);
        }
        return null;
    }

    /**
     * depends on document size, but > 0.01 should be a good threshold
     * 
     * @return a score that reflects if needle subsumes haystack
     */
    public static float subsumes(String haystack, final String needle) {

        if (needle == null || needle.length() < 0 || haystack == null
                || haystack.length() < 0)
            return 0f;

        try {
            // index the cleaned text
            RAMDirectory idx = new RAMDirectory();
            IndexWriterConfig iwc = new IndexWriterConfig(LUCENE_41, anal);
            IndexWriter writer = new IndexWriter(idx, iwc);

            // index
            Document doc = new Document();
            doc.add(new TextField(CONTENT_FIELD, haystack, Store.YES));
            writer.addDocument(doc);
            writer.close();

            // search
            IndexReader reader = open(idx);
            Query query = parser.parse(getNGram(needle, 3));
            TopDocs results = new IndexSearcher(reader).search(query, 1);
            float score = 0;
            for (ScoreDoc hit : results.scoreDocs) {
                score += hit.score;
            }
            reader.close();
            idx.close();
            return score;

        } catch (Exception e) {
            LOG.warn("could not computeSimilarity for " + needle, e);
        }
        return 0f;
    }

}
