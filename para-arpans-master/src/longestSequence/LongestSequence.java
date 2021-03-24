package longestSequence;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class LongestSequence {
    private static int CUTOFF;
    private static final ForkJoinPool POOL = new ForkJoinPool();

    public static int getLongestSequence(int val, int[] arr, int sequentialCutoff) {
        CUTOFF = sequentialCutoff;
        SequenceRange result =  POOL.invoke(new longestSequenceTask(val, arr, 0, arr.length));
        return result.longestRange;
    }

    public static class longestSequenceTask extends RecursiveTask<SequenceRange> {
        int[] arr;
        int val, low, high;

        public longestSequenceTask(int value, int[] array, int low, int high) {
            this.arr = array;
            this.val = value;
            this.low = low;
            this.high = high;
        }

        @Override
        protected SequenceRange compute() {
            if(high - low <= CUTOFF) { // once under the cutoff (meaning enough splits), run sequential code
                return sequential(val, arr, low, high); 
            }
            int mid = low + (high - low)/2;

            longestSequenceTask left = new longestSequenceTask(val, arr, low, mid);
            longestSequenceTask right = new longestSequenceTask(val, arr, mid, high);

            right.fork();

            return combine(left.compute(),right.join()); // use combine to collect to get the max from left and right tree or splits
        }
    }

    private static SequenceRange combine(SequenceRange l , SequenceRange r) {
        int leftChain = l.matchingOnLeft; // chain from left
        int rightChain = r.matchingOnRight; // chain from right

        if(l.matchingOnLeft == l.sequenceLength) { // if left array is all the same
            // combine with right array from left
            leftChain = l.sequenceLength + r.matchingOnLeft;
        }
        if(r.matchingOnRight == r.sequenceLength) { // if right array is all the same
            // combine with left array from right
            rightChain = r.sequenceLength + l.matchingOnRight;
        }

        int maxChain = Math.max(l.longestRange, Math.max(leftChain, rightChain));
        maxChain = Math.max(maxChain,  Math.max(r.longestRange, l.matchingOnRight + r.matchingOnLeft));
        // create the final chain from the combinations
        return new SequenceRange(leftChain, rightChain, maxChain, r.sequenceLength + l.sequenceLength);
    }

    private static SequenceRange sequential(int val, int[]arr, int lo, int hi) {

        int min = Math.min(hi, arr.length);
        int maxChainLen = 0;
        int curChain = 0;

        int matchingOnRight = 0;
        int matchingOnLeft = 0;

        for (int i = lo; i < min; i++) { // how many left side matches
            if (arr[i] != val) {
                break; // get out if it doesnt match
            } else {
                matchingOnLeft++; // else increment
            }
        }

        for (int i = Math.min(hi - 1, arr.length); i >= lo; i--) { // how many right side matches
            if (arr[i] != val) {
                break; // get out if it doesnt match
            } else {
                matchingOnRight++; // else increment
            }
        }

        for (int i = lo; i < min; i++) { // counting longest back to back recurrence
            if (arr[i] == val) {
                curChain++; // increment
            } else if (curChain > maxChainLen) { // when the chain breaks
                maxChainLen = curChain; // update the max chain
                curChain = 0; // reset
            } else {
                curChain = 0; // if the new chain is smaller, still reset
            }
        }

        return new SequenceRange(matchingOnLeft, matchingOnRight, Math.max(maxChainLen, curChain), Math.min(hi, arr.length) - lo);
    }

// NO EDITS PAST THIS POINT ********************************

    private static void usage() {
        System.err.println("USAGE: LongestSequence <number> <array> <sequential cutoff>");
        System.exit(2);
    }

    public static void main(String[] args) {
        if (args.length != 3) {
            usage();
        }

        int val = 0;
        int[] arr = null;

        try {
            val = Integer.parseInt(args[0]);
            String[] stringArr = args[1].replaceAll("\\s*",  "").split(",");
            arr = new int[stringArr.length];
            for (int i = 0; i < stringArr.length; i++) {
                arr[i] = Integer.parseInt(stringArr[i]);
            }
            System.out.println(getLongestSequence(val, arr, Integer.parseInt(args[2])));
        } catch (NumberFormatException e) {
            usage();
        }
    }
}