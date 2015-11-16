package de.advancedjava.dnamanipulator;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * Created by fillinger on 10/28/15.
 */
public class DnaManipulator extends Application{


    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        SequenceModel model = new SequenceModel();
        DnaManipulatorView view = new DnaManipulatorView(model);

        Scene scene = new Scene(view, 600, 800);

        DnaManipulatorPresenter presenter = new DnaManipulatorPresenter(model, view);

        primaryStage.setScene(scene);
        scene.getStylesheets().add("stylesheet.css");
        primaryStage.setTitle("DNA Manipulator 0.2");
        primaryStage.show();

    }
}
