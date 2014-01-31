package ch.epfl.bbp.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Utility/convenience {@link Iterable} class to read CSV or TSV files one line
 * at a time Usage:<br>
 * <code>
 * for (List'String' line : new TextFileReader("test.txt")) {<br>
 *   System.out.println(line);<br>
 * }</code>
 * 
 * @author renaud.richardet@epfl.ch
 */
public class SVReader implements Iterable<List<String>> {

    public static final String TAB = "\t";
    public static final String COMMA = ",";

    private Iterator<String> textFileReader;
    private boolean hasHeader;
    private List<String> header;
    private String separatorChar;

    private String filePath;

    /**
     * @param file
     *            input file
     * @param hasHeader
     *            whether this CSV contains a header in the first line. See
     *            getHeader()
     * @param separatorChar
     *            the separator char on each line, e.g {@link SVReader}.TAB
     * @throws IOException
     */
    public SVReader(File file, boolean hasHeader, String separatorChar)
            throws IOException {
        this(new FileInputStream(file), hasHeader, separatorChar);
        if (!file.exists()) {
            throw new IOException("no file at " + file.getAbsolutePath());
        }
        this.filePath = file.getAbsolutePath();
    }

    /**
     * @param file
     *            input file
     * @param hasHeader
     *            whether this CSV contains a header in the first line. See
     *            getHeader()
     * @param separatorChar
     *            the separator char on each line, e.g {@link SVReader}.TAB
     * @throws IOException
     */
    @SuppressWarnings("resource")
    public SVReader(InputStream is, boolean hasHeader, String separatorChar)
            throws IOException {
        if (is == null) {
            throw new IOException("null inputstream");
        }
        textFileReader = new LineReader(is).iterator();
        this.hasHeader = hasHeader;
        this.separatorChar = separatorChar;
        if (hasHeader) {
            if (!textFileReader.hasNext()) {
                throw new IOException("header specified, but none found");
            }
            header = parse(textFileReader.next());
        }
    }

    protected List<String> parse(String line) {
        String[] split = line.split(separatorChar);
        return Arrays.asList(split);
    }

    public List<String> getHeader() {
        if (!hasHeader)
            throw new IllegalAccessError("no header for this CSV");
        return header;
    }

    @Override
    public Iterator<List<String>> iterator() {
        return new Iterator<List<String>>() {

            public boolean hasNext() {
                return textFileReader.hasNext();
            }

            public List<String> next() {
                return parse(textFileReader.next());
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public String toString() {
        return filePath;
    }

    public static class TSVReader extends SVReader {
        public TSVReader(File file, boolean hasHeader) throws IOException {
            super(file, hasHeader, TAB);
        }

        public TSVReader(InputStream is, boolean hasHeader) throws IOException {
            super(is, hasHeader, TAB);
        }

        public static boolean lineIs(String line, String... patterns) {

            String[] split = line.split("\t");
            if (split.length != patterns.length)
                return false;

            for (int i = 0; i < split.length; i++) {
                String pattern = patterns[i];
                if (pattern.equals("i")) {
                    try {
                        Integer.parseInt(split[i]);
                    } catch (Exception e) {
                        return false;
                    }
                    // we passed this pattern
                } else if (pattern.equals("*")) {
                    // we passed this pattern anyway
                } else {
                    if (!pattern.equals(split[i])) {
                        return false;
                    }
                }
            }
            // we passed all patterns
            return true;
        }
    }

    public static class CSVReader extends SVReader {
        public CSVReader(File file, boolean hasHeader) throws IOException {
            super(file, hasHeader, COMMA);
        }

        @Override
        protected List<String> parse(String line) {

            CharacterIterator it = new CharacterIterator(line);

            List<String> store = new ArrayList<String>();
            StringBuffer curVal = new StringBuffer();
            boolean inquotes = false;
            boolean started = false;

            while (it.hasNext()) {
                Character ch = it.next();

                if (inquotes) {
                    started = true;
                    if (ch == '\"') {
                        inquotes = false;
                    } else {
                        curVal.append((char) ch);
                    }
                } else {
                    if (ch == '\"') {
                        inquotes = true;
                        if (started) {
                            // if this is the second quote in a value, add a
                            // quote
                            // this is for the double quote in the middle of a
                            // value
                            curVal.append('\"');
                        }
                    } else if (ch == ',') {
                        store.add(curVal.toString());
                        curVal = new StringBuffer();
                        started = false;
                    } else {
                        curVal.append((char) ch);
                    }
                }
            }
            store.add(curVal.toString());
            return store;
        }

        class CharacterIterator implements Iterator<Character> {

            private final String str;
            private int pos = 0;

            public CharacterIterator(String str) {
                this.str = str;
            }

            public boolean hasNext() {
                return pos < str.length();
            }

            public Character next() {
                return str.charAt(pos++);
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        }
    }
}
