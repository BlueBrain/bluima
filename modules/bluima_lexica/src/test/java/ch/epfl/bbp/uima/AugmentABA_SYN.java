package ch.epfl.bbp.uima;

import static ch.epfl.bbp.uima.LexicaHelper.LEXICA_ROOT;
import static ch.epfl.bbp.uima.utils.ConceptFileWriter.writeConceptFile;
import static ch.epfl.bbp.uima.xml.ConceptMapperParser.parse;

import java.io.File;
import java.util.Map;
import java.util.Map.Entry;

import ch.epfl.bbp.uima.utils.ConceptFileWriter.Concept;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

/**
 * Augment ABA and BAMS with synonyms
 * 
 * @author renaud.richardet@epfl.ch
 */
public class AugmentABA_SYN {

	 final static String LEXICON = "aba";
	//final static String LEXICON = "bams2013";

	public static void main(String[] args) throws Exception {

		String ROOT = LEXICA_ROOT + "resources/brainregions/";

		Map<String, Concept> abaConcepts = parse(new File(ROOT + LEXICON + ".xml"));
		for (Entry<String, Concept> c : abaConcepts.entrySet()) {
			System.err.println(c.getValue());
		}
		// k:concept v: synonym
		ListMultimap<String, String> synonyms = ArrayListMultimap.create();

		String[] atlases = { "aba", "bams2004", "bams2013", "dong", "hof",
				"neuronames", "paxinos", "swanson" };

		for (String atlas : atlases) {
			Map<String, Concept> atlasConcepts = parse(new File(ROOT + atlas
					+ ".xml"));

			// each abaConcept
			for (Entry<String, Concept> abaConcept : abaConcepts.entrySet()) {
				// System.out.println("ABA: " + abaConcept.getKey());

				// each atlas variant
				for (Entry<String, Concept> atlasConcept : atlasConcepts
						.entrySet()) {
					for (String atlasVariant : atlasConcept.getValue()
							.getVariants()) {

						// check if match
						for (String abaVariant : abaConcept.getValue()
								.getVariants()) {
							if (abaVariant.equalsIgnoreCase(atlasVariant)
									&& atlasConcept.getValue().getVariants()
											.size() > 1) {

								// add atlas variants to this aba concept
								for (String atlasVariant2 : atlasConcept
										.getValue().getVariants()) {
									System.out.println("     + syn: "
											+ atlasVariant2);
									synonyms.put(abaConcept.getKey(),
											atlasVariant2);
								}

							}
						}
					}
				}
			}
		}

		System.out.println("added synonyms " + synonyms.size());
		for (String syn : synonyms.keySet()) {
			Concept concept = abaConcepts.get(syn);
			for (String newSyn : synonyms.get(syn)) {
				concept.addVariant(newSyn);
			}
		}

		writeConceptFile(new File(ROOT + LEXICON + "-syn.xml"),
				abaConcepts.values(), "script: Augment_" + LEXICON);
	}
}
