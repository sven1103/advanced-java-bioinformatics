package models;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;

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

    /*@Override
    public int evaluateNumberHBonds(BaseModel otherBase){
        if(!otherBase.getClass().getName().equalsIgnoreCase("Adenine")){
            return -1;
        } else{
            float prelDistance = hBondMap.get("H6").getDistanceTo(otherBase.hBondMap.get("O4"));
            if(prelDistance == 0 || prelDistance > 10 ){
                return -1;
            }
            return numberHBonds(otherBase.hBondMap);
        }
    }

    private int numberHBonds(HashMap<String, Atom> otherHBondMap){
        int numberHBonds = -1;
        float firstHBondDistance = hBondMap.get("H6").getDistanceTo(otherHBondMap.get("O4"));
        float secondHBondDistance = hBondMap.get("N1").getDistanceTo(otherHBondMap.get("H3"));
        if(firstHBondDistance >= 2.4f && firstHBondDistance <= 3.2f){
            if(secondHBondDistance >= 2.4f && secondHBondDistance <= 3.2f){
                numberHBonds = 2;
                double angle1 = hBondMap.get("H6").getAngle(otherHBondMap.get("O4"), hBondMap.get("N6"));
                double angle2 = otherHBondMap.get("H3").getAngle(otherHBondMap.get("N3"), hBondMap.get("N1"));

                if(!isHbondAngle(angle1) || !isHbondAngle(angle2)){
                    numberHBonds = -1;
                }
            }
        }
        return numberHBonds;
    }*/

}
