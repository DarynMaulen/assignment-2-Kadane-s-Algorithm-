package algorithms;

import metrics.PerformanceTracker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class KadaneMetricsIntegrationTest {
    @Test
    void instrumentedAndPlainProduceSameResultAndMetricsPositive(){
        long[] array = {-2, 1, -3, 4, -1, 2, 1, -5, 4};

        // plain
        KadaneResult plain = Kadane.run(array);

        // instrumented
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
        long[] array = new long[0];
        PerformanceTracker tracker = new PerformanceTracker();
        KadaneResult r = Kadane.run(array, tracker);

        assertEquals(new KadaneResult(0L, -1, -1), r);

        assertTrue(tracker.getTimeMs() >= 0);
        assertTrue(tracker.getArrayAccesses() >= 0);
    }
}
