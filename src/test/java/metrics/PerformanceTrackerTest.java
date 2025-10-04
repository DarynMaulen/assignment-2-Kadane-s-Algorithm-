package metrics;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PerformanceTrackerTest {
    @Test
    void timerIncAndResetWorks() throws InterruptedException {
        PerformanceTracker tracker = new PerformanceTracker();
        tracker.startTimer();
        Thread.sleep(1); // small sleep to ensure time > 0
        tracker.stopTimer();
        assertTrue(tracker.getTimeMs() >= 0);

        // increments
        tracker.incrementComparisons();
        tracker.incrementArrayAccesses();
        tracker.incrementAssignments();
        tracker.incrementAdditions();

        assertTrue(tracker.getComparisons() > 0);
        assertTrue(tracker.getArrayAccesses() > 0);
        assertTrue(tracker.getAssignments() >= 0);
        assertTrue(tracker.getAdditions() >= 0);

        tracker.reset();
        assertEquals(0, tracker.getComparisons());
        assertEquals(0, tracker.getArrayAccesses());
        assertEquals(0, tracker.getAssignments());
        assertEquals(0, tracker.getAdditions());
    }
}
