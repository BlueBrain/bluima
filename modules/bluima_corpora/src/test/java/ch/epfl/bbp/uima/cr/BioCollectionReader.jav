package ch.epfl.bbp.uima.cr;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.collection.CollectionReader_ImplBase;
import org.apache.uima.examples.SourceDocumentInformation;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;

import ch.epfl.bbp.ResourceHelper;
import ch.epfl.bbp.uima.types.Protein;
import de.julielab.jules.types.Sentence;
import de.julielab.jules.types.Token;

/**
 * FIXME never tested
 */
public class BioCollectionReader extends CollectionReader_ImplBase {

    public static final String PARAM_DIRECTORY = "Directory";
    public static final String PARAM_READ_TEST_DATA = "ReadTestData";
    public static final String PARAM_SENTENCES_PER_DOCUMENT = "SentencesPerDocument";
    public static final String PARAM_TYPE_TO_BIO_SUFFIX_MAP = "TypeToBioSuffixMap";
    private static final String testCorpus = "nlpba/Genia4EReval1.iob2";
    private static final String trainingCorpus = "nlpba/Genia4ERtask1.iob2";

    private File userDirectory;
    private Map<String, Class<? extends Annotation>> suffixToClassMap = new HashMap<String, Class<? extends Annotation>>();
    private int processed = 0;
    private boolean released = false;
    private BufferedReader bufferedReader;
    private Integer sentencesPerDocument = Integer.valueOf(0x7fffffff);
    private File bioFiles[];
    private Boolean readTestData;

    public void initialize() throws ResourceInitializationException {
	// String typeToBioSuffixMapArray[] = (String[]) super.getUimaContext()
	// .getConfigParameterValue("TypeToBioSuffixMap");
	// if (typeToBioSuffixMapArray == null) {
	suffixToClassMap.put("protein", Protein.class);
	// } else {
	// for (int i = 0; i < typeToBioSuffixMapArray.length; i++) {
	// String map = typeToBioSuffixMapArray[i];
	// String split[] = map.split("\\,");
	// String typeName = split[0].trim();
	// try {
	// Class clazz = Class.forName(typeName);
	// String suffix = split[1].trim();
	// suffixToClassMap.put(suffix, clazz);
	// } catch (ClassNotFoundException e) {
	// e.printStackTrace();
	// }
	// }
	// }

	String directoryName = (String) getUimaContext()
		.getConfigParameterValue("Directory");
	if (directoryName != null) {
	    userDirectory = new File(directoryName);
	    if (userDirectory != null)
		if (!userDirectory.exists())
		    userDirectory = null;
		else if (!userDirectory.isDirectory())
		    userDirectory = null;
	}
	if (userDirectory == null) {
	    sentencesPerDocument = (Integer) getUimaContext()
		    .getConfigParameterValue("SentencesPerDocument");
	    if (sentencesPerDocument.intValue() <= 0)
		sentencesPerDocument = Integer.valueOf(0x7fffffff);
	    readTestData = (Boolean) getUimaContext().getConfigParameterValue(
		    "ReadTestData");
	    URL url;

	    try {
		if (readTestData.booleanValue())
		    url = ResourceHelper.getURL("nlpba/Genia4EReval1.iob2");
		else
		    url = ResourceHelper.getURL("nlpba/Genia4ERtask1.iob2");

		bufferedReader = new BufferedReader(new InputStreamReader(
			url.openStream()));
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	} else {
	    bioFiles = userDirectory.listFiles(new FilenameFilter() {

		public boolean accept(File dir, String name) {
		    return name.endsWith(".bio") || name.endsWith(".iob")
			    || name.endsWith(".BIO") || name.endsWith(".IOB");
		}

	    });
	}
    }

    public boolean hasNext() throws IOException, CollectionException {
	if (userDirectory == null)
	    return !released;
	return processed < bioFiles.length;
    }

    public void getNext(CAS acas) throws IOException, CollectionException {
	try {
	    JCas jcas = acas.getJCas();
	    SourceDocumentInformation sourceDocumentInformation = new SourceDocumentInformation(
		    jcas);
	    sourceDocumentInformation.addToIndexes();
	    if (userDirectory == null) {
		String prefix;
		if (readTestData.booleanValue())
		    prefix = "JNLPBA shared task test set ";
		else
		    prefix = "JNLPBA shared task training set ";
		sourceDocumentInformation.setUri((new StringBuilder(String
			.valueOf(prefix))).append(processed).toString());
	    } else {
		bufferedReader = new BufferedReader(new FileReader(
			bioFiles[processed]));
		sourceDocumentInformation.setUri(bioFiles[processed].getName());
	    }
	    processed++;
	    StringBuilder stringBuilder = new StringBuilder();
	    int namedEntityBegin = 0;
	    int namedEntityEnd = 0;
	    int sentenceBegin = 0;
	    String type = null;
	    int sentences = 0;
	    String line;
	    while ((line = bufferedReader.readLine()) != null) {
		if (line.equals("")) {
		    sentences++;
		    Sentence sentence = new Sentence(jcas);
		    sentence.addToIndexes();
		    sentence.setBegin(sentenceBegin);
		    sentence.setEnd(stringBuilder.length());
		    stringBuilder.append("\n");
		    sentenceBegin = stringBuilder.length();
		} else {
		    String strings[] = line.split("\\t");
		    if (strings.length != 2)
			continue;
		    int begin = stringBuilder.length();
		    int end = begin + strings[0].length();
		    stringBuilder.append((new StringBuilder(String
			    .valueOf(strings[0]))).append(" ").toString());
		    Token token = new Token(jcas);
		    token.addToIndexes();
		    token.setBegin(begin);
		    token.setEnd(end);
		    if (strings[1].equals("O")) {
			if (type != null) {
			    createNamedEntity(jcas, namedEntityBegin, type,
				    namedEntityEnd, suffixToClassMap);
			    type = null;
			}
		    } else if (strings[1].startsWith("B-")) {
			if (type != null) {
			    createNamedEntity(jcas, namedEntityBegin, type,
				    namedEntityEnd, suffixToClassMap);
			    type = null;
			}
			type = strings[1].substring(2);
			namedEntityBegin = begin;
			namedEntityEnd = end;
		    } else if (strings[1].startsWith("I-"))
			namedEntityEnd = end;
		}
		if (sentences > sentencesPerDocument.intValue())
		    break;
	    }
	    if (line == null) {
		released = true;
		bufferedReader.close();
	    }
	    jcas.setDocumentText(stringBuilder.toString());
	    sourceDocumentInformation.setBegin(0);
	    sourceDocumentInformation.setEnd(stringBuilder.length() - 1);
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	} catch (CASException e) {
	    e.printStackTrace();
	}
    }

    public static void createNamedEntity(JCas jcas, int namedEntityBegin,
	    String type, int end, Map suffixToClassMap) {
	Class entityClass = (Class) suffixToClassMap.get(type);
	if (entityClass != null)
	    try {
		Annotation annotation = (Annotation) entityClass
			.getConstructor(JCas.class).newInstance(
				new Object[] { jcas });
		annotation.addToIndexes();
		annotation.setBegin(namedEntityBegin);
		annotation.setEnd(end);
	    } catch (SecurityException e) {
		e.printStackTrace();
	    } catch (NoSuchMethodException e) {
		e.printStackTrace();
	    } catch (IllegalArgumentException e) {
		e.printStackTrace();
	    } catch (InstantiationException e) {
		e.printStackTrace();
	    } catch (IllegalAccessException e) {
		e.printStackTrace();
	    } catch (InvocationTargetException e) {
		e.printStackTrace();
	    }
    }

    public Progress[] getProgress() {
	return null;
    }

    public static void main(String args[]) {
	File bioFile = new File(
		"C:/Users/kano/Downloads/Genia4ERtraining.tar/Genia4ERtrain/Genia4ERtask1.iob2");
	File outFile = new File("C:/acl2009/NLPBA-all.bio");
	try {
	    BufferedReader bufferedReader = new BufferedReader(new FileReader(
		    bioFile));
	    StringBuilder stringBuilder = new StringBuilder();
	    String line;
	    while ((line = bufferedReader.readLine()) != null)
		if (line.equals("")) {
		    stringBuilder.append("\n");
		} else {
		    String strings[] = line.split("\\t");
		    stringBuilder.append((new StringBuilder(String
			    .valueOf(strings[0]))).append("|")
			    .append(strings[1]).append(" ").toString());
		}
	    bufferedReader.close();
	    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(
		    outFile));
	    bufferedWriter.write(stringBuilder.toString());
	    bufferedWriter.close();
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    @Override
    public void close() throws IOException {
	// TODO Auto-generated method stub

    }
}
