package views;

import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.DrawMode;
import javafx.scene.transform.Rotate;
import javafx.stage.Window;

/**
 * Created by sven on 12/12/15.
 */
public class RnaStrucViewer3dView {

    public static volatile RnaStrucViewer3dView instance;

    public Box testBox;

    public Scene scene;

    public Rotate ry;

    public Rotate rx;

    public Group structures = new Group();

    public PerspectiveCamera camera;

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

    private void initView(){

        /*
        Make the scen
         */
        scene = new Scene(structures, 800, 600, true, SceneAntialiasing.BALANCED);

        /*
        Make a test box for debuggin
         *//*
        testBox = new Box(100,100,100);
        testBox.setMaterial(new PhongMaterial(Color.LIGHTSKYBLUE));
        testBox.setDrawMode(DrawMode.FILL);

        structures.getChildren().addAll(testBox);
*/
        /*
        Set the camera for the scene
         */
        camera = new PerspectiveCamera(false);
        camera.setTranslateX(-scene.getWidth()/2);
        camera.setTranslateY(-scene.getHeight()/2);
        camera.setTranslateZ(50);
        camera.setFarClip(10000);
        camera.setNearClip(0.001);
        camera.setFieldOfView(45);

        scene.setCamera(camera);

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
