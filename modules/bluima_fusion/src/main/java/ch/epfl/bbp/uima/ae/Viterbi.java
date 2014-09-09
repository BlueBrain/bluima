package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.typesystem.TypeSystemSemantics.NON_CONTENT_ANNOTATIONS;
import static com.google.common.collect.Lists.newArrayList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import ch.epfl.bbp.uima.typesystem.To;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

import de.julielab.jules.types.Token;

/**
 * @author renaud.richardet@epfl.ch
 */
public class Viterbi {
    // private static Logger LOG = getLogger(Viterbi.class);

    /**
     * @param begin
     *            we look at each sentence. this is its begin
     * @param end
     *            and its end
     * @param jCas
     * @return a list of transitions on the shortest path
     */
    public static List<List<Transition>> viterbi(Iterable<Annotation> annots,
            JCas jCas) {

        /** k: coord where the state occurs; v: the state itself */
        final TreeMap<Integer, State> states = new TreeMap<Integer, State>();
        final Multimap<Integer, Transition> transitions = HashMultimap.create();

        // add all content annotations (skip Sentences, ...)
        for (Annotation a : annots) {
            if (!NON_CONTENT_ANNOTATIONS.contains(a.getClass().getName())) {
                int beg = a.getBegin();
                int end = a.getEnd();
                states.put(beg, new State(beg));
                states.put(end, new State(end));
                transitions.put(beg, new Transition(1, beg, end, a));
            }
        }

        // add dummy transitions btw Tokens (with 0/free weight)
        Token prevToken = null;
        for (Annotation a : annots) {
            if (a instanceof Token) {
                final Token token = (Token) a;
                if (prevToken != null && prevToken.getEnd() < token.getBegin()) {
                    // /dbg("Token: " + token.getCoveredText + " [" +
                    // token.getBegin + ":" + token.getEnd + "]")
                    states.put(prevToken.getEnd(),
                            new State(prevToken.getEnd()));
                    states.put(token.getBegin(), new State(token.getBegin()));
                    transitions.put(prevToken.getEnd(), new Transition(0,
                            prevToken.getEnd(), token.getBegin(), null));
                }
                prevToken = token;
            }
        }

        // ///dbg("states\n" + (states.values mkString "\n") + "\n")
        // ///dbg("transitions\n" + (transitions mkString "\n") + "\n")

        // forward
        states.firstEntry().getValue().pathLength = 0; // set init score at 1st
        for (Entry<Integer, State> stateE : states.entrySet()) {
            final int coord = stateE.getKey();
            final State state = stateE.getValue();
            // /dbg("forward " + coord + ", pathLength=" + state.pathLength)

            final Collection<Transition> tOut = transitions.get(coord);
            if (!tOut.isEmpty() && state.pathLength != -17) {
                // iterate all out-transitions, update score accordingly
                for (Transition t : tOut) {
                    // /dbg("  tOut " + t.from + ":" + t.to)
                    final State toState = states.get(t.to);

                    if (toState.pathLength == -17) {
                        // /dbg("     1st time reached, pathLength" +
                        // (state.pathLength +t.weight))
                        toState.pathLength = state.pathLength + t.weight;
                        toState.weight = state.pathLength + state.weight;
                        toState.backTracks = newArrayList(t);
                    } else if (state.pathLength + t.weight < toState.pathLength) {
                        // /dbg("     found shorter path, updating pathLength to "
                        // +
                        // (state.pathLength + t.weight) + " (was " +
                        // toState.pathLength + ")")
                        toState.pathLength = state.pathLength + t.weight;
                        toState.weight = state.pathLength + state.weight;
                        toState.backTracks = newArrayList(t); // reset others
                    } else if (toState.pathLength == state.pathLength
                            + t.weight) {
                        toState.backTracks.add(t);
                        toState.weight = toState.weight + 1;
                        // /dbg("     tie, just addBackTracks " + t)
                    }
                }
            }
        }

        // print(transitions, jCas.getDocumentText());
        // final List<List<Transition>> shortestPaths =
        // backward(states.lastKey(),
        // states, transitions, new ArrayList<List<Transition>>());
        // print(transitions, jCas.getDocumentText());
        // return shortestPaths;
        return backward(states.lastKey(), states, transitions,
                new ArrayList<List<Transition>>());
    }

    private static void print(Multimap<Integer, Transition> transitions,
            String txt) {
        // TODO Auto-generated method stub

    }

    private static List<List<Transition>> backward(int stateId,
            Map<Integer, State> states,
            Multimap<Integer, Transition> transitions,
            List<List<Transition>> path) {
        State s = states.get(stateId);
        // /dbg("backtracking " + s.coord)
        if (s.backTracks.isEmpty())
            return path; // no more to backtrack, exit
        else {
            // select backtracks with most shared arcs
            Transition bestTransitionFrom = null;
            int bestWeight = -1;
            for (Transition t : s.backTracks) {
                if (states.get(t.from).weight > bestWeight) {
                    bestWeight = states.get(t.from).weight;
                    bestTransitionFrom = t;
                }
            }

            // - s.backTracks.filter(t => (t.from == bestTransitionFrom))
            List<Transition> bestTransitions = Lists.newArrayList();
            for (Transition t : s.backTracks) {
                if (t.from == bestTransitionFrom.from) {
                    bestTransitions.add(t);
                }
            }

            // LOG.trace(" selected: " + bestTransitionFrom + ", "
            // + bestTransitions.size() + " transitions");
            // val b = s.backTracks.groupBy(t => t.from).mapValues(_.length)
            // val x = s.backTracks.foldLeft(Map[Int, Int]())((m, transition) =>
            // m )
            path.add(bestTransitions);
            return backward(bestTransitionFrom.from, states, transitions, path);
        }
    }

    /**
     * @param coord
     *            the index of this State in the sentence string
     * @param weight
     * @param pathLength
     *            length of the shortest path
     * @param backTracks
     *            for backward
     */
    public static class State {
        Integer coord, weight = 0, pathLength = -17;
        List<Transition> backTracks = new ArrayList<Transition>();

        public State(int coord) {
            this.coord = coord;
        }

        public String toString() {
            return coord + " pathLength=" + pathLength + " weight=" + weight;
        };
    }

    public static class Transition {
        int weight, from, to;
        Annotation annot;

        public Transition(int weight, int from, int to, Annotation annot) {
            this.weight = weight;
            this.from = from;
            this.to = to;
            this.annot = annot;
        }

        public String toString() {
            return from + ":" + to + " - w=" + weight + " - a="
                    + To.string(annot);
        };
    }
}