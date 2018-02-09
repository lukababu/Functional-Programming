import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class ScheduleTasks extends Schedule {
    final static boolean DEBUG = Test.DEBUG;
    private static final String FILENAME = "Output.txt";


    public ScheduleTasks(String pathname) throws IOException {
        super(pathname);
    }

    public void optimalSolution () throws IOException {
        Node aNode = new Node();
        super.getaName();
        int lowCost = 1000000000;
        int lowi = 0;
        for (int i = 0; i< super.terminalCollection.size(); i++) {
            System.out.println(super.terminalCollection.get(i).toString());
            System.out.println(super.terminalCollection.get(i).getParent());
            System.out.println(super.terminalCollection.get(i).getParent().getParent());
            System.out.println(super.terminalCollection.get(i).getParent().getParent().getParent());
            int tempLowCost = super.terminalCollection.get(i).getCost();
            if (lowCost>tempLowCost) {
                lowCost = tempLowCost;
                lowi = i;
            }

            System.out.println(super.terminalCollection.get(i).getCost());
            System.out.println(i + " | " + lowCost);



            System.out.println("New Machine");

            String solution = "Solution " +
                    super.getTerminalCollection().get(lowi).getParent().getParent().getParent().getParent().getParent().getParent().getParent() + "\u2081 " +
                    super.getTerminalCollection().get(lowi).getParent().getParent().getParent().getParent().getParent().getParent() + "\u2082 " +
                    super.getTerminalCollection().get(lowi).getParent().getParent().getParent().getParent().getParent() + "\u2083 " +
                    super.getTerminalCollection().get(lowi).getParent().getParent().getParent().getParent() + "\u2084 " +
                    super.getTerminalCollection().get(lowi).getParent().getParent().getParent() + "\u2085 " +
                    super.getTerminalCollection().get(lowi).getParent().getParent()+ "\u2086 " +
                    super.getTerminalCollection().get(lowi).getParent() + "\u2087 " +
                    super.getTerminalCollection().get(lowi) + "\u2088 " +
                    "Quality: " + super.getTerminalCollection().get(lowi).getCost();
            Output.write(solution);
        }

    }


}