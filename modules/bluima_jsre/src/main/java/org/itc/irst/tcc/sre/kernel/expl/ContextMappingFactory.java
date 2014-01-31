/*
 * Copyright 2005 ITC-irst (http://www.itc.it/)
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
public class ContextMappingFactory {

    static Logger LOG = LoggerFactory.getLogger(ContextMappingFactory.class
            .getName());

    /** Singleton */
    private static ContextMappingFactory mappingFactory;

    private List<MappingParameters> mappingList;

    @SuppressWarnings("unchecked")
    private ContextMappingFactory() {
        // defaultParameters = new Properties();

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
                LOG.debug("ContextMappingFactory uses the default config file: jsre-config.xml");
                checkFileExists(JSRE_HOME + RESOURCES_PATH + "jsre-config.xml");
                mappingList = (List<MappingParameters>) digester
                        .parse(new File(JSRE_HOME + RESOURCES_PATH
                                + "jsre-config.xml"));
            } else {
                LOG.debug("ContextMappingFactory uses the config file: "
                        + configFile);
                mappingList = (List<MappingParameters>) digester
                        .parse(new File(configFile));
            }

            LOG.debug("mapping-list size: " + mappingList.size());
            for (MappingParameters mp : mappingList)
                LOG.debug("{}", mp);

        } catch (Exception e) {
            LOG.error("woops", e);
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
    public AbstractMapping getInstance(String name)
            throws MappingNotFoundException {
        LOG.info("mappingFactory.getInstance: " + name);

        AbstractMapping mapping = null;
        try {

            for (int i = 0; i < mappingList.size(); i++) {
                MappingParameters mappingParameters = (MappingParameters) mappingList
                        .get(i);

                if (mappingParameters.getName().equals(name)) {
                    LOG.info(mappingParameters.getName() + " ("
                            + mappingParameters.getClassName()
                            + ") mapping found");

                    // instance the mapping
                    LOG.debug("instance the mapping");
                    mapping = (AbstractMapping) (Class
                            .forName(mappingParameters.getClassName()))
                            .newInstance();

                    // set the default parameters
                    LOG.debug("set the default parameters");
                    mapping.setParameters(mappingParameters.getParameters());
                }
            }
        } catch (ClassNotFoundException e) {
            throw new MappingNotFoundException(name + " Mapping not found.");
        } catch (Exception e) {
            LOG.error("", e);
        }
        return mapping;
    }

    public String toString() {
        return "ContextMappingFactory";
    }

    /**
     * Returns <code>ContextMappingFactory</code> object; only one
     * <code>ContextMappingFactory</code> instance can exist.
     * 
     * @return <code>ContextMappingFactory</code> object
     */
    public static synchronized ContextMappingFactory getContextMappingFactory() {
        // logger.debug("ContextMappingFactory.getContextMappingFactory");
        if (mappingFactory == null) {
            mappingFactory = new ContextMappingFactory();
        }

        return mappingFactory;
    }
}