import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class ScheduleTasks extends Schedule {
    final static boolean DEBUG = Test.DEBUG;

    public ScheduleTasks(String pathname) throws IOException {
        super(pathname);
    }

    public void optimalSolution () {
        Node aNode = new Node();
        super.getaName();
        int lowCost = 1000000000;
        for (int i = 0; i< super.terminalCollection.size(); i++) {
            System.out.println(super.terminalCollection.get(i).toString());
            System.out.println(super.terminalCollection.get(i).getParent());
            System.out.println(super.terminalCollection.get(i).getParent().getParent());
            System.out.println(super.terminalCollection.get(i).getParent().getParent().getParent());
            int tempLowCost = super.terminalCollection.get(i).getCost();
            if (lowCost>tempLowCost) lowCost = tempLowCost;
            System.out.println(super.terminalCollection.get(i).getCost());
            System.out.println(i + " | " + lowCost);


            System.out.println("New Machine");
        }





    }


}

