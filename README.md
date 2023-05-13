## Playing around with Java and interval graphs

### Introduction

Given a set of named of intervals construct an interval graph that may be interrogated thus:

1. For the entire graph, yield the set of intervals (nodes)
2. For a given interval (node), yield all overlapping intervals (connected nodes)
3. Bonus: For a given time period, yield the intervals in that time period 


### Input

Named intervals appear in a text file one per line in the format

`Name NumericStart Numeric End`

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
