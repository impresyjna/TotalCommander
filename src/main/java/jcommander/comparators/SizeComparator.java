package jcommander.comparators;

import java.util.Comparator;

/**
 * Created by impresyjna on 26.04.2016.
 */
public class SizeComparator implements Comparator<String> {

    @Override
    public int compare(String size1, String size2) {
        long size1InLong = 0;
        long size2InLong = 0;
        if (!size1.equals("<DIR>")) {
            size1InLong = Long.parseLong(size1.replace(" B", ""));
        }
        if (!size2.equals("<DIR>")) {
            size2InLong = Long.parseLong(size2.replace(" B", ""));
        }

        if (size1InLong<size2InLong)
            return -1;
        if (size1InLong==size2InLong) {
            return 0;
        }
        else return 1;
    }
}