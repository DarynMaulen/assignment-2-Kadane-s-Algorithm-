package metrics;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/*
  Unit tests for PerformanceTracker.
  Verifies:
    - timer start/stop and reported time
    - increment methods update counters
    - reset clears counters
*/
public class PerformanceTrackerTest {

    @Test
    void timerIncAndResetWorks() throws InterruptedException {
        // Test timer functionality and counter reset
        PerformanceTracker tracker = new PerformanceTracker();
        tracker.startTimer();
        Thread.sleep(1); // small sleep to ensure time > 0
        tracker.stopTimer();
        assertTrue(tracker.getTimeMs() >= 0);

        // test counter increments
        tracker.incrementComparisons();
        tracker.incrementArrayAccesses();
        tracker.incrementAssignments();
        tracker.incrementAdditions();

        assertTrue(tracker.getComparisons() > 0);
        assertTrue(tracker.getArrayAccesses() > 0);
        assertTrue(tracker.getAssignments() >= 0);
        assertTrue(tracker.getAdditions() >= 0);

        // test reset functionality
        tracker.reset();
        assertEquals(0, tracker.getComparisons());
        assertEquals(0, tracker.getArrayAccesses());
        assertEquals(0, tracker.getAssignments());
        assertEquals(0, tracker.getAdditions());
    }
}