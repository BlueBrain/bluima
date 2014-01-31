package ch.epfl.bbp.uima.pubmed;

import java.io.File;

import ch.epfl.bbp.io.TextFileWriter;

public class Fetch_EPFL_Professors__Script {

    public static void main(String[] args) throws Exception {

        String[] queries = new String[] {

        "Aebischer", "Aebischer Patrick[Author]",//
                "Barrandon", "Barrandon Yann[Author]",//
                "Blanke", "Blanke Olaf[Author]",//
                "Schorderet", "Schorderet Daniel[Author]",//
                "Rainer", "Rainer Gregor[Author]",//
                "Courtine", "Courtine Grégoire[Author]",//
                "Frearing", "Frearing Patrick[Author]",//
                "Gastpar", "Gastpar Michael[Author]",//
                "Gerstner", "Gerstner Wulfram[Author]",//
                "Gruetter", "Gruetter Rolf[Author]",//
                "Hadjikhani", "Hadjikhani Nouchine[Author]",//
                "Herzog", "Herzog Michael[Author]",//
                "Ijspeert", "Ijspeert Auke[Author]",//
                "Lashuel", "Lashuel Hilal[Author]",//
                "Magistretti", "Magistretti Pierre[Author]",//
                "Markram", "Markram Henry[Author]",//
                "Micera", "Micera Silvestro[Author]",//
                "Millan", "Millàn del R. José[Author]",//
                "Petersen", "Petersen Carl[Author]",//
                "Sandi", "Sandi Carmen[Author]",//
                "Schneggenburger", "Schneggenburger Ralf[Author]",//
                "VandeVille", "Van de Ville Dimitri[Author]",//
        };

        PubmedSearch searcher = new PubmedSearch();

        for (int i = 0; i < queries.length; i = i + 2) {

            String name = queries[i], q = queries[i + 1];

            TextFileWriter writer = new TextFileWriter(
                    new File(
                            "/Users/richarde/Desktop/BBP_experiments/18_txt2PmId/epfl_profs_stats/20130309_"
                                    + name.toLowerCase() + ".txt"));

            System.out.println("searching for " + name + " -- " + q);
            for (Integer pmId : searcher.searchIds(q)) {
                writer.addLine(pmId + "");
            }
            writer.close();
        }
        System.out.println("done :-)");
    }
}
