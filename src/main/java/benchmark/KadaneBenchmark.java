package benchmark;

import algorithms.Kadane;
import algorithms.KadaneResult;
import metrics.PerformanceTracker;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * JMH benchmark for Kadane:
 * - measures plain (no tracker) and instrumented (tracker) runs
 * - generates input once per trial to avoid measuring generation overhead
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(2)
@State(Scope.Benchmark)
public class KadaneBenchmark {

    @Param({"100", "1000", "10000", "100000"})
    private int size;

    @Param({"random", "sorted", "reverse_sorted", "all_positive", "all_negative"})
    private String dataType;

    // the array used by the benchmark methods, prepared in @Setup
    private long[] array;

    // use a fixed seed for reproducibility; change if you want different arrays across trials
    private static final long SEED = 42L;

    @Setup(Level.Trial)
    public void setup() {
        this.array = generateArrayBasedOnType(size, dataType, SEED);
    }

    // Plain benchmark
    @Benchmark
    public KadaneResult plain() {
        // return the result so JMH treats it as a real operation
        return Kadane.run(array);
    }

    // Instrumented benchmark: collects counters via PerformanceTracker
    @Benchmark
    public void instrumented(Blackhole bh) {
        PerformanceTracker tracker = new PerformanceTracker();
        KadaneResult r = Kadane.run(array, tracker);
        // consume both result and counters to prevent dead code elimination
        bh.consume(r);
        bh.consume(tracker.getArrayAccesses());
        bh.consume(tracker.getComparisons());
    }

    // Helper: generate arrays deterministically
    private static long[] generateArrayBasedOnType(int size, String type, long seed) {
        Random random = new Random(seed);
        long[] array = new long[size];

        switch (type) {
            case "random":
                for (int i = 0; i < size; i++) array[i] = random.nextInt(2001) - 1000;
                break;
            case "sorted":
                for (int i = 0; i < size; i++) array[i] = i + 1L;
                break;
            case "reverse_sorted":
                for (int i = 0; i < size; i++) array[i] = size - i;
                break;
            case "all_positive":
                for (int i = 0; i < size; i++) array[i] = Math.abs(random.nextInt(1000)) + 1L;
                break;
            case "all_negative":
                for (int i = 0; i < size; i++) array[i] = - (Math.abs(random.nextInt(1000)) + 1L);
                break;
            default:
                throw new IllegalArgumentException("Unknown data type: " + type);
        }
        return array;
    }
}
