package ch.epfl.bbp.uima.pubmed;

import static org.apache.commons.lang.StringUtils.join;

import java.util.ArrayList;
import java.util.List;

public class Citation {

    String title;
    int year;
    List<String> authors = new ArrayList<String>();

    public String getTitle() {
        return title;
    }

    public Citation setTitle(String title) {
        this.title = title;
        return this;
    }

    public int getYear() {
        return year;
    }

    public Citation setYear(int year) {
        this.year = year;
        return this;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public Citation setAuthors(List<String> authors) {
        this.authors = authors;
        return this;
    }

    public Citation addAuthor(String author) {
        this.authors.add(author);
        return this;
    }

    @Override
    public String toString() {
        return "t:" + title + " y:" + year + " as:" + authors;
    }

    // (Thomson A M & Lamy C[Author]) AND ("2007"[Date - Publication]) AND
    // Functional maps of neocortical local circuitry[Title]
    // ((aa bb[Title]) AND cc dd[Author]) AND ("2003"[Date - Publication])
    /**
     * @return a string that can be used to query pubmed
     */
    public String toPubmedQueryString() {
        return "" + title + "[title] AND " + join(authors, " ")
                + "[author] AND " + year + "[Date - Publication]";
    }
}
