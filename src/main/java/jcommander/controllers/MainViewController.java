package jcommander.controllers;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import jcommander.Main;
import jcommander.comparators.DateComparator;
import jcommander.comparators.NameComparator;
import jcommander.comparators.SizeComparator;
import jcommander.models.FileModelForApp;
import jcommander.operations.CopyFile;
import jcommander.operations.DeleteFile;
import jcommander.operations.FileOperation;
import jcommander.operations.MoveFile;
import jcommander.tasks.FileOperationTask;
import jcommander.utils.AppBundle;
import jcommander.utils.DialogUtil;
import jcommander.utils.FilesUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

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
    private TextField rightTablePathInputOutput;
    @FXML
    private Label rightTableLabel;
    @FXML
    private TextField leftTablePathInputOutput;
    @FXML
    private Label leftTableLabel;

    public void setMainApp(Main mainApp) {
        this.main = mainApp;
        try {
            leftTable.setItems(new FilesUtils().fileList(new File(".").getCanonicalPath()));
            leftTablePath.set(new File(".").getCanonicalPath());
            leftTableLabel.setText(leftTablePath.get());
            leftTablePathInputOutput.setText(leftTablePath.get());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            rightTable.setItems(new FilesUtils().fileList(new File(".").getCanonicalPath()));
            rightTablePath.set(new File(".").getCanonicalPath());
            rightTableLabel.setText(rightTablePath.get());
            rightTablePathInputOutput.setText(rightTablePath.get());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void objectInLeftListListener(FileModelForApp file) {
        if (file.getFileInModel().isDirectory()) {
            leftTable.setItems(new FilesUtils().fileList(file.getFileInModel().getPath()));
            leftTablePath.set(file.getFileInModel().getPath());
            leftTablePathInputOutput.textProperty().bind(leftTablePath);
        }
    }

    private void objectInRightListListener(FileModelForApp file) {
        if (file.getFileInModel().isDirectory()) {
            rightTable.setItems(new FilesUtils().fileList(file.getFileInModel().getPath()));
            rightTablePath.set(file.getFileInModel().getPath());
            rightTablePathInputOutput.textProperty().bind(rightTablePath);
        }
    }

    public void refreshLists(){
        leftTable.setItems(new FilesUtils().fileList(leftTablePath.get()));
        rightTable.setItems(new FilesUtils().fileList(rightTablePath.get()));
    }

    private void fileLeftChosen(FileModelForApp fileModelForApp){
        StringProperty fileName = new SimpleStringProperty();
        fileName.set(fileModelForApp.getFileInModel().getPath());
        leftTableLabel.textProperty().bind(fileName);
    }

    private void fileRightChosen(FileModelForApp fileModelForApp){
        StringProperty fileName = new SimpleStringProperty();
        fileName.set(fileModelForApp.getFileInModel().getPath());
        rightTableLabel.textProperty().bind(fileName);
    }

    private void initializeLeftTab() {
        nameColumnLeft.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        sizeColumnLeft.setCellValueFactory(cellData -> cellData.getValue().sizeProperty());
        dateColumnLeft.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        sizeColumnLeft.setComparator(new SizeComparator());
        nameColumnLeft.setComparator(new NameComparator());
        dateColumnLeft.setComparator(new DateComparator());
        leftTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        leftTable.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                FileModelForApp file = leftTable.getSelectionModel().getSelectedItem();
                if (file != null) {
                    objectInLeftListListener(file);
                }
            } else if(event.isPrimaryButtonDown() && event.getClickCount() == 1) {
                FileModelForApp file = leftTable.getSelectionModel().getSelectedItem();
                if (file != null) {
                    fileLeftChosen(file);
                }
            }
        });
    }

    private void initializeRightTab() {
        nameColumnRight.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        sizeColumnRight.setCellValueFactory(cellData -> cellData.getValue().sizeProperty());
        dateColumnRight.setCellValueFactory(cellData -> cellData.getValue().dateProperty());

        sizeColumnRight.setComparator(new SizeComparator());
        nameColumnRight.setComparator(new NameComparator());
        dateColumnRight.setComparator(new DateComparator());
        rightTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        rightTable.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                FileModelForApp file = rightTable.getSelectionModel().getSelectedItem();
                if (file != null) {
                    objectInRightListListener(file);
                }
            } else if(event.isPrimaryButtonDown() && event.getClickCount() == 1) {
                FileModelForApp file = rightTable.getSelectionModel().getSelectedItem();
                if (file != null) {
                    fileRightChosen(file);
                }
            }
        });
    }

    @FXML
    private void initialize() {
        initializeLeftTab();
        initializeRightTab();
        setupDragAndDrop(leftTable, leftTablePath);
        setupDragAndDrop(rightTable, rightTablePath);
    }

    @FXML
    private void onLeftInputOuputChange(ActionEvent event){
        File file = new File(leftTablePathInputOutput.getText());
        if(file.exists() && file.isDirectory() && !file.getName().startsWith(".")){
            objectInLeftListListener(new FilesUtils().fileToFileModelForApp(file));
        }
    }

    @FXML
    private void onRightInputOutputChange(ActionEvent event){
        File file = new File(rightTablePathInputOutput.getText());
        if(file.exists() && file.isDirectory() && !file.getName().startsWith(".")){
            objectInRightListListener(new FilesUtils().fileToFileModelForApp(file));
        }
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
        //TODO:
        if (leftTable.isFocused()) {
            System.out.println("LEFT");
        } else if (rightTable.isFocused()) {
            System.out.println("RIGHT");
        }
    }

    @FXML
    private void createFolder(ActionEvent event) {
        //TODO:
        if (leftTable.isFocused()) {
            System.out.println("LEFT");
        } else if (rightTable.isFocused()) {
            System.out.println("RIGHT");
        }
    }

    @FXML
    private void deleteActionOnClick(ActionEvent event) {
        if(DialogUtil.deleteDialog()) {
            ObservableList<FileModelForApp> fileListEntries = FXCollections.observableArrayList();
            if (leftTable.isFocused()) {
                fileListEntries = leftTable.getSelectionModel().getSelectedItems();
            } else if (rightTable.isFocused()) {
                fileListEntries = rightTable.getSelectionModel().getSelectedItems();
            }

            List<Path> pathsToDelete = fileListEntries.stream().
                    map(fileListEntry -> Paths.get(fileListEntry.getFileInModel().getPath())).collect(Collectors.toList());

            BooleanProperty isCanceledProperty = new SimpleBooleanProperty(false);
            DeleteFile deleteFiles = new DeleteFile(pathsToDelete, isCanceledProperty);
            try {
                new Thread(new FileOperationTask(deleteFiles, isCanceledProperty, this)).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setupDragAndDrop(TableView tableView, StringProperty path) {
        boolean canBeDropped = true;

        tableView.setOnDragDetected(event -> {
            List<FileModelForApp> selected = tableView.getSelectionModel().getSelectedItems();
            if (selected.size() != 0) {
                Dragboard db = tableView.startDragAndDrop(TransferMode.COPY_OR_MOVE);
                ClipboardContent content = new ClipboardContent();
                content.putFiles(selected.stream().map(FileModelForApp::getFileInModel).collect(Collectors.toList()));
                db.setContent(content);
                event.consume();
            }
        });

        tableView.setOnDragOver(event -> {
            if (event.getGestureSource() != tableView && event.getDragboard().hasFiles() && canBeDropped) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });

        tableView.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (event.getDragboard().hasFiles()) {
                List<File> files = db.getFiles();
                List<Path> paths = files.stream().map(File::getPath).map(Paths::get).collect(Collectors.toList());

                BooleanProperty isCanceledProperty = new SimpleBooleanProperty(false);

                FileOperation fileOperation;
                if (event.getTransferMode() == TransferMode.COPY) {
                    fileOperation = new CopyFile(paths, isCanceledProperty, Paths.get(path.get()));
                } else {
                    fileOperation = new MoveFile(paths, isCanceledProperty, Paths.get(path.get()));
                }

                try {
                    new Thread(new FileOperationTask(fileOperation, isCanceledProperty,this)).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        });
    }
}
