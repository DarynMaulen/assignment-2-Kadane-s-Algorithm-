package metrics;

/*
  Simple performance tracker.
  Counters:
    - comparisons
    - arrayAccesses
    - assignments (can increment by n)
    - additions
  Timing:
    - startTimer() / stopTimer() record elapsed time (stored in ns, exposed as ms).
  Methods that change counters are synchronized for basic thread-safety.
  Use reset() before a new run if reusing the same tracker.
*/
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

    // timer control
    public void startTimer(){
        startNs = System.nanoTime();
    }
    public void stopTimer(){
        timeNs = Math.max(0,System.nanoTime()-startNs);
    }

    // counter increments (synchronized)
    public synchronized void incrementComparisons(){
        incrementComparisons(1);
    }
    public synchronized void incrementComparisons(long n){
        comparisons += n;
    }
    public synchronized void incrementArrayAccesses(){
        incrementArrayAccesses(1);
    }
    public synchronized void incrementArrayAccesses(long n){
        arrayAccesses += n;
    }
    public synchronized void incrementAssignments(){
        incrementAssignments(1);
    }
    public synchronized void incrementAssignments(long n){
        assignments += n;
    }
    public synchronized void incrementAdditions(){
        incrementAdditions(1);
    }
    public synchronized void incrementAdditions(long n){
        additions += n;
    }

    // reset all counters and timers
    public void reset(){
        comparisons = arrayAccesses = assignments = additions = timeNs = startNs = 0;
    }

    // string helpers
    public String toString(){
        return "PerformanceTracker [comparisons=" + comparisons + ", " +
                "arrayAccess=" + arrayAccesses + ", assignments=" + assignments
                + ", additions=" + additions + ", timeNs=" +
                timeNs + ", startNs=" + startNs + "]";
    }

    // convenience CSV row
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
