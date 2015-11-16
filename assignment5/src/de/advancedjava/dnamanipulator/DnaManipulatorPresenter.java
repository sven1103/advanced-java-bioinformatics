package de.advancedjava.dnamanipulator;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

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
    }


    public void attachControls(){
        view.textInputField.textProperty().bindBidirectional(model.textFieldStringProperty());
        model.textFieldStringProperty().addListener(observable -> {
            System.out.println(model.getSequence());
        }

        );
    }
}
