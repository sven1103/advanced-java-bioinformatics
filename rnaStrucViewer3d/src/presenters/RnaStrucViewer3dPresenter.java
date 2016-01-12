package presenters;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Atom;
import models.PDBparser;
import models.RiboseModel;
import models.RnaStrucViewer3dModel;
import views.RnaStrucViewer3dView;

import java.io.File;
import java.util.List;

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

    PDBparser parser;

    List<Atom> atomList;

    RiboseModel riboseModel;

    RnaStrucViewer3dModel model;

    /**
     * The 3D-view
     */
    private RnaStrucViewer3dView view;

    private Stage stage;

    /**
     * Default constructor
     * @param view The view
     */
    public RnaStrucViewer3dPresenter(RnaStrucViewer3dView view, Stage stage, RnaStrucViewer3dModel model){
        this.view = view;
        this.stage = stage;
        this.model = model;
        initControls();
    }

    /**
     * Init the controls for mouse and keyboard
     */
    private void initControls(){
        /*
        Bind the subscene width and height properties to the parent scene properties.
         */
        view.scene3d.widthProperty().bind(view.totalScene.widthProperty());
        view.scene3d.heightProperty().bind(view.totalScene.heightProperty());

        /*
        Update camera when window is resized
         */
        view.totalScene.widthProperty().addListener((observable, oldValue, newValue) -> {
            view.camera.setTranslateX(-newValue.doubleValue()/2);
        });

        view.totalScene.heightProperty().addListener((observable, oldValue, newValue) -> {
            view.camera.setTranslateY(-newValue.doubleValue()/2);
        });


        view.totalScene.setOnMousePressed(event -> {
            mousePosX = event.getSceneX();
            mousePosY = event.getSceneY();
            mouseOldX = event.getSceneX();
            mouseOldY = event.getSceneY();
        });

        view.totalScene.setOnMouseDragged(event -> {
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

        view.openFile.setOnAction((value) -> openFile());
    }

    private void openFile(){

        view.fileChooser = new FileChooser();

        view.fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDB (pdb structure file)", "*.pdb"),
                new FileChooser.ExtensionFilter("All files", "*.*"));

        File defaultDirectory = new File("/home/sven/Downloads");

        if(defaultDirectory.exists())
            view.fileChooser.setInitialDirectory(defaultDirectory);


        File pdbFile = view.fileChooser.showOpenDialog(this.stage);

        if(pdbFile != null){
            PDBparser parser = PDBparser.getInstance();

            List<Atom> atomList = parser.parsePDB(pdbFile.getAbsolutePath()).getAtomList();

            RiboseModel riboseModel = new RiboseModel();

            atomList.forEach((Atom atom) -> riboseModel.setAtomCoords(atom));

            model.setAtomList(atomList).parseRiboseElements();

            view.structures.getChildren().addAll(model.getNucleotideGroup(),
                    model.getBoundsGroup(), model.getPhosphateGroup());
        }

    }

}
