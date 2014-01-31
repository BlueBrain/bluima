package ch.epfl.bbp.uima.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.uima.jcas.tcas.Annotation;

/**
 * @author renaud.richardet@epfl.ch
 * 
 * @param <T>
 */
public class AlternateTestEvaluator<T extends Annotation> {
   
    private int expectedCnt = 0;
    private int actualCnt = 0;
    private int truePositives = 0;
    private int falsePositives = 0;

    private static class Alternatives<T extends Annotation> {
	List<T> altForms = new ArrayList<T>();
	int min = 0, max = 0;

	public Alternatives(T firstElement) {
	    altForms.add(firstElement);
	    min = firstElement.getBegin();
	    max = firstElement.getEnd();
	}

	boolean canFitIn(T t) {// just draw it :-)
	    if ((t.getBegin() < max && t.getEnd() > min)
		    || (t.getEnd() > min && t.getBegin() < max))
		return true;
	    return false;
	}

	void add(T t) {
	    altForms.add(t);
	    min = Math.min(min, t.getBegin());
	    max = Math.max(max, t.getEnd());
	}

	boolean matches(T t) {
	    boolean matches = false;
	    for (T alt : altForms) {
		if (alt.getBegin() == t.getBegin()
			&& alt.getEnd() == t.getEnd()) {
		    matches = true;
		    break;
		}
	    }
	    return matches;
	}

	@Override
	public String toString() {
	    String list = " ";
	    for (T t : altForms) {
		list += t.getCoveredText() + "[" + t.getBegin() + "-"
			+ t.getEnd() + "] ";
	    }
	    return "min:" + min + " max:" + max + list;
	}
    }

    public String add(Collection<T> expecteds, Collection<T> actuals) {
	StringBuilder sb = new StringBuilder();

	// builds alternative-lists
	List<Alternatives<T>> altExpecteds = new LinkedList<AlternateTestEvaluator.Alternatives<T>>();
	for (T expected : expecteds) {
	    boolean isAltForm = false;
	    for (Alternatives<T> alternative : altExpecteds) {
		if (alternative.canFitIn(expected)) {
		    isAltForm = true;
		    alternative.add(expected);
		    break;
		}
	    }
	    if (!isAltForm) {
		altExpecteds.add(new Alternatives<T>(expected));
	    }
	}

	int _truePositives = 0;
	for (Alternatives<T> altExpected : altExpecteds) {
	    for (T actual : actuals) {
		if (altExpected.matches(actual)) {
		    _truePositives++;
		    break;// external_loop;
		}
	    }
	}
	int _falsePositives = 0;
	for (T actual : actuals) {
	    boolean _localFP = false;
	    for (Alternatives<T> altExpected : altExpecteds) {
		if (altExpected.matches(actual)) {
		    _localFP = true;
		    break;// external_loop;
		}
	    }
	    if (!_localFP == true) {
		_falsePositives++;
		sb.append("fp: " + actual.getCoveredText());
	    }
	}

	truePositives += _truePositives;
	falsePositives += _falsePositives;
	expectedCnt += altExpecteds.size();
	actualCnt += actuals.size();
	sb.append("--> tp:" + _truePositives + " fp:" + _falsePositives);
	return sb.toString();
    }

    public TestResult compare() {
	if (expectedCnt == 0 || actualCnt == 0) {
	    return new TestResult(0, 0, 0, 0);
	}

	double precision = (truePositives + 0d)
		/ (truePositives + falsePositives + 0d);
	double recall = (truePositives + 0d) / (expectedCnt + 0d);

	return new TestResult(expectedCnt, actualCnt, precision, recall);
    }
}
