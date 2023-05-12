package intervalGraph;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        // Press Opt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        System.out.println("Building a graph");

        // beware running from Intellij, the working directory is the project directory
        Interval[] intervals = new InputParser("./input/intervals01.txt").parseFile();
    }
}
