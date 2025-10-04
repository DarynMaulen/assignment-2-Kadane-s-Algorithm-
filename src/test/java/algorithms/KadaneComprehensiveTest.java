package algorithms;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;
import static utils.GenerateUtils.*;

/**
 * Comprehensive test suite for Kadane's algorithm covering all edge cases,
 * input distributions, and scalability requirements.
 */
class KadaneComprehensiveTest {

    // ===== BASIC EDGE CASES =====

    @Test
    void nullInputThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> Kadane.run(null));
    }

    @Test
    void emptyArrayReturnsZeroAndInvalidIndices() {
        KadaneResult result = Kadane.run(new long[0]);
        assertEquals(0L, result.getMaxSum());
        assertEquals(-1, result.getStartIndex());
        assertEquals(-1, result.getEndIndex());
    }

    @Test
    void singleElementPositive() {
        KadaneResult result = Kadane.run(new long[]{5});
        assertEquals(5L, result.getMaxSum());
        assertEquals(0, result.getStartIndex());
        assertEquals(0, result.getEndIndex());
    }

    @Test
    void singleElementNegative() {
        KadaneResult result = Kadane.run(new long[]{-5});
        assertEquals(-5L, result.getMaxSum());
        assertEquals(0, result.getStartIndex());
        assertEquals(0, result.getEndIndex());
    }

    @Test
    void singleElementZero() {
        KadaneResult result = Kadane.run(new long[]{0});
        assertEquals(0L, result.getMaxSum());
        assertEquals(0, result.getStartIndex());
        assertEquals(0, result.getEndIndex());
    }

    // ===== CLASSICAL EXAMPLES =====

    @Test
    void classicalExample() {
        KadaneResult result = Kadane.run(new long[]{-2, 1, -3, 4, -1, 2, 1, -5, 4});
        assertEquals(6L, result.getMaxSum());
        assertEquals(3, result.getStartIndex());
        assertEquals(6, result.getEndIndex());
    }

    @Test
    void allNegativeArrayReturnsMaxElement() {
        KadaneResult result = Kadane.run(new long[]{-8, -3, -6, -2, -5, -4});
        assertEquals(-2L, result.getMaxSum());
        assertEquals(3, result.getStartIndex());
        assertEquals(3, result.getEndIndex());
    }

    @Test
    void allPositiveArrayReturnsTotalSum() {
        long[] array = {1, 2, 3, 4, 5};
        KadaneResult result = Kadane.run(array);
        assertEquals(15L, result.getMaxSum());
        assertEquals(0, result.getStartIndex());
        assertEquals(4, result.getEndIndex());
    }

    // ===== INPUT DISTRIBUTION TESTS =====

    @Test
    void sortedAscendingPositive() {
        long[] array = {1, 2, 3, 4, 5};
        KadaneResult result = Kadane.run(array);
        assertEquals(15L, result.getMaxSum());
    }

    @Test
    void sortedDescendingPositive() {
        long[] array = {5, 4, 3, 2, 1};
        KadaneResult result = Kadane.run(array);
        assertEquals(15L, result.getMaxSum());
    }

    @Test
    void nearlySortedArray() {
        long[] array = {1, 3, 2, 4, 6, 5};
        KadaneResult result = Kadane.run(array);
        assertEquals(21L, result.getMaxSum());
    }

    @Test
    void arrayWithAllZeros() {
        long[] array = {0, 0, 0, 0, 0};
        KadaneResult result = Kadane.run(array);
        assertEquals(0L, result.getMaxSum());
        assertTrue(result.getStartIndex() >= -1 && result.getStartIndex() < array.length);
        assertTrue(result.getEndIndex() >= -1 && result.getEndIndex() < array.length);
    }

    @Test
    void arrayWithMixedZeros() {
        long[] array = {-1, 0, 2, 0, -3, 1};
        KadaneResult result = Kadane.run(array);
        assertEquals(2L, result.getMaxSum());
    }

    @Test
    void arrayWithDuplicates() {
        long[] array = {2, 2, 2, 2};
        KadaneResult result = Kadane.run(array);
        assertEquals(8L, result.getMaxSum());
        assertEquals(0, result.getStartIndex());
        assertEquals(3, result.getEndIndex());
    }

    @Test
    void alternatingPositiveNegative() {
        long[] array = {1, -1, 1, -1, 1, -1, 1};
        KadaneResult result = Kadane.run(array);
        assertEquals(1L, result.getMaxSum());
    }

    // ===== POSITION TESTS =====

    @Test
    void maxSubarrayAtBeginning() {
        long[] array = {5, -1, -2, -3, 1};
        KadaneResult result = Kadane.run(array);
        assertEquals(5L, result.getMaxSum());
        assertEquals(0, result.getStartIndex());
        assertEquals(0, result.getEndIndex());
    }

    @Test
    void maxSubarrayAtEnd() {
        long[] array = {-3, -1, 5, 2};
        KadaneResult result = Kadane.run(array);
        assertEquals(7L, result.getMaxSum());
        assertEquals(2, result.getStartIndex());
        assertEquals(3, result.getEndIndex());
    }

    @Test
    void maxSubarrayInMiddle() {
        long[] array = {-2, 1, -3, 4, -1, 2, 1, -5};
        KadaneResult result = Kadane.run(array);
        assertEquals(6L, result.getMaxSum());
        assertEquals(3, result.getStartIndex());
        assertEquals(6, result.getEndIndex());
    }

    // ===== PROPERTY-BASED TESTING =====

    @Test
    void randomArraysCompareWithBruteForce() {
        Random rand = new Random(12345);
        for (int t = 0; t < 100; t++) {
            long[] array = new long[1 + rand.nextInt(50)];
            for (int i = 0; i < array.length; i++) {
                array[i] = rand.nextInt(201) - 100;
            }
            KadaneResult expected = bruteForce(array);
            KadaneResult result = Kadane.run(array);
            assertEquals(expected, result, "Mismatch for input: " + Arrays.toString(array));
        }
    }

    // ===== SCALABILITY TESTS =====

    @Test
    void largeRandomArray() {
        long[] largeArray = generateRandomArray(100000);
        KadaneResult result = Kadane.run(largeArray);

        assertNotNull(result);
        assertTrue(result.getMaxSum() >= Long.MIN_VALUE);
        assertTrue(result.getStartIndex() >= -1 && result.getStartIndex() < largeArray.length);
        assertTrue(result.getEndIndex() >= -1 && result.getEndIndex() < largeArray.length);
    }

    @Test
    void largeAllPositiveArray() {
        long[] array = generateAllPositiveArray(50000);
        KadaneResult result = Kadane.run(array);
        assertEquals(getArraySum(array), result.getMaxSum());
    }

    @Test
    void largeAllNegativeArray() {
        long[] array = generateAllNegativeArray(50000);
        KadaneResult result = Kadane.run(array);
        long expectedMax = Arrays.stream(array).max().getAsLong();
        assertEquals(expectedMax, result.getMaxSum());
    }
}