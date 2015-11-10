package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fillinger on 11/9/15.
 */
public class ClusterContainer {

    private ClusterSequenceEntity referenceSequence;

    private List<ClusterSequenceEntity> clusterContent;


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

    public void addSequence(ClusterSequenceEntity entity){
        this.clusterContent.add(entity);
    }

    public List getClusterContent(){
        return this.clusterContent;
    }


}
