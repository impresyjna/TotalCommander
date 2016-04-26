package jcommander;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import jcommander.controllers.MainViewController;
import jcommander.utils.AppBundle;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class Main extends Application implements Observer {

    private FXMLLoader loader;
    private Stage primaryStage;
    private BorderPane rootLayout;
    private Parent root;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("JCommander");
        initRootLayout();
        showMainPage();
    }


    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("views/sample.fxml"));
            loader.setResources(AppBundle.getInstance().getBundle());
            rootLayout = loader.load();

            AppBundle.getInstance().addObserver(this);
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            rootLayout.setPrefSize(1024, 720);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showMainPage() {
        // Load person overview.
        try {
            this.loader = new FXMLLoader();
            this.loader.setLocation(getClass().getClassLoader().getResource("views/main_view.fxml"));
            this.loader.setResources(AppBundle.getInstance().getBundle());
            AnchorPane personOverview = this.loader.load();


            // Set person overview into the center of root layout.
            rootLayout.setCenter(personOverview);

            // Give the controller access to the main app.
            MainViewController controller = this.loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void update(Observable o, Object arg) {
        this.loader.setResources(AppBundle.getInstance().getBundle());
        try {
            this.loader.setRoot(null);
            this.loader.setController(null);
            root = this.loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.primaryStage.getScene().setRoot(root);
        MainViewController controller = this.loader.getController();
        controller.setMainApp(this);
    }
}
