import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;


/**
 * Created by fillinger on 11/7/15.
 */
public class ClusterViewerView{
    private Alert alertWindow;

    public Scene buildScene() {
        System.out.println(Platform.isFxApplicationThread());
        StackPane root = new StackPane();

        Label label = new Label("Juhuuu geht");

        root.getChildren().add(label);

        return new Scene(root, 300, 200);

    }

    public Alert getAlertWindow(){
        return this.alertWindow;
    }

    public void setAlertWindow(Alert alert){
        this.alertWindow = alert;
        this.alertWindow.showAndWait();
    }

    public ButtonType getAlertWindowResult() throws NullPointerException{
        return alertWindow.getResult();
    }



}
