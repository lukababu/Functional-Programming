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

    static String getOutFile() {
        return outFile;
    }

    public void setOutFile(String outFile) {
        Output.outFile = outFile;
    }
}

class PartialAssignmentError extends RuntimeException {

    PartialAssignmentError(String message) throws IOException {
        super(message);

        Output output = new Output("partial assignment error", Output.getOutFile());
    }
}

class RunTimeError extends RuntimeException {

    RunTimeError(String message, String outputFile) throws  IOException {
        super(message);

        Output output = new Output("runtime error "+message,
                outputFile);
    }
}