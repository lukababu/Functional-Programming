import java.io.IOException;

class ScheduleTasks extends Schedule {
    private String outputFile;

    ScheduleTasks(String inputFile, String outputFile) throws IOException {
        super(inputFile, outputFile);
        setOutputFile(outputFile);
    }

    void optimalSolution() throws IOException {
        Node aNode = new Node();
        super.getaName();
        int lowCost = 1000000000;
        int lowi = 0;
        for (int i = 0; i< super.terminalCollection.size(); i++) {
            int tempLowCost;
            if (Test.DEBUG) {
                System.out.println(super.terminalCollection.get(i).toString());
                System.out.println(super.terminalCollection.get(i).getParent());
                System.out.println(super.terminalCollection.get(i).getParent().getParent());
                System.out.println(super.terminalCollection.get(i).getParent().getParent().getParent());
                tempLowCost = super.terminalCollection.get(i).getCost();
            }
            else {
                super.terminalCollection.get(i).toString();
                super.terminalCollection.get(i).getParent();
                super.terminalCollection.get(i).getParent().getParent();
                super.terminalCollection.get(i).getParent().getParent().getParent();
                tempLowCost = super.terminalCollection.get(i).getCost();
            }

            if (lowCost>tempLowCost) {
                lowCost = tempLowCost;
                lowi = i;
            }

            if (Test.DEBUG) {
                System.out.println(super.terminalCollection.get(i).getCost());
                System.out.println(i + " | " + lowCost);
            }
            else super.terminalCollection.get(i).getCost();

            if(Test.DEBUG) {
                System.out.println("New Machine");
            }

            String solution = "Solution " +
                    super.getTerminalCollection().get(lowi).getParent().getParent().getParent().getParent().getParent().getParent().getParent() + "\u2081 " +
                    super.getTerminalCollection().get(lowi).getParent().getParent().getParent().getParent().getParent().getParent() + "\u2082 " +
                    super.getTerminalCollection().get(lowi).getParent().getParent().getParent().getParent().getParent() + "\u2083 " +
                    super.getTerminalCollection().get(lowi).getParent().getParent().getParent().getParent() + "\u2084 " +
                    super.getTerminalCollection().get(lowi).getParent().getParent().getParent() + "\u2085 " +
                    super.getTerminalCollection().get(lowi).getParent().getParent()+ "\u2086 " +
                    super.getTerminalCollection().get(lowi).getParent() + "\u2087 " +
                    super.getTerminalCollection().get(lowi) + "\u2088 " +
                    "Quality: " + super.getTerminalCollection().get(lowi).getCost();

            Output output = new Output(solution, getOutputFile());
        }
        if (terminalCollection.size() == 0) {
            throw new Output.NoSolution("", outputFile);
        }
    }

    private String getOutputFile() {
        return outputFile;
    }

    private void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }

}