package experiments;

import cse332.types.AlphabeticString;
import datastructures.dictionaries.ChainingHashTable;
import datastructures.dictionaries.MoveToFrontList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;



public class CircArrayHashComparison {

    public static double hashTimeInsert(ChainingHashTable<AlphabeticString, Integer> table, AlphabeticString [] words) {
        final int NUM_TESTS = 5000;
        final int NUM_WARMUP = 3990;

        double totalTime = 0;
        for (int i = 0; i < NUM_TESTS; i++) {
            table = new ChainingHashTable<>(MoveToFrontList::new);
            long startTime = System.currentTimeMillis(); // start timer

                for (int j = 0; j < 500; j++) {
                    table.insert(words[j], 0);
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

        // gets all the lines from alice.txt up to 200000
        String [] lines = new String[7000];
        String line;
        int linesIndex = 0;
        while ((line = br.readLine()) != null)
        {
            lines[linesIndex] = line;
            linesIndex++;
        }
        // gets all the words from alice.txt (lines) up to 200000 words

        String [] stringWords = new String[200000];
        AlphabeticString[] words = new AlphabeticString[200000];
        int alphaIndex = 0;

        for (int i = 0; i < 1000; i++) {
            stringWords = lines[i].split(" ");
            for (String stringWord : stringWords) {
                words[alphaIndex] = new AlphabeticString(stringWord);
                alphaIndex++;
            }
        }

        // this is the chaining hash table, which uses AlphabeticStrings as keys
        ChainingHashTable<AlphabeticString, Integer> table = new ChainingHashTable<>(MoveToFrontList::new);

        // we send in an empty table and an array of alphabetic strings
        for(int j = 0; j < 2; j++) {
            long startTime = System.currentTimeMillis(); // start timer
            System.out.println("Trial " + (j+1) + " result = " + hashTimeInsert(table,words));
            long endTime = System.currentTimeMillis(); // end timer
            System.out.println("Trial " + (j+1) + " total time = " + (endTime-startTime)/1000 + " seconds for 2000 total tests, of which only 1010 counted");
        } // fin
    }
}
