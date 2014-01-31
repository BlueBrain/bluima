package ch.epfl.bbp.uima.ae;

import static org.apache.uima.fit.util.JCasUtil.select;

import java.util.Collection;
import java.util.List;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;

import ch.epfl.bbp.uima.types.AgeDictTerm;
import ch.epfl.bbp.uima.types.DictTerm;
import ch.epfl.bbp.uima.types.DiseaseDictTerm;
import ch.epfl.bbp.uima.types.DocumentTextHolder;
import ch.epfl.bbp.uima.types.IonchannelDictTerm;
import ch.epfl.bbp.uima.types.MethodDictTerm;
import ch.epfl.bbp.uima.types.MoleculeDictTerm;
import ch.epfl.bbp.uima.types.MultipleProteins;
import ch.epfl.bbp.uima.types.OrganismDictTerm;
import ch.epfl.bbp.uima.types.RegionDictTerm;
import ch.epfl.bbp.uima.types.SexDictTerm;

import com.google.common.collect.Lists;

/**
 * Adds e.g. setAnnotType("⊂AGE⊃") to all {@link AgeDictTerm} annotations. This
 * is used by Lucas
 * 
 * @author renaud.richardet@epfl.ch
 */
public class LucasHelperAnnotator extends JCasAnnotator_ImplBase {

	static final String before = "⊂=", after = "=⊃";

	// FIXME make it dynamic for all DictTerm
	//

	static final String[] inserts = { "age", "ionchannel", "disease",
			"organism", "sex", "region", "molecule", "method" };

	@SuppressWarnings("unchecked")
	static final List<Class<? extends DictTerm>> annots = Lists.newArrayList(
			AgeDictTerm.class, IonchannelDictTerm.class, DiseaseDictTerm.class,
			OrganismDictTerm.class, SexDictTerm.class, RegionDictTerm.class,
			MoleculeDictTerm.class, MethodDictTerm.class);

	@Override
	public void process(JCas jcas) throws AnalysisEngineProcessException {

		for (int i = 0; i < annots.size(); i++) {

			String insert = before + inserts[i].toUpperCase() + after;
			Collection<? extends DictTerm> selects = select(jcas, annots.get(i));
			for (DictTerm d : selects) {
				d.setAnnotType(insert);
				d.setDictCanon("⊂" + d.getDictCanon() + "⊃");
			}
			// add annot if 2 prots
			if (inserts[i].equals("molecule") && selects.size() > 1) {
				MultipleProteins mp = new MultipleProteins(jcas);
				mp.setPresent(1);
				mp.addToIndexes();
			}
		}

		String text = jcas.getDocumentText();
		DocumentTextHolder dth = new DocumentTextHolder(jcas, 0, text.length());
		dth.setText(text);
		dth.addToIndexes();
	}
}