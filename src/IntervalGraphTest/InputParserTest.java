package IntervalGraphTest;

import intervalGraph.InputParser;
import intervalGraph.Interval;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class InputParserTest {

    //todo: refactor this and AdjacencyListTest.java to be DRY

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

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        File file = new File("InputParserTestData.txt");
        if ( ! file.delete()) {
            System.out.println("Failed to delete InputParserTestData.txt.");
        }
    }

    @Test
    void parseFile() {
        ArrayList<Interval> expectedIntervals= new ArrayList<>();  // had a warning with explicit type in initialisation
        expectedIntervals.add(new Interval("A", 1, 2));
        expectedIntervals.add(new Interval("B", 3, 4));

        ArrayList<Interval> sutReturnValue = new InputParser("InputParserTestData.txt").parseFile();

        // test for SUT correctness

        // illustrative test without hamcrest
        assertEquals(sutReturnValue, expectedIntervals);
        // better style with hamcrest
        assertThat(sutReturnValue, is(equalTo(expectedIntervals)));   // fails with not

        //test several cases that should not match SUT

        //unequal length lists, same initial Intervals in expected, fails without not
        assertThat(sutReturnValue, is(not(equalTo(expectedIntervals.add(new Interval("extra interval", 3, 4))))));   // fails with not
        //another unequal length test, empty list expected, fails without not
        assertThat(sutReturnValue, is(not(equalTo(new ArrayList<Interval>() ))));
        //test if fails same length but different interval
        ArrayList<Interval> expectedIntervalsWithDiffNode= new ArrayList<>();
        expectedIntervalsWithDiffNode.add(new Interval("A", 1, 2));
        expectedIntervalsWithDiffNode.add(new Interval("B", 3, 6)); // this node has different end value
        assertThat(sutReturnValue, is(not(equalTo(expectedIntervalsWithDiffNode))));   // fails without not

    }
}