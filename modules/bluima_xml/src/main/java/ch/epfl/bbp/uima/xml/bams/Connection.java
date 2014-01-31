package ch.epfl.bbp.uima.xml.bams;

public class Connection {

    enum Strength {
        very_strong, //
        strong, //
        moderate_strong, //
        moderate, //
        moderate_weak, //
        weak, //
        very_weak, //
        exists, //
        does_not_exist, //
        not_clear;//

        public static Strength fromString(String s) {
            return Strength.valueOf(s.replaceAll("[ /]", "_"));
        }
    }

    private Reference reference;
    private String description;
    private BrainPart receiver;
    private BrainPart sender;
    private Strength strength;
    private String technique;

    public Reference getReference() {
        return reference;
    }

    public Connection setReference(Reference reference) {
        this.reference = reference;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Connection setDescription(String description) {
        this.description = description;
        return this;
    }

    public BrainPart getReceiver() {
        return receiver;
    }

    public Connection setReceiver(BrainPart receiver) {
        this.receiver = receiver;
        return this;
    }

    public BrainPart getSender() {
        return sender;
    }

    public Connection setSender(BrainPart sender) {
        this.sender = sender;
        return this;
    }

    public Strength getStrength() {
        return strength;
    }

    public Connection setStrength(Strength strength) {
        this.strength = strength;
        return this;
    }

    public String getTechnique() {
        return technique;
    }

    public Connection setTechnique(String technique) {
        this.technique = technique;
        return this;
    }

    @Override
    public String toString() {
        return sender + " --> " + receiver;
    }
}
