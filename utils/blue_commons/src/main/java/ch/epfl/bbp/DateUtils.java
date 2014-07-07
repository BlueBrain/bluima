package ch.epfl.bbp;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtils {

    public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
    private static final SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);

    public static String now() {
        return sdf.format(Calendar.getInstance().getTime());
    }
}
