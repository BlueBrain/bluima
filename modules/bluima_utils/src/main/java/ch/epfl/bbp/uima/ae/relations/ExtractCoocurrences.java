package ch.epfl.bbp.uima.ae.relations;

import ch.epfl.bbp.StringUtils;
import ch.epfl.bbp.uima.BlueCasUtil;
import ch.epfl.bbp.uima.types.Cooccurrence;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.OperationalProperties;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.StringArray;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static ch.epfl.bbp.uima.BlueCasUtil.asList;
import static ch.epfl.bbp.uima.BlueUima.PARAM_COOCCURRENCE_TYPE;
import static ch.epfl.bbp.uima.BlueUima.PARAM_ENCLOSING_SCOPE;
import static ch.epfl.bbp.uima.BlueUima.PARAM_FIRST_ANNOT;
import static ch.epfl.bbp.uima.BlueUima.PARAM_FIRST_ANNOT_ID_FIELD;
import static ch.epfl.bbp.uima.BlueUima.PARAM_SECOND_ANNOT;
import static ch.epfl.bbp.uima.BlueUima.PARAM_SECOND_ANNOT_ID_FIELD;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.COOCCURRENCE;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.SENTENCE;
import static com.google.common.collect.Lists.newLinkedList;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static org.apache.commons.lang3.StringUtils.capitalize;
import static org.apache.uima.fit.util.JCasUtil.subiterate;

/**
 * Extracts cooccurrences
 * 
 * @author renaud.richardet@epfl.ch
 * @see WriteCoocurrencesToLoadfile WriteCoocurrencesToLoadfile to write these
 *      cooccurrences to a file
 */
// makes things simpler to handle a single file
@OperationalProperties(multipleDeploymentAllowed = false)
@TypeCapability(outputs = COOCCURRENCE)
public class ExtractCoocurrences extends JCasAnnotator_ImplBase {
    protected static Logger LOG = LoggerFactory
            .getLogger(ExtractCoocurrences.class);

    @ConfigurationParameter(name = PARAM_ENCLOSING_SCOPE, defaultValue = SENTENCE, //
    mandatory = false, description = "the enclosing scope to iterate on and extract co-occurrence from. Defaults to sentences")
    protected String enclosingScopeStr;
    protected Class<? extends Annotation> enclosingScope;

    @ConfigurationParameter(name = PARAM_FIRST_ANNOT, description = "the first annotation to extract co-occurrences from")
    protected String annotationStr1;
    protected Class<? extends Annotation> annotation1;

    // separated list
    @ConfigurationParameter(name = PARAM_FIRST_ANNOT_ID_FIELD, defaultValue = "coveredText",//
    description = "The name of the annotation field to get the entity id from. Uses coveredText as default to get the text of the annotation itself."
            + "Note that one can add multiple fields separated by commas.")
    protected String firstIdFieldsRaw;

    protected String[] firstIdFields;
    protected List<Method> firstIdMethods = new ArrayList<Method>();

    @ConfigurationParameter(name = PARAM_SECOND_ANNOT, description = "the first annotation to extract co-occurrences from")
    protected String annotationStr2;
    protected Class<? extends Annotation> annotation2;

    // separated list
    @ConfigurationParameter(name = PARAM_SECOND_ANNOT_ID_FIELD, defaultValue = "coveredText",//
    description = "the name of the annotation field to get the entity id from. Uses coveredText as default to get the text of the annotation itself."
            + "Note that one can add multiple fields separated by commas.")
    protected String secondIdFieldsRaw;

    protected String[] secondIdFields;
    protected List<Method> secondIdMethods = new ArrayList<Method>();

    @ConfigurationParameter(name = PARAM_COOCCURRENCE_TYPE, defaultValue = "",//
    description = "A string to distinguish the co-occurrence.")
    protected String cooccurrenceType;

    public static final String PARAM_KEEP_ONLY_NEAREST_NEIGHBORS = "keepOnlyNearestNeighbors";
    @ConfigurationParameter(name = PARAM_KEEP_ONLY_NEAREST_NEIGHBORS, defaultValue = "false", description = "keep only one co-occurrence per entity based on the distance between the two elements "
            + "of the co-occurrence")
    protected boolean keepOnlyNearestNeighbors;

    @SuppressWarnings("unchecked")
    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        try {

            firstIdFields = firstIdFieldsRaw.split(",");
            secondIdFields = secondIdFieldsRaw.split(",");

            // load classes
            enclosingScope = (Class<? extends Annotation>) Class
                    .forName(enclosingScopeStr);
            annotation1 = (Class<? extends Annotation>) Class
                    .forName(annotationStr1);
            annotation2 = (Class<? extends Annotation>) Class
                    .forName(annotationStr2);

            // validate fields
            firstIdMethods = validateFields(firstIdFields, annotation1);
            secondIdMethods = validateFields(secondIdFields, annotation2);

        } catch (Exception e) {
            throw new ResourceInitializationException(e);
        }
    }

    public void process(JCas jCas) throws AnalysisEngineProcessException {

        Map<Annotation, Integer> annotToDistance = new HashMap<Annotation, Integer>();
        Map<Annotation, Cooccurrence> annotationToCoOccurrence = new HashMap<Annotation, Cooccurrence>();

        for (Annotation enclosingAnnot : JCasUtil.select(jCas, enclosingScope)) {
            // System.out.println("tt "+enclosingAnnot.getCoveredText());

            List<? extends Annotation> annots1 = asList(subiterate(jCas,
                    annotation1, enclosingAnnot, true, false));
            List<? extends Annotation> annots2 = asList(subiterate(jCas,
                    annotation2, enclosingAnnot, true, false));

            // we need at least 1 elems in each collection
            if (annots1 != null && annots2 != null //
                    && !annots1.isEmpty() && !annots2.isEmpty()) {

                for (Annotation a1 : annots1) {
                    for (Annotation a2 : annots2) {
                        if (a1 != a2) {
                            String[] firstIdValues = getValues(firstIdMethods,
                                    a1);
                            String[] secondIdValues = getValues(
                                    secondIdMethods, a2);

                            if (keepOnlyNearestNeighbors) {
                                /*
                                 * The algorithm used below is simple. We keep
                                 * track using a map of the distance between an
                                 * element of a co-occurrence and the other. If
                                 * a new nearest co-occurrence is detected, we
                                 * remove the oldest (using another map).
                                 */
                                int oldDistanceForFirstAnnot = Integer.MAX_VALUE;
                                int oldDistanceForSecondAnnot = Integer.MAX_VALUE;
                                int distanceBetweenAnnot = BlueCasUtil
                                        .distance(a1, a2);
                                if (distanceBetweenAnnot != -1) {
                                    if (annotToDistance.containsKey(a1)) {
                                        oldDistanceForFirstAnnot = annotToDistance
                                                .get(a1);
                                    }

                                    if (annotToDistance.containsKey(a2)) {
                                        oldDistanceForSecondAnnot = annotToDistance
                                                .get(a2);
                                    }

                                    if ((distanceBetweenAnnot < oldDistanceForFirstAnnot)
                                            && (distanceBetweenAnnot < oldDistanceForSecondAnnot)) {

                                        removeExistingCooccurrences(
                                                annotationToCoOccurrence, a1,
                                                a2);

                                        annotToDistance.put(a1,
                                                distanceBetweenAnnot);
                                        annotToDistance.put(a2,
                                                distanceBetweenAnnot);

                                        Cooccurrence cooc = filterCooccurence(
                                                jCas, enclosingAnnot, a1, a2,
                                                firstIdValues, secondIdValues);
                                        if (cooc != null) {
                                            annotationToCoOccurrence.put(a1,
                                                    cooc);
                                            annotationToCoOccurrence.put(a2,
                                                    cooc);
                                        } else {
                                            // Can happen when one uses
                                            // ExtractSameCoocurrences or
                                            // ExtractCoocurrencesFilterDistance
                                            LOG.warn("a null co-occurrences happens");
                                        }
                                    }
                                }

                            } else {

                                filterCooccurence(jCas, enclosingAnnot, a1, a2,
                                        firstIdValues, secondIdValues);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Adds all cooccurrences (no filtering). Subclasses can implement finer
     * filtering.
     * 
     * @param jCas
     * @param enclosingAnnot
     * @param annot1
     * @param annot2
     * @param firstIds
     * @param firstIds
     */
    protected Cooccurrence filterCooccurence(JCas jCas,
            Annotation enclosingAnnot, Annotation annot1, Annotation annot2,
            String[] firstIds, String[] secondIds) {

        Cooccurrence cooccurence = new Cooccurrence(jCas, min(
                annot1.getBegin(), annot2.getBegin()), max(annot1.getEnd(),
                annot2.getEnd()));

        cooccurence.setFirstEntity(annot1);
        cooccurence.setSecondEntity(annot2);
        cooccurence.setFirstIds(convertToStringArray(jCas, firstIds));
        cooccurence.setSecondIds(convertToStringArray(jCas, secondIds));
        cooccurence.setSnippetBegin(enclosingAnnot.getBegin());
        cooccurence.setSnippetEnd(enclosingAnnot.getEnd());

        cooccurence.setCooccurrenceType(cooccurrenceType);
        cooccurence.addToIndexes();
        return cooccurence;
    }

    // A helper method to suppress bad cooccurrence in the keep-only-the-nearest
    // mode
    private void removeExistingCooccurrences(
            Map<Annotation, Cooccurrence> savedCoOccurrences, Annotation a1,
            Annotation a2) {
        if (savedCoOccurrences.containsKey(a1)) {
            savedCoOccurrences.get(a1).removeFromIndexes();
            savedCoOccurrences.remove(a1);
        }

        if (savedCoOccurrences.containsKey(a2)) {
            savedCoOccurrences.get(a2).removeFromIndexes();
            savedCoOccurrences.remove(a2);
        }
    }

    // A helper to avoid repeating java reflection to
    // collect the appropriate getters for given ids.
    static List<Method> validateFields(String[] idFields,
            Class<? extends Annotation> annotationClass) {
        List<Method> idMethods = newLinkedList();

        for (String idField : idFields) {

            Method idMethod = null;

            for (Method m : annotationClass.getMethods()) {
                if (m.getName().equals("get" + capitalize(idField.trim()))
                        && m.getParameterTypes().length == 0)
                    idMethod = m;
            }

            if (idMethod == null) {
                throw new RuntimeException(idField + " field for first annot "
                        + annotationClass.getCanonicalName() + " not found");
            } else {
                idMethods.add(idMethod);
            }
        }
        return idMethods;
    }

    static String[] getValues(List<Method> methods, Annotation onAnnotation) {
        List<String> ids = new LinkedList<String>();

        for (Method method : methods) {
            try {
                Object callResult = method.invoke(onAnnotation);

                if (callResult instanceof StringArray) {

                    // if the id appears to be an array, we flatten it
                    StringArray strArray = (StringArray) callResult;
                    for (String str : strArray.toStringArray()) {
                        ids.add(str);
                    }
                } else if (callResult == null) {
                    ids.add("NULL");
                } else {
                    ids.add(callResult.toString().replaceAll("[\t\n]", " "));
                }
            } catch (Exception e) {
                throw new RuntimeException("could not get idstring "
                        + method.getName() + ", " + StringUtils.print(e));
            }
        }

        return ids.toArray(new String[ids.size()]);
    }

    // A helper to convert a native java string array into a UIMA StringArray
    static StringArray convertToStringArray(JCas cas, String[] nativeStrArray) {

        int arrayLength = nativeStrArray.length;

        StringArray strArray = new StringArray(cas, arrayLength);
        for (int i = 0; i < arrayLength; i++) {
            strArray.set(i, nativeStrArray[i]);
        }
        return strArray;
    }
}
