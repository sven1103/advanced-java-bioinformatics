package de.advancedjava.dnamanipulator;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TextArea;

import java.util.List;

/**
 * Created by fillinger on 11/11/15.
 */
public class SequenceModel {

    public static final String nucleotideLib = "ACGTUacgtu";

    public static final String rnaPatternLower = "[t]";

    public static final String rnaPatternUpper = "[T]";

    public static final String dnaPatternLower = "[u]";

    public static final String dnaPatternUpper = "[U]";

    private final BooleanProperty isDNA =
            new SimpleBooleanProperty(this, "isDNA");

    private final BooleanProperty isRNA =
            new SimpleBooleanProperty(this, "isRNA");

    private final BooleanProperty isNucleotide =
            new SimpleBooleanProperty(this, "isNucleotide");

    private final StringProperty sequence =
            new SimpleStringProperty(this, "sequence", null);

    public SequenceModel(String sequence){
        this(false, false, sequence);
    }

    public SequenceModel(){
        this(false, false, null);
    }


    public SequenceModel(Boolean isDNA, Boolean isRNA, String sequence){
        this.isDNA.set(isDNA);
        this.sequence.set(sequence);
        this.isRNA.set(isRNA);
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

    public StringProperty textFieldStringProperty(){
        return this.sequence;
    }

    public BooleanProperty isDNABooleanProperty(){return this.isDNA;}

    public BooleanProperty isRNABooleanProperty(){return this.isRNA;}

    public BooleanProperty isNucleotideBooleanProperty(){return this.isNucleotide;}

    /**
     * Evaluates the nucleotide sequence and tries to identify it as
     * RNA or DNA. If unique assignment is not possible, the evaluation will
     * fail and the error list filled with the respective notification.
     * @param errorList
     * @return
     */
    public boolean evaluateSequenceType(List<String> errorList){
        boolean successfulEvaluation = false;

        if (this.sequence.get().equals("")){
            errorList.add("There is no sequence in the input.");
            isNucleotideBooleanProperty().setValue(false);
        } else {
            if (this.sequence.get().toLowerCase().contains("u") && !this.sequence.get().toLowerCase().contains("t")
                    && (this.sequence.get().toLowerCase().contains("a") || this.sequence.get().toLowerCase().contains("g")
            || this.sequence.get().toLowerCase().contains("c"))){
                this.isRNA.set(true);
                this.isDNA.set(false);
                isNucleotideBooleanProperty().setValue(true);
                errorList.add("isRNA");
                successfulEvaluation = true;
            } else if(this.sequence.get().toLowerCase().contains("t") && !this.sequence.get().toLowerCase().contains("u")
                    && (this.sequence.get().toLowerCase().contains("a") || this.sequence.get().toLowerCase().contains("g")
                    || this.sequence.get().toLowerCase().contains("c"))){
                this.isRNA.set(false);
                this.isDNA.set(true);
                isNucleotideBooleanProperty().setValue(true);
                errorList.add("isDNA");
                successfulEvaluation = true;
            } else{
                errorList.add("Cannot identify nucleotide sequence type DNA or RNA. Please convert sequence first.");
                isNucleotideBooleanProperty().setValue(true);
                isDNA.set(false);
                isRNA.set(false);
            }
        }
        return successfulEvaluation;
    }

    /**
     * Check, if the sequence is of type 'Nucleotide'
     * @return
     */
    public boolean isNucleotideSequence(List<String> errorList){
        Boolean isNucleotideSequence = true;

        for (Character letter : this.sequence.get().toCharArray()){
            if (nucleotideLib.indexOf(letter) == -1 ){
                isNucleotideSequence = false;
                errorList.add("Sequence is not a nucleotide sequence, because it contains the letter '" + letter + "'.");
                break;
            }
        }
        return isNucleotideSequence;
    }


    /**
     * Filters sequence for valid nucleotide letters
     * @param
     * @return
     */
    public void filterNucleotides(){
        StringBuilder filteredString = new StringBuilder();
        for(char letter : sequence.get().toCharArray()){
            if(nucleotideLib.contains(String.valueOf(letter))){
                filteredString.append(letter);
            }
        }
        this.sequence.setValue(filteredString.toString());
    }

    /**
     * Transform sequence into RNA sequence.
     * Also calls the filterNucleotide() method, to generate a valid RNA sequence.
     * @param
     * @return
     */
    public void transformToRNA(){
        filterNucleotides();
        this.sequence.set(this.sequence.get().replaceAll(rnaPatternLower, "u").replaceAll(rnaPatternUpper, "U"));
    }

    /**
     * Transform sequence into DNA sequence.
     * Also calls the filterNucleotide() method, to generate a valid DNA sequence.
     */
    public void transformToDNA(){
        filterNucleotides();
        this.sequence.set(this.sequence.get().replaceAll(dnaPatternLower, "t").replaceAll(dnaPatternUpper, "T"));
    }

    /**
     * Computes the reverse String
     * @return
     */
    public String reverseSeq(){
        filterNucleotides();
        return new StringBuilder(this.sequence.get()).reverse().toString();
    }

    /**
     * Computes the complementary sequence
     * @param
     * @return
     */
    public String complementary(){
        filterNucleotides();
        StringBuilder complementString = new StringBuilder(this.sequence.get());
        int lengthSeq = complementString.length();

        for(int i = 0; i < lengthSeq; i++){
            char currentChar = complementString.charAt(i);
            switch (currentChar){
                case 'a': complementString.replace(i,i+1,"t");
                    break;
                case 'c': complementString.replace(i,i+1,"g");
                    break;
                case 'g': complementString.replace(i,i+1,"c");
                    break;
                case 't': complementString.replace(i,i+1,"a");
                    break;
                case 'u': complementString.replace(i,i+1,"a");
                    break;
                case 'A': complementString.replace(i,i+1,"T");
                    break;
                case 'C': complementString.replace(i,i+1,"G");
                    break;
                case 'G': complementString.replace(i,i+1,"C");
                    break;
                case 'T': complementString.replace(i,i+1,"A");
                    break;
                case 'U': complementString.replace(i,i+1,"A");
                    break;
            }
        }
        return complementString.toString();
    }


    /**
     * Computes the complementary sequence
     * @param
     * @return
     */
    public String complementary(String sequence){
        filterNucleotides();
        StringBuilder complementString = new StringBuilder(sequence);
        int lengthSeq = complementString.length();

        for(int i = 0; i < lengthSeq; i++){
            char currentChar = complementString.charAt(i);
            switch (currentChar){
                case 'a': complementString.replace(i,i+1,"t");
                    break;
                case 'c': complementString.replace(i,i+1,"g");
                    break;
                case 'g': complementString.replace(i,i+1,"c");
                    break;
                case 't': complementString.replace(i,i+1,"a");
                    break;
                case 'u': complementString.replace(i,i+1,"a");
                    break;
                case 'A': complementString.replace(i,i+1,"T");
                    break;
                case 'C': complementString.replace(i,i+1,"G");
                    break;
                case 'G': complementString.replace(i,i+1,"C");
                    break;
                case 'T': complementString.replace(i,i+1,"A");
                    break;
                case 'U': complementString.replace(i,i+1,"A");
                    break;
            }
        }
        return complementString.toString();
    }

    /**
     * Computes the reverse complementary sequence
     * @param
     * @return
     */
    public String reverseComplementary(){
        String tempString = reverseSeq();
        return complementary(tempString);
    }


    /**
     * Computes GC content of a sequence
     */
    public String outputGcContent(){
        int amountGC = 0;
        for(char letter : this.sequence.get().toCharArray()){
            if ("gcGC".contains(String.valueOf(letter))){
                amountGC += 1;
            }
        }
        return (String.format("GC content is: %.2f %% (%d/%d)", 100*(double)amountGC/this.sequence.get().length(),
                amountGC, this.sequence.get().length()));
    }


    /**
     * Computes the length of a sequence
     *
     */
    public String outputSeqLength(){
       return (String.format("The sequence length is: %d", this.sequence.get().length()));
    }


    /**
     * Return the model sequence as a formatted String
     * @param lineWidth
     * @return
     */
    public String formatStringWidth(int lineWidth){
        StringBuilder formattedString = new StringBuilder();
        this.sequence.set(this.sequence.get().replaceAll("\\s", "").replaceAll("\n", ""));
        if (this.sequence.get().length() <= lineWidth){
            return this.sequence.get();
        }
        int numberLines = (this.sequence.get().length()/lineWidth)+1;

        for(int i=0; i<numberLines-1; i++){
            formattedString.append(this.sequence.get().substring(i*lineWidth, i*lineWidth+lineWidth));
            formattedString.append("\n");
        }
        formattedString.append(this.sequence.get().substring(
                this.sequence.get().length() - this.sequence.get().length()%lineWidth -1,
                this.sequence.get().length()-1));

        return formattedString.toString();
    }


    /**
     * Deletes the sequence content
     */
    public void deleteSequence(){
        this.sequence.setValue("");
    }


    /**
     * Flips the content of the two textareas
     * @param bottomTextField
     */
    public void flipTextFields(TextArea bottomTextField){
        String tempContent = bottomTextField.textProperty().getValue();
        bottomTextField.setText(this.sequence.get());
        this.sequence.set(tempContent);
    }


}
