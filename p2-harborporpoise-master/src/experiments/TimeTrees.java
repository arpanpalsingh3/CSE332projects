package experiments;

import cse332.datastructures.trees.BinarySearchTree;
import datastructures.dictionaries.AVLTree;
import datastructures.dictionaries.ChainingHashTable;
import datastructures.dictionaries.MoveToFrontList;
import javafx.scene.shape.MoveTo;

import java.io.IOException;

public class TimeTrees {

    public static void main(String[] args) throws IOException {
        int[] worst_case = new int[100000];
        int[] average_case = new int[100000];
        for (int i = 0; i < worst_case.length; i++) {
            worst_case[i] = i;
        }
        int index = 3;
        int values = 0;
        while (index < average_case.length) {
            for (int i = 0; i < 4; i++) {
                average_case[index] = values;
                index--;
                values++;
            }
            index += 8;
        }
        int input_size = 50000;
        System.out.println("INPUT SIZE: " + input_size);
        testChainMVTFLInsert(average_case, input_size);
        testChainMVTFLFind(average_case, input_size);
        testChainAVLInsert(average_case, input_size);
        testChainAVLFind(average_case, input_size);
        testChainBSTInsert(average_case, input_size);
        testChainBSTFind(average_case, input_size);
    }

    public static void testAVLInsert(int[] input_data, int size) {
        System.out.println("===============================");
        double totalTime = 0;
        int NUM_WARM = 11;
        int NUM_TESTS = 19;
        for (int i = 0; i < NUM_TESTS; i++) {
            AVLTree<Integer, Integer> avl = new AVLTree();
            long startTime = System.currentTimeMillis();
            for (int j = 0; j < size; j++) {
                avl.insert(j, j);
            }
            long endTime = System.currentTimeMillis();
            if(NUM_WARM <= i) {
                totalTime += (endTime - startTime);
            }
        }
        double average = totalTime / (NUM_TESTS - NUM_WARM);
        System.out.println("AVERAGE TIME (AVL INSERT): " + average + " MS");
    }

    public static void testAVLFind(int[] input_data, int size) {
        System.out.println("===============================");
        double totalTime = 0;
        int NUM_WARM = 3;
        int NUM_TESTS = 6 ;
        for (int i = 0; i < NUM_TESTS; i++) {
            AVLTree<Integer, Integer> avl = new AVLTree();
            for (int j = 0; j < size; j++) {
                avl.insert(j, j);
            }
            long startTime = System.currentTimeMillis();
            for (int j = 0; j < size; j++) {
                avl.find(j);
            }
            long endTime = System.currentTimeMillis();
            if(NUM_WARM <= i) {
                totalTime += (endTime - startTime);
            }
        }
        double average = totalTime / (NUM_TESTS - NUM_WARM);
        System.out.println("AVERAGE TIME (AVL FIND): " + average + " MS");
    }

    public static void testBSTInsert(int[] input_data, int size) {
        System.out.println("===============================");
        double totalTime = 0;
        int NUM_WARM = 3;
        int NUM_TESTS = 6 ;
        for (int i = 0; i < NUM_TESTS; i++) {
            BinarySearchTree<Integer, Integer> bst = new BinarySearchTree();
            long startTime = System.currentTimeMillis();
            for (int j = 0; j < size; j++) {
                bst.insert(j, j);
            }
            long endTime = System.currentTimeMillis();
            if(NUM_WARM <= i) {
                totalTime += (endTime - startTime);
            }
        }
        double average = totalTime / (NUM_TESTS - NUM_WARM);
        System.out.println("AVERAGE TIME (BST INSERT): " + average + " MS");
    }

    public static void testBSTFind(int[] input_data, int size) {
        System.out.println("===============================");
        double totalTime = 0;
        int NUM_WARM = 3;
        int NUM_TESTS = 6 ;
        for (int i = 0; i < NUM_TESTS; i++) {
            BinarySearchTree<Integer, Integer> bst = new BinarySearchTree();
            for (int j = 0; j < size; j++) {
                bst.insert(j, j);
            }
            long startTime = System.currentTimeMillis();
            for (int j = 0; j < size; j++) {
                bst.find(j);
            }
            long endTime = System.currentTimeMillis();
            if(NUM_WARM <= i) {
                totalTime += (endTime - startTime);
            }
        }
        double average = totalTime / (NUM_TESTS - NUM_WARM);
        System.out.println("AVERAGE TIME (BST FIND): " + average + " MS");
    }

    public static void testChainMVTFLInsert(int[] input_data, int size) {
        System.out.println("===============================");
        double totalTime = 0;
        int NUM_WARM = 3990;
        int NUM_TESTS = 5000;
        for (int i = 0; i < NUM_TESTS; i++) {
            ChainingHashTable<Integer, Integer> cht = new ChainingHashTable<>(MoveToFrontList::new);
            long startTime = System.currentTimeMillis();
            for (int j = 0; j < size; j++) {
                cht.insert(input_data[j], input_data[j]);
            }
            long endTime = System.currentTimeMillis();
            if(NUM_WARM <= i) {
                totalTime += (endTime - startTime);
            }
        }
        double average = totalTime / (NUM_TESTS - NUM_WARM);
        System.out.println("AVERAGE TIME (MVFL INSERT): " + average + " MS");
    }

    public static void testChainMVTFLFind(int[] input_data, int size) {
        System.out.println("===============================");
        double totalTime = 0;
        int NUM_WARM = 3990;
        int NUM_TESTS = 5000;
        for (int i = 0; i < NUM_TESTS; i++) {
            ChainingHashTable<Integer, Integer> cht = new ChainingHashTable<>(MoveToFrontList::new);
            for (int j = 0; j < size; j++) {
                cht.insert(input_data[j], input_data[j]);
            }
            long startTime = System.currentTimeMillis();
            for (int j = 0; j < size; j++) {
                cht.find(input_data[j]);
            }
            long endTime = System.currentTimeMillis();
            if(NUM_WARM <= i) {
                totalTime += (endTime - startTime);
            }
        }
        double average = totalTime / (NUM_TESTS - NUM_WARM);
        System.out.println("AVERAGE TIME (MVFL FIND): " + average + " MS");
    }
    public static void testChainAVLInsert(int[] input_data, int size) {
        System.out.println("===============================");
        double totalTime = 0;
        int NUM_WARM = 3990;
        int NUM_TESTS = 5000;
        for (int i = 0; i < NUM_TESTS; i++) {
            ChainingHashTable<Integer, Integer> cht = new ChainingHashTable<>(AVLTree::new);
            long startTime = System.currentTimeMillis();
            for (int j = 0; j < size; j++) {
                cht.insert(input_data[j], input_data[j]);
            }
            long endTime = System.currentTimeMillis();
            if(NUM_WARM <= i) {
                totalTime += (endTime - startTime);
            }
        }
        double average = totalTime / (NUM_TESTS - NUM_WARM);
        System.out.println("AVERAGE TIME (AVL INSERT): " + average + " MS");
    }
    public static void testChainAVLFind(int[] input_data, int size) {
        System.out.println("===============================");
        double totalTime = 0;
        int NUM_WARM = 3990;
        int NUM_TESTS = 5000;
        for (int i = 0; i < NUM_TESTS; i++) {
            ChainingHashTable<Integer, Integer> cht = new ChainingHashTable<>(AVLTree::new);
            for (int j = 0; j < size; j++) {
                cht.insert(input_data[j], input_data[j]);
            }
            long startTime = System.currentTimeMillis();
            for (int j = 0; j < size; j++) {
                cht.find(input_data[j]);
            }
            long endTime = System.currentTimeMillis();
            if(NUM_WARM <= i) {
                totalTime += (endTime - startTime);
            }
        }
        double average = totalTime / (NUM_TESTS - NUM_WARM);
        System.out.println("AVERAGE TIME (AVL FIND): " + average + " MS");
    }
    public static void testChainBSTInsert(int[] input_data, int size) {
        System.out.println("===============================");
        double totalTime = 0;
        int NUM_WARM = 3990;
        int NUM_TESTS = 5000;
        for (int i = 0; i < NUM_TESTS; i++) {
            ChainingHashTable<Integer, Integer> cht = new ChainingHashTable<>(BinarySearchTree::new);
            long startTime = System.currentTimeMillis();
            for (int j = 0; j < size; j++) {
                cht.insert(input_data[j], input_data[j]);
            }
            long endTime = System.currentTimeMillis();
            if(NUM_WARM <= i) {
                totalTime += (endTime - startTime);
            }
        }
        double average = totalTime / (NUM_TESTS - NUM_WARM);
        System.out.println("AVERAGE TIME (BST INSERT): " + average + " MS");
    }
    public static void testChainBSTFind(int[] input_data, int size) {
        System.out.println("===============================");
        double totalTime = 0;
        int NUM_WARM = 3990;
        int NUM_TESTS = 5000;
        for (int i = 0; i < NUM_TESTS; i++) {
            ChainingHashTable<Integer, Integer> cht = new ChainingHashTable<>(BinarySearchTree::new);
            for (int j = 0; j < size; j++) {
                cht.insert(input_data[j], input_data[j]);
            }
            long startTime = System.currentTimeMillis();
            for (int j = 0; j < size; j++) {
                cht.find(input_data[j]);
            }
            long endTime = System.currentTimeMillis();
            if(NUM_WARM <= i) {
                totalTime += (endTime - startTime);
            }
        }
        double average = totalTime / (NUM_TESTS - NUM_WARM);
        System.out.println("AVERAGE TIME (BST FIND): " + average + " MS");
    }
}
