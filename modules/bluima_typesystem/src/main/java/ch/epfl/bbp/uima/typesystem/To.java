package ch.epfl.bbp.uima.typesystem;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import ch.epfl.bbp.uima.types.BrainRegion;
import ch.epfl.bbp.uima.types.CellType;
import ch.epfl.bbp.uima.types.CellTypeProteinConcentration;
import ch.epfl.bbp.uima.types.Concentration;
import ch.epfl.bbp.uima.types.Cooccurrence;
import ch.epfl.bbp.uima.types.DictTerm;
import ch.epfl.bbp.uima.types.Keep;
import ch.epfl.bbp.uima.types.Measure;
import ch.epfl.bbp.uima.types.Protein;
import ch.epfl.bbp.uima.types.Punctuation;
import ch.epfl.bbp.uima.types.SurfaceForm;
import ch.epfl.bbp.uima.types.Verb;
import de.julielab.jules.types.Abbreviation;
import de.julielab.jules.types.Chemical;
import de.julielab.jules.types.ConceptMention;
import de.julielab.jules.types.DependencyRelation;
import de.julielab.jules.types.DocumentAnnotation;
import de.julielab.jules.types.GeniaPOSTag;
import de.julielab.jules.types.Sentence;
import de.julielab.jules.types.Token;

/**
 * Utility to create a nice toString from some type Objects<br>
 * This is a workaround to the <code>toString()</code> method of
 * annotations,because annotation class files are generated automatically from
 * xml.
 * 
 * @author ”renaud.richardet@epfl.ch”
 */
public class To {

    /**
     * @return a "nice" string representation of the object.<br>
     *         NOTE: this is not very fast. Better use it for tests only...
     */
    public static String string(Object o) {

        try {

            if (o == null) {
                return "null";
            }

            if (o instanceof Collection<?>) {
                Collection<?> c = (Collection<?>) o;
                StringBuilder sb = new StringBuilder();
                for (Object oc : c) {
                    sb.append(string(oc) + "\r\n");
                }
                return sb.toString();

            } else if (o instanceof Iterable<?>) {
                Iterable<?> i = (Iterable<?>) o;
                StringBuilder sb = new StringBuilder();
                for (Object oc : i) {
                    sb.append(string(oc) + "\r\n");
                }
                return sb.toString();

            } else if (o instanceof JCas) {
                JCas j = (JCas) o;
                return string(j.getAnnotationIndex());

                // ///
                // ///

            } else if (o instanceof Token) {
                Token t = (Token) o;
                return "Token[" + t.getCoveredText() + "]";

            } else if (o instanceof Sentence) {
                Sentence s = (Sentence) o;
                return "Sentence[" + s.getCoveredText() + "]";

            } else if (o instanceof DocumentAnnotation) {
                DocumentAnnotation d = (DocumentAnnotation) o;
                return "DocumentAnnotation[" + d.getCoveredText() + "]";

            } else if (o instanceof Verb) {
                Verb v = (Verb) o;
                return "Verb[" + v.getCoveredText() + "]";
            } else if (o instanceof Punctuation) {
                Punctuation v = (Punctuation) o;
                return "Punctuation[" + v.getCoveredText() + "]";

                // ///
                // ///

            } else if (o instanceof BrainRegion) {
                BrainRegion b = (BrainRegion) o;
                return "BrainRegion[" + b.getCoveredText() + "]";
            } else if (o instanceof Concentration) {
                Concentration c = (Concentration) o;
                return "Concentration[" + c.getCoveredText() + "]val:"
                        + c.getValue() + ";unit:" + c.getUnit();
            } else if (o instanceof Abbreviation) {
                Abbreviation a = (Abbreviation) o;
                return "Abbrev[" + a.getCoveredText() + "]expan:"
                        + a.getExpan() + "; defhere:" + a.getDefinedHere();
            } else if (o instanceof Cooccurrence) {
                Cooccurrence c = (Cooccurrence) o;
                return "Cooccurrence[" + c.getFirstEntity().getCoveredText()
                        + " <--> " + c.getSecondEntity().getCoveredText()
                        + "](" + c.getConfidence() + ")";
            } else if (o instanceof Measure) {
                Measure c = (Measure) o;
                return "Measure[" + c.getCoveredText() + "]val:" + c.getValue()
                        + ";unit:" + c.getUnit();

            } else if (o instanceof DictTerm) {
                DictTerm d = (DictTerm) o;
                return "DictTerm[" + d.getCoveredText() + "]class:"
                        + d.getClass().getSimpleName() + "; val:"
                        + d.getEntityId() + ";canonical:" + d.getDictCanon();

            } else if (o instanceof CellType) {
                CellType c = (CellType) o;
                return "CellType[" + c.getName() + "]";

            } else if (o instanceof Protein) {
                Protein p = (Protein) o;
                return "Protein[" + p.getCoveredText() + "]val:" + p.getName()
                        + ";id:" + p.getId();
            } else if (o instanceof Chemical) {
                Chemical c = (Chemical) o;
                return "Chemical[" + c.getCoveredText() + "]reg:"
                        + c.getRegistryNumber() + ";substname:"
                        + c.getNameOfSubstance();
            } else if (o instanceof SurfaceForm) {
                SurfaceForm sf = (SurfaceForm) o;
                return "SurfaceForm[" + sf.getCoveredText() + "]id:"
                        + sf.getId();

            } else if (o instanceof DependencyRelation) {
                DependencyRelation d = (DependencyRelation) o;
                String head = (d.getHead() == null) ? "" : d.getHead()
                        .getCoveredText();
                return "Dep[" + d.getCoveredText() + "]head:" + head
                        + ";label:" + d.getLabel() + ";projective:"
                        + d.getProjective();

            } else if (o instanceof ConceptMention) {
                ConceptMention c = (ConceptMention) o;
                return "ConceptMention[" + c.getCoveredText() + "]val:"
                        + c.getTextualRepresentation() + ";type:"
                        + c.getSpecificType();

            } else if (o instanceof GeniaPOSTag) {
                GeniaPOSTag g = (GeniaPOSTag) o;
                return "GeniaPOSTag[" + g.getCoveredText() + "]val:"
                        + g.getValue();

            } else if (o instanceof Abbreviation) {
                Abbreviation a = (Abbreviation) o;
                return "Abbreviation[" + a.getCoveredText() + "] expan:"
                        + a.getExpan() + " textRef:" + a.getTextReference();

            } else if (o instanceof Keep) {
                Keep k = (Keep) o;
                return "Keep[" + k.getCoveredText() + "] normalized:"
                        + k.getNormalizedText() + " enclosedA:"
                        + string(k.getEnclosedAnnot());

            } else if (o instanceof CellTypeProteinConcentration) {
                CellTypeProteinConcentration c = (CellTypeProteinConcentration) o;
                return "CellTypeProteinConcentration[" + string(c.getProtein())
                        + " - " + string(c.getConcentration()) + " - "
                        + string(c.getCelltype().getCoveredText()) + "]";

            } else if (o instanceof Annotation) { // fallback
                return o.getClass().getSimpleName() + "["
                        + ((Annotation) (o)).getCoveredText() + "]";
            }

        } catch (Exception e) {
            System.err.println("could not convert to string: " + o.toString());
        }
        return o.toString();

    }

    /**
     * @return a "nice" string representation of the object, including a header. <br>
     *         NOTE: this is not very fast. Better use it for tests only...
     */
    public static String string(String header, Object o) {
        StringBuffer sb = new StringBuffer();
        sb.append(header + ": " + StringUtils.repeat("-", 78 - header.length()));
        sb.append(string(o));
        return sb.toString();
    }

}
