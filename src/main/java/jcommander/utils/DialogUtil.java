package jcommander.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import jcommander.ReplaceOptions;

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

    public static ReplaceOptions replaceDialog(String fileName, String[] sizes, String[] dates) {
        final ReplaceOptions result;
        ResourceBundle bundle = AppBundle.getInstance().getBundle();

        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
        dialog.setTitle(bundle.getString("warning.title.text"));
        dialog.setHeaderText(bundle.getString("file.exists.header.text"));
        dialog.setContentText(bundle.getString("replace.text") + "\n" + fileName + "\n" + sizes[0] + " B, " + dates[0] +
                "\n" + bundle.getString("with.text") + "\n" + fileName + "\n" + sizes[1] + " B, " + dates[1]);

        ButtonType buttonReplace = new ButtonType(bundle.getString("replace.option.text"));
        ButtonType buttonKeep = new ButtonType(bundle.getString("keep.option.text"));
        ButtonType buttonSkip = new ButtonType(bundle.getString("skip.option.text"));
        ButtonType buttonReplaceAll = new ButtonType(bundle.getString("replace.all.option.text"));
        ButtonType buttonKeepAll = new ButtonType(bundle.getString("keep.all.option.text"));
        ButtonType buttonSkipAll = new ButtonType(bundle.getString("skip.all.option.text"));
        ButtonType buttonCancel = new ButtonType(bundle.getString("cancel.option.text"), ButtonBar.ButtonData.CANCEL_CLOSE);

        dialog.getButtonTypes().setAll(buttonReplace, buttonKeep, buttonSkip,
                buttonReplaceAll, buttonKeepAll, buttonSkipAll, buttonCancel);
        Optional<ButtonType> selectedOption = dialog.showAndWait();

        if(selectedOption.isPresent()) {
            if(selectedOption.get() == buttonReplace) {
                result = ReplaceOptions.YES;
            } else if(selectedOption.get() == buttonKeep) {
                result = ReplaceOptions.KEEP;
            } else if(selectedOption.get() == buttonSkip) {
                result = ReplaceOptions.NO;
            } else if(selectedOption.get() == buttonReplaceAll) {
                result = ReplaceOptions.YES_ALL;
            } else if(selectedOption.get() == buttonKeepAll) {
                result = ReplaceOptions.KEEP_ALL;
            } else if(selectedOption.get() == buttonSkipAll) {
                result = ReplaceOptions.NO_ALL;
            } else {
                result = ReplaceOptions.CANCEL;
            }
        } else {
            result = ReplaceOptions.CANCEL;
        }

        return result;
    }


}
