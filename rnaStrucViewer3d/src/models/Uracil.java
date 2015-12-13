package models;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;

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


}
