## Playing around with Java and interval graphs

### Introduction

Given a set of named of intervals construct an interval graph that may be interrogated thus:

1. For the entire graph, yield the set of intervals (nodes)
2. For a given interval (node), yield all overlapping intervals (connected nodes)
3. Bonus: For a given time period, yield the intervals in that time period, not implemented


### Input

Named intervals appear in a text file one per line in the format

`Name NumericStart NumericEnd`

thus

```
A 2 14
Snodgrass 3 5
```

Currently the file is 

`<project root dir>/input/intervals01.txt`

and this needs to be generalised in some way not yet designed.

### Approaches to implementing an interval graph

An undirected graph may be implemented in several ways.

In core we might build an adjacency matrix 
(space inefficient for large graphs) or an adjacency list
(time inefficient) or we might 
reimplement part of a graph database  
like Node4j. 

Or we might even run, say, [Google Guava](https://github.com/google/guava/wiki/GraphsExplained)
or [Apache Commons Gigraph](https://giraph.apache.org/) but, for the latter,
the weekend is too short to 
[set up a Hadoop instance on a virtual machine and then run Gigraph on it](https://giraph.apache.org/quick_start.html)
&mdash; next thing I'm deep in setting up Maven builds, which is doable, but is a slog  when things go wrong.

The easiest would be to [Baeldung's ajacency list code](https://www.baeldung.com/java-graphs), but what's the fun in that?

I could just say, I'm dealing with tiny examples and I'll implement an adjacency table in memory. But because of this desired functionality
```
2. For a given interval (node), yield all overlapping intervals (connected nodes)
```
its going to be almost trival to supply this via an adjacency list approach, so I'll implement that but without looking 
at Baeldung, just to make this a harder exercise.


### Compilation

Requires Hamcrest jar to be installed on the class path in order
to compile (at least some of) the tests.

### Notes on 'finishing for now' aka current state of play

Notes:

1. used the [wikipedia example](https://en.wikipedia.org/wiki/Interval_graph) augmented with non-intersecting interval 'H'.
2. Output is good, see below.
3. Some todos, grep for them in the code. 
4. Could do with reading code and tests, and refactoring the tests to make two files DRYer.
5. Fairly pleased after not touching Java for five years, by the time I finished I was not needing to look up syntax for the code I was writing.

### Output

```
Parsing the interval data
A 1 6
B 2 4
C 3 11
D 5 10
E 7 8
F 9 13
G 12 14
H 15 16
Building the interval graph
Listing intersecting intervals for each interval
Intersecting intervals for interval A
interval [B, 2, 4]
interval [C, 3, 11]
interval [D, 5, 10]
Intersecting intervals for interval B
interval [A, 1, 6]
interval [C, 3, 11]
Intersecting intervals for interval C
interval [A, 1, 6]
interval [B, 2, 4]
interval [D, 5, 10]
interval [E, 7, 8]
interval [F, 9, 13]
Intersecting intervals for interval D
interval [A, 1, 6]
interval [C, 3, 11]
interval [E, 7, 8]
interval [F, 9, 13]
Intersecting intervals for interval E
interval [C, 3, 11]
interval [D, 5, 10]
Intersecting intervals for interval F
interval [C, 3, 11]
interval [D, 5, 10]
interval [G, 12, 14]
Intersecting intervals for interval G
interval [F, 9, 13]
Intersecting intervals for interval H
no intersecting intervals

Process finished with exit code 0
```




