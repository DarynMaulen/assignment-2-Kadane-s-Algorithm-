package metrics;

public class PerformanceTracker {
    private long comparisons = 0;
    private long arrayAccesses = 0;
    private long assignments = 0;
    private long additions = 0;
    private long timeNs = 0;
    private long startNs = 0;

    public long getComparisons() {
        return comparisons;
    }
    public long getArrayAccesses() {
        return arrayAccesses;
    }
    public long getAssignments() {
        return assignments;
    }
    public long getAdditions() {
        return additions;
    }
    public long getTimeMs() {
        return timeNs/1_000_000;
    }

    public void startTimer(){
        startNs = System.nanoTime();
    }
    public void stopTimer(){
        timeNs = Math.max(0,System.nanoTime()-startNs);
    }

    public synchronized void incrementComparisons(){
        comparisons++;
    }
    public synchronized void incrementArrayAccesses(){
        arrayAccesses++;
    }
    public synchronized void incrementAssignments(){
        incrementAssignments(1);
    }
    public synchronized void incrementAssignments(long n){
        assignments += n;
    }
    public synchronized void incrementAdditions(){
        additions++;
    }

    public void reset(){
        comparisons = arrayAccesses = assignments = additions = timeNs = startNs = 0;
    }

    public String toString(){
        return "PerformanceTracker [comparisons=" + comparisons + ", " +
                "arrayAccess=" + arrayAccesses + ", assignments=" + assignments
                + ", additions=" + additions + ", timeNs=" +
                timeNs + ", startNs=" + startNs + "]";
    }

    public String toCsvLine(String algorithm, String inputType, int n, int trial){
        return String.format("%s,%s,%d,%d,%d,%d,%d,%d,%d",
                algorithm,
                inputType,
                n,
                trial,
                getTimeMs(),
                getComparisons(),
                getArrayAccesses(),
                getAssignments(),
                getAdditions()
        );
    }
}
