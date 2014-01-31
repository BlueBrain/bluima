package ch.epfl.bbp.uima;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.xmlbeans.impl.xb.xsdschema.DocumentationDocument.Documentation;

/**
 * Documenting annotation. Some {@link AnalysisEngine}s have static methods to
 * instantiate them. You can document them here.
 * 
 * @see Documentation.java
 * @author renaud.richardet@epfl.ch
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ScriptingShortcut {
	
	/** A static method to call that returns an {@link AnalysisEngineDescription} */
	String shortcut();
}
