package jcommander.operations;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyLongWrapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.util.List;

/**
 * Created by impresyjna on 27.04.2016.
 */
public abstract class FileOperation extends SimpleFileVisitor<Path> {
    ReadOnlyLongWrapper progress;
    List<Path> paths;
    BooleanProperty isCanceledProperty;

    FileOperation(List<Path> paths, BooleanProperty isCanceledProperty) {
        this.paths = paths;
        this.isCanceledProperty = isCanceledProperty;
        this.progress = new ReadOnlyLongWrapper(this, "progress");
    }

    public long getProgress() {
        return progress.get();
    }

    public ReadOnlyLongWrapper progressProperty() {
        return progress;
    }

    public abstract void execute() throws IOException;

    public List<Path> getPaths() {
        return paths;
    }
}
