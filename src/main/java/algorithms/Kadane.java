package algorithms;

import metrics.PerformanceTracker;

/*
  Kadane algorithm with optimized instrumentation: local counters are accumulated
  inside the loop and flushed to the PerformanceTracker only once at the end.
  This reduces synchronized method call overhead when tracker != null.
*/
public final class Kadane {
    public static KadaneResult run(long[] array){
        return run(array, null);
    }

    public static KadaneResult run(long[] array, PerformanceTracker tracker){
        if (array == null) {
            throw new IllegalArgumentException("Input is null");
        }
        if (tracker != null) {
            tracker.reset();
        }
        if (array.length == 0) {
            return new KadaneResult(0L,-1,-1);
        }

        // fast path: if no tracker, run minimal-instrumentation loop
        if (tracker == null) {
            long maxEnding = array[0];
            long maxSoFar = array[0];
            int temporaryStart = 0;
            int start = 0;
            int end = 0;
            for (int i = 1, len = array.length; i < len; i++) {
                long current = array[i];
                long sum = current + maxEnding;
                if (current > sum) {
                    maxEnding = current;
                    temporaryStart = i;
                } else {
                    maxEnding = sum;
                }
                if (maxEnding > maxSoFar) {
                    maxSoFar = maxEnding;
                    start = temporaryStart;
                    end = i;
                }
            }
            return new KadaneResult(maxSoFar, start, end);
        }

        // instrumented path with local counter batching
        long localComparisons = 0;
        long localArrayAccesses = 0;
        long localAssignments = 0;
        long localAdditions = 0;

        // initial reads
        localArrayAccesses++;
        long maxEnding = array[0];
        localArrayAccesses++;
        long maxSoFar = array[0];
        int temporaryStart = 0;
        int start = 0;
        int end = 0;

        for (int i = 1, len = array.length; i < len; i++) {
            // access
            localArrayAccesses++;
            long current = array[i];

            // addition
            localAdditions++;
            long sum = current + maxEnding;

            // comparison to decide start/extend
            localComparisons++;
            localAssignments++; // for assignment to maxEnding (will be applied either way)
            if (current > sum) {
                maxEnding = current;
                temporaryStart = i;
            } else {
                maxEnding = sum;
            }

            // compare with global best
            localComparisons++;
            if (maxEnding > maxSoFar) {
                // we'll perform three logical assignments: maxSoFar, start, end
                localAssignments += 3;
                maxSoFar = maxEnding;
                start = temporaryStart;
                end = i;
            }
        }

        // flush local counters into shared tracker once
        // use the tracker API; tracker methods are synchronized so single calls are cheaper
        tracker.incrementComparisons(localComparisons);
        tracker.incrementArrayAccesses(localArrayAccesses);
        tracker.incrementAssignments(localAssignments);
        tracker.incrementAdditions(localAdditions);

        return new KadaneResult(maxSoFar, start, end);
    }

}
