package p2.sorts;

import java.util.Comparator;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.PriorityWorkList;
import datastructures.worklists.MinFourHeap;

public class HeapSort {
    public static <E extends Comparable<E>> void sort(E[] array) {
        sort(array, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, Comparator<E> comparator) {
        PriorityWorkList<E> organizer = new MinFourHeap(comparator);
        int itemLength = array.length;
        int index = 0;
        while (index < itemLength) {
            organizer.add(array[index]);
            index++;
        }
        index = 0;
        while (index < itemLength) {
            array[index] = organizer.next();
            index++;
        }
    }
}
