package models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by fillinger on 11/9/15.
 */
public class ClsrParser{

    public ClsrParser(){}

    /**
     * A parser for the CD-hit clsr output files. Creates a list cluster container, which contain
     * cluster sequence identities themselves.
     * @param fastaSequences
     * @param clusterFile
     * @return List<ClusterContainer>
     * @throws IOException
     */
    public List<ClusterContainer> parse(Map<String, String> fastaSequences, File clusterFile)
            throws IOException{
        List<ClusterContainer> clusterContainerList = new ArrayList<>();

        BufferedReader bufferedReader = new BufferedReader(new FileReader(clusterFile));

        /*
        Init some variables for the parsing step
         */
        String currentLine = "";
        Boolean readInCluster = false;
        ClusterContainer clusterContainer = new ClusterContainer();
        String lengthSequence;
        String id;
        String identity;

        /*
        Parse the cluster file
         */
        while ((currentLine = bufferedReader.readLine()) != null){
            if (currentLine.charAt(0) == '>' && !readInCluster){ // a Cluster notation begins
                clusterContainerList.add(clusterContainer);
                readInCluster = true;
                clusterContainer = new ClusterContainer();
                continue;
            } else {
                readInCluster = false;
            }
            String[] tempLength = currentLine.substring(0, currentLine.indexOf(",")-1).split("\t"); // extract the sequence length part
            lengthSequence = tempLength[tempLength.length-1]; // get the xxxxnt part
            lengthSequence = lengthSequence.replaceAll("n", "").replaceAll("t", ""); // remove 'nt' notation
            id = currentLine.substring(currentLine.indexOf('>')+1, currentLine.indexOf("."));

            // Get the strain from the FASTA HashMap
            String fastaStrain = fastaSequences.get(id);

            if (currentLine.contains("*")){ // this is the reference sequence of the cluster then
                identity = "100.00"; // the reference sequence has always 100% identity to itself ;)
                clusterContainer.setReferenceSequence(new ClusterSequenceEntity(id, fastaStrain,
                        Integer.valueOf(lengthSequence), Double.valueOf(identity)));
            }else {
                identity = currentLine.substring(currentLine.indexOf("/")+1, currentLine.indexOf("%"));
            }

            // Add the cluster-container to the list
            clusterContainer.addSequence(new ClusterSequenceEntity(id, fastaStrain,
                    Integer.valueOf(lengthSequence), Double.valueOf(identity)));

        }

    // returns the List of cluster containers
    return clusterContainerList;

    }
}
