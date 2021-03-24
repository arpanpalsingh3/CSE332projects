package datastructures.dictionaries;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

import cse332.datastructures.containers.Item;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.Dictionary;

/**
 * TODO: Replace this comment with your own as appropriate.
 * 1. You must implement a generic chaining hashtable. You may not
 *    restrict the size of the input domain (i.e., it must accept 
 *    any key) or the number of inputs (i.e., it must grow as necessary).
 * 3. Your HashTable should rehash as appropriate (use load factor as
 *    shown in class!). 
 * 5. HashTable should be able to resize its capacity to prime numbers for more 
 *    than 200,000 elements. After more than 200,000 elements, it should 
 *    continue to resize using some other mechanism.
 * 6. We suggest you hard code some prime numbers. You can use this
 *    list: http://primes.utm.edu/lists/small/100000.txt 
 *    NOTE: Do NOT copy the whole list!
 * 7. When implementing your iterator, you should NOT copy every item to another
 *    dictionary/list and return that dictionary/list's iterator. 

 */
public class ChainingHashTable<K, V> extends DeletelessDictionary<K, V> {
    private Supplier<Dictionary<K, V>> newChain;
    private Dictionary<K,V>[] table;
    private int start;
    private int size;
    private double ticker;
    private double load;
    private final int[] sizes = {13, 227 , 241, 313 , 499, 863, 1069, 2851, 4157, 12343, 24019, 65111, 101021, 137909, 172519, 209623};



    public ChainingHashTable(Supplier<Dictionary<K, V>> newChain) {
        this.newChain = newChain;
        load = 0.0;
        // initialize the table at a small primary number length
        table = new Dictionary[7];
        for(int i = 0; i < 7; i ++) {
            table[i] = newChain.get();
        }
        start = 0;
        ticker = 0.0;
        size = 0;
    }

    @Override
    public V insert(K key, V value) {
        if(load >= 1) {
            // when the load is too big, resize
            this.table = resize(table);
        }
        // otherwise insert using index based off key hashcode
        int index = Math.abs(key.hashCode() % table.length);
        if(table[index] == null) { // if its empty, insert it in
            table[index] = newChain.get();
        }
        V returnValue = null; // initialize our return value
        if(this.find(key) == null) { // if the key isn't already present in the table
            size ++; // raise the size of the table, since we are adding a new things
        } else {
            returnValue = this.find(key); // otherwise return the old value, since its already in there
        }
        table[index].insert(key, value); // insert
        load = (++ticker) / table.length; // increment the load
        return returnValue; // return the value we found
    }

    @Override
    public V find(K key) {
        int index = Math.abs(key.hashCode() % table.length); // finding index based on hashcode
        if(table[index] == null) { // check if its there
            table[index] = newChain.get();
            return null;
        }
        // if the index isn't empty, find the key in the underlying move to front list
        return table[index].find(key);
    }

    @Override
    public Iterator<Item<K, V>> iterator() {

        if(table[0] == null) {
            table[0] = newChain.get();
        }
        return new Iterator<>() {
            private int iterStarter = 0;
            Iterator<Item<K, V>> iter = table[0].iterator();
            @Override
            public boolean hasNext() {
                if (iterStarter < table.length && !iter.hasNext()) {
                    if (table[iterStarter + 1] == null) {
                        iterStarter++;

                        while (table[iterStarter] == null) {
                            iterStarter++;
                            if (iterStarter >= table.length) {
                                return false;
                            }
                        }
                    } else {
                        iterStarter++;
                    }
                    if (iterStarter < table.length) {
                        iter = table[iterStarter].iterator();
                    }
                }
                if (iterStarter >= table.length) {
                    return false;
                } else {
                    return iter.hasNext();
                }
            }

            @Override
            public Item<K, V> next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return iter.next();
            }
        };
    }

    // helper to return the size (since I don't know how to increase dictionary size)
    public int size() {
        return size;
    }

    // helper to resize the table
    private Dictionary<K,V>[] resize(Dictionary<K, V>[] arrayChange) {
        Dictionary<K,V>[] changedDictionary;
        if(start > 15) { // when start (the thing that keeps track of how many times I have resized) is too big
            changedDictionary = new Dictionary[arrayChange.length * 2]; // i can no longer grab from prime array, so just double
        } else {
            changedDictionary = new Dictionary[sizes[start]]; // otherwise grab from the array
        }
        // copy
        for (Dictionary<K, V> items : arrayChange) {
            if (items != null) {
                for (Item<K, V> item : items) {
                    int index = Math.abs(item.key.hashCode() % changedDictionary.length);
                    if (changedDictionary[index] == null) {
                        changedDictionary[index] = newChain.get();
                    }
                    changedDictionary[index].insert(item.key, item.value);
                }
            }
        }
        start ++;
        return changedDictionary;

    }
}