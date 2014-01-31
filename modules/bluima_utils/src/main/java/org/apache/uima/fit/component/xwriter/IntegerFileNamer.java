package org.apache.uima.fit.component.xwriter;

import org.apache.uima.UimaContext;
import org.apache.uima.fit.component.initialize.ConfigurationParameterInitializer;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.factory.initializable.Initializable;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

/**
 * 
 * Allows to start at a given number
 * 
 * @author renaud.richardet@epfl.ch
 */
public class IntegerFileNamer implements XWriterFileNamer, Initializable {

    /**
     * The parameter name for the configuration parameter that specifies a fixed
     * prefix for all returned file names.
     */
    public static final String PARAM_PREFIX = "prefix";
    @ConfigurationParameter(description = "specify a prefix that is prepended to all returned file names", mandatory = false)
    private String prefix = "";

    public static final String PARAM_START_AT = "startAt";
    @ConfigurationParameter(description = "specify a number to start", mandatory = false)
    private int startAt = 0;

    public String nameFile(JCas jCas) {
        return prefix + startAt++;
    }

    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        ConfigurationParameterInitializer.initialize(this, context);
    }
}
