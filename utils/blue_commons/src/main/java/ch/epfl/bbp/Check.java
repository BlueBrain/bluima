package ch.epfl.bbp;

public class Check {

    public static void nonnNull(Object input,
            @SuppressWarnings("rawtypes") Class classz) {
        if (input == null || !(classz.isInstance(input))) {
            throw new IllegalArgumentException();
        }
    }

    public static <T> T checkNotNull(T reference, String errorMessage) {
        if (reference == null) {
            throw new NullPointerException(errorMessage);
        }
        return reference;
    }

    public static void checkArgument(boolean expression, String errorMessage) {
        if (!expression) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

}
