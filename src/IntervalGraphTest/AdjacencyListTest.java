package IntervalGraphTest;

import intervalGraph.Interval;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static intervalGraph.AdjacencyList.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AdjacencyListTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void construction() {

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