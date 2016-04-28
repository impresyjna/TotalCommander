package jcommander.utils;

import com.google.common.collect.Ordering;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jcommander.comparators.FileNameComparator;
import jcommander.comparators.FileTypeComparator;
import jcommander.models.FileModelForApp;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by impresyjna on 25.04.2016.
 */
public class FilesUtils {

    public static ObservableList<FileModelForApp> fileList(String path) {
        ObservableList<FileModelForApp> listOfFiles = FXCollections.observableArrayList();
        File file = new File(path);
        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, AppBundle.getInstance().getCurrentLocale());
        if (file.isDirectory()) {
            List<File> files = Arrays.asList(file.listFiles());
            if (file.getParentFile() != null) {
                Icon icon = FileSystemView.getFileSystemView().getSystemIcon(file.getParentFile());
                listOfFiles.add(new FileModelForApp("[...]", "<ROOT>", "", file.getParentFile(), icon));
            }
            Collections.sort(files, Ordering.from(new FileTypeComparator()).compound(new FileNameComparator()));
            for (File tempFile : files) {
                if (!tempFile.getName().startsWith(".")) {
                    String formattedDate = df.format(getAttributeOfFile(tempFile).creationTime().toMillis());
                    Icon icon = FileSystemView.getFileSystemView().getSystemIcon(tempFile);
                    if (tempFile.isDirectory()) {
                        listOfFiles.add(new FileModelForApp(tempFile.getName(), "<DIR>", formattedDate, tempFile, icon));
                    } else if (tempFile.isFile()) {
                        listOfFiles.add(new FileModelForApp(tempFile.getName(), Long.toString(tempFile.length()) + " B", formattedDate, tempFile,icon));
                    }
                }
            }
        }
        return listOfFiles;
    }

    private static BasicFileAttributes getAttributeOfFile(File file) {
        Path p = Paths.get(file.getAbsolutePath());
        BasicFileAttributes attr = null;
        try {
            attr = Files.readAttributes(p, BasicFileAttributes.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return attr;
    }

    public FileModelForApp fileToFileModelForApp(File file){
        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, AppBundle.getInstance().getCurrentLocale());
        String formattedDate = df.format(getAttributeOfFile(file).creationTime().toMillis());
        Icon icon = FileSystemView.getFileSystemView().getSystemIcon(file);
        return new FileModelForApp(file.getName(), Long.toString(file.length())+" B", formattedDate, file,icon);
    }
}
