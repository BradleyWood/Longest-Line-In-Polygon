# Longest Line in Polygon

### Intro

As an assignment for SOFE 3770 (Design and Analysis of Algorithms) we are tasked 
to find the longest possible runway that could be constructed on an island.

### The solution

The solution can be straight forward given a few rules. The longest line
inside a polygon must pass through at least two vertices but not necessarily
end on a vertex. This rule simplifies runway construction since if we have found
a valid line between two vertices we can extend the line on both sides
until it intersects with an edge.

### Valid line tests

<p>
This is the more challenging part of the algorithm. A valid line is said to be line
that if fully contained within the polygon. To begin, if the two vertices in the 
specified line are also one of the edges of the polygon (that is that the vertices
are sequential) we can immediately say it is valid. However this is not the case for
most of the valid line tests. 
</p>
<p>
Next we will eliminate the line if it said to CROSS
one of the edges. Two lines cross when there is an intersection between them, and one
of the lines does not end on the other.
</p>
<p>
At this point we know that the line does not cross any of the edges but that does not
account for lines that are parallel. This is important because if our line is parallel
with an edge it may also exit the polyon at some point without our knowing. To solve this
problem we calculate each intersection point between our line and the edges of the polygon
and sort the intersection points by distance from one of our lines verticies. We can use
this information to check if the midpoint between each intersection is contained within
the polyon. If one of the midpoints between the intersections is outside the polygon it
is said to be an invalid line.
</p>
<p>
At this point, if the line hasn't been invalidated, we establish that it is valid.
</p>

### Complexity

<p>
This algorithm is has an average time complexity of n^3 and a worst case time complexity
of n^3 log n. The space case space complexity is n.
</p>

### Running times


|  Data Set (n) | Time (ms) |
| :-----------: | :-------: |
|       3       |    3      |
|       5       |    2      |
|       7       |    5      |
|       9       |    3      |
|       13      |    5      |
|       17      |    5      |
|       56      |    20     |
|       113     |    41     |
|       148     |    78     |
|       200     |    380    |
|       500     |    4100   |


## Results

![Alt Text](https://github.com/BradleyWood/Island-Airport-Calculator/blob/master/results/data3.png?raw=true) <br> <br>
![Alt Text](https://github.com/BradleyWood/Island-Airport-Calculator/blob/master/results/data5.png?raw=true) <br> <br>
![Alt Text](https://github.com/BradleyWood/Island-Airport-Calculator/blob/master/results/data9.png?raw=true) <br> <br>
![Alt Text](https://github.com/BradleyWood/Island-Airport-Calculator/blob/master/results/data11.png?raw=true) <br> <br>
![Alt Text](https://github.com/BradleyWood/Island-Airport-Calculator/blob/master/results/data17.png?raw=true) <br> <br>
![Alt Text](https://github.com/BradleyWood/Island-Airport-Calculator/blob/master/results/data56.png?raw=true) <br> <br>
![Alt Text](https://github.com/BradleyWood/Island-Airport-Calculator/blob/master/results/data113.png?raw=true) <br> <br>
![Alt Text](https://github.com/BradleyWood/Island-Airport-Calculator/blob/master/results/data148.png?raw=true) <br> <br>
![Alt Text](https://github.com/BradleyWood/Island-Airport-Calculator/blob/master/results/data500.png?raw=true) <br> <br>
