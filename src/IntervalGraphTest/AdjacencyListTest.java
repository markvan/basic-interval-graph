package IntervalGraphTest;

import intervalGraph.AdjacencyList;
import intervalGraph.InputParser;
import intervalGraph.Interval;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static intervalGraph.AdjacencyList.*;
import static org.hamcrest.JMock1Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class AdjacencyListTest {

    //todo: refactor this and InputParserTest.java to be DRY

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        // WIKIPEDIA EXAMPLE    https://en.wikipedia.org/wiki/Interval_graph
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
    void intervalNamesTest() {
        ArrayList<Interval> intervalsFromFile = new InputParser("InputParserTestData.txt").parseFile();
        AdjacencyList adjList = new AdjacencyList(intervalsFromFile);
        Set<String> intervalNames = adjList.getIntervalNames();
        assertTrue(intervalNames.size() == intervalsFromFile.size() );
        Collection<String> names = new ArrayList<>();
        for (Interval i : intervalsFromFile) {
            names.add(i.getName());
        }
        assertTrue(intervalNames.containsAll(names));

    }

    // only tests one case with a list to return, and an empty case
    //todo improve testing
    @Test
    void getIntersectingIntervalsTest() {
        ArrayList<Interval> intervalsFromFile = new InputParser("InputParserTestData.txt").parseFile();
        AdjacencyList adjList = new AdjacencyList(intervalsFromFile);
        try {
            List<Interval> expectedIntervals = new ArrayList<Interval>();
            expectedIntervals.add(new Interval("B", 2, 4));
            expectedIntervals.add(new Interval("C", 3, 11));
            expectedIntervals.add(new Interval("D", 5, 10));
            assertEquals(adjList.getIntersectingIntervals("A"), expectedIntervals);

            List<Interval> emptyExpectedIntervals = new ArrayList<Interval>();
            assertEquals(adjList.getIntersectingIntervals("H"), emptyExpectedIntervals);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void intervalsIntersectTest() {
        // non overlapping
        Interval i1 = new Interval("A", 1, 2);
        Interval i2 = new Interval("A", 4, 6);
        assertFalse(intervalsIntersect(i1, i2));
        assertFalse(intervalsIntersect(i2, i1));
        i1 = new Interval("A", 2, 3);
        i2 = new Interval("A", 3, 6);
        assertFalse(intervalsIntersect(i1, i2));
        assertFalse(intervalsIntersect(i2, i1));

        //overlapping
        i1 = new Interval("A", 2, 5);
        i2 = new Interval("A", 4, 6);
        assertTrue(intervalsIntersect(i1, i2));
        assertTrue(intervalsIntersect(i2, i1));
        i1 = new Interval("A", 3, 4);
        i2 = new Interval("A", 2, 6);
        assertTrue(intervalsIntersect(i1, i2));
        assertTrue(intervalsIntersect(i2, i1));
        i1 = new Interval("A", 4, 6);
        i2 = new Interval("A", 1, 5);
        assertTrue(intervalsIntersect(i1, i2));
        assertTrue(intervalsIntersect(i2, i1));
        i1 = new Interval("A", 5, 6);
        i2 = new Interval("A", 1, 5);

        // non overlapping
        assertFalse(intervalsIntersect(i1, i2));
        assertFalse(intervalsIntersect(i2, i1));
        i1 = new Interval("A", 6, 7);
        i2 = new Interval("A", 1, 5);
        assertFalse(intervalsIntersect(i1, i2));
        assertFalse(intervalsIntersect(i2, i1));




    }
}