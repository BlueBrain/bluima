package ch.epfl.bbp.nlp;

public interface ModelResource {
    /**
     * Return the path to the resource inside the jar, e.g. `/file.txt`
     */
    public String getResourcePath();
}
