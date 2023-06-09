package IntervalGraph;

import java.util.List;
import java.util.TreeSet;

import static java.lang.System.exit;

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
        TreeSet<String> intervalNames = intervalGraph.getIntervalNames();
        for (String name : intervalNames) {
            printIntervalList(name, intervalGraph);
        }
    }


    /**
     * @param intervalName
     * @param adjList
     */
    static private void printIntervalList(String intervalName, AdjacencyList adjList)  {
        try { // getoverlappingIntervals will throw an exception if given a non-existent interval name

            // get and print any overlapping intervals
            Interval interval = adjList.getIntervalFromName(intervalName);
            List<Interval> intervals = adjList.getOverlappingIntervals(intervalName);

            if (intervals.size() == 0) {
                System.out.println("No overlapping intervals for interval " + interval.toString() );

            }
            else {
                System.out.println("Overlapping intervals for interval " + interval.toString() );
                for (Interval i : intervals) {
                    System.out.println( "\t\t"+ i.toString() );
                }
            }

        }
        catch (Exception e) {
            System.out.println("Main printInterval list called with non-existent interval name "+e);
            exit(1);
        }
    }
}
