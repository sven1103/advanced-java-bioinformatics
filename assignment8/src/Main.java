
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.DrawMode;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;


/**
 * Created by sven on 12/6/15.
 */
public class Main extends Application {

    double mousePosX;
    double mousePosY;
    double mouseOldX;
    double mouseOldY;
    double mouseDeltaX;
    double mouseDeltaY;

    public Parent createContent() throws Exception {

        // Box
        Box box1 = new Box(50, 50, 50);
        box1.setMaterial(new PhongMaterial(Color.BLUE));
        box1.setDrawMode(DrawMode.FILL);
        box1.setTranslateY(100);
        Tooltip.install(box1, new Tooltip("I am the blue box."));

        Box box2 = new Box(50, 50, 50);
        box2.setMaterial(new PhongMaterial(Color.YELLOW));
        box2.setDrawMode(DrawMode.FILL);
        box2.setTranslateY(-100);
        Tooltip.install(box2, new Tooltip("I am the yellow box."));

        Cylinder cylinder = new Cylinder(15, 200);
        cylinder.setMaterial(new PhongMaterial(Color.RED));
        cylinder.setDrawMode(DrawMode.FILL);
        Tooltip.install(cylinder, new Tooltip("I am the red cylinder."));

        Group shapeGroup = new Group();
        shapeGroup.getChildren().addAll(box1, box2, cylinder);

        // Create and position camera
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setFieldOfView(75);
        camera.setNearClip(0.1);
        camera.setFarClip(10000);
        camera.getTransforms().addAll(
                new Rotate(0, Rotate.Y_AXIS),
                new Rotate(0, Rotate.X_AXIS),
                new Translate(0, 0, -500));

        // Build the scene graph
        Group root = new Group();
        root.getChildren().add(camera);
        root.getChildren().add(shapeGroup);

        // Use a subscene
        SubScene subScene = new SubScene(root, 600, 600, true, SceneAntialiasing.BALANCED);
        subScene.setFill(Color.DARKGRAY);
        subScene.setCamera(camera);
        subScene.setOnMousePressed(event -> {
            mousePosX = event.getSceneX();
            mousePosY = event.getSceneY();
            mouseOldX = event.getSceneX();
            mouseOldY = event.getSceneY();

        });
        subScene.setOnMouseDragged(event -> {
            mouseOldX = mousePosX;
            mouseOldY = mousePosY;
            mousePosX = event.getSceneX();
            mousePosY = event.getSceneY();
            mouseDeltaX = (mousePosX - mouseOldX);
            mouseDeltaY = (mousePosY - mouseOldY);
            if(event.isShiftDown()){
                camera.setTranslateZ(camera.getTranslateZ() + mouseDeltaY);
            } else{
                shapeGroup.getTransforms().addAll(
                        new Rotate(shapeGroup.getRotate() + mouseDeltaX*(-1), Rotate.Y_AXIS),
                        new Rotate(shapeGroup.getRotate() + mouseDeltaY, Rotate.Z_AXIS));
            }
        });

        Group group = new Group();
        group.getChildren().add(subScene);
        return group;

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setResizable(false);
        Scene scene = new Scene(createContent());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
