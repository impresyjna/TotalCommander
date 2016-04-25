package jcommander.controllers;

import javafx.fxml.FXML;
import jcommander.Main;
import jcommander.models.FileModelForApp;
import jcommander.utils.FilesUtils;

import java.io.File;
import java.io.IOException;

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

    public void setMainApp(Main mainApp) {
        this.main = mainApp;
        try {
            leftTable.setItems(new FilesUtils().fileList(new File(".").getCanonicalPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void initialize() {
        nameColumnLeft.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        sizeColumnLeft.setCellValueFactory(cellData -> cellData.getValue().sizeProperty());
        dateColumnLeft.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
    }
}
