package views;

import javafx.scene.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.stage.FileChooser;

/**
 * Created by sven on 12/12/15.
 */
public class RnaStrucViewer3dView {

    public static volatile RnaStrucViewer3dView instance;

    public SubScene scene3d;

    public Scene totalScene;

    public Rotate ry;

    public Rotate rx;

    public Group structures = new Group();

    public Group totalView = new Group();

    public PerspectiveCamera camera;

    public MenuBar menuBar;

    public Menu menu;

    public MenuItem openFile;

    public FileChooser fileChooser;

    private RnaStrucViewer3dView(){}

    public static RnaStrucViewer3dView getInstance(){
        if(instance == null){
            synchronized (RnaStrucViewer3dView.class){
                if(instance == null) {
                    instance = new RnaStrucViewer3dView();
                    instance.initView();
                }
            }
        }
        return instance;
    }


    /**
     * Initializes the view
     */
    private void initView(){

        /*
        Make the scene
         */
        menuBar = new MenuBar();

        menu = new Menu("File");

        openFile = new MenuItem("Open File");

        menu.getItems().addAll(openFile);

        menuBar.getMenus().addAll(menu);

        scene3d = new SubScene(structures, 800, 600, true, SceneAntialiasing.BALANCED);

        VBox pane = new VBox();

        pane.getChildren().addAll(menuBar, scene3d);

        totalScene = new Scene(pane, 800, 600);

        /*
        Set the camera for the scene
         */
        camera = new PerspectiveCamera(false);
        camera.setTranslateX(-scene3d.getWidth()/2);
        camera.setTranslateY(-scene3d.getHeight()/2);
        camera.setTranslateZ(50);
        camera.setFarClip(10000);
        camera.setNearClip(0.001);
        camera.setFieldOfView(45);

        scene3d.setCamera(camera);

        /*
        Set the rotation axis
         */
        ry = new Rotate(0, Rotate.Y_AXIS);
        rx = new Rotate(0, Rotate.X_AXIS);

        structures.getTransforms().addAll(ry, rx);
        ry.setAngle(10);
        rx.setAngle(10);

    }



}
