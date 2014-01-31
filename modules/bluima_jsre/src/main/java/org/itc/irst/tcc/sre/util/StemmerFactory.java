/*
 * Copyright 2005 FBK-irst (http://www.fbk.eu)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.itc.irst.tcc.sre.util;

import static ch.epfl.bbp.uima.BlueUima.RESOURCES_PATH;
import static ch.epfl.bbp.uima.JsreHelper.JSRE_HOME;
import static ch.epfl.bbp.uima.utils.Preconditions.checkFileExists;

import java.util.Properties;

import org.apache.commons.digester.Digester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Factory class for vending standard <code>Stemmer</code> objects. Wherever
 * possible, this factory will hand out references to shared
 * <code>Stemmer</code> instances.
 * 
 * @author Claudio Giuliano
 * @version 1.0
 * @since 1.0
 */
public class StemmerFactory {
    static Logger logger = LoggerFactory.getLogger(StemmerFactory.class
            .getName());

    /** Singleton */
    private static StemmerFactory stemmerFactory;

    private Properties initParams;

    private StemmerFactory() {
        initParams = new Properties();

        try {
            Digester digester = new Digester();
            digester.push(this);

            digester.addCallMethod("jsre-config/stemmer-list/stemmer",
                    "addStemmer", 2);
            digester.addCallParam(
                    "jsre-config/stemmer-list/stemmer/stemmer-name", 0);
            digester.addCallParam(
                    "jsre-config/stemmer-list/stemmer/stemmer-class", 1);

            String configFile = System.getProperty("config.file");
            if (configFile == null) {
                logger.debug("StemmerFactory uses the default config file: jsre-config.xml");
                checkFileExists(JSRE_HOME + RESOURCES_PATH + "jsre-config.xml");
                digester.parse(JSRE_HOME + RESOURCES_PATH + "jsre-config.xml");
            } else {
                logger.debug("StemmerFactory uses the config file: "
                        + configFile);
                digester.parse(configFile);
            }
        } catch (Exception e) {
            logger.error("", e);
        }

    }

    public void addStemmer(String stemmerName, String stemmerClass) {
        logger.debug("Add stemmer: " + stemmerName + ", " + stemmerClass);
        initParams.setProperty(stemmerName.toUpperCase(), stemmerClass);
    }

    /**
     * Returns the <i>id</i> of the specified stemmer and adds the stemmer to
     * the lexicon if it is not present yet.
     * <p>
     * If the lexicon is read only, stemmers not already present in the lexicon
     * will not be added and a <code>null</code> <i>id</i> will be returned.
     * 
     * @param name
     *            the string representation of the stemmer.
     * @return the <i>id</i> of the specified stemmer.
     */
    public Stemmer getInstance(String name) throws StemmerNotFoundException {
        // logger.debug("stemmerFactory.getInstance: " + name);
        String stemmerClass = initParams.getProperty(name.toUpperCase());
        Stemmer stemmer = null;
        try {
            stemmer = (Stemmer) (Class.forName(stemmerClass)).newInstance();
            // stemmer.set(initParams);
        } catch (Exception e) {
            throw new StemmerNotFoundException(name + " stemmer not found.");
        }

        return stemmer;
    }

    public String toString() {
        return "StemmerFactory";
    }

    /**
     * Returns <code>StemmerFactory</code> object; only one
     * <code>StemmerFactory</code> instance can exist.
     * 
     * @return <code>StemmerFactory</code> object
     */
    public static synchronized StemmerFactory getStemmerFactory() {
        if (stemmerFactory == null) {
            stemmerFactory = new StemmerFactory();
        }
        return stemmerFactory;
    }
}
