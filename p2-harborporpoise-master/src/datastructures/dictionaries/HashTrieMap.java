package datastructures.dictionaries;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import cse332.datastructures.containers.Item;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.BString;
import cse332.interfaces.trie.TrieMap;

/**
 * See cse332/interfaces/trie/TrieMap.java
 * and cse332/interfaces/misc/Dictionary.java
 * for method specifications.
 */
public class HashTrieMap<A extends Comparable<A>, K extends BString<A>, V> extends TrieMap<A, K, V> {
    public class HashTrieNode extends TrieNode<ChainingHashTable<A, HashTrieNode>, HashTrieNode> {
        public HashTrieNode() {
            this(null);
        }

        public HashTrieNode(V value) {
            this.pointers = new ChainingHashTable<A, HashTrieNode>(MoveToFrontList::new);
            this.value = value;
        }

        @Override
        public Iterator<Entry<A,HashTrieNode>> iterator() {
            Iterator<Entry<A, HashTrieNode>> finalIterator = new Iterator<>() {
            Iterator<Item<A, HashTrieNode>> elements = pointers.iterator();
                @Override
                public boolean hasNext() {
                    return elements.hasNext();
                }
                @Override
                public Entry<A, HashTrieNode> next() {
                    Item<A, HashTrieNode> element = elements.next();
                    return new AbstractMap.SimpleEntry<>(element.key, element.value);
                }
            };
            return finalIterator;
        }
    }

    public HashTrieMap(Class<K> KClass) {
        super(KClass);
        this.root = new HashTrieNode();
    }

    @Override
    public V insert(K key, V value) {
        // checking the exception
        if(key == null || value == null) {
            throw new IllegalArgumentException();
        }
        // our current tree, starting at the root node
        HashTrieNode curRoot = (HashTrieMap<A, K, V>.HashTrieNode) root;

        // find the NODE in the tree associated with key
        for (A thisKey : key) {
            if (curRoot.pointers.find(thisKey) == null) {
                curRoot.pointers.insert(thisKey, new HashTrieNode());
            }
            curRoot = curRoot.pointers.find(thisKey);
        }
        // store the old value of child
        V oldVal = curRoot.value;

        // Check if node doesn't have child
        if (curRoot.value == null) {
            // increase the size since we are gonna add new child
            size++;
        }
        // add child to node key
        curRoot.value = value;

        return oldVal;

    }

    @Override
    public V find(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        HashTrieNode curRoot = (HashTrieMap<A, K, V>.HashTrieNode) root;

        for ( A thisKey: key) {
            // if the path does not exist, return null
            if (curRoot.pointers.find(thisKey) == null) {
                return null;
            }
            curRoot = curRoot.pointers.find(thisKey);
        }
        return curRoot.value;
    }

    @Override
    public boolean findPrefix(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        HashTrieNode curRoot = (HashTrieMap<A, K, V>.HashTrieNode) root;

        for ( A thisKey: key) {
            if (curRoot.pointers.find(thisKey) == null) {
                return false;
            }
            curRoot = curRoot.pointers.find(thisKey);
        }
        return true;
    }

    @Override
    public void delete(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }

        if (find(key) != null) {
            // Setting up reference pointer to a Node that:
            //   1) Has a value and at least 1 key
            //   2) Has more than 1 keys
            HashTrieNode curRoot = (HashTrieMap<A, K, V>.HashTrieNode) root;
            HashTrieNode prev = curRoot;
            for (A thisKey : key) {
                if (curRoot.pointers.find(thisKey).value != null &&
                        curRoot.pointers.find(thisKey).pointers.size() >= 1) {
                    prev = curRoot.pointers.find(thisKey);
                } else if (curRoot.pointers.find(thisKey).pointers.size() > 1) {
                    prev = curRoot.pointers.find(thisKey);
                }
                curRoot = curRoot.pointers.find(thisKey);
            }

            // Checking if there's any child past the end of the path
            boolean deleteAfter = true;
            if (curRoot.pointers.size() >= 1) {
                deleteAfter = false;
            }

            // BEFORE CASES
            if (curRoot == prev) {
                curRoot.value = null;
            } else {
                // Starts setting values between prev to curr as null
                boolean startDelete = false;
                HashTrieNode deleter = (HashTrieMap<A, K, V>.HashTrieNode) root;
                for (A thisKey : key) {
                    if (deleter == prev) {
                        startDelete = true;
                    }
                    if (startDelete) {
                        deleter.pointers.find(thisKey).value = null;
                    }
                    if (deleter == curRoot) {
                        startDelete = false;
                    }
                    deleter = deleter.pointers.find(thisKey);
                }
            }

            // AFTER CASES
            if (deleteAfter) {
                HashTrieNode tracker = (HashTrieMap<A, K, V>.HashTrieNode) root;
                boolean atPosition = (tracker == prev);
                int keyCounter = 0;
                A lastKey = null;
                for (A thisKey : key) {
                    keyCounter++;
                    if (atPosition) {
                        tracker.pointers.delete(thisKey);
                        break;
                    }
                    if (tracker.pointers.find(thisKey) == prev) {
                        atPosition = true;
                    }
                    lastKey = thisKey;
                    tracker = tracker.pointers.find(thisKey);
                }
                if (keyCounter == 1) {
                    tracker = (HashTrieMap<A, K, V>.HashTrieNode) root;
                    tracker.pointers.delete(lastKey);
                }
            }
            size--;
        }
    }

    @Override
    public void clear() {
        HashTrieNode curRoot = (HashTrieMap<A, K, V>.HashTrieNode) this.root;
        curRoot.pointers.clear();
        this.size = 0;
    }
}
