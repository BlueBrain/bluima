package ch.epfl.bbp.uima.projects.misc;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import ch.epfl.bbp.Histogram;
import ch.epfl.bbp.io.SVReader.TSVReader;

public class HistogramOnSlurmLogs {

    public static void main(String[] args) throws IOException {

        Histogram<Integer> hist = new Histogram<Integer>();

        for (File f : new File("/Users/richarde/Desktop/out/").listFiles()) {

            String txt = FileUtils.readFileToString(f);

            int tt = txt.lastIndexOf("StatsTextAnnotator");
            if (tt > -1) {

                txt = txt.substring(tt + 1);
                for (String line : txt.split("\n")) {

                    if (line.indexOf("\t") > -1) {

                        String[] split = line.split("\t");
                        if (TSVReader.lineIs(line, "i", "i")) {

                            Integer cnt = new Integer(split[1]);
                            cnt = ((cnt + 500) / 1000) * 1000;// round

                            hist.add(cnt, new Integer(split[0]));
                        } else if (TSVReader.lineIs(line, "empty", "i")) {
                            hist.add(0, new Integer(split[1]));
                        }
                    }
                }
            }
        }
        System.out.println(hist);
    }
}
