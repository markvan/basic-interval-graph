package IntervalGraphTest;

import IntervalGraph.AdjacencyList;
import IntervalGraph.InputParser;
import IntervalGraph.Interval;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static IntervalGraph.AdjacencyList.intervalsOverlap;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test suite for the AdjacencyList class
 */
//todo make sure all assertEquals args are the right way round - expected, actual - to help with failure output
class AdjacencyListTest {

    final boolean debugMessages = false;

    //handy list of intervals from the parsed file, set during setup
    static ArrayList<Interval> intervalsFromFile;
    // SUT, created during setup
    static AdjacencyList adjList;

    /**
     * On setup -- create a file containing the input the tests need<br>
     * The data is the WIKIPEDIA EXAMPLE    https://en.wikipedia.org/wiki/Interval_graph <br>
     * with an added interval 'H' that is intersects no other interval<br>
     * This is also shown diagrammatically in the repo README
     */
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
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
            if (debugMessages) {
                System.out.println("Successfully set up test data by creating 'InputParserTestData.txt'");
            }
            // get the list of intervals by parsing the test input file
            intervalsFromFile = new InputParser("InputParserTestData.txt").parseFile();
            // create the SUT
            adjList = new AdjacencyList(intervalsFromFile);
        } catch (IOException e) {
            System.err.println("IOException thrown trying to set up 'InputParserTestData.txt'");
            e.printStackTrace();
        }
    }

    /**
     * Delete the test input file on teardown
     */
    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        File file = new File("InputParserTestData.txt");
        if ( ! file.delete()) {
            System.err.println("Failed to delete InputParserTestData.txt.");
        }
    }

    /**
     * Test we can determine if an interval name is in the graph or not
     */
    @Test
    void intervalNameIsNotDuplicateTest() {
        Interval uniquelyNamedInterval = new Interval("unique name", 0, 1);
        Interval duplicateNamedInterval = new Interval("A", 0, 1);
        assertTrue(adjList.intervalNameIsNotDuplicate(uniquelyNamedInterval));
        assertFalse(adjList.intervalNameIsNotDuplicate(duplicateNamedInterval));
    }


    /**
     *  Test that one can retrieve all interval (aka node) names from interval graph / adjacency list
     *  AFTER SETUP
     */
    @Test
    void getSetupIntervalNamesTest() {
        // set up the list of names we expect from the input parser output
        Collection<String> expectedIntervalNames = new ArrayList<>();
        for (Interval i : intervalsFromFile) {
            expectedIntervalNames.add(i.getName());
        }

        // get the actual interval names from SUT
        TreeSet<String> actualIntervalNames = adjList.getIntervalNames();

        // check we have the right number of interval names
        assertEquals(actualIntervalNames.size(), intervalsFromFile.size());
        // see if the SUT is supplying the interval names we expect
        assertTrue(actualIntervalNames.containsAll(expectedIntervalNames) &&
                    expectedIntervalNames.containsAll(actualIntervalNames));
    }

    /**
     * Check can make first steps in populating an empty graph by adding three intervals to that empty graph
     */
    @Test
    void addIntervalsToEmptyGraphTest() {
        adjList = new AdjacencyList();
        Interval aa = new Interval("AA", 3, 4);
        adjList.addInterval(aa);
        assertTrue(adjList.size() == 1);
        Interval bb = new Interval("BB", 3, 8);
        adjList.addInterval(bb);
        Interval cc = new Interval("CC", 30, 38);
        adjList.addInterval(cc);
        assertTrue(adjList.size() == 3);
        List<Interval> resultList = new ArrayList<>();
        List<Interval> overlappingIntervalList= adjList.getOverlappingIntervals("AA");
        assertTrue(overlappingIntervalList.size()==1);
        assertTrue( overlappingIntervalList.contains(bb));

        overlappingIntervalList= adjList.getOverlappingIntervals("BB");
        assertTrue(overlappingIntervalList.size()==1);
        assertTrue( overlappingIntervalList.contains(aa));

        overlappingIntervalList= adjList.getOverlappingIntervals("CC");
        assertTrue(overlappingIntervalList.size()==0);

        assertTrue(adjList.isConsistent());

    }

    /**
     * Test removal of intervals from the graph
     */
    @Test
    void RemoveIntervalsFromGraphTest() {
        // ** first ** check that a non-existent interval name causes a null return
        assertTrue(adjList.removeInterval("no exist name")==null);

        // ** second ** remove single-overlapping G and check its gone from F, leaving A, B, C, D, E and H unchanged
        // ** each step ** also check the number of intervals in the graph and the graph consistency
        Interval toRemove = adjList.getIntervalFromName("G");
        int oldCount = adjList.size();
        assertTrue(adjList.removeInterval("G")==toRemove);
        assertTrue (adjList.size()==(oldCount-1));
        assertTrue(adjList.isConsistent());
        //belt and braces, but want to see isConsistent and overlap appropriate working together to check isConsistent
        try {
            // first arg is the interval being considered, remaining args are the only overlapping intervals
            overlapAppropriate("A", "B", "C", "D");
            overlapAppropriate("B", "A", "C");
            overlapAppropriate("C", "A", "B", "D", "E", "F");
            overlapAppropriate("D", "A", "C", "E", "F");
            overlapAppropriate("E", "C", "D");
            overlapAppropriate("F", "C", "D");
            overlapAppropriate("H" );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // ** third ** remove twice-overlapping interval E and check its gone from C and D, leaving A, B, F and H unchanged
        toRemove = adjList.getIntervalFromName("E");
        oldCount = adjList.size();
        assertTrue(adjList.removeInterval("E")==toRemove);
        assertTrue (adjList.size()==(oldCount-1));
        assertTrue(adjList.isConsistent());
        //belt and braces, but want to see isConsistent and overlap appropriate working together to check isConsistent
        try {
            // first arg is the interval being considered, remaining args are the only overlapping intervals
            overlapAppropriate("A", "B", "C", "D");
            overlapAppropriate("B", "A", "C");
            overlapAppropriate("C", "A", "B", "D", "F");
            overlapAppropriate("D", "A", "C", "F");
            overlapAppropriate("F", "C", "D");
            overlapAppropriate("H" );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // ** fourth ** remove thrice overlapping A and check its gone from B, C and D, leaving F and H unchanged
        toRemove = adjList.getIntervalFromName("A");
        oldCount = adjList.size();
        assertTrue(adjList.removeInterval("A")==toRemove);
        assertTrue (adjList.size()==(oldCount-1));
        assertTrue(adjList.isConsistent());
        //belt and braces, but want to see isConsistent and overlap appropriate working together to check isConsistent
        try {
            // first arg is the interval being considered, remaining args are the only overlapping intervals
            overlapAppropriate("B", "C");
            overlapAppropriate("C", "B", "D", "F");
            overlapAppropriate("D", "C", "F");
            overlapAppropriate("F", "C", "D");
            overlapAppropriate("H" );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    // ** fifth ** remove non-overlapping H leaving B, C, D and F unchanged
    toRemove = adjList.getIntervalFromName("H");
    oldCount = adjList.size();
    assertTrue(adjList.removeInterval("H")==toRemove);
    assertTrue (adjList.size()==(oldCount-1));
    assertTrue(adjList.isConsistent());
    //belt and braces, but want to see isConsistent and overlap appropriate working together to check isConsistent
        try {
        // first arg is the interval being considered, remaining args are the only overlapping intervals
        overlapAppropriate("B", "C");
        overlapAppropriate("C", "B", "D", "F");
        overlapAppropriate("D", "C", "F");
        overlapAppropriate("F", "C", "D");
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}

    /**
     * Tests to see if a graph is internally consistent as reveled by the isConsistent method in AdjacencyList--
     * that has not been tested to show failure with an inconsistent list,
     * but it has been manually examined in depth usi9ng a debugger, and elsewhere in the tests
     * consistent testing has been paired with use of overlapAppropriate; the use of these together
     * adds to feelings of assurance that isConsistent works appropriately.
     */
    //todo use the debugger or programatically introduce an inconsistency in an AdjacencyList via a one-off method later commented out
    @Test
    void isConsistentTest() {
        assertTrue(adjList.isConsistent());
    }

    /**
     * Test we can add an overlapping interval and a non-overlapping interval to a non-empty graph
     * //todo make DRY
     */
    @Test
    void addIntervalToNonEmptyGraphTest() {
        Interval newInte1 = new Interval("new interval 1", 3, 4);

        // set up the list of names we expect from the input parser output
        ArrayList<String> expectedNames = new ArrayList<>();
        intervalsFromFile.forEach(inte -> expectedNames.add(inte.getName()));
        final int oldCount = expectedNames.size();

        // add an interval and adjust the expected names and set the number of names
        adjList.addInterval(newInte1);
        expectedNames.add(newInte1.getName());
        // check we have the right number of intervals
        assertEquals(oldCount+1, adjList.size());
        // get the actual interval names from SUT
        TreeSet<String> actualIntervalNames = adjList.getIntervalNames();
        // see if the SUT is supplying the interval names we expect
        assertTrue(actualIntervalNames.containsAll(expectedNames) &&
                    expectedNames.containsAll(actualIntervalNames));

        assertTrue(adjList.isConsistent());

        //try with another interval
        Interval newInte2 = new Interval("new interval 2", 30, 40);
        // add an interval and adjust the expected names and set the number of names
        adjList.addInterval(newInte2);
        expectedNames.add(newInte2.getName());
        // check we have the right number of intervals
        assertEquals(oldCount+2, adjList.size());
        // get the actual interval names from SUT
        actualIntervalNames = adjList.getIntervalNames();
        // see if the SUT is supplying the interval names we expect
        assertTrue(actualIntervalNames.containsAll(expectedNames) &&
                expectedNames.containsAll(actualIntervalNames));

        assertTrue(adjList.isConsistent());

    }

    //todo test getIntervalFromName, getIntervals

    /**
     * Helper method called by overlapAppropriate
     * @param intervalNames -- variable number of Strings denoting interval names
     * @return an ArrayList of intervals named by the strings, in the parameter ordering
     */
    private ArrayList<Interval> selectIntervals (String... intervalNames) {
        ArrayList<Interval> retIntervals = new ArrayList<>();
        TreeSet<Interval> allIntervals = new TreeSet<>(adjList.getIntervals());

        for (String name : intervalNames) {
            for (Interval inte :allIntervals) {
                if ( name.equals(inte.getName())) {
                    retIntervals.add(inte);
                }
            }
        }
        // check its working, kindof
        assertEquals(intervalNames.length, retIntervals.size());
        return retIntervals;
    }

    /**
     * Helper method called by overlapAppropriate
     * @param intervalNames variable number of Strings denoting interval names
     * @return an ArrayList of intervals NOT named by the strings, in the parameter ordering
     */
    private ArrayList<Interval> rejectIntervals (String... intervalNames) {
        ArrayList<Interval> retList = new ArrayList<>(adjList.getIntervals());

        for (String name : intervalNames) {
            for (Interval intvl : adjList.getIntervals()) {
                if ( name.equals(intvl.getName())) {
                    retList.remove(intvl);
                }
            }
        }
        return retList;
    }

    /**
     * Helper method for tests, called by many tests
     * //todo think that this may be redundant now that we have a tested isConistent method in AdjacencyList
     * //todo evaluate style as per comment above the method
     */
    // has assertions without being a test, now sure about that style-wise
    // args are the names of intervals in the interval graph
    // check the first arg only appears in overlapping lists for the remaining args,
    // and not in the overlapping lists for the first arg and the unmentioned args
    //todo write test for this
    private void overlapAppropriate (String testIntervalName, String... overlappingIntervalNames) throws Exception {
        // get the intervals we expect will overlap the test interval
        List<Interval> expectedIntervals = selectIntervals(overlappingIntervalNames);
        //check the actual list from the SUT is the same as the expected intervals
        List<Interval> actualIntervals = adjList.getOverlappingIntervals(testIntervalName);
        // want to assertTrue(expectedIntervals.contains(actualIntervals) && actualIntervals.contains(expectedIntervals));
        // but comparisons not working, presume doesn't rely on Interval equals, so rewrite
        // -- same length?
        assertEquals(expectedIntervals.size(), actualIntervals.size() );
        // -- unique elements?
        assertEquals(expectedIntervals.size(), new HashSet<Interval>(expectedIntervals).size());
        assertEquals(actualIntervals.size(), new HashSet<Interval>(actualIntervals).size());
        // -- same elements in each?
        boolean allGood = true;
        for (Interval eI : expectedIntervals) {
            boolean found = false;
            for (Interval aI : actualIntervals) {
                if (eI.equals(aI)) found = true;
            }
            if (found == false) allGood = false;
        }
        // -- the lists are the same re elements inside
        assertTrue(allGood);

        //check testIntervalName is not in the lists for any interval that is not in the overlappingIntervalNames
        // includes it should not be in its own list
        List<Interval> notExpectedInIntervals = rejectIntervals(overlappingIntervalNames);
        Interval testInterval = adjList.getIntervalFromName(testIntervalName);
        notExpectedInIntervals.add(testInterval);
        for (Interval intvl : notExpectedInIntervals) {
            assertFalse(adjList.getOverlappingIntervals(intvl.getName()).contains(testInterval) );
        }
    }


    /**
     * Test that all intervals in a graph overlap appropriately
     */
    //
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

    /**
     * Test that a new interval non-overlapping interval can be added successfully
     * //todo can I get rid of the exception that I guard against here?
     */
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


    /**
     * Test that a new interval non-overlapping interval can be added successfully
     */
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

    /**
     * Test that an interval can be added to an empty graph
     */
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

    /**
     * Test that the intervals overlap method works correctly
     */
    @Test
    void intervalsOverlapTest() {
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