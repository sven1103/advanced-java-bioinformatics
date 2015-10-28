import com.sun.deploy.util.StringUtils;
import java.util.Iterator;

/**
 * Created by fillinger on 10/28/15.
 */
public class ManipulatorMethods {

    public static final String nucleotideLib = "ACGTUacgtu";

    public static final String rnaPatternLower = "[t]";

    public static final String rnaPatternUpper = "[T]";

    /**
     * Filters sequence for valid nucleotide letters
     * @param content
     * @return
     */
    public static String filterNucleotides(String content){
        StringBuilder filteredString = new StringBuilder();
        for(char letter : content.toCharArray()){
            if(nucleotideLib.contains(String.valueOf(letter))){
                filteredString.append(letter);
            }
        }
        return filteredString.toString();
    }

    /**
     * Transform sequence into RNA sequence.
     * Also calls the filterNucleotide() method, to generate a valid RNA sequence.
     * @param content
     * @return
     */
    public static String transformToRNA(String content){
        return filterNucleotides(content).replaceAll(rnaPatternLower, "u").replaceAll(rnaPatternUpper, "U");
    }

    /**
     * Computes the reverse String
     * @param seq
     * @return
     */
    public static String reverseSeq(String seq){
        return new StringBuilder(seq).reverse().toString();
    }

    /**
     * Computes the complementary sequence
     * @param seq
     * @return
     */
    public static String complementary(String seq){

        StringBuilder complementString = new StringBuilder(filterNucleotides(seq));

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
     * @param seq
     * @return
     */
    public static String reverseComplementary(String seq){
        return complementary(reverseSeq(seq));
    }


    /**
     * Computes GC content of a sequence
     */
    public static String ouputGcContent(String seq){
        int amountGC = 0;
        for(char letter : seq.toCharArray()){
            if ("gcGC".contains(String.valueOf(letter))){
                amountGC += 1;
            }
        }
        return (String.format("GC content is: %.2f %% (%d/%d)", 100*(double)amountGC/seq.length(),
                amountGC, seq.length()));
    }

    /**
     * Computes the length of a sequence
     */
    public static String outputSeqLength(String seq){
        return (String.format("The sequence length is: %d", seq.length()));
    }



}
