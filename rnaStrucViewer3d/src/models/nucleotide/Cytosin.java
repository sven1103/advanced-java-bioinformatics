package models.nucleotide;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import models.misc.Atom;
import models.misc.Constants;

import java.util.HashMap;

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
        if(!(hBondMap.containsKey("N4") && hBondMap.containsKey("H41") &&
                hBondMap.containsKey("N3") && hBondMap.containsKey("O2"))){
            isFilledCompletely = false;
        }
        modelFilledComplete.setValue(isFilledCompletely);
    }


    @Override
    public int evaluateNumberHBonds(BaseModel otherBase){
        if(!otherBase.getClass().getName().contains("Guanine")){
            return -1;
        } else{
            float prelDistance = hBondMap.get("H41").getDistanceTo(otherBase.hBondMap.get("O6"));
            if(prelDistance == 0 || prelDistance > 10 ){
                return -1;
            }
            return numberHBonds(otherBase.hBondMap);
        }
    }


    private int numberHBonds(HashMap<String, Atom> otherHBondMap){
        int numberHBonds = -1;
        float firstHBondDistance = hBondMap.get("H41").getDistanceTo(otherHBondMap.get("O6"));
        float secondHBondDistance = hBondMap.get("N3").getDistanceTo(otherHBondMap.get("H1"));
        float thirdHBondDistance = hBondMap.get("O2").getDistanceTo(otherHBondMap.get("H21"));

        if(firstHBondDistance >= Constants.HBOND_MIN_DISTANCE && firstHBondDistance <= Constants.HBOND_MAX_DISTANCE){
            if(secondHBondDistance >= Constants.HBOND_MIN_DISTANCE && secondHBondDistance <= Constants.HBOND_MAX_DISTANCE){
                if(thirdHBondDistance >= Constants.HBOND_MIN_DISTANCE && secondHBondDistance <= Constants.HBOND_MAX_DISTANCE) {
                    numberHBonds = 3;
                    double angle1 = hBondMap.get("H41").getAngle(otherHBondMap.get("O6"), hBondMap.get("N4"));
                    double angle2 = otherHBondMap.get("H1").getAngle(otherHBondMap.get("N1"), hBondMap.get("N3"));
                    double angle3 = otherHBondMap.get("H21").getAngle(otherHBondMap.get("N2"), hBondMap.get("O2"));

                    if (!isHbondAngle(angle1) && !isHbondAngle(angle2) || !isHbondAngle(angle3)) {
                        numberHBonds = -1;
                    }
                }
            }
        }
        return numberHBonds;
    }
}
