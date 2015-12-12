import presenters.ClusterViewerController;
import javafx.application.Application;

import javafx.stage.Stage;
import models.ClusterViewerModel;
import views.ClusterViewerView;

/**
 * Created by fillinger on 11/7/15.
 */
public class StartClusterViewer extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        ClusterViewerModel model = new ClusterViewerModel();

        ClusterViewerView view = new ClusterViewerView(model);

        ClusterViewerController controller = new ClusterViewerController(model, view);

        primaryStage.setScene(controller.buildScene());

        primaryStage.setTitle("ClusterViewer 0.1");

        primaryStage.show();

        controller.initViewControls(primaryStage);

    }

}
