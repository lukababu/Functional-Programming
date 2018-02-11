import java.io.IOException;

public class Driver {
    final static boolean DEBUG = Test.DEBUG;
    private final static String INPUT_FILE = "sample.txt";
    private final static String OUTPUT_FILE = "Output.txt";

    public static void main(String args[]) throws IOException {
        String inputFile;
        String outputFile;

        if (DEBUG) {
            inputFile = INPUT_FILE;
            outputFile = OUTPUT_FILE;
        }
        else {
            try {
                inputFile = args[0];
                outputFile = args[1];
            } catch (Exception e) {
                throw new RunTimeError("Incorrect input " + e, OUTPUT_FILE);
            }
        }

        System.out.println("Working...");
        ScheduleTasks scheduleTasks = new ScheduleTasks(inputFile, outputFile);
        scheduleTasks.optimalSolution();

    }
}

