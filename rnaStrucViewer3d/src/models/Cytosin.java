package models;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;

/**
 * Created by svenfillinger on 13.12.15.
 */
public class Cytosin extends PyrimidineModel {


    public Cytosin(){
        super();
        this.material = new PhongMaterial(Color.RED);
    }

    @Override
    public PyrimidineModel setAtomCoords(Atom atom) {

        System.out.println("CYTOSINE");
        System.out.println(atom.toString());
        if(!atom.getBaseType().equals(BaseType.C)){
            return this;
        } else {
            makeAtomCoords(atom);
        }
        return this;

    }
}
