package models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

import java.util.Arrays;

/**
 * Created by sven on 12/12/15.
 */
public class RiboseModel {

    private MeshView meshView;

    private final int NUMBER_ATOMS = 15;

    private float[] atomCoords = new float[NUMBER_ATOMS];

    BooleanProperty modelFilledComplete = new SimpleBooleanProperty(false);


    private float[] texCoords = new float[]
            {
                    0, 1, // 1st triangle point
                    0.5f, 0.5f, // 2nd triangle point
                    0.5f, 1, // 3rd triangle point
            };

    private int[] faces = new int[]
            {
                    3, 0, 2, 1, 4, 2,  // font face triangle 1
                    3, 0, 4, 2, 2, 1,  // back face triangle 1
                    2, 0, 1, 1, 4, 2,  // font face triangle 2
                    2, 0, 4, 2, 1, 1,  // back face triangle 2
                    1, 0, 0, 1, 4, 2,  // font face triangle 3
                    1, 0, 4, 2, 0, 1   // back face triangle 3
        };


    public RiboseModel(){

    }

    public RiboseModel setAtomCoords(Atom atom){
        if(!AtomMapping.RIBOSE_MAPPING.containsKey(atom.getAtomName())){
        } else{
            int position = AtomMapping.RIBOSE_MAPPING.get(atom.getAtomName());
            System.arraycopy(atom.getCoords(), 0, this.atomCoords, position, 3);
            evaluateModel();
        }
        return this;
    }


    private void evaluateModel(){
        boolean isFilledCompletely = true;
        for(float coordinate : atomCoords){
            if(coordinate == 0){
                isFilledCompletely = false;
                break;
            }
        }
        modelFilledComplete.setValue(isFilledCompletely);
    }


    /**
     * Make the MeshView of the ribose molecule
     * @return The MeshView representation of ribose
     */
    public RiboseModel makeRiboseMesh(){
        System.err.println(Arrays.toString(atomCoords));


        TriangleMesh mesh = new TriangleMesh();

        mesh.getPoints().addAll(atomCoords);
        mesh.getTexCoords().addAll(texCoords);
        mesh.getFaces().addAll(faces);
        mesh.getFaceSmoothingGroups().addAll(0, 1, 0, 1, 0, 1);

        MeshView meshView = new MeshView();
        meshView.setMesh(mesh);
        meshView.setMaterial(new PhongMaterial(Color.LIGHTSKYBLUE));


        //sugarMeshView.setTranslateZ();
        this.meshView = meshView;
        return this;
    }


    public MeshView getRibose(){
        return this.meshView;
    }

    public void resetCoords(){
        this.atomCoords = new float[NUMBER_ATOMS];
    }





}
