package RecRelation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
        int[][] solution = new int[n+1][n+1];
        for(int i = 0; i <= n; i++){
            solution[0][i] = 0;
            solution[i][0] = 0;
        }
        for(int i = 1; i <= n; i++){
            for(int j = 1; j <= n; j++){
                if(arr[i-1]==arr2[j-1]){
                    solution[i][j] = solution[i-1][j-1] +1;
                }else{
                    solution[i][j] = Math.max(solution[i-1][j],solution[i][j-1]);
                }
            }
        }
        List<Integer> result1 = new ArrayList<>();
        List<Integer> result2 = new ArrayList<>();
        int i = n;
        int j = n;
        while(i>0 && j >0){
            if(arr[i-1] == arr2[j-1]){
                result1.add(i-1);
                result2.add(j-1);
                i--;
                j--;
            }else if(solution[i-1][j] > solution[i][j-1]){
                i--;
            }else{
                j--;
            }
        }
        Collections.reverse(result1);
        Collections.reverse(result2);
        System.out.println(solution[n][n]);
        for(Integer a : result1){
            System.out.print(a + " ");
        }
        System.out.println();
        for(Integer s : result2){
            System.out.print(s + " ");
        }
    }
    public static void main(String[] args) {
        start();
    }
}
