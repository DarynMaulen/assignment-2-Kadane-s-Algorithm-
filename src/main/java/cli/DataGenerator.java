package cli;

import static utils.GenerateUtils.*;

/**
 * Generates test data arrays of various distributions
 */
public class DataGenerator {
    public static long[] generateArray(int size, String inputType) {
        if (inputType == null) throw new IllegalArgumentException("inputType must not be null");
        return switch (inputType.toLowerCase()) {
            case "random" -> generateRandomArray(size);
            case "sorted" -> generateSortedArray(size);
            case "reverse_sorted" -> generateReverseSortedArray(size);
            case "all_positive" -> generateAllPositiveArray(size);
            case "all_negative" -> generateAllNegativeArray(size);
            case "nearly_sorted" -> generateNearlySortedArray(size);
            default -> throw new IllegalArgumentException("Unknown input type: " + inputType);
        };
    }
}