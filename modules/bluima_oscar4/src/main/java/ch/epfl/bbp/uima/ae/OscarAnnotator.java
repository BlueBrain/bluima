package ch.epfl.bbp.uima.ae;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static uk.ac.cam.ch.wwmm.oscar.types.NamedEntityType.ASE;
import static uk.ac.cam.ch.wwmm.oscar.types.NamedEntityType.ASES;
import static uk.ac.cam.ch.wwmm.oscar.types.NamedEntityType.COMPOUND;
import static uk.ac.cam.ch.wwmm.oscar.types.NamedEntityType.COMPOUNDS;
import static uk.ac.cam.ch.wwmm.oscar.types.NamedEntityType.GROUP;
import static uk.ac.cam.ch.wwmm.oscar.types.NamedEntityType.LOCANTPREFIX;
import static uk.ac.cam.ch.wwmm.oscar.types.NamedEntityType.POLYMER;
import static uk.ac.cam.ch.wwmm.oscar.types.NamedEntityType.REACTION;

import java.util.Arrays;
import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.TypeCapability;

import uk.ac.cam.ch.wwmm.oscar.Oscar;
import uk.ac.cam.ch.wwmm.oscar.document.NamedEntity;
import uk.ac.cam.ch.wwmm.oscar.types.NamedEntityType;
import ch.epfl.bbp.uima.typesystem.TypeSystem;
import de.julielab.jules.types.Chemical;

/**
 * Wrapper for the OSCAR4 chemical NER, developed by the Murray-Rust research
 * group at the Unilever Centre for Molecular Science Informatics, University of
 * Cambridge (bitbucket.org/wwmm/oscar4/)
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(outputs = { TypeSystem.CHEMICAL })
public class OscarAnnotator extends JCasAnnotator_ImplBase {

    private Oscar oscar = new Oscar();

    public static final List<NamedEntityType> RELEVANT_TYPES = Arrays
            .asList(new NamedEntityType[] { //
            COMPOUND, COMPOUNDS, GROUP, REACTION, LOCANTPREFIX, ASE, ASES,
                    POLYMER });

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        // warmup
        oscar.findNamedEntities("Addition of Butyl or Ethyl Acrylate with HPEI. The synthetic procedure for partially EA- or BA-modified HPEI is exemplified for HPEI25K-EA0.79: 1.00 g of HPEI25K (Mn = 2.50 x 104, 23.3 mmol of amine groups) was dissolved in 5.00 mL of THF, and then 2.52 mL (23.3 mmol) of EA was added.");
    }

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        try {
            // TODO use oscar.findResolved???
            for (NamedEntity ne : oscar.findNamedEntities(//
                    jCas.getDocumentText())) {

                if (RELEVANT_TYPES.contains(ne.getType())) {

                    Chemical chemical = new Chemical(jCas, ne.getStart(),
                            ne.getEnd());
                    if (ne.getConfidence() != Double.NaN)
                        chemical.setConfidence(ne.getConfidence() + "");
                    if (ne.getOntIds() != null && ne.getOntIds().size() > 0)
                        chemical.setRegistryNumber(ne.getOntIds().iterator()
                                .next());
                    // TODO chemical.setType(entity.getType());
                    chemical.addToIndexes();
                }
            }
        } catch (Exception e) {
            throw new AnalysisEngineProcessException(e);
        }
    }

    public static AnalysisEngineDescription getAED()
            throws ResourceInitializationException {
        return createEngineDescription(OscarAnnotator.class);
    }

    public static AnalysisEngine getAE() throws ResourceInitializationException {
        return createEngine(OscarAnnotator.class);
    }
}
