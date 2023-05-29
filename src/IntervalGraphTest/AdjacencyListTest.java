package IntervalGraphTest;

import IntervalGraph.AdjacencyList;
import IntervalGraph.InputParser;
import IntervalGraph.Interval;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static IntervalGraph.AdjacencyList.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class AdjacencyListTest {

    //handy list of intervals from the parsed file, set during setup
    static ArrayList<Interval> intervalsFromFile;
    // SUT, created during setup
    static AdjacencyList adjList;

    // create a file containing the input the tests need
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        // WIKIPEDIA EXAMPLE    https://en.wikipedia.org/wiki/Interval_graph
        // with an added interval 'H' that is intersects no other interval
        try {
            FileWriter myWriter = new FileWriter("InputParserTestData.txt");

            myWriter.write("A 1 6\n");
            myWriter.write("B 2 4\n");
            myWriter.write("C 3 11\n");
            myWriter.write("D 5 10\n");
            myWriter.write("E 7 8\n");
            myWriter.write("F 9 13\n");
            myWriter.write("G 12 14\n");
            myWriter.write("H 15 16\n");

            myWriter.close();
            // System.out.println("Successfully set up test data by creating 'InputParserTestData.txt'");
            // get the list of intervals by parsing the test input file
            intervalsFromFile = new InputParser("InputParserTestData.txt").parseFile();
            // create the SUT
            adjList = new AdjacencyList(intervalsFromFile);
        } catch (IOException e) {
            System.out.println("IOException thrown trying to set up 'InputParserTestData.txt'");
            e.printStackTrace();
        }
    }

    // just delete the test input file on teardown
    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        File file = new File("InputParserTestData.txt");
        if ( ! file.delete()) {
            System.out.println("Failed to delete InputParserTestData.txt.");
        }
    }

    // test that one can retrieve all interval (aka node) names from interval graph / adjacency list
    @Test
    void getIntervalNamesTest() {

        // get the expected interval names from SUT
        TreeSet<String> intervalNames = adjList.getIntervalNames();

        // check we have the right number of names
        //todo - maybe - to make this stricter count the number of lines in the input file
        assertEquals(intervalNames.size(), intervalsFromFile.size());

        // get the expected names, note intervalsFromFile is the result from the file parser method call
        //todo - maybe - to make this stricter get the names from the the input file
        Collection<String> names = new ArrayList<>();
        for (Interval i : intervalsFromFile) {
            names.add(i.getName());
        }

        // intervalNames comes from the SUT, names from the parser
        assertTrue(intervalNames.containsAll(names));

    }

    //todo test getIntervalFromName, getIntervals


    private ArrayList<Interval> selectIntervals (String... intervalNames) {
        ArrayList<Interval> retList = new ArrayList<>();

        for (String name : intervalNames) {
            for (Interval intvl : adjList.getIntervals()) {
                if ( name.equals(intvl.getName())) {
                    retList.add(intvl);
                }
            }
        }
        return retList;
    }

    private ArrayList<Interval> rejectIntervals (String... intervalNames) {
        ArrayList<Interval> retList = new ArrayList<>(intervalsFromFile);

        for (String name : intervalNames) {
            for (Interval intvl : intervalsFromFile) {
                if ( name.equals(intvl.getName())) {
                    retList.remove(intvl);
                }
            }
        }
        return retList;
    }

    // has assertions without being a test, now sure about that style-wise
    // args are the names of intervals in the interval graph
    // check the first arg only appears in overlapping lists for the remaining args,
    // and not in the overlapping lists for the first arg and the unmentioned args
    //todo write test for this
    private void overlapAppropriate (String testIntervalName, String... overlappingIntervalNames) throws Exception {
        // get the intervals we expect will overlap the test interval
        List<Interval> expectedIntervals = selectIntervals(overlappingIntervalNames);
        //check the actual list from the SUT is the same as the expected intervals
        assertEquals(adjList.getOverlappingIntervals(testIntervalName), expectedIntervals);
        //check testIntervalName is not in the lists for any interval that is not in the overlappingIntervalNames
        // includes it should not be in its own list
        List<Interval> notExpectedInIntervals = rejectIntervals(overlappingIntervalNames);
        Interval testInterval = adjList.getIntervalFromName(testIntervalName);
        notExpectedInIntervals.add(testInterval);
        for (Interval intvl : notExpectedInIntervals) {
            assertFalse(adjList.getOverlappingIntervals(intvl.getName()).contains(testInterval) );
        }
    }

    @Test
    void aTest () {
        adjList.getIntervalNames();
        int i=1;
    }


    // test all intervals to see if they overlap appropriately
    @Test
    void getOverlappingIntervalsTest() {
        try {
            // first arg is the interval being considered, remaining args are the only overlapping intervals
            overlapAppropriate("A", "B", "C", "D");
            overlapAppropriate("B", "A", "C");
            overlapAppropriate("C", "A", "B", "D", "E", "F");
            overlapAppropriate("D", "A", "C", "E", "F");
            overlapAppropriate("E", "C", "D");
            overlapAppropriate("F", "C", "D", "G");
            overlapAppropriate("G", "F");
            overlapAppropriate("H" );

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // test that a new interval non-overlapping interval can be added successfully
    @Test
    void addNonOverlappingIntervalTest () {
        // create a non-overlapping interval and add it
        Interval nonOverlapInterval = new Interval("X", 100, 101);
        adjList.addInterval(nonOverlapInterval);
        try {
            // first arg is the interval being considered, remaining args are the only overlapping intervals
            overlapAppropriate("A", "B", "C", "D");
            overlapAppropriate("B", "A", "C");
            overlapAppropriate("C", "A", "B", "D", "E", "F");
            overlapAppropriate("D", "A", "C", "E", "F");
            overlapAppropriate("E", "C", "D");
            overlapAppropriate("F", "C", "D", "G");
            overlapAppropriate("G", "F");
            overlapAppropriate("H");
            overlapAppropriate("X");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


        // test that a new interval non-overlapping interval can be added successfully
        @Test
        void addOverlappingIntervalTest () {
            // create a non-overlapping interval and add it
            Interval nonOverlapInterval = new Interval("Y", 3, 4);
            adjList.addInterval(nonOverlapInterval);
            try {
                // first arg is the interval being considered, remaining args are the only overlapping intervals
                overlapAppropriate("A", "B", "C", "D", "Y");
                overlapAppropriate("B", "A", "C", "Y");
                overlapAppropriate("C", "A", "B", "D", "E", "F", "Y");
                overlapAppropriate("D", "A", "C", "E", "F");
                overlapAppropriate("E", "C", "D");
                overlapAppropriate("F", "C", "D", "G");
                overlapAppropriate("G", "F");
                overlapAppropriate("H" );
                overlapAppropriate("Y", "A", "B", "C");

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            int i = 1;

        }

        @Test
        void addIntervalsStartingWithEmptyGraphTest () {
            adjList = new AdjacencyList(null);
            try {
                adjList.addInterval(new Interval("A", 3, 10));
                overlapAppropriate("A");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    @Test
    void intervalsIntersectTest() {
        // non overlapping
        Interval i1 = new Interval("A", 1, 2);
        Interval i2 = new Interval("A", 4, 6);
        assertFalse(intervalsOverlap(i1, i2));
        assertFalse(intervalsOverlap(i2, i1));
        i1 = new Interval("A", 2, 3);
        i2 = new Interval("A", 3, 6);
        assertFalse(intervalsOverlap(i1, i2));
        assertFalse(intervalsOverlap(i2, i1));

        //overlapping
        i1 = new Interval("A", 2, 5);
        i2 = new Interval("A", 4, 6);
        assertTrue(intervalsOverlap(i1, i2));
        assertTrue(intervalsOverlap(i2, i1));
        i1 = new Interval("A", 3, 4);
        i2 = new Interval("A", 2, 6);
        assertTrue(intervalsOverlap(i1, i2));
        assertTrue(intervalsOverlap(i2, i1));
        i1 = new Interval("A", 4, 6);
        i2 = new Interval("A", 1, 5);
        assertTrue(intervalsOverlap(i1, i2));
        assertTrue(intervalsOverlap(i2, i1));
        i1 = new Interval("A", 5, 6);
        i2 = new Interval("A", 1, 5);

        // non overlapping
        assertFalse(intervalsOverlap(i1, i2));
        assertFalse(intervalsOverlap(i2, i1));
        i1 = new Interval("A", 6, 7);
        i2 = new Interval("A", 1, 5);
        assertFalse(intervalsOverlap(i1, i2));
        assertFalse(intervalsOverlap(i2, i1));
    }
}