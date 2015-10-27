package visualization;

/**
 * Created by fillinger on 10/27/15.
 */
public class Settings {
    /**
     * Init showHeaders: show by default
     */
    private static boolean showHeaders = false;
    /**
     * Init showSequence: show by default
     */
    private static boolean showSequence = true;
    /**
     * Init showNumbering: show by default
     */
    private static boolean showNumbering = true;

    /**
     * Toogle functions for the settings.
     */
    public static void toogleHeaders(){
        showHeaders = showHeaders ? false : true;
    }

    public static void toogleSequence(){
        showSequence = showSequence ? false : true;
    }

    public static void toogleNumbering(){
        showNumbering = showNumbering ? false : true;
    }

    /**
     * Getter methods
     * @return
     */
    public static boolean showHeaders(){
        return showHeaders;
    }

    public static boolean showSequence(){
        return showSequence;
    }

    public static boolean showNumbering(){
        return showNumbering;
    }
}
