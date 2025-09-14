package Second;

import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class BinartSearch {
    private static int UpperBound;
    private static int LowerBound;
    private static int n;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        n = scanner.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = scanner.nextInt();
        }

        int k = scanner.nextInt();
        int[] queries = new int[k];
        for (int i = 0; i < k; i++) {
            queries[i] = scanner.nextInt();
        }

        for(int i = 0; i < k; i++){
        }
    }
    private static int LowerBound(int[] arr, int target){
        int low =0;
        int high = arr.length-1;
        int resault = arr.length;
        while(low<high){
            int mid = low + (high - low)/2;
            if(arr[high]>=target){
                resault = mid;
                high = mid -1;
            }else{
                return resault;
            }
        }
        return 0;
    }

}
