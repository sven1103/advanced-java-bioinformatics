package controllers;

/**
 * Created by fillinger on 11/7/15.
 */
public interface IClusterViewerNotifications {

    /**
     * Prints status message on the console
     * @param message
     */
    void printConsoleStatus(String message);

    /**
     * Prints error message on the console
     * @param message
     */
    void printConsoleError(String message);

    /**
     * Shows status notification dialog
     * @param message
     */
    void printStatusDialog(String message);

    /**
     * Shows error notification dialog
     * @param message
     */
    void printStatusError(String message);
}
