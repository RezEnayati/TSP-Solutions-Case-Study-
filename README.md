# TSP-Solutions Case Study
**Traveling Salesman Problem:** Given a set of cities and distance between every pair of cities, find the shortest tour that visits every city exactly ones and returns to the starting point. It turns out answering this question is not so easy and it belongs to a select subset of problems classified as NP-hard because there is no known algorithm that can optimally give us a solution. A brute force approach can be implemented for this problem, the way you would solve with a brute force approach is that you can find every possible route that visits each city, calculate the costs for each one and choose the one that is the shortest. This solution provides us with the shortest path but it comes at a high computational cost because the number of possible routs increases **factorially** with the number of cities. For example if we had 10 cities the total number of possible routes is $10! = 362,880$ and for 20 cities is $20! = 121,645,100,408,832,000$ possible routes. To put these numbers in perspective if we calculate every route in a millisecond, it would require 3.8 million years to check every solution for a 20 city TSP. Fortunately, we can leverage the power of heuristics with problems like this. Heuristics are problem-solving techniques that use shortcuts, rules of thumb, or practical approaches to find approximate or “good-enough” solutions to complex problems more quickly and efficiently than exhaustive methods.

One popular heuristic for the TSP problem is **The Nearest Neighbor** algorithm:

1. Start at a city.
2. Mark the current city as visited. 
3. Find the unvisited city nearest to the current city. 
4. If there are no unvisited cities left, go back to the starting city and terminate the algorithm. Otherwise, set the nearest unvisited city as the current city and return to step 2. 

This is a greedy heuristic algorithm meaning that it makes the best local choice at each step. Of course this series of locally optimal solutions does not guarantee that the final solution will be optimal but it’s primary advantage is that it is easy to implement and executes quickly. This implementation is provided in the java files I uploaded and the main source is this article on reddit but the code is written by me. 

https://www.reddit.com/r/computerscience/comments/131yw2k/an_introduction_to_algorithms_pt_4_the_tsp_and/

**The Problems with the Nearest Neighbor Algorithm:** 

The Nearest Neighbor algorithm is a fast and simple heuristic for the Traveling Salesman Problem, but it often produces suboptimal results due to its greedy, shortsighted decisions and sensitivity to the starting city.

To improve on this, the **2-opt algorithm** is commonly used to refine the initial tour by **iteratively swapping edges to reduce the total distance**, leading to significantly better solutions while still maintaining reasonable speed.

**2-opt** is a **local search optimization** algorithm used to improve an initial tour (like one generated by Nearest Neighbor). It works by:

- **Removing two edges**
- **Reconnecting them in a different way** to reduce the overall tour length

It’s simple, efficient, and often produces much better solutions than greedy heuristics alone.

The code for this was found in this repository but I had to update it to able to read matrices instead of Point2D ArrayLists. 

https://github.com/jackspyder/2-opt

**Explanation:**

The 2-opt algorithm begins by generating an initial tour using the Nearest Neighbor heuristic, which provides a fast but often suboptimal solution to the Traveling Salesman Problem (TSP). This initial tour is represented as a list of city indices.

The core idea of 2-opt is to improve this tour by iteratively identifying and removing pairs of edges that cross or create inefficient detours. For each pair of non-adjacent edges in the tour, the algorithm considers swapping their endpoints by reversing the segment of cities between them. This process is known as a “2-opt move.”

In each iteration, the algorithm calculates the cost of the current tour and the potential cost if the segment between cities i and j were reversed. Specifically, it compares the distances of the original edges (i−1, i) and (j, j+1) with the proposed edges (i−1, j) and (i, j+1). If the swap results in a shorter total tour length, the tour is updated and the algorithm restarts the outer loop to recheck for further improvements.

The swapping logic is implemented in a helper function that constructs a new tour by preserving the first part of the original tour up to city i−1, reversing the segment from city i to j, and appending the remaining part of the tour after city j. The loop continues until no further improvements can be made, which indicates that a local minimum has been reached.

At the end of the process, the improved tour is returned. This refined solution typically has a significantly lower total distance than the initial greedy tour, and the algorithm strikes a practical balance between efficiency and solution quality.
