package p2.sorts;

import java.util.Comparator;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.PriorityWorkList;
import datastructures.worklists.MinFourHeap;

public class TopKSort {
    public static <E extends Comparable<E>> void sort(E[] array, int k) {
        sort(array, k, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, int k, Comparator<E> comparator) {
        if (k > array.length) {
            k = array.length;
        }
        MinFourHeap<E> sorter = new MinFourHeap<E>(comparator);
        for (int i = 0; i < k; i++) {
            sorter.add(array[i]);
        }
        for (E e : array) {
            if (comparator.compare(e, sorter.peek()) > 0) {
                sorter.next();
                sorter.add(e);
            }
        }
        for (int i = 0; i < k; i++) {
            array[i] = sorter.next();
        }
        for (int i = k; i < array.length; i++) {
            array[i] = null;
        }
    }
}
