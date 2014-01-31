package ch.epfl.bbp.uima.utils;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import com.google.common.base.Stopwatch;

/**
 * @author renaud.richardet@epfl.ch
 */
public class StopwatchUtils {

    private static final List<Stopwatch> stopWatches = newArrayList();

    public static void START(final int id) {
        while (stopWatches.size() < id + 1)
            stopWatches.add(new Stopwatch());
        stopWatches.get(id).start();
    }

    public static void STOP(final int id) {
        stopWatches.get(id).stop();
    }

    public static void closeAndPrint() {
        for (int i = 0; i < stopWatches.size(); i++) {
            System.out.println("STOPWATCH " + i + " took: "
                    + stopWatches.get(i));
        }
    }
}
