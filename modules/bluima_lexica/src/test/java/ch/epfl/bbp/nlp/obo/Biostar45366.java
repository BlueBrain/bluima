package ch.epfl.bbp.nlp.obo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class Biostar45366 {
    
    private BufferedReader in;
    private String buffer;
    private Map<String, Term> id2term = new HashMap<String, Term>();

    public class Term {
        String id;
        String name;
        String def;
        Set<String> properties = new HashSet<String>();
        Set<String> children = new HashSet<String>();
        Set<String> is_a = new HashSet<String>();

        private Term() { // private
        }

        public String toString() {
            return id + "\t" + name + "\t" + is_a;
        }
    }

    private Term getTermById(String id, boolean create) {
        Term t = this.id2term.get(id);
        if (t == null && create) {
            t = new Term();
            t.id = id;
            t.name = id;
            t.def = id;
            this.id2term.put(id, t);
        }
        return t;
    }

    private static String nocomment(String s) {
        int excl = s.indexOf('!');
        if (excl != -1)
            s = s.substring(0, excl);
        return s.trim();
    }

    private String next() throws IOException {
        if (buffer != null) {
            String s = buffer;
            buffer = null;
            return s;
        }
        return in.readLine();
    }

    private void parseTerm() throws IOException {
        Term t = null;
        String line;
        while ((line = next()) != null) {
            if (line.startsWith("[")) {
                this.buffer = line;
                break;
            }
            int colon = line.indexOf(':');
            if (colon == -1)
                continue;
            if (line.startsWith("id:") && t == null) {
                t = getTermById(line.substring(colon + 1).trim(), true);
                continue;
            }
            if (t == null)
                continue;
            if (line.startsWith("name:")) {
                t.name = nocomment(line.substring(colon + 1));
                continue;
            } else if (line.startsWith("def:")) {
                t.def = nocomment(line.substring(colon + 1));
                continue;
            } else if (line.startsWith("property_value:")) {
                t.properties.add(nocomment(line.substring(colon + 1)));
                continue;
            } else if (line.startsWith("is_a:")) {
                String rel = nocomment(line.substring(colon + 1));
                t.is_a.add(rel);
                Term parent = getTermById(rel, true);
                parent.children.add(t.id);
                continue;
            }
        }
    }

    public static Map<String, Term> parse(File f) throws IOException {

        Biostar45366 parser = new Biostar45366();
        parser.in = new BufferedReader(new FileReader(f));
        String line;
        while ((line = parser.next()) != null) {
            if (line.equals("[Term]"))
                parser.parseTerm();
        }
        parser.in.close();
        return parser.id2term;
    }
}
