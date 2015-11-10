package models;

/**
 * Created by fillinger on 11/9/15.
 */
public class ClusterSequenceEntity {

    private String sequenceID;
    private String strain;
    private int sequenceLength;
    private double similarityToRef;


    public ClusterSequenceEntity(String sequenceID,
                                 String strain){
        this.sequenceID = sequenceID;
        this.strain = strain;
    }


    public ClusterSequenceEntity(String sequenceID,
                                 String strain,
                                 int sequenceLength,
                                 double similarityToRef){
        this.sequenceID = sequenceID;
        this.strain = strain;
        this.sequenceLength = sequenceLength;
        this.similarityToRef = similarityToRef;
    }

    public String getSequenceID(){return this.sequenceID;}

    public String getStrain(){return this.strain;}

    public String getSequenceLength(){
        if(this.sequenceLength == 0){
            return "";
        }
        return Integer.toString(this.sequenceLength);
    }

    public String getSimilarityToRef(){
        if(this.similarityToRef == 0.0){
            return "";
        }
        return Double.toString(this.similarityToRef);
    }


}
