package alignmentletters;

/**
 * Created by fillinger on 10/19/15.
 */
public class Nucleotide implements IAlignmentLetter{

    public Base base;
    public enum Base {A, C, U, G}

    public Nucleotide (Base base){
        this.base = base;
    }

    @Override
    public String toString(){
        return this.base.toString();
    }

}
