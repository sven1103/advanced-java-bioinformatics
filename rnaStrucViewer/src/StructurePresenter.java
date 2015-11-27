import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import models.Graph;
import models.SpringEmbedder;
import models.drawings.AbstractNucleotideCircle;
import models.drawings.NucleotideFactory;
import java.io.IOException;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;
import static java.lang.Math.random;

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
        // Gather the animation keyframes
        List<Timeline> timeLineList = new ArrayList<>();

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
            double[][] newCoordinates = SpringEmbedder.computeSpringEmbedding(50,
                    structure.getNumberOfNodes(), structure.getEdges(), null);

            // Center the coordinates with respect to the draw area size
            SpringEmbedder.centerCoordinates(newCoordinates, 20, view.drawArea.widthProperty().intValue()-20,
                    20, view.drawArea.heightProperty().intValue()-20);

            // Now generate the lines and nucleotide circles
            for (int i=0; i < newCoordinates.length; i++){
                // Check if index is part of a computed base pair
                // and add base-pair lines if positive
                int basePair = isPairing(i, structure.getEdges());
                if (basePair != -1){
                    Line basePairLine = new Line(newCoordinates[i][0], newCoordinates[i][1],
                            newCoordinates[basePair][0], newCoordinates[basePair][1]);
                    basePairLine.setStroke(Color.MAGENTA);
                    basePairing.add(basePairLine);
                }
                // Draw connection lines between sequential nucleotides
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
                nucleotideCircle.setCenter(random() * view.drawArea.widthProperty().get(),
                        random() * view.drawArea.heightProperty().get());
                nucleotideList.add(nucleotideCircle);
                final KeyValue keyValueX0 = new KeyValue(nucleotideCircle.setTranslateX(),
                        random() * view.drawArea.getLayoutX());
                final KeyValue keyValueY0 = new KeyValue(nucleotideCircle.layoutYProperty(),
                        random() * view.drawArea.getLayoutY());
                final KeyFrame keyFrame0 = new KeyFrame(Duration.seconds(0),
                        keyValueX0,
                        keyValueY0);
                final KeyValue keyValueX1 = new KeyValue(nucleotideCircle.layoutXProperty(),
                        newCoordinates[i][1]);
                final KeyValue keyValueY1 = new KeyValue(nucleotideCircle.layoutYProperty(),
                        newCoordinates[i][1]);
                final KeyFrame keyFrame1 = new KeyFrame(Duration.seconds(2),
                        keyValueX1,
                        keyValueY1);
                timeLineList.add(new Timeline(keyFrame0, keyFrame1));
            }

            // Add the lines to the SceneGraph
            view.drawArea.getChildren().addAll(sequenceConnection);
            view.drawArea.getChildren().addAll(basePairing);

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
        playAnimation(timeLineList);
    }


    private void playAnimation(List<Timeline> timelineList){
        for (Timeline timeline : timelineList){
            timeline.play();
            System.err.println("Play");
        }
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


    /**
     * Checks if a index i is in a 2d array of indices with the computed
     * values for base pairing
     * @param i the index to evaluate
     * @param pairList 2D integer array with computed indices
     * @return the index of the corresponding base pair, -1 if not found
     */
    private int isPairing(int i, int[][] pairList){
        boolean firstOccurence = false;
        for (int[] row : pairList){
            if(row[0] == i){
                if(!firstOccurence){
                    firstOccurence = true;
                } else {
                    return row[1];
                }
            }
        }
        return -1;
    }








}
