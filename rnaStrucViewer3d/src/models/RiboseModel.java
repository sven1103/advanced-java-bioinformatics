package models;

import javafx.scene.shape.MeshView;

import java.util.Arrays;

/**
 * Created by sven on 12/12/15.
 */
public class RiboseModel {

    private MeshView meshView;

    private final int NUMBER_ATOMS = 15;

    private float[] atomCoords = new float[NUMBER_ATOMS];

    public RiboseModel(){

    }

    public RiboseModel setAtomCoords(Atom atom){
        if(!AtomMapping.RIBOSE_MAPPING.containsKey(atom.getAtomName())){
            System.err.println("This atom is not located in ribose-sugar.");
            System.err.println(atom.toString());
        } else{
            int position = AtomMapping.RIBOSE_MAPPING.get(atom.getAtomName());
            System.arraycopy(atom.getCoords(), 0, this.atomCoords, position, 3);
        }
        return this;
    }

    public RiboseModel makeRiboseMesh(){
        System.err.println(Arrays.toString(atomCoords));
        return this;
    }





}
