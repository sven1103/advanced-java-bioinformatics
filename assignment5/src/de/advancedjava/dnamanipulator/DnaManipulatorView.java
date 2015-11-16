package de.advancedjava.dnamanipulator;

import de.advancedjava.dnamanipulator.SequenceModel;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Created by fillinger on 11/11/15.
 */
public class DnaManipulatorView extends BorderPane{

    private final SequenceModel model;

    VBox verticalShapeContainer = new VBox(10);
    Button flipBut = new Button("Flip");

    // Text input/output fields
    TextArea textInputField = new TextArea();
    TextArea textOutputField = new TextArea();

    // Control section
    Label controlLabel = new Label("Manipulate your sequence");
    FlowPane controlSection = new FlowPane();
    Button filterButton = new Button("Filter");
    Button upperCaseBut = new Button("UPPER case");
    Button lowerCaseBut = new Button("lower case");
    Button transformBut = new Button("to RNA");
    Button reverseBut = new Button("reverse");
    Button complementaryBut = new Button("complementary");
    Button reverseComplementBut = new Button("reverse-complementary");
    Button gcAmountBut = new Button("GC content");
    Button seqLengthBut = new Button("length");
    Button clearButton = new Button("clear");

    // Slider area
    HBox sliderContent = new HBox();
    Label sliderDescription = new Label("Output width:");
    Label sliderValue = new Label();
    Slider slider = new Slider();

    /**
     * Constructor
     * @param model
     */
    public DnaManipulatorView(SequenceModel model){
        super();
        this.model = model;
        layoutTool();
        addElements();
        setStyles();
    }

    /**
     * Set the layout
     */
    private void layoutTool(){
        // set the gaps in the button control section
        controlSection.setHgap(10);
        controlSection.setVgap(10);

        // set the slider layout
        slider.setMinWidth(250);
        slider.setMin(1);
        slider.setMax(200);
        slider.setValue(60);
        slider.setShowTickMarks(true);
        slider.setBlockIncrement(1);
        slider.setMinorTickCount(10);
        slider.setMajorTickUnit(50);
        sliderValue.setText(String.format("%03d", (int) slider.getValue()));

    }

    /**
     * Add elements to this view
     */
    private void addElements(){
        verticalShapeContainer.getChildren().addAll(textInputField,
                flipBut,
                textOutputField,
                controlLabel,
                controlSection,
                new Label("\nFormat output"),
                sliderContent);

        controlSection.getChildren().addAll(filterButton,
                upperCaseBut,
                lowerCaseBut,
                transformBut,
                reverseBut,
                complementaryBut,
                reverseComplementBut,
                gcAmountBut,
                seqLengthBut,
                clearButton);

        sliderContent.setPrefWidth(600);
        sliderContent.setSpacing(20);
        sliderContent.setAlignment(Pos.CENTER);
        sliderDescription.getStyleClass().add("label");
        sliderContent.getChildren().addAll(sliderDescription, slider, sliderValue);

        verticalShapeContainer.setAlignment(Pos.TOP_CENTER);

        this.setCenter(verticalShapeContainer);

    }

    public void setStyles(){
        flipBut.getStyleClass().add("flip-button");
        clearButton.setId("clear-button");
        this.getStyleClass().add("pane");
        controlSection.getStyleClass().add("flowpane");

    }

}
