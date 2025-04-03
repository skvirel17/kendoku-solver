# Kendoku Solver 

### Overview
This project implements a solver for a simplified version of a Kendoku pazzle using three different search algorithms: A* Search, Iterative Deepening Search (IDS), and Uniform Cost Search (UCS). 
The program allows users to input the initial state of the cube and choose the algorithm to find the solution.

### Key Features
- User Input: The program prompts the user to input the initial state of the pazzle as a string of 12 characters representing the start pazzle state.
- Algorithm Selection: Users can choose between A* Search,  and Uniform Cost Search (UCS) to solve the puzzle.
- Solution Path: The program outputs the sequence of moves to solve the pazzle and the number of options visited during the search.
## Classes and Methods
### Solver Class
- main(String[] args): The entry point of the program, handles user input and algorithm selection.
### KenDokuCalculator Class
- isGoal(State state): Checks if the current state is the goal state.
- getHeuristic(State state, String goal): calculates a heuristic value for a given state (represented as a 2D array) based on the specified goal type. 
- aStarSearch(State initialState): Implements A* Search to find a solution.
- uniformCostSearch(State initialState): Implements UCS to find a solution.
- constructSolutionPath(State goalState): Constructs the solution path from the initial state to the goal state.
- swapcells(State state, x1, y1, x2, y2): swaps the values at positions.
- reconstructPath(Map<int[][],int[][]> cameFrom, int[][] current): shows path to goal.
### MatrixKey class
- The MatrixKey class is a wrapper for a 2D integer array (int[][]) that provides proper implementations of equals() and hashCode(). 
It enables using 2D arrays as keys in hash-based collections (like HashMap or HashSet) by comparing the contents of the arrays rather than their memory addresses.

## Search Algorithms
- A Search:* Uses a priority queue to explore the most promising paths first, combining the cost to reach a state and a heuristic estimate of the cost to reach the goal.
- Iterative Deepening Search (IDS): Combines the depth-first search's space efficiency and breadth-first search's completeness by iteratively deepening the search limit.
- Uniform Cost Search (UCS): Explores paths in order of their cost, ensuring that the lowest-cost solution is found.
## Usage
1. Input the initial state: Enter a 12-character string representing the pazzle's state.
2. Choose the goal:
- 'DEF' — basic solve
- 'SUM' — sum of the first three cells
- 'TOP' — path for the first three cells only
3. View the solution: The program outputs the sequence of moves and the number of visited vertices.
## Example
Enter the initial state of the pazzle (For example: 321 456 780 DEF):

Solution path:

![321 456 780 DEF](https://github.com/user-attachments/assets/b4a4c337-ee89-48cb-821e-de88aa40e87b)
