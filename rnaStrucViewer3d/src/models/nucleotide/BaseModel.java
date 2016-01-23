package models.nucleotide;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.shape.MeshView;
import models.misc.Atom;
import models.misc.Constants;

import java.util.HashMap;

/**
 * Created by sven on 1/17/16.
 */
public class BaseModel implements Cloneable{

    protected MeshView meshView;

    BooleanProperty modelFilledComplete = new SimpleBooleanProperty(false);

    protected HashMap<String, Atom> hBondMap = new HashMap<>();

    protected float[] atomCoords;

    public BaseModel(){

    }


    public int evaluateNumberHBonds(BaseModel otherBase){
        return 0;
    }

    public MeshView getBase(){
        return this.meshView;
    }

    protected boolean isHbondAngle(double angle){
        boolean isHBondAngle = false;

        if(angle >= Constants.HBOND_MIN_ANGLE && angle <= Constants.HBOND_MAX_ANGLE){
            isHBondAngle = true;
        }
        return isHBondAngle;
    }

    public float[] getAtomCoords(){
        return this.atomCoords;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        BaseModel clone = (BaseModel) super.clone();
        clone.hBondMap = (HashMap<String, Atom>) this.hBondMap.clone();
        this.hBondMap.clear();
        return clone;
    }
}
