package models;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;

/**
 * Created by svenfillinger on 13.12.15.
 */
public class Adenine extends PurineModel {


    public Adenine(){
        super();
        this.material = new PhongMaterial(Color.GREEN);
    }

    @Override
    public PurineModel setAtomCoords(Atom atom) {
        if(!atom.getBaseType().equals(BaseType.A)){
            return this;
        } else{
            makeAtomCoords(atom);
        }

        return this;
    }
}
