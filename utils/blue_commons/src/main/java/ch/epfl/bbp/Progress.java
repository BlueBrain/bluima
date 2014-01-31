package ch.epfl.bbp;

/**
 * Basic util to print progress dots on the console. Prints a new line after 80
 * dots. Caveats: this is static, and therefore shared...
 * 
 * @author renaud.richardet@epfl.ch
 */
public class Progress {

    private static int i = 0;

    public static void reinit() {
        i = 0;
    }

    /** Add a dot. Prints a new line after 80 dots. */
    public static void tick() {
        if (++i % 80 == 0)
            System.out.println("∎");
        else
            System.out.print("∎");
    }
}
