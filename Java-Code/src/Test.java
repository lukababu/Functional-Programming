import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Test {
    public static final boolean DEBUG = false;
    public static final String inputFilePath =
            "Java-Code\\inputs\\";
    public static final String outputFilePath =
            "Java-Code\\outputs\\";
    public static final String correctResults =
            "Java-Code\\correct.txt";
    private List<String> results;


    public Test() {
        List<String> tempResults = new ArrayList<String>();

        File folder = new File(inputFilePath);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                System.out.println("File " + listOfFiles[i].getName());
                tempResults.add(listOfFiles[i].getName());
            }
        }
        setTempResults(tempResults);
    }

    public void checkResults() throws IOException {
        List<String> correctResultsTable = new ArrayList<String>();

        File folder = new File(outputFilePath);
        File[] listOfFiles = folder.listFiles();

        BufferedReader br = new BufferedReader(new FileReader(correctResults));

        System.out.println("\n\n===============================\n" +
                "Running Solution Checker\n" +
                "===============================\n");
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            correctResultsTable.add(line);

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
                correctResultsTable.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            br.close();
        }

        int solutionNumbers = getResults().size()-1;
        do {
            File f = new File(outputFilePath+getResults().get(solutionNumbers));
            if(f.exists() && !f.isDirectory()) {
                BufferedReader solutionCheck = new BufferedReader(
                        new FileReader(outputFilePath+getResults().get(solutionNumbers)));
                try {
                    String line = solutionCheck.readLine();

                    for (int i = 0; i < correctResultsTable.size()-1; i+=2) {
                        if (correctResultsTable.get(i).equals(getResults().get(solutionNumbers))) {
                            if (line.equals(correctResultsTable.get(i+1))) {
                                System.out.println(getResults().get(solutionNumbers) + " Correct!");
                            }
                            else {
                                System.out.println(getResults().get(solutionNumbers) + " Incorrect!");
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    br.close();
                }
            }
            else {
                System.out.println(outputFilePath+getResults().get(solutionNumbers) + " DOES NOT EXIST!");
            }

            solutionNumbers--;
        }while (solutionNumbers >= 0);

        for(File file: Objects.requireNonNull(new File(outputFilePath).listFiles())) {
            if (!file.isDirectory())
                file.delete();
        }

    }

    public List<String> getResults() {
        return results;
    }

    public void setTempResults(List<String> results) {
        this.results = results;
    }
}
