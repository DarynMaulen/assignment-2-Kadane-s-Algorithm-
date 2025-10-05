package cli;

import java.util.Scanner;

/**
 * Interactive menu to run BenchmarkRunner without typing long terminal commands.
 * It builds the equivalent args array and calls BenchmarkRunner.main(args).
 */
public class BenchmarkMenu {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Kadane Benchmark Interactive Menu ===");
        System.out.print("Enter sizes (comma-separated, e.g. 100,1000,10000): ");
        String sizes = sc.nextLine().trim();
        if (sizes.isEmpty()) sizes = "100,1000,10000";

        System.out.println("Input types: random, sorted, reverse_sorted, all_positive, all_negative, nearly_sorted");
        System.out.print("Enter input-type (comma-separated allowed, default random): ");
        String inputTypes = sc.nextLine().trim();
        if (inputTypes.isEmpty()) inputTypes = "random";

        System.out.print("Enter trials per size (default 3): ");
        String trialsS = sc.nextLine().trim();
        if (trialsS.isEmpty()) trialsS = "3";

        System.out.print("Enter output CSV file (default benchmark_results.csv): ");
        String output = sc.nextLine().trim();
        if (output.isEmpty()) output = "benchmark_results.csv";

        // Build args exactly like from terminal
        String[] runnerArgs = new String[] {
                "--sizes", sizes,
                "--input-type", inputTypes,
                "--trials", trialsS,
                "--output", output
        };

        System.out.println("\nRunning with:");
        System.out.println("  sizes = " + sizes);
        System.out.println("  input-types = " + inputTypes);
        System.out.println("  trials = " + trialsS);
        System.out.println("  output = " + output);
        System.out.println();

        // Delegate to existing BenchmarkRunner
        BenchmarkRunner.main(runnerArgs);

        sc.close();
    }
}
