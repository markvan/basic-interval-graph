package IntervalGraphTest;

import intervalGraph.InputParser;
import intervalGraph.Interval;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class InputParserTest {

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
        if (file.delete()) {
            // System.out.println("Successfully tore down test data by deleting 'InputParserTestData.txt'");
        } else {
            System.out.println("Failed to delete InputParserTestData.txt.");
        }
    }

    @Test
    void parseFile() {
        ArrayList<Interval> expectedIntervals= new ArrayList<Interval>();
        expectedIntervals.add(new Interval("A", 1, 2));
        expectedIntervals.add(new Interval("B", 3, 4));

        ArrayList<Interval> sutReturnValue = new InputParser("InputParserTestData.txt").parseFile();
        assertEquals(sutReturnValue, expectedIntervals);
        // better style with hamcrest


    }
}