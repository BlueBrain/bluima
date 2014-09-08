package ch.epfl.bbp.uima;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.Lists.newArrayList;
import static org.apache.commons.io.FileUtils.iterateFiles;
import static org.apache.uima.fit.factory.ResourceCreationSpecifierFactory.createResourceCreationSpecifier;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.conceptMapper.support.dictionaryResource.DictionaryResource_impl;
import org.apache.uima.fit.factory.ExternalResourceFactory;
import org.apache.uima.resource.ResourceInitializationException;
import org.xml.sax.SAXException;

import ch.epfl.bbp.uima.ae.OpenNlpHelper;
import ch.epfl.bbp.uima.utils.SCharsStemmer;

import com.google.common.collect.Lists;

public class LexicaHelper {

    public final static String LEXICA_ROOT = BlueUima.BLUE_UIMA_ROOT
            + "modules/bluima_lexica/";

    private static File tokenDescrPath = null; // TODO should not be static

    private static String getTokenDescPath(AnalysisEngineDescription tokenDesc)
            throws IOException, ResourceInitializationException, SAXException {

        if (tokenDescrPath != null)
            return tokenDescrPath.getAbsolutePath();

        tokenDescrPath = File.createTempFile("tmp", "TokenizerAnnotator.xml");
        tokenDesc.toXML(new FileWriter(tokenDescrPath));
        return tokenDescrPath.getAbsolutePath();
    }

    /**
     * @param path
     *            e.g. baseline/age
     */
    @SuppressWarnings("unchecked")
    public static AnalysisEngineDescription getConceptMapper(String path,
            String[] args) throws UIMAException, IOException, SAXException {

        String conceptMapper = LEXICA_ROOT + "desc/" + path
                + "ConceptMapper.xml";
        if (!new File(conceptMapper).exists()) {

            String fileList = "";
            for (File f : newArrayList(iterateFiles(new File(LEXICA_ROOT
                    + "desc"), new String[] { "" }, true))) {
                fileList += f.getPath() + " ";
            }

            throw new FileNotFoundException("no ConceptMapper file at "
                    + conceptMapper + ", available: " + fileList);
        }

        String lexicon = LEXICA_ROOT + "resources/" + path + ".xml";
        checkArgument(new File(lexicon).exists(), "no lexicon file at "
                + lexicon);

        @SuppressWarnings("rawtypes")
        List argsArray;
        if (args == null) {
            argsArray = newArrayList("TokenizerDescriptorPath",
                    getTokenDescPath(OpenNlpHelper.getTokenizer()), "Stemmer",
                    SCharsStemmer.class.getName());
        } else {
            argsArray = Lists.newArrayList(args);
            argsArray.add("TokenizerDescriptorPath");
            argsArray.add(getTokenDescPath(OpenNlpHelper.getTokenizer()));
        }

        AnalysisEngineDescription aed = (AnalysisEngineDescription) createResourceCreationSpecifier(
                conceptMapper, argsArray.toArray());

        // Create the external resource dependency for the model and bind it
        ExternalResourceFactory.createDependencyAndBind(aed, "DictionaryFile",
                DictionaryResource_impl.class, "file:" + lexicon);
        return aed;
    }

    public static AnalysisEngineDescription getConceptMapper(String path)
            throws UIMAException, IOException, SAXException {
        return getConceptMapper(path, OpenNlpHelper.getTokenizer());
    }

    public static AnalysisEngineDescription getConceptMapper(String path,
            AnalysisEngineDescription tokenDesc) throws UIMAException,
            IOException, SAXException {

        String conceptMapper = LEXICA_ROOT + "desc/" + path
                + "ConceptMapper.xml";
        checkArgument(new File(conceptMapper).exists(),
                "no ConceptMapper file at " + conceptMapper);

        String lexicon = LEXICA_ROOT + "resources/" + path + ".xml";
        checkArgument(new File(lexicon).exists(), "no lexicon file at "
                + lexicon);

        AnalysisEngineDescription aed = (AnalysisEngineDescription) createResourceCreationSpecifier(
                conceptMapper, new Object[] { "TokenizerDescriptorPath",
                        getTokenDescPath(tokenDesc), "Stemmer",
                        SCharsStemmer.class.getName() });

        // Create the external resource dependency for the model and bind it
        ExternalResourceFactory.createDependencyAndBind(aed, "DictionaryFile",
                DictionaryResource_impl.class, "file:" + lexicon);
        return aed;
    }
}
