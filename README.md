## Playing around with Java and interval graphs

### Introduction

Given a set of named of intervals construct an interval graph that may be interrogated thus:

1. For the entire graph, yield the set of nodes 
2. For a given time period, yield the intervals in that time period 
3. For a node, yield the nodes connected to it

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



### Compilation

Requires Hamcrest jar to be installed on the class path in order
to compile (at least some of) the tests.
