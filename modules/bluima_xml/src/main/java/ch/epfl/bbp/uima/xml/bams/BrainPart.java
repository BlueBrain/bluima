package ch.epfl.bbp.uima.xml.bams;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * @author renaud.richardet@epfl.ch
 */
public class BrainPart {

    private String abbr;
    private String name;
    private Reference reference;
    private String grossConstituent;
    private String description;
    private BrainPart isPartOf;
    /** a synonym */
    private BrainPart equivalentClass;

    private List<BrainPart> receiverFor = Lists.newArrayList();
    private List<BrainPart> senderFor = Lists.newArrayList();

    public String getAbbr() {
        return abbr;
    }

    public BrainPart setAbbr(String abbr) {
        this.abbr = abbr;
        return this;
    }

    public String getName() {
        return name;
    }

    public BrainPart setName(String name) {
        this.name = name;
        return this;
    }

    public Reference getReference() {
        return reference;
    }

    public BrainPart setReference(Reference reference) {
        this.reference = reference;
        return this;
    }

    public String getGrossConstituent() {
        return grossConstituent;
    }

    public BrainPart setGrossConstituent(String grossConstituent) {
        this.grossConstituent = grossConstituent;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public BrainPart setDescription(String description) {
        this.description = description;
        return this;
    }

    public BrainPart addReceiver(BrainPart r) {
        receiverFor.add(r);
        return this;
    }

    public List<BrainPart> getReceiverFor() {
        return receiverFor;
    }

    public BrainPart addSender(BrainPart s) {
        senderFor.add(s);
        return this;
    }

    public List<BrainPart> getSenderFor() {
        return senderFor;
    }

    public BrainPart getIsPartOf() {
        return isPartOf;
    }

    public void setIsPartOf(BrainPart isPartOf) {
        this.isPartOf = isPartOf;
    }

    public BrainPart getEquivalentClass() {
        return equivalentClass;
    }

    public void setEquivalentClass(BrainPart equivalentClass) {
        this.equivalentClass = equivalentClass;
    }

    // TODO // static helper methods
    // public boolean isSwanson() {
    // return reference != null
    // && reference.getNodeId().matches("wuaxqixc[275|276|367]");
    // }
    //
    // public boolean isNeuronames() {
    // return reference != null
    // && reference.getNodeId().matches("wuaxqixc[275|276|367]");
    // }

    @Override
    public String toString() {
        return name + " (" + abbr + ")";
    }

}
