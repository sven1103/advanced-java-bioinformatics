package models.nucleotide;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import models.misc.Atom;

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

    @Override
    void evaluateModel() {
        boolean isFilledCompletely = true;
        for(float coordinate : atomCoords){
            if(coordinate == 0){
                isFilledCompletely = false;
                break;
            }
        }
        if(!(hBondMap.containsKey("O6") && hBondMap.containsKey("N1") &&
                hBondMap.containsKey("H1") && hBondMap.containsKey("N2") &&
                hBondMap.containsKey("H21"))){
            isFilledCompletely = false;
        }
        modelFilledComplete.setValue(isFilledCompletely);
    }
}
