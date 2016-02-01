package presenters;

import com.sun.org.apache.xpath.internal.SourceTree;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.misc.Atom;
import models.nucleotide.*;
import models.pdb.PDBparser;
import system.PDBparseException;
import views.RnaStrucViewer3dView;

import java.io.File;
import java.util.ArrayList;
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

    List<Atom> atomList;

    RnaStrucViewer3dModel model;

    String helpMessage = String.format("Hold SHIFT for zooming in/out\n" +
            "Hold CTRL for moving the molecule\n" +
            "Use the MOUSE + LEFTCLICK for rotation!");

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
            view.update();
        });

        view.totalScene.heightProperty().addListener((observable, oldValue, newValue) -> {
            view.camera.setTranslateY(-newValue.doubleValue()/2);
            view.update();
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
                view.structures.setTranslateY(view.structures.getTranslateY() + mouseDeltaY);
            } else if(event.isSecondaryButtonDown()){
                view.rz.setAngle(view.rz.getAngle() + mouseDeltaY);
            }else{
                view.ry.setAngle(view.ry.getAngle() - mouseDeltaX);
                view.rx.setAngle(view.rx.getAngle() - mouseDeltaY);
            }});

        view.openFile.setOnAction((value) -> openFile());
    }


    /**
     * Opens a PDB-file from the file-chooser menu
     */
    private void openFile(){
        /*
        Filechooser settings
         */
        File pdbFile;
        view.fileChooser = new FileChooser();
        view.fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDB (pdb structure file)", "*.pdb"),
                new FileChooser.ExtensionFilter("All files", "*.*"));
        // Set default directory
        File defaultDirectory = new File("/home/sven/Downloads");

        if(defaultDirectory.exists())
            view.fileChooser.setInitialDirectory(defaultDirectory);

        // Open the dialog
        pdbFile = view.fileChooser.showOpenDialog(this.stage);

        // Parse pdb, if file is chosen
        if(pdbFile != null){
            // If a pdb file is already loaded, reset the model
            if(!model.getBaseGroup().getChildren().isEmpty())
                    model = new RnaStrucViewer3dModel();

            PDBparser parser = PDBparser.getInstance();

            view.sendMessage("Parsing file: " + pdbFile.getName());

            try{
                atomList = parser.parsePDB(pdbFile.getAbsolutePath()).getAtomList();
            } catch (PDBparseException e){
                view.sendMessage("[ERROR]: Could not parse " + pdbFile.getName());
            }

            view.sendMessage(helpMessage);

            // TODO: Rename to NucleotideModel
            RiboseModel riboseModel = new RiboseModel();

            atomList.forEach((Atom atom) -> riboseModel.setAtomCoords(atom));
            model.setAtomList(atomList).parseRiboseElements();

            view.structures.getChildren().clear();
            view.structures.getChildren().addAll(model.getNucleotideGroup(),
                    model.getBoundsGroup(), model.getPhosphateGroup());

            computeSecondaryStructure();
            view.update();
        }

    }

    public void computeSecondaryStructure(){

        StringBuilder sequence = new StringBuilder();

        StringBuilder dotBracketNotation = new StringBuilder();

        int sequenceLength = model.getNucleotideList().size();

        ArrayList<CovalentBond> hBondCollection = new ArrayList<>();

        for(int i = 0; i<sequenceLength; i++){
            dotBracketNotation.append(".");
        }

        int thisIndex = 0;

        for(Nucleotide nucleotide : model.getNucleotideList()){
            int otherIndex = 0;
            BaseModel currentBase = nucleotide.getBase();
            sequence.append(nucleotide.getBaseType());


            for(Nucleotide otherNucleotide : model.getNucleotideList()){
                BaseModel otherBase = otherNucleotide.getBase();

                int hBonds = currentBase.evaluateNumberHBonds(otherBase);

                //System.err.println(String.format("%s%s:%s%s\t %s", nucleotide.getBaseType(), nucleotide.getResiduePosition(), otherNucleotide.getBaseType(), otherNucleotide.getResiduePosition(), hBonds));
                if(hBonds >= 2){
                    if(thisIndex < otherIndex){
                        dotBracketNotation.setCharAt(thisIndex, '(');
                        dotBracketNotation.setCharAt(otherIndex, ')');
                    } else{
                        dotBracketNotation.setCharAt(thisIndex, ')');
                        dotBracketNotation.setCharAt(otherIndex, '(');
                    }

                    Atom[] hBondSet1 = nucleotide.getBase().getHBondAtoms();
                    Atom[] hBondSet2 = otherNucleotide.getBase().getHBondAtoms();

                    if(hBondSet1.length == hBondSet2.length){
                        for(int i = 0; i<hBondSet1.length; i++){
                            Hbond newHBond = new Hbond();
                            newHBond.setStartAtom(hBondSet1[i].getCoords());
                            newHBond.setEndAtom(hBondSet2[i].getCoords());
                            hBondCollection.add(newHBond);
                        }
                    }
                    /*
                    System.err.println(String.format("%d(%s%d:%s%d)", hBonds, nucleotide.getBaseType(),
                            nucleotide.getResiduePosition(), otherNucleotide.getBaseType(),
                            otherNucleotide.getResiduePosition()));*/
                }
                otherIndex++;
            }
            thisIndex += 1;
        }


        view.structures.getChildren().addAll(this.model.setHbondList(hBondCollection).getHBondAs3D());


        view.sendMessage(String.format("Sequence:\n%s", sequence.toString()));
        view.sendMessage(String.format("%s", dotBracketNotation.toString()));
    }

}
