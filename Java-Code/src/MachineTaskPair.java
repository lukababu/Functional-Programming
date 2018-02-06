public class MachineTaskPair {
    private int machine;
    private int task;

    public MachineTaskPair (int machine, int task) {
        this.machine = machine;
        this.task = task;
    }

    int getMachine() {
        return this.machine;
    }

    int getTask() {
        return this.task;
    }
}
