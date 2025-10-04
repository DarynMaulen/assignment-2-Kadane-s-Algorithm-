package metrics;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/*
  Simple CSV writer for metrics.
  - writeHeader(file): create/truncate file and write header row.
  - appendLine(file, algorithm, inputType, n, trial, tracker): append one CSV row using tracker counters.
*/
public class MetricsCsvWriter {
    public static final String HEADER = "algorithm,input_type,n,trial,time_ms,comparisons,array_accesses,assignments,additions";

    public static void writeHeader(File file) throws IOException {
        try(PrintWriter out = new PrintWriter(new FileWriter(file, false))) {
            out.println(HEADER);
        }
    }

    public static void appendLine(File file,
                                  String algorithm,
                                  String inputType,
                                  int n,
                                  int trial,
                                  PerformanceTracker tracker) throws IOException {
        try(PrintWriter out = new PrintWriter(new FileWriter(file, true))) {
            String line = String.format("%s,%s,%d,%d,%d,%d,%d,%d,%d",
                    algorithm,
                    inputType,
                    n,
                    trial,
                    tracker.getTimeMs(),
                    tracker.getComparisons(),
                    tracker.getArrayAccesses(),
                    tracker.getAssignments(),
                    tracker.getAdditions()
            );
            out.println(line);
        }
    }
}
