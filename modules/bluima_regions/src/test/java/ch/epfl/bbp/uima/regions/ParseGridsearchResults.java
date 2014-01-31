package ch.epfl.bbp.uima.regions;

import static ch.epfl.bbp.uima.utils.StaticOption.getBoolean;
import static ch.epfl.bbp.uima.utils.StaticOption.getInt;
import static ch.epfl.bbp.uima.utils.StaticOption.parseOptions;
import static ch.epfl.bbp.uima.utils.StaticOption.setChoosenOption;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newTreeMap;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javatools.datatypes.Pair;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import ch.epfl.bbp.io.SVReader.CSVReader;
import ch.epfl.bbp.uima.utils.StaticOption;
import ch.epfl.bbp.uima.utils.StaticOption.Type;

public class ParseGridsearchResults {

    public static void main(String[] args) throws Exception {

        File f = new File(
                "src/test/resources/eval/20130520_gridsearch_evaluation_time.csv");
//    "src/test/resources/eval/20130520_gridsearch_evaluation_results.csv");

        Map<Integer, DescriptiveStatistics> sampleMap = newTreeMap();

        for (List<String> entry : new CSVReader(f, true)) {
            // combinaisonIndex,repetition,fold,p,r,f1

            int combinaisonIndex = parseInt(entry.get(0));
            checkArgument(combinaisonIndex > -1);
            // p,r or F1
///            double value = Double.parseDouble(entry.get(5));
            double value = parseTime(entry.get(2));
            checkArgument(value > 0d);

            if (!sampleMap.containsKey(combinaisonIndex)) {
                DescriptiveStatistics ds = new DescriptiveStatistics();
                ds.addValue(value);
                sampleMap.put(combinaisonIndex, ds);
            } else {
                DescriptiveStatistics ds = sampleMap.get(combinaisonIndex);
                ds.addValue(value);
                sampleMap.put(combinaisonIndex, ds);
            }
        }

        for (Entry<Integer, DescriptiveStatistics> entry : sampleMap.entrySet()) {

            DescriptiveStatistics stat = entry.getValue();

            System.out.println(entry.getKey() + ": " + stat.getMean() + " ["
                    + stat.getN() + "]");
        }

        List<String> optionStrs = newArrayList(
                "Window integer 0 1 2",
                "Lemma bool",
                "TextPipe bool",
                "SubstringRegexPipes bool",
                "SmallLexicons_TextPressoPipes_BrainRegionLexicons_AbbreviationLexiconPipes_AreaLexicons bool",
                "HandMadeRegexPipes_MalletNEPipes bool");
        parseOptions(optionStrs);

        final String o1 = "SubstringRegexPipes";
        final String o2 = "SmallLexicons_TextPressoPipes_BrainRegionLexicons_AbbreviationLexiconPipes_AreaLexicons";
        final String o3 = "HandMadeRegexPipes_MalletNEPipes";
        final String o4 = "Window";
        final String o5 = "Lemma";
        final String o6 = "TextPipe";

        for (int i = 0; i < StaticOption.getMaxCombinaisonIndex(); i++) {

            System.out.print(i + "\t");
            for (Entry<String, Pair<Type, Object>> option : setChoosenOption(i)
                    .entrySet()) {
                System.out.print(option.getValue().second + "\t");
            }
            System.out.println(sampleMap.get(i).getMean());
        }
        System.out.println("\n$$$$$$$$$$$$$$$$$$$");

//        for (String optionVal4 : optionsValsFor(o4)) {
//            System.out.print(optionVal4 + "\t\t\t\n");
//            for (String optionVal5 : optionsValsFor(o5)) {
//                System.out.print(optionVal5 + "\t\t\n");
//                for (String optionVal6 : optionsValsFor(o6)) {
//                    System.out.print(optionVal6 + "\t");
//                }
//                System.out.print("\n");
//            }
//        }
//        System.out.println("\n------------------");
//
//        for (String optionVal1 : optionsValsFor(o1)) {
//            // System.out.print(optionVal1 + "\t");
//            for (String optionVal2 : optionsValsFor(o1)) {
//                // System.out.print(optionVal2 + "\t");
//                for (String optionVal3 : optionsValsFor(o3)) {
//                    // System.out.print(optionVal3 + "\t");
//
//                    for (String optionVal4 : optionsValsFor(o4)) {
//                        for (String optionVal5 : optionsValsFor(o5)) {
//                            for (String optionVal6 : optionsValsFor(o6)) {
//
//                                for (int i = 0; i < StaticOption
//                                        .getMaxCombinaisonIndex(); i++) {
//
//                                    setChoosenOption(i);
//
//                                    boolean a1 = optionVal1
//                                            .equals(getBoolean(o1) + "");
//                                    boolean a2 = optionVal2
//                                            .equals(getBoolean(o2) + "");
//                                    boolean a3 = optionVal3
//                                            .equals(getBoolean(o3) + "");
//                                    boolean a4 = optionVal4.equals(getInt(o4)
//                                            + "");
//                                    boolean a5 = optionVal5
//                                            .equals(getBoolean(o5) + "");
//                                    boolean a6 = optionVal6
//                                            .equals(getBoolean(o6) + "");
//
//                                    if (a1 && a2 && a3 && a4 && a5 && a6) {
//                                        System.out.print(sampleMap.get(i)
//                                                .getMean() + "\t");
//                                    }
//                                }
//
//                            }
//                        }
//                    }
//                    System.out.print("\n");
//                }
//            }
//        }

    }

    private static double parseTime(String inputDate) {
        String[] split = inputDate.split(":");
        int hours = Integer.valueOf(split[0]);
        int minutes = Integer.valueOf(split[1]);
        int seconds = Integer.valueOf(split[2]);
        return hours * 3600+ minutes * 60 + seconds;
    }

    private static List<String> optionsValsFor(String optionName) {
        Pair<Type, Object[]> optionDefinition = StaticOption
                .getOptionDefinition(optionName);
        List<String> optionValues = newArrayList();
        for (Object optionValue : optionDefinition.second) {
            optionValues.add(optionValue.toString());
        }
        return optionValues;
    }

}
