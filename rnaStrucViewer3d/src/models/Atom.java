package models;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by sven on 12/12/15.
 */
public class Atom {

    private int residuePos = 0;

    private float[] coords = {0f, 0f, 0f};

    private BaseType baseType = BaseType.N;

    private String atomName = "";

    public Atom(){}

    public Atom setResiduePos(int pos){
        this.residuePos = pos;
        return this;
    }

    public Atom setCoords(float[] coords) throws IOException{
        if (coords.length != 3){
            throw new IOException("Wrong size of coordinate array. Must be of size three.");
        } else{
            this.coords = coords;
        }
        return this;
    }

    public Atom setAtomName(String name){
        this.atomName = name;
        return this;
    }

    public Atom setBaseType(BaseType basteType){
        this.baseType = basteType;
        return this;
    }

    public int getResiduePos() {
        return residuePos;
    }

    public float[] getCoords() {
        return coords;
    }

    public BaseType getBaseType() {
        return baseType;
    }

    public String getAtomName() {
        return atomName;
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.atomName);
        stringBuilder.append(":");
        stringBuilder.append(this.baseType);
        stringBuilder.append(":");
        stringBuilder.append(Arrays.toString(this.coords));
        return stringBuilder.toString();
    }
}
