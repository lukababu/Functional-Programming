import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Output {
    private final static String FILE_OUTPUT = "Output.txt";

    public static void write(String str) throws IOException {

        Path path = Paths.get(FILE_OUTPUT);
        byte[] strToBytes = str.getBytes();

        Files.write(path, strToBytes);
    }

}

class PartialAssignmentError extends RuntimeException {

    public PartialAssignmentError(String message) throws IOException {
        super(message);

        Output.write("partial assignment error");
    }
}