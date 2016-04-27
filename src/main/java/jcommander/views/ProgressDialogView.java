package jcommander.views;

import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jcommander.Main;
import jcommander.controllers.MainViewController;
import jcommander.controllers.ProgressDialogController;
import jcommander.utils.AppBundle;

import java.io.IOException;
import java.util.ResourceBundle;

/**
 * Created by impresyjna on 27.04.2016.
 */
public class ProgressDialogView {
    private Stage stage;
    private ProgressDialogController controller;

    public ProgressDialogView() throws IOException {
        ResourceBundle bundle = AppBundle.getInstance().getBundle();

        stage = new Stage();
        stage.initStyle(StageStyle.DECORATED);
        stage.setResizable(false);
        stage.initOwner(Main.getPrimaryStage());
        stage.initModality(Modality.NONE);
        stage.setTitle(bundle.getString("operation.progress.text"));

        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getClassLoader().getResource("views/progress_dialog.fxml"));
        loader.setResources(bundle);
        Parent root = loader.load();

        controller =  loader.getController();

        stage.setScene(new Scene(root));
    }

    public void show() {
        stage.show();
    }

    public void close() {
        stage.close();
    }

    public void setTask(Task<Void> task) {
        stage.setOnCloseRequest(event -> task.cancel());
        controller.setTask(task);
    }
}
