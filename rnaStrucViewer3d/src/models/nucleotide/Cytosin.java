package models.nucleotide;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import models.misc.Atom;

/**
 * Created by svenfillinger on 13.12.15.
 */
public class Cytosin extends PyrimidineModel {


    public Cytosin(){
        super();
        this.material = new PhongMaterial(Color.RED);
    }

    @Override
    public int evaluateNumberHBonds(BaseModel otherBase) {
        return 0;
    }

    @Override
    public PyrimidineModel setAtomCoords(Atom atom) {

        if(!atom.getBaseType().equals(BaseType.C)){
            return this;
        } else {
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
        if(hBondMap.size() < 0){
            isFilledCompletely = false;
        }
        modelFilledComplete.setValue(isFilledCompletely);
    }
}
