package ch.epfl.bbp.uima.ae;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;


/**
 * https://code.google.com/p/biomedner/source/browse/trunk/biomedner/src/bioner/
 * tools/enju/?r=274
 */
public class EnjuResultFileReader {
//	public static HashMap<String, Vector<EnjuRelation>> readEnjuResultFiles() {
//		return readEnjuResultFiles(GlobalConfig.ENJU_BC2GN_ALLSENTENCES,
//				GlobalConfig.ENJU_BC2GN_PARSED);
//	}
//
//	public static HashMap<String, Vector<EnjuRelation>> readEnjuResultFiles(
//			String allSentenceFile, String parsedFile) {
//		HashMap<String, Vector<EnjuRelation>> map = new HashMap<String, Vector<EnjuRelation>>();
//		try {
//			BufferedReader freader = new BufferedReader(new FileReader(
//					allSentenceFile));
//			BufferedReader relationReader = new BufferedReader(new FileReader(
//					parsedFile));
//			String line;
//			Vector<EnjuRelation> relationVector;
//			Vector<String> sentenceVector = new Vector<String>();
//			while ((line = freader.readLine()) != null
//					&& (relationVector = readEnjuRelations(relationReader)) != null) {
//				if (line.length() > 0) {
//					sentenceVector.add(line);
//					map.put(line, relationVector);
//				}
//			}
//			freader.close();
//			relationReader.close();
//
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		return map;
//	}
//
//	private static Vector<EnjuRelation> readEnjuRelations(BufferedReader freader)
//			throws IOException {
//		String line = freader.readLine();
//		if (line == null)
//			return null;
//		Vector<EnjuRelation> relationVector = new Vector<EnjuRelation>();
//		HashMap<Integer, EnjuRelation> relationMap = new HashMap<Integer, EnjuRelation>();
//		while (line != null && line.length() > 0) {
//			if (line.startsWith("ROOT")) {
//				line = freader.readLine();
//				continue;
//			}
//			if (line.startsWith("Parsing failure:")) {
//				return relationVector;
//			}
//			String[] parts = line.split("\\t");
//			int index = Integer.parseInt(parts[4]);
//			EnjuRelation relation = relationMap.get(index);
//			if (relation == null) {
//				relation = new EnjuRelation();
//				relationMap.put(index, relation);
//				relationVector.add(relation);
//			}
//			relation.addLine(line);
//			line = freader.readLine();
//		}
//
//		return relationVector;
//	}
//
//	public static void main(String[] args) {
//		HashMap<String, Vector<EnjuRelation>> map = readEnjuResultFiles();
//		String sentence = "In contrast , PTHrP interaction with the cell surface PTH\\/PTHrP receptor resulted in decreased cell proliferation in the same cell line .";
//		Vector<EnjuRelation> relationVector = map.get(sentence);
//		for (EnjuRelation relation : relationVector) {
//			System.out.println(relation.toString());
//		}
//	}
}
