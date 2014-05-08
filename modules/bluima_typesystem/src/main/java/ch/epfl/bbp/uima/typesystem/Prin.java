package ch.epfl.bbp.uima.typesystem;

import org.apache.commons.lang.StringUtils;

public class Prin {
    // TODO private static Logger LOG = LoggerFactory.getLogger(Prin.class);

    public static void t(Object o) {
        System.out.println(To.string(o));
    }

    public static void t(String header, Object o) {
        System.out.println(header + ": "
                + StringUtils.repeat("-", 78 - header.length()));
        t(o);
        System.out.println();
    }
}
