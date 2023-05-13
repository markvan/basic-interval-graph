package intervalGraph;

import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        // beware filepath running from Intellij, the working directory is the project directory
        final String fn = "./input/intervals01.txt";

        System.out.println("Parsing the interval data");
        List<Interval> intervals = new InputParser(fn).parseFile();

        System.out.println("Building the interval graph");
        AdjacencyList adjList = new AdjacencyList(intervals);

        System.out.println("Listing intersecting intervals for each interval");
        Set<String> intervalNames = adjList.getIntervalNames();
        for (String name : intervalNames) {
            printIntervalList(name, adjList);
        }
    }

    static private void printIntervalList(String name, AdjacencyList adjList) {
        try {
            List<Interval> intervals = adjList.getIntersectingIntervals(name);
            System.out.println("Intersecting intervals for interval " + name );
            if (intervals.size() == 0) {
                System.out.println("  no intersecting intervals");
            }
            else {
                for (Interval i : intervals) {
                    System.out.println("  interval [" + i.getName() + ", " + i.getStart() + ", " + i.getEnd() + "]");
                }
            }

        }
        catch (Exception e) {
            System.out.println(e);
        }

    }
}
