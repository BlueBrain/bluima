package ch.epfl.bbp.uima.typesystem;

import static org.junit.Assert.fail;

import java.lang.reflect.Field;

import org.junit.Test;

public class TypeSystemTest {

    /**
     * Smoketest that uses reflexion to test that all static fields in
     * {@link TypeSystem} are effectively pointing to valid classes (that are
     * subclasses of AnnotationBase)
     */
    @Test
    @SuppressWarnings("unused")
    public void testStaticFields() throws Exception {

        Field[] fields = TypeSystem.class.getFields();
        for (Field field : fields) {
            if (field.getType().equals(String.class)) {
                String value = (String) field.get(null);

                // try to load the class
                Class<?> clasz = null;
                try {

                    clasz = Class.forName(value);
                    //System.out.println("testing" + clasz.getName());
                } catch (Exception e) {
                    throw new RuntimeException("cannot load class " + value);
                }

                while (true) {

                    Class<?> superclass = clasz.getSuperclass();
                    // System.out
                    //        .println("  |-superclass " + superclass.getName());
                    if (superclass.getName().equals(
                            "org.apache.uima.jcas.cas.AnnotationBase")) {
                        break; // free, otherwise loop
                    } else if (superclass == null) {
                        fail(value
                                + " should be an instance of AnnotationBase (I could not find it, but it is defined in TypeSystem.java)");
                    }
                    clasz = superclass;
                }
            }
        }
    }
}
