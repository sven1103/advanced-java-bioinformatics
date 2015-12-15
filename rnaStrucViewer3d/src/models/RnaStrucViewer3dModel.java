package models;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.shape.MeshView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by svenfillinger on 13.12.15.
 */
public class RnaStrucViewer3dModel {

    private List<Atom> atomList = new ArrayList<>();

    private List<MeshView> riboseList = new ArrayList<>();

    private List<MeshView> baseList = new ArrayList<>();


    public RnaStrucViewer3dModel(){

    }


    public RnaStrucViewer3dModel setAtomList(List<Atom> atomList){
        this.atomList = atomList;
        return this;
    }


    /**
     * Makes a node group from the ribose molecules
     * @return A group containing all ribose molecules
     */
    public Group getRiboseGroup(){

        Group riboseGroup = new Group();

        for(MeshView element : riboseList){
            riboseGroup.getChildren().addAll(element);
        }
        return riboseGroup;
    }

    public Group getBaseGroup(){
        Group baseGroup = new Group();

        for(MeshView element : baseList){
            baseGroup.getChildren().addAll(element);
        }
        return baseGroup;
    }

    /**
     * Iterates through the atom list and builds the sugar molecules
     * @return this
     */
    public RnaStrucViewer3dModel parseRiboseElements(){
        RiboseModel riboseMolecule = new RiboseModel();
        Uracil uracil = new Uracil();
        Adenine adenine = new Adenine();
        Cytosin cytosine = new Cytosin();
        Guanine guanine = new Guanine();




        guanine.modelFilledComplete.addListener((observable, oldValue, newValue) -> {
            if(newValue){
                baseList.add(guanine.makeMesh().getBase());
                guanine.resetCoords();
                System.out.println("Full uracil");
            }
        });

        uracil.modelFilledComplete.addListener((observable, oldValue, newValue) -> {
            if(newValue){
                baseList.add(uracil.makeMesh().getBase());
                uracil.resetCoords();
                System.out.println("Full uracil");
            }
        });

        cytosine.modelFilledComplete.addListener((observable, oldValue, newValue) -> {
            if(newValue){
                baseList.add(cytosine.makeMesh().getBase());
                cytosine.resetCoords();
                System.out.println("Full cytosine");
            }
        });


        adenine.modelFilledComplete.addListener((observable, oldValue, newValue) -> {
            if(newValue){
                baseList.add(adenine.makeMesh().getBase());
                adenine.resetCoords();
                System.out.println("Full uracil");
            }
        });

        riboseMolecule.modelFilledComplete.addListener((observable, oldValue, newValue) -> {
            if(newValue){
                riboseList.add(riboseMolecule.makeRiboseMesh().getRibose());
                riboseMolecule.resetCoords();
                System.err.println("Full ribose");
            }
        });



        for(Atom atom : atomList){
            riboseMolecule.setAtomCoords(atom);

            switch(atom.getBaseType()){
                case A:
                    adenine.setAtomCoords(atom);
                    break;
                case C:
                    cytosine.setAtomCoords(atom);
                    break;
                case G:
                    guanine.setAtomCoords(atom);
                    break;
                case U:
                    uracil.setAtomCoords(atom);
            }
        }

        System.err.println(riboseMolecule.modelFilledComplete.get());

        System.err.println("[NOTE]: Ribose sugars parsed");


     return this;
    }



}
