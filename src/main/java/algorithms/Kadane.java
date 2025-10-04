package algorithms;

import metrics.PerformanceTracker;

public final class Kadane {
    public static KadaneResult run(long[] array){
        return run(array,null);
    }

    public static KadaneResult run(long[] array, PerformanceTracker tracker){
        if (array == null) {
            throw new IllegalArgumentException("Input is null");
        }

        if (array.length == 0) {
            return new KadaneResult(0L,-1,-1);
        }

        startAlgorithm(tracker);

        incrementArrayAccesses(tracker);
        long maxEnding = array[0];
        incrementArrayAccesses(tracker);
        long maxSoFar = array[0];
        int temporaryStart = 0;
        int start = 0;
        int end = 0;

        for (int i = 1; i < array.length; i++) {
            incrementArrayAccesses(tracker);
            long currentEnding = array[i];

            incrementAdditions(tracker);
            long sum = currentEnding + maxEnding;

            incrementComparisons(tracker);
            incrementAssignments(tracker);
            if( currentEnding > sum ) {
                maxEnding = currentEnding;
                temporaryStart = i;
            }else{
                maxEnding = sum;
            }

            incrementComparisons(tracker);
            if(maxEnding > maxSoFar) {
                incrementAssignments(tracker,3);
                maxSoFar = maxEnding;
                start = temporaryStart;
                end = i;
            }
        }

        stopAlgorithm(tracker);
        return new KadaneResult(maxSoFar,start,end);
    }

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
