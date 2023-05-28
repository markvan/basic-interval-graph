package IntervalGraphTest;

import IntervalGraph.InputParser;
import IntervalGraph.Interval;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class InputParserTest {

    //todo refactor this and AdjacencyListTest.java to be DRY

    // make a two interval input file
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        try {
            FileWriter myWriter = new FileWriter("InputParserTestData.txt");
            myWriter.write("A 1 2\n");
            myWriter.write("B 3 4\n");
            myWriter.close();
            // System.out.println("Successfully set up test data by creating 'InputParserTestData.txt'");
        } catch (IOException e) {
            System.out.println("IOException thrown trying to set up 'InputParserTestData.txt'");
            e.printStackTrace();
        }
    }

    // delete the input file
    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        File file = new File("InputParserTestData.txt");
        if ( ! file.delete()) {
            System.out.println("Failed to delete InputParserTestData.txt.");
        }
    }

    // I'm just experimenting with asserti
    @Test
    void parseFile() {
        // make list of expected intervals
        ArrayList<Interval> expectedIntervals= new ArrayList<>();
        expectedIntervals.add(new Interval("A", 1, 2));
        expectedIntervals.add(new Interval("B", 3, 4));

        // parse file with SUT
        ArrayList<Interval> sutReturedIntervals = new InputParser("InputParserTestData.txt").parseFile();

        // test for SUT correctness

        // illustrative test without hamcrest that SUT has parsed the file correctly
        assertEquals(sutReturedIntervals, expectedIntervals);
        // same test but better style with hamcrest
        assertThat(sutReturedIntervals, is(equalTo(expectedIntervals)));   // fails with not

        //test several cases where 'expected' should not match SUT's parsing of file

        //unequal length lists, same initial Intervals in expected, fails without not
        assertThat(sutReturedIntervals, is(not(equalTo(expectedIntervals.add(new Interval("extra interval", 3, 4))))));   // fails with not
        //another unequal length test, empty list expected, fails without not
        assertThat(sutReturedIntervals, is(not(equalTo(new ArrayList<Interval>() ))));
        //test if fails same length but different interval
        ArrayList<Interval> expectedIntervalsWithDiffNode= new ArrayList<>();
        expectedIntervalsWithDiffNode.add(new Interval("A", 1, 2));
        expectedIntervalsWithDiffNode.add(new Interval("B", 3, 6)); // this node has different end value
        assertThat(sutReturedIntervals, is(not(equalTo(expectedIntervalsWithDiffNode))));   // fails without not

    }
}