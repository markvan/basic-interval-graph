package IntervalGraphTest;

import intervalGraph.Interval;
import org.junit.jupiter.api.Test;

import static java.util.function.Predicate.isEqual;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.*;

class IntervalTest {

    @Test
    void testEquals() {
        Interval a = new Interval("name1", 1, 2);
        Interval eqA = new Interval("name1", 1, 2);
        Interval notEqA1 = new Interval("name2", 1, 2);
        Interval notEqA2 = new Interval("name1", 0, 2);
        Interval notEqA3 = new Interval("name1", 1, 3);
        //assertThat(a, equalTo(eqA));
        assertEquals(a,eqA);
        // this fails correctly assertNotEquals(a, eqA);
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