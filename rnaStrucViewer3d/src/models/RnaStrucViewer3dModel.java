package models;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.shape.Cylinder;
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

    private List<Cylinder> bonds = new ArrayList<>();

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

    public Group getBoundsGroup(){
        Group boundsGroup = new Group();

        for(Cylinder bond : bonds){
            boundsGroup.getChildren().addAll(bond);
        }
        return boundsGroup;
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
        CovalentBond baseSugarBond = new CovalentBond();

        guanine.modelFilledComplete.addListener((observable, oldValue, newValue) -> {
            if(newValue){
                baseList.add(guanine.makeMesh().getBase());
                guanine.resetCoords();
            }
        });

        uracil.modelFilledComplete.addListener((observable, oldValue, newValue) -> {
            if(newValue){
                baseList.add(uracil.makeMesh().getBase());
                uracil.resetCoords();
            }
        });

        cytosine.modelFilledComplete.addListener((observable, oldValue, newValue) -> {
            if(newValue){
                baseList.add(cytosine.makeMesh().getBase());
                cytosine.resetCoords();
            }
        });


        adenine.modelFilledComplete.addListener((observable, oldValue, newValue) -> {
            if(newValue){
                baseList.add(adenine.makeMesh().getBase());
                adenine.resetCoords();
            }
        });

        riboseMolecule.modelFilledComplete.addListener((observable, oldValue, newValue) -> {
            if(newValue){
                riboseList.add(riboseMolecule.makeRiboseMesh().getRibose());
                riboseMolecule.resetCoords();
            }
        });


        baseSugarBond.fullBondSet.addListener((observable, oldValue, newValue) -> {
            if(newValue){
                bonds.add(baseSugarBond.createConnection(0.02));
                baseSugarBond.resetBond();
                System.out.println("Full Bond set");
            }
        });



        for(Atom atom : atomList){
            riboseMolecule.setAtomCoords(atom);

            if(atom.getAtomName().equals("C1'")){
                System.err.println("C1' found!");
                baseSugarBond.setStartAtom(atom.getCoords());
            }

            switch(atom.getBaseType()){
                case A:
                    adenine.setAtomCoords(atom);
                    if(atom.getAtomName().equals("N9")){
                        baseSugarBond.setEndAtom(atom.getCoords());
                    }
                    break;
                case C:
                    cytosine.setAtomCoords(atom);
                    if(atom.getAtomName().equals("N1")){
                        baseSugarBond.setEndAtom(atom.getCoords());
                    }
                    break;
                case G:
                    guanine.setAtomCoords(atom);
                    if(atom.getAtomName().equals("N9")){
                        baseSugarBond.setEndAtom(atom.getCoords());
                    }
                    break;
                case U:
                    uracil.setAtomCoords(atom);
                    if(atom.getAtomName().equals("N1")){
                        baseSugarBond.setEndAtom(atom.getCoords());
                    }
                    break;
            }
        }



     return this;
    }



}
