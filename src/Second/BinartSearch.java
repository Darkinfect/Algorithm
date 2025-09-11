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
    private static int n;
    private static int foundMinIndexNumber(int[] arr,int index){
        if( index == 0) return index;
        if(arr[index] > arr[index-1]){
            return index;
        }
        int index2 = foundMinIndexNumber(arr,index-1);
        return index2;
    }
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
    }
    private static int upperbound(int[] arr,int target){
        int left = 0;
        int right = arr.length;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] <= target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }
    private static int binSearch(int[] massive, int target){
        int min, max, index;
        max = massive.length;
        min = 0;
        if(target > massive[max-1] || target < massive[min]){
            return n;
        }
        while(min<max){
            index = min + (max-min) / 2;
            if(massive[index] == target){
                return foundMinIndexNumber(massive,index);
            }
            if(target > massive[index]){;
                min = index + 1;
                continue;
            }
            if(target< massive[index]){
                max = index;
                continue;
            }
        }
        return n;
    }
}
