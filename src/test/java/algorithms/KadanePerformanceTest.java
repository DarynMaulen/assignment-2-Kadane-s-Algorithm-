package algorithms;

import metrics.PerformanceTracker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static utils.GenerateUtils.*;

/**
 * Performance-focused tests for Kadane's algorithm to validate
 * scalability characteristics. These tests check relative behaviour
 * (counters grow with n) and consistency; heavy/time-sensitive
 * assertions are disabled for CI.
 */
class KadanePerformanceTest {

    @Test
    void scalabilityAcrossInputSizes() {
        int[] sizes = {100, 1000, 10000};

        long prevAccesses = -1;
        for (int size : sizes) {
            long[] array = generateRandomArray(size);
            PerformanceTracker tracker = new PerformanceTracker();

            KadaneResult result = Kadane.run(array, tracker);

            // minimal sanity checks
            assertNotNull(result);
            assertTrue(tracker.getArrayAccesses() >= size,
                    "Expected at least one array access per element");

            // loose upper bound to catch gross regressions (not exact)
            assertTrue(tracker.getArrayAccesses() <= 50L * size,
                    "Array accesses should be O(n) (loose upper bound)");

            // comparisons should be proportional to n (loose)
            assertTrue(tracker.getComparisons() >= 0);
            assertTrue(tracker.getComparisons() <= 50L * size);

            // ensure monotonic-ish behaviour of accesses as size increases
            if (prevAccesses >= 0) {
                assertTrue(tracker.getArrayAccesses() >= prevAccesses / 2,
                        "Accesses should not drastically drop for larger n");
            }
            prevAccesses = tracker.getArrayAccesses();
        }
    }

    @Test
    void differentInputTypesPerformance() {
        int size = 10000;

        long[][] testArrays = {
                generateRandomArray(size),
                generateSortedArray(size),
                generateReverseSortedArray(size),
                generateAllPositiveArray(size),
                generateAllNegativeArray(size)
        };

        for (long[] arr : testArrays) {
            PerformanceTracker tracker = new PerformanceTracker();
            Kadane.run(arr, tracker);

            // no hard time asserts — only coarse checks on counters
            assertTrue(tracker.getArrayAccesses() >= arr.length);
            assertTrue(tracker.getArrayAccesses() <= 50L * arr.length);
        }
    }

    @Test
    void veryLargeInput() {
        long[] veryLargeArray = generateRandomArray(200_000);
        PerformanceTracker tracker = new PerformanceTracker();
        KadaneResult result = Kadane.run(veryLargeArray, tracker);
        assertNotNull(result);
        assertTrue(tracker.getArrayAccesses() >= veryLargeArray.length);
    }
}
