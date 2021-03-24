package experiments;

import cse332.datastructures.trees.BinarySearchTree;
import cse332.types.AlphabeticString;
import datastructures.dictionaries.AVLTree;
import datastructures.dictionaries.ChainingHashTable;
import datastructures.dictionaries.HashTrieMap;
import datastructures.dictionaries.MoveToFrontList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DictionaryComparison {
    public static final int WORD_NUMS = 27420;
    // timing ChainingHashTable
    public static double timeComparisonChain(AlphabeticString[] words) {
        final int NUM_TESTS = 5000;
        final int NUM_WARMUP = 3990;

        double totalTime = 0;
        for (int i = 0; i < NUM_TESTS; i++) {
            ChainingHashTable<AlphabeticString, Integer> table = new ChainingHashTable<>(MoveToFrontList::new);
            long startTime = System.currentTimeMillis(); // start timer

            for (int j = 0; j < WORD_NUMS ; j++) {
                table.insert(words[j], 0);
            }

            long endTime = System.currentTimeMillis(); // end timer
            if (NUM_WARMUP <= i) { // Throw away first NUM_WARMUP runs to exclude JVM warmup
                totalTime += (endTime - startTime);
            }
        }
        return totalTime / (NUM_TESTS - NUM_WARMUP);
    }
    // timing BST
    public static double timeComparisonBST(AlphabeticString[] words) {
        final int NUM_TESTS = 5000;
        final int NUM_WARMUP = 3990;

        double totalTime = 0;
        for (int i = 0; i < NUM_TESTS; i++) {
            BinarySearchTree<AlphabeticString, Integer> bst = new BinarySearchTree<>();
            long startTime = System.currentTimeMillis(); // start timer

            for (int j = 0; j < WORD_NUMS; j++) {
                bst.insert(words[j], 0);
            }

            long endTime = System.currentTimeMillis(); // end timer
            if (NUM_WARMUP <= i) { // Throw away first NUM_WARMUP runs to exclude JVM warmup
                totalTime += (endTime - startTime);
            }
        }
        return totalTime / (NUM_TESTS - NUM_WARMUP);
    }
    // timing AVL
    public static double timeComparisonAVL(AlphabeticString[] words) {
        final int NUM_TESTS = 5000;
        final int NUM_WARMUP = 3990;

        double totalTime = 0;
        for (int i = 0; i < NUM_TESTS; i++) {
            AVLTree<AlphabeticString, Integer> avl = new AVLTree<>();
            long startTime = System.currentTimeMillis(); // start timer

            for (int j = 0; j < WORD_NUMS; j++) {
                avl.insert(words[j], 0);
            }

            long endTime = System.currentTimeMillis(); // end timer
            if (NUM_WARMUP <= i) { // Throw away first NUM_WARMUP runs to exclude JVM warmup
                totalTime += (endTime - startTime);
            }
        }
        return totalTime / (NUM_TESTS - NUM_WARMUP);
    }
    // timing hashTrie
    public static double timeComparisonTrie(AlphabeticString[] words) {
        final int NUM_TESTS = 5000;
        final int NUM_WARMUP = 3990;

        double totalTime = 0;
        for (int i = 0; i < NUM_TESTS; i++) {
            HashTrieMap<Character, AlphabeticString, Integer> hashTrie = new HashTrieMap<>(AlphabeticString.class);
            long startTime = System.currentTimeMillis(); // start timer

            for (int j = 0; j < WORD_NUMS; j++) {
                hashTrie.insert(words[j], 0);
            }

            long endTime = System.currentTimeMillis(); // end timer
            if (NUM_WARMUP <= i) { // Throw away first NUM_WARMUP runs to exclude JVM warmup
                totalTime += (endTime - startTime);
            }
        }
        return totalTime / (NUM_TESTS - NUM_WARMUP);
    }

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader("alice.txt"));

        // gets all the lines from alice.txt
        String[] lines = new String[7000];
        String line;
        int linesIndex = 0;
        while ((line = br.readLine()) != null) {
            lines[linesIndex] = line;
            linesIndex++;
        }
        // gets all the words from alice.txt (lines) up to 200000 words

        String[] stringWords = new String[200000];
        AlphabeticString[] words = new AlphabeticString[200000];
        int alphaIndex = 0;

        for (int i = 0; i < 1000; i++) {
            stringWords = lines[i].split(" ");
            for (String stringWord : stringWords) {
                words[alphaIndex] = new AlphabeticString(stringWord);
                alphaIndex++; // incrementing words, and keeps track of how many words total (27420 total words)
            }
        }
        // now we have all our words from Alice.txt

        // these are our data structures we need to compare
        ChainingHashTable<AlphabeticString, Integer> table = new ChainingHashTable<>(MoveToFrontList::new);
        BinarySearchTree<AlphabeticString, Integer> bst = new BinarySearchTree<>();
        AVLTree<AlphabeticString, Integer> avl = new AVLTree<>();
        HashTrieMap<Character,AlphabeticString, Integer> hashTrie = new HashTrieMap<>(AlphabeticString.class);

        // print out the times it takes
        System.out.println("ChainingHashTable took " + timeComparisonChain(words) + " MS");
        System.out.println("BST took " + timeComparisonBST(words) + " MS");
        System.out.println("AVL took " + timeComparisonAVL(words) + " MS");
        System.out.println("HashTrie took " + timeComparisonTrie(words) + " MS");



    }

}
