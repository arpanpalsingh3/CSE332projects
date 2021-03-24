package datastructures.worklists;

        import cse332.interfaces.worklists.FixedSizeFIFOWorkList;
        import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FixedSizeFIFOWorkList.java
 * for method specifications.
 */
public class CircularArrayFIFOQueue<E extends Comparable<E>> extends FixedSizeFIFOWorkList<E> {
    private E[] circArray;
    private int front;
    private int back;
    private int totalVal;
    private int totalCap;

    public CircularArrayFIFOQueue(int capacity) {
        super(capacity);
        this.totalCap = capacity;
        this.totalVal = 0;
        this.front = 0;
        this.back = 0;
        this.circArray= (E[])new Comparable[capacity];
    }

    @Override
    public void add(E work) {
        if (totalVal == totalCap) {
            throw new IllegalStateException();
        }
        if (back == totalCap) {
            back = 0;
        }
        circArray[back] = work;
        back++;
        totalVal++;
    }

    @Override
    public E peek() {
        if (totalVal == 0) {
            throw new NoSuchElementException();
        }
        return circArray[front];

    }

    @Override
    public E peek(int i) {
        if (totalVal == 0) {
            throw new NoSuchElementException();
        }
        if (i < 0 || i >= totalVal) {
            throw new IndexOutOfBoundsException();
        }
        if (front + i < totalCap) {
            return circArray[front+i];
        } else {
            int index = front+i-totalCap;
            return circArray[index];
        }
    }

    @Override
    public E next() {
        if (totalVal == 0) {
            throw new NoSuchElementException();
        }
        E outWork = circArray[front];
        circArray[front] = null;
        front++;
        totalVal--;
        if (front == totalCap) {
            front = 0;
        }
        return outWork;
    }

    @Override
    public void update(int i, E value) {
        if (totalVal == 0) {
            throw new NoSuchElementException();
        }
        if (i < 0 || i >= totalVal) {
            throw new IndexOutOfBoundsException();
        }
        if (front + i < totalCap) {
            this.circArray[front+i]  = value;
        }else {
            int index = front+i-totalCap;
            this.circArray[index] = value;
        }

    }

    @Override
    public int size() {
        return totalVal;
    }

    @Override
    public void clear() {
        this.totalVal = 0;
        this.front = 0;
        this.back = 0;
        this.circArray= (E[])new Comparable[totalCap];
    }

    @Override
    public int compareTo(FixedSizeFIFOWorkList<E> other) {
        int shortLen = Math.min(this.size(), other.size());
        int comparison = 0;
        for (int i = 0; i < shortLen; i++) {
            comparison = this.peek(i).compareTo(other.peek(i));
            if (comparison != 0) {
                return comparison;
            }
        }
        return this.size() - other.size();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        // You will finish implementing this method in project 2. Leave this method unchanged for project 1.
        if (this == obj) {
            return true;
        }
        else if (!(obj instanceof FixedSizeFIFOWorkList<?>)) {
            return false;
        }
        else {
            FixedSizeFIFOWorkList<E> other = (FixedSizeFIFOWorkList<E>) obj;
            if (other.size() != this.size()) {
                return false;
            } else {
                return (this.compareTo(other) == 0);
            }
        }
    }


/*    @Override
    // BAD HASH CODE FOR EXPERIMENTS
    public int hashCode() {
        return 1;
    } */

    @Override
    public int hashCode() {
        int build = 0;
        for(int i = 0; i < this.totalVal; i ++) {
            build += circArray[(front + i)%7].hashCode() * (this.front + i);
        }
        return build;
    }
}
