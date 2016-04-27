package jcommander.comparators;

import java.util.Comparator;

/**
 * Created by impresyjna on 28.04.2016.
 */
public class NameComparator implements Comparator<String> {

    @Override
    public int compare(String name1, String name2) {
        if (name1.equals("[...]")) {
            return Integer.MIN_VALUE;
        } else if (name2.equals("[...]")) {
            return Integer.MIN_VALUE;
        } else {
            int compare = name1.compareTo(name2);
            if (compare < 0) {
                return -1;
            } else if (compare > 0) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}
