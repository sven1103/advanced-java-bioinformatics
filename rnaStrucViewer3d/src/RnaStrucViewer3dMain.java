import models.PDBparser;

/**
 * Created by sven on 12/12/15.
 */
public class RnaStrucViewer3dMain {

    public static void main(String[] args){

        if (args.length != 1){
            System.err.println("Wrong number of arguments. Provide the pdb file");
            System.exit(1);
        }

        PDBparser parser = PDBparser.getInstance();

        parser.parsePDB(args[0]);

    }

}
