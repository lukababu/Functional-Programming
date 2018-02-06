public class TooNearPenalty {
    private int task1;
    private int task2;
    private int penalty;

    public TooNearPenalty (int task1, int task2, int penalty) {
        this.task1 = task1;
        this.task2 = task2;
        this.penalty = penalty;
    }

    int getTask1() {
        return this.task1;
    }

    int getTask2() {
        return this.task2;
    }

    int getPenalty() {
        return this.penalty;
    }
}