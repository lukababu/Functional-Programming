import java.io.IOException;

public class Driver {
    final static boolean DEBUG = Test.DEBUG;
    private final static String INPUT_FILE = "sample.txt";
    private final static String OUTPUT_FILE = "Output.txt";
    private static String inputFile = INPUT_FILE;

    public static void main(String args[]) throws IOException {
        //if (DEBUG) inputFile = "Debug.txt";
        ScheduleTasks scheduleTasks = new ScheduleTasks(inputFile);
        scheduleTasks.optimalSolution();

    }
}

