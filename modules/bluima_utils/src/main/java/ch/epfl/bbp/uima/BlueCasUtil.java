package ch.epfl.bbp.uima;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Collections.unmodifiableList;
import static org.apache.uima.fit.util.JCasUtil.exists;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.NotImplementedException;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.impl.Subiterator;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.FSArray;
import org.apache.uima.jcas.cas.StringArray;
import org.apache.uima.jcas.cas.TOP;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.util.CasCreationUtils;
import org.slf4j.Logger;

import ch.epfl.bbp.uima.filter.TooFewTokensFilterAnnotator;
import ch.epfl.bbp.uima.filter.TooMuchOOVFilterAnnotator;
import ch.epfl.bbp.uima.types.TooFewTokensPerPage;
import ch.epfl.bbp.uima.types.TooManyOOV;

import com.google.common.collect.Lists;

import de.julielab.jules.types.Header;
import de.julielab.jules.types.POSTag;
import de.julielab.jules.types.Sentence;
import de.julielab.jules.types.Token;

/**
 * Utility class to work with {@link JCas}es
 * 
 * @see JCasUtil
 * @author renaud.richardet@epfl.ch
 */
public class BlueCasUtil {
    private static Logger LOG = getLogger(BlueCasUtil.class);

    /**
     * @param jcas
     * @param clasz
     * @param strictClass
     * @return the annotations of this jcas that are strictly equal to
     *         strictClass
     */
    public static <T extends TOP> Collection<T> selectStrict(JCas jcas,
            Class<T> clasz, Class<?> strictClass) {

        Collection<T> raw = JCasUtil.select(jcas, clasz);
        return filterStrict(raw, strictClass);
    }

    public static boolean isEmptyText(JCas jCas) {
        return jCas.getDocumentText() == null
                || jCas.getDocumentText().length() == 0;
    }

    /**
     * @return all annotations of this jcas that start and end at the same char
     *         as annotIn
     */
    public static List<Annotation> findOverlapping(JCas jcas, Annotation annotIn) {

        List<Annotation> overlappings = new ArrayList<Annotation>();
        for (Annotation a : jcas.getAnnotationIndex()) {

            if (a.getBegin() == annotIn.getBegin()
                    && a.getEnd() == annotIn.getEnd()) {
                overlappings.add(a);
            }
        }

        return overlappings;
    }

    public static <T> Collection<T> filterStrict(Iterable<T> raw,
            Class<?> strictClass) {
        Collection<T> strict = new LinkedList<T>();
        for (T t : raw) {
            if (t.getClass().equals(strictClass)) {
                strict.add(t);
            }
        }
        return strict;
    }

    /**
     * @return the value of the {@link Header#getDocId()} for this jCas as an
     *         int, if set; -1 otherwise
     */
    public static int getHeaderIntDocId(JCas jCas) {
        try {
            Header header = JCasUtil.selectSingle(jCas, Header.class);
            return Integer.parseInt(header.getDocId());
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * @return the value of the {@link Header#getDocId()} for this jCas, if set;
     *         null otherwise
     */
    public static String getHeaderDocId(JCas jCas) {
        try {
            Header header = JCasUtil.selectSingle(jCas, Header.class);
            return header.getDocId();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @return the value of the {@link Header#getSource()} for this jCas, if
     *         set; null otherwise
     */
    public static String getHeaderSource(JCas jCas) {
        try {
            Header header = JCasUtil.selectSingle(jCas, Header.class);
            return header.getSource();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * If this cas has no Sentence annotation, creates one with the whole cas
     * text
     */
    public static void fixNoSentences(JCas jCas) {
        Collection<Sentence> sentences = select(jCas, Sentence.class);
        if (sentences.size() == 0) {
            String text = jCas.getDocumentText();
            Sentence sentence = new Sentence(jCas, 0, text.length());
            sentence.addToIndexes();
        }
    }

    /** If this cas has no text, set it to "" */
    public static void fixNoText(JCas jCas) {
        if (jCas.getDocumentText() == null)
            jCas.setDocumentText("");
    }

    public static String getSinglePosTag(Token t) {
        FSArray posTag = t.getPosTag();
        if (posTag != null && posTag.size() > 0) {
            return ((POSTag) posTag.get(0)).getValue();
        }
        return null;
    }

    public static Iterator<JCas> iterator(final CollectionReader cr) {

        return new Iterator<JCas>() {

            @Override
            public boolean hasNext() {
                try {
                    return cr.hasNext();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public JCas next() {
                try {
                    CAS cas;
                    cas = CasCreationUtils.createCas(cr
                            .getProcessingResourceMetaData());
                    cr.getNext(cas);
                    return cas.getJCas();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void remove() {
                throw new NotImplementedException();
            }
        };
    }

    public static ArrayList<JCas> asList(final CollectionReader cr) {
        return Lists.newArrayList(iterator(cr));
    }

    public static String inspect(JCas jcas, int begin, int end) {
        return inspect(new Annotation(jcas, begin, end));
    }

    /**
     * Util to print an annotation for inspection.Prints some text before and
     * after this annotation, and add '{', '}' around the annotation text.
     * 
     * @return
     **/
    public static String inspect(Annotation a) {

        final int begin = 40, after = 50;
        try {
            JCas jCas = a.getCAS().getJCas();

            String text = jCas.getDocumentText();

            StringBuilder sb = new StringBuilder();
            if (a.getBegin() - begin < 0) {
                for (int i = 0; i < (begin - a.getBegin()); i++) {
                    sb.append(' ');
                }
                sb.append(text.substring(0, a.getBegin()));
            } else {
                sb.append(text.substring(a.getBegin() - begin, a.getBegin()));
            }
            sb.append('{');
            sb.append(a.getCoveredText());
            sb.append('}');

            if (a.getEnd() < text.length()) {
                sb.append(text.substring(a.getEnd(),
                        Math.min(a.getEnd() + after, text.length())));
            }

            sb.append("[pmid:" + getHeaderIntDocId(jCas) + ", " + a.getBegin()
                    + ":" + a.getEnd() + "]");

            return sb.toString();

        } catch (Throwable e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void setDocId(JCas jCas, int docId) {
        if (JCasUtil.exists(jCas, Header.class)) {
            throw new IllegalArgumentException();
        }
        Header h = new Header(jCas);
        h.setDocId(docId + "");
        h.addToIndexes();
    }

    public static boolean haveSameBeginEnd(Annotation a, Annotation b) {
        return a.getBegin() == b.getBegin() && a.getEnd() == b.getEnd();
    }

    /**
     * @return the value of the {@link Header#getTitle()} for this jCas, if set;
     *         empty string otherwise
     */
    public static String getTitle(JCas jCas) {
        try {
            Header header = JCasUtil.selectSingle(jCas, Header.class);
            return header.getTitle();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * REM: modified from CasUtils..
     * 
     * Get a list of annotations constraint by a certain annotation. Iterates
     * over all annotations to find the covered annotations. Does not use
     * subiterators and does not respect type prioritites. Was adapted from
     * {@link Subiterator}. Uses the same approach except that type priorities
     * are ignored.
     * 
     * @param cas
     *            a CAS.
     * @param coveringAnnotation
     *            the covering annotation.
     * @see Subiterator
     */
    public static List<Annotation> selectCovered(CAS cas,
            AnnotationFS coveringAnnotation) {
        final int begin = coveringAnnotation.getBegin();
        final int end = coveringAnnotation.getEnd();

        final List<Annotation> list = new ArrayList<Annotation>();
        final FSIterator<AnnotationFS> it = cas.getAnnotationIndex().iterator();

        // Try to seek the insertion point.
        it.moveTo(coveringAnnotation);

        // If the insertion point is beyond the index, move back to the last.
        if (!it.isValid()) {
            it.moveToLast();
            if (!it.isValid()) {
                return list;
            }
        }

        // Ignore type priorities by seeking to the first that has the same
        // begin
        boolean moved = false;
        while (it.isValid() && (it.get()).getBegin() >= begin) {
            it.moveToPrevious();
            moved = true;
        }

        // If we moved, then we are now on one starting before the requested
        // begin, so we have to
        // move one ahead.
        if (moved) {
            it.moveToNext();
        }

        // If we managed to move outside the index, start at first.
        if (!it.isValid()) {
            it.moveToFirst();
        }

        // Skip annotations whose start is before the start parameter.
        while (it.isValid() && (it.get()).getBegin() < begin) {
            it.moveToNext();
        }

        boolean strict = true;
        while (it.isValid()) {
            AnnotationFS a = it.get();
            if (!(a instanceof Annotation))
                continue;

            // If the start of the current annotation is past the end parameter,
            // we're done.
            if (a.getBegin() > end) {
                break;
            }
            it.moveToNext();
            if (strict && a.getEnd() > end) {
                continue;
            }

            checkArgument(a.getBegin() >= coveringAnnotation.getBegin(),
                    "Illegal begin " + a.getBegin() + " in ["
                            + coveringAnnotation.getBegin() + ".."
                            + coveringAnnotation.getEnd() + "]");
            checkArgument(
                    a.getEnd() <= coveringAnnotation.getEnd(),
                    "Illegal end " + a.getEnd() + " in ["
                            + coveringAnnotation.getBegin() + ".."
                            + coveringAnnotation.getEnd() + "]");

            if (!a.equals(coveringAnnotation) && !BlueCasUtil.isDocAnnot(a)) {
                list.add((Annotation) a);
            }
        }
        return unmodifiableList(list);
    }

    // @SuppressWarnings({ "rawtypes", "unchecked" })
    // public static Collection<? extends TOP> filter(
    // Collection<? extends TOP> annotations, String fieldName,
    // Object expected) throws SecurityException, NoSuchFieldException,
    // IllegalArgumentException, IllegalAccessException,
    // NoSuchMethodException, InvocationTargetException {
    //
    // Collection ret = new ArrayList();
    //
    // for (TOP annotation : annotations) {
    //
    // Method method = annotation.getClass().getMethod(
    // "get" + StringUtils.capitalize(fieldName));
    //
    // if (expected instanceof Integer) {
    // try {
    // int expectedInt = (Integer) expected;
    // int actual = Integer.parseInt((String) method
    // .invoke(annotation));
    // if (actual == expectedInt) {
    // ret.add(annotation);
    // }
    // } catch (Exception e) {// nope
    // }
    // } else if (expected instanceof String) {
    // String expectedStr = (String) expected;
    // String actual = (String) method.invoke(annotation);
    // if (actual.equals(expectedStr)) {
    // ret.add(annotation);
    // }
    // } // TODO for other fields types
    //
    // }
    //
    // return ret;
    // }

    /**
     * Whether this document should be kept for analysis, based on:
     * <ul>
     * <li>language == en</li>
     * <li>OOV < 0.4 (see {@link TooMuchOOVFilterAnnotator})</li>
     * <li>Enough tokens per page (see {@link TooFewTokensFilterAnnotator})</li>
     * </ul>
     */
    public static boolean keepDoc(JCas jCas) {
        String lang = jCas.getDocumentLanguage();
        if (lang.equals("x-unspecified")) {
            LOG.warn("document language needed to decide whether to keepDoc(), but document language is not set, pmId"
                    + getHeaderDocId(jCas));
        }
        if (!(lang.equals("en") || lang.equals("x-unspecified")) || //
                exists(jCas, TooFewTokensPerPage.class) || //
                exists(jCas, TooManyOOV.class)) {
            return false;
        }
        return true;
    }

    /**
     * (Potentially) Slow, but convenient
     * 
     * @param select
     *            comes from {@link JCasUtil} select.
     */
    public static <T extends TOP> List<T> asList(Iterable<T> select) {
        return Lists.newLinkedList(select);
    }

    public static boolean isDocAnnot(AnnotationFS a) {
        return a.getType().getName().equals("uima.tcas.DocumentAnnotation");
    }

    /**
     * @param annot1
     * @param annot2
     * @return the distance (gap) between these two annotation, or -1 if they
     *         overlap
     */
    public static int distance(Annotation a1, Annotation a2) {

        // a1 then a2
        int dist = a2.getBegin() - a1.getEnd();
        if (dist > 0)
            return dist;

        // a2 then a1
        dist = a1.getBegin() - a2.getEnd();
        if (dist > 0)
            return dist;

        // overlap --> dist not defined
        return -1;
    }

    public static int maxEnd(Annotation a1, Annotation a2) {
        return Math.max(a1.getEnd(), a2.getEnd());
    }

    public static int minBegin(Annotation a1, Annotation a2) {
        return Math.min(a1.getBegin(), a2.getBegin());
    }

    public static enum Position {
        before, after, overlap
    };

    /**
     * @param annot1
     * @param annot2
     * @return whether annot1 is before annot2
     */
    public static Position isBefore(Annotation a1, Annotation a2) {

        // a1 then a2
        if (a2.getBegin() - a1.getEnd() > 0)
            return Position.before;

        // a2 then a1
        if (a1.getBegin() - a2.getEnd() > 0)
            return Position.after;

        // overlap
        return Position.overlap;
    }

    public static List<String> toList(StringArray tokens) {
        List<String> l = new ArrayList<String>(tokens.size());
        for (int i = 0; i < tokens.size(); i++) {
            l.add(tokens.get(i));
        }
        return l;
    }

}
