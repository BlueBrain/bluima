package ch.epfl.bbp.uima.utils;

import static org.apache.commons.lang.StringUtils.join;

import java.io.File;
import java.io.IOException;

import ch.epfl.bbp.io.TextFileWriter;

/**
 * To write LOAD_DATA mysql files. <a
 * href="http://dev.mysql.com/doc/refman/5.1/en/load-data.html">LOAD_DATA</a> is
 * significantly faster than inserts, even in batches.<br>
 * Load command:<br>
 * <br/>
 * <code>mysql --local-infile   </code> <br/>
 * <br/>
 * <code>LOAD DATA LOCALINFILE 'myfile.txt' INTO TABLE mytable  LINES TERMINATED BY '\n';</code>
 * <br/>
 * <br/>
 * <code>LOAD DATA LOCAL INFILE 'myfile.txt' INTO TABLE mytable FIELDS TERMINATED BY ' ' LINES TERMINATED BY '\n' (`field1`, `field2`, ...);
</code> <br>
 * <code> [IGNORE number LINES] to skip header</code><br/>
 * Load format:<br>
 * <code>;</code>
 * 
 * @author renaud.richardet@epfl.ch
 */
public class LoadDataFileWriter extends TextFileWriter {

    private final String separator;

    /** Uses a tab as as separator */
    public LoadDataFileWriter(File f) throws IOException {
        this(f, "\t");
    }

    public LoadDataFileWriter(File f, final String separator)
            throws IOException {
        super(f);
        this.separator = separator;
    }

    public LoadDataFileWriter(File f, final String separator, String... headers)
            throws IOException {
        this(f, separator);
    }

    private Integer dataCnt = null;

    /**
     * @param datas
     *            make sure it's a varargs or an array!
     */
    public void addLoadLine(Object... datas) {

        // check # datas is consistent on each record
        if (dataCnt == null) // on 1st call
            dataCnt = datas.length;
        else if (datas.length != dataCnt)
            throw new IllegalArgumentException(
                    "non-valid number of datas, should be " + dataCnt
                            + " but was " + datas.length + ": "
                            + join(datas, separator));

        // escape
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < datas.length; i++) {
            line.append(datas[i].toString().replaceAll("[\r\t\n]", ""));
            if (i < datas.length - 1)
                line.append(separator);
        }

        addLine(line.toString());
    }
}
