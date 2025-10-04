package utils;

import algorithms.KadaneResult;

import java.util.Random;

public class TestUtils {
    public static KadaneResult bruteForce(long[] array) {
        if (array.length == 0) {
            return new KadaneResult(0L, -1, -1);
        }
        long bestSum = Long.MIN_VALUE;
        int bestStart = 0;
        int bestEnd = 0;

        for (int i = 0; i < array.length; i++) {
            long sum = 0;
            for (int j = i; j < array.length; j++) {
                sum += array[j];
                if (sum > bestSum) {
                    bestSum = sum;
                    bestStart = i;
                    bestEnd = j;
                }
            }
        }
        return new KadaneResult(bestSum, bestStart, bestEnd);
    }

    public static long[] generateRandomArray(int size) {
        Random rand = new Random(42);
        long[] array = new long[size];
        for (int i = 0; i < size; i++) {
            array[i] = rand.nextInt(2001) - 1000;
        }
        return array;
    }

    public static long[] generateAllPositiveArray(int size) {
        long[] array = new long[size];
        for (int i = 0; i < size; i++) {
            array[i] = i + 1;
        }
        return array;
    }

    public static long[] generateAllNegativeArray(int size) {
        long[] array = new long[size];
        for (int i = 0; i < size; i++) {
            array[i] = -(i + 1);
        }
        return array;
    }

    public static long getArraySum(long[] array) {
        long sum = 0;
        for (long value : array) {
            sum += value;
        }
        return sum;
    }

    public static long[] generateSortedArray(int size) {
        long[] array = new long[size];
        for (int i = 0; i < size; i++) array[i] = i + 1;
        return array;
    }

    public static long[] generateReverseSortedArray(int size) {
        long[] array = new long[size];
        for (int i = 0; i < size; i++) array[i] = size - i;
        return array;
    }
}
