import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Schedule {
    final static boolean DEBUG = Test.DEBUG;

    private String aName;
    private ArrayList<MachineTaskPair> partialAssignments = new ArrayList<MachineTaskPair>();
    private ArrayList<MachineTaskPair> forbiddenMachines = new ArrayList<MachineTaskPair>();
    private ArrayList<TaskTaskPair> tooNearTasks = new ArrayList<TaskTaskPair>();
    private int[][] machinePenalties = new int[8][8];
    private ArrayList<TooNearPenalty> tooNearPenalties = new ArrayList<TooNearPenalty>();

    public Schedule(String pathname) {

        // Read file and put it into an array
        File file = new File(pathname);
        ArrayList<String> stringBuffer = new ArrayList<String>();
        String line;
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.add(line);
            }
            fileReader.close();
            System.out.println("Contents of file:");
            if (DEBUG)
                for (String aString : stringBuffer)
                    System.out.println(aString);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Process the buffer
        for (int i = 0; i < stringBuffer.size(); ) {
            String currentString = stringBuffer.get(i);
            switch (currentString) {
                case "Name:":
                    if (DEBUG)
                        System.out.println("Inputting name...");
                    i++;
                    currentString = stringBuffer.get(i);

                    if (currentString.length() != 0) {
                        if (DEBUG)
                            System.out.println("name set to: " + currentString);
                        aName = (currentString);
                    } else {
                        System.out.println("Invalid name");
                        return;
                    }

                    i++;
                    break;
                case "forced partial assignment:":
                    if (DEBUG)
                        System.out.println("inputting forced partial assignments...");
                    i++;
                    currentString = stringBuffer.get(i);
                    while (currentString.length() != 0) {
                        if (DEBUG)
                            System.out.println("Current string:" + currentString);
                        String newString = currentString.substring(1, currentString.length() - 1);
                        if (DEBUG)
                            System.out.println("New string: " + newString);
                        String[] values = newString.split(",");
                        if (DEBUG)
                            System.out.println("Splitted: " + values[0] + " " + values[1]);
                        MachineTaskPair mtp = new MachineTaskPair(Integer.valueOf(values[0]), toIntNumber(values[1]));
                        partialAssignments.add(mtp);
                        i++;
                        currentString = stringBuffer.get(i);
                    }
                    break;
                case "forbidden machine:":
                    if (DEBUG)
                        System.out.println("forbidden machines...");
                    i++;
                    currentString = stringBuffer.get(i);
                    while (currentString.length() != 0) {
                        if (DEBUG)
                            System.out.println("Current string:" + currentString);
                        String newString = currentString.substring(1, currentString.length() - 1);
                        if (DEBUG)
                            System.out.println("New string: " + newString);
                        String[] values = newString.split(",");
                        if (DEBUG)
                            System.out.println("Splitted: " + values[0] + " " + values[1]);
                        MachineTaskPair mtp = new MachineTaskPair(Integer.valueOf(values[0]), toIntNumber(values[1]));
                        forbiddenMachines.add(mtp);
                        i++;
                        currentString = stringBuffer.get(i);
                    }
                    break;
                case "too-near tasks:":
                    if (DEBUG)
                        System.out.println("too-near tasks...");
                    i++;
                    currentString = stringBuffer.get(i);
                    while (currentString.length() != 0) {
                        if (DEBUG)
                            System.out.println("Current string:" + currentString);
                        String newString = currentString.substring(1, currentString.length() - 1);
                        if (DEBUG)
                            System.out.println("New string: " + newString);
                        String[] values = newString.split(",");
                        if (DEBUG)
                            System.out.println("Splitted: " + values[0] + " " + values[1]);
                        TaskTaskPair ttp = new TaskTaskPair(toIntNumber(values[0]), toIntNumber(values[1]));
                        tooNearTasks.add(ttp);
                        i++;
                        currentString = stringBuffer.get(i);
                    }
                    break;
                case "machine penalties:":
                    if (DEBUG)
                        System.out.println("machines penalties...");
                    i++;
                    currentString = stringBuffer.get(i);
                    int offset = 0;
                    while (currentString.length() != 0) {
                        if (DEBUG)
                            System.out.println("Offset: " + offset);
                        if (DEBUG)
                            System.out.println("Current string:" + currentString);
                        String[] values = currentString.split(" ");

                        if (values.length == 8) {
                            if (DEBUG)
                                System.out.println("Splitted: " + values[0] + " " + values[1] + " " + values[2] + " " + values[3] + " " + values[4] + " " + values[5] + " " + values[6] + " " + values[7]);
                            int[] numbers = new int[8];
                            for (int j = 0; j < 8; j++) {
                                numbers[j] = Integer.parseInt(values[j]);
                            }
                            machinePenalties[offset] = numbers;
                        } else {
                            System.out.println("Invalid Machine Penalty Size (Horizontal) at: " + offset + 1);
                        }
                        offset++;
                        i++;
                        currentString = stringBuffer.get(i);
                    }
                    if (offset != 8) {
                        System.out.println("Invalid Machine Penalty Size (Vertical)");
                        return;
                    }
                    break;
                case "too-near penalties:":
                    if (DEBUG)
                        System.out.println("too-near penalties...");
                    i++;
                    if (i < stringBuffer.size())
                        currentString = stringBuffer.get(i);
                    else {
                        if (DEBUG)
                            System.out.println("End of the file");
                        break;
                    }
                    while (currentString.length() != 0) {
                        if (DEBUG)
                            System.out.println("Current string:" + currentString);
                        String newString = currentString.substring(1, currentString.length() - 1);
                        if (DEBUG)
                            System.out.println("New string: " + newString);
                        String[] values = newString.split(",");
                        if (DEBUG)
                            System.out.println("Splitted: " + values[0] + " " + values[1] + " " + values[2]);
                        TooNearPenalty tnp = new TooNearPenalty(values[0], values[1], Integer.valueOf(values[2]));
                        tooNearPenalties.add(tnp);
                        i++;
                        if (i < stringBuffer.size())
                            currentString = stringBuffer.get(i);
                        else {
                            if (DEBUG)
                                System.out.println("End of the file");
                            break;
                        }
                    }
                    break;
                default:
                    if (DEBUG)
                        System.out.println("Empty Line");
                    i++;
            }
        }

        //Report
        System.out.println("Report:");
        System.out.println("Name: " + aName);
        System.out.println("Partial Assignments: ");
        for (MachineTaskPair a : partialAssignments) {
            System.out.println("\t" + a.getMachine() + ", " + a.getTask());
        }
        System.out.println("Forbidden Machines: ");
        for (MachineTaskPair a : forbiddenMachines) {
            System.out.println("\t" + a.getMachine() + ", " + a.getTask());
        }
        System.out.println("Too Near Tasks: ");
        for (TaskTaskPair a : tooNearTasks) {
            System.out.println("\t" + a.getTask1() + ", " + a.getTask2());
        }
        System.out.println("Machine Penalties: ");
        for (int[] a : machinePenalties) {
            System.out.println("\t" + a[0] + ", " + a[1]);
        }
        System.out.println("Too Near Penalties: ");
        for (TooNearPenalty a : tooNearPenalties) {
            System.out.println("\t" + a.getTask1() + ", " + a.getTask2() + ", " + a.getPenalty());
        }
    }

    private int toIntNumber(String input) {
        if (input.equals("A")) return 1;
        if (input.equals("B")) return 2;
        if (input.equals("C")) return 3;
        if (input.equals("D")) return 4;
        if (input.equals("E")) return 5;
        if (input.equals("F")) return 6;
        if (input.equals("G")) return 7;
        if (input.equals("H")) return 8;

        return -1;
    }

    public String getaName() {
        return aName;
    }

    public void setaName(String aName) {
        this.aName = aName;
    }

    public ArrayList<MachineTaskPair> getPartialAssignments() {
        return partialAssignments;
    }

    public void setPartialAssignments(ArrayList<MachineTaskPair> partialAssignments) {
        this.partialAssignments = partialAssignments;
    }

    public ArrayList<MachineTaskPair> getForbiddenMachines() {
        return forbiddenMachines;
    }

    public void setForbiddenMachines(ArrayList<MachineTaskPair> forbiddenMachines) {
        this.forbiddenMachines = forbiddenMachines;
    }

    public ArrayList<TaskTaskPair> getTooNearTasks() {
        return tooNearTasks;
    }

    public void setTooNearTasks(ArrayList<TaskTaskPair> tooNearTasks) {
        this.tooNearTasks = tooNearTasks;
    }

    public int[][] getMachinePenalties() {
        return machinePenalties;
    }

    public void setMachinePenalties(int[][] machinePenalties) {
        this.machinePenalties = machinePenalties;
    }

    public ArrayList<TooNearPenalty> getTooNearPenalties() {
        return tooNearPenalties;
    }

    public void setTooNearPenalties(ArrayList<TooNearPenalty> tooNearPenalties) {
        this.tooNearPenalties = tooNearPenalties;
    }
}
