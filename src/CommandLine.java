import alignmentletters.Sequence;
import org.apache.commons.cli.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by sven on 10/18/15.
 */

public class CommandLine {
    private static double VERSION = 0.1;
    private String CLASS_NAME = this.getClass().getSimpleName();
    public static ArrayList<Sequence> sequenceList;
    public static File fileName;

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

        //
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

        try {
            sequenceList = FastaReader.readMultiFasta(fileName);
        } catch (IOException e) {
            System.err.println("Could not read in FASTA file.");
            System.err.println(e);
            System.exit(1);
        }

        for (Sequence sequence : sequenceList){
            System.out.println(sequence.toString());
        }

        System.exit(0);

    }
}
