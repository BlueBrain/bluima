package ch.epfl.bbp;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.epfl.bbp.io.LineReader;

public class FileParseUtils {

    public static List<Double> parseToDoubleList(String txt, String separator) {
        if (txt.length() == 0)
            return new ArrayList<Double>();
        String[] split = txt.trim().split(separator);
        List<Double> ret = new ArrayList<Double>(split.length);
        for (String s : split)
            ret.add(Double.parseDouble(s.trim()));
        return ret;
    }

    public static List<Float> parseToFloatList(String txt, String separator) {
        if (txt.length() == 0)
            return new ArrayList<Float>();
        String[] split = txt.trim().split(separator);
        List<Float> ret = new ArrayList<Float>(split.length);
        for (String s : split)
            ret.add(Float.parseFloat(s.trim()));
        return ret;
    }

    public static List<Integer> parseToIntegerList(String txt, String separator) {
        if (txt.length() == 0)
            return new ArrayList<Integer>();
        String[] split = txt.trim().split(separator);
        List<Integer> ret = new ArrayList<Integer>(split.length);
        for (String s : split)
            ret.add(Integer.parseInt(s.trim()));
        return ret;
    }

    public static Map<Integer, Double> parseIntDouble(String file,
            String separator) throws FileNotFoundException {
        Map<Integer, Double> ret = new HashMap<Integer, Double>();
        for (String line : new LineReader(new FileInputStream(file))) {
            String[] split = line.split(separator);
            ret.put(parseInt(split[0].trim()), parseDouble(split[1].trim()));
        }
        return ret;
    }

    public static Map<Integer, Integer> parseIntInt(String file,
            String separator) throws FileNotFoundException {
        Map<Integer, Integer> ret = new HashMap<Integer, Integer>();
        for (String line : new LineReader(new FileInputStream(file))) {
            String[] split = line.split(separator);
            ret.put(parseInt(split[0]), parseInt(split[1]));
        }
        return ret;
    }

    public static List<Integer> parseIntColumn(String file, int column,
            String separator, boolean skipHeader) throws NumberFormatException,
            FileNotFoundException {
        List<Integer> ret = new ArrayList<Integer>();
        for (String line : new LineReader(new FileInputStream(file))) {
            if (skipHeader) {
                skipHeader = false;
                continue;
            }
            ret.add(parseInt(line.split(separator)[column]));
        }
        return ret;
    }
}
