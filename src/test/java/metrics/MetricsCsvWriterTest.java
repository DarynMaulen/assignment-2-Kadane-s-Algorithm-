package metrics;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;


public class MetricsCsvWriterTest {
    @Test
    void writeHeaderAndAppendLine() throws Exception {
        File tmp = Files.createTempFile("metrics_test", ".csv").toFile();
        tmp.deleteOnExit();

        PerformanceTracker tracker = new PerformanceTracker();
        tracker.incrementComparisons();
        tracker.incrementArrayAccesses();
        tracker.incrementAssignments();
        tracker.incrementAdditions();
        tracker.startTimer();
        tracker.stopTimer();

        MetricsCsvWriter.writeHeader(tmp);
        MetricsCsvWriter.appendLine(tmp, "Kadane", "random", 100, 1, tracker);

        String content = new String(Files.readAllBytes(tmp.toPath()));
        assertTrue(content.contains("algorithm,input_type,n,trial,time_ms,comparisons,array_accesses,assignments,additions"));
        assertTrue(content.contains("Kadane,random,100,1"));
    }
}
