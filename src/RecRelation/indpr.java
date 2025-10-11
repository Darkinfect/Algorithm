package RecRelation;

import java.util.Arrays;
import java.util.Scanner;

public class indpr {
    private static void start(){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int k = sc.nextInt();
        int p = 0;
        int[] massive = new int[n];
        while(n>p){
            massive[p] = sc.nextInt();
            p++;
        }
        int[][] dp = new int[n+1][k+1];
        for (int i = 0; i <= n; i++){
            Arrays.fill(dp[i], Integer.MIN_VALUE);
        }
        dp[0][0] = 0;

        for(int i =1; i <= n; i++){
            for(int j = 1; j<= Math.min(i,k); j++){
                int currMin = Integer.MAX_VALUE;
                for(int m = i-1; m >= j -1; m-- ){
                    currMin = Math.min(currMin,massive[m]);
                    if(dp[m][j-1] != Integer.MIN_VALUE){
                        dp[i][j] = Math.max(dp[i][j], dp[m][j-1] + currMin);
                    }
                }
            }
        }
        System.out.println(dp[n][k]);
    }
    public static void main(String[] args) {
        start();
    }
}
/*10 5
1 10 2 8 9 3 5 4 7 6*/