package org.apache.uima.ruta.engine;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.junit.Test;

import ch.epfl.bbp.uima.typesystem.TypeSystem;
import de.julielab.jules.types.Token;

public class Ruta101 {

    @Test
    public void testRuta() throws Exception {

        JCas jCas = JCasFactory.createJCas(TypeSystem.JULIE_TSD);
        jCas.setDocumentText("hello world");

        Ruta.apply(jCas.getCas(), "\"hello\"-> Token;");

        Collection<Token> tokens = JCasUtil.select(jCas, Token.class);

        assertEquals(1, tokens.size());

    }
}
