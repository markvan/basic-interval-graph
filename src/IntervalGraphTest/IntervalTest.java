package IntervalGraphTest;

import IntervalGraph.Interval;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

class IntervalTest {

    // check Interval instantiation is correct when duplicating
    @Test
    void checkDuplicateInstantiation() {
        Interval a = new Interval("name1", 1, 2);
        // check that the 'duplication' constructor works
        assertEquals(a, new Interval(a));
    }

    // check that override of Object.equals in Interval is working correctly
    @Test
    void testEquals() {
        Interval a = new Interval("name1", 1, 2);
        Interval eqA = new Interval("name1", 1, 2);
        Interval notEqA1 = new Interval("name2", 1, 2);
        Interval notEqA2 = new Interval("name1", 0, 2);
        Interval notEqA3 = new Interval("name1", 1, 3);

        //a should not be equal to null
        assertNotEquals(a,null);
        //a should  be equal a
        assertEquals(a,a);
        //assertThat(a, equalTo(eqA));
        assertEquals(a,eqA);
        // check not equal
        assertNotEquals(a, notEqA1);
        assertNotEquals(a, notEqA2);
        assertNotEquals(a, notEqA3);
        // better style with hamcrest matchers
        assertThat(a, is(equalTo(eqA)));           // fails with not
        assertThat(a, is(not(equalTo(notEqA1))));  // fails without not
        assertThat(a, is(not(equalTo(notEqA2))));  // fails without not
        assertThat(a, is(not(equalTo(notEqA3))));  // fails without not
    }
}