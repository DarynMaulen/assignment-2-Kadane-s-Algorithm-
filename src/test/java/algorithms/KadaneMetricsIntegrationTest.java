package algorithms;

import metrics.PerformanceTracker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/*
  Integration tests for Kadane instrumentation.
  Verifies:
    - instrumented run returns the same KadaneResult as plain run
    - basic sanity checks on tracker counters (non-negative, at least one access per element)
    - instrumented call on empty array does not throw
*/

public class KadaneMetricsIntegrationTest {

    @Test
    void instrumentedAndPlainProduceSameResultAndMetricsPositive(){
        // Integration test: verify metrics collection doesn't affect functionality
        long[] array = {-2, 1, -3, 4, -1, 2, 1, -5, 4};

        // plain execution
        KadaneResult plain = Kadane.run(array);

        // instrumented execution
        PerformanceTracker tracker = new PerformanceTracker();
        KadaneResult instr = Kadane.run(array, tracker);

        // functional equality
        assertEquals(plain, instr);

        // metrics sanity checks
        assertTrue(tracker.getTimeMs() >= 0, "time should be non-negative");
        assertTrue(tracker.getArrayAccesses() >= array.length, "at least one array access per element expected");
        assertTrue(tracker.getComparisons() > 0, "comparisons should be positive");
        assertTrue(tracker.getAssignments() >= 1, "should have at least one assignment");
    }

    @Test
    void emptyArrayInstrumentation() {
        // Test metrics collection with empty array
        long[] array = new long[0];
        PerformanceTracker tracker = new PerformanceTracker();
        KadaneResult r = Kadane.run(array, tracker);

        assertEquals(new KadaneResult(0L, -1, -1), r);

        assertTrue(tracker.getTimeMs() >= 0);
        assertTrue(tracker.getArrayAccesses() >= 0);
    }
}