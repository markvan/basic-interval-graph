package IntervalGraphTest;

import intervalGraph.InputParser;
import intervalGraph.Interval;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InputParserTest {

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @Test
    void parseFile() {
        Interval[] intervals = new InputParser("./input/intervals01.txt").parseFile();
    }
}