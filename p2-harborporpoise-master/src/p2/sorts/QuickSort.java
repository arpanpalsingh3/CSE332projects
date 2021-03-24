package p2.sorts;

import java.util.Comparator;

import cse332.exceptions.NotYetImplementedException;

public class QuickSort {
    public static <E extends Comparable<E>> void sort(E[] array) {
        QuickSort.sort(array, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, Comparator<E> comparator) {
        quickSort(array, 0, array.length - 1, comparator);
    }
    public static <E> void quickSort(E[] array, int low, int high, Comparator<E> comparator) {
        if (low < high) {
            int partitionIndex = insert(array, low, high, comparator);
            quickSort(array, low, partitionIndex - 1, comparator);
            quickSort(array, partitionIndex + 1, high, comparator);
        }
    }

    public static <E> int insert(E[] array, int low, int high, Comparator<E> comparator) {
        E pivot = array[high];
        int index = (low - 1);
        for (int j = low; j < high; j++) {
            if (comparator.compare(array[j], pivot) < 0) {
                index++;
                E temp = array[index];
                array[index] = array[j];
                array[j] = temp;
            }
        }
        E temp = array[index + 1];
        array[index + 1] = array[high];
        array[high] = temp;
        return index + 1;
    }
}
