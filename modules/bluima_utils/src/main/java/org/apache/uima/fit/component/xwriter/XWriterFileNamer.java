package org.apache.uima.fit.component.xwriter;

import org.apache.uima.jcas.JCas;

/**
 * To allow legacy code..
 * 
 * @author renaud.richardet@epfl.ch
 * 
 */
public interface XWriterFileNamer {

    String nameFile(JCas jCas);

}
