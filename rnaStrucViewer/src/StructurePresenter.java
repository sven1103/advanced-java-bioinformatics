import models.Graph;
import models.SpringEmbedder;

import java.io.IOException;

/**
 * Created by fillinger on 11/24/15.
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
        Graph structure = new Graph();
        try {
            structure.parseNotation(model.getNotationProperty().getValue());
            double[][] newCoordinates = SpringEmbedder.computeSpringEmbedding(100, structure.getNumberOfNodes(), structure.getEdges(), null);

        } catch (IOException e){
            System.err.println(e);
        }
    }





}
