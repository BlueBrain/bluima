package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.BlueUima.PARAM_CONFIG_FILE;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.LINNAEUS_SPECIES;
import static com.google.common.base.Preconditions.checkArgument;
import static java.util.logging.Level.WARNING;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import martin.common.ArgParser;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import uk.ac.man.entitytagger.EntityTagger;
import uk.ac.man.entitytagger.Mention;
import uk.ac.man.entitytagger.matching.Matcher;
import ch.epfl.bbp.uima.types.LinnaeusSpecies;

/**
 * Linnaeus Uima wrapper, for species name recognition and normalization
 * (http://linnaeus.sourceforge.net/ and http://sourceforge
 * .net/projects/linnaeus/files/Linnaeus/linnaeus-uima-wrapper.pear/download).<br>
 * The provided trained model shows 94% recall and 97% precision on the
 * LINNAEUS-species-corpus.<br>
 * LINNAEUS is the subject of the following paper: Gerner M., Nenadic, G. and
 * Bergman, C. M. (2010) LINNAEUS: a species name identification system for
 * biomedical literature. BMC Bioinformatics 11:85.
 * 
 * @author renaud.richardet@epfl.ch
 * @author Mora Gyorgy
 */
@TypeCapability(outputs = { LINNAEUS_SPECIES })
public class LinnaeusAnnotator extends JCasAnnotator_ImplBase {

    private Matcher matcher;
    private Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @ConfigurationParameter(name = PARAM_CONFIG_FILE, mandatory = true)
    private String configFile;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        logger.setLevel(WARNING); // be quiet!!

        checkArgument(new File(configFile).exists(),
                "please provide a valid configuration file for Linnaeus, not found: "
                        + configFile);

        ArgParser ap = new ArgParser(
                new String[] { "--properties", configFile });

        this.matcher = EntityTagger.getMatcher(ap, logger);
    }

    @Override
    public void process(JCas cas) throws AnalysisEngineProcessException {

        String text = cas.getDocumentText();
        List<Mention> mentions = matcher.match(text);
        for (Mention mention : mentions) {

            String mostProbableID = mention.getMostProbableID();
            String idsToString = mention.getIdsToString();

            LinnaeusSpecies species = new LinnaeusSpecies(cas);
            species.setBegin(mention.getStart());
            species.setEnd(mention.getEnd());
            species.setMostProbableSpeciesId(mostProbableID);
            species.setAllIdsString(idsToString);
            species.setAmbigous(mention.isAmbigous());
            species.addToIndexes();
        }
    }

    @Override
    public void collectionProcessComplete()
            throws AnalysisEngineProcessException {
        this.matcher = null;
        super.collectionProcessComplete();
    }
}
