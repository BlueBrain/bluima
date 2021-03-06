package ch.epfl.bbp.uima.word2vec;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import org.junit.Ignore;
import org.junit.Test;

import ch.epfl.bbp.uima.word2vec.Word2Vec.WordEntry;

@Ignore
public class Word2VecUtilsTest {

    @Test
    public void test() {
        assertNotNull(Word2VecUtils.getWordVector("brain"));
        assertNotNull(Word2VecUtils.getWordVector("thalamic"));

    }

    @Test
    @Ignore
    public void testClasses() throws Exception {

        final String MODEL_FILE = "/Volumes/HDD2/ren_data/dev_hdd/bluebrain/9_lda/50_word2vec/word2vec_trunk/1m_ns.w2v.100classes.bin";
        HashMap<String, Integer> w2c = Word2Vec.loadClassModel(MODEL_FILE);
        assertTrue(w2c.size() > 1000);

        assertTrue(w2c.containsKey("brain"));

        String[] proteins = { "acetylcholinesterase", "acrosin",
                "adenosylhomocysteinase", "adipogenin", "adiponectin",
                "adipophilin", "ADM", "ADM2", "adropin", "adseverin",
                "advillin", "afadin", "afamin", "aftiphilin", "agmatinase",
                "agrin", "aladin", "allantoinase", "alsin", "ameloblastin",
                "amelotin", "amidophosphoribosyltransferase",
                "aminomethyltransferase", "amphiphysin", "amphiregulin",
                "anamorsin", "angiogenin", "angiomotin", "ankycorbin",
                "antileukoproteinase", "apelin", "apolipoprotein(a)",
                "aprataxin", "artemin", "arylsulfatase", "aspartoacylase",
                "atherin", "attractin", "aureolysin", "axin", "azurocidin",
                "bacterioferritin", "barttin", "basigin", "battenin", "BET1",
                "biotinidase", "borealin", "brorin", "BUD13", "butyrophilin",
                "bystin", "cadherin", "calbindin", "calcitonin", "calcyphosin",
                "caldesmon", "calicin", "calmegin", "calmin", "calmodulin",
                "calnexin", "calpastatin", "calreticulin", "calretinin",
                "calsenilin", "calumenin", "capucin", "carabin",
                "carboxymethylenebutenolidase", "catalase", "caytaxin",
                "CD247", "centlein", "centriolin", "centrobin", "cerberus",
                "ceruloplasmin", "cholecystokinin", "cholinesterase",
                "chondroadherin", "chondrolectin", "chordin", "chymase",
                "cingulin", "cirhin", "claspin", "clusterin", "coagulase",
                "cochlin", "coilin", "colipase", "collectrin", "copine",
                "corneodesmosin", "cornifelin", "cornulin", "corticoliberin",
                "cortistatin", "cubilin", "cullin", "cystinosin", "cytoglobin",
                "delphilin", "dematin", "dendrin", "dermatopontin",
                "dermcidin", "dermokine", "desmin", "desmoplakin", "destrin",
                "DET1", "dihydroorotase", "dihydropyrimidinase", "dixin",
                "DPH3", "drebrin", "dymeclin", "dysbindin", "dysferlin",
                "dystroglycan", "dystrophin", "dystrotelin", "ecotin",
                "elafin", "elastin", "embigin", "emerin", "enamelin",
                "endoglin", "endoglucanase", "endomucin", "endosialin",
                "enolase", "ensconsin", "enteropeptidase", "envoplakin",
                "ephrin", "epigen", "epiphycan", "epiplakin", "eppin", "ermin",
                "erythropoietin", "ESF1", "espin", "exopolyphosphatase",
                "fascin", "ferrochelatase", "fibrocystin", "fibroleukin",
                "fibromodulin", "fibronectin", "fidgetin", "filaggrin",
                "filensin", "flagellin", "flavohemoprotein", "folliculin",
                "follistatin", "fractalkine", "fructokinase", "fukutin",
                "fumarylacetoacetase", "galactocerebrosidase", "galactokinase",
                "galanin", "gametogenetin", "gastricsin", "gastrin",
                "gelsolin", "geminin", "gephyrin", "geranyltranstransferase",
                "gigaxonin", "girdin", "gliomedin", "glomulin", "glucagon",
                "glucokinase", "glucosylceramidase", "glycodelin",
                "glycophorin", "goliath", "grancalcin", "granulins",
                "granulysin", "grifin", "guanylin", "hamartin", "haptoglobin",
                "harmonin", "hemogen", "hemojuvelin", "hemopexin",
                "heparanase", "hepcidin", "hephaestin", "hornerin", "humanin",
                "huntingtin", "hyccin", "ichthyin", "insulin", "inversin",
                "involucrin", "iporin", "isochorismatase", "IST1", "jouberin",
                "kalirin", "kanadaptin", "kaptin", "kazrin", "keratocan",
                "ketohexokinase", "kinectin", "kinocilin", "klotho",
                "kynureninase", "lactadherin", "lactoperoxidase",
                "lactotransferrin", "laforin", "latexin", "latherin",
                "layilin", "lebercilin", "legumain", "lengsin", "leptin",
                "leukosialin", "leupaxin", "ligatin", "limbin", "loricrin",
                "lumican", "macoilin", "malcavernin", "malectin", "maltoporin",
                "maspardin", "matrilysin", "meckelin", "melanophilin",
                "melanoregulin", "melanotransferrin", "membralin", "menin",
                "mesothelin", "meteorin", "microcephalin", "midasin",
                "midkine", "midnolin", "mimecan", "musculin", "muskelin",
                "myeloblastin", "myeloperoxidase", "myocardin", "myocilin",
                "myoferlin", "myogenin", "myoglobin", "myomegalin",
                "myoneurin", "myopalladin", "myotilin", "myotrophin",
                "myotubularin", "nardilysin", "nebulette", "nebulin", "necdin",
                "NEDD8", "neogenin", "nephrin", "nephronectin", "neprilysin",
                "nestin", "neudesin", "neugrin", "neuritin", "neurobeachin",
                "neurochondrin", "neurofibromin", "neuroglobin", "neurogranin",
                "neuroguidin", "neuromodulin", "neuronatin", "neuroplastin",
                "neurotrimin", "neurotrophin", "neurotrypsin", "neurturin",
                "nexilin", "nibrin", "nicalin", "nicastrin", "ninein",
                "nischarin", "nociceptin", "nocturnin", "noelin", "noggin",
                "norrin", "nostrin", "nucleolin", "nucleophosmin",
                "nucleoredoxin", "nurim", "nyctalopin", "obscurin", "occludin",
                "octanoyltransferase", "oculomedin", "oligoribonuclease",
                "opalin", "opticin", "optineurin", "orexin", "osteocrin",
                "osteomodulin", "osteopontin", "otoancorin", "otoferlin",
                "otogelin", "otoraplin", "otospiralin", "ovostatin", "paladin",
                "palladin", "pallidin", "palmdelphin", "pantetheinase",
                "papilin", "parafibromin", "paralemmin", "paraplegin",
                "parathymosin", "paxillin", "peflin", "pejvakin", "pendrin",
                "peregrin", "periaxin", "pericentrin", "perilipin",
                "periostin", "peripherin", "periplakin", "peroxidasin",
                "persephin", "pescadillo", "phakinin", "phosducin",
                "phosphoglucomutase", "phospholemman", "phosphomannomutase",
                "phosphopentomutase", "pikachurin", "pilin", "pinin", "pirin",
                "plasminogen", "plasmolipin", "pleckstrin", "pleiotrophin",
                "podocan", "podocin", "podoplanin", "porimin", "prestin",
                "probasin", "probetacellulin", "proepiregulin", "prohibitin",
                "prolactin", "prolargin", "promethin", "prominin",
                "promotilin", "properdin", "ProSAAS", "prostasin",
                "prothrombin", "prothyroliberin", "protogenin",
                "pyrazinamidase/nicotinamidase", "pyrin", "raftlin",
                "recoverin", "reelin", "regucalcin", "renin", "repetin",
                "resistin", "retbindin", "retinoschisin", "rhamnulokinase",
                "rhotekin", "ribokinase", "ribulokinase", "rootletin",
                "roquin", "rotatin", "sacsin", "saitohin", "sarcalumenin",
                "sarcolipin", "sarcospan", "sciellin", "sclerostin",
                "secretagogin", "secretin", "securin", "sedoheptulokinase",
                "seipin", "semaphorin", "separin", "seprase", "serglycin",
                "serotransferrin", "serpin", "sharpin", "sialin",
                "sialoadhesin", "smoothelin", "smoothened", "somatoliberin",
                "somatostatin", "somatotropin", "sorcin", "sororin",
                "sortilin", "SPARC", "spartin", "spastin", "spatacsin",
                "speriolin", "stannin", "staphylokinase", "statherin",
                "stathmin", "stereocilin", "striatin", "supervillin",
                "suprabasin", "symplekin", "synaptophysin", "synaptopodin",
                "synaptoporin", "syncoilin", "syncollin", "synemin",
                "syntabulin", "syntaphilin", "tafazzin", "tapasin",
                "telethonin", "tenascin", "tenomodulin", "tescalcin", "tesmin",
                "testin", "testisin", "tetranectin", "tetraspanin",
                "thioredoxin", "thrombopoietin", "thyroglobulin", "titin",
                "transaldolase", "transgelin", "transketolase", "translin",
                "transthyretin", "trehalase", "triadin", "trichohyalin",
                "tristetraprolin", "trophinin", "trypsin", "tryptase",
                "tryptophanase", "tsukushin", "tuberin", "tuftelin",
                "tyrosinase", "uricase", "urocortin", "uromodulin", "usherin",
                "uteroglobin", "utrophin", "vasculin", "vasorin", "vezatin",
                "vigilin", "vimentin", "vinculin", "vinexin", "vitrin",
                "vitronectin", "whirlin", "wolframin", "zonadhesin", "zyxin" };
        String[] morphologies = { "amacrine", "basket", "bitufted", "bouquet",
                "bipolar", "candelabrum", "chandelier", "cartwheel", "cone",
                "fusiform", "Golgi", "granule", "horizontal", "polymorphous",
                "Lugaro", "spiny", "mitral", "multipolar", "neurogliaform",
                "nest", "octopus", "Purkinje", "pyramidal", "Renshaw", "spiny",
                "star", "stellate", "tufted", "untufted", "unipolar" };
        for (String test : proteins) {
            System.out.println(w2c.get(test));
        }

    }

    @Test
    @Ignore
    public void testBrainRegions() throws Exception {

        assertNotNull(Word2VecUtils.getClass("brain"));

        String[] proteins = { "acetylcholinesterase", "acrosin",
                "adenosylhomocysteinase", "adipogenin", "adiponectin",
                "adipophilin", "ADM", "ADM2", "adropin", "adseverin",
                "advillin", "afadin", "afamin", "aftiphilin", "agmatinase",
                "agrin", "aladin", "allantoinase", "alsin", "ameloblastin",
                "amelotin", "amidophosphoribosyltransferase",
                "aminomethyltransferase", "amphiphysin", "amphiregulin",
                "anamorsin", "angiogenin", "angiomotin", "ankycorbin",
                "antileukoproteinase", "apelin", "apolipoprotein(a)",
                "aprataxin", "artemin", "arylsulfatase", "aspartoacylase",
                "atherin", "attractin", "aureolysin", "axin", "azurocidin",
                "bacterioferritin", "barttin", "basigin", "battenin", "BET1",
                "biotinidase", "borealin", "brorin", "BUD13", "butyrophilin",
                "bystin", "cadherin", "calbindin", "calcitonin", "calcyphosin",
                "caldesmon", "calicin", "calmegin", "calmin", "calmodulin",
                "calnexin", "calpastatin", "calreticulin", "calretinin",
                "calsenilin", "calumenin", "capucin", "carabin",
                "carboxymethylenebutenolidase", "catalase", "caytaxin",
                "CD247", "centlein", "centriolin", "centrobin", "cerberus",
                "ceruloplasmin", "cholecystokinin", "cholinesterase",
                "chondroadherin", "chondrolectin", "chordin", "chymase",
                "cingulin", "cirhin", "claspin", "clusterin", "coagulase",
                "cochlin", "coilin", "colipase", "collectrin", "copine",
                "corneodesmosin", "cornifelin", "cornulin", "corticoliberin",
                "cortistatin", "cubilin", "cullin", "cystinosin", "cytoglobin",
                "delphilin", "dematin", "dendrin", "dermatopontin",
                "dermcidin", "dermokine", "desmin", "desmoplakin", "destrin",
                "DET1", "dihydroorotase", "dihydropyrimidinase", "dixin",
                "DPH3", "drebrin", "dymeclin", "dysbindin", "dysferlin",
                "dystroglycan", "dystrophin", "dystrotelin", "ecotin",
                "elafin", "elastin", "embigin", "emerin", "enamelin",
                "endoglin", "endoglucanase", "endomucin", "endosialin",
                "enolase", "ensconsin", "enteropeptidase", "envoplakin",
                "ephrin", "epigen", "epiphycan", "epiplakin", "eppin", "ermin",
                "erythropoietin", "ESF1", "espin", "exopolyphosphatase",
                "fascin", "ferrochelatase", "fibrocystin", "fibroleukin",
                "fibromodulin", "fibronectin", "fidgetin", "filaggrin",
                "filensin", "flagellin", "flavohemoprotein", "folliculin",
                "follistatin", "fractalkine", "fructokinase", "fukutin",
                "fumarylacetoacetase", "galactocerebrosidase", "galactokinase",
                "galanin", "gametogenetin", "gastricsin", "gastrin",
                "gelsolin", "geminin", "gephyrin", "geranyltranstransferase",
                "gigaxonin", "girdin", "gliomedin", "glomulin", "glucagon",
                "glucokinase", "glucosylceramidase", "glycodelin",
                "glycophorin", "goliath", "grancalcin", "granulins",
                "granulysin", "grifin", "guanylin", "hamartin", "haptoglobin",
                "harmonin", "hemogen", "hemojuvelin", "hemopexin",
                "heparanase", "hepcidin", "hephaestin", "hornerin", "humanin",
                "huntingtin", "hyccin", "ichthyin", "insulin", "inversin",
                "involucrin", "iporin", "isochorismatase", "IST1", "jouberin",
                "kalirin", "kanadaptin", "kaptin", "kazrin", "keratocan",
                "ketohexokinase", "kinectin", "kinocilin", "klotho",
                "kynureninase", "lactadherin", "lactoperoxidase",
                "lactotransferrin", "laforin", "latexin", "latherin",
                "layilin", "lebercilin", "legumain", "lengsin", "leptin",
                "leukosialin", "leupaxin", "ligatin", "limbin", "loricrin",
                "lumican", "macoilin", "malcavernin", "malectin", "maltoporin",
                "maspardin", "matrilysin", "meckelin", "melanophilin",
                "melanoregulin", "melanotransferrin", "membralin", "menin",
                "mesothelin", "meteorin", "microcephalin", "midasin",
                "midkine", "midnolin", "mimecan", "musculin", "muskelin",
                "myeloblastin", "myeloperoxidase", "myocardin", "myocilin",
                "myoferlin", "myogenin", "myoglobin", "myomegalin",
                "myoneurin", "myopalladin", "myotilin", "myotrophin",
                "myotubularin", "nardilysin", "nebulette", "nebulin", "necdin",
                "NEDD8", "neogenin", "nephrin", "nephronectin", "neprilysin",
                "nestin", "neudesin", "neugrin", "neuritin", "neurobeachin",
                "neurochondrin", "neurofibromin", "neuroglobin", "neurogranin",
                "neuroguidin", "neuromodulin", "neuronatin", "neuroplastin",
                "neurotrimin", "neurotrophin", "neurotrypsin", "neurturin",
                "nexilin", "nibrin", "nicalin", "nicastrin", "ninein",
                "nischarin", "nociceptin", "nocturnin", "noelin", "noggin",
                "norrin", "nostrin", "nucleolin", "nucleophosmin",
                "nucleoredoxin", "nurim", "nyctalopin", "obscurin", "occludin",
                "octanoyltransferase", "oculomedin", "oligoribonuclease",
                "opalin", "opticin", "optineurin", "orexin", "osteocrin",
                "osteomodulin", "osteopontin", "otoancorin", "otoferlin",
                "otogelin", "otoraplin", "otospiralin", "ovostatin", "paladin",
                "palladin", "pallidin", "palmdelphin", "pantetheinase",
                "papilin", "parafibromin", "paralemmin", "paraplegin",
                "parathymosin", "paxillin", "peflin", "pejvakin", "pendrin",
                "peregrin", "periaxin", "pericentrin", "perilipin",
                "periostin", "peripherin", "periplakin", "peroxidasin",
                "persephin", "pescadillo", "phakinin", "phosducin",
                "phosphoglucomutase", "phospholemman", "phosphomannomutase",
                "phosphopentomutase", "pikachurin", "pilin", "pinin", "pirin",
                "plasminogen", "plasmolipin", "pleckstrin", "pleiotrophin",
                "podocan", "podocin", "podoplanin", "porimin", "prestin",
                "probasin", "probetacellulin", "proepiregulin", "prohibitin",
                "prolactin", "prolargin", "promethin", "prominin",
                "promotilin", "properdin", "ProSAAS", "prostasin",
                "prothrombin", "prothyroliberin", "protogenin",
                "pyrazinamidase/nicotinamidase", "pyrin", "raftlin",
                "recoverin", "reelin", "regucalcin", "renin", "repetin",
                "resistin", "retbindin", "retinoschisin", "rhamnulokinase",
                "rhotekin", "ribokinase", "ribulokinase", "rootletin",
                "roquin", "rotatin", "sacsin", "saitohin", "sarcalumenin",
                "sarcolipin", "sarcospan", "sciellin", "sclerostin",
                "secretagogin", "secretin", "securin", "sedoheptulokinase",
                "seipin", "semaphorin", "separin", "seprase", "serglycin",
                "serotransferrin", "serpin", "sharpin", "sialin",
                "sialoadhesin", "smoothelin", "smoothened", "somatoliberin",
                "somatostatin", "somatotropin", "sorcin", "sororin",
                "sortilin", "SPARC", "spartin", "spastin", "spatacsin",
                "speriolin", "stannin", "staphylokinase", "statherin",
                "stathmin", "stereocilin", "striatin", "supervillin",
                "suprabasin", "symplekin", "synaptophysin", "synaptopodin",
                "synaptoporin", "syncoilin", "syncollin", "synemin",
                "syntabulin", "syntaphilin", "tafazzin", "tapasin",
                "telethonin", "tenascin", "tenomodulin", "tescalcin", "tesmin",
                "testin", "testisin", "tetranectin", "tetraspanin",
                "thioredoxin", "thrombopoietin", "thyroglobulin", "titin",
                "transaldolase", "transgelin", "transketolase", "translin",
                "transthyretin", "trehalase", "triadin", "trichohyalin",
                "tristetraprolin", "trophinin", "trypsin", "tryptase",
                "tryptophanase", "tsukushin", "tuberin", "tuftelin",
                "tyrosinase", "uricase", "urocortin", "uromodulin", "usherin",
                "uteroglobin", "utrophin", "vasculin", "vasorin", "vezatin",
                "vigilin", "vimentin", "vinculin", "vinexin", "vitrin",
                "vitronectin", "whirlin", "wolframin", "zonadhesin", "zyxin" };
        String[] morphologies = { "amacrine", "basket", "bitufted", "bouquet",
                "bipolar", "candelabrum", "chandelier", "cartwheel", "cone",
                "fusiform", "Golgi", "granule", "horizontal", "polymorphous",
                "Lugaro", "spiny", "mitral", "multipolar", "neurogliaform",
                "nest", "octopus", "Purkinje", "pyramidal", "Renshaw", "spiny",
                "star", "stellate", "tufted", "untufted", "unipolar" };
        for (String test : proteins) {
            System.out.println(test);
            System.out.println(Word2VecUtils.getClass(test));
        }

    }

    @Test
    public void testSleepDistanceForSmoothing() throws Exception {
        //
        // final String MODEL_FILE =
        // "/Volumes/HDD2/ren_data/data_hdd/word2vec_models/wikipedia-pubmed-and-PMC-w2v.bin";
        // FAIL final String MODEL_FILE =
        // "/Volumes/HDD2/ren_data/data_hdd/word2vec_models/1m_ns-50.w2v";

        // OK final String
        // MODEL_FILE="/Volumes/HDD2/ren_data/data_hdd/word2vec_models/PubMed-w2v.bin";
        final String MODEL_FILE = "/Volumes/HDD2/ren_data/data_hdd/word2vec_models/1m_ns_mw.w2vec-corpus.100.w2v.bin";

        // final String MODEL_FILE =
        // "/Volumes/HDD2/ren_data/data_hdd/word2vec_models/1m_ns-40.w2v.bin";
        final Word2Vec w2v = new Word2Vec().loadModel(MODEL_FILE);

        for (String queryWord : new String[] { //
        "sleep", "sleepiness", "circadian" }) {
            System.out.println(queryWord + "----------------------");
            for (WordEntry sim : w2v.distance(queryWord)) {
                System.out.println(sim);
            }
        }
    }

}
