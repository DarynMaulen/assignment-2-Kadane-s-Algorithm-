package cli;

import algorithms.Kadane;
import algorithms.KadaneResult;
import metrics.PerformanceTracker;
import metrics.MetricsCsvWriter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

/**
 * CLI benchmark runner for Kadane's algorithm with configurable input sizes and types.
 */
public class BenchmarkRunner {

    public static void main(String[] args) {
        if (args.length == 0 || (args.length == 1 && (args[0].equals("--help") || args[0].equals("-h")))) {
            printUsage();
            return;
        }

        try {
            BenchmarkConfig config = parseArguments(args);
            validateConfig(config);

            // ensure output file exists / create parent dirs
            if (config.outputFile == null) {
                config.outputFile = new File("benchmark_results.csv");
            }
            Path outPath = config.outputFile.toPath();
            if (outPath.getParent() != null) {
                Files.createDirectories(outPath.getParent());
            }
            if (!Files.exists(outPath)) {
                Files.createFile(outPath);
            }

            System.out.println("Running benchmarks with configuration:");
            System.out.println("  Sizes: " + Arrays.toString(config.sizes));
            System.out.println("  Input type: " + config.inputType);
            System.out.println("  Trials: " + config.trials);
            System.out.println("  Output: " + config.outputFile.getAbsolutePath());

            runBenchmarks(config);
        } catch (IllegalArgumentException e) {
            System.err.println("Argument error: " + e.getMessage());
            printUsage();
            System.exit(1);
        } catch (IOException e) {
            System.err.println("I/O error while preparing output file: " + e.getMessage());
            System.exit(2);
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace(System.err);
            System.exit(3);
        }
    }

    private static BenchmarkConfig parseArguments(String[] args) {
        BenchmarkConfig config = new BenchmarkConfig();

        for (int i = 0; i < args.length; i++) {
            String a = args[i];
            switch (a) {
                case "--sizes":
                    if (i + 1 >= args.length) throw new IllegalArgumentException("--sizes requires a comma-separated list");
                    config.sizes = parseSizes(args[++i]);
                    break;
                case "--input-type":
                    if (i + 1 >= args.length) throw new IllegalArgumentException("--input-type requires a value");
                    config.inputType = args[++i].toLowerCase();
                    break;
                case "--trials":
                    if (i + 1 >= args.length) throw new IllegalArgumentException("--trials requires a number");
                    try {
                        config.trials = Integer.parseInt(args[++i]);
                    } catch (NumberFormatException ex) {
                        throw new IllegalArgumentException("Invalid trials value: must be an integer");
                    }
                    break;
                case "--output":
                    if (i + 1 >= args.length) throw new IllegalArgumentException("--output requires a file path");
                    config.outputFile = new File(args[++i]);
                    break;
                case "--help":
                case "-h":
                    printUsage();
                    System.exit(0);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown argument: " + a);
            }
        }
        return config;
    }

    private static void runBenchmarks(BenchmarkConfig config) throws IOException {
        // Write CSV header (overwrites existing file)
        MetricsCsvWriter.writeHeader(config.outputFile);

        for (int size : config.sizes) {
            System.out.println("Testing size: " + size);

            for (int trial = 1; trial <= config.trials; trial++) {
                long[] array = DataGenerator.generateArray(size, config.inputType);
                PerformanceTracker tracker = new PerformanceTracker();

                try {
                    MetricsCsvWriter.appendLine(config.outputFile, "Kadane",
                            config.inputType, size, trial, tracker);
                } catch (IOException e) {
                    System.err.println("Failed to write CSV row: " + e.getMessage());
                    throw e;
                }

                // print lightweight summary (no strict timing assertions)
                System.out.printf("  Trial %d: accesses=%d, comparisons=%d, assignments=%d%n",
                        trial, tracker.getArrayAccesses(), tracker.getComparisons(), tracker.getAssignments());
            }
        }

        System.out.println("Benchmark completed. Results saved to: " + config.outputFile.getAbsolutePath());
    }

    private static int[] parseSizes(String sizesStr) {
        String[] parts = sizesStr.split(",");
        int[] sizes = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            sizes[i] = Integer.parseInt(parts[i].trim());
            if (sizes[i] <= 0) throw new IllegalArgumentException("Sizes must be positive integers");
        }
        return sizes;
    }

    private static void validateConfig(BenchmarkConfig config) {
        if (config.sizes == null || config.sizes.length == 0) {
            throw new IllegalArgumentException("Sizes must be specified (use --sizes)");
        }
        if (config.inputType == null) {
            throw new IllegalArgumentException("Input type must be specified (use --input-type)");
        }
        // validate input type known
        switch (config.inputType.toLowerCase()) {
            case "random":
            case "sorted":
            case "reverse_sorted":
            case "all_positive":
            case "all_negative":
            case "nearly_sorted":
                break;
            default:
                throw new IllegalArgumentException("Unknown input type: " + config.inputType);
        }
        if (config.trials <= 0) {
            throw new IllegalArgumentException("Trials must be positive");
        }
    }

    private static void printUsage() {
        System.out.println("Kadane Algorithm Benchmark Runner");
        System.out.println("Usage: java -cp target/classes cli.BenchmarkRunner [options]");
        System.out.println();
        System.out.println("Options:");
        System.out.println("  --sizes <n1,n2,n3>    Array sizes to test (comma-separated)");
        System.out.println("  --input-type <type>   Input data type: random, sorted, reverse_sorted, all_positive, all_negative, nearly_sorted");
        System.out.println("  --trials <n>          Number of trials per size (default: 3)");
        System.out.println("  --output <file>       Output CSV file (default: benchmark_results.csv)");
        System.out.println("  --help, -h            Show this help message");
    }
}
