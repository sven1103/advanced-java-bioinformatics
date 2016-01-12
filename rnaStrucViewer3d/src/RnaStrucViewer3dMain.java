import javafx.application.Application;
import javafx.stage.Stage;
import models.RnaStrucViewer3dModel;
import presenters.RnaStrucViewer3dPresenter;
import views.RnaStrucViewer3dView;


/**
 * Created by sven on 12/12/15.
 */
public class RnaStrucViewer3dMain extends Application{

    public static void main(String[] args){

        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        RnaStrucViewer3dView view = RnaStrucViewer3dView.getInstance();
        RnaStrucViewer3dModel model = new RnaStrucViewer3dModel();
        RnaStrucViewer3dPresenter presenter = new RnaStrucViewer3dPresenter(view, primaryStage, model);

        primaryStage.setScene(view.totalScene);

        primaryStage.show();
    }
}
