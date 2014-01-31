package javatools.filehandlers;

import java.io.File;
import java.io.IOException;

public class TSVWriter {

  /* the writer */
  private UTF8Writer writer = null;

  /**
   * constructor take a string as a path
   * @param path
   * @throws IOException
   */
  public TSVWriter(String path) throws IOException {
    this(new File(path));
  }

  /**
   * constructor take a file
   * @param file
   * @throws IOException
   */
  public TSVWriter(File file) throws IOException {
    writer = new UTF8Writer(file);
  }

  /**
   * the main method to write facts as arg1 tab relation tab arg2 
   * @param arg1
   * @param relation
   * @param arg2
   * @throws IOException
   */
  public void write(String arg1, String relation, String arg2) throws IOException {
    writer.writeln(arg1 + "\t" + relation + "\t" + arg2);
  }

  /**
   * the main method to write facts as arg1 tab relation tab arg2 
   * @param arg1
   * @param relation
   * @param arg2
   * @throws IOException
   */
  public void write(String pattern, String method, String[] factResults) throws IOException {
    StringBuffer sb = new StringBuffer();
    for (int i = 0;i<factResults.length;i++){
      sb.append(factResults[i]);
      if (i+1!= factResults.length){
        sb.append(";\t");  
      }
    }
    writer.writeln(pattern + "\t" + method + "\t" + sb.toString());
  }

  /**
   * flushes and closes the writer
   * @throws IOException
   */
  public void close() throws IOException {
    writer.flush();
    writer.close();
  }

}
