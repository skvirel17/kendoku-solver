import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;


public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        KenDokuCalculator calculator1 = new KenDokuCalculator();
        calculator1.enterStartState(reader.readLine());
        calculator1.printMatrix();
        List<int[][]> steps = calculator1.aStarSearch();
        printSolution(steps, calculator1.countOption);
        List<int[][]> uninformCostSearch = calculator1.uniformCostSearch();
        printSolution(uninformCostSearch, calculator1.countOption);

    }

    public static void printSteps(List<int[][]> steps){
        for(int[][] matrix: steps){
            printMatrix(matrix);
        }
    }

    public static void printSolution(List<int[][]> steps, int countOption){
        System.out.println("==========Solution==========");
        if(steps.isEmpty()){
            System.out.println("Solution not found");
        }
        printSteps(steps);
        System.out.println("Number of steps to solution: " + (steps.size()-1));
        System.out.println("Number of variants: " + (countOption));
        System.out.println("===========End===========");
    }

    public static void printMatrix(int[][] matrix){
        for(int[] row: matrix){
            for(int value: row){
                System.out.print("|" + value + "|");
            }
            System.out.println();
        }
        System.out.println();
    }
}