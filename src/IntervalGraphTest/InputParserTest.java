package IntervalGraphTest;

import intervalGraph.InputParser;
import intervalGraph.Interval;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class InputParserTest {

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        try {
            FileWriter myWriter = new FileWriter("InputParserTestData.txt");
            myWriter.write("A 1 2\n");
            myWriter.write("B 3 4\n");
            myWriter.close();
            System.out.println("Successfully wrote to InputParserTestData.txt");
        } catch (IOException e) {
            System.out.println("IOException thrown trying to write to InputParserTestData.txt");
            e.printStackTrace();
        }
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        File file = new File("InputParserTestData.txt");
        if (file.delete()) {
            System.out.println("Deleted the file: " + file.getName());
        } else {
            System.out.println("Failed to delete InputParserTestData.txt.");
        }
    }

    @Test
    void parseFile() {
        Interval[] intervals = new InputParser("./input/intervals01.txt").parseFile();
    }
}