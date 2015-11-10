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
        treeTableView = new TreeTableView<>();
        root.setCenter(treeTableView);

        /**
         * Make the Notification area
         */
        label = new Label();
        root.setBottom(label);

        return new Scene(root, 500, 300);

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


    public void updateTableTreeView(){
        List<ClusterContainer> clusterContainerList = this.model.getClusterContainerList();

        TreeItem<ClusterSequenceEntity> fakeRoot = new TreeItem<>(new ClusterSequenceEntity("", "", 0, 0));

        int counter = 1;

        for(ClusterContainer container : clusterContainerList){
            TreeItem<ClusterSequenceEntity> clusterNode =
                    new TreeItem<>(new ClusterSequenceEntity("Cluster" + counter, "", 0, 0));
            counter++;
            List<ClusterSequenceEntity> clusterSequenceEntityList= container.getClusterContent();
            for(ClusterSequenceEntity entity : clusterSequenceEntityList){
                System.out.println(entity.getSequenceID());
                clusterNode.getChildren().add(new TreeItem<>(entity));
            }
            fakeRoot.getChildren().add(clusterNode);
        }

        TreeTableColumn<ClusterSequenceEntity, String> strainColumn =
                new TreeTableColumn<>("Strain");
        strainColumn.setPrefWidth(200);
        strainColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<ClusterSequenceEntity, String> param) ->
                        new ReadOnlyStringWrapper(param.getValue().getValue().getStrain())
        );

        TreeTableColumn<ClusterSequenceEntity, String> seqLengthCol =
                new TreeTableColumn<>("Sequence Length (nt)");
        strainColumn.setPrefWidth(50);
        strainColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<ClusterSequenceEntity, String> param) ->
                        new ReadOnlyStringWrapper(Integer.toString(param.getValue().getValue().getSequenceLength()))
        );

        TreeTableColumn<ClusterSequenceEntity, String> idColumn =
                new TreeTableColumn<>("ID");
        strainColumn.setPrefWidth(100);
        strainColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<ClusterSequenceEntity, String> param) ->
                        new ReadOnlyStringWrapper(param.getValue().getValue().getSequenceID())
        );

        TreeTableColumn<ClusterSequenceEntity, String> identityColumn =
                new TreeTableColumn<>("Identity(%)");
        strainColumn.setPrefWidth(50);
        strainColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<ClusterSequenceEntity, String> param) ->
                        new ReadOnlyStringWrapper(Double.toString(param.getValue().getValue().getSimilarityToRef()))
        );

        this.treeTableView.setRoot(fakeRoot);
        treeTableView.getColumns().setAll(strainColumn, idColumn, seqLengthCol, identityColumn);
        treeTableView.setShowRoot(false);
        label.setText("Geloaden :)))");

    }

    @Override
    public void update(java.util.Observable o, Object arg) {
        updateTableTreeView();

    }
}
