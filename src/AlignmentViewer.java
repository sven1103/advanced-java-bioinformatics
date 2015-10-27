import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import org.apache.commons.cli.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import alignmentletters.Sequence;
import visualization.*;

/**
 * Created by sven on 10/18/15.
 * This is the entry class for the alignment reader and printer.
 * It provides the command-line interface (CLI), reads the filename
 * and executes the FastaReader with additional formatted print out on
 * the command line.
 */
public class AlignmentViewer extends Application {
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

        launch();
    }

   

    @Override
    public void start(Stage primaryStage){
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

        /**
         * Init alignment presentation
         */
        String alignmentString = Visualization.printAlignment(sequenceList, 60);

        System.out.println(alignmentString);

        primaryStage.setTitle("AlignmentViewer " + VERSION);

        Text seqAlignment = new Text();
        seqAlignment.setFont(Font.font("Monospace", 12));
        seqAlignment.setText(alignmentString);

        BorderPane root = new BorderPane();
        root.getChildren().add(seqAlignment);

        primaryStage.setScene(new Scene(root, 800, 600));

        primaryStage.show();

       

    }

}
