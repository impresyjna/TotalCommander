package jcommander.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.util.Optional;
import java.util.ResourceBundle;

public abstract class DialogUtil {
    public static boolean deleteDialog() {
        ResourceBundle bundle = AppBundle.getInstance().getBundle();

        Alert dialog = new Alert(Alert.AlertType.WARNING);
        dialog.setTitle(bundle.getString("warning.title.text"));
        dialog.setHeaderText(bundle.getString("delete.header.text"));
        dialog.setContentText(bundle.getString("delete.warning.text"));

        ButtonType buttonYes = new ButtonType(bundle.getString("yes.option"));
        ButtonType buttonNo = new ButtonType(bundle.getString("no.option"), ButtonBar.ButtonData.CANCEL_CLOSE);

        dialog.getButtonTypes().setAll(buttonYes, buttonNo);
        Optional<ButtonType> selectedOption = dialog.showAndWait();

        return selectedOption.isPresent() && selectedOption.get() == buttonYes;
    }

}
