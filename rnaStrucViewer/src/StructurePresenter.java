import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import models.Graph;
import models.SpringEmbedder;
import models.drawings.AbstractNucleotideCircle;
import models.drawings.NucleotideFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * advanced-java-bioinformatics
 *
 * Description:
 *  <- content ->
 *
 * @author fillinger
 * @version
 * Date: 11/24/15
 * EMail: sven.fillinger@student.uni-tuebingen.de
 */
public class StructurePresenter {

    private final StructureModel model;
    private final StructureView view;

    /**
     * Presenter constructor
     * @param model the secondary structure model
     * @param view the view
     */
    public StructurePresenter(StructureModel model, StructureView view){
        this.model = model;
        this.view = view;
        attachControls();
        setBindings();

    }

    /**
     * Attach control functions to the buttons
     */
    private void attachControls(){
        view.foldButton.setOnAction(value -> model.applyNussinov());
        //view.drawArea.widthProperty().addListener(value -> System.err.println(view.drawArea.getWidth()));
        view.drawButton.setOnAction(value -> drawStructure());
    }


    /**
     * Set the bindings for button functions and model objects
     */
    private void setBindings(){
        // Fold button disabling
        view.foldButton.disableProperty().bind((model.getRnaProperty().
                and(view.sequence.textProperty().length().greaterThan(1))).not());

        // Bind the model's sequence to the view's sequence input
        model.getSequenceProperty().bind(view.sequence.textProperty());

        // Bind
        view.notation.textProperty().bindBidirectional(model.getNotationProperty());
        view.drawButton.disableProperty().bind(((model.getNotationProperty().length().
                isEqualTo(model.getSequenceProperty().length())).and(model.getSequenceProperty().isNotEmpty())).not());
    }


    /**
     * Draws the secondary structure in the drawArea
     */
    private void drawStructure(){

        // clear the draw area :)
        view.drawArea.getChildren().clear();

        // Init a Graph object
        Graph structure = new Graph();

        // Init a NucleotideFactory for easy nucleotide graphic object generation
        NucleotideFactory nucleotideFactory = new NucleotideFactory();

        // List for collection of nucleotide objects
        List<AbstractNucleotideCircle> nucleotideList = new ArrayList<>();
        // List of lines representing the sequence connection
        List<Line> sequenceConnection = new ArrayList<>();
        // List of lines representing the predicted base-pairing
        List<Line> basePairing = new ArrayList<>();

        /*
        1. Parse the secondary structure notation from dot-bracket
        notation to a Graph object.

        2. Then assign coordinates to each node.

        3. Iterate over the coordinates and make NucleotideCircles,
        which will be added then to the draw area.
         */
        try {
            // The Parsing
            structure.parseNotation(model.getNotationProperty().getValue());
            // Assign the coordinates
            double[][] newCoordinates = SpringEmbedder.computeSpringEmbedding(20,
                    structure.getNumberOfNodes(), structure.getEdges(), null);

            // Center the coordinates with respect to the draw area size
            SpringEmbedder.centerCoordinates(newCoordinates, 20, view.drawArea.widthProperty().intValue()-20,
                    20, view.drawArea.heightProperty().intValue()-20);

            // test area
            for (int[] row : structure.getEdges()){
                for(int value : row){
                    System.err.print(value + " ");
                }
                System.err.print("\n");
            }



            // Now generate the lines and nucleotide circles
            for (int i=0; i < newCoordinates.length; i++){

                // The line constructing section
                if (i < newCoordinates.length-1){
                    Line connection = new Line(newCoordinates[i][0], newCoordinates[i][1],
                            newCoordinates[i+1][0], newCoordinates[i+1][1]);
                    connection.setStroke(Color.WHITE);
                    sequenceConnection.add(connection);
                }

                // Create NucleotideCircles dependent on the
                // sequence character at position 'i'
                Character base = model.getSequenceProperty().get().charAt(i);
                AbstractNucleotideCircle nucleotideCircle = nucleotideFactory.getNucleotide(base.toString());
                nucleotideCircle.setCenter(newCoordinates[i][0], newCoordinates[i][1]);
                nucleotideList.add(nucleotideCircle);
            }

            // Add the lines to the SceneGraph
            view.drawArea.getChildren().addAll(sequenceConnection);

            // Add the nucleotides to the SceneGraph
            for (AbstractNucleotideCircle nucleotide : nucleotideList){
                view.drawArea.getChildren().addAll(nucleotide.getNucleotide());
            }

        } catch (IOException e){
            System.err.println(e);
        }

        // Last but not least, add interactivity
        // to the graphic elements :)
        addInteractivity();
    }


    /**
     * Adds interactivity to the Nucleotide objects
     */
    private void addInteractivity(){
        List<Node> nodeList = view.drawArea.getChildren();
        for (Node nucleotide : nodeList){
            nucleotide.setOnMouseEntered(value -> {
                nucleotide.setCursor(Cursor.MOVE);
            });
            nucleotide.setOnMouseExited(value -> nucleotide.setCursor(Cursor.DEFAULT));
            if(nucleotide instanceof AbstractNucleotideCircle){

            }
        }

    }








}
