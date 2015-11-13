package de.advancedjava.dnamanipulator;

import javafx.application.Application;
import javafx.beans.binding.BooleanBinding;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * Created by fillinger on 11/3/15.
 */
public class BindingTest extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root = new BorderPane();

        VBox vboxContainer = new VBox(10);

        Button okButton = new Button("OK");

        TextField textArea1 = new TextField();
        TextField textArea2 = new TextField();

        CheckBox checkBoxBronze = new CheckBox("Bronze");
        CheckBox checkBoxSilver = new CheckBox("Silver");
        CheckBox checkBoxGold = new CheckBox("Gold");


        vboxContainer.getChildren().addAll(okButton,
                textArea1,
                textArea2,
                checkBoxBronze,
                checkBoxSilver,
                checkBoxGold);

        vboxContainer.setAlignment(Pos.TOP_CENTER);

        root.setCenter(vboxContainer);

        /**
         * This is where the magic happens
         */
        BooleanBinding textFieldFilled = textArea1.textProperty().isNotEmpty().
                and(textArea1.textProperty().length().greaterThan(1)).
                and(textArea2.textProperty().isNotEmpty().
                and(textArea2.textProperty().length().greaterThan(1)));

        BooleanBinding checkBoxSelected = checkBoxBronze.selectedProperty().
                or(checkBoxSilver.selectedProperty()).
                or(checkBoxGold.selectedProperty());

        okButton.disableProperty().bind(textFieldFilled.and(checkBoxSelected).not());


        primaryStage.setScene(new Scene(root, 400, 500));
        primaryStage.setTitle("Test the awesome bindings");
        primaryStage.show();
    }
}
