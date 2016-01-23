package models.nucleotide;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import models.misc.Atom;

import java.util.HashMap;

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

    @Override
    void evaluateModel() {
        boolean isFilledCompletely = true;
        for(float coordinate : atomCoords){
            if(coordinate == 0){
                isFilledCompletely = false;
                break;
            }
        }
        if(!(hBondMap.containsKey("H62") && hBondMap.containsKey("N6") && hBondMap.containsKey("N1"))){
            isFilledCompletely = false;
        }
        modelFilledComplete.setValue(isFilledCompletely);
    }



    @Override
    public int evaluateNumberHBonds(BaseModel otherBase){
        if(!otherBase.getClass().getName().contains("Uracil")){
            System.err.println(otherBase.getClass().getName());
            return -1;
        } else{
            for(String key : hBondMap.keySet()){
                System.err.println(key);
            }
            float prelDistance = hBondMap.get("H62").getDistanceTo(otherBase.hBondMap.get("O4"));
            System.err.println(prelDistance);
            if(prelDistance == 0 || prelDistance > 10 ){
                return -1;
            }
            return numberHBonds(otherBase.hBondMap);
        }
    }

    private int numberHBonds(HashMap<String, Atom> otherHBondMap){
        int numberHBonds = -1;
        System.err.println(hBondMap.get("H62"));
        float firstHBondDistance = hBondMap.get("H62").getDistanceTo(otherHBondMap.get("O4"));
        float secondHBondDistance = hBondMap.get("N1").getDistanceTo(otherHBondMap.get("H3"));
        if(firstHBondDistance >= 1.9f && firstHBondDistance <= 3.2f){
            if(secondHBondDistance >= 1.9f && secondHBondDistance <= 3.2f){
                numberHBonds = 2;
                double angle1 = hBondMap.get("H62").getAngle(otherHBondMap.get("O4"), hBondMap.get("N6"));
                double angle2 = otherHBondMap.get("H3").getAngle(otherHBondMap.get("N3"), hBondMap.get("N1"));
                System.err.println(angle1 + ":" + angle2);
                if(!isHbondAngle(angle1) || !isHbondAngle(angle2)){
                    numberHBonds = -1;
                }
            }
        }
        return numberHBonds;
    }




}
