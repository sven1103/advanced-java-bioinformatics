package controllers;

import controllers.IClusterViewerNotifications;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import models.ClusterViewerModel;
import models.FastaParser;
import views.ClusterViewerView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by fillinger on 11/7/15.
 */
public class ClusterViewerController implements IClusterViewerNotifications {

    private ClusterViewerModel model;
    private ClusterViewerView view;
    private File clusterFile;
    private File fastaFile;

    public ClusterViewerController(ClusterViewerModel model, ClusterViewerView view){
        this.model = model;
        this.view = view;
    }


    public Scene buildScene(){
        return this.view.buildScene();
    }

    public void initViewControls(Stage stage){
        view.getOpenMenu().setOnAction((value) -> {
            clusterFile = view.getFileChooser().showOpenDialog(stage);
            if (clusterFile != null){
                findAndSetFASTA(clusterFile);
            }
        });
        view.getExitMenu().setOnAction((value) -> Platform.exit());
    }

    @Override
    public void printConsoleStatus(String message) {
        System.out.println(message);
    }

    @Override
    public void printConsoleError(String message) {
        System.err.println(message);
    }

    @Override
    public void printStatusDialog(String message) {
        this.view.setAlertWindow(new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK));
    }

    @Override
    public void printStatusError(String message) {
        this.view.setAlertWindow(new Alert(Alert.AlertType.ERROR, message, ButtonType.OK));
    }

    /**
     * Scans the directory of the cluster-file and searches for a matching
     * FASTA file. If not found, the method returns FALSE, TRUE if file found
     * and the variable of the fasta file is set.
     * @param clusterFile
     * @return
     */
    public Boolean findAndSetFASTA(File clusterFile){
        Boolean containsMatchingFastaFile = false; // return value
        File directory = new File(clusterFile.getAbsolutePath().substring(0,
                clusterFile.getAbsolutePath().lastIndexOf(File.separator)));
        String clusterFileName = clusterFile.getName();

        ArrayList<File> foundFastaFiles = new ArrayList();

        /*
        Scans the directory and extracts FASTA files
         */
        for(File file : directory.listFiles()){
            String fileExtension = "";
            String name = file.getName();
            try{
                fileExtension = name.substring(name.lastIndexOf(".") + 1);
            } catch (Exception e){
            }
            if(fileExtension.toLowerCase().equals("fasta")){
                foundFastaFiles.add(file);
            }
        }

        /*
        Check in the list of FASTA files, if a matching filename corresponding
        to the cluster filename is contained
         */
        if (foundFastaFiles.isEmpty()){
            printStatusError("Sorry, could not find any matching \n.fasta file " +
                    "in the directory \n" + directory.getAbsolutePath());
        } else{
            for (File file : foundFastaFiles){
                String fileName = file.getName().substring(0, file.getName().lastIndexOf("."));
                if (clusterFileName.toLowerCase().contains(fileName.toLowerCase())){
                    fastaFile = file;
                    printConsoleStatus("matching FASTA file found: " + fastaFile.getName());
                    containsMatchingFastaFile = true;
                    try{
                        FastaParser.parseFASTA(fastaFile);
                    } catch (IOException e){
                        printStatusError("Fasta file format is broken, could not read from file.");
                    }
                }
            }

        }
        return containsMatchingFastaFile;
    }


}
