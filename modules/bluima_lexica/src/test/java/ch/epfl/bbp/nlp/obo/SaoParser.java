package ch.epfl.bbp.nlp.obo;

import static ch.epfl.bbp.uima.utils.ConceptFileWriter.writeConceptFile;
import static com.google.common.collect.Lists.newArrayList;
import static org.apache.commons.lang.StringUtils.substringsBetween;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ch.epfl.bbp.nlp.obo.Biostar45366.Term;
import ch.epfl.bbp.uima.utils.ConceptFileWriter.Concept;

import com.google.common.collect.Sets;

public class SaoParser {

    public static void main(String[] args) throws Exception {

        List<Concept> concepts = newArrayList();

        Map<String, Term> onto = Biostar45366.parse(new File(
                "/Volumes/HDD2/ren_data/data_hdd/ontologies/OBO/sao.obo"));
        for (Term t : onto.values()) {
            // System.out.println(t);

            if (!t.id.startsWith("sao:"))
                continue;

            Set<String> variants = Sets.newHashSet(t.name);
            // property_value: sao:synonym "Stretch cells" xsd:string
            for (String ch : t.properties) {
                try {
                    // System.out.println("\t" + ch);
                    if (ch.startsWith("sao:synonym")) {
                        String v = substringsBetween(ch, "\"", "\"")[0];
                        if (v.length() > 2)
                            variants.add(v);
                    }
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
            concepts.add(new Concept(t.name, t.id, variants));
        }

        writeConceptFile(new File("target/sao.xml"), concepts);
    }
}
