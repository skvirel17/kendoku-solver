import java.util.*;


public class KenDokuCalculator {
    private static final int[][] DEF_GOAL = new int[][]{{1,2,3}, {4,5,6}, {7,8,0}};
    private static final int[][] TOP_GOAL = new int[][]{{1,2,3}, {-1,-1,-1}, {-1,-1,-1}};
    private static final int[][] SUM_GOAL = new int[][]{{1,2,3}, {4,5,6}, {7,8,0}};

    //valid value only after A* method
    int countOption = 0;
    int[][] state;
    String goal;

    public void enterStartState(String s){
        state = new int[3][3];
        String[] rows = s.split(" ");
        for (int i = 0; i < 3; i++){
            String[] values = rows[i].split("");
            for (int j = 0; j < 3; j++){
                state[i][j] = Integer.parseInt(values[j]);
            }
        }
        goal = rows[3].toUpperCase();
    }

    private int getHeuristic(int[][] state, String goal){
        int result = 0;
        if(goal.equals("DEF")){
           result = calculateDefHeuristic(state);
        }
        if(goal.equals("TOP")){
            result = calculateTopHeuristic(state);
        }
        if(goal.equals("SUM")){
            result = calculateSumHeuristic(state);
        }
        return result;
    }

    private int calculateSumHeuristic(int[][] state) {
         int x = 9 - state[0][0];
         int[] xCoordinates = find(x, state);
         int[] emptyCoordinates = find(0, state);
         int tmp = 9;
         if(state[0][0] + state[0][1] == 9){
             tmp = ManDis(emptyCoordinates[0], emptyCoordinates[1], 0, 2);
         }
         return ManDis(xCoordinates[0], xCoordinates[1], 0, 1) + tmp;
    }

    private int calculateTopHeuristic(int[][] state) {
        int[] oneCoordinates = find(1, state);
        int[] twoCoordinates = find(2, state);
        int[] threeCoordinates = find(3, state);
        return ManDis(oneCoordinates[0], oneCoordinates[1], 0, 0) + ManDis(twoCoordinates[0], twoCoordinates[1], 0, 1) +
                ManDis(threeCoordinates[0], threeCoordinates[1], 0, 2); //+
                //2*getConflicts(state, DEF_GOAL);
    }

    private int calculateDefHeuristic(int[][] state) {
        int[] oneCoordinates   = find(1, state);
        int[] twoCoordinates   = find(2, state);
        int[] threeCoordinates = find(3, state);
        int[] fourCoordinates  = find(4, state);
        int[] fiveCoordinates  = find(5, state);
        int[] sixCoordinates   = find(6, state);
        int[] sevenCoordinates = find(7, state);
        int[] eightCoordinates = find(8, state);
        int[] emptyCoordinates  = find(0, state);
        return ManDis(oneCoordinates[0], oneCoordinates[1], 0, 0) + ManDis(twoCoordinates[0], twoCoordinates[1], 0, 1) +
                ManDis(threeCoordinates[0], threeCoordinates[1], 0, 2) + ManDis(fourCoordinates[0], fourCoordinates[1], 1, 0) +
                ManDis(fiveCoordinates[0], fiveCoordinates[1], 1,1) + ManDis(sixCoordinates[0], sixCoordinates[1], 1, 2) +
                ManDis(sevenCoordinates[0], sevenCoordinates[1], 2, 0) + ManDis(eightCoordinates[0], eightCoordinates[1], 2, 1) +
                ManDis(emptyCoordinates[0], emptyCoordinates[1], 2, 2);
    }

    private int getConflicts(int[][] state, int[][] goal) {
        int size = state.length;
        int conflict = 0;

        // Check rows for line conflict
        for (int row = 0; row < size; row++) {
            conflict += countRowConflicts(state, goal, row);
        }

        // Check columns for line conflict
        for (int col = 0; col < size; col++) {
            conflict += countColumnConflicts(state, goal, col);
        }

        return conflict;
    }

    private int countRowConflicts(int[][] state, int[][] goal, int row) {
        int conflict = 0;

        for (int i = 0; i < state.length; i++) {
            for (int j = i + 1; j < state.length; j++) {
                // Check if both tiles are valid and in one row
                if (state[row][i] != 0 && state[row][j] != 0) {
                    int targetPosI = findTargetPosition(goal, state[row][i]);
                    int targetPosJ = findTargetPosition(goal, state[row][j]);

                    // Skip tiles with targets in goal marked as -1
                    if (targetPosI == -1 || targetPosJ == -1) continue;

                    if (targetPosI / state.length == row && targetPosJ / state.length == row && targetPosI > targetPosJ) {
                        conflict += 2; // Line conflict
                    }
                }
            }
        }

        return conflict;
    }

    private int countColumnConflicts(int[][] state, int[][] goal, int col) {
        int conflict = 0;

        for (int i = 0; i < state.length; i++) {
            for (int j = i + 1; j < state.length; j++) {
                // Check if both tiles are valid and in one column
                if (state[i][col] != 0 && state[j][col] != 0) {
                    int targetPosI = findTargetPosition(goal, state[i][col]);
                    int targetPosJ = findTargetPosition(goal, state[j][col]);

                    // Skip tiles with targets in goal marked as -1
                    if (targetPosI == -1 || targetPosJ == -1) continue;

                    if (targetPosI % state.length == col && targetPosJ % state.length == col && targetPosI > targetPosJ) {
                        conflict += 2; // Line conflict
                    }
                }
            }
        }

        return conflict;
    }

    private int findTargetPosition(int[][] goal, int value) {
        for (int row = 0; row < goal.length; row++) {
            for (int col = 0; col < goal[0].length; col++) {
                if (goal[row][col] == value) {
                    return row * goal.length + col;
                }
            }
        }
        return -1; // Return -1 if the value is not in the goal or is marked as -1
    }



    private int[] find(int value, int[][] state){
        for (int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(value==state[i][j]){
                    return new int[]{i,j};
                }
            }
        }
        return new int[]{-1,-1};
    }

    private int ManDis(int x1, int y1, int x2, int y2){
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    private boolean isGoalReached(int[][] state){
        if(goal.equals("DEF")){
            return checkDefGoal(state);
        }
        if(goal.equals("TOP")){
            return checkTopGoal(state);
        }
        if(goal.equals("SUM")){
            return checkSumGoal(state);
        }
        return false;
    }

    private boolean checkSumGoal(int[][] state) {
        return state[0][0] + state[0][2] + state[0][1] == 9;
    }

    private boolean checkTopGoal(int[][] state) {
        return state[0][0] == 1 && state [0][1] == 2 && state[0][2] == 3;
    }

    private boolean checkDefGoal(int[][] state) {
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(i + j != 4 && state[i][j] != i*3+j+1){
                    return false;
                }
            }
        }
        return true;
    }

    public List<int[][]> aStarSearch() {
        Map<int[][], int[][]> cameFrom = new HashMap<>();
        Map<MatrixKey, Integer> gScore = new HashMap<>();
        PriorityQueue<int[][]> openQueue = new PriorityQueue<>((state1, state2) -> {
            int f1 = gScore.getOrDefault(new MatrixKey(state1), Integer.MAX_VALUE) + getHeuristic(state1, goal);
            int f2 = gScore.getOrDefault(new MatrixKey(state2), Integer.MAX_VALUE) + getHeuristic(state2, goal);
            return Integer.compare(f1, f2);
        });
        int countOption = 0;

        gScore.put(new MatrixKey(state), 0);
        openQueue.offer(state);
        Set<MatrixKey> closedSet = new HashSet<>();

        while (!openQueue.isEmpty()) {
            int[][] current = openQueue.poll();
            countOption++;
            MatrixKey currentKey = new MatrixKey(current);

            // if goal is reached
            if (isGoalReached(current)) {
                this.countOption = countOption;
                return reconstructPath(cameFrom, current);
            }

            closedSet.add(currentKey);

            for (int[][] neighbor : getNeighbors(current)) {
                MatrixKey neighborKey = new MatrixKey(neighbor);

                if (closedSet.contains(neighborKey)) continue; // skip variants which have already been

                int tentativeGScore = gScore.get(currentKey) + 1; // cost of path

                if (!gScore.containsKey(neighborKey) || tentativeGScore < gScore.get(neighborKey)) {
                    cameFrom.put(neighbor, current);
                    gScore.put(neighborKey, tentativeGScore);
                    openQueue.offer(neighbor);
                }
            }
        }
        // if goal isnt reached
        this.countOption = countOption;
        return new ArrayList<>();
    }


    public List<int[][]> uniformCostSearch() {
        Map<int[][], int[][]> cameFrom = new HashMap<>();
        Map<MatrixKey, Integer> costSoFar = new HashMap<>();
        PriorityQueue<int[][]> openQueue = new PriorityQueue<>((state1, state2) -> {
            int cost1 = costSoFar.getOrDefault(new MatrixKey(state1), Integer.MAX_VALUE);
            int cost2 = costSoFar.getOrDefault(new MatrixKey(state2), Integer.MAX_VALUE);
            return Integer.compare(cost1, cost2);
        });

        costSoFar.put(new MatrixKey(state), 0); // start cost of path
        openQueue.offer(state);
        int countOption = 0;
        while (!openQueue.isEmpty()) {
            int[][] current = openQueue.poll();
            countOption++;

            if (isGoalReached(current)) {
                this.countOption = countOption;
                return reconstructPath(cameFrom, current);
            }

            for (int[][] neighbor : getNeighbors(current)) {
                int newCost = costSoFar.get(new MatrixKey(current)) + getStepCost(current, neighbor); // calculating new cost

                MatrixKey neighborKey = new MatrixKey(neighbor);
                if (!costSoFar.containsKey(neighborKey) || newCost < costSoFar.get(neighborKey)) {
                    costSoFar.put(neighborKey, newCost);
                    openQueue.offer(neighbor);
                    cameFrom.put(neighbor, current);
                }
            }
        }
        this.countOption = countOption;
        return new ArrayList<>(); //
    }

    private int getStepCost(int[][] current, int[][] neighbor) {
        return 1; //
    }


    private List<int[][]> getNeighbors(int[][] current) {
        List<int[][]> res = new ArrayList<>();
        int[] emptyCur = find(0, current);
        if(emptyCur[0] > 0){
            res.add(swapcells(current, emptyCur[0], emptyCur[1], emptyCur[0] - 1, emptyCur[1]));
        }
        if(emptyCur[0] < 2){
            res.add(swapcells(current, emptyCur[0], emptyCur[1], emptyCur[0] + 1, emptyCur[1]));
        }
        if(emptyCur[1] > 0){
            res.add(swapcells(current, emptyCur[0], emptyCur[1], emptyCur[0] , emptyCur[1] - 1));
        }
        if(emptyCur[1] < 2){
            res.add(swapcells(current, emptyCur[0], emptyCur[1], emptyCur[0], emptyCur[1] + 1));
        }
        return res;
    }

    private int[][] swapcells(int[][] state, int x1, int y1, int x2, int y2) {
        int[][] res = new int[3][3];
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                res[i][j] = state[i][j];
            }
        }
        int temp = res[x1][y1];
        res[x1][y1] = res[x2][y2];
        res[x2][y2] = temp;
        return res;
    }


    private List<int[][]> reconstructPath(Map<int[][],int[][]> cameFrom, int[][] current) {
        List<int[][]> res = new ArrayList<>();
        res.add(current);
        while (cameFrom.containsKey(current)){
            current = cameFrom.get(current);
            res.add(0, current);
        }
        return res;
    }

    public void printMatrix(){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                System.out.print(state[i][j] + "|");
            }
            System.out.println();
        }
    }
}
