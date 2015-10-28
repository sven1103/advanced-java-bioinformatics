package alignmentletters;

import java.util.ArrayList;

/**
 * Created by fillinger on 10/19/15.
 */
public class Sequence {
    /**
     * The sequence will be represented as Letter-List
     */

    private ArrayList<IAlignmentLetter> sequence;
    /**
     * Each sequence will contain a name
     */
    private String name;

    /**
     * Default constructor
     */
    public Sequence(){
        this.sequence = new ArrayList();
        this.name = "";
    }

    /**
     * Constructor with sequence name
     * @param name
     */
    public Sequence(String name, String sequence){
        this.sequence = toSequence(sequence);
        this.name = name;
    }

    /**
     * Set the sequence name
     * @param name
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Get the sequence name
     * @return
     */
    public String getName(){
        return(this.name);
    }

    /**
     * returns the size of a sequence
     * @return
     */
    public int getLength(){
        return this.sequence.size();
    }

    /**
     * Prints a specified region of the sequence. Important for
     * the formatted output.
     * @param start
     * @param end
     * @return
     */
    public String getSequence(int start, int end){
        String partialSequence = "";
        for(int i = start; i <= end; i++ ){
            partialSequence += this.sequence.get(i).toString();
        }
        return(partialSequence);
    }

    @Override
    public String toString(){
        String sequence = "";
        for(IAlignmentLetter letter : this.sequence){
            sequence += letter.toString();
        }
        return (String.format("%-30s %s", this.name, sequence));
    }

    /**
     * Converts a sequence string into an ArrayList<alignmentletters.IAlignmentLetter>
     * @param string
     * @return
     */
    public static ArrayList<IAlignmentLetter> toSequence(String string){
        ArrayList<IAlignmentLetter> sequence = new ArrayList();
        for(char letter : string.toCharArray()){

            try {
                sequence.add(getBaseOfChar(letter));
            }
            catch (Exception e){
                System.err.println("Wrong sequence letters!? Sequence letter have to be one of (A,C,G,U or '-");
                System.exit(1);
            }
        }
        return sequence;
    }

    /**
     * Evaluates a character if it is a base or a gap
     * @param letter
     * @return
     */
    public static IAlignmentLetter getBaseOfChar(char letter){
        IAlignmentLetter base = null;
        if(letter == 'A'){
            base = new Nucleotide(Nucleotide.Base.A);
        } else if(letter == 'C'){
            base = new Nucleotide(Nucleotide.Base.C);
        } else if(letter == 'U'){
            base = new Nucleotide(Nucleotide.Base.U);
        } else if(letter == 'G'){
            base = new Nucleotide(Nucleotide.Base.G);
        } else if(letter == '-'){
            base = new Gap();
        }
        return base;
    }
}
