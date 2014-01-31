package ch.epfl.bbp.uima.ae.cleanup;

import static com.google.common.collect.Lists.newArrayList;
import static org.apache.uima.fit.util.JCasUtil.selectCovered;

import java.util.ArrayList;
import java.util.List;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;

import ch.epfl.bbp.uima.types.AgeDictTerm;
import ch.epfl.bbp.uima.types.DiseaseDictTerm;
import ch.epfl.bbp.uima.types.IonchannelDictTerm;
import ch.epfl.bbp.uima.types.LinnaeusSpecies;
import ch.epfl.bbp.uima.types.Measure;
import ch.epfl.bbp.uima.types.OrganismDictTerm;
import ch.epfl.bbp.uima.types.Punctuation;
import ch.epfl.bbp.uima.types.RegionDictTerm;
import ch.epfl.bbp.uima.types.SexDictTerm;

@Deprecated
public class OneRulesThemOutAnnotator extends JCasAnnotator_ImplBase {

	/** order matters! */
	@SuppressWarnings("unchecked")
	public static final ArrayList<Class<? extends Annotation>> RULE_THEM_OUT_ANNOTATIONS = newArrayList(
			AgeDictTerm.class,//
			IonchannelDictTerm.class, //
			OrganismDictTerm.class,//
			Measure.class,//
			Punctuation.class,//
			LinnaeusSpecies.class,//
			RegionDictTerm.class, //
			DiseaseDictTerm.class,//
			// CellDictTerm.class,//
			SexDictTerm.class//
	);

	@Override
	public void process(JCas jCas) throws AnalysisEngineProcessException {

		for (Class<? extends Annotation> ruleAnn : RULE_THEM_OUT_ANNOTATIONS) {

			for (Annotation t : JCasUtil.select(jCas, ruleAnn)) {

				List<Annotation> as = selectCovered(jCas, Annotation.class,
						t.getBegin(), t.getEnd());
				for (Annotation a : as) {
					if ((!a.getClass().equals(ruleAnn))
							&& DisambiguatorAnnotator.BIO_ANNOTATIONS
									.contains(a.getClass()))
						a.removeFromIndexes();
				}
			}
		}
	}
}