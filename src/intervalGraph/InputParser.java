package intervalGraph;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class InputParser {
    final private String fileName;
    public InputParser(String fn) {
        fileName = fn;
    }

    public  Interval[]  parseFile() {
        final int intervalName = 0;
        final int intervalStart = 1;
        final int intervalEnd = 2;
        Interval intervals[] = null;

        try
        {
            String line;
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null)
            {
                String lineValues[] = line.split(" ");
                System.out.println(lineValues[intervalName] + " " + lineValues[intervalStart] + " " + lineValues[intervalEnd] );
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
