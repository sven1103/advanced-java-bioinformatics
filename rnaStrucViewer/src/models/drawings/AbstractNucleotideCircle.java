package models.drawings;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

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
 * Date: 11/26/15
 * EMail: sven.fillinger@student.uni-tuebingen.de
 */
public abstract class AbstractNucleotideCircle extends Node{

    protected Circle circle;

    protected Circle shadow;

    protected Text base;

    protected Circle toolTipMask;

    protected Group elementGroup;

    private final double DEFAULT_RADIUS = 10;

    private final double DEFAULT_X_OFFSET = -5;

    private final double DEFAULT_Y_OFFSET = 4;

    private List<Shape> shapeList = new ArrayList<>();

    protected Tooltip tooltip;

    public AbstractNucleotideCircle(){
        super();
        this.elementGroup = new Group();
        this.circle = new Circle(DEFAULT_RADIUS);
        this.shadow = new Circle(DEFAULT_RADIUS+1);
        this.toolTipMask = new Circle(DEFAULT_RADIUS+1);
        this.base = new Text("N");
        this.shadow.setFill(Color.WHITE);
        this.base.setFill(Color.WHITE);
        this.base.getStyleClass().addAll("nucleotide_text");
        this.toolTipMask.setFill(Color.TRANSPARENT);
        this.elementGroup.getChildren().addAll(this.shadow,
                this.circle, this.base);
    }

    public AbstractNucleotideCircle(double x, double y){
        this();
        setCenter(x, y);
    }


    public double getX(){
        return this.circle.getCenterX();
    }

    public double getY(){
        return this.circle.getCenterY();
    }

    public void setCenter(double x, double y){
        this.circle.setCenterX(x);
        this.circle.setCenterY(y);
        this.shadow.setCenterX(x);
        this.shadow.setCenterY(y);
        this.toolTipMask.setCenterX(x);
        this.toolTipMask.setCenterY(y);
        this.base.setX(x + DEFAULT_X_OFFSET);
        this.base.setY(y + DEFAULT_Y_OFFSET);
    }

    public Group getNucleotide(){
        return this.elementGroup;
    }

    protected void setColor(Color color){
        this.circle.setFill(color);
    }


    abstract protected void setBaseType();

    abstract protected void setTooltip(String message);


}
