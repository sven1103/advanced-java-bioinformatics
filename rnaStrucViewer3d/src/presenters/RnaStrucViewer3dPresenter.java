package presenters;

import javafx.scene.transform.Rotate;
import views.RnaStrucViewer3dView;

/**
 * Created by sven on 12/12/15.
 */
public class RnaStrucViewer3dPresenter {

    double mousePosX;
    double mousePosY;
    double mouseOldX;
    double mouseOldY;
    double mouseDeltaX;
    double mouseDeltaY;

    /**
     * The 3D-view
     */
    private RnaStrucViewer3dView view;

    /**
     * Default constructor
     * @param view The view
     */
    public RnaStrucViewer3dPresenter(RnaStrucViewer3dView view){
        this.view = view;
        initControls();
    }

    /**
     * Init the controls for mouse and keyboard
     */
    private void initControls(){

        view.scene.setOnMousePressed(event -> {
            mousePosX = event.getSceneX();
            mousePosY = event.getSceneY();
            mouseOldX = event.getSceneX();
            mouseOldY = event.getSceneY();
        });

        view.scene.setOnMouseDragged(event -> {
            mouseOldX = mousePosX;
            mouseOldY = mousePosY;
            mousePosX = event.getSceneX();
            mousePosY = event.getSceneY();
            mouseDeltaX = (mousePosX - mouseOldX);
            mouseDeltaY = (mousePosY - mouseOldY);
            if(event.isShiftDown()){
                view.camera.setTranslateZ(view.camera.getTranslateZ() + mouseDeltaY);
            } else if(event.isControlDown()) {
                view.structures.setTranslateX(view.structures.getTranslateX() + mouseDeltaX);
                view.structures.setTranslateY(view.structures.getTranslateY() - mouseDeltaY);
            } else{
                view.ry.setAngle(view.ry.getAngle() - mouseDeltaX);
                view.rx.setAngle(view.rx.getAngle() - mouseDeltaY);
            }});
    }

}
