package intervalGraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class AdjacencyList {
    HashMap<String, List<Interval>> adjacencyLists = new HashMap<>();

    public AdjacencyList(List<Interval> intervalList) {
        // make a bunch of adjacency lists in the Hashmap adjacencyLists
        for(Interval interval : intervalList) {
            // this is the creation of an adjacency list
            List<Interval> intersectIntervals = new ArrayList<>();
            // stick the adjacency list in the hashmap indexed buy the interval name
            adjacencyLists.put(interval.getName(), intersectIntervals);
            // now in that adjacency list add all the intersecting intervals
            for(Interval innerInterval : intervalList) {
                if(interval != innerInterval && intervalsIntersect(interval, innerInterval)) {
                    // making a duplicate interval here so if anyone messes with the original the
                    // AdjacencyList being instantiated is still OK
                    intersectIntervals.add(new Interval(innerInterval.getName(), innerInterval.getStart(), innerInterval.getEnd()));
                }
            }
        }
    }

    public Set<String> getIntervalNames() {
        return adjacencyLists.keySet();
    }

    // could create an Exception subclass for this but just taking a shortcut
    public List<Interval> getIntersectingIntervals(String intervalName) throws Exception {
        if (! getIntervalNames().contains(intervalName) ) {
            throw new Exception("Bad interval name supplied to AdjacencyList getIntersectingIntervals");
        }
        //  unwilling to do anything other than supply a copy of a list of intesecting intervals
        List<Interval> intersectingList = adjacencyLists.get(intervalName);
        List<Interval> retList = new ArrayList<Interval>();
        for (Interval i : intersectingList) {
            //todo  add a duplicate method to Interval to avoid this use of getters three times in code
            retList.add(new Interval( i.getName(), i.getStart(), i.getEnd() ));
        }
        return retList;
    }

    //not crazy about this being public but need to test it
    static public boolean intervalsIntersect(Interval interval1, Interval interval2) {
        return ( interval1.getEnd() > interval2.getStart() && interval1.getStart() < interval2.getEnd() );
    }

}
