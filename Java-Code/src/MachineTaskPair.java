public class MachineTaskPair {
    private int machine;
    private String task;

    public MachineTaskPair (int machine, String task) {
        this.machine = machine;
        this.task = task;
    }

    int getMachine() {
        return this.machine;
    }

    String getTask() {
        return this.task;
    }
}
