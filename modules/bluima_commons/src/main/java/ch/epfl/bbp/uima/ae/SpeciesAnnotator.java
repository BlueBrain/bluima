package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.MapUtils.keyOfHighestValue;
import static ch.epfl.bbp.MissingUtils.getOrElse;
import static ch.epfl.bbp.collections.DefaultHashMap.intDefaultMap;
import static ch.epfl.bbp.uima.BlueUima.RESOURCES_PATH;
import static ch.epfl.bbp.uima.CommonAnnotatorsHelper.COMMON_ANNOTATORS_ROOT;
import static ch.epfl.bbp.uima.ae.SpeciesAnnotator.Species.None;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.DOCUMENT_SPECIES;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.LINNAEUS_SPECIES;
import static ch.epfl.bbp.uima.utils.Preconditions.checkEquals;
import static com.google.common.collect.Maps.newHashMap;
import static java.lang.Integer.parseInt;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import ch.epfl.bbp.io.SVReader;
import ch.epfl.bbp.uima.types.DocumentSpecies;
import ch.epfl.bbp.uima.types.LinnaeusSpecies;

/**
 * 
 * Adds a document-wide {@link DocumentSpecies} that is the most prominent
 * species family (see {@link Species} enum) of all the {@link LinnaeusSpecies}
 * from that document.
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(inputs = { LINNAEUS_SPECIES }, outputs = { DOCUMENT_SPECIES })
public class SpeciesAnnotator extends JCasAnnotator_ImplBase {

	public enum Species {
		Mus, //
		Rattus, //
		Cricetinae, //
		Mustela_putorius, //
		Homo_sapiens, //
		Simiiformes, // other Si. like monkeys, does not include humans
		Felidae, // cats
		Canis, // dogs
		Chordata, // other Chordata, = does not include aboves
		None
	}

	Map<Integer, Species> speciesMapping = newHashMap();

	@Override
	public void initialize(UimaContext context)
			throws ResourceInitializationException {
		super.initialize(context);
		try {
			for (List<String> line : new SVReader.TSVReader(
					new File(
							COMMON_ANNOTATORS_ROOT
									+ RESOURCES_PATH
									+ "ncbi/20131218_pubmed_species_top1000_resolved.tsv"),
					true)) {
				// ncbi_id name
				// 10116 Rattus
				speciesMapping.put(parseInt(line.get(0)),
						Species.valueOf(line.get(1)));
			}
			checkEquals("mapping should have 591 species", 591,
					speciesMapping.size());

		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	@Override
	public void process(JCas jCas) throws AnalysisEngineProcessException {

		Map<Species, Integer> speciesCount = intDefaultMap();

		for (LinnaeusSpecies sp : select(jCas, LinnaeusSpecies.class)) {
			// e.g. species:ncbi:9685
			int speciesId = parseInt(sp.getMostProbableSpeciesId().substring(
					"species:ncbi:".length()));
			Species species = getOrElse(speciesMapping, speciesId, None);
			speciesCount.put(species, speciesCount.get(species) + 1);
		}

		DocumentSpecies docSpecies = new DocumentSpecies(jCas);
		if (!speciesCount.isEmpty()) {
			docSpecies
					.setFamilyName(keyOfHighestValue(speciesCount).toString());
		}
		docSpecies.addToIndexes();
	}
}
