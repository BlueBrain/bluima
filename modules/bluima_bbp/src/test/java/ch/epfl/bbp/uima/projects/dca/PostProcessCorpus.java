package ch.epfl.bbp.uima.projects.dca;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.Integer.parseInt;
import static org.python.google.common.collect.Maps.newHashMap;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

import ch.epfl.bbp.io.LineReader;
import ch.epfl.bbp.io.TextFileWriter;

/**
 * Post process a DCA corpus to remove too infrequent tokens. This allows to do
 * the DCA preprocessing in one single pass.
 * 
 * @author richarde
 */
public class PostProcessCorpus {

    public static void main(String[] args) throws Exception {

        String root = "/Users/richarde/dev/bluebrain/git/lda_mesh/corpora/1m_ns/1m_ns.dca_corpus";
        // String root =
        // "/nfs4/bbp.epfl.ch/simulation/nlp/data/lda/20140417_preprocess_abstracts_dca/pubmed.dca_corpus";

        File corpus = new File(root);
        corpus.setReadOnly();
        File outCorpus = new File(root + "_filtered");
        final int minThreshold = 10;

        Map<Integer, Integer> freqs = newHashMap();
        // // read vocab, init frequency map to 0
        // List<String> vocab = linesFrom(root + ".vocab");
        // for (int i = 0; i < vocab.size(); i++) {
        // freqs.put(i, 0);
        // }

        // iterate corpus and count frequencies
        for (String line : new LineReader(new FileInputStream(corpus))) {

            String[] ids = line.split(" ");
            checkArgument(ids.length % 2 == 1, "expect uneven # ids::" + line);
            for (int i = 1/* skip first */; i < ids.length; i = i + 2/* jump 2 */) {
                int id = parseInt(ids[i]), freq = parseInt(ids[i + 1]);
                if (freqs.containsKey(id))
                    freqs.put(id, freqs.get(id) + freq);
                else
                    freqs.put(id, freq);
            }
        }
        System.out.println("done counting frequencies.\noriginalvocab size="
                + freqs.size());
        int filteredVocabSize = 0;
        for (Integer f : freqs.values()) {
            if (f > minThreshold)
                filteredVocabSize++;
        }
        System.out.println("filtered vocab size=" + filteredVocabSize);

        // re-iterate corpus and filter if freq < threshold
        int filtered = 0, kept = 0;
        TextFileWriter w = new TextFileWriter(outCorpus);
        for (String line : new LineReader(new FileInputStream(corpus))) {

            String[] ids = line.split(" ");
            int nrWords = 0;
            StringBuilder sb = new StringBuilder();
            for (int i = 1/* skip first */; i < ids.length; i = i + 2/* jump 2 */) {
                int id = parseInt(ids[i]), freq = parseInt(ids[i + 1]);
                if (freqs.get(id) > minThreshold) {
                    sb.append(" " + id + " " + freq);
                    nrWords++;
                    kept++;
                } else {
                    filtered++;
                }
            }
            w.addLine(nrWords + sb.toString());
        }
        w.close();
        System.out.println("done writing new corpus.\nkept=" + kept
                + "\nfilt=" + filtered);
    }
}
