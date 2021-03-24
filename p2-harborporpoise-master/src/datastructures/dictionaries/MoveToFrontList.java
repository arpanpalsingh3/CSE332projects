package datastructures.dictionaries;

import java.util.Iterator;

import cse332.datastructures.containers.Item;
import cse332.datastructures.trees.BinarySearchTree;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.SimpleIterator;
import cse332.interfaces.worklists.WorkList;
import datastructures.worklists.ArrayStack;

/**
 * TODO: Replace this comment with your own as appropriate.
 * 1. The list is typically not sorted.
 * 2. Add new items to the front of the list.
 * 3. Whenever find is called on an item, move it to the front of the 
 *    list. This means you remove the node from its current position 
 *    and make it the first node in the list.
 * 4. You need to implement an iterator. The iterator SHOULD NOT move
 *    elements to the front.  The iterator should return elements in
 *    the order they are stored in the list, starting with the first
 *    element in the list. When implementing your iterator, you should 
 *    NOT copy every item to another dictionary/list and return that 
 *    dictionary/list's iterator. 
 */
public class MoveToFrontList<K, V> extends DeletelessDictionary<K, V> {

    private Node front;

    public MoveToFrontList() {
        this.front = null;
    }

    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) { throw new IllegalArgumentException(); }

        if (front == null) {
            front = new Node(key, value, null);
            size++;
            return null;
        } else {
            if (find(key) != null) {
                V oldVal = front.value;
                front.value = value;
                return oldVal;
            } else {
                front = new Node(key, value, front);
                size++;
                return null;
            }
        }
    }

    @Override
    public V find(K key) {
        if (key == null) { throw new IllegalArgumentException(); }
        Node curr = front;
        Node prev = curr;
        while (curr != null) {
            if (curr.key.equals(key)) {
                if (curr != front) {
                    prev.next = curr.next;
                    curr.next = front;
                    front = curr;
                }
                return curr.value;
            }
            prev = curr;
            curr = curr.next;
        }
        return null;
    }

    @Override
    public Iterator<Item<K, V>> iterator() {
        return new MTFIterator();
    }

    private class MTFIterator extends SimpleIterator<Item<K, V>> {
        private Node curr;
        private final WorkList<Node> nodes;

        public MTFIterator() {
            this.nodes = new ArrayStack<Node>();
            if (MoveToFrontList.this.front != null) {
                this.curr = MoveToFrontList.this.front;
            }
        }

        @Override
        public boolean hasNext() {
            return this.curr != null || nodes.hasWork();
        }

        @Override
        public Item<K, V> next() {
            nodes.add(this.curr);
            this.curr = this.nodes.next();
            Item<K, V> value = new Item<K, V>(this.curr);
            nodes.add(this.curr.next);
            this.curr = this.nodes.next();
            return value;
        }
    }

    public class Node extends Item<K, V> {
        private Node next;

        public Node(K key, V value, Node next) {
            super(key, value);
            this.next = next;
        }
    }
}
