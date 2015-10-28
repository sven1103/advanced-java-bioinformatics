package visualization;

/**
 * Created by fillinger on 10/27/15.
 */
public class Settings {
    /**
     * Init showHeaders: show by default
     */
    private static boolean showHeaders = true;
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

    /**
     * Setter methods
     * @return
     */
    public static void selectAll(){
        showHeaders = true;
        showSequence = true;
        showNumbering = true;
    }

    public static void clearAll(){
        showHeaders = false;
        showSequence = false;
        showNumbering = false;
    }
}