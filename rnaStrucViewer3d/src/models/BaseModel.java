package models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.shape.MeshView;

import java.util.HashMap;
import java.util.HashSet;

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

        if(angle >= 130.0 && angle <= 180){
            isHBondAngle = true;
        }
        return isHBondAngle;
    }

    public float[] getAtomCoords(){
        return this.atomCoords;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        BaseModel clone = (BaseModel) super.clone();
        clone.hBondMap = (HashMap<String, Atom>) this.hBondMap.clone();
        System.err.println("Size: " + clone.hBondMap.size());
        this.hBondMap.clear();
        System.err.println("Size new: " + clone.hBondMap.size() + ": " + this.hBondMap.size());
        return clone;
    }
}
