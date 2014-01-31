package ch.epfl.bbp.uima.utils;

import static ch.epfl.bbp.uima.utils.StaticOption.Type.bool;
import static ch.epfl.bbp.uima.utils.StaticOption.Type.dble;
import static ch.epfl.bbp.uima.utils.StaticOption.Type.integer;
import static ch.epfl.bbp.uima.utils.StaticOption.Type.string;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.Maps.newLinkedHashMap;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static org.apache.commons.lang.StringUtils.join;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javatools.datatypes.Pair;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.uima.ae.GridSearchConfiguration;
import ch.epfl.bbp.uima.ae.StaticConfiguration;

/**
 * @see GridSearchConfiguration
 * @see StaticConfiguration
 * 
 * @author renaud.richardet@epfl.ch
 */
public class StaticOption {
    private static Logger LOG = LoggerFactory.getLogger(StaticOption.class);

    public enum Type {
        bool, integer, dble, string
    };

    /** a map of all possible, available options */
    private static Map<String, Pair<Type, Object[]>> optionsDefinitions = newLinkedHashMap();
    /**
     * a map that contains the currently set option, and/or the current one for
     * this COMBINAISON_INDEX
     */
    private static Map<String, Pair<Type, Object>> choosenOption = newLinkedHashMap();

    private static int maxCombinaisonIndex = Integer.MIN_VALUE;

    public static void parseOptions(List<String> optionsStr) {

        maxCombinaisonIndex = 1;
        for (int i = 0; i < optionsStr.size(); i++) {

            String[] split = optionsStr.get(i).split(" ");
            checkArgument(split.length > 1, "Param o" + i
                    + " should have at least paramName{space}paramType");
            checkArgument(split.length < 99, "Param o" + i
                    + ": too many options");
            String name = split[0], type = split[1];
            if (type.equals(bool.name())) {
                optionsDefinitions.put(name, new Pair<Type, Object[]>(bool,
                        new Object[] { true, false }));
                maxCombinaisonIndex *= 2;
                LOG.info("static option '{}' ('o{}') with type '{}' added",
                        new Object[] { name, i + 1, type });

            } else if (type.equals(integer.name())) {
                Object[] optionsValues = new Object[split.length - 2];
                for (int j = 2; j < split.length; j++) {
                    optionsValues[j - 2] = parseInt(split[j]);
                }
                optionsDefinitions.put(name, new Pair<Type, Object[]>(integer,
                        optionsValues));
                maxCombinaisonIndex *= optionsValues.length;
                LOG.info(
                        "static option '{}' ('o{}') with type '{}' added with options '{}'",
                        new Object[] { name, i + 1, type,
                                join(optionsValues, ", ") });

            } else if (type.equals(dble.name())) {
                Object[] optionsValues = new Object[split.length - 2];
                for (int j = 2; j < split.length; j++) {
                    optionsValues[j - 2] = parseDouble(split[j]);
                }
                optionsDefinitions.put(name, new Pair<Type, Object[]>(dble,
                        optionsValues));
                maxCombinaisonIndex *= optionsValues.length;
                LOG.info(
                        "static option '{}' ('o{}') with type '{}' added with options '{}'",
                        new Object[] { name, i + 1, type,
                                join(optionsValues, ", ") });

            } else if (type.equals(string.name())) {
                Object[] optionsValues = new Object[split.length - 2];
                for (int j = 2; j < split.length; j++) {
                    optionsValues[j - 2] = split[j];
                }
                optionsDefinitions.put(name, new Pair<Type, Object[]>(string,
                        optionsValues));
                maxCombinaisonIndex *= optionsValues.length;
                LOG.info(
                        "static option '{}' ('o{}') with type '{}' added with options '{}'",
                        new Object[] { name, i + 1, type,
                                join(optionsValues, " ,") });

            } else {
                throw new IllegalArgumentException("no paramType " + type);
            }
        }

    }

    public static Map<String, Pair<Type, Object>> setChoosenOption(
            int combinaisonIndex) {
        // choosenOption = newLinkedHashMap();// reset?

        // from combinaisonIndex, find current option
        int ci = combinaisonIndex;
        // LOG.info("combinaisonIndex: " + combinaisonIndex);
        int totalCombinaisons = 1;
        for (Pair<Type, Object[]> v : optionsDefinitions.values()) {
            totalCombinaisons *= v.second.length;
        }
        if (ci > totalCombinaisons)
            throw new IllegalArgumentException(
                    "combinaisonIndex too large, max is "
                            + (totalCombinaisons - 1));

        for (Entry<String, Pair<Type, Object[]>> optionEntry : optionsDefinitions
                .entrySet()) {
            String name = optionEntry.getKey();
            Type type = optionEntry.getValue().first;
            Object[] options = optionEntry.getValue().second;

            int l = totalCombinaisons / options.length;
            int whichBin = ci / l;
            ci = ci % l;

            totalCombinaisons = totalCombinaisons / options.length;

            choosenOption.put(name, new Pair<Type, Object>(type,
                    options[whichBin]));
        }
        return choosenOption;
    }

    public static Pair<Type, Object[]> getOptionDefinition(String name) {
        return optionsDefinitions.get(name);
    }

    public static void print() {
        String msg = "choosen static options: ";
        for (Entry<String, Pair<Type, Object>> e : choosenOption.entrySet()) {
            msg += e.getKey() + "::" + e.getValue().second + " ";
        }
        LOG.info(msg);
    }

    public static void reset() {
        optionsDefinitions = newLinkedHashMap();
        choosenOption = newLinkedHashMap();
    }

    public static int getMaxCombinaisonIndex() {
        return maxCombinaisonIndex;
    }

    public static Object get(String key, Type type) {
        if (!choosenOption.containsKey(key))
            throw new IllegalArgumentException("no key " + key);
        Pair<Type, Object> pair = choosenOption.get(key);
        if (pair.first.equals(type))
            return pair.second;
        else
            throw new IllegalArgumentException("key " + key
                    + " has wrong type: " + pair.first);
    }

    public static boolean getBoolean(String key) {
        return (Boolean) get(key, Type.bool);
    }

    public static void setBoolean(String key, boolean value) {
        choosenOption.put(key, new Pair<Type, Object>(bool, value));
    }

    public static int getInt(String key) {
        return (Integer) get(key, Type.integer);
    }

    public static void setInt(String key, int value) {
        choosenOption.put(key, new Pair<Type, Object>(integer, value));
    }

    public static double getDouble(String key) {
        return (Double) get(key, Type.dble);
    }

    public static void setDouble(String key, double value) {
        choosenOption.put(key, new Pair<Type, Object>(dble, value));
    }

    public static String getString(String key) {
        return (String) get(key, Type.string);
    }

    public static void setString(String key, String value) {
        choosenOption.put(key, new Pair<Type, Object>(string, value));
    }

    public static void parseConfiguration(List<String> optionsStr) {
        for (String configParameter : optionsStr) {

            String[] split = configParameter.split(" ");
            checkArgument(
                    split.length > 1,
                    "Param "
                            + configParameter
                            + " should have the format paramName{space}paramType{space}value");
            checkArgument(
                    split.length <= 3,
                    "Param "
                            + configParameter
                            + " should have the format paramName{space}paramType{space}value");

            String name = split[0], type = split[1], value = split[2];
            LOG.info("static option '{}' of type '{}' set to value '{}'",
                    new Object[] { name, type, value });

            if (type.equals(bool.name())) {
                StaticOption.setBoolean(name, value.equals("true"));

            } else if (type.equals(integer.name())) {
                StaticOption.setInt(name, parseInt(value));

            } else if (type.equals(dble.name())) {
                StaticOption.setDouble(name, parseDouble(value));

            } else if (type.equals(string.name())) {
                StaticOption.setString(name, value);

            } else {
                throw new IllegalArgumentException("no paramType " + type);
            }
        }
    }
}
