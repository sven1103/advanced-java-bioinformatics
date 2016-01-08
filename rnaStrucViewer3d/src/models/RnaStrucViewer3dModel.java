package models;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Sphere;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by svenfillinger on 13.12.15.
 */
public class RnaStrucViewer3dModel {

    private List<Atom> atomList = new ArrayList<>();

    private List<MeshView> riboseList = new ArrayList<>();

    private List<MeshView> baseList = new ArrayList<>();

    private List<Cylinder> bondList = new ArrayList<>();

    private List<Sphere> phosphateList = new ArrayList<>();

    /**
     * Nullary constructor
     */
    public RnaStrucViewer3dModel(){}

    /**
     * Set the atom list in the model
     * @param atomList A list of atoms
     * @return A model of type RnaStrucViewer3dModel
     */
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

    /**
     * Makes a group node from the base molecules
     * @return A group containing all base molecules
     */
    public Group getBaseGroup(){
        Group baseGroup = new Group();

        for(MeshView element : baseList){
            baseGroup.getChildren().addAll(element);
        }
        return baseGroup;
    }

    /**
     * Makes a group node from the covalent bondList
     * @return A group containing all covalent bondList
     */
    public Group getBoundsGroup(){
        Group boundsGroup = new Group();

        for(Cylinder bond : bondList){
            boundsGroup.getChildren().addAll(bond);
        }
        return boundsGroup;
    }

    /**
     * Makes a group node from the list of all phosphateList
     * @return A group containing all covalent bondList
     */
    public Group getPhosphateGroup(){
        Group phosphateGroup = new Group();

        for(Sphere phosphate : phosphateList){
            phosphateGroup.getChildren().addAll(phosphate);
        }

        return phosphateGroup;
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

        Atom lastPhosphateAtom = new Atom();

        CovalentBond baseSugarBond = new CovalentBond();
        CovalentBond threePrimeOP = new CovalentBond();
        CovalentBond fivePrimeOP = new CovalentBond();
        CovalentBond phosphateConnection = new CovalentBond();
        phosphateConnection.setColor(Color.LIGHTGOLDENRODYELLOW);

        /*
        Set the model change listener for all base types, covalent bondList
        and phosphate-links
         */
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
                bondList.add(baseSugarBond.createConnection(0.02));
                baseSugarBond.resetBond();
            }
        });

        phosphateConnection.fullBondSet.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                bondList.add(phosphateConnection.createConnection(0.25));
                phosphateConnection.resetBond();
            }
        });

        threePrimeOP.fullBondSet.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                bondList.add(threePrimeOP.createConnection(0.02));
                threePrimeOP.resetBond();
            }
        });

        fivePrimeOP.fullBondSet.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                bondList.add(fivePrimeOP.createConnection(0.02));
                fivePrimeOP.resetBond();
            }
        });


        /*
        Iterate through the atom list and build the bases, sugars, bonds and phosphates
         */
        for(Atom atom : atomList){
            riboseMolecule.setAtomCoords(atom);

            if(atom.getAtomName().equals("C1'")){
                baseSugarBond.setStartAtom(atom.getCoords());
            }

            if(atom.getAtomName().equals("P")){
                lastPhosphateAtom = atom;
                phosphateList.add(new Phosphate(atom));
                threePrimeOP.setEndAtom(atom.getCoords());
                fivePrimeOP.setStartAtom(atom.getCoords());

                if(phosphateConnection.getStartAtom() == null){
                    phosphateConnection.setStartAtom(atom.getCoords());
                } else {
                    phosphateConnection.setEndAtom(atom.getCoords());
                    phosphateConnection.setStartAtom(atom.getCoords());
                }
                continue;
            }

            if(atom.getAtomName().equals("C4'") && (atom.getResiduePos() == lastPhosphateAtom.getResiduePos())) {
                fivePrimeOP.setEndAtom(atom.getCoords());
                continue;
            }

            if(atom.getAtomName().equals("C3'")) {
                threePrimeOP.setStartAtom(atom.getCoords());
                continue;
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
