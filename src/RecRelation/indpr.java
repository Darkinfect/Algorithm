package RecRelation;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class indpr {
    private static List<Integer> integerList = new ArrayList<>();
    private static void start(){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int k = sc.nextInt();
        int p = 0;
        while(n>p){
            integerList.add(sc.nextInt());
            p++;
        }
        int[][] dp = new int[n][k+1];
        dp[0][0] = 0;
        /*for(int i = 1; i <= n; i++){
            dp[0][0] =0;
            dp[i][0] = - Integer.MAX_VALUE;
            dp[0][i] = - Integer.MAX_VALUE;
        }*/

        for(int i =1; i < n; i++){
            for(int t = 1; t<= k; t++){
                int minval = Integer.MAX_VALUE;
                for(int j = i-1;j>=t-1; j-- ){
                    minval = Math.min(minval,integerList.get(j+1));
                    if(dp[i][t-1] != - Integer.MAX_VALUE){
                        dp[i][t] = Math.max(dp[i][t], dp[j][t-1] + minval);
                    }
                }
            }
        }
        System.out.println(dp[n-1][k-1]);
    }
    public static void main(String[] args) {
        start();
    }
}
/*10 5
1 10 2 8 9 3 5 4 7 6*/