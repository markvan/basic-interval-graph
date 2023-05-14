package intervalGraph;

import java.util.List;
import java.util.Set;

// build an interval graph from the input file and interrogate it
//see output statements as a quick guide to what is happening
public class Main {
    public static void main(String[] args) {
        // beware filepath running from Intellij, the working directory is the project directory
        //todo check that this is not fragile, improve input filepath specification eg make it interactive
        final String fn = "./input/intervals01.txt";

        System.out.println("Parsing the interval data");
        List<Interval> intervals = new InputParser(fn).parseFile();

        System.out.println("Building the interval graph");
        AdjacencyList intervalGraph = new AdjacencyList(intervals);

        System.out.println("Listing overlapping intervals for each interval");
        Set<String> intervalNames = intervalGraph.getIntervalNames();
        for (String name : intervalNames) {
            printIntervalList(name, intervalGraph);
        }
    }

    // service method for printing overlapping intervals for a given interval
    static private void printIntervalList(String name, AdjacencyList adjList) {
        try { // getoverlappingIntervals will throw an exception if given a non-existent interval name

            System.out.println("Overlapping intervals for interval " + name );

            // get and print any overlapping intervals
            List<Interval> intervals = adjList.getOverlappingtervals(name);

            if (intervals.size() == 0) {
                System.out.println("  no overlapping intervals");
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
