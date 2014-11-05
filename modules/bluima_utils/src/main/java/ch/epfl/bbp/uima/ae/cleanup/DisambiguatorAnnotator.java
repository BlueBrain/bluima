package ch.epfl.bbp.uima.ae.cleanup;

import static com.google.common.collect.Lists.newArrayList;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.uima.jcas.tcas.Annotation;

import ch.epfl.bbp.collections.Create;
import ch.epfl.bbp.uima.types.AgeDictTerm;
import ch.epfl.bbp.uima.types.BiolexiconDictTerm;
import ch.epfl.bbp.uima.types.CellDictTerm;
import ch.epfl.bbp.uima.types.DictTerm;
import ch.epfl.bbp.uima.types.DiseaseDictTerm;
import ch.epfl.bbp.uima.types.DocumentBlock;
import ch.epfl.bbp.uima.types.DocumentElement;
import ch.epfl.bbp.uima.types.IonchannelDictTerm;
import ch.epfl.bbp.uima.types.LinnaeusSpecies;
import ch.epfl.bbp.uima.types.Measure;
import ch.epfl.bbp.uima.types.MethodDictTerm;
import ch.epfl.bbp.uima.types.MoleculeDictTerm;
import ch.epfl.bbp.uima.types.OrganismDictTerm;
import ch.epfl.bbp.uima.types.Protein;
import ch.epfl.bbp.uima.types.ProteinDictTerm;
import ch.epfl.bbp.uima.types.RegionDictTerm;
import ch.epfl.bbp.uima.types.SexDictTerm;
import ch.epfl.bbp.uima.types.VerbDictTerm;

import com.google.common.collect.Sets;

import de.julielab.jules.types.Chemical;
import de.julielab.jules.types.DocumentAnnotation;
import de.julielab.jules.types.GeniaPOSTag;
import de.julielab.jules.types.Header;
import de.julielab.jules.types.Sentence;
import de.julielab.jules.types.Token;

@Deprecated
public class DisambiguatorAnnotator {

    @Deprecated
    public static final List<String> SKIP_VERBS = newArrayList("must", "would",
            "should", "being", "do", "does", "will", "might", "did", "had",
            "could", "can", "has", "may", "been", "have", "be", "were", "was",
            "are", "is");

    // FIXME add to seamantics
    @Deprecated
    public static final Set<String> SKIP_ANNOTATIONS = Sets.newHashSet(
    // FIXME org.apache.uima.jcas.tcas.DocumentAnnotation.class.getName(),//
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
        List<String> l = Create.list();
        for (Class<? extends Annotation> a : BIO_ANNOTATIONS) {
            l.add(a.getSimpleName());
        }
        BIO_ANNOTATIONS_ARRAY = l.toArray(new String[l.size()]);
    }
}