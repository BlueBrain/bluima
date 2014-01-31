package ch.epfl.bbp.uima.typesystem;

import org.apache.uima.fit.factory.TypeSystemDescriptionFactory;
import org.apache.uima.resource.metadata.TypeSystemDescription;

public class TypeSystem {

    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String ABBREVIATION = "de.julielab.jules.types.Abbreviation";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String ABSTRACT_TEXT = "de.julielab.jules.types.AbstractText";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String ACRONYM = "de.julielab.jules.types.Acronym";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String ANNOTATION = "de.julielab.jules.types.Annotation";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String AUTHOR_INFO = "de.julielab.jules.types.AuthorInfo";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String BAD_ENTITY_MENTION = "de.julielab.jules.types.BadEntityMention";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String BIO_ENTITY_MENTION = "de.julielab.jules.types.BioEntityMention";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String BIO_EVENT_MENTION = "de.julielab.jules.types.BioEventMention";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String BIO_RELATION_MENTION = "de.julielab.jules.types.BioRelationMention";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String CAPTION = "de.julielab.jules.types.Caption";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String CELL = "de.julielab.jules.types.Cell";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String CHEMICAL = "de.julielab.jules.types.Chemical";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String CHEMICALS = "de.julielab.jules.types.Chemicals";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String CHUNK = "de.julielab.jules.types.Chunk";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String CHUNK_ADJP = "de.julielab.jules.types.ChunkADJP";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String CHUNK_ADVP = "de.julielab.jules.types.ChunkADVP";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String CHUNK_CONJP = "de.julielab.jules.types.ChunkCONJP";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String CHUNK_LST = "de.julielab.jules.types.ChunkLST";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String CHUNK_NP = "de.julielab.jules.types.ChunkNP";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String CHUNK_PP = "de.julielab.jules.types.ChunkPP";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String CHUNK_PRT = "de.julielab.jules.types.ChunkPRT";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String CHUNK_SBAR = "de.julielab.jules.types.ChunkSBAR";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String CHUNK_VP = "de.julielab.jules.types.ChunkVP";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String CONCEPT_MENTION = "de.julielab.jules.types.ConceptMention";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String DATE = "de.julielab.jules.types.Date";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String DEPENDENCY_RELATION = "de.julielab.jules.types.DependencyRelation";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String DESCRIPTOR = "de.julielab.jules.types.Descriptor";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String DISEASE = "de.julielab.jules.types.Disease";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String DOCUMENT_ANNOTATION = "de.julielab.jules.types.DocumentAnnotation";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String ENTITY_MENTION = "de.julielab.jules.types.EntityMention";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String EVENT_MENTION = "de.julielab.jules.types.EventMention";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String EVENT_TRIGGER = "de.julielab.jules.types.EventTrigger";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String GENE = "de.julielab.jules.types.Gene";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String GENIA_CONSTITUENT = "de.julielab.jules.types.GENIAConstituent";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String GENIA_POSTAG = "de.julielab.jules.types.GeniaPOSTag";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String GO_MENTION = "de.julielab.jules.types.GOMention";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String GOOD_ENTITY_MENTION = "de.julielab.jules.types.GoodEntityMention";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String GRAMMATICAL_FEATS = "de.julielab.jules.types.GrammaticalFeats";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String HEADER = "de.julielab.jules.types.Header";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String JOURNAL = "de.julielab.jules.types.Journal";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String KEYWORD = "de.julielab.jules.types.Keyword";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String LEMMA = "de.julielab.jules.types.Lemma";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String LIST = "de.julielab.jules.types.List";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String LIST_ITEM = "de.julielab.jules.types.ListItem";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String MANUAL_DESCRIPTOR = "de.julielab.jules.types.ManualDescriptor";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String MESH_HEADING = "de.julielab.jules.types.MeshHeading";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String MESH_MENTION = "de.julielab.jules.types.MeshMention";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String NOUN_FEATS = "de.julielab.jules.types.NounFeats";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String ONT_CLASS_MENTION = "de.julielab.jules.types.OntClassMention";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String ONT_RELATION_MENTION = "de.julielab.jules.types.OntRelationMention";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String ORGANISM = "de.julielab.jules.types.Organism";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String ORGANISM_ATTRIBUTE = "de.julielab.jules.types.OrganismAttribute";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String PARAGRAPH = "de.julielab.jules.types.Paragraph";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String PENNBIOIE_CONSTITUENT = "de.julielab.jules.types.PennBioIEConstituent";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String PENNBIOIE_POSTAG = "de.julielab.jules.types.PennBioIEPOSTag";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String PENN_POSTAG = "de.julielab.jules.types.PennPOSTag";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String POS_TAG = "de.julielab.jules.types.POSTag";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String PRONOUN_FEATS = "de.julielab.jules.types.PronounFeats";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String JULIE_PROTEIN = "de.julielab.jules.types.Protein";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String PUB_TYPE = "de.julielab.jules.types.PubType";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String PUBMED_HEADER = "de.julielab.jules.types.pubmed.Header";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String RELATION_MENTION = "de.julielab.jules.types.RelationMention";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String RESOURCE_ENTRY = "de.julielab.jules.types.ResourceEntry";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String SECTION = "de.julielab.jules.types.Section";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String SENTENCE = "de.julielab.jules.types.Sentence";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String SIMPLEX = "de.julielab.jules.types.Simplex";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String STEMMED_FORM = "de.julielab.jules.types.StemmedForm";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String TITLE = "de.julielab.jules.types.Title";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String TOKEN = "de.julielab.jules.types.Token";
    /** Annotation for JULIE Lab typesystem @version 2.6.8 */
    public static final String ZONE = "de.julielab.jules.types.Zone";

    /** Annotation for BBP Lab typesystem @version 2.6.8 */
    public static final String CELL_TYPE = "ch.epfl.bbp.uima.types.CellType";
    /** Annotation for BBP Lab typesystem @version 2.6.8 */
    public static final String CELL_TYPE_PROTEIN_CONCENTRATION = "ch.epfl.bbp.uima.types.CellTypeProteinConcentration";
    /** Annotation for BBP Lab typesystem @version 2.6.8 */
    public static final String CONCENTRATION = "ch.epfl.bbp.uima.types.Concentration";
    /** Annotation for BBP Lab typesystem @version 2.6.8 */
    public static final String PROTEIN = "ch.epfl.bbp.uima.types.Protein";
    /** Annotation for BBP Lab typesystem @version 2.6.8 */
    public static final String SURFACE_FORM = "ch.epfl.bbp.uima.types.SurfaceForm";
    /** Annotation for BBP Lab typesystem @version 2.6.8 */
    public static final String POS_VERB = "ch.epfl.bbp.uima.types.POSVerb";
    /** Annotation for BBP Lab typesystem @version 2.6.8 */
    public static final String LINNAEUS_SPECIES = "ch.epfl.bbp.uima.types.LinnaeusSpecies";
    /** Annotation for BBP Lab typesystem @version 2.6.8 */
    public static final String DOCUMENT_SPECIES = "ch.epfl.bbp.uima.types.DocumentSpecies";
    /** Annotation for BBP Lab typesystem @version 2.6.8 */
    public static final String BRAIN_REGION = "ch.epfl.bbp.uima.types.BrainRegion";
    /** Annotation for BBP Lab typesystem @version 2.6.8 */
    public static final String ROOM_NUMBER = "org.apache.uima.examples.RoomNumber";
    /** Annotation for BBP Lab typesystem @version 2.6.8 */
    public static final String MEASURE = "ch.epfl.bbp.uima.types.Measure";
    /** Annotation for BBP Lab typesystem @version 2.6.8 */
    public static final String GENERAL_ENGLISH = "ch.epfl.bbp.uima.types.GeneralEnglish";
    /** Annotation for BBP Lab typesystem @version 2.6.8 */
    public static final String MULTIPLE_PROTEINS = "ch.epfl.bbp.uima.types.MultipleProteins";
    /** Annotation for BBP Lab typesystem @version 2.6.8 */
    public static final String WORDNET = "ch.epfl.bbp.uima.types.WordnetDictTerm";
    /** Annotation for BBP Lab typesystem @version 2.6.8 */
    public static final String BRAINREGION_DICT = "ch.epfl.bbp.uima.types.BrainRegionDictTerm";
    /** Annotation for BBP Lab typesystem @version 2.6.8 */
    public static final String PUNCTUATION = "ch.epfl.bbp.uima.types.Punctuation";
    /** Annotation for BBP Lab typesystem @version 2.6.8 */
    public static final String TOPIC = "ch.epfl.bbp.uima.types.Topic";
    /** Annotation for BBP Lab typesystem @version 2.6.8 */
    public static final String TOPIC_DISTRIBUTION = "ch.epfl.bbp.uima.types.TopicDistribution";
    /** Annotation for BBP Lab typesystem @version 2.6.8 */
    public static final String KEEP = "ch.epfl.bbp.uima.types.Keep";
    /** Annotation for BBP Lab typesystem @version 2.6.8 */
    public static final String DOCUMENT_ELEMENT = "ch.epfl.bbp.uima.types.DocumentElement";
    /** Annotation for BBP Lab typesystem @version 2.6.8 */
    public static final String DOCUMENT_BLOCK = "ch.epfl.bbp.uima.types.DocumentBlock";
    /** Annotation for BBP Lab typesystem @version 2.6.8 */
    public static final String TOO_MANY_OOV = "ch.epfl.bbp.uima.types.TooManyOOV";
    /** Annotation for BBP Lab typesystem @version 2.6.8 */
    public static final String TOO_FEW_TOKENS_PER_PAGE = "ch.epfl.bbp.uima.types.TooFewTokensPerPage";
    /** Annotation for BBP Lab typesystem @version 2.6.8 */
    public static final String ET_AL_INLINE_REFERENCE = "ch.epfl.bbp.uima.types.EtAlInlineReference";
    /** Annotation for BBP Lab typesystem @version 2.6.8 */
    public static final String COOCCURRENCE = "ch.epfl.bbp.uima.types.Cooccurrence";

    public static final String[] JULIE_TSD_ARRAY = new String[] {
            // "typeSystem/jules-ace-types",//
            "typeSystem/julie-basic-types",//
            "typeSystem/julie-document-meta-pubmed-types",//
            "typeSystem/julie-document-meta-types",//
            "typeSystem/julie-document-structure-types",//
            "typeSystem/julie-morpho-syntax-types",//
            // "typeSystem/julie-muc7-types",//
            // "typeSystem/julie-semantics-ace-types",//
            "typeSystem/julie-semantics-biology-types",//
            "typeSystem/julie-semantics-bootstrep-types",//
            "typeSystem/julie-semantics-concept-types",//
            "typeSystem/julie-semantics-mention-types",//
            "typeSystem/julie-semantics-stemnet-types",//
            "typeSystem/test-entity-type",//

            "typeSystem/bbp-basic-types",//
            "typeSystem/bbp-semantics-biology-types",//
            "typeSystem/LinnaeusAnnotationTypes",//
            "typeSystem/RoomNumber",//
            "typeSystem/DictTerm",//
            "typeSystem/RASPTypes",//

    // "org/cleartk/TypeSystem",//

    };

    /** Typesystem from Julie Lab, version 2.6.8 */
    public static final TypeSystemDescription JULIE_TSD = TypeSystemDescriptionFactory
            .createTypeSystemDescription(JULIE_TSD_ARRAY);
}
