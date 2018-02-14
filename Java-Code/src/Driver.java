import java.io.IOException;
import java.util.List;

public class Driver {
    public static void main(String args[]) throws IOException {
        String inputFile;
        String outputFile;

        if (Test.DEBUG || args[0].equals("DEBUG")) {
            Test test = new Test();
            List<String> inputFiles = test.getResults();

            // Calculate and output solutions
            for (int i = 0; i < inputFiles.size(); i++) {
                while(true){
                    try{
                        ScheduleTasks scheduleTasks = new ScheduleTasks(
                                Test.inputFilePath +inputFiles.get(i),
                                Test.outputFilePath +inputFiles.get(i)
                        );
                        scheduleTasks.optimalSolution();
                        break;
                    }
                    catch(Exception e){
                        break;
                    }
                }
            }

            // Check Solution correctness
        }
        else {
            try {
                inputFile = args[0];
                outputFile = args[1];
            } catch (Exception e) {
                throw new Output.RunTimeError("Incorrect input " + e, outputFile);
            }
            ScheduleTasks scheduleTasks = new ScheduleTasks(inputFile, outputFile);
            scheduleTasks.optimalSolution();
        }

        System.out.println("Working...");
    }
}

