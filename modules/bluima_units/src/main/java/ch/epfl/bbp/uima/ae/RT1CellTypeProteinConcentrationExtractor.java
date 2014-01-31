package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.typesystem.TypeSystem.CELL_TYPE_PROTEIN_CONCENTRATION;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.CONCENTRATION;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.CONCEPT_MENTION;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.PROTEIN;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.SENTENCE;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.apache.uima.fit.util.JCasUtil.subiterate;

import java.util.ArrayList;
import java.util.List;

import org.apache.uima.jcas.JCas;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.TypeCapability;

import ch.epfl.bbp.uima.types.CellType;
import ch.epfl.bbp.uima.types.CellTypeProteinConcentration;
import ch.epfl.bbp.uima.types.Concentration;
import ch.epfl.bbp.uima.types.Protein;
import de.julielab.jules.types.Sentence;

/**
 * Collects existing annotations ({@link Protein}, {@link Concentration} and
 * trigger words) and aggregates (copy) them in a
 * {@link CellTypeProteinConcentration}.
 * 
 * @author renaud.richardet@epfl.ch
 */
@Deprecated
@TypeCapability(inputs = { SENTENCE, PROTEIN, CONCENTRATION, CONCEPT_MENTION }, outputs = { CELL_TYPE_PROTEIN_CONCENTRATION })
public class RT1CellTypeProteinConcentrationExtractor extends
        JCasAnnotator_ImplBase {

    @Override
    public void process(JCas jCas) {

        // AnnotationIndex<Annotation> proteinIndex =
        // jCas.getJFSIndexRepository()
        // .getAnnotationIndex(Protein.type);

        for (Sentence sent : select(jCas, Sentence.class)) {

            List<Protein> prots = new ArrayList<Protein>();
            for (Protein prot : subiterate(jCas, Protein.class, sent, false,
                    true)) {
                prots.add(prot);
            }

            List<Concentration> concs = new ArrayList<Concentration>();
            for (Concentration conc : subiterate(jCas, Concentration.class,
                    sent, false, true)) {
                concs.add(conc);
            }

            List<CellType> cts = new ArrayList<CellType>();
            for (CellType ct : subiterate(jCas, CellType.class, sent, false,
                    true)) {
                cts.add(ct);
            }

            // FIXME 1
            if (!prots.isEmpty() && !concs.isEmpty() && !cts.isEmpty()) {
                CellTypeProteinConcentration ctpc = new CellTypeProteinConcentration(
                        jCas);
                ctpc.setCelltype(cts.get(0));
                ctpc.setConcentration(concs.get(0));
                ctpc.setProtein(prots.get(0));
                ctpc.addToIndexes();
            }
        }
    }
}
