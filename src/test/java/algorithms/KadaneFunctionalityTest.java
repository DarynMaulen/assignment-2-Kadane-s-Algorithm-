package algorithms;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class KadaneFunctionalityTest {
    @Test
    void nullInputThrowsException(){
        assertThrows(IllegalArgumentException.class,() -> Kadane.run(null));
    }

    @Test
    void emptyArrayReturnsZeroAndInvalidIndices(){
        KadaneResult result = Kadane.run(new long[0]);
        assertEquals(0L,result.getMaxSum());
        assertEquals(-1,result.getStartIndex());
        assertEquals(-1,result.getEndIndex());
    }

    @Test
    void singleElementPositive(){
        KadaneResult result = Kadane.run(new long[]{5});
        assertEquals(5L,result.getMaxSum());
        assertEquals(0,result.getStartIndex());
        assertEquals(0,result.getEndIndex());
    }

    @Test
    void singleElementNegative(){
        KadaneResult result = Kadane.run(new long[]{-5});
        assertEquals(-5L,result.getMaxSum());
        assertEquals(0,result.getStartIndex());
        assertEquals(0,result.getEndIndex());
    }

    @Test
    void classicalExample(){
        KadaneResult result = Kadane.run(new long[]{-2, 1, -3, 4, -1, 2, 1, -5, 4});
        assertEquals(6L, result.getMaxSum());
        assertEquals(3, result.getStartIndex());
        assertEquals(6, result.getEndIndex());
    }

    @Test
    void allNegativeArrayReturnsMaxElement(){
        KadaneResult r = Kadane.run(new long[]{-8, -3, -6, -2, -5, -4} );
        assertEquals(-2L, r.getMaxSum());
        assertEquals(3, r.getStartIndex());
        assertEquals(3, r.getEndIndex());
    }

    @Test
    void randomArrayCompareWithBruteForce(){
        Random rand = new Random(12345);
        for(int t = 0; t < 50; t++){
            long[] array = new long[1 + rand.nextInt(20)];
            for (int i = 0; i < array.length; i++) {
                array[i] = rand.nextInt(21) - 10;
            }
            KadaneResult expected = bruteForce(array);
            KadaneResult result = Kadane.run(array);
            assertEquals(expected,result,"Mismatch for input: " + Arrays.toString(array));
        }
    }

    private KadaneResult bruteForce(long[] array){
        if(array.length == 0){return new KadaneResult(0L,-1,-1);}
        long bestSum = Long.MIN_VALUE;
        int bi = 0, bj = 0;
        for(int i = 0; i < array.length; i++){
            long sum = 0;
            for(int j = i; j < array.length; j++){
                sum += array[j];
                if(sum > bestSum){
                    bestSum = sum;
                    bi = i;
                    bj = j;
                }
            }
        }
        return new KadaneResult(bestSum,bi,bj);
    }
}