package IntervalGraph;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

// input file parser, returns a list of intervals

public class InputParser {

    final boolean debugMessages = false;

    final private String fileName;
    public InputParser(String fn) {
        fileName = fn;
    }

    //todo solid input checking
    //todo ensure no duplicate interval names
    //todo sort sensitivity to blank input lines, eg on end of file
    public  ArrayList<Interval>  parseFile() {
        final int intervalName = 0;
        final int intervalStart = 1;
        final int intervalEnd = 2;
        ArrayList<Interval> intervals = new ArrayList<>();

        // read the input file, parse lines and add Interval instances to the list of Intervals
        try {
            String line;
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null)
            {
                if (debugMessages) {
                    System.out.println(line);
                }
                String lineValues[] = line.split(" ");
                final int start = parseInt(lineValues[intervalStart]);
                final int end   = parseInt(lineValues[intervalEnd]);
                // got the data for an interval so
                // instantiate an interval object and add iot to the list of intervals
                intervals.add(new Interval((lineValues[intervalName]), start, end ));
            }
            bufferedReader.close();
        }
        catch(FileNotFoundException e)
        {
            System.err.println("Unable to open file '" + fileName + "'");
        }
        catch(IOException e)
        {
            System.err.println("Error reading file '" + fileName + "'");
        }

        // return the intervals in the file
        return intervals;
    }


}
