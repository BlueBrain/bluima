package ch.epfl.bbp.nlp;


class DummyModelResource implements ModelResource {

    static final String CONTENT = "Hello, World!\n";
    static final String PATH = "/file.txt";
    static final String KLASS = "ch.epfl.bbp.nlp.DummyModelResource";

    @Override
    public String getResourcePath() {
        return PATH;
    }

}
