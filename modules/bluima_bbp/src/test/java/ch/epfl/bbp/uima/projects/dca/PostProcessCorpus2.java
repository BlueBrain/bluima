package ch.epfl.bbp.uima.projects.dca;

import static ch.epfl.bbp.MapUtils.sortByValue;
import static ch.epfl.bbp.io.LineReader.linesFrom;
import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.Integer.parseInt;
import static org.python.google.common.collect.Maps.newHashMap;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import ch.epfl.bbp.io.LineReader;
import ch.epfl.bbp.io.TextFileWriter;

/**
 * Post process a DCA corpus to remove too infrequent tokens. This allows to do
 * the DCA preprocessing in one single pass.
 * 
 * TODO update ids, too
 * 
 * @author richarde
 */
public class PostProcessCorpus2 {

    public static void main(String[] args) throws Exception {

        final String root = "/Volumes/HDD2/ren_data/data_hdd/__mycorpora/1m_ns/1m_ns.dca_corpus";
        // String root =
        // "/nfs4/bbp.epfl.ch/simulation/nlp/data/lda/20140417_preprocess_abstracts_dca/pubmed.dca_corpus";
        final int MIN_THRESHOLD = 20;

        String newSuffix = "_filtered-" + MIN_THRESHOLD;
        File corpus = new File(root);
        corpus.setReadOnly();
        File vocabF = new File(root + ".vocab");
        File outCorpus = new File(root + newSuffix);
        File outVocab = new File(root + newSuffix + ".vocab");
        File outVocabFreqs = new File(root + newSuffix + ".vocab" + "-freqs");

        // read vocab, init frequency map to 0
        Map<Integer, Integer> freqs = newHashMap();// id:freq
        List<String> vocab = linesFrom(vocabF.getAbsolutePath());
        for (int i = 0; i < vocab.size(); i++) {
            freqs.put(i, 0);
        }
        // iterate corpus and count frequencies
        for (String line : new LineReader(new FileInputStream(corpus))) {
            String[] ids = line.split(" ");
            checkArgument(ids.length % 2 == 1, "expect uneven # ids::" + line);
            for (int i = 1/* skip first */; i < ids.length; i = i + 2/* jump 2 */) {
                int id = parseInt(ids[i]), freq = parseInt(ids[i + 1]);
                try {
                    freqs.put(id, freqs.get(id) + freq);

                } catch (Exception e) {
                    System.err.println("could not find" + id);
                }
            }
        }
        System.out.println("done counting frequencies.\noriginalvocab size="
                + freqs.size());

        // filter vocab & create new mapping
        Map<Integer, Integer> old2newId = newHashMap();
        TextFileWriter wvFreqs = new TextFileWriter(outVocabFreqs);
        int newIdIdx = 0;
        for (Entry<Integer, Integer> id_freq : freqs.entrySet()) {// id:freq
            if (id_freq.getValue() > MIN_THRESHOLD) {
                old2newId.put(id_freq.getKey(), newIdIdx++);
                wvFreqs.addLine(vocab.get(id_freq.getKey()) + "\t"
                        + id_freq.getValue());
            }
        }
        wvFreqs.close();
        // write out new vocab
        TextFileWriter wv = new TextFileWriter(outVocab);
        for (Entry<Integer, Integer> old_new : sortByValue(old2newId)
                .entrySet()) {
            wv.addLine(vocab.get(old_new.getKey()));
        }
        wv.close();
        System.out.println("filtered vocab size=" + old2newId.size());

        // re-iterate corpus and filter if freq < threshold
        int filtered = 0, kept = 0; // stats
        TextFileWriter w = new TextFileWriter(outCorpus);
        for (String line : new LineReader(new FileInputStream(corpus))) {

            final String[] ids = line.split(" ");
            int nrWords = 0;
            StringBuilder sb = new StringBuilder();
            for (int i = 1/* skip first */; i < ids.length; i = i + 2/* jump 2 */) {
                int id = parseInt(ids[i]), freq = parseInt(ids[i + 1]);
                if (old2newId.containsKey(id)) {
                    int newId = old2newId.get(id);
                    sb.append(" " + newId + " " + freq);
                    nrWords++;
                    kept++;
                } else {
                    filtered++;
                }
            }
            w.addLine(nrWords + sb.toString());
        }
        w.close();
        System.out.println("done writing new corpus.\nkept=" + kept + "\nfilt="
                + filtered);
    }
}
