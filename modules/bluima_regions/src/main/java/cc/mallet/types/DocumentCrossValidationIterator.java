package cc.mallet.types;

import static com.google.common.collect.ArrayListMultimap.create;
import static com.google.common.collect.Lists.newArrayList;
import static java.lang.Integer.parseInt;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;

import cc.mallet.pipe.Pipe;

import com.google.common.collect.Multimap;

/**
 * <code>CrossValidationIterator</code> allows iterating over pairs of
 * <code>InstanceList</code>, where each pair is split into training/testing
 * based on nfolds. <strong>Split at the document level, not sentence
 * level.</strong>
 * 
 * @see edu.umass.cs.mallet.base.types.InstanceList.CrossValidationIterator
 */
public class DocumentCrossValidationIterator implements
        java.util.Iterator<InstanceList[]>, Serializable {
    private static Logger LOG = getLogger(DocumentCrossValidationIterator.class);
    private static final long serialVersionUID = 1L;
    int nfolds;
    InstanceList[] folds;
    int index;

    private Pipe pipe;
    private Multimap<Integer, Instance> instanceArticles;

    /**
     * @param _nfolds
     *            number of folds to split InstanceList into
     * @param seed
     *            seed for random number used to split InstanceList
     */
    public DocumentCrossValidationIterator(InstanceList ilist, int _nfolds,
            int seed) {
        assert (_nfolds > 0) : "nfolds: " + nfolds;
        this.nfolds = _nfolds;
        this.index = 0;
        this.pipe = ilist.pipe;

        // group instances by pubmed id
        instanceArticles = create();
        Iterator<Instance> iit = ilist.shallowClone().iterator();
        while (iit.hasNext()) {
            Instance instance = iit.next();
            int pmId = parseInt(instance.name.toString().split("__")[0]);
            instanceArticles.put(pmId, instance);
        }

        folds = split(new java.util.Random(seed), _nfolds);
    }

    public DocumentCrossValidationIterator(InstanceList ilist, int _nfolds) {
        this(ilist, _nfolds, 1);
    }

    /**
     * Shuffles the elements of this list among several smaller lists.
     * 
     * @param fraction
     *            A list of numbers (not necessarily summing to 1) which, when
     *            normalized, correspond to the proportion of elements in each
     *            returned sublist. This method (and all the split methods) do
     *            not transfer the Instance weights to the resulting
     *            InstanceLists.
     * @param r
     *            The source of randomness to use in shuffling.
     * @return one <code>InstanceList</code> for each element of
     *         <code>proportions</code>
     */
    public InstanceList[] split(java.util.Random r, int _nfolds) {

        List<Integer> articleIds = newArrayList(instanceArticles.keySet());
        Collections.shuffle(articleIds, r);

        final int nrSentences = instanceArticles.size();
        final int nrSentencesPerSplit = (int) (nrSentences / (double) _nfolds);

        int runningSplitId = 0;
        int runningSentencesPerSplit = 0;

        InstanceList[] splitted = new InstanceList[_nfolds];
        for (int i = 0; i < splitted.length; i++) {
            splitted[i] = new InstanceList(pipe);
        }
        for (int i = 0; i < articleIds.size(); i++) {

            // add all sentences from this article
            for (Instance sentence : instanceArticles.get(articleIds.get(i))) {
                // System.out.println(format("adding {} to split {}",
                // sentence.name, runningSplitId));
                splitted[runningSplitId].add(sentence);
                runningSentencesPerSplit++;
            }
            // move to next split?
            if (runningSentencesPerSplit > nrSentencesPerSplit
                    && runningSplitId < (_nfolds - 1)) {
                runningSplitId++;
                runningSentencesPerSplit -= nrSentencesPerSplit;
            }
        }

        // some stats
        LOG.debug("{} sentences in {} splits", nrSentences, _nfolds);
        for (InstanceList iList : splitted) {
            LOG.debug("size:: " + iList.size());
        }

        return splitted;
    }

    public boolean hasNext() {
        return index < nfolds;
    }

    /**
     * Returns the next training/testing split.
     * 
     * @return A pair of lists, where <code>InstanceList[0]</code> is the larger
     *         split (training) and <code>InstanceList[1]</code> is the smaller
     *         split (testing)
     */
    public InstanceList[] nextSplit() {
        InstanceList[] ret = new InstanceList[2];
        ret[0] = new InstanceList(pipe);
        for (int i = 0; i < folds.length; i++) {
            if (i == index)
                continue;
            Iterator<Instance> iter = folds[i].iterator();
            while (iter.hasNext())
                ret[0].add(iter.next());
        }
        ret[1] = folds[index].shallowClone();
        index++;
        return ret;
    }

    /**
     * Returns the next split, given the number of folds you want in the
     * training data.
     */
    public InstanceList[] nextSplit(int numTrainFolds) {
        InstanceList[] ret = new InstanceList[2];
        ret[0] = new InstanceList(pipe);
        ret[1] = new InstanceList(pipe);

        // train on folds [index, index+numTrainFolds), test on rest
        for (int i = 0; i < folds.length; i++) {
            int foldno = (index + i) % folds.length;
            InstanceList addTo;
            if (i < numTrainFolds) {
                addTo = ret[0];
            } else {
                addTo = ret[1];
            }

            Iterator<Instance> iter = folds[foldno].iterator();
            while (iter.hasNext())
                addTo.add(iter.next());
        }
        index++;
        return ret;
    }

    public InstanceList[] next() {
        return nextSplit();
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }
}
