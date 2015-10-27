import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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

        Label headerLabel = new Label();
        headerLabel.setText("Alignment of your file:");


        final Text seqAlignment = new Text();
        seqAlignment.setFont(Font.font("Monospace", 12));
        seqAlignment.setText(alignmentString);

        /**
         * Define the checkboxes
         */
        final CheckBox cbShowHeaders = new CheckBox();
        cbShowHeaders.setText("Show Headers");
        cbShowHeaders.setSelected(Settings.showHeaders());
        cbShowHeaders.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Settings.toogleHeaders(); // true -> false, false -> true
            }
        });

        final CheckBox cbShowSequences = new CheckBox();
        cbShowSequences.setText("Show Sequences");
        cbShowSequences.setSelected(Settings.showSequence());
        cbShowSequences.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                /*
                It doesn't make sense to show headers without sequences,
                so disable the checkbox for headers, if sequences are not
                shown anymore.
                 */
                Settings.toogleSequence(); // true -> false, false -> true
                if(cbShowHeaders.isDisabled()){
                    cbShowHeaders.setDisable(false);
                } else{
                    cbShowHeaders.setDisable(true);
                }
            }
        });

        final CheckBox cbShowNumbering = new CheckBox();
        cbShowNumbering.setText("Show Numbering");
        cbShowNumbering.setSelected(Settings.showNumbering());
        cbShowNumbering.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Settings.toogleNumbering(); // true -> false, false -> true
            }
        });


        /**
         * Defines the view control buttons of the UI
         */
        Button selectAll = new Button();
        selectAll.setText("Select all");
        selectAll.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Settings.selectAll();
                cbShowHeaders.setSelected(true);
                cbShowNumbering.setSelected(true);
                cbShowSequences.setSelected(true);
            }
        });

        Button clearSelection = new Button();
        clearSelection.setText("Clear selection");
        clearSelection.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Settings.clearAll();
                cbShowHeaders.setDisable(true);
                cbShowHeaders.setSelected(false);
                cbShowSequences.setSelected(false);
                cbShowNumbering.setSelected(false);
            }
        });

        Button applySettings = new Button();
        applySettings.setText("Apply");
        applySettings.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                seqAlignment.setText(Visualization.printAlignment(sequenceList, 60));
            }
        });


        /**
         * Build nodes and attach them to the scene
         */
        VBox settings = new VBox(20);
        settings.getChildren().addAll(cbShowHeaders, cbShowSequences, cbShowNumbering);

        HBox viewControl = new HBox(20);
        viewControl.getChildren().addAll(selectAll, clearSelection, applySettings);

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20, 20, 20, 20)); // distance to the window border
        root.setTop(headerLabel);
        root.setAlignment(seqAlignment, Pos.TOP_LEFT);
        root.setAlignment(settings, Pos.TOP_CENTER);
        root.setMargin(seqAlignment, new Insets(20, 0, 0, 50)); // nice formatting for the alignment
        root.setMargin(settings, new Insets(20,0,0,0));
        root.setCenter(seqAlignment);
        root.setRight(settings);
        root.setBottom(viewControl);

        primaryStage.setScene(new Scene(root, 1000, 600));

        primaryStage.show();

       

    }

}
