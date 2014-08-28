package ch.epfl.bbp;

public class CheckThat {

    public static void itIsANonNull(Object input,
            @SuppressWarnings("rawtypes") Class classz) {
        if (input == null || !(classz.isInstance(input))) {
            throw new IllegalArgumentException();
        }
    }

}
