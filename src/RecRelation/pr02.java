package RecRelation;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class pr02 {
    private static List<List<Integer>> buildTriangle(int numRows) {
        List<List<Integer>> triangle = new ArrayList<>();

        if (numRows <= 0) {
            return triangle;
        }
        List<Integer> firstRow = new ArrayList<>();
        firstRow.add(1);
        triangle.add(firstRow);

        for (int i = 1; i < numRows; i++) {
            List<Integer> prevRow = triangle.get(i - 1);
            List<Integer> currentRow = new ArrayList<>();

            currentRow.add(1);


            for (int j = 1; j < i; j++) {
                currentRow.add((prevRow.get(j - 1) + prevRow.get(j)) % 1000000007);
            }

            // Последний элемент строки всегда 1
            currentRow.add(1);

            triangle.add(currentRow);
        }

        return triangle;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int k = scanner.nextInt();
        if(k == 0){
            System.out.println("1");
            return;
        }
        List<List<Integer>> triangle = buildTriangle(n+1);
        System.out.println(triangle.get(n).get(k));
    }
}
