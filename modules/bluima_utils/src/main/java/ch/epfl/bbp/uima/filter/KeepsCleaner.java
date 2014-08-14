package ch.epfl.bbp.uima.filter;

import static org.apache.uima.fit.util.JCasUtil.select;

import java.util.Collection;
import java.util.Stack;
import java.util.regex.Pattern;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.jcas.JCas;

import ch.epfl.bbp.uima.types.Keep;

/**
 * Performs cleaning on {@link Keep#getNormalizedText()}. Should be performed at
 * the end of the filtering process.
 * 
 * @author renaud.richardet@epfl.ch
 */
public class KeepsCleaner extends JCasAnnotator_ImplBase {

    private final int minLength = 3;

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        Collection<Keep> col = select(jCas, Keep.class);
        Keep[] keeps = col.toArray(new Keep[col.size()]);

        for (int i = 0; i < keeps.length; i++) {
            final String txt = keeps[i].getNormalizedText();

            if (txt.length() < minLength //
                    || !isBalanced(txt) //
                    || txt.startsWith("www.") //
                    || isOnlyPunctAndNumbers(txt)) {
                // REMOVE
                keeps[i].removeFromIndexes();
            } else {
                // CLEAN
                keeps[i].setNormalizedText(cleanup(txt));
            }
        }
    }

    private static final Pattern onlyPunctAndNumbers = Pattern
            .compile("^(?:\\p{Punct}|\\d)+$");

    public static boolean isOnlyPunctAndNumbers(String txt) {
        return onlyPunctAndNumbers.matcher(txt).matches();
    }

    public static String cleanup(String txt) {
        // 1) merge if starts or ends with punctuation
        // 2) lowercase
        return txt.replaceAll("^\\W+|\\W+$", "").toLowerCase();
    }

    /**
     * @return true if the parentheses in the given expression are balanced.
     *         Otherwise the return value is false.<br>
     *         Note that characters other than ( ) { } and [ ] are ignored.
     */
    public static boolean isBalanced(final String expression) {

        final char LEFT_NORMAL = '(', RIGHT_NORMAL = ')';
        final char LEFT_CURLY = '{', RIGHT_CURLY = '}';
        final char LEFT_SQUARE = '[', RIGHT_SQUARE = ']';

        Stack<Character> stack = new Stack<Character>();
        int i; // An index into the string
        boolean failed = false; // true if a mismatch

        for (i = 0; !failed && (i < expression.length()); i++) {
            switch (expression.charAt(i)) {
            case LEFT_NORMAL:
            case LEFT_CURLY:
            case LEFT_SQUARE:
                stack.push(expression.charAt(i));
                break;
            case RIGHT_NORMAL:
                if (stack.isEmpty() || (stack.pop() != LEFT_NORMAL))
                    failed = true;
                break;
            case RIGHT_CURLY:
                if (stack.isEmpty() || (stack.pop() != LEFT_CURLY))
                    failed = true;
                break;
            case RIGHT_SQUARE:
                if (stack.isEmpty() || (stack.pop() != LEFT_SQUARE))
                    failed = true;
                break;
            }
        }
        return (stack.isEmpty() && !failed);
    }
}