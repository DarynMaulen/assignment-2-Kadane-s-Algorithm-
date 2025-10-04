package cli;

import java.io.File;
import java.util.Arrays;

/**
 * Immutable-ish configuration for benchmark runner.
 * Fields are public for simple access; validateConfig ensures correctness.
 */
public class BenchmarkConfig {
    public int[] sizes;
    public String inputType;
    public int trials = 3; // default
    public File outputFile;

    @Override
    public String toString() {
        return "BenchmarkConfig{" +
                "sizes=" + (sizes == null ? "null" : Arrays.toString(sizes)) +
                ", inputType='" + inputType +
                ", trials=" + trials +
                ", outputFile=" + (outputFile == null ? "null" : outputFile.getAbsolutePath()) +
                '}';
    }
}
