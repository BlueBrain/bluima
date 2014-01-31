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
package org.itc.irst.tcc.sre.kernel.expl;

import static ch.epfl.bbp.uima.BlueUima.RESOURCES_PATH;
import static ch.epfl.bbp.uima.JsreHelper.JSRE_HOME;
import static ch.epfl.bbp.uima.utils.Preconditions.checkFileExists;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.digester.Digester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Factory class for vending standard <code>Mapping</code> objects. Wherever
 * possible, this factory will hand out references to shared
 * <code>Mapping</code> instances.
 * 
 * @author Claudio Giuliano
 * @version 1.0
 * @since 1.0
 */
public class MappingFactory {
    static Logger LOG = LoggerFactory.getLogger(MappingFactory.class.getName());

    /** Singleton */
    private static MappingFactory mappingFactory;

    private List<MappingParameters> mappingList;

    @SuppressWarnings("unchecked")
    private MappingFactory() {
        // defaultParameters = new Properties();
        mappingFactory = this;

        try {
            Digester digester = new Digester();
            digester.setValidating(false);

            digester.addObjectCreate("jsre-config/mapping-list",
                    ArrayList.class);

            digester.addObjectCreate("jsre-config/mapping-list/mapping",
                    MappingParameters.class);
            digester.addBeanPropertySetter(
                    "jsre-config/mapping-list/mapping/mapping-name", "name");
            digester.addBeanPropertySetter(
                    "jsre-config/mapping-list/mapping/mapping-class",
                    "className");

            digester.addCallMethod(
                    "jsre-config/mapping-list/mapping/init-param",
                    "setParameters", 2);
            digester.addCallParam(
                    "jsre-config/mapping-list/mapping/init-param/param-name", 0);
            digester.addCallParam(
                    "jsre-config/mapping-list/mapping/init-param/param-value",
                    1);

            digester.addSetNext("jsre-config/mapping-list/mapping", "add");

            String configFile = System.getProperty("config.file");
            if (configFile == null) {
                LOG.debug("MappingFactory uses the default config file: jsre-config.xml");
                checkFileExists(JSRE_HOME + RESOURCES_PATH + "jsre-config.xml");
                mappingList = (List<MappingParameters>) digester
                        .parse(new File(JSRE_HOME + RESOURCES_PATH
                                + "jsre-config.xml"));
            } else {
                LOG.debug("MappingFactory uses the config file: " + configFile);
                mappingList = (List<MappingParameters>) digester
                        .parse(configFile);
            }

            LOG.debug("mapping-list size: " + mappingList.size());
            for (MappingParameters mp : mappingList)
                LOG.debug(mp.toString());

        } catch (Exception e) {
            LOG.error("woops ", e);
        }
    }

    /**
     * Returns the <i>id</i> of the specified Mapping and adds the Mapping to
     * the lexicon if it is not present yet.
     * <p>
     * If the lexicon is read only, mappings not already present in the lexicon
     * will not be added and a <code>null</code> <i>id</i> will be returned.
     * 
     * @param name
     *            the string representation of the Mapping.
     * @return the <i>id</i> of the specified Mapping.
     */
    public Mapping getInstance(String name) throws MappingNotFoundException {
        LOG.info("mappingFactory.getInstance: " + name);

        Mapping mapping = null;
        try {
            for (int i = 0; i < mappingList.size(); i++) {
                MappingParameters mappingParameters = mappingList.get(i);

                if (mappingParameters.getName().equals(name)) {
                    LOG.info(mappingParameters.getName() + " ("
                            + mappingParameters.getClassName()
                            + ") mapping found");

                    // instantiate the mapping
                    LOG.debug("instance the mapping");
                    mapping = (Mapping) (Class.forName(mappingParameters
                            .getClassName())).newInstance();

                    // set the default parameters
                    LOG.debug("set the default parameters");
                    mapping.setParameters(mappingParameters.getParameters());
                }
            }
        } catch (ClassNotFoundException e) {
            throw new MappingNotFoundException(name + " Mapping not found.");
        } catch (Exception e) {
            LOG.error("woooops", e);
        }

        return mapping;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        Iterator<MappingParameters> it = mappingList.iterator();
        while (it.hasNext())
            sb.append(it.next().toString());
        return sb.toString();
    }

    /**
     * Returns <code>MappingFactory</code> object; only one
     * <code>MappingFactory</code> instance can exist.
     * 
     * @return <code>MappingFactory</code> object
     */
    public static synchronized MappingFactory getMappingFactory() {
        // logger.debug("MappingFactory.getMappingFactory");
        if (mappingFactory == null) {
            mappingFactory = new MappingFactory();
        }

        return mappingFactory;
    }

    // public static void main(String args[]) throws Exception {
    // String logConfig = System.getProperty("log-config");
    // if (logConfig == null)
    // logConfig = "log-config.txt";
    //
    // PropertyConfigurator.configure(logConfig);
    //
    // if (args.length < 2) {
    // System.err
    // .println("java -mx512M org.itc.irst.tcc.sre.kernel.expl.MappingFactory Mapping word+");
    // System.exit(0);
    // }
    //
    // MappingFactory mappingFactory = MappingFactory.getMappingFactory();
    // Mapping mapping = mappingFactory.getInstance(args[0]);
    // logger.info(mapping + "");
    // }
}