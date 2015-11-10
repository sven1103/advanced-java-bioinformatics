package views;


import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import models.ClusterContainer;
import models.ClusterSequenceEntity;
import models.ClusterViewerModel;

import java.util.List;
import java.util.Observer;


/**
 * Created by fillinger on 11/7/15.
 */
public class ClusterViewerView implements Observer {
    private Alert alertWindow;
    private BorderPane root;
    private Menu menu;
    private MenuBar menuBar;
    private MenuItem openMenuI;
    private MenuItem exitMenuI;
    private Label label;
    private TreeTableView<ClusterSequenceEntity> treeTableView;

    private FileChooser fileChooser;

    private ClusterViewerModel model;

    public ClusterViewerView(ClusterViewerModel model){
        this.model = model;
    }

    public Scene buildScene() {
        root = new BorderPane();

        /**
         * Make the MenuBar
         */
        menu = new Menu("File");
        openMenuI = new MenuItem("Open File");
        exitMenuI = new MenuItem("Exit");

        menu.getItems().addAll(openMenuI, exitMenuI);
        menuBar = new MenuBar();
        menuBar.getMenus().addAll(menu);

        fileChooser = new FileChooser();
        root.setTop(menuBar);

        /**
         * Make the Content
         */
        //treeTableView = new TreeTableView<>();
        root.setCenter(treeTableView);

        /**
         * Make the Notification area
         */
        label = new Label();
        root.setBottom(label);

        return new Scene(root, 850, 500);

    }

    public void setAlertWindow(Alert alert){
        this.alertWindow = alert;
        this.alertWindow.showAndWait();
    }

    public ButtonType getAlertWindowResult() throws NullPointerException{
        return alertWindow.getResult();
    }

    public MenuItem getOpenMenu(){
        return this.openMenuI;
    }

    public FileChooser getFileChooser(){
        return this.fileChooser;
    }

    public MenuItem getExitMenu(){
        return this.exitMenuI;
    }

    /**
     * Updates the TableTreeView, is called once the model has changed
     * (e.g. user has load another cluster)
     */
    public void updateTableTreeView(){
        List<ClusterContainer> clusterContainerList = this.model.getClusterContainerList();

        final TreeItem<ClusterSequenceEntity> fakeRoot = new TreeItem<>(new ClusterSequenceEntity("", "", 0, 0));

        int counter = 1;

        /*
         * Iterate through the container list of ClusterContainers and
         * make new parent nodes for each cluster.
         */
        for(ClusterContainer container : clusterContainerList){
            TreeItem<ClusterSequenceEntity> clusterNode =
                    new TreeItem<>(new ClusterSequenceEntity("", "Cluster " + counter));
            counter++;
            List<ClusterSequenceEntity> clusterSequenceEntityList= container.getClusterContent();
            /*
             * Add
             */
            for(ClusterSequenceEntity entity : clusterSequenceEntityList){
                //System.out.println(entity.getSequenceID() + entity.getStrain() + entity.getSimilarityToRef());
                clusterNode.getChildren().add(new TreeItem<>(entity));
            }
            fakeRoot.getChildren().add(clusterNode);
        }

        /*
         * define the strain column
         */
        TreeTableColumn<ClusterSequenceEntity, String> strainColumn =
                new TreeTableColumn<>("Strain");
        strainColumn.setPrefWidth(400);
        strainColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<ClusterSequenceEntity, String> param) ->
                        new ReadOnlyStringWrapper(param.getValue().getValue().getStrain())
        );

        /*
         * define the sequence length column
         */
        TreeTableColumn<ClusterSequenceEntity, String> seqLengthCol =
                new TreeTableColumn<>("Sequence Length (nt)");
        seqLengthCol.setPrefWidth(150);
        seqLengthCol.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<ClusterSequenceEntity, String> param) ->
                        new ReadOnlyStringWrapper((param.getValue().getValue().getSequenceLength()))
        );


        /*
         * define the id column
         */
        TreeTableColumn<ClusterSequenceEntity, String> idColumn =
                new TreeTableColumn<>("ID");
        idColumn.setPrefWidth(150);
        idColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<ClusterSequenceEntity, String> param) ->
                        new ReadOnlyStringWrapper(param.getValue().getValue().getSequenceID())
        );

        /*
         * define the identity column
         */
        TreeTableColumn<ClusterSequenceEntity, String> identityColumn =
                new TreeTableColumn<>("Identity(%)");
        identityColumn.setPrefWidth(150);
        identityColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<ClusterSequenceEntity, String> param) ->
                        new ReadOnlyStringWrapper((param.getValue().getValue().getSimilarityToRef()))
        );

        this.treeTableView = new TreeTableView<>();
        treeTableView.setRoot(fakeRoot);
        treeTableView.getColumns().setAll(strainColumn, seqLengthCol, identityColumn, idColumn);
        treeTableView.setShowRoot(false);
        root.setCenter(treeTableView);
        label.setText("Loaded File");

    }

    @Override
    public void update(java.util.Observable o, Object arg) {
        updateTableTreeView();

    }
}
