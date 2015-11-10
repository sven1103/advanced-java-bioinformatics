package models;


import javafx.collections.ObservableList;


import java.util.List;
import java.util.Observable;

/**
 * Created by fillinger on 11/7/15.
 */
public class ClusterViewerModel extends Observable {

    private List<ClusterContainer> clusterContainerList;

    public ClusterViewerModel(){}

    public void setClusterContainerList(List<ClusterContainer> clusterContainerList) {
        setChanged();
        this.clusterContainerList = clusterContainerList;
        notifyObservers();
    }

    public List getClusterContainerList(){
        return this.clusterContainerList;
    }


}
