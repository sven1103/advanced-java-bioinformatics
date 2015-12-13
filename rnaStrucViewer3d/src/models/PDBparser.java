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

    /**
     * The current parser instance
     */
    public static volatile PDBparser instance;

    /**
     * Will store the parsed atoms
     */
    private List<Atom> atomList;

    /**
     * Make the constructor private
     */
    private PDBparser(){}

    /**
     * Singleton getInstance()
     * @return PDPparser
     */
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

    /**
     * Parses a pdb-file containing RNA structures
     * @param pdbFile The path to the pdb-file
     * @return this
     */
    public PDBparser parsePDB(String pdbFile){

        atomList = new ArrayList<>();   // Will store the atoms

        /*
        Read in the pdb file
         */
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(pdbFile))){
            String line;

            while((line = bufferedReader.readLine()) != null){
                // Get the record name (i.e. 'ATOM')
                String recordName = line.substring(PDBcolumns.RECORD_NAME_START, PDBcolumns.RECORD_NAME_END+1);
                // Check if line is of record type 'ATOM'
                if( !removeWhiteSpace(recordName.toLowerCase()).equals("atom")){
                    continue;
                }

                Atom atom = new Atom();

                /*
                Extract the important information, such as residue name, position and coordinates
                 */
                String atomName = line.substring(PDBcolumns.ATOM_NAME_START, PDBcolumns.ATOM_NAME_END+1);
                String residueName = line.substring(PDBcolumns.RESIDUE_NAME_START, PDBcolumns.RESIDUE_NAME_END+1);
                String residuePos = line.substring(PDBcolumns.RESIDUE_SEQ_NUMBER_START, PDBcolumns.RESIDUE_SEQ_NUMBER_END+1);
                String xCoord = line.substring(PDBcolumns.ATOM_X_START, PDBcolumns.ATOM_X_END+1);
                String yCoord = line.substring(PDBcolumns.ATOM_Y_START, PDBcolumns.ATOM_Y_END+1);
                String zCoord = line.substring(PDBcolumns.ATOM_Z_START, PDBcolumns.ATOM_Z_END+1);

                /*
                Fill the atom with the parsed information
                 */
                atom.setAtomName(removeWhiteSpace(atomName));
                atom.setBaseType(evaluateBaseType(residueName));
                atom.setResiduePos(Integer.parseInt(removeWhiteSpace(residuePos)));
                atom.setCoords(new float[]{makeFloat(xCoord),
                                           makeFloat(yCoord),
                                           makeFloat(zCoord)});
                // Add the atom to the list
                atomList.add(atom);
            }

        } catch (IOException e){
            System.err.println("Could not read from file: " + pdbFile);
        }
        return this;
    }

    /**
     * Aquire the atom list
     * @return
     */
    public List<Atom> getAtomList(){
        return this.atomList;
    }

    /**
     * Helper function for removing whitespaces in strings
     * @param string The target string
     * @return Formatted string
     */
    private String removeWhiteSpace(String string){
        return string.replaceAll("\\s", "");
    }

    /**
     * Helper function for converting a string to float
     * @param string The target string
     * @return The parsed float
     */
    private Float makeFloat(String string){
        string = removeWhiteSpace(string);
        return Float.parseFloat(string);
    }

    /**
     * Determines the base type and sets an
     * enum type for robustness.
     * @param string The string to be checked
     * @return The parsed BaseType enum
     */
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
