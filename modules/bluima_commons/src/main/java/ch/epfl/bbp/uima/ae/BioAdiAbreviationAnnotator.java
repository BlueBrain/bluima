package ch.epfl.bbp.uima.ae;

import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.TypeCapability;

import aiiaadi.util.Utility;
import ch.epfl.bbp.uima.typesystem.TypeSystem;
import de.julielab.jules.types.Abbreviation;
import de.julielab.jules.types.Annotation;

/**
 * Finds abbreviations (e.g. "PC" for pyramidal cells) using BioAdi
 * (http://bioagent.iis.sinica.edu.tw/BIOADI/), Biomedical Abbreviation
 * Definition Identifier.<br>
 * BioAdi identifies the mapping between SF (Short Form) and LF (Long Form)
 * terms in paper. The SF and LF terms can vary and different writing habits
 * generate different subtypes of terms. For example, HCC generally stands for
 * "Hepatocellular Carcinoma", but it can also be written as "Hepatoma",
 * "hepatocellular cancer", "hepato-cellular carcinoma", and so on and so forth.
 * These transformers cause problems on many fields such as database querying,
 * text mining, and literature annotation.<br>
 * Paper: BIOADI: a machine learning approach to identifying abbreviations and
 * definitions in biological literature Cheng-Ju Kuo, Maurice HT Ling, Kuan-Ting
 * Lin, and Chun-Nan Hsu, BMC Bioinformatics. 2009; 10(Suppl 15): S7.
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(outputs = { TypeSystem.ABBREVIATION, TypeSystem.ANNOTATION })
public class BioAdiAbreviationAnnotator extends JCasAnnotator_ImplBase {

	public String dummy;

	@Override
	public void initialize(UimaContext context)
			throws ResourceInitializationException {
		super.initialize(context);
		// just to warm it up...
		dummy = Utility.performTest("",
				" for normal pressure hydrocephalus (NPH) was ").get(0);
	}

	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		String text = aJCas.getDocumentText();
		if (text.length() > 0) {

			List<String> abrevs = Utility.performTest("", text);
			for (String abrevArr : abrevs) {

				try {
					// e.g. NPH|normal pressure hydrocephalus|0.9929856278736344
					String[] abrev = abrevArr.split("\\|");

					String shortF = abrev[0];
					String longF = abrev[1];

					int shortStart = text.indexOf(shortF);
					int longStart = text.indexOf(longF);

					Annotation a = new Annotation(aJCas, longStart, longStart
							+ longF.length());
					a.setComponentId(BioAdiAbreviationAnnotator.class.getName());
					a.addToIndexes();

					Abbreviation abr = new Abbreviation(aJCas, shortStart,
							shortStart + shortF.length());
					abr.setDefinedHere(true); // TODO
					abr.setExpan(longF);
					abr.setTextReference(a);
					abr.setConfidence(abrev[2]);
					abr.setComponentId(BioAdiAbreviationAnnotator.class
							.getName());
					abr.addToIndexes();

				} catch (Exception e) { // something went wrong with the indexOf
					System.err.println("could not match abreviation, " + e);
				}
			}
		}
	}
}
