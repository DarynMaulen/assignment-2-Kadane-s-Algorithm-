package algorithms;

import metrics.PerformanceTracker;

/*
  Kadane algorithm: finds maximum subarray sum and its start/end indices.
  - run(array): plain version (no metrics).
  - run(array, tracker): instrumented version that records simple counters
    into the provided PerformanceTracker (null disables metrics).
  - Returns KadaneResult(maxSum, startIndex, endIndex).
*/
public final class Kadane {
    /**
     * Plain run without metrics.
     */
    public static KadaneResult run(long[] array){
        return run(array,null);
    }

    /**
     * Instrumented run with optional PerformanceTracker.
     * The tracker is reset and timed at the start; counters are updated
     * during the algorithm and the timer is stopped before returning.
     */
    public static KadaneResult run(long[] array, PerformanceTracker tracker){
        if (array == null) {
            throw new IllegalArgumentException("Input is null");
        }

        if (array.length == 0) {
            return new KadaneResult(0L,-1,-1);
        }

        startAlgorithm(tracker);

        // initial element reads
        incrementArrayAccesses(tracker);
        long maxEnding = array[0];
        incrementArrayAccesses(tracker);
        long maxSoFar = array[0];

        int temporaryStart = 0;
        int start = 0;
        int end = 0;

        // single pass O(n)
        for (int i = 1; i < array.length; i++) {
            // read element once and count access
            incrementArrayAccesses(tracker);
            long currentEnding = array[i];

            // count arithmetic operation
            incrementAdditions(tracker);
            long sum = currentEnding + maxEnding;

            // decide whether to start new subarray or extend
            incrementComparisons(tracker);
            incrementAssignments(tracker);
            if( currentEnding > sum ) {
                maxEnding = currentEnding;
                temporaryStart = i;
            }else{
                maxEnding = sum;
            }

            // update global best if needed
            incrementComparisons(tracker);
            if(maxEnding > maxSoFar) {
                // three logical assignments: maxSoFar, start, end
                incrementAssignments(tracker,3);
                maxSoFar = maxEnding;
                start = temporaryStart;
                end = i;
            }
        }

        stopAlgorithm(tracker);
        return new KadaneResult(maxSoFar,start,end);
    }

    /*
      Helper: start/stop metrics and utility increment methods.
      These simply guard tracker != null and forward to tracker.
    */
    private static void startAlgorithm(PerformanceTracker tracker){
        if (tracker != null) {
            tracker.reset();
            tracker.startTimer();
        }
    }

    private static void stopAlgorithm(PerformanceTracker tracker){
        if (tracker != null) {
            tracker.stopTimer();
        }
    }

    private static void incrementArrayAccesses(PerformanceTracker tracker){
        if(tracker != null){
            tracker.incrementArrayAccesses();
        }
    }

    private static void incrementAssignments(PerformanceTracker tracker){
        incrementAssignments(tracker,1);
    }
    private static void incrementAssignments(PerformanceTracker tracker, long n){
        if(tracker != null){
            tracker.incrementAssignments(n);
        }
    }

    private static void incrementAdditions(PerformanceTracker tracker){
        if(tracker != null){
            tracker.incrementAdditions();
        }
    }

    private static void incrementComparisons(PerformanceTracker tracker){
        if(tracker != null){
            tracker.incrementComparisons();
        }
    }
}
