package jcommander.controllers;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import jcommander.Main;
import jcommander.comparators.SizeComparator;
import jcommander.models.FileModelForApp;
import jcommander.utils.AppBundle;
import jcommander.utils.FilesUtils;

import java.io.File;
import java.io.IOException;

public class MainViewController {

    // Reference to the main application.
    private Main main;
    private StringProperty leftTablePath = new SimpleStringProperty();
    private StringProperty rightTablePath = new SimpleStringProperty();

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
    @FXML
    private javafx.scene.control.TableView<FileModelForApp> rightTable;
    @FXML
    private javafx.scene.control.TableColumn<FileModelForApp, String> nameColumnRight;
    @FXML
    private javafx.scene.control.TableColumn<FileModelForApp, String> sizeColumnRight;
    @FXML
    private javafx.scene.control.TableColumn<FileModelForApp, String> dateColumnRight;
    @FXML
    private Label rightTableLabel;
    @FXML
    private Label leftTableLabel;

    public void setMainApp(Main mainApp) {
        this.main = mainApp;
        try {
            //TODO: Change this to open good directory list
            leftTable.setItems(new FilesUtils().fileList(new File(".").getCanonicalPath()));
            leftTablePath.set(new File(".").getCanonicalPath());
            leftTableLabel.setText(leftTablePath.get());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            rightTable.setItems(new FilesUtils().fileList(new File(".").getCanonicalPath()));
            rightTablePath.set(new File(".").getCanonicalPath());
            rightTableLabel.setText(rightTablePath.get());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void objectInLeftListListener(FileModelForApp file) {
        System.out.println(file.getPathToFile());
        if (file.isDirectory()) {
            leftTable.setItems(new FilesUtils().fileList(file.getPathToFile()));
            leftTablePath.set(file.getPathToFile());
            leftTableLabel.textProperty().bind(leftTablePath);
        }
    }

    private void objectInRightListListener(FileModelForApp file) {
        System.out.println(file.getPathToFile());
        if (file.isDirectory()) {
            rightTable.setItems(new FilesUtils().fileList(file.getPathToFile()));
            rightTablePath.set(file.getPathToFile());
            rightTableLabel.textProperty().bind(rightTablePath);
        }
    }

    @FXML
    private void initialize() {
        initializeLeftTab();
        initializeRightTab();
    }

    private void initializeLeftTab() {
        nameColumnLeft.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        sizeColumnLeft.setCellValueFactory(cellData -> cellData.getValue().sizeProperty());
        dateColumnLeft.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        sizeColumnLeft.setComparator(new SizeComparator());
        leftTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        leftTable.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                FileModelForApp file = leftTable.getSelectionModel().getSelectedItem();
                if (file != null) {
                    objectInLeftListListener(file);
                }
            }
        });
    }

    private void initializeRightTab() {
        nameColumnRight.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        sizeColumnRight.setCellValueFactory(cellData -> cellData.getValue().sizeProperty());
        dateColumnRight.setCellValueFactory(cellData -> cellData.getValue().dateProperty());

        sizeColumnRight.setComparator(new SizeComparator());
        rightTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        rightTable.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                FileModelForApp file = rightTable.getSelectionModel().getSelectedItem();
                if (file != null) {
                    objectInRightListListener(file);
                }
            }
        });
    }

    @FXML
    private void closeApp(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void changeLanguageToEnglish(ActionEvent event) {
        AppBundle bundleUtil = AppBundle.getInstance();
        if (!bundleUtil.getCurrentLocale().getLanguage().equals("en")) {
            AppBundle.getInstance().setCurrentLocale("en");
        }
    }

    @FXML
    private void changeLanguageToPolish(ActionEvent event) {
        AppBundle bundleUtil = AppBundle.getInstance();
        if (!bundleUtil.getCurrentLocale().getLanguage().equals("pl")) {
            AppBundle.getInstance().setCurrentLocale("pl");
        }
    }

    @FXML
    private void createFile(ActionEvent event) {
        if (leftTable.isFocused()) {
            System.out.println("LEFT");
        } else if (rightTable.isFocused()) {
            System.out.println("RIGHT");
        }
    }
}
