package models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sven on 12/12/15.
 */
public class PDBparser {

    public static volatile PDBparser instance;

    private List<Atom> atomList;

    private PDBparser(){}

    public static PDBparser getInstance(){
        if(instance == null){
            synchronized (PDBparser.class){
                if(instance == null) {
                    instance = new PDBparser();
                }
            }
        }
        return instance;
    }


    public PDBparser parsePDB(String pdbFile){
        atomList = new ArrayList<>();

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(pdbFile))){
            String line;

            while((line = bufferedReader.readLine()) != null){
                String recordName = line.substring(PDBcolumns.RECORD_NAME_START, PDBcolumns.RECORD_NAME_END+1);
                if( !removeWhiteSpace(recordName.toLowerCase()).equals("atom")){
                    continue;
                }
                Atom atom = new Atom();

                String atomName = line.substring(PDBcolumns.ATOM_NAME_START, PDBcolumns.ATOM_NAME_END+1);
                String residueName = line.substring(PDBcolumns.RESIDUE_NAME_START, PDBcolumns.RESIDUE_NAME_END+1);
                String residuePos = line.substring(PDBcolumns.RESIDUE_SEQ_NUMBER_START, PDBcolumns.RESIDUE_SEQ_NUMBER_END+1);
                String xCoord = line.substring(PDBcolumns.ATOM_X_START, PDBcolumns.ATOM_X_END+1);
                String yCoord = line.substring(PDBcolumns.ATOM_Y_START, PDBcolumns.ATOM_Y_END+1);
                String zCoord = line.substring(PDBcolumns.ATOM_Z_START, PDBcolumns.ATOM_Z_END+1);

                atom.setAtomName(removeWhiteSpace(atomName));
                atom.setBaseType(evaluateBaseType(residueName));
                atom.setResiduePos(Integer.parseInt(removeWhiteSpace(residuePos)));
                atom.setCoords(new float[]{makeFloat(xCoord),
                                           makeFloat(yCoord),
                                           makeFloat(zCoord)});
                atomList.add(atom);
            }

        } catch (IOException e){
            System.err.println("Could not read from file: " + pdbFile);
        }

        return this;
    }


    public List<Atom> getAtomList(){
        return this.atomList;
    }

    private String removeWhiteSpace(String string){
        return string.replaceAll("\\s", "");
    }

    private Float makeFloat(String string){
        string = removeWhiteSpace(string);
        return Float.parseFloat(string);
    }

    private BaseType evaluateBaseType(String string){
        BaseType baseType = BaseType.N;
        string = removeWhiteSpace(string);
        switch(string){
            case "A":
                baseType = BaseType.A;
                break;
            case "ADE":
                baseType = BaseType.A;
                break;
            case "C":
                baseType = BaseType.C;
                break;
            case "CYT":
                baseType = BaseType.C;
                break;
            case "G":
                baseType = BaseType.G;
                break;
            case "GUA":
                baseType = BaseType.G;
                break;
            case "U":
                baseType = BaseType.U;
                break;
            case "URA":
                baseType = BaseType.U;
                break;
            default:
                baseType = BaseType.N;
        }
        return baseType;
    }




}
