package ch.epfl.bbp.uima.xml.bams;

import static ch.epfl.bbp.MissingUtils.printf;
import static ch.epfl.bbp.uima.xml.bams.BamsOntologyParser.parse;
import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;
import static org.apache.commons.lang.StringUtils.join;

import java.io.File;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import ch.epfl.bbp.Histogram;
import ch.epfl.bbp.MapUtils;
import ch.epfl.bbp.io.TextFileWriter;

/**
 *
 * Scripts for initial descriptive analysis of BAMS ontology. See wiki for
 * results.
 *
 * @author renaud.richardet@epfl.ch
 */
@Ignore
public class BamsOntologyTest {

    private BamsOntology bo;

    @Before
    public void before() throws Exception {
        bo = parse();
    }

    @Test
    public void testRECEIVER_SENDER() {

        Histogram<Integer> receiver = new Histogram<Integer>();
        Histogram<Integer> sender = new Histogram<Integer>();

        for (BrainPart br : bo.brainParts.values()) {
            receiver.add(br.getReceiverFor().size());
            sender.add(br.getSenderFor().size());
        }
        System.out.println("receiver\n" + receiver);
        System.out.println("sender\n" + sender);
    }

    @Test
    public void testREFERENCES_BR() {

        Histogram<String> refH = new Histogram<String>();
        for (BrainPart br : bo.brainParts.values()) {
            Reference r = br.getReference();
            if (r == null)
                refH.add("NULL");
            else
                refH.add(r.getNodeId());
        }
        System.out.println("\nREFERENCES <--> BR");
        for (Entry<String, Long> ref : refH.getMap().entrySet()) {
            System.out.println(ref.getValue() + "\t"
                    + bo.references.get(ref.getKey()));
        }
    }

    @Test
    public void testREFERENCES_CONN() {

        Histogram<String> refConnH = new Histogram<String>();
        for (Connection c : bo.connections) {
            Reference r = c.getReference();
            if (r == null)
                refConnH.add("NULL");
            else
                refConnH.add(r.getNodeId());
        }
        System.out.println("\nREFERENCES <--> CONN");
        for (Entry<String, Long> ref : refConnH.getMap().entrySet()) {
            System.out.println("| " + ref.getValue() + " | "
                    + bo.references.get(ref.getKey()));
        }
    }

    // http://www.ncbi.nlm.nih.gov/pmc/articles/PMC2886810/
    @Test
    public void testPMC2886810() throws Exception {
        String refId = "wuaxqixc568";
        for (Connection c : bo.connections) {
            Reference r = c.getReference();
            if (r != null && refId.equals(r.getNodeId()))
                System.out.println(c);
        }
    }

    // 36887 false
    // 26212 true
    @Test
    @Ignore
    public void testReciprocalConnections() throws Exception {
        for (Connection c : bo.connections) {
            boolean reciprocal = false;
            for (Connection c2 : bo.connections) {
                if (c.getReceiver() == c2.getSender() //
                        && c.getSender() == c2.getReceiver()) {
                    reciprocal = true;
                    break;
                }
            }
            System.out.println(reciprocal);
        }
    }

    @Test
    public void testCellUsage() throws Exception {

        // init freqs
        Map<BrainPart, Integer> freq = newHashMap();
        for (BrainPart bp : bo.brainParts.values()) {
            freq.put(bp, 0);
        }

        // count freqs
        for (Connection c : bo.connections) {
            freq.put(c.getReceiver(), freq.get(c.getReceiver()) + 1);
            freq.put(c.getSender(), freq.get(c.getSender()) + 1);
        }

        Map<BrainPart, Integer> byValue = MapUtils.sortByValue(freq, true);
        for (Entry<BrainPart, Integer> en : byValue.entrySet()) {
            System.out.println(en.getKey() + "\t" + en.getKey().getReference()
                    + "\t" + en.getValue());
        }
    }

    @Test
    public void testExtractPmiIdsRegions() throws Exception {

        new File("bams_refs").mkdir();

        for (Reference ref : bo.references.values()) {

            Integer pmId = ref.getPmId();
            if (pmId != null) {

                TextFileWriter brWriter = new TextFileWriter("bams_refs/"
                        + pmId + "_bams_br.txt");
                TextFileWriter connWriter = new TextFileWriter("bams_refs/"
                        + pmId + "_bams_conn.txt");

                Set<String> brs = newHashSet();
                for (Connection c : bo.connections) {
                    Reference cRef = c.getReference();
                    if (cRef != null && cRef.equals(ref)) {

                        String r = c.getReceiver().getName();
                        String s = c.getSender().getName();

                        connWriter.addText(r + "\t" + s);
                        brs.add(r);
                        brs.add(s);
                    }
                }
                brWriter.addText(join(brs, "\n"));
                brWriter.close();
                connWriter.close();
            }
        }
    }

    @Test
    public void testStatsOnHierarchy() throws Exception {
        int tot = 0, hasPart = 0;
        for (BrainPart bp : bo.brainParts.values()) {
            tot++;
            if (bp.getIsPartOf() != null)
                hasPart++;
        }
        System.out.println("tot " + tot + " hasPart " + hasPart);
    }

    @Test
    public void testStatsOnHierarchy2() throws Exception {

        for (BrainPart bp : bo.brainParts.values()) {

            if (bp.getIsPartOf() != null) {

                String isPartOf = bp.getIsPartOf().getName().toLowerCase();
                String brName = bp.getName().toLowerCase();

                String xfix = org.apache.commons.lang.StringUtils.replace(
                        brName, isPartOf, "").trim();

                System.out.println(xfix);
            }
        }

    }

    @Test
    public void test10331578() throws Exception {
        int pmId = 10331578, pc = 0;
        for (Connection c : bo.connections) {
            if (c.getReference() != null && c.getReference().getPmId() != null
                    && c.getReference().getPmId() == pmId) {
                String r = c.getReceiver().getName();
                String s = c.getSender().getName();

                if (r.equals("Precommissural nucleus")
                        || s.equals("Precommissural nucleus"))
                    pc++;
                System.out.println(c.getReceiver().getName() + " -- "
                        + c.getSender().getName());

            }
        }
        printf("pc={}", pc);
    }
}
