package ch.epfl.bbp;

import java.util.Map;

/**
 * @author renaud.richardet@epfl.ch
 */
public class MissingUtils {

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Similar to Slf4J logger
     */
    public static String format(String msg, Object... args) {

        if (args == null || args.length == 0)
            return msg;

        StringBuilder sb = new StringBuilder();
        int argId = 0;
        for (int i = 0; i < msg.length(); i++) {

            final char c = msg.charAt(i);
            if (c == '{' && msg.charAt(i + 1) == '}') {
                Object val = args[argId++];
                if (val == null)
                    sb.append("null");
                else
                    sb.append(val.toString());
                i++;
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * Similar to Slf4J logger
     */
    public static void printf(String msg, Object... args) {
        System.out.println(format(msg, args));
    }

    public static void printTabbed(Object... args) {
        if (args == null || args.length == 0)
            return;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < args.length; i++) {
            if (i > 0)
                sb.append("\t");
            sb.append(args[i]);
        }
        System.out.println(sb);
    }

    public static <K, V> V getOrElse(Map<K, V> map, K key, V deflt) {
        if (map.containsKey(key)) {
            return map.get(key);
        } else {
            return deflt;
        }
    }
}
