package ch.epfl.bbp.nlp;

import java.io.IOException;
import java.io.InputStream;

/**
 * An InputStream with its associated filename
 * 
 * The filename can be used to interpret the content of the stream. (e.g. is it compressed?)
 */
public class ModelStream extends InputStream {
    
    public ModelStream(String filename, InputStream input) {
        this.filename = filename;
        this.input = input;
    }

    public String getFilename() {
        return filename;
    }

    @Override
    public int read() throws IOException {
        return input.read();
    }

    private final String filename;
    private final InputStream input;
}
