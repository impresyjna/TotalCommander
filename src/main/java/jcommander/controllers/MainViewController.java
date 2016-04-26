package jcommander.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

    public void setMainApp(Main mainApp) {
        this.main = mainApp;
        new Thread() {
            public void run() {
                try

                {
                    //TODO: Change this to open good directory list
                    leftTable.setItems(new FilesUtils().fileList(new File(".").getCanonicalPath()));
                } catch (
                        IOException e
                        )

                {
                    e.printStackTrace();
                }

                try

                {
                    rightTable.setItems(new FilesUtils().fileList(new File(".").getCanonicalPath()));
                } catch (
                        IOException e
                        )

                {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @FXML
    private void initialize() {
        nameColumnLeft.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        sizeColumnLeft.setCellValueFactory(cellData -> cellData.getValue().sizeProperty());
        dateColumnLeft.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        nameColumnRight.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        sizeColumnRight.setCellValueFactory(cellData -> cellData.getValue().sizeProperty());
        dateColumnRight.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        sizeColumnLeft.setComparator(new SizeComparator());
        sizeColumnRight.setComparator(new SizeComparator());
    }

    @FXML
    private void closeApp(ActionEvent event){
        Platform.exit();
    }

    @FXML
    private void changeLanguageToEnglish(ActionEvent event) {
        AppBundle bundleUtil = AppBundle.getInstance();
        if(!bundleUtil.getCurrentLocale().getLanguage().equals("en")) {
            AppBundle.getInstance().setCurrentLocale("en");
        }
    }

    @FXML
    private void changeLanguageToPolish(ActionEvent event) {
        AppBundle bundleUtil = AppBundle.getInstance();
        if(!bundleUtil.getCurrentLocale().getLanguage().equals("pl")) {
            AppBundle.getInstance().setCurrentLocale("pl");
        }
    }
}
