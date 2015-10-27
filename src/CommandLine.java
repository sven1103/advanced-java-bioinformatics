import org.apache.commons.cli.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import alignmentletters.Sequence;
import visualization.*;

/**
 * Created by sven on 10/18/15.
 */
public class CommandLine {
    /**
     * The CLI version of this tool
     */
    private static double VERSION = 0.2;

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
     * The main entry point :)
     * @param args
     */
    public static void main(String[] args) {

        /**
           Set the argument parser options
         */
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

        /**
            The main program starts here
         */
        // try to read in the alignment file
        try {
            sequenceList = FastaReader.readMultiFasta(fileName);
        } catch (IOException e) {
            System.err.println("Could not read in FASTA file.");
            System.err.println(e);
            System.exit(1);
        }

        String string = Visualization.printAlignment(sequenceList, 60);

        System.out.println(string);

        System.exit(0);

    }
}
