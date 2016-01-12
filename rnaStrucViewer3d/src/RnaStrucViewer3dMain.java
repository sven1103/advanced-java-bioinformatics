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

        RnaStrucViewer3dView view = RnaStrucViewer3dView.getInstance();
        RnaStrucViewer3dModel model = new RnaStrucViewer3dModel();
        RnaStrucViewer3dPresenter presenter = new RnaStrucViewer3dPresenter(view, primaryStage, model);

        primaryStage.setScene(view.totalScene);


/*


*/
        primaryStage.show();
    }
}
