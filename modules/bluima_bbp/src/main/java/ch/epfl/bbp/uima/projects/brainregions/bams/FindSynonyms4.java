package ch.epfl.bbp.uima.projects.brainregions.bams;

import static ch.epfl.bbp.uima.LexicaHelper.getConceptMapper;
import static ch.epfl.bbp.uima.ae.OpenNlpHelper.getSentenceSplitter;
import static ch.epfl.bbp.uima.ae.OpenNlpHelper.getTokenizer;
import static ch.epfl.bbp.uima.testutils.UimaTests.getTestCas;
import static ch.epfl.bbp.uima.xml.bams.BamsOntologyParser.parse;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.util.List;

import org.apache.uima.jcas.JCas;
import org.python.google.common.collect.Lists;

import ch.epfl.bbp.uima.types.BrainRegionDictTerm;
import ch.epfl.bbp.uima.uimafit.JcasPipelineBuilder;
import ch.epfl.bbp.uima.xml.bams.BamsOntology;
import ch.epfl.bbp.uima.xml.bams.BrainPart;

/**
 * Compare bams2013 and bams2004
 * 
 * @author renaud.richardet@epfl.ch
 */
public class FindSynonyms4 {

	public static void main(String[] args) throws Exception {

		BamsOntology bamsOnto = parse();

		for (String b2004 : bams2004) {

			boolean found = false;
			for (BrainPart bp : bamsOnto.getBrainParts().values()) {
				if (b2004.equalsIgnoreCase(bp.getName()))
					found = true;
			}
			if (!found)
				System.err.println("not found\t" + b2004);
		}
	}

	public static void main2(String[] args) throws Exception {

		JcasPipelineBuilder p = new JcasPipelineBuilder();
		p.add(getSentenceSplitter());
		p.add(getTokenizer());
		p.add(getConceptMapper("/brainregions/bams2004"));

		BamsOntology bamsOnto = parse();

		for (BrainPart bp : bamsOnto.getBrainParts().values()) {

			JCas jCas = getTestCas(bp.getName());
			p.process(jCas);

			// System.out.println(bp);
			boolean found = false;
			for (BrainRegionDictTerm br : select(jCas,
					BrainRegionDictTerm.class)) {
				found = true;
				// System.out.println(" found: " + br.getCoveredText());
			}
			if (!found)
				System.err.println("not found\t" + bp.getName());
		}
	}

	final static List<String> bams2004 = Lists
			.newArrayList(
					"Brain",//
					"Spinal Cord",//
					"Cerebrum",//
					"Cerebellum",//
					"Brainstem",//
					"Interbrain",//
					"Cerebral cortex",//
					"Cerebral cortex, layers1-6a [cortical plate]",//
					"Olfactory areas",//
					"Main olfactory bulb",//
					"Main olfactory bulb, glomerular layer",//
					"Main olfactory bulb, outer plexiform layer",//
					"Main olfactory bulb, mitral layer",//
					"Main olfactory bulb, inner plexiform layer",//
					"Main olfactory bulb, granule layer",//
					"Accessory olfactory bulb",//
					"Accessory olfactory bulb, glomerular layer",//
					"Accessory olfactory bulb, mitral layer",//
					"Accessory olfactory bulb, granular layer",//
					"Hippocampal formation",//
					"Retrohippocampal region",//
					"Entorhinal area",//
					"Entorhinal area, lateral part",//
					"Entorhinal area, lateral part layers 1-6",//
					"Entorhinal area medial part, dorsal zone",//
					"Entorhinal area medial part, dorsal zone layers 1-6",//
					"Entorhinal area medial part, ventral zone",//
					"Presubiculum",//
					"Presubiculum, layers 1-6",//
					"Postsubiculum",//
					"Postsubiculum layers 1-6",//
					"Thalamus",//
					"Dorsal Thalamus",//
					"Anterior group of the dorsal thalamus",//
					"Anteroventral nucleus of thalamus",//
					"Anteromedial nucleus of thalamus",//
					"Anteromedial nucleus of thalamus dorsal part",//
					"Anteromedial nucleus of thalamus ventral part",//
					"Anterodorsal nucleus of the thalamus",//
					"Interanteromedial nucleus of the thalamus",//
					"Interanterodorsal nucleus of the thalamus",//
					"Lateral dorsal nucleus of thalamus",//
					"Medial group of the dorsal thalamus",//
					"Mediodorsal nucleus of the thalamus",//
					"Mediodorsal nucleus of the thalamus medial part",//
					"Mediodorsal nucleus of the thalamus central part",//
					"Mediodorsal nucleus of the thalamus lateral part",//
					"Intermediodorsal nucleus of the thalamus",//
					"Submedial nucleus of the thalamus",//
					"Perireunensis nucleus",//
					"Midline group of the dorsal thalamus",//
					"Paraventricular nucleus of the thalamus",//
					"Parataenial nucleus",//
					"Nucleus reuniens",//
					"Nucleus reuniens rostral division",//
					"Nucleus reuniens rostral division rostral part",//
					"Nucleus reuniens rostral division dorsal part",//
					"Nucleus reuniens rostral division ventral part",//
					"Nucleus reuniens rostral division lateral part",//
					"Nucleus reuniens rostral division median part",//
					"Nucleus reuniens caudal division",//
					"Nucleus reuniens caudal division caudal part",//
					"Nucleus reuniens caudal division dorsal part",//
					"Nucleus reuniens caudal division median part",//
					"Intralaminar nuclei of the dorsal thalamus",//
					"Rhomboid nucleus",//
					"Central medial nucleus of the thalamus",//
					"Paracentral nucleus of the thalamus",//
					"Central lateral nucleus of the thalamus",//
					"Parafascicular nucleus",//
					"Lateral group of the dorsal thalamus",//
					"Lateral posterior nucleus of the thalamus",//
					"Posterior complex of the thalamus",//
					"Suprageniculate nucleus",//
					"Posterior limiting nucleus of the thalamus",//
					"Ventral group of the dorsal thalamus",//
					"Ventral anterior-lateral complex of the thalamus",//
					"Ventral medial nucleus of the thalamus",//
					"Ventral posterior complex of the thalamus",//
					"Basal Nuclei",//
					"Striatum",//
					"Pallidum rostral region",//
					"Bed nuclei of the stria terminalis",//
					"Bed nuclei of the stria terminalis anterior division",//
					"Bed nuclei of the stria terminalis anterior division anterodorsal area",//
					"Bed nuclei of the stria terminalis anterolateral area",//
					"Bed nuclei of the stria terminalis anterior division anteroventral area",//
					"Bed nuclei of the stria terminalis oval nucleus",//
					"Bed nuclei of the stria terminalis juxtacapsular nucleus",//
					"Bed nuclei of the stria terminalis rhomboid nucleus",//
					"Bed nuclei of the stria terminalis anterior division dorsomedial nucleus",//
					"Bed nuclei of the stria terminalis anterior division dorsolateral nucleus",//
					"Bed nuclei of the stria terminalis anterior division fusiform nucleus",//
					"Bed nuclei of the stria terminalis anterior division ventral nucleus",//
					"Bed nuclei of the stria terminalis anterior division magnocellular nucleus",//
					"Bed nuclei of the stria terminalis posterior division",//
					"Bed nuclei of the stria terminalis posterior division principal nucleus",//
					"Bed nuclei of the stria terminalis posterior division interfascicular  nucleus",//
					"Bed nuclei of the stria terminalis posterior division transverse nucleus",//
					"Bed nuclei of the stria terminalis posterior division premedullary nucleus",//
					"Bed nuclei of the stria terminalis posterior division dorsal nucleus",//
					"Bed nuclei of the stria terminalis posterior division strial extension",//
					"Bed nuclei of the stria terminalis posterior division cell-sparse zone",//
					"Bed nucleus of the anterior commissure",//
					"Bed nucleus of the stria medularis",//
					"Pallidum",//
					"Striatum dorsal region",//
					"Caudoputamen",//
					"Striatum ventral region",//
					"Nucleus accumbens",//
					"Hypothalamus",//
					"Epithalamus",//
					"Lateral forebrain bundle system",//
					"Corpus callosum",//
					"Corpus callosum anterior forceps",//
					"Corpus callosum external capsule",//
					"Corpus callosum extreme capsule",//
					"Genu of corpus callosum",//
					"Corpus callosum posterior forceps",//
					"Rostrum of corpus callosum",//
					"Splenium of corpus callosum",//
					"Medial forebrain bundle system",//
					"Amygdalar capsule",//
					"Ansa peduncularis",//
					"Anterior commissure, temporal limb",//
					"Fornix system",//
					"Alveus of fornix",//
					"Dorsal fornix",//
					"Fimbria",//
					"Precommissural fornix",//
					"Postcommissural fornix",//
					"Medial corticohypothalamic tract",//
					"Columns of the fornix",//
					"Hippocampal commissures",//
					"Dorsal hippocampal commissure",//
					"Dorsal hippocampal commissure angular bundle",//
					"Ventral hippocampal commissure",//
					"Perforant path",//
					"Lateral ventricle",//
					"Rhinocele",//
					"Subependymal zone",//
					"Interventricular foramen",//
					"Third ventricle",//
					"Third ventricle preoptic recess",//
					"Third ventricle periventricular recess",//
					"Third ventricle mamillary recess",//
					"Cerebral aqueduct",//
					"Cerebral aqueduct collicular recess",//
					"Cerebral aqueduct subcommissural organ",//
					"Fourth ventricle",//
					"Fourth ventricle median aperture",//
					"Fourth ventricle lateral aperture",//
					"Fourth ventricle lateral recess",//
					"Rostral medullary velum",//
					"Caudal medullary velum",//
					"Corticospinal tract",//
					"Internal capsule",//
					"Cerebral peduncle",//
					"Thalamic peduncles",//
					"Corticotectal tract",//
					"Corticorubral tract",//
					"Corticopontine tract",//
					"Corticobulbar tract",//
					"Pyramidal decussation",//
					"Pyramidal tract, crossed",//
					"Retina",//
					"Periventricular zone of the hypothalamus",//
					"Vascular organ of the lamina terminalis",//
					"Suprachiasmatic preoptic nucleus",//
					"Median preoptic nucleus",//
					"Anteroventral periventricular nucleus",//
					"Preoptic periventricular nucleus",//
					"Supraoptic nucleus",//
					"Supraoptic nucleus, retrochiasmatic part",//
					"Supraoptic nucleus, accessory supraoptic group",//
					"Supraoptic nucleus, accessory supraoptic group, nucleus circularis",//
					"Paraventricular nucleus of the hypothalamus",//
					"Paraventricular nucleus of the hypothalamus, descending division",//
					"Paraventricular nucleus of the hypothalamus, descending division, medial parvicellular part, ventral zone",//
					"Paraventricular nucleus of the hypothalamus, descending division, dorsal parvicellular part",//
					"Paraventricular nucleus of the hypothalamus, descending division, lateral parvicellular part",//
					"Paraventricular nucleus of the hypothalamus, descending division, forniceal part",//
					"Paraventricular nucleus of the hypothalamus, magnocellular division",//
					"Paraventricular nucleus of the hypothalamus, magnocellular division, anterior magnocellular part",//
					"Paraventricular nucleus of the hypothalamus, magnocellular division, medial magnocellular part",//
					"Paraventricular nucleus of the hypothalamus, magnocellular division, posterior magnocellular part",//
					"Paraventricular nucleus of the hypothalamus, magnocellular division, posterior magnocellular part, medial zone",//
					"Paraventricular nucleus of the hypothalamus, magnocellular division, posterior magnocellular part, lateral zone",//
					"Paraventricular nucleus of the hypothalamus, parvicellular division",//
					"Paraventricular nucleus of the hypothalamus, parvicellular division, anterior parvicellular part",//
					"Paraventricular nucleus of the hypothalamus, parvicellular division, medial parvicellular part, dorsal zone",//
					"Paraventricular nucleus of the hypothalamus, parvicellular division, periventricular part",//
					"Striatum caudal (amygdalar) region",//
					"Anterior amygdaloid area",//
					"Central nucleus of amygdala",//
					"Central nucleus of amygdala medial part",//
					"Central nucleus of amygdala lateral part",//
					"Central nucleus of amygdala capsular part",//
					"Medial nucleus of the amygdala",//
					"Medial nucleus of the amygdala anterodorsal part",//
					"Medial nucleus of the amygdala anteroventral part",//
					"Medial nucleus of the amygdala posterodorsal part",//
					"Medial nucleus of the amygdala posterodorsal part,sublayer a",//
					"Medial nucleus of the amygdala posterodorsal part,sublayer b",//
					"Medial nucleus of the amygdala posterodorsal part,sublayer c",//
					"Medial nucleus of the amygdala posteroventral part",//
					"Bed nucleus of the accessory olfactory part",//
					"Intercalated nuclei of the amygdala",//
					"Anterior periventricular nucleus of the hypothalamus",//
					"Dorsomedial nucleus of the hypothalamus",//
					"Dorsomedial nucleus of the hypothalamus anterior part",//
					"Dorsomedial nucleus of the hypothalamus posterior part",//
					"Dorsomedial nucleus of the hypothalamus ventral part",//
					"Intermediate periventricular nucleus of the hypothalamus",//
					"Arcuate nucleus of the hypothalamus",//
					"Posterior periventricular nucleus of the hypothalamus",//
					"Medial zone of the hypothalamus",//
					"Medial preoptic area",//
					"Medial preoptic nucleus",//
					"Medial preoptic nucleus lateral part",//
					"Medial preoptic nucleus medial part",//
					"Medial preoptic nucleus central part",//
					"Anterodorsal preoptic nucleus",//
					"Anteroventral preoptic nucleus",//
					"Posterodorsal preoptic nucleus",//
					"Parastrial nucleus",//
					"Anterior hypothalamic area",//
					"Anterior hypothalamic nucleus",//
					"Anterior hypothalamic nucleus anterior part",//
					"Anterior hypothalamic central part",//
					"Anterior hypothalamic posterior part",//
					"Anterior hypothalamic dorsal part",//
					"Suprachiasmatic nucleus",//
					"Suprachiasmatic nucleus dorsomedial part",//
					"Suprachiasmatic nucleus ventrolateral part",//
					"Subparaventricular zone",//
					"Retrochiasmatic area",//
					"Tuberal area of the hypothalamus",//
					"Ventromedial nucleus of the hypothalamus",//
					"Ventromedial nucleus of the hypothalamus anterior part",//
					"Ventromedial nucleus of the hypothalamus dorsomedial part",//
					"Ventromedial nucleus of the hypothalamus central part",//
					"Ventromedial nucleus of the hypothalamus ventrolateral part",//
					"Ventral premammillary nucleus",//
					"Mammillary body",//
					"Tuberomammillary nucleus",//
					"Tuberomammillary nucleus dorsal part",//
					"Tuberomammillary nucleus ventral part",//
					"Supramammillary nucleus",//
					"Supramammillary nucleus lateral part",//
					"Supramammillary nucleus medial part",//
					"Dorsal premammillary nucleus",//
					"Medial mammillary nucleus",//
					"Medial mammillary nucleus median part",//
					"Lateral mammillary nucleus",//
					"Posterior hypothalamic nucleus",//
					"Lateral zone of the hypothalamus",//
					"Lateral preoptic area",//
					"Lateral hypothalamic area",//
					"Tuberal nucleus",//
					"Subthalamic nucleus",//
					"Ventral posterolateral nucleus of the thalamus",//
					"Ventral posterolateral nucleus of the thalamus parvicellular part",//
					"Ventral posteromedial nucleus of the thalamus",//
					"Ventral posteromedial nucleus of the thalamus parvicellular part",//
					"Geniculate group of the dorsal thalamus",//
					"Medial geniculate complex",//
					"Medial geniculate complex dorsal part",//
					"Medial geniculate complex ventral part",//
					"Medial geniculate complex medial part",//
					"Dorsal part of the lateral geniculate complex",//
					"Subfornical organ",//
					"Medial habenula",//
					"Medial habenula dorsal part",//
					"Medial habenula ventral part",//
					"Lateral habenula",//
					"Ventral thalamus",//
					"Reticular nucleus of the thalamus",//
					"Geniculate group of the ventral thalamus",//
					"Intergeniculate leaflet of the lateral geniculate complex",//
					"Ventral part of the lateral geniculate complex",//
					"Ventral part of the lateral geniculate complex lateral zone",//
					"Ventral part of the lateral geniculate complex medial zone",//
					"Zona incerta",//
					"Zona incerta dopaminergic group",//
					"Fields of Forel",//
					"Zona incerta proper",//
					"Peripeduncular nucleus",//
					"Subparafascicular nucleus",//
					"Subparafascicular nucleus magnocellular part",//
					"Subparafascicular nucleus parvicellular part",//
					"Fundus of the striatum",//
					"Olfactory tubercle",//
					"Olfactory tubercle molecular layer",//
					"Olfactory tubercle pyramidal layer",//
					"Olfactory tubercle polymorph layer",//
					"Islands of Calleja",//
					"Major island of Calleja",//
					"Striatum medial (septal) region",//
					"Lateral septal complex",//
					"Lateral septal nucleus",//
					"Lateral septal nucleus caudal (caudodorsal) part",//
					"Lateral septal nucleus caudal (caudodorsal) part dorsal zone",//
					"Lateral septal nucleus caudal (caudodorsal) part dorsal zone rostral region",//
					"Lateral septal nucleus caudal (caudodorsal) part dorsal zone dorsal region",//
					"Lateral septal nucleus caudal (caudodorsal) part dorsal zone lateral region",//
					"Lateral septal nucleus caudal (caudodorsal) part dorsal zone ventral region",//
					"Lateral septal nucleus caudal (caudodorsal) part ventral zone",//
					"Lateral septal nucleus caudal (caudodorsal) part ventral zone medial region",//
					"Lateral septal nucleus caudal (caudodorsal) part ventral zone medial region dorsal domain",//
					"Lateral septal nucleus caudal (caudodorsal) part ventral zone medial region ventral domain",//
					"Lateral septal nucleus caudal (caudodorsal) part ventral zone intermediate region",//
					"Lateral septal nucleus caudal (caudodorsal) part ventral zone lateral region",//
					"Lateral septal nucleus caudal (caudodorsal) part ventral zone lateral region dorsal domain",//
					"Lateral septal nucleus caudal (caudodorsal) part ventral zone lateral region ventral domain",//
					"Lateral septal nucleus rostral (rostroventral) part",//
					"Lateral septal nucleus rostral (rostroventral) part medial zone",//
					"Lateral septal nucleus rostral (rostroventral) part medial zone dorsal region",//
					"Lateral septal nucleus rostral (rostroventral) part medial zone ventral region",//
					"Lateral septal nucleus rostral (rostroventral) part medial zone ventral region rostral domain",//
					"Lateral septal nucleus rostral (rostroventral) part medial zone ventral region caudal domain",//
					"Lateral septal nucleus rostral (rostroventral) part ventrolateral zone",//
					"Lateral septal nucleus rostral (rostroventral) part ventrolateral zone dorsal region",//
					"Lateral septal nucleus rostral (rostroventral) part ventrolateral zone dorsal region medial domain",//
					"Lateral septal nucleus rostral (rostroventral) part ventrolateral zone dorsal region lateral domain",//
					"Lateral septal nucleus rostral (rostroventral) part ventrolateral zone ventral region",//
					"Lateral septal nucleus rostral (rostroventral) part dorsolateral zone",//
					"Lateral septal nucleus rostral (rostroventral) part dorsolateral zone medial region",//
					"Lateral septal nucleus rostral (rostroventral) part dorsolateral zone medial region dorsal domain",//
					"Lateral septal nucleus rostral (rostroventral) part dorsolateral zone medial region ventral domain",//
					"Lateral septal nucleus rostral (rostroventral) part dorsolateral zone lateral region",//
					"Lateral septal nucleus rostral (rostroventral) part dorsolateral zone lateral region dorsal domain",//
					"Lateral septal nucleus rostral (rostroventral) part dorsolateral zone lateral region ventral domain",//
					"Lateral septal nucleus ventral part",//
					"Septofimbrial nucleus",//
					"Septohippocampal nucleus",//
					"Pallidum dorsal region",//
					"Globus pallidus",//
					"Globus pallidus lateral segment",//
					"Globus pallidus medial segment",//
					"Pallidum ventral region",//
					"Substantia innominata",//
					"Magnocellular preoptic nucleus",//
					"Medial septal complex",//
					"Medial septal nucleus",//
					"Nucleus of the diagonal band",//
					"Triangular nucleus of the septum",//
					"Midbrain-Hindbrain",//
					"Midbrain-Hindbrain, Sensory",//
					"Midbrain-Hindbrain, Sensory, Visual",//
					"Superior colliculus",//
					"Superior colliculus zonal layer",//
					"Superior colliculus superficial gray layer",//
					"Superior colliculus optic layer",//
					"Superior colliculus intermediate gray layer",//
					"Superior colliculus intermediate gray layer sublayer a",//
					"Superior colliculus intermediate gray layer sublayer b",//
					"Superior colliculus intermediate gray layer sublayer c",//
					"Superior colliculus intermediate white layer",//
					"Superior colliculus intermediate deep gray layer",//
					"Superior colliculus intermediate deep white layer",//
					"Parageminal nucleus",//
					"Pretectal region",//
					"Olivary pretectal nucleus",//
					"Nucleus of the optic tract",//
					"Posterior pretectal nucleus",//
					"Nucleus of the posterior commissure",//
					"Anterior pretectal nucleus",//
					"Medial pretectal area",//
					"Medial terminal nucleus of the accessory optic tract",//
					"Lateral terminal nucleus of the accessory optic tract",//
					"Dorsal terminal nucleus of the accessory optic tract",//
					"Midbrain-Hindbrain, Sensory, Somatosensory",//
					"Trigeminal ganglion",//
					"Mesencephalic nucleus of the trigeminal",//
					"Principal sensory nucleus of the trigeminal",//
					"Spinal nucleus of the trigeminal",//
					"Spinal nucleus of the trigeminal oral part",//
					"Spinal nucleus of the trigeminal oral part ventrolateral part",//
					"Spinal nucleus of the trigeminal oral part rostral dorsomedial part",//
					"Spinal nucleus of the trigeminal oral part middle dorsomedial part, dorsal zone",//
					"Spinal nucleus of the trigeminal oral part middle dorsomedial part, ventral zone",//
					"Spinal nucleus of the trigeminal oral part caudal dorsomedial part",//
					"Spinal nucleus of the trigeminal interpolar part",//
					"Spinal nucleus of the trigeminal caudal part",//
					"Paratrigeminal nucleus",//
					"Dorsal column nuclei",//
					"Gracile nucleus",//
					"Gracile nucleus median part",//
					"Gracile nucleus proper",//
					"Cuneate nucleus",//
					"Nucleus z",//
					"External cuneate nucleus",//
					"Midbrain-Hindbrain, Sensory, Auditory",//
					"Cochlear nuclei",//
					"Dorsal coclear nucleus",//
					"Ventral coclear nucleus",//
					"Ventral coclear nucleus anterior part",//
					"Ventral coclear nucleus posterior part",//
					"Subpeduncular granular region of the cochlear nuclei",//
					"Granular lamina of the cochlear nuclei",//
					"Interstitial nucleus of the auditory nerve",//
					"Nucleus of the trapezoid body",//
					"Superior olivary complex",//
					"Superior olivary complex medial part",//
					"Superior olivary complex lateral part",//
					"Superior olivary complex periolivary region",//
					"Nucleus of the lateral lemniscus",//
					"Inferior colliculus",//
					"Inferior colliculus external nucleus",//
					"Inferior colliculus dorsal nucleus",//
					"Inferior colliculus central nucleus",//
					"Nucleus of the brachium of the inferior colliculus",//
					"Nucleus sagulum",//
					"Midbrain-Hindbrain, Sensory, Vestibular",//
					"Vestibular nuclei",//
					"Medial vestibular nucleus",//
					"Lateral vestibular nucleus",//
					"Superior vestibular nucleus",//
					"Spinal vestibular nucleus",//
					"Perihypoglossal nuclei",//
					"Nucleus intercalatus",//
					"Nucleus prepositus",//
					"Nucleus of Roller",//
					"Interstitial nucleus of the vestibular nerve",//
					"Nucleus x",//
					"Nucleus y",//
					"Infracerebellar nucleus",//
					"Midbrain-Hindbrain, Sensory, Gustatory-Visceral",//
					"Nucleus of the solitary tract",//
					"Nucleus of the solitary tract central part",//
					"Nucleus of the solitary tract commissural part",//
					"Nucleus of the solitary tract gelatinous part",//
					"Nucleus of the solitary tract lateral part",//
					"Nucleus of the solitary tract medial part",//
					"Nucleus of the solitary tract medial part rostral zone",//
					"Nucleus of the solitary tract medial part caudal zone",//
					"Area postrema",//
					"Parabrachial nucleus",//
					"Parabrachial nucleus lateral division",//
					"Parabrachial nucleus lateral division central lateral part",//
					"Parabrachial nucleus lateral division dorsal lateral part",//
					"Parabrachial nucleus lateral division external lateral part",//
					"Parabrachial nucleus lateral division extreme lateral part",//
					"Parabrachial nucleus lateral division internal lateral part",//
					"Parabrachial nucleus lateral division superior lateral part",//
					"Parabrachial nucleus lateral division ventral lateral part",//
					"Kolliker-Fuse subnucleus",//
					"Parabrachial nucleus medial division",//
					"Parabrachial nucleus medial division medial medial part",//
					"Parabrachial nucleus medial division external medial part",//
					"Parabrachial nucleus medial division ventral medial part",//
					"Midbrain-Hindbrain, Motor",//
					"Midbrain-Hindbrain, Motor, Eye",//
					"Oculomotor nucleus",//
					"Medial accessory nucleus",//
					"Oculomotor nucleus proper",//
					"Trochlear nucleus",//
					"Abducens nucleus",//
					"Accesory abducens nucleus",//
					"Abducens nucleus proper",//
					"Midbrain-Hindbrain, Motor, Jaw",//
					"Motor nucleus of the trigeminal",//
					"Motor nucleus of the trigeminal parvicellular part",//
					"Motor nucleus of the trigeminal proper",//
					"Midbrain-Hindbrain, Motor, Extrapyramidal",//
					"Substantia nigra",//
					"Substantia nigra compact part",//
					"Substantia nigra reticular part",//
					"Ventral tegmental area",//
					"Reticular core",//
					"Central gray of the brain",//
					"Periaqueductal gray",//
					"Precommissural nucleus",//
					"Commissural nucleus",//
					"Periaqueductal gray rostromedial division",//
					"Periaqueductal gray rostrolateral division",//
					"Periaqueductal gray medial division",//
					"Periaqueductal gray dorsal division",//
					"Periaqueductal gray ventrolateral division",//
					"Periaqueductal gray dorsolateral division",//
					"Interstitial nucleus of Cajal",//
					"Nucleus of Darkschewitsch",//
					"Dorsal tegmental nucleus",//
					"Ventral tegmental nucleus",//
					"Anterior tegmental nucleus",//
					"Lateral tegmental nucleus",//
					"Laterodorsal tegmental nucleus",//
					"Sublaterodorsal nucleus",//
					"Locus coeruleus",//
					"Subcoeruleus nucleus",//
					"Barrington nucleus",//
					"Supargenual nucleus",//
					"Pontine central gray",//
					"Raphe of mesenchephalon",//
					"Interfascicular nucleus raphe",//
					"Rostral linear nucleus raphe",//
					"Central linear nucleus raphe",//
					"Superior central nucleus raphe",//
					"Superior central nucleus raphe medial part",//
					"Superior central nucleus raphe lateral part",//
					"Dorsal nucleus raphe",//
					"Nucleus incertus",//
					"Nucleus incertus compact part",//
					"Nucleus incertus diffuse part",//
					"Nucleus raphe pontis",//
					"Nucleus raphe magnus",//
					"Nucleus raphe pallidus",//
					"Nucleus raphe obscurus",//
					"Interpeduncular nucleus",//
					"Interpeduncular nucleus rostral subnucleus",//
					"Interpeduncular nucleus apical subnucleus",//
					"Interpeduncular nucleus dorsomedial subnucleus",//
					"Interpeduncular nucleus lateral subnucleus",//
					"Interpeduncular nucleus lateral subnucleus dorsal part",//
					"Interpeduncular nucleus lateral subnucleus intermediate part",//
					"Interpeduncular nucleus lateral subnucleus ventral part",//
					"Interpeduncular nucleus lateral subnucleus rostral part",//
					"Interpeduncular nucleus intermediate subnucleus",//
					"Interpeduncular nucleus central subnucleus",//
					"Reticular formation",//
					"Mesencephalic reticular nucleus",//
					"Retrorubral area",//
					"Pedunculopontine nucleus",//
					"Cuneiform nucleus",//
					"Pontine reticular nucleus",//
					"Pontine reticular nucleus caudal part",//
					"Pontine reticular nucleus rostral part",//
					"Gigantocellular reticular nucleus",//
					"Paragigantocellular reticular nucleus",//
					"Paragigantocellular reticular nucleus dorsal part",//
					"Paragigantocellular reticular nucleus lateral part",//
					"Parapyramidal nucleus",//
					"Parapyramidal nucleus deep part",//
					"Parapyramidal nucleus superficial part",//
					"Magnocellular reticular nucleus",//
					"Supratrigeminal nucleus",//
					"Medullary reticular nucleus",//
					"Medullary reticular nucleus dorsal part",//
					"Medullary reticular nucleus ventral part",//
					"Dorsal horn of the spinal cord",//
					"Marginal zone of the spinal cord",//
					"Substantia gelatinosa of the spinal cord",//
					"Nucleus proprius of the spinal cord",//
					"Reticular nucleus of the spinal cord",//
					"Basal nucleus of the dorsal horn",//
					"Lateral cervical nucleus",//
					"Lateral spinal nucleus",//
					"Intermediate Gray of the Spinal Cord",//
					"Central cervical nucleus",//
					"Dorsal nucleus of the spinal cord",//
					"Dorsal nucleus of the spinal cord caudal part",//
					"Intermediomedial column of the spinal cord",//
					"Intermediolateral column of the spinal cord",//
					"Dorsal commissural nucleus",//
					"Intercalated nucleus of the spinal cord",//
					"Intermediolateral column of the spinal cord sympathetic column",//
					"Intermediolateral column of the spinal cord parasympathetic column",//
					"Central gray of the spinal cord",//
					"Ventral horn of the spinal cord",//
					"Nucleus of the bulbocavernosus",//
					"Onuf nucleus",//
					"Phrenic nucleus",//
					"Somatic motor areas",//
					"Primary motor area",//
					"Secondary motor area",//
					"Auditory areas",//
					"Primary auditory area",//
					"Dorsal auditory areas",//
					"Ventral auditory areas",//
					"Gustatory areas",//
					"Anterior olfactory nucleus",//
					"Anterior olfactory nucleus dorsal part",//
					"Anterior olfactory nucleus dorsal part molecular layer",//
					"Anterior olfactory nucleus dorsal part pyramidal layer",//
					"Anterior olfactory nucleus external part",//
					"Anterior olfactory nucleus external part molecular layer",//
					"Anterior olfactory nucleus external part pyramidal layer",//
					"Anterior olfactory nucleus lateral part",//
					"Anterior olfactory nucleus lateral part molecular layer",//
					"Anterior olfactory nucleus lateral part pyramidal layer",//
					"Anterior olfactory nucleus medial part",//
					"Anterior olfactory nucleus medial part molecular layer",//
					"Anterior olfactory nucleus medial part pyramidal layer",//
					"Anterior olfactory nucleus posteroventral part",//
					"Anterior olfactory nucleus posteroventral part molecular layer",//
					"Anterior olfactory nucleus posteroventral part pyramidal layer",//
					"Taenia tecta",//
					"Taenia tecta dorsal part",//
					"Taenia tecta dorsal part layer 1",//
					"Taenia tecta dorsal part layer 2",//
					"Taenia tecta dorsal part layer 3",//
					"Taenia tecta dorsal part layer 4",//
					"Taenia tecta ventral part",//
					"Taenia tecta ventral part layer1",//
					"Taenia tecta ventral part layer2",//
					"Taenia tecta ventral part layer3",//
					"Piriform area",//
					"Piriform area molecular layer",//
					"Piriform area pyramidal layer",//
					"Piriform area polymorph layer",//
					"Postpiriform transition area",//
					"Piriform-amygdaloid area",//
					"Nucleus of the lateral olfactory tract",//
					"Nucleus of the lateral olfactory tract molecular layer",//
					"Nucleus of the lateral olfactory tract pyramidal layer",//
					"Nucleus of the lateral olfactory tract dorsal cap",//
					"Cortical nucleus of the amygdala",//
					"Cortical nucleus of the amygdala anterior part",//
					"Cortical nucleus of the amygdala posterior part",//
					"Cortical nucleus of the amygdala posterior part lateral zone",//
					"Cortical nucleus of the amygdala posterior part medial zone",//
					"Somatosensory areas",//
					"Primary somatosensory area",//
					"Primary somatosensory area barrel field",//
					"Primary somatosensory area lower limb",//
					"Primary somatosensory area mouth",//
					"Primary somatosensory area nose",//
					"Primary somatosensory area trunk",//
					"Primary somatosensory area upper limb",//
					"Supplemental somatosensory area",//
					"Visceral area",//
					"Visual areas",//
					"Anterior laterolateral visual area",//
					"Anterolateral visual area",//
					"Anteromedial visual area",//
					"Intermediolateral visual area",//
					"Laterolateral visual area",//
					"Mediolateral visual area",//
					"Posterolateral visual area",//
					"Primary visual area",//
					"Rostrolateral visual area",//
					"Agranular insular area",//
					"Agranular insular area dorsal part",//
					"Agranular insular area ventral part",//
					"Agranular insular area posterior part",//
					"Anterior cingulate area",//
					"Anterior cingulate area dorsal part",//
					"Anterior cingulate area ventral part",//
					"Ectorhinal area",//
					"Parasubiculum",//
					"Parasubiculum layer 1",//
					"Parasubiculum layer 2",//
					"Parasubiculum layer 3",//
					"Parasubiculum layer 4",//
					"Parasubiculum layer 5",//
					"Parasubiculum layer 6",//
					"Subiculum",//
					"Subiculum dorsal part",//
					"Subiculum dorsal part molecular layer",//
					"Subiculum dorsal part stratum radiatum",//
					"Subiculum dorsal part pyramidal layer",//
					"Subiculum ventral part",//
					"Subiculum ventral part molecular layer",//
					"Subiculum ventral part stratum radiatum",//
					"Subiculum ventral part pyramidal layer",//
					"Hippocampal region",//
					"Ammon Horn",//
					"Field CA1",//
					"Field CA1 stratum lacunosum-moleculare",//
					"Field CA1 stratum radiatum",//
					"Field CA1 pyramidal layer",//
					"Field CA1 pyramidal layer, deep",//
					"Field CA1 pyramidal layer, superficial",//
					"Field CA1 stratum oriens",//
					"Field CA2",//
					"Field CA2 stratum lacunosum-moleculare",//
					"Field CA2 stratum radiatum",//
					"Field CA2 pyramidal layer",//
					"Field CA2 stratum oriens",//
					"Field CA3",//
					"Field CA3 stratum lacunosum-moleculare",//
					"Field CA3 stratum lucidum",//
					"Field CA3 stratum oriens",//
					"Field CA3 pyramidal layer",//
					"Dentate gyrus",//
					"Dentate gyrus crest",//
					"Dentate gyrus crest molecular layer",//
					"Dentate gyrus crest granule cell  layer",//
					"Dentate gyrus crest polymorph  layer",//
					"Dentate gyrus lateral blade",//
					"Dentate gyrus lateral blade molecular layer",//
					"Dentate gyrus lateral blade granule cell layer",//
					"Dentate gyrus lateral blade polymorph layer",//
					"Dentate gyrus medial blade",//
					"Dentate gyrus medial blade molecular layer",//
					"Dentate gyrus medial blade granule cell layer",//
					"Dentate gyrus medial blade polymorph layer",//
					"Induseum griseum",//
					"Fasciola cinerea",//
					"Infralimbic area",//
					"Orbital area",//
					"Orbital area lateral part",//
					"Orbital area medial part",//
					"Orbital area ventral part",//
					"Orbital area ventrolateral part",//
					"Perirhinal area",//
					"Posterior parietal association areas",//
					"Prelimbic area",//
					"Retrosplenial area",//
					"Retrosplenial area dorsal part",//
					"Retrosplenial area lateral agranular part",//
					"Retrosplenial area ventral part",//
					"Retrosplenial area ventral part zone a",//
					"Retrosplenial area ventral part zone b/c",//
					"Ventral temporal association areas",//
					"Layer 6b(layer 7, subplate, deep cortex, claustral complex)",//
					"Layer 6b, isocortex",//
					"Claustrum",//
					"Endopiriform nucleus",//
					"Endopiriform nucleus dorsal part",//
					"Endopiriform nucleus ventral part",//
					"Lateral nucleus of the amygdala",//
					"Basolateral nucleus of the amygdala",//
					"Basolateral nucleus of the amygdala anterior part",//
					"Basolateral nucleus of the amygdala posterior part",//
					"Basomedial nucleus of the amygdala",//
					"Basomedial nucleus of the amygdala anterior part",//
					"Basomedial nucleus of the amygdala posterior part",//
					"Posterior nucleus of the amygdala",//
					"Cerebellar cortex",//
					"Vermal regions",//
					"Lingula (l)",//
					"Central lobule",//
					"Central lobule, lobule II",//
					"Central lobule, lobule II sublobule a",//
					"Central lobule, lobule II sublobule b",//
					"Central lobule, lobule III",//
					"Central lobule, lobule III sublobule a",//
					"Central lobule, lobule III sublobule b",//
					"Culmen",//
					"Culmen lobule IV",//
					"Culmen lobule V",//
					"Declive (VI)",//
					"Declive, sublobule a",//
					"Declive, sublobule b",//
					"Declive, sublobule c",//
					"Declive, sublobule d",//
					"Folium-tuber vermis (VII)",//
					"Pyramus",//
					"Pyramus sublobule a",//
					"Pyramus sublobule b",//
					"Uvula (IX)",//
					"Uvula (IX) sublobule ab",//
					"Uvula (IX) sublobule c",//
					"Nodulus (X)",//
					"Nodulus sublobule a",//
					"Nodulus sublobule b",//
					"Hemispheric regions",//
					"Simple lobule",//
					"Simple lobule sublobule a",//
					"Simple lobule sublobule b",//
					"Ansiform lobule",//
					"Ansiform lobule crus 1",//
					"Ansiform lobule crus 1 sublobule a",//
					"Ansiform lobule crus 1 sublobule b",//
					"Ansiform lobule crus 1 sublobule c",//
					"Ansiform lobule crus 1 sublobule d",//
					"Ansiform lobule crus 2",//
					"Ansiform lobule crus 2 sublobule a",//
					"Ansiform lobule crus 2 sublobule b",//
					"Paramedian lobule",//
					"Copula pyramidis",//
					"Copula pyramidis sublobule a",//
					"Copula pyramidis sublobule b",//
					"Paraflocculus",//
					"Flocculus",//
					"Deep cerebellar nuclei",//
					"Fastigial nucleus",//
					"Interposed nucleus",//
					"Interposed nucleus parvicellular part",//
					"Interposed nucleus proper",//
					"Dentate nucleus",//
					"Dentate nucleus parvicellular part",//
					"Dentate nucleus proper",//
					"Retina, outer nuclear layer",//
					"Retina, outer plexiform layer",//
					"Retina, inner nuclear layer",//
					"Retina, inner plexiform layer",//
					"Retina, ganglion cell layer",//
					"Pre-Post Cerebellar nuclei",//
					"Pontine gray",//
					"Tegmental reticular nucleus",//
					"Pontine gray proper",//
					"Inferior olivary complex",//
					"Dorsal accessory olive",//
					"Medial accessory olive",//
					"Principal olive",//
					"Lateral reticular nucleus",//
					"Lateral reticular nucleus magnocellular part",//
					"Lateral reticular nucleus parvicellular part",//
					"Linear nucleus of the medulla",//
					"Paramedian reticular nucleus",//
					"Parasolitary nucleus",//
					"Red nucleus",//
					"Midbrain-Hindbrain, Motor, Face",//
					"Facial nucleus",//
					"Accessory facial nucleus",//
					"Facial nucleus_proper",//
					"Midbrain-Hindbrain, Motor, Labyrinth",//
					"Efferent cochlear group",//
					"Efferent vesstibular nucleus",//
					"Midbrain-Hindbrain, Motor, Pharynx-Larynx-Esophagus",//
					"Nucleus ambiguus dorsal division",//
					"Nucleus ambiguus dorsal division, stylopharyngeal motoneurons",//
					"Nucleus ambiguus dorsal division proper",//
					"Midbrain-Hindbrain, Motor, Neck",//
					"Nucleus of the spinal accessory nerve",//
					"Midbrain-Hindbrain, Motor, Tongue",//
					"Hypoglossal nucleus",//
					"Midbrain-Hindbrain, Motor, Viscera",//
					"Edinger-Westphal nucleus",//
					"Superior salivatory nucleus",//
					"Inferior salivatory nucleus",//
					"Dorsal motor nucleus of the vagus nerve",//
					"Nucleus ambiguus, ventral division",//
					"Cranial and Spinal Nerves (and Related)",//
					"Terminal nerve",//
					"Olfactory nerve",//
					"Vomeronasal nerve",//
					"Lateral olfactory tract",//
					"Lateral olfactory tract dorsal limb",//
					"Anterior commissure, olfactory limb",//
					"Optic nerve",//
					"Accessory optic tract",//
					"Brachium of the superior colliculus",//
					"Commissure of the superior colliculus",//
					"Optic chiasm",//
					"Optic tract",//
					"Techtothalamic pathway",//
					"Oculomotor nerve",//
					"Medial longitudinal fascicle",//
					"Posterior commissure",//
					"Trochlear nerve",//
					"Decussation of the trochlear nerve",//
					"Trochlear nerve proper",//
					"Abducens nerve",//
					"Trigeminal nerve",//
					"Motor root of the trigeminal nerve",//
					"Sensory root of the trigeminal nerve",//
					"Mesencephalic tract of the trigeminal nerve",//
					"Spinal tract of the trigeminal nerve",//
					"Facial nerve",//
					"Intermediate nerve",//
					"Genu of the facial nerve",//
					"Vestibulocochlear nerve",//
					"Efferent cochleovestibular bundle",//
					"Vestibular nerve",//
					"Cochlear nerve",//
					"Trapezoid body",//
					"Intermediate acoustic stria",//
					"Dorsal acoustic stria",//
					"Lateral lemniscus",//
					"Commissure of the inferior colliculus",//
					"Brachium of the inferior colliculus",//
					"Glossopharyngeal nerve",//
					"Vagus nerve",//
					"Solitary tract",//
					"Accessory spinal nerve",//
					"Hypoglossal nerve",//
					"Ventral roots",//
					"Dorsal roots",//
					"Cervicothalamic tract",//
					"Dorsolateral fascicle",//
					"Ventral commissure of the spinal cord",//
					"Dorsal columns",//
					"Cuneate fascicle",//
					"Gracile fascicle",//
					"Internal arcuate fibers",//
					"Medial lemniscus",//
					"Spinothalamic tract",//
					"Lateral spinothalamic tract",//
					"Ventral spinothalamic tract",//
					"Spinocervical tract",//
					"Spino-olivary pathway",//
					"Spinoreticular pathway",//
					"Spinovestibular pathway",//
					"Spinotectal pathway",//
					"Spinohypothalamic pathway",//
					"Spinotelencephalic pathway",//
					"Hypothalamohypophysial tract",//
					"Cerebellar commissure",//
					"Cerebellar peduncles",//
					"Superior cerebellar peduncle",//
					"Descussation of the scp",//
					"Uncinate fascicle",//
					"Ventral spinocerebellar tract",//
					"Middle cerebellar peduncle",//
					"Inferior cerebellar peduncle",//
					"Dorsal spinocerebellar tract",//
					"Cuneocerebellar tract",//
					"Juxtarestiform body",//
					"Bulbocerebellar tract",//
					"Olivocerebellar tract",//
					"Reticulocerebellar tract",//
					"Trigeminocerebellar tract",//
					"Arbor vitae",//
					"Extrapyramidal fiber systems",//
					"Extrapyramidal fiber systems, basal-nuclei related",//
					"Pallidothalamic pathway",//
					"Nigrostriatal tract",//
					"Nigrothalamic fibers",//
					"Pallidotegmental fascicle",//
					"Striatonigral pathway",//
					"Subthalamic fascicle",//
					"Tectospinal pathway",//
					"Direct tectospinal pathway",//
					"Dorsal tegmental decussation",//
					"Crossed tectospinal pathway",//
					"Rubrospinal tract",//
					"Ventral tegmental decussation",//
					"Rubroreticular tract",//
					"Central tegmental bundle",//
					"Reticulospinal tract",//
					"Reticulospinal tract, lateral part",//
					"Reticulospinal tract, medial part",//
					"Vestibulospinal pathway",//
					"Cingulum bundle",//
					"Medial forebrain bundle",//
					"Supraoptic commissures",//
					"Supraoptic commissures, anterior",//
					"Supraoptic commissures, dorsal",//
					"Supraoptic commissures, ventral",//
					"Supramammillary decussation",//
					"Periventricular bundle of the hypothalamus",//
					"Principal mammillary tract",//
					"Mammillothalamic tract",//
					"Mammillotegmental tract",//
					"Mammillary peduncle",//
					"Periventricular bundle of the thalamus",//
					"Stria medularis",//
					"Fasciculus retroflexus",//
					"Habenular commisure",//
					"Dorsal longitudinal fascicle",//
					"Dorsal tegmental tract",//
					"Dorsal commissure of the spinal cord",//
					"Lateral hypothalamic area proper",//
					"Bed nuclei of the stria terminalis, anterior division, subcommisural zone",//
					"Medial preoptic area proper",//
					"Anterior hypothalamic area proper",//
					"Supraoptic nucleus proper",//
					"Nucleus reuniens caudal division posterior part",//
					"Parvicellular reticular nucleus",//
					"Mesencephalic reticular nucleus proper",//
					"Locus coeruleus proper",//
					"Stria terminalis",//
					"Bed nuclei of the stria terminalis anterior division anterodorsal area proper",//
					"Bed nuclei of the stria terminalis anterior division anterodorsal area central core",//
					"Laterodorsal tegmental nucleus proper",//
					"Field CA3 stratum radiatum",//
					"Pituitary gland, neural lobe",//
					"Pituitary gland",//
					"Median eminence",//
					"Median eminence external lamina",//
					"Median eminence internal lamina",//
					"Infundibulum",//
					"Infundibulum external lamina",//
					"Infundibulum internal lamina",//
					"Pituitary gland, anterior lobe",//
					"Pituitary gland, intermediate lobe",//
					"Central canal",//
					"Nucleus reuniens rostral division anterior part");
}
