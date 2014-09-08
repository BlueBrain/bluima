package ch.epfl.bbp.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class Create {

    public static <E> ArrayList<E> list() {
        return new ArrayList<E>();
    }

    public static <E> ArrayList<E> list(E... elements) {
        ArrayList<E> list = new ArrayList<E>(elements.length);
        Collections.addAll(list, elements);
        return list;
    }

    public static <E> HashSet<E> set() {
        return new HashSet<E>();
    }

    public static <E> HashSet<E> set(E... elements) {
        HashSet<E> set = new HashSet<E>(elements.length);
        Collections.addAll(set, elements);
        return set;
    }

    public static <K, V> HashMap<K, V> map() {
        return new HashMap<K, V>();
    }
}
