package ch.epfl.bbp.uima.laucher;

import static ch.epfl.bbp.uima.ae.BeanshellAnnotator.SCRIPT_STRING;
import static ch.epfl.bbp.uima.ae.PrintCommentAnnotator.MSG_STRING;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.JULIE_TSD;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.Sets.newHashSet;
import static java.lang.Integer.parseInt;
import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.join;
import static org.apache.commons.lang3.StringEscapeUtils.escapeXml;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReaderDescription;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.math3.util.Pair;
import org.apache.uima.analysis_component.AnalysisComponent;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.component.JCasCollectionReader_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.ResourceCreationSpecifierFactory;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.metadata.ResourceMetaData;
import org.slf4j.Logger;

import bsh.EvalError;
import bsh.Interpreter;
import ch.epfl.bbp.IteratorWithPrevious;
import ch.epfl.bbp.uima.BlueUima;
import ch.epfl.bbp.uima.ae.BeanshellAnnotator;
import ch.epfl.bbp.uima.ae.PrintCommentAnnotator;
import ch.epfl.bbp.uima.jython.JythonAnnotator2;

/**
 * Pipeline script parser. See README.txt for formal pipeline script format.
 * 
 * @author renaud.richardet@epfl.ch
 */
public class PipelineScriptParser {
    private static Logger LOG = getLogger(PipelineScriptParser.class);

    /**
     * Parses a pipeline script file.
     * 
     * @param scriptFile
     *            the pipeline script file to parse
     * @return the parsed {@link Pipeline}
     */
    public static Pipeline parse(File scriptFile) throws ParseException,
            IOException {
        return parse(scriptFile, null);
    }

    /**
     * Parses a pipeline script file.
     * 
     * @param scriptFile
     *            the pipeline script file to parse
     * @param replacementVars
     *            the variables to replace in the original pipeline script. The
     *            first variable gets inserted in place of all $1, the second in
     *            place of $2, etc.
     * @return the parsed {@link Pipeline}
     */
    public static Pipeline parse(File scriptFile, List<String> replacementVars)
            throws ParseException, IOException {
        checkArgument(
                scriptFile.exists(),
                "could not find pipeline script file at "
                        + scriptFile.getAbsolutePath());
        if (replacementVars == null)
            replacementVars = new ArrayList<String>();

        List<String> scriptLines = FileUtils.readLines(scriptFile);

        // parse
        Pipeline pipeline = parseAndDispatch(scriptLines, new Pipeline(),
                scriptFile.getParentFile(), replacementVars);

        // verify pipeline not empty
        if (pipeline.crd == null)
            LOG.warn("no CollectionReader defined in this pipeline");
        // throw new ParseException(
        // "no collection reader defined in this pipeline script", -1);
        if (pipeline.aeds.isEmpty())
            throw new ParseException(
                    "no analysis engines defined in this pipeline script, at least define one",
                    -1);

        return pipeline;
    }

    private static Pipeline parseAndDispatch(List<String> scriptLines,
            Pipeline pipeline, File parentFile, List<String> replacementVars)
            throws ParseException {

        // replace variables ($ROOT, $1, $2, ...)
        List<String> replacedScriptLines = new ArrayList<String>();
        for (String l : scriptLines) {
            l = l.replaceAll("\\$ROOT", BlueUima.BLUE_UIMA_ROOT);
            for (int i = 0; i < replacementVars.size(); i++) {
                l = l.replaceAll("\\$" + (i + 1), replacementVars.get(i));
            }
            replacedScriptLines.add(l);
        }

        IteratorWithPrevious<String> it = new IteratorWithPrevious<String>(
                replacedScriptLines.listIterator());
        while (it.hasNext()) {
            String current = it.next();

            if (StringUtils.isBlank(current) || current.startsWith("#")
                    || current.startsWith("//")) {
                continue; // a comment --> skip

            } else if (current.startsWith("max_errors: ")) {
                parseMaxErrors(current, pipeline);
            } else if (current.startsWith("threads: ")) {
                parseThreads(current, pipeline);
            } else if (current.startsWith("cr: ")) {
                parseCR(current, it, pipeline);
            } else if (current.startsWith("ae: ")) {
                parseAE(current, it, pipeline);
            } else if (current.startsWith("ae_java: ")) {
                parseAEJava(current, it, pipeline);
            } else if (current.startsWith("include: ")) {
                parseInclude(current, pipeline, parentFile, replacementVars);
            } else if (current.startsWith("python:")) {
                parsePython(it, pipeline);
            } else if (current.startsWith("java:")) {
                parseJava(it, pipeline);
            } else if (current.startsWith("print:")) {
                parsePrint(current, pipeline);
            } else {
                throw new ParseException(
                        "could not parse line '"
                                + current
                                + "'\n--> line should start with one of the "
                                + "following, followed by a space:\n# (a comment)\n\nthreads:\n"
                                + "cr:\nae:\nae_java:\ninclude:\npython:\njava:\n"
                                + "print:\nmax_errors:\n", -1);
            }
        }
        return pipeline;
    }

    /** Parses max # of errors */
    private static void parseMaxErrors(String current, Pipeline pipeline)
            throws ParseException {
        try {
            String errorsStr = current.replaceFirst("max_errors: ", "").trim();
            int errors = parseInt(errorsStr);
            pipeline.setMaxErrors(errors);
            pipeline.addXml("<maxErrors>" + errors + "</maxErrors>");
            LOG.info("+-using {} max_errors", errors);
        } catch (NumberFormatException e) {
            throw new ParseException(
                    "could not parse number of max_errors from " + current, -1);
        }
    }

    /**
     * Parses # of threads for this pipeline
     * 
     * @return
     */
    private static void parseThreads(String current, Pipeline pipeline)
            throws ParseException {
        try {
            String threadsStr = current.replaceFirst("threads: ", "").trim();
            int threads = parseInt(threadsStr);
            pipeline.setThreads(threads);
            LOG.info("+-using {} threads ", threads);
            pipeline.addXml("<threads>" + threads + "</threads>");
        } catch (NumberFormatException e) {
            throw new ParseException("could not parse number of threads from "
                    + current, -1);
        }
    }

    /**
     * Parses inline, raw Python code and executes it with Jython
     * 
     * @return
     */
    private static void parsePython(IteratorWithPrevious<String> it,
            Pipeline pipeline) throws ParseException {

        String script = "";
        while (it.hasNext()) {
            String current = it.next();
            if (StringUtils.isBlank(current))
                break;
            script += current + "\n";
        }
        if (script.length() < 3)
            throw new ParseException("empty script", -1);

        try {
            AnalysisEngineDescription aed = AnalysisEngineFactory
                    .createEngineDescription(JythonAnnotator2.class,
                            JythonAnnotator2.SCRIPT_STRING, script);
            pipeline.addAe(aed);
            pipeline.addXml("<pythonInline>" + escapeXml(script)
                    + "</pythonInline>");
        } catch (ResourceInitializationException e) {
            throw new ParseException("could not create aed with script, "
                    + e.getMessage(), -1);
        }
    }

    /**
     * Parses inline, raw java code and executes it with Beanshell
     * 
     * @return
     */
    private static void parseJava(IteratorWithPrevious<String> it,
            Pipeline pipeline) throws ParseException {

        String script = "";
        while (it.hasNext()) {
            String current = it.next();
            if (isBlank(current))
                break;
            script += current + "\n";
        }
        if (script.length() < 3)
            throw new ParseException("empty script", -1);

        try {
            pipeline.aeds.add(createEngineDescription(BeanshellAnnotator.class,
                    SCRIPT_STRING, script));
            pipeline.addXml("<javaInline>" + escapeXml(script)
                    + "</javaInline>");
        } catch (ResourceInitializationException e) {
            throw new ParseException("could not create aed with script "
                    + script, -1);
        }
    }

    /** Creates an AE that prints the given msg */
    private static void parsePrint(String msg, Pipeline pipeline)
            throws ParseException {
        try {
            pipeline.aeds.add(createEngineDescription(
                    PrintCommentAnnotator.class, MSG_STRING, msg.trim()));
        } catch (ResourceInitializationException e) {
            throw new ParseException("could not create aed with msg " + msg, -1);
        }
    }

    /** parse include commands */
    private static void parseInclude(String line, Pipeline pipeline,
            File parentFile, List<String> cliArgs) throws ParseException {
        LOG.info("+-parsing include line '{}'", line);
        String includeName = line.replaceFirst("include: ", "").trim();
        File includeFile = new File(parentFile, includeName);
        if (!includeFile.exists()) {
            String didYouMean = "";
            if (new File(parentFile, "../" + includeName).exists()) {
                didYouMean = "\ndid you mean: '../" + includeName + "'";
            } else if (new File(parentFile, includeName.replaceFirst("\\.\\./",
                    "")).exists()) {
                didYouMean = "\ndid you mean: '"
                        + includeName.replaceFirst("\\.\\./", "") + "'";
            }
            throw new ParseException("include file does not exist ("
                    + includeFile.getAbsolutePath() + ") " + didYouMean, -1);
        }

        try {
            List<String> includeStr = FileUtils.readLines(includeFile);
            includeStr.add("");
            includeStr.add("");
            parseAndDispatch(includeStr, pipeline, includeFile.getParentFile(),
                    cliArgs);

        } catch (IOException e) {
            throw new ParseException("cannot read include file ("
                    + includeFile.getAbsolutePath() + ")", -1);
        }
    }

    /** Parses an AE defined on ONE line in inline java code (interpreted) */
    private static void parseAEJava(String line,
            IteratorWithPrevious<String> it, Pipeline pipeline)
            throws ParseException {
        LOG.info("+-parsing line '{}'", line);

        Interpreter i = new Interpreter(); // Construct an interpreter

        // Eval a statement and get the result
        try {
            String script = line.replaceFirst("ae_java: ", "").trim();
            i.eval("aed = " + script + ";");
            AnalysisEngineDescription aed = (AnalysisEngineDescription) i
                    .get("aed");
            pipeline.addAe(aed);
            pipeline.addXml("<javaAe>" + escapeXml(script) + "</javaAe>");
        } catch (EvalError e) {
            e.fillInStackTrace();
            throw new ParseException(
                    "cannot compile and run 'ae_java' command, " + e.toString(),
                    -1);
        }
    }

    @SuppressWarnings("unchecked")
    private static void parseCR(String line, IteratorWithPrevious<String> it,
            Pipeline pipeline) throws ParseException {
        LOG.info("+-parsing line '{}'", line);

        if (pipeline.getCr() != null) {
            throw new ParseException("CollectionReader set twice!", -1);
        }

        // instantiate class
        String clasz = line.replaceFirst("cr: ", "").trim();
        if (clasz.indexOf('.') == -1)
            clasz = "ch.epfl.bbp.uima.cr." + clasz;
        Class<? extends CollectionReader> classz = null;
        try {
            classz = (Class<? extends CollectionReader>) Class.forName(clasz);
        } catch (ClassNotFoundException e1) {
            throw new ParseException("no class found on classpath: " + clasz,
                    -1);
        } catch (Exception e1) {
            throw new ParseException("unknown error: " + e1.getMessage(), -1);
        }

        // parse params
        Pair<List<Object>, String> params = parseParams(it);
        validateParams(params.getKey(), classz);

        // create cr
        try {
            CollectionReaderDescription descr = createReaderDescription(classz,
                    JULIE_TSD, params.getKey().toArray());
            String name = descr.getMetaData().getName();
            if (name == null) {
                ResourceMetaData metaData = descr.getMetaData();
                metaData.setName(clasz);
                descr.setMetaData(metaData);
            }
            pipeline.setCr(descr);
            pipeline.addXml("<cr id=\"" + name + "\">\n" + params.getValue()
                    + "</cr>\n");
        } catch (ResourceInitializationException e) {
            throw new ParseException(
                    "could not instantiate CollectionReader, error: "
                            + e.getMessage(), -1);
        }
    }

    /**
     * check that this ae or cr has the right parameters
     * 
     * @param params
     *            key : val : key : val
     * @param classz
     *            a cr or an ae
     * @throws ParseException
     *             if the keys are not declared as fields in the classz
     */
    private static void validateParams(List<Object> params, Class<?> classz) {

        // skip validation if not uimafit
        if (!JCasAnnotator_ImplBase.class.isAssignableFrom(classz)
                && !JCasCollectionReader_ImplBase.class.isAssignableFrom(//
                        classz)) {
            LOG.warn(
                    "    +- Could not validate parameters in {}. You might want to check it manually.",
                    classz.getName());
            return;
        }

        for (int i = 0; i < params.size(); i = i + 2) {
            String paramName = (String) params.get(i);
            try {

                // add fields from superclass as well
                Set<Field> fields = newHashSet(classz.getDeclaredFields());
                fields.addAll(newHashSet(classz.getSuperclass()
                        .getDeclaredFields()));

                List<String> paramNames = new ArrayList<String>();
                for (Field f : fields) {
                    if (f.isAnnotationPresent(ConfigurationParameter.class)) {
                        ConfigurationParameter confParam = f
                                .getAnnotation(ConfigurationParameter.class);
                        paramNames.add(confParam.name());
                    }
                }

                if (!paramNames.contains(paramName)) {
                    LOG.warn(
                            "    +-XXXXXXXXX Could not find parameter '{}' in {}. You should check it.",
                            paramName, classz.getName());
                    LOG.warn("    +-XXXXXXXXX Available parameters are:\n",
                            join(paramNames, "\n"));
                }
            } catch (SecurityException e) {
                LOG.error(
                        "could not validate params for "
                                + classz.getSimpleName(), e);
            }
        }
    }

    @SuppressWarnings("unchecked")
    static void parseAE(String line, IteratorWithPrevious<String> it,
            Pipeline pipeline) throws ParseException {
        LOG.info("+-parsing line '{}'", line);

        String aeName = line.replaceFirst("ae: ", "").trim();
        if (aeName.endsWith(".xml")) {
            File aeXml = new File(aeName.replaceAll("\\$ROOT",
                    BlueUima.BLUE_UIMA_ROOT));
            if (!aeXml.exists())
                throw new ParseException("no xml descriptor found at "
                        + aeXml.getAbsolutePath(), -1);

            Pair<List<Object>, String> params = parseParams(it);

            // create ae
            try {
                AnalysisEngineDescription specifier = (AnalysisEngineDescription) ResourceCreationSpecifierFactory
                        .createResourceCreationSpecifier(aeXml
                                .getAbsolutePath(), params.getKey().toArray());
                pipeline.addAe(specifier);
                pipeline.addXml("<aeXml file=\"" + aeXml.getAbsolutePath()
                        + "\">\n" + params.getValue() + "</aeXml>\n");

            } catch (Exception e) {
                throw new ParseException(
                        "could not instantiate CollectionReader, error: "
                                + e.getMessage(), -1);
            }

        } else {

            // instantiate class
            Class<? extends AnalysisComponent> classz = null;
            // first prefix is for exact match, then 1st fallback is the generic
            // package name, then 2nd fallback correspond to packages
            // in bluima_utils
            String[] prefixes = { "", "ch.epfl.bbp.uima.ae.",
                    "ch.epfl.bbp.uima.ae.output.",
                    "ch.epfl.bbp.uima.ae.cleanup." };
            String tried = "";
            for (String prefix : prefixes) {

                try {
                    tried += prefix + aeName + " ";
                    classz = (Class<? extends AnalysisComponent>) Class
                            .forName(prefix + aeName);
                    break;
                } catch (Exception e1) {// nope
                }
            }
            if (classz == null)
                throw new ParseException("no class found on classpath: "
                        + aeName + ", tried: " + tried, -1);

            Pair<List<Object>, String> params = parseParams(it);
            validateParams(params.getKey(), classz);

            // create ae
            try {
                AnalysisEngineDescription aed = AnalysisEngineFactory
                        .createEngineDescription(classz, params.getKey()
                                .toArray());
                pipeline.addAe(aed);
                pipeline.addXml("<ae class=\"" + classz.getName() + "\">\n"
                        + params.getValue() + "</ae>\n");

            } catch (ResourceInitializationException e) {
                throw new ParseException(
                        "could not instantiate CollectionReader, error: "
                                + e.getMessage(), -1);
            }
        }
    }

    static Pair<List<Object>, String> parseParams(
            IteratorWithPrevious<String> it) throws ParseException {
        List<Object> params = new ArrayList<Object>();
        String xml = "";
        while (it.hasNext()) {
            String current = it.next();
            if (StringUtils.isBlank(current)) // end of parameters
                break;
            if (current.startsWith(" #")) // skip comments
                continue;
            if (!current.startsWith(" ")) {
                it.previous(); // end of parameters
                break;
            }

            int split = current.indexOf(": ");
            if (split == -1) {
                throw new ParseException(
                        "incorrect parameter line, should have a key, followed by a column, followed by a space, followed by a value: '"
                                + current + "'", -1);
            }
            String key = current.substring(0, split).trim();
            String valueStr = current.substring(split + 1).trim();
            xml += "<" + key + ">" + escapeXml(valueStr) + "</" + key + ">";

            Object value = null;
            if (key.endsWith("__java")) { // interpret as java code
                Interpreter i = new Interpreter();
                key = key.substring(0, key.length() - 6);
                try {
                    i.eval("value = " + valueStr + ";");
                    value = i.get("value");
                } catch (Exception e) {
                    throw new ParseException(
                            "cannot compile and run '__java' parameter with value '"
                                    + valueStr + "', error:" + e.toString(), -1);
                }
            } else { // a string

                // check if multiline
                if (valueStr.endsWith("\\")) {
                    value = valueStr.substring(0, valueStr.length() - 1) // rm \
                            + " " + parseNextLines(it);
                } else {
                    value = valueStr;
                }
            }
            LOG.info("  +-param. {}=>{}", key, value);

            params.add(key);
            params.add(value);
        }
        return new Pair<List<Object>, String>(params, xml);
    }

    private static String parseNextLines(IteratorWithPrevious<String> it) {
        if (it.hasNext()) {
            String nextLine = it.next().trim();
            if (nextLine.endsWith("\\")) {
                return nextLine.substring(0, nextLine.length() - 1) + " "
                        + parseNextLines(it);// recurse
            } else {
                return nextLine;
            }
        }
        return "";
    }
}
