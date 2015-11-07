import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * Created by fillinger on 11/7/15.
 */
public class ClusterViewerController implements IClusterViewerNotifications{

    private ClusterViewerModel model;
    private ClusterViewerView view;

    public ClusterViewerController(ClusterViewerModel model, ClusterViewerView view){
        this.model = model;
        this.view = view;
    }


    public Scene buildScene(){
        return this.view.buildScene();
    }

    @Override
    public void printConsoleStatus(String message) {
        System.out.println(message);
    }

    @Override
    public void printConsoleError(String message) {
        System.err.println(message);
    }

    @Override
    public void printStatusDialog(String message) {
        this.view.setAlertWindow(new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK));
    }

    @Override
    public void printStatusError(String message) {
        this.view.setAlertWindow(new Alert(Alert.AlertType.ERROR, message, ButtonType.OK));
    }




}
