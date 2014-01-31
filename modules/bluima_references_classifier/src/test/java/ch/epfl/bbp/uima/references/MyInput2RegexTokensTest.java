package ch.epfl.bbp.uima.references;

import static org.apache.commons.lang.StringUtils.join;
import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.TreeMap;

import org.junit.Test;

import cc.mallet.types.Token;

import com.google.common.collect.Maps;

/**
 * @author renaud.richardet@epfl.ch
 */
public class MyInput2RegexTokensTest {

    MyInput2RegexTokens r = new MyInput2RegexTokens();

    @Test
    public void testRegex() throws Exception {

        rassert("Gillessen T, Alzheimer C (1997) Amplifi", //
                "years_parenthesis", "author5", "author2", "author2", "years");

        rassert("R. M. Bell, Y. Koren, and C. Volinsky. The BellKor ", //
                "author3", "author6", "author1");
        // rassert("P. Fatt and B. Katz", //
        // "author3___author6___author1");
    }

    /**
     * @param input
     *            text to annotate
     * @param expected
     *            , in lexical order
     */
    private void rassert(String input, String... expected) {

        TreeMap<String, ?> gold = Maps.newTreeMap();
        for (String exp : expected) {
            gold.put(exp, null);
        }

        TreeMap<String, ?> system = Maps.newTreeMap();
        List<Token> tokens = r.addRegexes(input);
        for (Token token : tokens) {
            system.put(token.toString(), null);
        }
        assertEquals(join(gold.keySet(), "___"), join(system.keySet(), "___"));
    }
}
