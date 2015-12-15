import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.shape.MeshView;
import javafx.stage.Stage;
import models.Atom;
import models.PDBparser;
import models.RiboseModel;
import models.RnaStrucViewer3dModel;
import presenters.RnaStrucViewer3dPresenter;
import views.RnaStrucViewer3dView;

import java.util.List;

/**
 * Created by sven on 12/12/15.
 */
public class RnaStrucViewer3dMain extends Application{

    public static void main(String[] args){

        if (args.length != 1){
            System.err.println("Wrong number of arguments. Provide the pdb file");
            System.exit(1);
        }

        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        List<String> args = getParameters().getRaw();

        PDBparser parser = PDBparser.getInstance();

        List<Atom> atomList = parser.parsePDB(args.get(0)).getAtomList();

        RiboseModel riboseModel = new RiboseModel();

        atomList.forEach((Atom atom) -> riboseModel.setAtomCoords(atom));


        RnaStrucViewer3dView view = RnaStrucViewer3dView.getInstance();
        RnaStrucViewer3dPresenter presenter = new RnaStrucViewer3dPresenter(view);
        RnaStrucViewer3dModel model = new RnaStrucViewer3dModel();


        primaryStage.setScene(view.scene);

        model.setAtomList(atomList).parseRiboseElements();

        view.structures.getChildren().addAll(model.getRiboseGroup(), model.getBaseGroup(), model.getBoundsGroup());

        primaryStage.show();
    }
}
