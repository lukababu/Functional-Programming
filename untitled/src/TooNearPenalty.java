public class TooNearPenalty {
    private String task1;
    private String task2;
    private int penalty;

    public TooNearPenalty (String task1, String task2, int penalty) {
        this.task1 = task1;
        this.task2 = task2;
        this.penalty = penalty;
    }

    String getTask1() {
        return this.task1;
    }

    String getTask2() {
        return this.task2;
    }

    int getPenalty() {
        return this.penalty;
    }
}