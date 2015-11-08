import javafx.application.Application;

import javafx.stage.Stage;

/**
 * Created by fillinger on 11/7/15.
 */
public class ClusterViewer extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        ClusterViewerModel model = new ClusterViewerModel();

        ClusterViewerView view = new ClusterViewerView();

        ClusterViewerController controller = new ClusterViewerController(model, view);

        primaryStage.setScene(controller.buildScene());

        primaryStage.show();

        controller.initViewControls(primaryStage);

        //controller.printStatusError("Alex riecht nach Alk");
    }

}
