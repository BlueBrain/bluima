package ch.epfl.bbp.uima.typesystem;

import static ch.epfl.bbp.uima.typesystem.TypeSystemSemantics.newHashSet;

import java.util.Set;

import ch.epfl.bbp.uima.types.DocumentBlock;

/**
 * Content sections represent the type of a paragraph, e.g. if it is a table,
 * the abstract or a bibliographical entry. Content sections are constants
 * (String) used in Filtering framework, and set to
 * {@link DocumentBlock#setLabel()}.
 * 
 * @author renaud.richardet@epfl.ch
 */
public class ContentSection {

    // CONTENT SECTIONS
    /** used to flag {@link DocumentBlock#setLabel()} */
    public static final String SECTION_TITLE = "TITLE";
    /** used to flag {@link DocumentBlock#setLabel()} */
    public static final String SECTION_ABSTRACT = "ABSTRACT";
    /** used to flag {@link DocumentBlock#setLabel()} */
    public static final String SECTION_INTRODUCTION = "INTRODUCTION";
    /** used to flag {@link DocumentBlock#setLabel()} */
    public static final String SECTION_MAT_MET = "MAT_MET";
    /** used to flag {@link DocumentBlock#setLabel()} */
    public static final String SECTION_SUMMARY = "SUMMARY";
    /** used to flag {@link DocumentBlock#setLabel()} */
    public static final String SECTION_FIGURE = "FIGURE";
    /** used to flag {@link DocumentBlock#setLabel()} */
    public static final String SECTION_TABLE = "TABLE";
    /** used to flag {@link DocumentBlock#setLabel()} */
    public static final String SECTION_RESULTS = "RESULTS";
    /** used to flag {@link DocumentBlock#setLabel()} */
    public static final String SECTION_SUPPL = "SUPL_MAT";

    /**
     * List of section labels (set in {@link DocumentBlock#setLabel()} that
     * represent content
     */
    public static final Set<String> CONTENT_SECTIONS = newHashSet(
            SECTION_ABSTRACT, //
            SECTION_INTRODUCTION,//
            SECTION_MAT_MET, //
            SECTION_SUMMARY, //
            SECTION_FIGURE,//
            SECTION_TABLE, //
            SECTION_RESULTS, //
            SECTION_SUPPL);

    // NON-CONTENT SECTIONS
    /** used to flag {@link DocumentBlock#setLabel()} */
    public static final String SECTION_CITATION = "CITATION";
    /** used to flag {@link DocumentBlock#setLabel()} */
    public static final String SECTION_ABBREVIATIONS = "ABBREVIATIONS";
    /** used to flag {@link DocumentBlock#setLabel()} */
    public static final String SECTION_DOWNLOADED = "DOWNLOADED";
    /** used to flag {@link DocumentBlock#setLabel()} */
    public static final String SECTION_DOWNLOADED_ON = "DOWNLOADED_ON";
    /** used to flag {@link DocumentBlock#setLabel()} */
    public static final String SECTION_URL_ON_A_LINE = "URL_ON_A_LINE";
    /** used to flag {@link DocumentBlock#setLabel()} */
    public static final String SECTION_RECEIVED = "RECEIVED";
    /** used to flag {@link DocumentBlock#setLabel()} */
    public static final String SECTION_ACKNOWLEDGEMENTS = "ACKNOWLEDGEMENTS";
    /** used to flag {@link DocumentBlock#setLabel()} */
    public static final String SECTION_EMAIL = "EMAIL";
    /** used to flag {@link DocumentBlock#setLabel()} */
    public static final String SECTION_REFERENCES = "REFERENCES";
    /** used to flag {@link DocumentBlock#setLabel()} */
    // set by ReferenceClassifier
    public static final String SECTION_REFERENCES_ENTRY = "REFERENCES_ENTRY";

    /**
     * List of section labels (set in {@link DocumentBlock#setLabel()} that
     * represent NON-content, like references or acknowledgement
     */
    public static final Set<String> NONCONTENT_SECTIONS = newHashSet(
            SECTION_CITATION, //
            SECTION_ABBREVIATIONS, //
            SECTION_DOWNLOADED, //
            SECTION_DOWNLOADED_ON,//
            SECTION_URL_ON_A_LINE, //
            SECTION_RECEIVED, //
            SECTION_ACKNOWLEDGEMENTS,//
            SECTION_EMAIL, //
            SECTION_REFERENCES, //
            SECTION_REFERENCES_ENTRY);

    public static final boolean isNonContentSection(String sectionId) {
        return NONCONTENT_SECTIONS.contains(sectionId);
    }
}
