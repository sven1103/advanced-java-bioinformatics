package de.advancedjava.dnamanipulator;

import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sven on 11/14/15.
 */
public class DnaManipulatorPresenter {

    private final SequenceModel model;

    private final DnaManipulatorView view;


    public DnaManipulatorPresenter(SequenceModel model, DnaManipulatorView view){
        this.model = model;
        this.view = view;
        attachControls();
        setBindings();
    }


    /**
     * Give life to the control elements :)
     */
    public void attachControls(){

        view.filterButton.setOnAction(value -> model.filterNucleotides());
        view.clearButton.setOnAction(value -> {
            model.deleteSequence();
            view.textOutputField.clear();
        });

        view.flipBut.setOnAction(value -> model.flipTextFields(view.textOutputField));
        view.gcAmountBut.setOnAction(value -> view.textOutputField.setText(model.outputGcContent()));
        view.transformBut.setOnAction(value -> model.transformToRNA());
        view.dnaTransformBut.setOnAction(value -> model.transformToDNA());

        view.slider.valueProperty().addListener((obs, oldVal, newVal) -> {
            view.sliderValue.setText(String.format("%03d", (int) view.slider.getValue()));
            view.textOutputField.setText(model.formatStringWidth(newVal.intValue()));
        });

        view.seqLengthBut.setOnAction(value -> view.textOutputField.setText(model.outputSeqLength()));
        view.complementaryBut.setOnAction(value -> view.textOutputField.setText(model.complementary()));
        view.upperCaseBut.setOnAction(value -> view.textOutputField.setText(model.getSequence().toUpperCase()));
        view.lowerCaseBut.setOnAction(value -> view.textOutputField.setText(model.getSequence().toLowerCase()));
        view.reverseBut.setOnAction(value -> view.textOutputField.setText(model.reverseSeq()));
        view.reverseComplementBut.setOnAction(value -> view.textOutputField.setText(model.reverseComplementary()));
        view.exitButton.setOnAction(value -> Platform.exit());

        model.textFieldStringProperty().addListener(observable -> {
            List<String> errorList = new ArrayList<>();
            if(model.isNucleotideSequence(errorList)){
                model.isNucleotideBooleanProperty().set(true);
                model.evaluateSequenceType(errorList);
            } else {
                model.isNucleotideBooleanProperty().set(false);
                model.isDNABooleanProperty().set(false);
            }
            view.printErrors(errorList);
        });
    }

    public void setBindings(){
        // Bind text from the upper input field to the model.

        view.textInputField.textProperty().bindBidirectional(model.textFieldStringProperty());
        view.transformBut.disableProperty().bind(model.isNucleotideBooleanProperty().not().or(
                model.isRNABooleanProperty()));
        view.dnaTransformBut.disableProperty().bind(model.isNucleotideBooleanProperty().not().or(
                model.isDNABooleanProperty()));
        view.complementaryBut.disableProperty().bind(model.isNucleotideBooleanProperty().not());
        view.reverseBut.disableProperty().bind(model.isNucleotideBooleanProperty().not());
        view.reverseComplementBut.disableProperty().bind(model.isNucleotideBooleanProperty().not());

    }



}
