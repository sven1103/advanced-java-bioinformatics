import alignmentletters.Sequence;
import org.apache.commons.cli.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by sven on 10/18/15.
 */

public class CommandLine {
    /**
     * The CLI version of this tool
     */
    private static double VERSION = 0.1;

    /**
     * Stores the CLASS name
     */
    private String CLASS_NAME = this.getClass().getSimpleName();

    /**
     *  The sequence list, containing all sequences from an alignment file
     */
    public static ArrayList<Sequence> sequenceList;

    /**
     *  The alignment file name (FASTA) from the command line
     */
    public static File fileName;

    /**
     * Prints the sequence alignment nicely on the console
     * @param sequenceList
     */
    public static void printAlignment(ArrayList<Sequence> sequenceList, int width){

        int lengthAlignment = 0;

        try
        {
            lengthAlignment = sequenceList.get(0).getLength();
            System.out.println(lengthAlignment);
        }
        catch(Exception e){
            System.err.println("Ah, the sequence list seems to be empty?");
            System.err.println(e);
            System.exit(1);
        }

        int numberLines = (int) Math.ceil((double) lengthAlignment / width);

        for(int line = 1; line < numberLines; line++){
            System.out.println(String.format("%-30s %-"+(width-1)+"s %s", "", (line-1)*width+1, line*width));
            for(Sequence seq : sequenceList){
                System.out.println(String.format("%-30s %s", seq.getName(),
                        seq.getSequence((line-1)*width, line*width)));
            }
            System.out.println("");
        }
        System.out.println(String.format("%-30s %-"+(Math.floorMod(lengthAlignment, width)-2)+"s %s", "", lengthAlignment-Math.floorMod(lengthAlignment, width)+1,lengthAlignment));
        for(Sequence seq : sequenceList){
            System.out.println(String.format("%-30s %s", seq.getName(),
                    seq.getSequence(lengthAlignment-Math.floorMod(lengthAlignment, width), lengthAlignment-1)));
        }


    }

    /**
     * The main entry point :)
     * @param args
     */
    public static void main(String[] args) {

        System.out.println("Alignment Printer " + VERSION);

        // the command line parameters
        Options helpOptions = new Options();
        helpOptions.addOption("h", "help", false, "show this help page");
        Options options = new Options();
        options.addOption("h", "help", false, "show this help page");
        options.addOption("i", "input", true, "a multi-fasta or fasta alignment file");

        // For a nice help formatting
        HelpFormatter helpformatter = new HelpFormatter();

        // Define the parser object
        CommandLineParser parser = new DefaultParser();

        //Try to parse the arguments
        try {
            org.apache.commons.cli.CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption('h')) {
                helpformatter.printHelp("options", options);
                System.exit(0);
            }
            if (cmd.hasOption('i')) {
                fileName = new File(cmd.getOptionValue("i"));
            }

        } catch (ParseException e1) {
            System.err.println("Something went wrong reading your commandline arguments!\n");
            helpformatter.printHelp("options", options);
            System.exit(1);
        }

        // try to read in the alignment file
        try {
            sequenceList = FastaReader.readMultiFasta(fileName);
        } catch (IOException e) {
            System.err.println("Could not read in FASTA file.");
            System.err.println(e);
            System.exit(1);
        }

        printAlignment(sequenceList, 60);

        System.exit(0);

    }
}
