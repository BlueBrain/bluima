package ch.epfl.bbp.io;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

/**
 * Simple util to write text to a file
 * 
 * @author renaud.richardet@epfl.ch
 */
public class TextFileWriter implements Closeable {

    protected BufferedWriter writer;

    public TextFileWriter(File f) throws IOException {
        this.writer = new BufferedWriter(new FileWriter(f));
    }

    public TextFileWriter(String file) throws IOException {
        this.writer = new BufferedWriter(new FileWriter(new File(file)));
    }

    /** add text, and a new line */
    public void addLine(String line) {
        try {
            writer.append(line);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** add text, without new line */
    public void addText(String text) {
        try {
            writer.append(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws IOException {
        writer.close();
    }

    public void flush() throws IOException {
        writer.flush();
    }

    public static void write(File f, String text) throws IOException {
        TextFileWriter w = new TextFileWriter(f);
        w.addText(text);
        w.close();
    }

    public static void write(File f, Collection<Integer> ints)
            throws IOException {
        TextFileWriter w = new TextFileWriter(f);
        Iterator<Integer> it = ints.iterator();
        while (it.hasNext()) {
            w.addLine(it.next() + "");
        }
        w.close();
    }
}
