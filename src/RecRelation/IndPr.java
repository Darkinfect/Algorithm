package RecRelation;

import java.util.Arrays;
import java.util.Scanner;

public class IndPr {
    private static void start(){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int k = sc.nextInt();
        int[] massive = new int[n];
        for(int i = 0; i < n; i++){
            massive[i] = sc.nextInt();
        }
        int[][] minRange = new int[n][n];
        for (int l = 0; l < n; l++) {
            int minVal = massive[l];
            for (int r = l; r < n; r++) {
                minVal = Math.min(minVal, massive[r]);
                minRange[l][r] = minVal;
            }
        }
        long[][] dp = new long[n+1][k+1];
        for (int i = 0; i <= n; i++){
            Arrays.fill(dp[i], Integer.MIN_VALUE);
        }
        dp[0][0] = 0;

        for(int i =1; i <= n; i++){
            for(int j = 1; j<= Math.min(i,k); j++){
                long bet = Integer.MIN_VALUE;
                for(int m = i-1; m >= j -1; m-- ){
                    long prev = dp[m][j-1];
                    if(prev == Integer.MIN_VALUE){
                        continue;
                    }
                    int curr = minRange[m][i-1];
                    long som = prev + curr;
                    if(som>bet) bet = som;
                }
                dp[i][j] = bet;
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