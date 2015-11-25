import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Created by fillinger on 11/24/15.
 */
public class StructureView extends BorderPane {

    // The model
    private final StructureModel model;

    // DEFAULT minimal width
    final double MIN_WIDTH = 300.0;

    // The UI elements
    final TextField sequence = new TextField();
    final TextField notation = new TextField();
    final Button foldButton = new Button();
    final Button drawButton = new Button();
    final CheckBox animateCheckBox = new CheckBox();
    final VBox controlContainer = new VBox();
    final HBox controlBar = new HBox();
    final VBox viewContainer = new VBox();

    final Pane drawArea = new Pane();

    /**
     * Constructor
     * @param model
     */
    public StructureView(StructureModel model){
        super();
        this.model = model;
        initView();
        setStyles();
    }

    /**
     * Initialize the view elements
     */
    private void initView(){
        sequence.setMinWidth(MIN_WIDTH);
        notation.setMinWidth(MIN_WIDTH);

        foldButton.setText("Fold");
        drawButton.setText("Draw");
        animateCheckBox.setText("Animate");

        controlBar.getChildren().addAll(foldButton, drawButton, animateCheckBox);
        controlBar.alignmentProperty().setValue(Pos.CENTER_LEFT);
        controlContainer.getChildren().addAll(sequence, notation, controlBar);

        this.setTop(controlContainer);

        Rectangle rectangle = new Rectangle(200,200, Color.BLUE);
        drawArea.getChildren().add(rectangle);

        this.setCenter(drawArea);

    }

    /**
     * Set styles to the view elements
     */
    private void setStyles(){
        controlContainer.setSpacing(2);
        controlBar.setSpacing(25);
        viewContainer.setSpacing(20);
        drawArea.setStyle("-fx-background-color: #979797");
    }
}
