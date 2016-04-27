package jcommander.tasks;

import javafx.beans.property.BooleanProperty;
import javafx.concurrent.Task;
import jcommander.controllers.MainViewController;
import jcommander.operations.FileOperation;
import jcommander.views.ProgressDialogView;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by impresyjna on 27.04.2016.
 */
public class FileOperationTask extends Task<Void> {
    private FileOperation fileOperation;
    private BooleanProperty isCanceledProperty;
    private ProgressDialogView progressDialog;
    private MainViewController controller;

    public FileOperationTask(FileOperation fileOperation, BooleanProperty isCanceledProperty, MainViewController mainViewController) throws IOException {
        this.fileOperation = fileOperation;
        this.isCanceledProperty = isCanceledProperty;
        this.progressDialog = new ProgressDialogView();
        this.controller = mainViewController;

        progressDialog.setTask(this);
        this.setOnCancelled(event -> {
            this.isCanceledProperty.set(true);
            progressDialog.close();
        });
        this.setOnSucceeded(event -> onSuccess());
        progressDialog.show();
    }

    @Override
    protected Void call() throws Exception {
        long fileListSize = fileOperation.getPaths().size();

        fileOperation.progressProperty().addListener((observable, oldValue, newValue) -> {
            updateProgress(fileOperation.getProgress(), fileListSize);
        });

        fileOperation.execute();
        return null;
    }

    private void onSuccess(){
        controller.refreshLists();
        progressDialog.close();
    }
}
