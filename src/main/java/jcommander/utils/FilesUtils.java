package jcommander.utils;

import com.google.common.collect.Ordering;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jcommander.comparators.FileNameComparator;
import jcommander.comparators.FileTypeComparator;
import jcommander.models.FileModelForApp;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by impresyjna on 25.04.2016.
 */
public class FilesUtils {

    public static ObservableList<FileModelForApp> fileList(String path) {
        ObservableList<FileModelForApp> listOfFiles = FXCollections.observableArrayList();
        File file = new File(path);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            Collections.sort(Arrays.asList(files), Ordering.from(new FileTypeComparator()).compound(new FileNameComparator()));
            for (File tempFile : files) {
                Path p = Paths.get(tempFile.getAbsolutePath());
                BasicFileAttributes attr = null;
                try {
                    attr = Files.readAttributes(p, BasicFileAttributes.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                DateFormat df = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, AppBundle.getInstance().getCurrentLocale());
                String formattedDate = df.format(attr.creationTime().toMillis());
                if (tempFile.isDirectory()) {
                    listOfFiles.add(new FileModelForApp(tempFile.getName(), "<DIR>", formattedDate));
                } else if(tempFile.isFile()){
                    listOfFiles.add(new FileModelForApp(tempFile.getName(), Long.toString(tempFile.length())+" B", formattedDate));
                }
            }
        }
        return listOfFiles;
    }
}
