package algorithms;

public record KadaneResult(long maxSum, int startIndex, int endIndex) {

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
