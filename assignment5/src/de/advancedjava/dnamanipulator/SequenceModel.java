package de.advancedjava.dnamanipulator;

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

    private final StringProperty sequence =
            new SimpleStringProperty(this, "sequence", null);


    public SequenceModel(String sequence){
        this(false, sequence);
    }

    public SequenceModel(){
        this(false, null);
    }


    public SequenceModel(Boolean isDNA, String sequence){
        this.isDNA.set(isDNA);
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

    /**
     * Evaluates the nucleotide sequence and tries to identify it as
     * RNA or DNA. If unique assignment is not possible, the evaluation will
     * fail and the error list filled with the respective notification.
     * @param errorList
     * @return
     */
    public boolean evaluateSequenceType(List<String> errorList){
        boolean successfulEvaluation = false;

        if (this.sequence.isNull().get()){
            errorList.add("There is no sequence in the input.");
        } else {
            if (this.sequence.get().toLowerCase().contains("u") && !this.sequence.get().toLowerCase().contains("t")){
                this.isDNA.set(false);
                successfulEvaluation = true;
            } else if(this.sequence.get().toLowerCase().contains("t") && !this.sequence.get().toLowerCase().contains("u")){
                this.isDNA.set(true);
            } else{
                errorList.add("Cannot identify nucleotide sequence type DNA or RNA. Please convert sequence first.");
            }
        }
        return successfulEvaluation;
    }

    public StringProperty textFieldStringProperty(){
        return this.sequence;
    }

}
