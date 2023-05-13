package intervalGraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdjacencyList {
    private int sizePlusNames;
    //private List<List<Interval>> AdjacencyLists = new ArrayList<>();

    HashMap<String, List<Interval>> adjacencyLists = new HashMap<>();

    public AdjacencyList(List<Interval> intervalList) {
        for(Interval interval : intervalList) {
            List<Interval> intersectIntervals = new ArrayList<>();
            adjacencyLists.put(interval.getName(), intersectIntervals);
            for(Interval innerInterval : intervalList) {
                if(interval != innerInterval && intervalsIntersect(interval, innerInterval)) {


                }
            }

        }
    }

    static public boolean intervalsIntersect(Interval interval1, Interval interval2) {
        return ( interval1.getEnd() > interval2.getStart() && interval1.getStart() < interval2.getEnd() );
    }

}
