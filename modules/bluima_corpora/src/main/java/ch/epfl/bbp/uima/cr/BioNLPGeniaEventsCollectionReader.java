package ch.epfl.bbp.uima.cr;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.uima.UimaContext;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.FSArray;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.uima.fit.component.JCasCollectionReader_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;

import ch.epfl.bbp.ResourceHelper;
import ch.epfl.bbp.StringUtils;
import ch.epfl.bbp.io.SVReader;
import ch.epfl.bbp.uima.BlueUima;
import ch.epfl.bbp.uima.genia.Event;
import ch.epfl.bbp.uima.types.Protein;
import de.julielab.jules.types.BioEntityMention;

/**
 * Corpus reader for the BioNLP 2011 GENIA Event Extraction (GENIA) Shared Task
 * http://2011.bionlp-st.org/home/genia-event-extraction-genia<br>
 * Format:
 * http://www-tsujii.is.s.u-tokyo.ac.jp/GENIA/SharedTask/detail.shtml#format
 * 
 * @author renaud.richardet@epfl.ch
 */
public class BioNLPGeniaEventsCollectionReader extends
	JCasCollectionReader_ImplBase {
    Logger LOG = LoggerFactory
	    .getLogger(BioNLPGeniaEventsCollectionReader.class);

    public final static String DEVDATA_DIR = "pear_resources/BioNLP-ST_2011/BioNLP-ST_2011_genia_devel_data_rev1";
    public final static String TESTDATA_DIR = "pear_resources/BioNLP-ST_2011/BioNLP-ST_2011_genia_test_data/";
    public final static String TRAINDATA_DIR = "pear_resources/BioNLP-ST_2011/BioNLP-ST_2011_genia_train_data_rev1/";

    private static final String EXTENSION = "txt";

    @ConfigurationParameter(name = BlueUima.PARAM_INPUT_DIRECTORY, defaultValue = DEVDATA_DIR)
    private String inputDirectory;

    private Iterator<File> directoryIterator;

    @SuppressWarnings("unchecked")
    @Override
    public void initialize(UimaContext context)
	    throws ResourceInitializationException {
	super.initialize(context);

	try {
	    File dir = ResourceHelper.getFile(inputDirectory);

	    directoryIterator = FileUtils.iterateFiles(dir,
		    new String[] { EXTENSION }, true);

	} catch (Exception e) {
	    LOG.debug(StringUtils.print(e));
	    throw new ResourceInitializationException(
		    ResourceInitializationException.NO_RESOURCE_FOR_PARAMETERS,
		    new Object[] { inputDirectory }, e);
	}
    }

    @Override
    public void getNext(JCas jcas) throws IOException, CollectionException {

	// read all 3 files
	File textFile = directoryIterator.next();
	File annotationFile1 = new File(textFile.getParentFile()
		.getAbsoluteFile()
		+ File.separator
		+ textFile.getName().replace(EXTENSION, "") + "a1");
	File annotationFile2 = new File(textFile.getParentFile()
		.getAbsoluteFile()
		+ File.separator
		+ textFile.getName().replace(EXTENSION, "") + "a2");
	if (!annotationFile1.exists() || !annotationFile2.exists()) {
	    throw new IOException(
		    "cannot find corresponding .a1 and .a2 files for "
			    + textFile.getAbsolutePath());
	}

	// parse files and set in jcas
	jcas.setDocumentText(FileUtils.readFileToString(textFile));

	Map<String, Protein> proteinMap = new HashMap<String, Protein>();
	Map<String, Event> eventMap = new HashMap<String, Event>();

	// .a1 protein annotations
	for (List<String> line : new SVReader(annotationFile1, false,
		SVReader.TAB)) {
	    // e.g. T1 Protein 12 17 RUNX1
	    String[] split = line.get(1).split(" ");
	    Protein prot = new Protein(jcas, new Integer(split[1]),
		    new Integer(split[2]));
	    prot.setId(line.get(0));
	    prot.setName(line.get(2));
	    prot.addToIndexes();
	    proteinMap.put(line.get(0), prot);
	}

	// .a2 events annotations
	for (List<String> line : new SVReader(annotationFile2, false,
		SVReader.TAB)) {

	    String idStr = line.get(0);
	    if (idStr.startsWith("T")) {
		// e.g. T91 Positive_regulation 378 391 up-regulation
		String[] split = line.get(1).split(" ");
		BioEntityMention em = new BioEntityMention(jcas, new Integer(
			split[1]), new Integer(split[2]));
		em.setId(idStr);
		em.setSpecificType(line.get(1));
		em.setTextualRepresentation(line.get(2));
		em.addToIndexes();

	    } else if (idStr.startsWith("E")) {
		// e.g. E1 Positive_regulation:T91 Theme:E2 Cause:T9
		Event event = new Event(jcas);
		event.setId(idStr);

		String[] type = line.get(1).split(":");
		event.setEvent_type(type[0]);

		List<Event> causeEvents = new ArrayList<Event>();
		List<Protein> causeProts = new ArrayList<Protein>();
		List<Event> themeEvents = new ArrayList<Event>();
		List<Protein> themeProts = new ArrayList<Protein>();

		// parse each arguments
		for (int i = 2; i < line.size(); i++) {
		    String[] arg = line.get(i).split(":");
		    String id = arg[1];

		    if (arg[0].equals("Theme")) {
			if (id.startsWith("E")) {
			    themeEvents.add(eventMap.get(id));
			} else if (id.startsWith("T")) {
			    themeProts.add(proteinMap.get(id));
			}

		    } else if (arg[0].equals("Cause")) {
			if (id.startsWith("E")) {
			    causeEvents.add(eventMap.get(id));
			} else if (id.startsWith("T")) {
			    causeProts.add(proteinMap.get(id));
			}
		    }
		}

		FSArray causeEventsA = new FSArray(jcas, causeEvents.size());
		for (int i = 0; i < causeEvents.size(); i++) {
		    causeEventsA.set(i, causeEvents.get(i));
		}
		event.setCauses_event(causeEventsA);

		FSArray causeProtsA = new FSArray(jcas, causeProts.size());
		for (int i = 0; i < causeProts.size(); i++) {
		    causeProtsA.set(i, causeProts.get(i));
		}
		event.setCauses_protein(causeProtsA);

		FSArray themeEventsA = new FSArray(jcas, themeEvents.size());
		for (int i = 0; i < themeEvents.size(); i++) {
		    themeEventsA.set(i, themeEvents.get(i));
		}
		event.setThemes_event(themeEventsA);

		FSArray themeProtsA = new FSArray(jcas, themeProts.size());
		for (int i = 0; i < themeProts.size(); i++) {
		    themeProtsA.set(i, themeProts.get(i));
		}
		event.setThemes_event(themeProtsA);

		event.addToIndexes();
		eventMap.put(idStr, event);
	    } else if (idStr.startsWith("M")) {
		LOG.debug("not implemented yet: {}", line);
	    }
	}
    }

    public boolean hasNext() throws IOException, CollectionException {
	return directoryIterator.hasNext();
    }

    public Progress[] getProgress() {// nope
	return null;
    }

    @Override
    public void close() throws IOException {// nope
    }
}
