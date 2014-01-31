package org.apache.uima.fit.component.xwriter;

import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import de.julielab.jules.types.Header;

/**
 * 
 * Set the {@link Header} document id as filename
 * 
 * @author renaud.richardet@epfl.ch
 */
public class HeaderDocIdFileNamer implements XWriterFileNamer {

    public String nameFile(JCas jCas) {
	Header header = JCasUtil.selectSingle(jCas, Header.class);
	return header.getDocId();
    }
}
