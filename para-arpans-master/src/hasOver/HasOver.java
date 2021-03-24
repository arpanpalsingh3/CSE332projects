package hasOver;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class HasOver {
    private static int CUTOFF;
    private static final ForkJoinPool POOL = new ForkJoinPool();

    private static int sequential(int[]arr, int lo, int hi) {
        int max = arr[lo];
        for(int i = lo + 1; i < hi; i++) {
            if(arr[i] > max) {
                max = arr[i];
            }
        }
        return max;
    }

    public static boolean hasOver(int val, int[] arr, int sequentialCutoff) { // parallel
        CUTOFF = sequentialCutoff;
        return POOL.invoke(new LargestTask(val, arr, 0 , arr.length) );
    }

    public static class LargestTask extends RecursiveTask<Boolean> {
        int[] arr;
        int value, low, hi;

        public LargestTask(int val, int[] arrayParse, int lo, int high) {
            this.arr = arrayParse;
            this.value = val;
            this.low = lo;
            this.hi = high;
        }

        @Override
        protected Boolean compute() {
            if(hi - low <= CUTOFF) {
                return sequential(arr, low, hi) > value;
            }
            int mid = low + (hi - low)/2;

            LargestTask left = new LargestTask(value, arr, low, mid);
            LargestTask right = new LargestTask(value, arr, mid, hi);

            right.fork();

            boolean leftgetAnswer = left.compute();
            boolean rightAnswer = right.join();

            return leftgetAnswer || rightAnswer;
        }

    }

    private static void usage() {
        System.err.println("USAGE: HasOver <number> <array> <sequential cutoff>");
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
            System.out.println(hasOver(val, arr, Integer.parseInt(args[2])));
        } catch (NumberFormatException e) {
            usage();
        }

    }
}
