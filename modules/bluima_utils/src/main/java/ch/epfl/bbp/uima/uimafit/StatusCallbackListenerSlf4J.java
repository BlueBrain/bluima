package ch.epfl.bbp.uima.uimafit;

import static ch.epfl.bbp.uima.utils.TimeUtils.toHuman;
import static java.lang.String.format;
import static java.lang.System.currentTimeMillis;
import static org.apache.commons.lang.StringUtils.repeat;
import static org.slf4j.LoggerFactory.getLogger;

import java.text.DecimalFormat;

import org.apache.uima.UIMAFramework;
import org.apache.uima.cas.CAS;
import org.apache.uima.collection.CollectionProcessingEngine;
import org.apache.uima.collection.EntityProcessStatus;
import org.apache.uima.collection.StatusCallbackListener;
import org.apache.uima.util.Level;
import org.apache.uima.util.ProcessTrace;
import org.apache.uima.util.ProcessTraceEvent;
import org.slf4j.Logger;

/** Callback Listener using Slf4J. Prints performance stats */
public class StatusCallbackListenerSlf4J implements StatusCallbackListener {
    private static Logger LOG = getLogger(StatusCallbackListenerSlf4J.class);

    int entityCount = 0;
    long size = 0;
    String name;
    private boolean collectionProcessComplete;
    private long start = currentTimeMillis();

    private CollectionProcessingEngine cpe;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void initializationComplete() {
        LOG.info("[{}] initializationComplete, took {}s", getName(),
                (currentTimeMillis() - start) / 1000 + "");
    }

    public synchronized void batchProcessComplete() {
        LOG.info("[{}] batchProcessComplete", getName());
    }

    final static DecimalFormat pctFmt = new DecimalFormat("##.##%");

    public synchronized void collectionProcessComplete() {

        if (entityCount == 0) {
            LOG.info("[{}] collectionProcessComplete, processed 0 cas",
                    getName());
            System.exit(0); // otherwise it hangs, somehow...
            return;
        }

        final long duration = currentTimeMillis() - start;
        LOG.info(
                "[{}] collectionProcessComplete, processed {} cas in {}s (avrg. {}ms per CAS)",
                new Object[] { getName(), entityCount, duration / 1000 + "",
                        duration / entityCount });
        this.collectionProcessComplete = true;

        // print performance stats
        StringBuffer buf = new StringBuffer();
        ProcessTrace processTrace = cpe.getPerformanceReport();
        // count total time so we can do percentages
        int totalTime = 0;
        for (ProcessTraceEvent event : processTrace.getEvents()) {
            totalTime += event.getDuration();
        }
        buf.append("\n--PERFORMANCE STATS --------------------------------------------------------------------------------\n");
        if (totalTime > 0) {
            buf.append("      Duration\t      %\t Type           \tComponent Name\n");
            for (ProcessTraceEvent event : processTrace.getEvents()) {
                final int eventDuration = event.getDuration();
                if (duration > 100) {

                    double pct = (double) eventDuration / totalTime;
                    if (pct > 0.005d) {
                        String pctStr = pctFmt.format(pct);
                        buf.append(format("%12s", eventDuration) + "ms \t"
                                + format("%7s", pctStr) + "\t "
                                + cropToLength(event.getType(), 15) + "\t"
                                + cropToLength(event.getComponentName(), 60)
                                + "\n");
                    }
                }
            }
        }
        buf.append(format("%12s", totalTime)
                + "ms   TOTAL TIME ("
                + toHuman(totalTime)
                + ")\n----------------------------------------------------------------------------------------------------\n");
        System.out.println(buf);
    }

    public static String cropToLength(String s, int l) {
        if (l < 1)
            return null;
        if (s == null || s.length() == 0) {
            return repeat(" ", l);
        } else if (s.length() > l) {
            return s.substring(s.length() - l);
        } else {
            return s + repeat(" ", l - s.length());
        }
    }

    public boolean isCollectionProcessComplete() {
        return collectionProcessComplete;
    }

    public synchronized void paused() {
        LOG.info("[{}] paused", getName());
    }

    public synchronized void resumed() {
        LOG.info("[{}] resumed", getName());
    }

    public void aborted() {
        LOG.info("[{}] aborted", getName());
        // forcing program to quit, otherwise hangs forever...
        System.exit(17);
    }

    /**
     * Called when the processing of a Document is completed. <br>
     * The process status can be looked at and corresponding actions taken.
     * 
     * @param aCas
     *            CAS corresponding to the completed processing
     * @param aStatus
     *            EntityProcessStatus that holds the status of all the events
     *            for aEntity
     */

    public void entityProcessComplete(CAS aCas, EntityProcessStatus aStatus) {

        // if there is an error above the individual document level,
        // an entityProcessStatus is created with a null value for entity
        if (aCas == null) {
            for (int i = 0; i < aStatus.getFailedComponentNames().size(); i++) {
                LOG.info("[{}] FailedComponentNames", aStatus
                        .getFailedComponentNames().get(i));
            }
            for (int i = 0; i < aStatus.getExceptions().size(); i++) {
                LOG.info("[{}] Exceptions", aStatus.getExceptions().get(i));
            }
            return;
        }
        try {
            entityCount++;
            // FIXME int dataSize = 0;
            // // get size here
            // Type t = aCas.getTypeSystem().getType("uima.cpm.FileLocation");
            // Feature f = t.getFeatureByBaseName("DocumentSize");
            // FSIterator fsI = aCas.getAnnotationIndex(t).iterator();
            // if (fsI.isValid()) {
            // dataSize = fsI.get().getIntValue(f);
            // }
            //
            // size += dataSize;
            // to handle exceptions occured in any of the components for the
            // entity
            if (aStatus.isException()) {
                for (int q = 0; q < aStatus.getExceptions().size(); q++) {
                    Exception e = (Exception) aStatus.getExceptions().get(q);
                    e.printStackTrace();
                }
            }
        } catch (Exception io) {
            UIMAFramework.getLogger(this.getClass()).log(Level.WARNING, "", io);
        }
    }

    public void setCpe(CollectionProcessingEngine cpe) {
        this.cpe = cpe;
    }
}
