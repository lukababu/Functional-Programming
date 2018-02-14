import java.io.IOException;
import java.util.List;

public class Driver {
    final static boolean DEBUG = Test.DEBUG;
    private final static String INPUT_FILE = "sample.txt";
    private final static String OUTPUT_FILE = "Output.txt";

    public static void main(String args[]) throws IOException {
        String inputFile;
        String outputFile;

        if (DEBUG || args[0].equals("DEBUG")) {
            inputFile = INPUT_FILE;
            outputFile = OUTPUT_FILE;
            Test test = new Test();
            List<String> inputFiles = test.getResults();
            for (int i = 0; i < inputFiles.size(); i++) {

                while(true){
                    try{
                        // Code you want to execute that can throw an error£
                        ScheduleTasks scheduleTasks = new ScheduleTasks(
                                test.filePath+"\\"+inputFiles.get(i),
                                "outputs\\"+inputFiles.get(i));
                        scheduleTasks.optimalSolution();
                        break;
                    }
                    catch(Exception e){
                        // Exception logic (such as showing a message) or just printing the trace (but this doesn't help a user)
                        break;
                    }
                }
            }
        }
        else {
            try {
                inputFile = args[0];
                outputFile = args[1];
            } catch (Exception e) {
                throw new Output.RunTimeError("Incorrect input " + e, OUTPUT_FILE);
            }
            ScheduleTasks scheduleTasks = new ScheduleTasks(inputFile, outputFile);
            scheduleTasks.optimalSolution();
        }

        System.out.println("Working...");
    }
}

