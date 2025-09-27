package RecRelation;

import java.util.Scanner;

public class pr05 {
    private static void start(){
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] arr = new int[n];
        for(int i = 0; i < n; i++){
            arr[i] = scanner.nextInt();
        }
        int[] arr2 = new int[n];
        for(int i = 0; i < n; i++){
            arr2[i] = scanner.nextInt();
        }
        int[][] solution = new int[n][n];
        for(int i = 0; i < n; i++){
            solution[0][i] = 0;
            solution[i][0] = 0;
        }
        for(int i = 1; i <n; i++){
            for(int j = 1; j < n; j++){
                if(arr[i-1]==arr2[j-1]){
                    solution[i][j] = solution[i-1][j-1] +1;
                }else{
                    solution[i][j] = Math.max(solution[i-1][j],solution[i][j-1]);
                }
            }
        }
        System.out.println(solution[n-1][n-1]);
    }
    public static void main(String[] args) {
        start();
    }
}
