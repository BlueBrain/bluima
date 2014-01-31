package ch.epfl.bbp;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * For String utils not covered by Apache commons or others
 * 
 * @author renaud.richardet@epfl.ch
 */
public class StringUtils {

    /**
     * @return the same as String.split() if separator is found, or return input
     *         otherwise
     */
    @Deprecated
    // not working
    public static String[] safeSplit(String input, String regex) {
        if (input == null)
            return null;
        if (input.matches(regex))
            return input.split(regex);
        else
            return new String[] { input };
    }

    // TODO see if avail in commons
    public static String implode(int[] values, String separator) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < values.length; i++) {
            if (i > 0)
                result.append(separator);
            result.append(values[i]);
        }
        return result.toString();
    }

    public static String implode(String[] values, String separator) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < values.length; i++) {
            if (i > 0)
                result.append(separator);
            result.append(values[i]);
        }
        return result.toString();
    }

    public static String implode(String[] values, String separator,
            String beforeEach, String afterEach) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < values.length; i++) {
            if (i > 0)
                result.append(separator);
            result.append(beforeEach);
            result.append(values[i]);
            result.append(afterEach);
        }
        return result.toString();
    }

    public static String implodeEscapeSql(Object[] values, String separator,
            String beforeEach, String afterEach) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < values.length; i++) {
            if (i > 0)
                result.append(separator);
            result.append(beforeEach);
            result.append(escapeSql(values[i]));
            result.append(afterEach);
        }
        return result.toString();
    }

    public static String implodeEscapeSql(List<?> values) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < values.size(); i++) {
            if (i > 0)
                result.append(',');
            result.append('\'');
            result.append(escapeSql(values.get(i).toString()));
            result.append('\'');
        }
        return result.toString();
    }

    public static String printStringArray(String[] array) {

        if (array == null || array.length == 0) {
            return "";
        }

        String s = "";
        for (int i = 0; i < array.length; i++) {
            s += array[i] + ", ";
        }
        s = s.substring(0, s.length() - 2);

        return s;
    }

    public static String prettyPrintStringArray(String[] array) {

        if (array == null || array.length == 0) {
            return "";
        }

        String s = "";
        for (int i = 0; i < array.length; i++) {
            s += array[i] + ", ";
            if (i % 10 == 0) {
                s += "\n";
            }
        }
        s = s.substring(0, s.length() - 2);

        return s;
    }

    public static boolean arrayContains(String[] array, String element) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(element)) {
                return true;
            }
        }
        return false;
    }

    public static String print(Throwable e) {

        StringBuffer sb = new StringBuffer();
        sb.append(e.toString() + " caused by " + e.getCause() + " (");

        int i = 0;
        for (StackTraceElement el : e.getStackTrace()) {
            sb.append(el.getClassName() + "." + el.getMethodName() + ":l."
                    + el.getLineNumber() + "; ");
            if (i++ > 11) {
                break;
            }
        }

        return sb.toString();
    }

    public static String escapeQuotes(String in) {
        return in.replaceAll("'", "\"");
    }

    public static String[] uniq(String[] in) {

        Set<String> noDupes = new HashSet<String>(in.length);
        for (int i = 0; i < in.length; i++) {
            noDupes.add(in[i]);
        }
        return noDupes.toArray(new String[noDupes.size()]);
    }

    /** blabla.ahah.ohoh --> ohoh */
    public static String getExtension(String path) {
        int dot = path.lastIndexOf(".");
        if (dot == -1) {
            return "";
        } else {
            return path.substring(dot + 1, path.length());
        }
    }

    /** blabla.ahah.ohoh --> blabla */
    public static String getPrefix(String s) {
        int dot = s.lastIndexOf(".");
        if (dot == -1) {
            return s;
        } else {
            return s.substring(0, dot);
        }
    }

    public static String escapeSql(Object obj) {
        if (obj == null) {
            return null;
        }

        String ret = StringUtils.replace(obj.toString(), "\\", "\\\\", -1);
        ret = StringUtils.replace(ret, "'", "\\'", -1);
        ret = StringUtils.replace(ret, "\"", "\\\"", -1);
        ret = StringUtils.replace(ret, "\n", "\\n", -1);
        ret = StringUtils.replace(ret, "{", "\\{", -1);
        ret = StringUtils.replace(ret, "}", "\\}", -1);
        return ret;
    }

    /**
     * Replace a string with another string inside a larger string, for the
     * first <code>max</code> values of the search string. A <code>null</code>
     * reference is passed to this method is a no-op.
     * 
     * @param text
     *            text to search and replace in
     * @param repl
     *            String to search for
     * @param with
     *            String to replace with
     * @param max
     *            maximum number of values to replace, or <code>-1</code> if no
     *            maximum
     * @return the text with any replacements processed
     */
    public static String replace(String text, String repl, String with, int max) {
        if (text == null) {
            return null;
        }

        StringBuffer buf = new StringBuffer(text.length());
        int start = 0, end = 0;
        while ((end = text.indexOf(repl, start)) != -1) {
            buf.append(text.substring(start, end)).append(with);
            start = end + repl.length();

            if (--max == 0) {
                break;
            }
        }
        buf.append(text.substring(start));
        return buf.toString();
    }

    public static String questionMarksSql(int size) {

        StringBuffer result = new StringBuffer();
        for (int i = 0; i < size; i++) {
            if (i > 0)
                result.append(',');
            result.append('?');
        }
        return result.toString();
    }

    public static int nrOccurrence(String text, String searchFor) {
        int ret = 0;
        int start = 0;
        while (start >= 0) {
            start = text.indexOf(searchFor, start + 1);
            if (start != -1)
                ret++;
        }

        return ret;
    }

    /**
     * Truncates the String at maxNrChars.
     * 
     * @param source
     * @param maxNrChars
     * @return
     */
    public static String maxTruncStr(String source, int maxNrChars) {
        if (source != null && source.length() > maxNrChars) {
            return source.substring(0, maxNrChars);
        } else {
            return source;
        }
    }

    public static String implode(List<String> a, String separator) {
        return implode(a.toArray(new String[a.size()]), separator);
    }

    /**
     * @param text
     * @param length
     * @return a non-null snippetized version of this text
     */
    public static String snippetize(String text, int length) {
        if (text == null || text.length() == 0) {
            return "";
        } else if (text.length() < length) {
            return text;
        } else {
            return text.substring(0, length);
        }
    }

    /**
     * @param text
     * @param length
     * @return a non-null snippetized version of this text, cut at a space
     */
    public static String snippetizeAtSpace(String text, int length) {
        if (text == null || text.length() == 0) {
            return "";
        } else if (text.length() < length) {
            return text;
        } else {
            return text
                    .substring(0, text.substring(0, length).lastIndexOf(" "));
        }
    }

    /**
     * This method ensures that the output String has only valid XML unicode
     * characters as specified by the XML 1.0 standard. For reference, please
     * see <a href="http://www.w3.org/TR/2000/REC-xml-20001006#NT-Char">the
     * standard</a>. This method will return an empty String if the input is
     * null or empty.
     * 
     * @author Mark McLaren
     * @param in
     *            The String whose non-valid characters we want to remove.
     * @return The in String, stripped of non-valid characters.
     */
    public static String stripNonValidXMLCharacters(String in) {
        StringBuffer out = new StringBuffer(); // Used to hold the output.
        char current; // Used to reference the current character.

        if (in == null || ("".equals(in)))
            return ""; // vacancy test.
        for (int i = 0; i < in.length(); i++) {
            current = in.charAt(i); // NOTE: No IndexOutOfBoundsException caught
            // here; it should not happen.
            if ((current == 0x9) || (current == 0xA) || (current == 0xD)
                    || ((current >= 0x20) && (current <= 0xD7FF))
                    || ((current >= 0xE000) && (current <= 0xFFFD))
                    || ((current >= 0x10000) && (current <= 0x10FFFF)))
                out.append(current);
        }
        return out.toString();
    }

    public static String nullToEmpty(String s) {
        if (s == null)
            return "";
        return s;
    }
}
