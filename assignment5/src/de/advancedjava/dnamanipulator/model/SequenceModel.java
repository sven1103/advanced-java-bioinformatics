package de.advancedjava.dnamanipulator.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.List;

/**
 * Created by fillinger on 11/11/15.
 */
public class SequenceModel {

    private final BooleanProperty isDNA =
            new SimpleBooleanProperty(this, "isDNA");

    private final BooleanProperty isRNA =
            new SimpleBooleanProperty(this, "isRNA");

    private final StringProperty sequence =
            new SimpleStringProperty(this, "sequence", null);


    public SequenceModel(){
        this(null, null, null);
    }

    public SequenceModel(String sequence){
        this(null, null, sequence);
    }

    public SequenceModel(Boolean isDNA, Boolean isRNA, String sequence){
        this.isDNA.set(isDNA);
        this.isRNA.set(isRNA);
        this.sequence.set(sequence);
    }

    public final String getSequence(){
        if(this.sequence.isNull().get()){
            return "";
        } else{
            return this.sequence.get();
        }
    }

    public final void setSequence(String sequence){
        this.sequence.set(sequence);
    }

    public boolean evaluateSequenceType(List<String> errorList){
        if (this.sequence.isNull().get()){
            errorList.add("There is no sequence in the input.");
        }
    }

}
