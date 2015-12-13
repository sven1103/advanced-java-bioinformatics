package models;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;

/**
 * Created by svenfillinger on 13.12.15.
 */
public class Guanine extends PurineModel {

    public Guanine(){
        super();
        this.material = new PhongMaterial(Color.YELLOW);
    }


    @Override
    public PurineModel setAtomCoords(Atom atom) {
        if(!atom.getBaseType().equals(BaseType.G)){
            return this;
        } else{
            makeAtomCoords(atom);
        }
        return this;
    }
}
