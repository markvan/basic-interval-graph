package intervalGraph;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class InputParser {
    final private String fileName;
    public InputParser(String fn) {
        fileName = fn;
    }

    //todo solid input checking
    //todo sort sensitivity to blank input lines
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
                System.out.println(line);
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
            System.out.println("Unable to open file '" + fileName + "'");
        }
        catch(IOException e)
        {
            System.out.println("Error reading file '" + fileName + "'");
        }

        return intervals;
    }


}
