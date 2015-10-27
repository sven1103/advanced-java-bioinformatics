import alignmentletters.Sequence;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by fillinger on 10/19/15.
 * The FastaReader class provides a static method
 * for reading a multi-fasta alignment file, parses the sequence into
 * Sequence objects, and returns a list with these sequences for further
 * manipulation.
 */
public class FastaReader {

    /**
     * A static method reading from a multi fasta file and converting it
     * into a list of alignmentletters.Sequence objects.
     * @param fastaFile
     * @return
     * @throws IOException
     */
    public static ArrayList<Sequence> readMultiFasta(File fastaFile) throws IOException {
        FileReader fr = new FileReader(fastaFile);
        BufferedReader bfr = new BufferedReader(fr);

        ArrayList<Sequence> list = new ArrayList();
        String currName = "";
        String currSequence = "";

        String currLine = "";
        while((currLine = bfr.readLine()) != null){
            if(currLine.contains(">")){
                if(currSequence != ""){
                    list.add(new Sequence(currName, currSequence));
                    currSequence = "";
                }
                currName = currLine.split(">")[1];
                continue;
            }
            if(currName != ""){
                currSequence += currLine;
            }
        }
        list.add(new Sequence(currName, currSequence));
        return list;
    }
}
