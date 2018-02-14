import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static final boolean DEBUG = true;
    public static final String filePath =
            "C:\\Users\\a\\IdeaProjects\\Functional-Programming\\Java-Code\\inputs";
    private List<String> results;


    public Test() {
        List<String> tempResults = new ArrayList<String>();

        File folder = new File(filePath);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                System.out.println("File " + listOfFiles[i].getName());
                tempResults.add(listOfFiles[i].getName());
            }
        }
        setTempResults(tempResults);
    }

    public List<String> getResults() {
        return results;
    }

    public void setTempResults(List<String> results) {
        this.results = results;
    }
}
