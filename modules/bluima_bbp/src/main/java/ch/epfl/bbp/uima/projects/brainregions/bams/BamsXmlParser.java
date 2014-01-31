package ch.epfl.bbp.uima.projects.brainregions.bams;

import static java.lang.Integer.parseInt;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.io.SVReader.TSVReader;
import ch.epfl.bbp.uima.projects.brainregions.TwoDHashMap;

/**
 * 
 * @author renaud.richardet@epfl.ch
 */
@Deprecated
public class BamsXmlParser {
    static Logger LOG = LoggerFactory.getLogger(BamsXmlParser.class);

    public static void main(String[] args) throws Exception {

        for (int MIN_CNT : new int[] { 0, 1, 2, 5, 10, 20, 50, 100, 150, 200,
                500, 1000 }) {

            // final int MIN_CNT = 50;
            System.out.println("\nmincnt " + MIN_CNT);
            String myCoocurences = "/Users/richarde/Desktop/BBP_experiments/23_extract_brainregions/bams/20130703_extract_ft_with_BAMS/20130706_bams_aggregate.load_data.txt";

            // load counts
            // k1:from k2:to val:cnt
            TwoDHashMap<Integer, Integer, Integer> counts = new TwoDHashMap<Integer, Integer, Integer>();
            for (List<String> line : new TSVReader(new File(myCoocurences),
                    true)) {
                // from to cnt
                int from = parseInt(line.get(0));
                int to = parseInt(line.get(1));
                int cnt = parseInt(line.get(2));
                if (cnt > MIN_CNT) {
                    counts.set(from, to, cnt);
                }
            }
            // System.out.println(counts.size() + " from myCoocurences");
            //
            //

            double n11 = 0, n10 = 0, n01 = 0, n00 = 0;
            double nx1 = 0, nx0 = 0, n0x = 0, n1x = 0;
            //
            //

            String xml = "/Volumes/HDD2/ren_data/data_hdd/ontologies/BAMS/bams_connectome_2013-07-05_06-33-37.xml";
            InputStream corpusIs = new FileInputStream(xml);

            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(corpusIs);
            Element rootNode = doc.getRootElement();

            Iterator<Element> articleIt = rootNode// .getChild("nomenclature")
                    .getChild("connections").getChildren().iterator();
            while (articleIt.hasNext()) {
                Element e = (Element) articleIt.next();

                int id = Integer.parseInt(e.getAttribute("id").getValue());
                String name = e.getChild("name").getText();
                Element targets = e.getChild("targets");
                if (targets != null) {
                    Iterator<Element> targetIt = targets.getChildren()
                            .iterator();
                    while (targetIt.hasNext()) {
                        int toId = Integer.parseInt(((Element) targetIt.next())
                                .getAttribute("id").getValue()
                                .replaceAll("t", ""));
                        targetIt.next();
                        String strength = targetIt.next().getText();
                        // System.out.println(toId);

                        boolean bamsConnected = !strength.equals("not present");

                        boolean myConnected = counts.containsKey(id, toId)
                                || counts.containsKey(toId, id);

                        if (myConnected)
                            n1x++;
                        else
                            n0x++;
                        if (bamsConnected)
                            nx1++;
                        else
                            nx0++;

                        if (bamsConnected && myConnected)
                            n11++;
                        else if (!bamsConnected && myConnected)
                            n10++;
                        else if (bamsConnected && !myConnected)
                            n01++;
                        else if (!bamsConnected && !myConnected)
                            n00++;
                        else
                            System.err.println("woops");

                    }
                }
            }

            double phi = (n11 * n00 - n01 * n10 + 0d)
                    / Math.sqrt(n1x * n0x * nx0 * nx1 + 0d);

            System.out.println("     \tb=1  \tb=0  \ttotal");
            System.out.println("my=1\t" + n11 + "\t" + n10 + "\t" + n1x);
            System.out.println("my=0\t" + n01 + "\t" + n00 + "\t" + n0x);
            System.out.println("    \t" + nx1 + "\t" + nx0 + " phi=" + phi);

        }
    }
}
