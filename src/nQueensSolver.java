import java.util.ArrayList;
import java.util.Scanner;

public class nQueensSolver {
    public static void main(String[] args) {
        System.out.println("This is a solver to the n-Queen problem.");
        System.out.println("The problem consists of arranging n queens on a n by n board such that no queen can attack another.");
        Scanner input = new Scanner(System.in);
        System.out.println("Enter a number to solve for: ");
        int n = input.nextInt();
        input.close();
        Board b = new Board(n);
        ArrayList<Boolean> result = b.solve();
        if (result == b.reference) {
            System.out.println("No Solution.");
        } else {
            System.out.println(b.printResult(b.board));
        }
    }
}