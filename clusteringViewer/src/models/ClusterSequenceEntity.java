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

    public int getSequenceLength(){return this.sequenceLength;}

    public double getSimilarityToRef(){return this.similarityToRef;}


}
