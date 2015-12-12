import javafx.application.Application;
import javafx.stage.Stage;
import models.Atom;
import models.PDBparser;
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

        PDBparser parser = PDBparser.getInstance();

        List<Atom> atomList = parser.parsePDB(args[0]).getAtomList();

        atomList.forEach((Atom atom) -> System.out.println(atom.toString()));

        launch();

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        RnaStrucViewer3dView view = RnaStrucViewer3dView.getInstance();
        RnaStrucViewer3dPresenter presenter = new RnaStrucViewer3dPresenter(view);
        primaryStage.setScene(view.scene);

        primaryStage.show();
    }
}
