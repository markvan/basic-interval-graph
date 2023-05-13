package intervalGraph;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        final String fn = "./input/intervals01.txt";
        System.out.println("Building a graph");

        // beware filepath running from Intellij, the working directory is the project directory
        List<Interval> intervals = new InputParser(fn).parseFile();
    }
}
