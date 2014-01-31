package ch.epfl.bbp.uima.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {

    public final static long ONE_SECOND = 1000;
    public final static long SECONDS = 60;

    public final static long ONE_MINUTE = ONE_SECOND * 60;
    public final static long MINUTES = 60;

    public final static long ONE_HOUR = ONE_MINUTE * 60;
    public final static long HOURS = 24;

    public final static long ONE_DAY = ONE_HOUR * 24;

    private TimeUtils() {
    }

    /**
     * converts time (in milliseconds) to human-readable format
     * "<w> days, <x> hours, <y> minutes and (z) seconds"
     */
    public static String toHuman(long duration) {
        StringBuffer res = new StringBuffer();
        long temp = 0;
        if (duration >= ONE_SECOND) {
            temp = duration / ONE_DAY;
            if (temp > 0) {
                duration -= temp * ONE_DAY;
                res.append(temp).append(" day").append(temp > 1 ? "s" : "")
                        .append(duration >= ONE_MINUTE ? ", " : "");
            }

            temp = duration / ONE_HOUR;
            if (temp > 0) {
                duration -= temp * ONE_HOUR;
                res.append(temp).append(" hour").append(temp > 1 ? "s" : "")
                        .append(duration >= ONE_MINUTE ? ", " : "");
            }

            temp = duration / ONE_MINUTE;
            if (temp > 0) {
                duration -= temp * ONE_MINUTE;
                res.append(temp).append(" minute").append(temp > 1 ? "s" : "");
            }

            if (!res.toString().equals("") && duration >= ONE_SECOND) {
                res.append(" and ");
            }

            temp = duration / ONE_SECOND;
            if (temp > 0) {
                res.append(temp).append(" second").append(temp > 1 ? "s" : "");
            }
            return res.toString();
        } else {
            return "0 second";
        }
    }

    private static final SimpleDateFormat DF = new SimpleDateFormat(
            "yyyyMMdd_hhmm");

    public static String nowToHuman() {
        return DF.format(new Date());
    }

    public static void main(String args[]) {
        System.out.println(toHuman(123));
        System.out.println(toHuman((5 * ONE_SECOND) + 123));
        System.out.println(toHuman(ONE_DAY + ONE_HOUR));
        System.out.println(toHuman(ONE_DAY + 2 * ONE_SECOND));
        System.out.println(toHuman(ONE_DAY + ONE_HOUR + (2 * ONE_MINUTE)));
        System.out.println(toHuman((4 * ONE_DAY) + (3 * ONE_HOUR)
                + (2 * ONE_MINUTE) + ONE_SECOND));
        System.out.println(toHuman((5 * ONE_DAY) + (4 * ONE_HOUR) + ONE_MINUTE
                + (23 * ONE_SECOND) + 123));
        System.out.println(toHuman(42 * ONE_DAY));
        System.out.println("Now: " + nowToHuman());
    }
}