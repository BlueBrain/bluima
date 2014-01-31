package ch.epfl.bbp.uima.ae.cleanup;

import static ch.epfl.bbp.uima.BlueCasUtil.haveSameBeginEnd;
import static com.google.common.collect.Lists.newArrayList;
import static java.lang.System.out;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;

import ch.epfl.bbp.uima.types.AgeDictTerm;
import ch.epfl.bbp.uima.types.BiolexiconDictTerm;
import ch.epfl.bbp.uima.types.CellDictTerm;
import ch.epfl.bbp.uima.types.DictTerm;
import ch.epfl.bbp.uima.types.DiseaseDictTerm;
import ch.epfl.bbp.uima.types.DocumentBlock;
import ch.epfl.bbp.uima.types.DocumentElement;
import ch.epfl.bbp.uima.types.GeneralEnglish;
import ch.epfl.bbp.uima.types.IonchannelDictTerm;
import ch.epfl.bbp.uima.types.LinnaeusSpecies;
import ch.epfl.bbp.uima.types.Measure;
import ch.epfl.bbp.uima.types.MethodDictTerm;
import ch.epfl.bbp.uima.types.MoleculeDictTerm;
import ch.epfl.bbp.uima.types.OrganismDictTerm;
import ch.epfl.bbp.uima.types.Protein;
import ch.epfl.bbp.uima.types.ProteinDictTerm;
import ch.epfl.bbp.uima.types.Punctuation;
import ch.epfl.bbp.uima.types.RegionDictTerm;
import ch.epfl.bbp.uima.types.SexDictTerm;
import ch.epfl.bbp.uima.types.VerbDictTerm;
import ch.lambdaj.Lambda;
import ch.lambdaj.function.convert.Converter;

import com.google.common.collect.Sets;

import de.julielab.jules.types.Chemical;
import de.julielab.jules.types.DocumentAnnotation;
import de.julielab.jules.types.GeniaPOSTag;
import de.julielab.jules.types.Header;
import de.julielab.jules.types.Sentence;
import de.julielab.jules.types.Token;

@Deprecated
public class DisambiguatorAnnotator extends JCasAnnotator_ImplBase {

    @Deprecated
    public static final List<String> SKIP_VERBS = newArrayList("must", "would",
            "should", "being", "do", "does", "will", "might", "did", "had",
            "could", "can", "has", "may", "been", "have", "be", "were", "was",
            "are", "is");

    // FIXME add to seamantics
    @Deprecated
    public static final Set<String> SKIP_ANNOTATIONS = Sets.newHashSet(
          //FIXME  org.apache.uima.jcas.tcas.DocumentAnnotation.class.getName(),//
            DocumentAnnotation.class.getName(),//
            Token.class.getName(), //
            Sentence.class.getName(), //
            Header.class.getName(),//
            GeniaPOSTag.class.getName(),//
            DocumentElement.class.getName(),//
            DocumentBlock.class.getName(),//
            de.julielab.jules.types.pubmed.Header.class.getName());

    // FIXME add to seamantics
    @Deprecated
    @SuppressWarnings("unchecked")
    public static final ArrayList<Class<? extends Annotation>> BIO_ANNOTATIONS = newArrayList(
            DictTerm.class, //
            AgeDictTerm.class,//
            BiolexiconDictTerm.class,//
            CellDictTerm.class,//
            DiseaseDictTerm.class,//
            IonchannelDictTerm.class, //
            MethodDictTerm.class,//
            MoleculeDictTerm.class,//
            OrganismDictTerm.class,//
            ProteinDictTerm.class,//
            RegionDictTerm.class, //
            SexDictTerm.class,//
            Measure.class,//
            VerbDictTerm.class,//
            LinnaeusSpecies.class,//
            Protein.class,//
            de.julielab.jules.types.Protein.class,//
            Chemical.class);

    public static final String[] BIO_ANNOTATIONS_ARRAY;
    static {
        List<String> l = Lambda.convert(BIO_ANNOTATIONS,
                new Converter<Class<? extends Annotation>, String>() {
                    public String convert(Class<? extends Annotation> from) {
                        return from.getSimpleName();
                    }
                });

        BIO_ANNOTATIONS_ARRAY = l.toArray(new String[l.size()]);
    }

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        // int pmId = BlueCasUtil.getHeaderIntDocId(jCas);

        for (Token t : JCasUtil.select(jCas, Token.class)) {
            String pos = t.getPos();

            // POS=V --> only retain verbs that have VerbDictTerm
            if (pos.matches("^[V|MD].*$")) {

                List<Annotation> as = JCasUtil.selectCovered(jCas,
                        Annotation.class, t.getBegin(), t.getEnd());
                Annotation hasDictTerm = null;
                for (Annotation a : as) {
                    if (a instanceof VerbDictTerm)
                        hasDictTerm = a;
                }
                if (hasDictTerm != null
                        || SKIP_VERBS.contains(t.getCoveredText())) {
                    for (Annotation a : as) {
                        if (a != hasDictTerm && haveSameBeginEnd(t, a)
                                && BIO_ANNOTATIONS.contains(a.getClass()))
                            a.removeFromIndexes(jCas);
                    }
                }
            }
            // POS!=V --> remove all VerbDictTerm's
            else {
                for (VerbDictTerm v : JCasUtil.selectCovered(jCas,
                        VerbDictTerm.class, t.getBegin(), t.getEnd())) {
                    v.removeFromIndexes(jCas);
                }
            }

            System.out.println(t.getCoveredText());

            // GET ALL ANNOT. THAT COVER THIS TOKEN
            List<Annotation> allCovered = JCasUtil.selectCovered(jCas,
                    Annotation.class, t.getBegin(), t.getEnd());

            for (Annotation a : allCovered) {
                if (!(a instanceof Token || a instanceof GeneralEnglish || a instanceof Punctuation))
                    out.println("\t" + a.getClass().getSimpleName() + "\t"
                            + a.getCoveredText());
            }
        }

        // for (Token t : JCasUtil.select(jCas, Token.class)) {
        //
        // boolean hasAnnot = false;
        // List<Annotation> as = JCasUtil.selectCovered(jCas,
        // Annotation.class, t.getBegin(), t.getEnd());
        // for (Annotation a : as) {
        // if (BIO_ANNOTATIONS.contains(a.getClass()) && !(a instanceof
        // BiolexiconDictTerm))
        // hasAnnot = true;
        // }
        //
        // if (hasAnnot)
        // System.out.print("<" + t.getCoveredText() + "> ");
        // else
        // System.out.print(t.getCoveredText() + " ");
        // }
        // System.out.println();
    }

    static void p(Object... o) {
        System.out.println(StringUtils.join(o, "\t"));
    }
}