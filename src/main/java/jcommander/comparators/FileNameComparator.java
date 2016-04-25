package jcommander.comparators;

import java.io.File;
import java.util.Comparator;

/**
 * Created by impresyjna on 25.04.2016.
 */
public class FileNameComparator implements Comparator<File> {

    @Override
    public int compare(File file1, File file2) {

        return String.CASE_INSENSITIVE_ORDER.compare(file1.getName(),
                file2.getName());
    }
}
