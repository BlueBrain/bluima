package ch.epfl.bbp.uima.xml.bams;

public class Reference {

    enum Type {
        article("http://brancusi1.usc.edu/RDF/article"), //
        book("http://brancusi1.usc.edu/RDF/book"), //
        bookChapter("http://brancusi1.usc.edu/RDF/bookChapter"), //
        thesis("http://brancusi1.usc.edu/RDF/thesis");

        private String ns;

        Type(String ns) {
            this.ns = ns;
        }

        public static Type fromNs(String ns) {
            for (Type t : Type.values()) {
                if (ns.equals(t.ns)) {
                    return t;
                }
            }
            return null;
        }
    }

    private String nodeId;
    private String author;
    private String booktitle;
    private String name;
    private String number;
    private String pages;
    private String title;
    private Type type;
    private String url;
    private String volume;
    private String workspace;
    private String year;

    public String getNodeId() {
        return nodeId;
    }

    public Reference setNodeId(String nodeId) {
        this.nodeId = nodeId;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public Reference setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getBooktitle() {
        return booktitle;
    }

    public Reference setBooktitle(String booktitle) {
        this.booktitle = booktitle;
        return this;
    }

    public String getName() {
        return name;
    }

    public Reference setName(String name) {
        this.name = name;
        return this;
    }

    public String getNumber() {
        return number;
    }

    public Reference setNumber(String number) {
        this.number = number;
        return this;
    }

    public String getPages() {
        return pages;
    }

    public Reference setPages(String pages) {
        this.pages = pages;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Reference setTitle(String title) {
        this.title = title;
        return this;
    }

    public Type getType() {
        return type;
    }

    public Reference setType(Type type) {
        this.type = type;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Reference setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getVolume() {
        return volume;
    }

    public Reference setVolume(String volume) {
        this.volume = volume;
        return this;
    }

    public String getWorkspace() {
        return workspace;
    }

    public Reference setWorkspace(String workspace) {
        this.workspace = workspace;
        return this;
    }

    public String getYear() {
        return year;
    }

    public Reference setYear(String year) {
        this.year = year;
        return this;
    }

    // helper methods

    public Integer getPmId() {
        try {
            return Integer.parseInt(url.replaceAll("\\D+", ""));
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return "[" + type.name().toUpperCase() + "] "
                + ((title == null) ? booktitle : title) + " (" + url + ")";
    }
}
