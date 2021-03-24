package datastructures.worklists;


import cse332.interfaces.worklists.PriorityWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/PriorityWorkList.java
 * for method specifications.
 */
public class MinFourHeapComparable<E extends Comparable<E>> extends PriorityWorkList<E> {
    /* Do not change the name of this field; the tests rely on it to work correctly. */
    private E[] data;
    private int size;

    public MinFourHeapComparable() {
        this.data = (E[]) new Comparable[10];
        size = 0;
    }
    private void resize() {
        E[] newArray = (E[])new Comparable[this.size * 2];
        if (this.size >= 0) System.arraycopy(this.data, 0, newArray, 0, this.size);
        this.data = newArray;
    }

    @Override
    public boolean hasWork() {
        return this.size > 0;
    }

    @Override
    public void add(E work) {
        // resize the min heap when you hit max size to be double the cap
        if (size == data.length) {
            resize();
        }
        data[size] = work;
        size++;

        // Percolate up
        if (size > 1) {
            percolateUp();
        }

    }

    @Override
    public E peek() {
        if (!hasWork()) {
            throw new NoSuchElementException();
        }
        return data[0];
    }

    @Override
    public E next() {
        if (!this.hasWork()) {
            throw new NoSuchElementException();
        }

        E value = this.data[0];
        // last element in heap moves to the top
        this.data[0] = this.data[this.size - 1];
        this.size--;
        // if there's only one element, no percolating needed
        if (this.size > 0) {
            // begins percolating down with root and child of root with
            // highest priority (smallest value)
            int firstChildIndex = this.child(0);
            this.percolateDown(0, this.findMinChildIndex(firstChildIndex));
        }
        return value;

    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        this.data = (E[]) new Comparable[10];
        this.size = 0;
    }
    // private helpers to move things up
    private void percolateUp()  {
        for (int i = size-1; i > 0; i--) {
            E child = data[i];
            int parentIndex = (i-1)/4;
            E parent = data [parentIndex];
            // swap the parent and child, if child is ever smaller than parent
            int result = child.compareTo(parent);
            if (result < 0) {
                data[parentIndex] = child;
                data[i] = parent;
            }
        }
    }

    // private helper to move things down
    private void percolateDown(int parentIndex, int minChildIndex) {
        while (minChildIndex != -1 && (this.data[parentIndex].compareTo(this.data[minChildIndex])) > 0) {

            E temp = this.data[minChildIndex];
            this.data[minChildIndex] = this.data[parentIndex];
            this.data[parentIndex] = temp;
            parentIndex = minChildIndex;
            int firstChildIndex = this.child(parentIndex);
            minChildIndex = this.findMinChildIndex(firstChildIndex);
        }

    }
    private int findMinChildIndex(int firstChild) {
        int min;
        if (firstChild > this.size) {
            return -1;
        } else {
            min = firstChild;
            for (int i = firstChild + 1; i < firstChild + 4; i++) {
                if (i < this.size && (this.data[i].compareTo(this.data[min])) < 0) {
                    min = i;
                }
            }
        }
        return min;
    }
    private int child(int parentIndex) {
        return 4 * parentIndex + 1;
    }




}
