package ch.epfl.bbp.uima.typesystem;

import static com.google.common.collect.Sets.newHashSet;

import java.util.List;
import java.util.Set;

import ch.epfl.bbp.uima.types.AgeDictTerm;
import ch.epfl.bbp.uima.types.BiolexiconDictTerm;
import ch.epfl.bbp.uima.types.BrainRegionDictTerm;
import ch.epfl.bbp.uima.types.CellDictTerm;
import ch.epfl.bbp.uima.types.DictTerm;
import ch.epfl.bbp.uima.types.DiseaseDictTerm;
import ch.epfl.bbp.uima.types.DocumentBlock;
import ch.epfl.bbp.uima.types.DocumentElement;
import ch.epfl.bbp.uima.types.DocumentPage;
import ch.epfl.bbp.uima.types.IonchannelDictTerm;
import ch.epfl.bbp.uima.types.Keep;
import ch.epfl.bbp.uima.types.LinnaeusSpecies;
import ch.epfl.bbp.uima.types.Measure;
import ch.epfl.bbp.uima.types.MethodDictTerm;
import ch.epfl.bbp.uima.types.MoleculeDictTerm;
import ch.epfl.bbp.uima.types.NifTerm;
import ch.epfl.bbp.uima.types.OrganismDictTerm;
import ch.epfl.bbp.uima.types.POSSkip;
import ch.epfl.bbp.uima.types.POSVerb;
import ch.epfl.bbp.uima.types.POSWh;
import ch.epfl.bbp.uima.types.Protein;
import ch.epfl.bbp.uima.types.ProteinDictTerm;
import ch.epfl.bbp.uima.types.Punctuation;
import ch.epfl.bbp.uima.types.RegionDictTerm;
import ch.epfl.bbp.uima.types.SexDictTerm;
import ch.epfl.bbp.uima.types.Verb;
import ch.epfl.bbp.uima.types.VerbDictTerm;
import ch.epfl.bbp.uima.types.WordnetDictTerm;

import com.google.common.collect.Lists;

import de.julielab.jules.types.Chemical;
import de.julielab.jules.types.DocumentAnnotation;
import de.julielab.jules.types.GeniaPOSTag;
import de.julielab.jules.types.Header;
import de.julielab.jules.types.Sentence;
import de.julielab.jules.types.Token;

/**
 * Provides semantic "classes" of annotations
 * 
 * @author renaud.richardet@epfl.ch
 */
public class TypeSystemSemantics {

    /** Annotations that are not bound to content, e.g. {@Header}, etc */
    public final static Set<String> NON_CONTENT_ANNOTATIONS = newHashSet(
            DocumentAnnotation.class.getName(),
            // FIXME
            // org.apache.uima.jcas.tcas.DocumentAnnotation.class.getName(),
            Header.class.getName(), //
            de.julielab.jules.types.pubmed.Header.class.getName(),
            Sentence.class.getName(),//
            GeniaPOSTag.class.getName(),//
            DocumentPage.class.getName(),//
            DocumentElement.class.getName(),//
            DocumentBlock.class.getName());

    /**
     * same as {@link #NON_CONTENT_ANNOTATIONS}, but with {@link Token} and
     * {@link Keep}
     */
    public final static Set<String> NON_CONTENT_ANNOTATIONS_OR_TOKEN = newHashSet(
            DocumentAnnotation.class.getName()//
            , "uima.tcas.DocumentAnnotation"// LATER
            , "org.apache.uima.jcas.tcas.DocumentAnnotation"// LATER
            , Header.class.getName() //
            , de.julielab.jules.types.pubmed.Header.class.getName()//
            , Sentence.class.getName()//
            , neuromodulo.Neuromodulo.Sentence.class.getName()//
            , GeniaPOSTag.class.getName()//
            , DocumentPage.class.getName()//
            , DocumentElement.class.getName()//
            , DocumentBlock.class.getName()//
            , Token.class.getName()//
            , TypeSystem.W//
            , TypeSystem.SW//
            , TypeSystem.CW//
            , TypeSystem.RUTA_BASIC//
            , Keep.class.getName()//
    );

    /**
     * Defines semantic priority between annotations, in INCREASING order of
     * prio. Used among others in ViterbiFilterAnnotator.
     */
    public static final List<String> ANNOTATIONS_PRIORITY = Lists.newArrayList( //
            Token.class.getName() //
            , DictTerm.class.getName() //
            , Verb.class.getName() //
            , BiolexiconDictTerm.class.getName() //
            , NifTerm.class.getName() //
            , ProteinDictTerm.class.getName() //
            , VerbDictTerm.class.getName() //
            , WordnetDictTerm.class.getName() //
            , Chemical.class.getName() //
            , MoleculeDictTerm.class.getName() //
            , MethodDictTerm.class.getName() //
            , SexDictTerm.class.getName() //
            , CellDictTerm.class.getName() //
            , DiseaseDictTerm.class.getName() //
            , RegionDictTerm.class.getName() //
            , BrainRegionDictTerm.class.getName() //
            , LinnaeusSpecies.class.getName() //
            , Punctuation.class.getName() //
            , Measure.class.getName() //
            , Protein.class.getName() //
            , OrganismDictTerm.class.getName() //
            , IonchannelDictTerm.class.getName() //
            , AgeDictTerm.class.getName() //
            , POSVerb.class.getName() //
            , POSWh.class.getName() //
            , POSSkip.class.getName() //
            );
}
