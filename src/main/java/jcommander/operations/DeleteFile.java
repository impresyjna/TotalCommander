package jcommander.operations;

import javafx.beans.property.BooleanProperty;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;


import static java.nio.file.FileVisitResult.CONTINUE;
import static java.nio.file.FileVisitResult.TERMINATE;

/**
 * Created by impresyjna on 27.04.2016.
 */
public class DeleteFile extends FileOperation {
    public DeleteFile(List<Path> paths, BooleanProperty isCanceledProperty) {
        super(paths, isCanceledProperty);
    }

    @Override
    public void execute() throws IOException {
        for(Path path: paths) {
            if(isCanceledProperty.get()) {
                break;
            }
            Files.walkFileTree(path, this);
            try {
                Thread.sleep(4000);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
    {
        if(!isCanceledProperty.get()) {
            if(Files.isWritable(file)) {
                progress.set(progress.getValue() + Files.size(file));
                Files.delete(file);
            } else {
                throw new IOException("Can't delete file");
            }
            return CONTINUE;
        } else {
            return TERMINATE;
        }
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException e) throws IOException {
        if(!isCanceledProperty.get()) {
            if(e == null) {
                Files.delete(dir);
            }
            return CONTINUE;
        } else {
            return TERMINATE;
        }
    }
}
