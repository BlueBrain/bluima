package ch.epfl.bbp.uima.projects.brainregions.bams;

import static ch.epfl.bbp.uima.LexicaHelper.LEXICA_ROOT;
import static com.google.common.collect.Lists.newArrayList;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jdom2.JDOMException;

import ch.epfl.bbp.uima.utils.ConceptFileWriter.Concept;
import ch.epfl.bbp.uima.xml.ConceptMapperParser;

import com.google.common.collect.Sets;

/**
 * Loads all ConceptMapper BR lexicon.
 * 
 * @author renaud.richardet@epfl.ch
 */
public class FindSynonyms3 {

    final String[] lexiconName = { "bams2004", "bams2013", "dong", "hof",
            "neuronames", "paxinos", "swanson" };

    List<Map<String, Concept>> lexicon = newArrayList();

    public FindSynonyms3() throws JDOMException, IOException {
        for (String lexica : lexiconName) {
            Map<String, Concept> l = ConceptMapperParser.parse(new File(
                    LEXICA_ROOT + "resources/brainregions/" + lexica + ".xml"));
            lexicon.add(l);
        }
    }

    /**
     * given a canonical form (found with a lex-NER, return all variants, if any
     */
    public Set<String> getSynonyms(final String canonical) {
        Set<String> syn = Sets.newHashSet();

        for (Map<String, Concept> lexica : lexicon) {
            for (String lexCanonical : lexica.keySet()) {
                Set<String> variants = lexica.get(lexCanonical).getVariants();
                for (String variant : variants) {

                    if (canonical.equals(variant)) {
                        for (String v : variants) {
                            syn.add(v.toLowerCase());
                        }
                    }
                }
            }
        }
        return syn;
    }
}
