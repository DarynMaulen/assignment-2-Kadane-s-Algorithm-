# Kadane Algorithm — Assignment 2

Maximum subarray (Kadane) implementation, instrumentation, testing, and benchmark CLI

## 📌 Overview

This repository contains an implementation of Kadane’s algorithm , extended with:

- Operation counters (comparisons, array accesses, assignments, additions).

- CLI benchmark runner with configurable input arrays.

- CSV export of performance metrics.

- Unit and property-based tests.

- Complexity analysis (Big-O/Θ/Ω).

## 📂 Project Structure
assignment2-kadane/

├── src/main/java/

│   ├── algorithms/Kadane.java

│   ├── algorithms/KadaneResult.java

│   ├── metrics/PerformanceTracker.java

│   ├── metrics/MetricsCsvWriter.java

│   └── cli/BenchmarkConfig.java

│   └── cli/BenchmarkRunner.java

│   └── cli/GenerateUtils.java

│   └── benchmark/KadaneBenchmark.java

│   └── benchmark/BenchmarkLauncher.java

├── src/test/java/...

├── docs/

│   └── analysis-report.pdf

├── README.md

└── pom.xml

## ⚙️ Build & Run

Build (Java 17+, Maven):

- mvn clean package


Run all tests:

- mvn test

## 🚀 CLI Usage

Run benchmark:

Use BenchmarkMenu

Options

- --sizes <n1,n2,...> — input sizes to test.

- --input-types <type> — random, sorted, reverse_sorted, all_positive, all_negative, nearly_sorted. (default: random)

- --trials <n> — number of trials per size (default: 3).

- --output <file> — CSV file path (default: benchmark_results.csv).

Example output:

Testing size: 100
  Trial 1: accesses=305, comparisons=200, assignments=150
...
Benchmark completed. Results saved to: results.csv

## 📑 CSV Format
algorithm,input_type,n,trial,comparisons,array_accesses,assignments,additions


- algorithm — always Kadane.

- input_type — type of generated array.

- n — array size.

- trial — trial number.

- comparisons, array_accesses, assignments, additions — operation counters.

## ✅ Testing

- Unit tests: null input, empty, single element, classic cases.

- Property-based tests: comparison with brute force on random arrays.

- Integration tests: equality of instrumented vs non-instrumented results.

- Performance smoke tests: large arrays (usually excluded from CI).

Run:

- mvn test

## 📊 Complexity Analysis

Let n = array size.

Time complexity:

- Worst-case: Θ(n)

- Average-case: Θ(n)

- Best-case: Θ(n)

The algorithm makes a single pass with O(1) work per element.

Space complexity:

- Θ(1) — only a few scalar variables are used.


