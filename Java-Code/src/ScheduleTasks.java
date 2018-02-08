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
        System.out.println(super.terminalCollection.get(0).getCost());
    }


}

