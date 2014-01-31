package ch.epfl.bbp.io;

import static java.lang.Integer.parseInt;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.epfl.bbp.io.SVReader.TSVReader;

/**
 * @author renaud.richardet@epfl.ch
 */
public class TsvUtils {

    public static Map<Integer, String> loadIntString(InputStream is,
            boolean hasHeader) throws IOException {
        Map<Integer, String> map = new HashMap<Integer, String>();
        for (List<String> line : new TSVReader(is, hasHeader)) {
            map.put(parseInt(line.get(0)), line.get(1));
        }
        return map;
    }

    public static Map<String, Integer> loadStringInt(InputStream is,
            boolean hasHeader) throws IOException {
        Map<String, Integer> map = new HashMap<String, Integer>();
        for (List<String> line : new TSVReader(is, hasHeader)) {
            map.put(line.get(0), parseInt(line.get(1)));
        }
        return map;
    }
}
