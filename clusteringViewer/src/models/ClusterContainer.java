package models;

import java.util.ArrayList;

/**
 * Created by fillinger on 11/9/15.
 */
public class ClusterContainer {

    private ClusterSequenceEntity referenceSequence;

    private ArrayList<ClusterSequenceEntity> clusterContent;


    public ClusterContainer(){
        this.clusterContent = new ArrayList<>();
    }

    public ClusterContainer(ClusterSequenceEntity refSeq){
        this.referenceSequence = refSeq;
        this.clusterContent = new ArrayList<>();
    }

    public void setReferenceSequence(ClusterSequenceEntity refSeq){
        this.referenceSequence = refSeq;
    }

    public ClusterSequenceEntity getReferenceSequence() throws NullPointerException{
        return this.referenceSequence;
    }

}
