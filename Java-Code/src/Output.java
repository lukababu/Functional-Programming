import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Output {
    private static String outFile;

    public Output(String str, String outputFile) throws IOException {
        setOutFile(outputFile);
        Path path = Paths.get(getOutFile());
        byte[] strToBytes = str.getBytes();

        Files.write(path, strToBytes);
    }

    public Output(String one,
                  String two,
                  String three,
                  String four,
                  String five,
                  String six,
                  String seven,
                  String eight,
                  int quality,
                  String outputFile) throws IOException {

        setOutFile(outputFile);
        Path path = Paths.get(getOutFile());
        String solution = "Solution ";
        solution += one + " ";
        solution += two + " ";
        solution += three + " ";
        solution += four + " ";
        solution += five + " ";
        solution += six + " ";
        solution += seven + " ";
        solution += eight + ";";
        solution += " Quality: " + quality;

        byte[] strToBytes = solution.getBytes();

        Files.write(path, strToBytes);
    }

    static String getOutFile() {
        return outFile;
    }

    public void setOutFile(String outFile) {
        Output.outFile = outFile;
    }


    static class PartialAssignmentError extends RuntimeException {

        PartialAssignmentError(String message, String outputFile) throws IOException {
            super(message);

            Output output = new Output("partial assignment error", outputFile);
        }
    }

    static class ParsingInputFile extends RuntimeException {

        ParsingInputFile(String message, String outputFile) throws IOException {
            super(message);

            Output output = new Output("Error while parsing input file", outputFile);
        }
    }

    static class NoValidSolution extends RuntimeException {

        NoValidSolution(String message, String outputFile) throws IOException {
            super(message);

            Output output = new Output("No valid solution possible!" + message,
                    outputFile);
        }
    }

    static class InvalidMachineTask extends RuntimeException {

        InvalidMachineTask(String message, String outputFile) throws IOException {
            super(message);

            Output output = new Output("invalid machine/task" + message,
                    outputFile);
        }
    }

    static class MachinePenaltyError extends RuntimeException {

        MachinePenaltyError(String message, String outputFile) throws IOException {
            super(message);

            Output output = new Output("machine penalty error" + message,
                    outputFile);
        }
    }

    static class InvalidPenalty extends RuntimeException {

        InvalidPenalty(String message, String outputFile) throws IOException {
            super(message);

            Output output = new Output("invalid penalty" + message,
                    outputFile);
        }
    }

    static class InvalidTask extends RuntimeException {

        InvalidTask(String message, String outputFile) throws IOException {
            super(message);

            Output output = new Output("invalid task" + message,
                    outputFile);
        }
    }

    static class RunTimeError extends RuntimeException {

        RunTimeError(String message, String outputFile) throws IOException {
            super(message);

            Output output = new Output(message, outputFile);
        }
    }

}