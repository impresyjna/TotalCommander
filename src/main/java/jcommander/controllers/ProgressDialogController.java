package jcommander.controllers;

import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;

/**
 * Created by impresyjna on 27.04.2016.
 */
public class ProgressDialogController {
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Button cancelButton;

    public void setTask(Task<Void> task) {
        progressBar.progressProperty().bind(task.progressProperty());
        cancelButton.setOnAction(event -> {
            if(task.getState() == Worker.State.RUNNING) {
                task.cancel();
            }
        });
    }
}
