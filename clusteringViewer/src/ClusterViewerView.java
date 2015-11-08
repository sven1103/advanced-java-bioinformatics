import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;


/**
 * Created by fillinger on 11/7/15.
 */
public class ClusterViewerView{
    private Alert alertWindow;
    private BorderPane root;
    private Menu menu;
    private MenuBar menuBar;
    private MenuItem openMenuI;
    private MenuItem exitMenuI;

    private FileChooser fileChooser;



    public Scene buildScene() {
        System.out.println(Platform.isFxApplicationThread());
        BorderPane root = new BorderPane();

        menu = new Menu("File");
        openMenuI = new MenuItem("Open File");
        exitMenuI = new MenuItem("Exit");

        menu.getItems().addAll(openMenuI, exitMenuI);
        menuBar = new MenuBar();
        menuBar.getMenus().addAll(menu);

        fileChooser = new FileChooser();

        root.setTop(menuBar);

        return new Scene(root, 500, 300);

    }

    public void setAlertWindow(Alert alert){
        this.alertWindow = alert;
        this.alertWindow.showAndWait();
    }

    public ButtonType getAlertWindowResult() throws NullPointerException{
        return alertWindow.getResult();
    }

    public MenuItem getOpenMenu(){
        return this.openMenuI;
    }

    public FileChooser getFileChooser(){
        return this.fileChooser;
    }

    public MenuItem getExitMenu(){
        return this.exitMenuI;
    }



}
