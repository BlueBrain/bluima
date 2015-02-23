package ch.epfl.bbp.nlp.obo;

import static ch.epfl.bbp.MissingUtils.printf;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;

import ch.epfl.bbp.io.SVReader.TSVReader;
import ch.epfl.bbp.uima.obo.OBOOntology;
import ch.epfl.bbp.uima.obo.OntologyTerm;

public class EvalCoverageGoProteins {
    public static void main(String[] args) throws IOException {

        OBOOntology obo = new OBOOntology();
        obo.read(new File("resources/obo/go-basic.obo"));
        TSVReader reader = new TSVReader(
                new File(
                        "/Volumes/HDD2/ren_scratch/Dropbox/neuron_ner/experiments/20150209_proteins.tsv"),
                false);

        int cnt = 0, found = 0;

        for (List<String> line : reader) {
            String protein = line.get(1);
            //FIXME space
            protein = protein.replaceAll("-?expressing|principal|containing|immuno(nega|posi|reac)tive|positive", "");

            boolean foundHere = false;
            for (Entry<String, OntologyTerm> en : obo.terms.entrySet()) {
                String name = en.getValue().getName();
                name = name.replaceAll(" process|activity", "");
                if (name.equals(protein)) {
                    foundHere = true;
                }
            }
            if (foundHere){
                found++;
            }else{
                System.out.println("not found: "+protein);
            }
            cnt++;
        }
        printf("cnt {}, found {}", cnt, found);

    }
}
