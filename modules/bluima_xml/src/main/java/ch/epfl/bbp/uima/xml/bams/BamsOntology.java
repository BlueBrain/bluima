package ch.epfl.bbp.uima.xml.bams;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;

import java.util.List;
import java.util.Map;

public class BamsOntology {

    Map<String, Reference> references = newHashMap();
    Map<String, BrainPart> brainParts = newHashMap();
    List<Connection> connections = newArrayList();

    public Map<String, BrainPart> getBrainParts() {
        return brainParts;
    }

    public List<Connection> getConnections() {
        return connections;
    }

    public Map<String, Reference> getReferences() {
        return references;
    }

}
