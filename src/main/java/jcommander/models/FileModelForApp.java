package jcommander.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.File;
import java.nio.file.Path;
import java.util.Date;

/**
 * Created by impresyjna on 25.04.2016.
 */
public class FileModelForApp {
    private StringProperty name = new SimpleStringProperty();
    private StringProperty size = new SimpleStringProperty();
    private StringProperty date = new SimpleStringProperty();
    private File fileInModel;

    public FileModelForApp(String name, String size, String date, File file) {
        this.name.set(name);
        this.size.set(size);
        this.date.set(date);
        this.fileInModel = file;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty sizeProperty() {
        return size;
    }

    public void setSize(String size) {
        this.size.set(size);
    }

    public StringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getName() {
        return name.get();
    }

    public String getSize() {
        return size.get();
    }

    public String getDate() {
        return date.get();
    }

    public File getFileInModel() {
        return fileInModel;
    }

    public void setFileInModel(File fileInModel) {
        this.fileInModel = fileInModel;
    }
}
