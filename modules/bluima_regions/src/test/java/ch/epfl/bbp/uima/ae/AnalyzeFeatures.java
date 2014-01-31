package ch.epfl.bbp.uima.ae;

import static com.google.common.collect.Maps.newHashMap;
import static java.lang.Double.parseDouble;
import static java.lang.Math.abs;

import java.io.FileInputStream;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Ignore;
import org.junit.Test;

import ch.epfl.bbp.io.LineReader;

public class AnalyzeFeatures {

    @Test
    @Ignore
    public void test() throws Exception {

        Map<String, Double> hist = newHashMap();

        for (String line : new LineReader(new FileInputStream(
                "/Users/richarde/Desktop/a"))) {

            String[] split = line.split("\t");

            double score = abs(parseDouble(split[1]));
            String entry = split[0].replaceAll("/[+-]\\d", "");

            if (hist.containsKey(entry)) {
                hist.put(entry, score + hist.get(entry));
            } else
                hist.put(entry, score);
        }

        for (Entry<String, Double> s : hist.entrySet()) {
            System.out.println(s.getKey() + "\t" + s.getValue());
        }
    }
}
