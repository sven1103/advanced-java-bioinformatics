import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Created by fillinger on 11/25/15.
 */
public class StructureViewerMain extends Application{

    public static void main(String[] args){
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        StructureModel model = new StructureModel();

        StructureView view = new StructureView(model);

        StructurePresenter presenter = new StructurePresenter(model, view);

        Scene scene = new Scene(view, 800, 480, Color.BLUE);
        scene.getStylesheets().add("format.css");

        primaryStage.setTitle("RNA Structure Viewer 0.1");
        primaryStage.setScene(scene);

        primaryStage.show();



    }
}
