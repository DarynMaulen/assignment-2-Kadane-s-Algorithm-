package algorithms;

/*
  Result container for Kadane's algorithm.
  - maxSum: maximum subarray sum.
  - startIndex, endIndex: inclusive indices of the subarray, or -1 if none.
  This is an immutable record and provides simple getters.
*/
public record KadaneResult(long maxSum, int startIndex, int endIndex) {

    // Convenience getter
    public long getMaxSum() {
        return maxSum();
    }

    public int getStartIndex() {
        return startIndex();
    }

    public int getEndIndex() {
        return endIndex();
    }

}
