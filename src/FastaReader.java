import alignmentletters.Sequence;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by fillinger on 10/19/15.
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

        ArrayList<Sequence> list = new ArrayList<>();
        String currName = "";
        Sequence currSequence;

        String currLine = "";
        while((currLine = bfr.readLine()) != null){
            if(currLine.contains(">")){
                currName = currLine.split(">")[1];
                continue;
            }
            if(currName != ""){
                list.add(new Sequence(currName, currLine));
                currName = "";
            }
        }
        return list;
    }
}
