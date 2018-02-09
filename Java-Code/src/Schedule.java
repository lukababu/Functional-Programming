import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Schedule {
    final static boolean DEBUG = Test.DEBUG;

    private String aName;
    private ArrayList<MachineTaskPair> partialAssignments = new ArrayList<MachineTaskPair>();
    private ArrayList<MachineTaskPair> forbiddenMachines = new ArrayList<MachineTaskPair>();
    private ArrayList<TaskTaskPair> tooNearTasks = new ArrayList<TaskTaskPair>();
    private int[][] machinePenalties = new int[8][8];
    private ArrayList<TooNearPenalty> tooNearPenalties = new ArrayList<TooNearPenalty>();
    public ArrayList<Node> terminalCollection = new ArrayList<Node>();

    public Schedule(String pathname) throws IOException {

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

                    boolean[] availableMach = new boolean[]{true, true, true, true, true, true, true, true};
                    boolean[] availableTask = new boolean[]{true, true, true, true, true, true, true, true};
                    while (currentString.length() != 0) {
                        if (DEBUG)
                            System.out.println("Current string:" + currentString);
                        String newString = currentString.substring(1, currentString.length() - 1);
                        if (DEBUG)
                            System.out.println("New string: " + newString);
                        String[] values = newString.split(",");
                        if (DEBUG)
                            System.out.println("Spitted: " + values[0] + " " + values[1]);

                        // Check if there is an invalid machine
                        int machineNum = Integer.valueOf(values[0]);
                        if (machineNum > 8 || machineNum < 1) throw new PartialAssignmentError("" +
                                "partial assignment error: Invalid machine");

                        // Check if there is an invalid task
                        int taskNum = toIntNumber(values[1]);
                        if (taskNum > 8 || taskNum < 1)
                            throw new PartialAssignmentError("" +
                                    "partial assignment error: Invalid task" + taskNum);


                        // Check if there is duplicate machine, and throw an error
                        if (availableMach[Integer.valueOf(values[0])-1])
                            availableMach[Integer.valueOf(values[0])-1] = false;
                        else throw new PartialAssignmentError("" +
                                "partial assignment error: duplicate machine");


                        // Check if there is duplicate task, and throw an error
                        if (availableTask[toIntNumber(values[1])-1])
                            availableTask[toIntNumber(values[1])-1] = false;
                        else {
                            throw new PartialAssignmentError("partial assignment error: duplicate task");
                        }

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
                        TooNearPenalty tnp = new TooNearPenalty(toIntNumber(values[0]), toIntNumber(values[1]), Integer.valueOf(values[2]));
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

    private char toLetter(int input) {
        if (input == 1) return 'A';
        if (input == 2) return 'B';
        if (input == 3) return 'C';
        if (input == 4) return 'D';
        if (input == 5) return 'E';
        if (input == 6) return 'F';
        if (input == 7) return 'G';
        if (input == 8) return 'H';

        return 'Z';
    }

    public String getaName() {
        return aName;
    }

    public ArrayList<MachineTaskPair> getPartialAssignments() {
        return partialAssignments;
    }

    public ArrayList<MachineTaskPair> getForbiddenMachines() {
        return forbiddenMachines;
    }

    public ArrayList<TaskTaskPair> getTooNearTasks() {
        return tooNearTasks;
    }

    public int[][] getMachinePenalties() {
        return machinePenalties;
    }

    public ArrayList<TooNearPenalty> getTooNearPenalties() {
        return tooNearPenalties;
    }

    public ArrayList<Node> getTerminalCollection() {return terminalCollection; }

    class Node {


        final static boolean DEBUG = Test.DEBUG;

        private Node parent;
        private int cost;
        private int level;

        public List<Node> getChildren() {
            return children;
        }

        private List<Node> children = new ArrayList<Node>(); 			//Does this need to have its type changed?
        public List<Integer> currentSet;
        private int task;
        /**
         * The null node, used as the level 0/starting point of the tree
         */
        public Node() {
            System.out.println();
            this.level = 0;
            this.cost = 0;
            currentSet = new ArrayList<Integer>();
            currentSet.add(1);
            currentSet.add(2);
            currentSet.add(3);
            currentSet.add(4);
            currentSet.add(5);
            currentSet.add(6);
            currentSet.add(7);
            currentSet.add(8);
            genChildren();
        }

        /**
         * Constructor for nodes
         * @param parent The node before the current level
         * @param task
         * @param currentSet
         * @param level The current level
         *
         */
        public Node(Node parent, int task, List<Integer> currentSet, int level, int cost) {
            this.parent = parent;
            this.task = task;
            this.currentSet = new ArrayList<Integer>(currentSet);
            this.level = level;
            this.cost = cost;
            if (DEBUG) System.out.println("Machine: " + level + " Task: " + task);
            genChildren();
            if (this.level==8)
                terminalCollection.add(this);
        }

        /*
         * Getter for parent of the given node
         */
        public Node getParent() {
            return parent;
        }
        /*
         * Getter for cost of the given node
         */
        public int getCost() {
            return cost;
        }
        /*
         * Getter for level of the given node
         */
        public int getLevel() {
            return level;
        }
        /*
         *Returns the array up to the point it is called
         */
        public int[] path(int[] path, int level){

            return path;
        }
        public void genChildren(){
            if (level!=0) {
                currentSet.remove((currentSet.indexOf(this.task)));
            }

            //Partial Assign Constraint
            int assignTask = 0;
            for (int i=0; i<partialAssignments.size(); i++) {
                if (level+1==partialAssignments.get(i).getMachine()) {
                    if (DEBUG) System.out.println(i + " " + level);
                    assignTask = partialAssignments.get(i).getTask();
                    break;
                }
            }

            //Forbidden Machines
            int forbiddenTask = 0;
            for (int i=0; i<forbiddenMachines.size(); i++) {
                if (level+1==forbiddenMachines.get(i).getMachine()) {
                    forbiddenTask = forbiddenMachines.get(i).getTask();
                }
            }

            List<Integer> tnTasks = new ArrayList<Integer>();
            for (int i=0; i<tooNearTasks.size(); i++) {
                TaskTaskPair tempTTP = tooNearTasks.get(i);
                if (task==tempTTP.getTask1())
                    tnTasks.add(tempTTP.getTask2());
                else if (task==tempTTP.getTask2())
                    tnTasks.add(tempTTP.getTask1());
                else;
            }

            for (int i=0; i<8-level;i++) {
                if (assignTask != currentSet.get(i) && assignTask!=0) //Partial Assign Constraint
                    continue;

                if (forbiddenTask == currentSet.get(i) && forbiddenTask!=0) //Forbidden Machines
                    continue;

                if (tnTasks.contains(currentSet.get(i))) //Too near Tasks
                    continue;

                int tempPenalty = 0;

                if (level!=0) {
                    TooNearPenalty tempTNP;
                    for (int j=0;j<tooNearPenalties.size();j++) {
                        if ((task == tooNearPenalties.get(j).getTask1()) && (currentSet.get(i) == tooNearPenalties.get(j).getTask2())) {
                            tempPenalty = tooNearPenalties.get(j).getPenalty();
                            break;
                        }

                        else if((task == tooNearPenalties.get(j).getTask2()) && (currentSet.get(i) == tooNearPenalties.get(j).getTask1())) {
                            tempPenalty = tooNearPenalties.get(j).getPenalty();
                            break;
                        }
                        else;
                    }
                }




                if (DEBUG) {
                    System.out.println("Cost: " + cost +
                                        " Temp Penalty " + tempPenalty +
                                        " Current: " + machinePenalties[level][currentSet.get(i)-1]);
                }

                Node aNode = new Node(this, currentSet.get(i), currentSet, level+1, cost+tempPenalty+machinePenalties[level][currentSet.get(i)-1]);

                children.add(aNode);
            }



        }

        public String toString() {
            String output = "";

            output += toLetter(this.task);

            return output;
        }
    }
}
