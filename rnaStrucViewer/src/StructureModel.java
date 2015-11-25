import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
import models.Nussinov;

/**
 * Created by fillinger on 11/24/15.
 */
public class StructureModel {

    private Nussinov nussinov;

    private final String nucleotideLib = "ACGTUacgtu";

    private final BooleanProperty isRNA =
            new SimpleBooleanProperty(this, "isRNA", false);

    private final StringProperty sequence =
            new SimpleStringProperty(this, "sequence", null);

    private final StringProperty notation =
            new SimpleStringProperty(this, "notation", null);

    /**
     * Default constructor
     */
    public StructureModel(){
        // Add invalidation listener to the sequence
        this.sequence.addListener(value -> evaluateRnaSequence());
    }

    /**
     * Apply the Nussinov algorithm to the sequence and
     * set the notation.
     */
    public void applyNussinov(){
        nussinov = new Nussinov(this.sequence.getValue());
        nussinov.apply();
        this.notation.setValue(nussinov.getBracketNotation());
    }

    public StringProperty getNotationProperty(){
        return this.notation;
    }

    public StringProperty getSequenceProperty(){
        return this.sequence;
    }

    public BooleanProperty getRnaProperty(){
        return this.isRNA;
    }

    /**
     * The wrapper for evaluating the sequence
     */
    private void evaluateRnaSequence(){
        this.isRNA.setValue(isRnaSequence());
    }

    /**
     * Check, if the sequence is of type 'RNA'
     * @return boolean: true/false
     */
    private boolean isRnaSequence(){

        Boolean isRNA = true;

        // Check if a nnucleotide sequence at all
        for (Character letter : this.sequence.getValue().toCharArray()){
            if (nucleotideLib.indexOf(letter) == -1 ){
                isRNA = false;
                break;
            }
        }
        // Check if sequence is of type 'RNA'
        if (this.sequence.getValue().equals("")){
            isRNA = false;
        } else {
            if (this.sequence.getValue().toLowerCase().contains("u") && !this.sequence.getValue().toLowerCase().contains("t")
                    || (this.sequence.getValue().toLowerCase().contains("a") || this.sequence.getValue().toLowerCase().contains("g")
                    || this.sequence.getValue().toLowerCase().contains("c"))) {
            } else {
                isRNA = false;
            }
        }
        return isRNA;
    }



}
