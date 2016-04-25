package jcommander.controllers;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import jcommander.Main;
import jcommander.models.FileModelForApp;

import javax.swing.table.TableColumn;
import javax.swing.text.TableView;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainViewController {

    // Reference to the main application.
    private Main main;

    public MainViewController() {
    }

    @FXML
    private javafx.scene.control.TableView<FileModelForApp> leftTable;
    @FXML
    private javafx.scene.control.TableColumn<FileModelForApp, String> nameColumnLeft;
    @FXML
    private javafx.scene.control.TableColumn<FileModelForApp, String> sizeColumnLeft;
    @FXML
    private javafx.scene.control.TableColumn<FileModelForApp, String> dateColumnLeft;

    public void fillTable() {
        String path = null;
        ObservableList<FileModelForApp> listOfFiles = FXCollections.observableArrayList();;
        try {
            path = new File(".").getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<File> fileList = fileList(path);
        for (File file : fileList) {
            System.out.println(file.getName());
            if (file.isDirectory()) {

            } else {
                listOfFiles.add(new FileModelForApp(file.getName(), Long.toString(file.getUsableSpace()), ""));
            }
        }
        leftTable.setItems(listOfFiles);
    }

    private static List<File> fileList(String directory) {
        List<File> fileList = new ArrayList<>();
        File path = new File(directory);
        if (path.isDirectory()) {
            File[] files = path.listFiles();
            for (File file : files) {
                fileList.add(file);
            }
        }
        return fileList;
    }

    public void setMainApp(Main mainApp) {
        this.main = mainApp;
        fillTable();
    }


    @FXML
    private void initialize() {
        nameColumnLeft.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        sizeColumnLeft.setCellValueFactory(cellData -> cellData.getValue().sizeProperty());
        dateColumnLeft.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
    }
}
