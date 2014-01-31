package ch.epfl.bbp.uima.mongo;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang.StringUtils.capitalize;
import static org.slf4j.LoggerFactory.getLogger;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.StringArray;
import org.apache.uima.jcas.tcas.Annotation;
import org.slf4j.Logger;

import ch.epfl.bbp.uima.types.AgeDictTerm;
import ch.epfl.bbp.uima.types.BiolexiconDictTerm;
import ch.epfl.bbp.uima.types.BrainRegionDictTerm;
import ch.epfl.bbp.uima.types.CellDictTerm;
import ch.epfl.bbp.uima.types.Concentration;
import ch.epfl.bbp.uima.types.DataTable;
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
import ch.epfl.bbp.uima.types.Keep;
import ch.epfl.bbp.uima.types.Stopword;
import ch.epfl.bbp.uima.types.VariableAssignment;
import ch.epfl.bbp.uima.types.Verb;
import ch.epfl.bbp.uima.types.VerbDictTerm;
import ch.epfl.bbp.uima.types.WordnetDictTerm;

import com.google.common.collect.Lists;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;

import de.julielab.jules.types.Chemical;
import de.julielab.jules.types.Sentence;
import de.julielab.jules.types.Token;

/**
 * Handle mapping between UIMA {@link Annotation}s and Mongo.<br>
 * ATM, mappings are hardcoded in {@link MongoFieldMapping#ALL_MAPPINGS}. LATER
 * use Mongo to store the mappings.
 * 
 * @author renaud.richardet@epfl.ch
 */
public class MongoFieldMapping {
    protected static final Logger LOG = getLogger(MongoFieldMapping.class);

    // mongo (reserved) db fields
    static public final String ID = "_id";
    static public final String PM_ID = "pmid";
    static public final String BEGIN = "b";
    static public final String END = "e";
    static public final String TITLE = "title";
    static public final String TEXT = "text";
    static public final String FILTER = "ftr";

    /** key: annotation class name */
    public static final Map<String, MongoFieldMapping> ALL_MAPPINGS = new HashMap<String, MongoFieldMapping>();
    static {
        // FORMAT: Class, shortname, pairs of field - short

        // Basic annot.
        add(Sentence.class, "snt");
        add(Token.class, "tok", "pos", "p");
        add(Punctuation.class, "pnct");
        add(GeneralEnglish.class, "gen");
        add(POSWh.class, "poswh");
        add(POSVerb.class, "posv");
        add(POSSkip.class, "possk");
        add(Keep.class, "kp");
        add(Stopword.class, "stp");
        // TODO add mapping for referenced annotation

        // DictTerms
        add(DictTerm.class, "dt", "entityId", "eid");
        add(BiolexiconDictTerm.class, "blx", "entityId", "eid");
        add(VerbDictTerm.class, "vrb", "entityId", "eid");
        add(Verb.class, "verb");
        add(RegionDictTerm.class, "reg", "entityId", "eid");
        add(BrainRegionDictTerm.class, "breg", "entityId", "eid");
        add(CellDictTerm.class, "cel", "entityId", "eid");
        add(ProteinDictTerm.class, "prt", "entityId", "eid");
        add(AgeDictTerm.class, "age", "entityId", "eid");
        add(IonchannelDictTerm.class, "ic", "entityId", "eid");
        add(DiseaseDictTerm.class, "dis", "entityId", "eid");
        add(OrganismDictTerm.class, "org", "entityId", "eid");
        add(SexDictTerm.class, "sex", "entityId", "eid");
        add(MoleculeDictTerm.class, "mol", "entityId", "eid");
        add(MethodDictTerm.class, "met", "entityId", "eid");
        add(WordnetDictTerm.class, "wnt", "entityId", "eid");
        add(NifTerm.class, "nif", "entityId", "eid");

        add(Measure.class, "mes", "textValue", "tv", "value", "v", "unit", "u");
        add(VariableAssignment.class, "var", "textValue", "tv", "value", "v",
                "variableName", "vn", "operator", "op");
        add(Concentration.class, "conc", "textValue", "tv", "value", "v",
                "unit", "u");

        add(LinnaeusSpecies.class, "lin", "mostProbableSpeciesId", "sid",
                "allIdsString", "aid", "ambigous", "amb");
        add(Chemical.class, "che", "registryNumber", "reg", "nameOfSubstance",
                "nam");
        add(Protein.class, "pro", "id", "id", "name", "name");

        // PDF doc mappings
        add(DocumentElement.class, "de", "ElementId", "eid", "height", "h",//
                "width", "w", "x", "x", "y", "y", "pageId", "pid", "label", "l");
        add(DocumentBlock.class, "db", "ElementId", "eid", "height", "h",//
                "width", "w", "x", "x", "y", "y", "pageId", "pid", //
                "label", "l", "hasBold", "bo", "hasManyFontsizes", "f");
        add(DataTable.class, "dtb", "tableId", "id", "pageNumber", "pg",
                "rowCount", "rw", "columnCount", "col", "caption", "cap",
                "referenceText", "ref", "headings", "h", "body", "bdy");

        // "verify" mappings
        for (String mName : ALL_MAPPINGS.keySet()) {
            MongoFieldMapping m = ALL_MAPPINGS.get(mName);
            try {
                @SuppressWarnings("unchecked")
                Class<? extends Annotation> classz = (Class<? extends Annotation>) Class
                        .forName(mName);
                List<String> methods = new ArrayList<String>();
                for (Method mt : classz.getMethods()) {
                    methods.add(mt.getName());
                }

                for (String field : m.fieldMappings.keySet()) {
                    if (!methods.contains("get" + capitalize(field))) {
                        System.err.println("cannot find field for mapping "
                                + mName + ", field " + field);
                        System.exit(2);
                    }
                    String shortCut = m.fieldMappings.get(field);
                    if (shortCut.equals(BEGIN) || shortCut.equals(END)) {
                        System.err.println("field shortcut for " + field
                                + " cannot be either e or b (reserved)");
                        System.exit(2);
                    }
                }
            } catch (ClassNotFoundException e) {
                System.err.println("cannot find class for mapping " + mName);
                System.exit(2);
            }
        }
    }
    public final static Set<String> ALL_MAPPINGS_KEYS = ALL_MAPPINGS.keySet();

    private static void add(Class<? extends Annotation> class1,
            String shortName, String... fieldMappings) {

        checkArgument(fieldMappings.length % 2 == 0,
                "even nr of field mappings! " + fieldMappings.length);

        for (MongoFieldMapping mfm : ALL_MAPPINGS.values()) {
            checkArgument(!mfm.shortName.equals(shortName),
                    "duplicate shortname! " + shortName);
        }
        checkArgument(!shortName.equals(FILTER), FILTER + " shortname reserved");

        MongoFieldMapping mfm = new MongoFieldMapping(shortName);
        for (int i = 0; i < fieldMappings.length; i = i + 2) {
            mfm.add(fieldMappings[i], fieldMappings[i + 1]);
        }
        ALL_MAPPINGS.put(class1.getName(), mfm);
    }

    // phew, done with statics

    public final String shortName;
    public final Map<String, String> fieldMappings = new HashMap<String, String>();

    public MongoFieldMapping(String shortName) {
        this.shortName = shortName;
    }

    public MongoFieldMapping add(String fieldName, String shortFieldName) {
        fieldMappings.put(fieldName, shortFieldName);
        return this;
    }

    static void writeFieldToDb(String range, BasicDBObject o, Annotation a,
            String dbKey, Feature f) {

        if (range.equals("String")) {
            o.put(dbKey, a.getStringValue(f));
        } else if (range.equals("StringArray")) {
            StringArray sa = (StringArray) a.getFeatureValue(f);
            if (sa != null) {
                String[] vals = sa.toArray();
                o.put(dbKey, Lists.newArrayList(vals));
            }
        } else if (range.equals("Integer")) {
            o.put(dbKey, a.getIntValue(f));
        } else if (range.equals("Float")) {
            o.put(dbKey, a.getFloatValue(f));
        } else if (range.equals("Boolean")) {
            o.put(dbKey, a.getBooleanValue(f));
        } else {
            LOG.warn("range not supported " + range);
        }
    }

    public static void readFieldFromDb(String fieldKey, String range,
            Annotation a, Feature f, BasicDBObject dbO, JCas jCas) {

        if (dbO.containsField(fieldKey)) {

            if (range.equals("String")) {
                a.setStringValue(f, dbO.getString(fieldKey));
            } else if (range.equals("StringArray")) {
                BasicDBList vals = (BasicDBList) dbO.get(fieldKey);
                StringArray sa = new StringArray(jCas, vals.size());
                for (int i = 0; i < vals.size(); i++) {
                    sa.set(i, vals.get(i).toString());
                }
                a.setFeatureValue(f, sa);
            } else if (range.equals("Integer")) {
                a.setIntValue(f, dbO.getInt(fieldKey));
            } else if (range.equals("Float")) {
                a.setFloatValue(f, (float) dbO.getDouble(fieldKey));
            } else if (range.equals("Boolean")) {
                a.setBooleanValue(f, dbO.getBoolean(fieldKey));
            } else {
                LOG.warn("range not supported " + range);
            }
        }
    }
}
