package intervalGraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

// The AdjacencyList class implements an interval graph
// in a more sophisticated approach where one could choose between implementations
// IntervalGraph might be an (abstract) interface with AdjacencyList as one of the implementations

public class AdjacencyList {
    HashMap<String, List<Interval>> adjacencyLists = new HashMap<>();

    public AdjacencyList(List<Interval> intervalList) {
        // make one adjacency lists for each interval in the Hashmap adjacencyLists
        for(Interval interval : intervalList) {
            // create an adjacency list
            List<Interval> intersectIntervals = new ArrayList<>();
            // stick the adjacency list in the hashmap indexed buy the interval name
            //todo - consider if we want the key to be the complete interval triple rather than the name, some advantages
            adjacencyLists.put(interval.getName(), intersectIntervals);
            // now in that adjacency list add all the overlapping intervals
            for(Interval innerInterval : intervalList) {
                if(interval != innerInterval && intervalsOverlap(interval, innerInterval)) {
                    // making a duplicate interval here so if anyone messes with the original the
                    // AdjacencyList being instantiated is still OK
                    intersectIntervals.add(new Interval(innerInterval));
                }
            }
        }
    }

    // get the interval names from the nodes in the interval graph
    public Set<String> getIntervalNames() {
        return adjacencyLists.keySet();
    }

    // could create an Exception subclass for this but just taking a shortcut
    public List<Interval> getOverlappingtervals(String intervalName) throws Exception {
        if (! getIntervalNames().contains(intervalName) ) {
            throw new Exception("Bad interval name supplied to AdjacencyList getOverlappingtervals");
        }
        //  unwilling to do anything other than supply a copy of a list of overlapping intervals for safety
        List<Interval> overlappingIntervalsList = adjacencyLists.get(intervalName);
        List<Interval> retOverlappingIntervalsList = new ArrayList<Interval>();
        for (Interval i : overlappingIntervalsList) {
            retOverlappingIntervalsList.add(new Interval( i ));
        }
        return retOverlappingIntervalsList;
    }

    //not crazy about this being public but need to test it
    static public boolean intervalsOverlap(Interval interval1, Interval interval2) {
        return ( interval1.getEnd() > interval2.getStart() && interval1.getStart() < interval2.getEnd() );
    }

}
