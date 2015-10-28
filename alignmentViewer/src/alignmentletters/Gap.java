package alignmentletters;

/**
 * Created by fillinger on 10/19/15.
 * As the gap is an alignment letter, but not a nucleotide, it gets its own
 * class.
 */
public class Gap implements IAlignmentLetter {

    public Gap(){
    }

    @Override
    public String toString(){
        return "-";
    }

}
