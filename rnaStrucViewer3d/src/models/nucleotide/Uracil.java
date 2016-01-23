package models.nucleotide;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import models.misc.Atom;

import java.util.HashMap;

/**
 * Created by svenfillinger on 13.12.15.
 */
public class Uracil extends PyrimidineModel{

    public Uracil(){
        super();
        this.material = new PhongMaterial(Color.GRAY);
    }


    @Override
    public PyrimidineModel setAtomCoords(Atom atom) {
        if(!atom.getBaseType().equals(BaseType.U)){
            return this;
        } else{
            makeAtomCoords(atom);
        }
        return this;
    }

    @Override
    void evaluateModel() {
        boolean isFilledCompletely = true;
        for(float coordinate : atomCoords){
            if(coordinate == 0){
                isFilledCompletely = false;
                break;
            }
        }
        if(!(hBondMap.containsKey("O4") && hBondMap.containsKey("N3") && hBondMap.containsKey("H3"))){
            isFilledCompletely = false;
        }
        modelFilledComplete.setValue(isFilledCompletely);
    }

}
