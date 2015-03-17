package ch.epfl.bbp.nlp;

public class ModelProxyException extends Exception {

    public ModelProxyException(String message, Throwable cause) {
        super("[ModelProxy] " + message, cause);
    }

    public ModelProxyException(String message) {
        super("[ModelProxy] " + message);
    }

    private static final long serialVersionUID = -7027912266995054618L;

}
