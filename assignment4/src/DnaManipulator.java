import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;

/**
 * Created by fillinger on 10/28/15.
 */
public class DnaManipulator extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("DnaManipulator 0.1");

        BorderPane mainContainer = new BorderPane();

        VBox verticalShapeContainer = new VBox(10);

        /**
         * Input area
         * ------------
         * Provides two textarea and an flip-button. The upper textarea
         * is the input field for the user, the bottom one displays effects
         * of manipulations. The flip-button exchanges the content between the
         * two textarea.
         */
        TextArea textInputField = new TextArea();
        TextArea textOutputField = new TextArea();
        textOutputField.setEditable(false); // bottom textarea will be not editable!
        textInputField.setWrapText(true); // avoids ugly scrollbars
        textOutputField.setWrapText(true);

        Button flipBut = new Button("Flip");
        //textInputField.setMinSize(400, 200);
        flipBut.setAlignment(Pos.CENTER);
        flipBut.setOnAction((value)->{{
            StringBuilder copyText = new StringBuilder(textInputField.getText());
            textInputField.setText(textOutputField.getText());
            textOutputField.setText(copyText.toString());
        }});


        /**
         * Control Button Section
         * -----------------------
         * At the bottom of both textarea, follows the section with
         * the main manipulation buttons. They provide functions like:
         *      - nucleotide filtering
         *      - upper and lower case formatting
         *      - transform to RNA
         *      - reverse the sequence
         *      - complementary sequence
         *      - reverse-complementary
         *      - GC content
         *      - sequence length
         *      - clear fields
         *      - slider: formats the content of the bottom textarea
         */
        Label controlLabel = new Label("Manipulate your sequence");
        FlowPane controlSection = new FlowPane();
        controlSection.setHgap(10);
        controlSection.setVgap(10);
        Button filterButton = new Button("Filter");
        Button upperCaseBut = new Button("UPPER case");
        Button lowerCaseBut = new Button("lower case");
        Button transformBut = new Button("to RNA");
        Button reverseBut = new Button("reverse");
        Button complementaryBut = new Button("complementary");
        Button reverseComplementBut = new Button("reverse-complementary");
        Button gcAmountBut = new Button("GC content");
        Button seqLengthBut = new Button("length");
        Button clearButton = new Button("clear");


        filterButton.setOnAction((value) ->
            textOutputField.setText(ManipulatorMethods.filterNucleotides(textInputField.getText())));

        upperCaseBut.setOnAction((value) ->
            textOutputField.setText(textInputField.getText().toUpperCase()));

        lowerCaseBut.setOnAction((value) ->
            textOutputField.setText(textInputField.getText().toLowerCase()));

        transformBut.setOnAction((value) ->
            textOutputField.setText(ManipulatorMethods.transformToRNA(textInputField.getText())));

        reverseBut.setOnAction((value) ->
            textOutputField.setText(ManipulatorMethods.reverseSeq(textInputField.getText())));

        complementaryBut.setOnAction((value) ->
            textOutputField.setText(ManipulatorMethods.complementary(textInputField.getText())));

        reverseComplementBut.setOnAction((value) ->
            textOutputField.setText(ManipulatorMethods.reverseComplementary(textInputField.getText())));

        gcAmountBut.setOnAction((value) ->
            textOutputField.setText(ManipulatorMethods.ouputGcContent(textInputField.getText())));

        seqLengthBut.setOnAction((value) ->
            textOutputField.setText(ManipulatorMethods.outputSeqLength(textInputField.getText())));

        clearButton.setOnAction((value) ->
        {
            textInputField.clear();
            textOutputField.clear();
        });

        // add the buttons to the control section
        controlSection.getChildren().addAll(filterButton,
                upperCaseBut,
                lowerCaseBut,
                transformBut,
                reverseBut,
                complementaryBut,
                reverseComplementBut,
                gcAmountBut,
                seqLengthBut,
                clearButton);


        /**
         * The awesome slider control
         */
        Slider slider = new Slider();
        slider.setMin(1);
        slider.setMax(200);
        slider.setValue(140);
        slider.setShowTickMarks(true);
        slider.setSnapToTicks(true);
        slider.setBlockIncrement(1);
        slider.setMinorTickCount(1);



        /**
         * Combine the nodes on the vertical shape container and set up the scene
         */
        verticalShapeContainer.getChildren().addAll(textInputField,
                flipBut,
                textOutputField,
                controlLabel,
                controlSection,
                new Label("\nFormat output"),
                slider);
        verticalShapeContainer.setAlignment(Pos.TOP_CENTER);
        mainContainer.setCenter(verticalShapeContainer);

        primaryStage.setScene(new Scene(mainContainer, 600, 800));

        primaryStage.show();


    }
}