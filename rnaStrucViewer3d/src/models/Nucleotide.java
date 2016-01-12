package models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.shape.MeshView;


/**
 * Created by sven on 1/12/16.
 */
public class Nucleotide {

    private BaseType baseType;

    private Node ribose;

    private Node base;

    private Group nucleotide;

    BooleanProperty modelFilled = new SimpleBooleanProperty(false);

    private int residuePosition;

    public Nucleotide(){
        this.nucleotide = new Group();
        this.residuePosition = -1;
        this.baseType = BaseType.N;
    }

    public Nucleotide setBaseType(BaseType n){
        this.baseType = n;
        return this;
    }

    public Nucleotide setRibose(Node ribose){
        this.ribose = ribose;
        evaluateModel();
        return this;
    }

    public Nucleotide setBase(Node base){
        this.base = base;
        evaluateModel();
        return this;
    }

    public Nucleotide setResiduePosition(int pos){
        this.residuePosition = pos;
        return this;
    }

    private void evaluateModel(){
        boolean isFilledCompletely = true;
        if(ribose == null || base == null){
           isFilledCompletely = false;
        }
        modelFilled.setValue(isFilledCompletely);
    }

    public void reset(){
        this.nucleotide = new Group();
        this.residuePosition = -1;
        this.baseType = BaseType.N;
        this.ribose = null;
        this.base = null;
    }

    public Group getNucleotide(){
        if(ribose != null && base != null){
            this.nucleotide.getChildren().addAll(ribose, base);
        }

        Tooltip tooltip = new Tooltip(this.baseType.toString() + this.residuePosition);
        Tooltip.install(this.nucleotide, tooltip);

        return nucleotide;
    }


}
