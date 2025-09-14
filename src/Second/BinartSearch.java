package Second;


import java.util.Scanner;


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
        for(int i = 0; i < k; i++) {

            int lower = LowerBound(arr, queries[i]);
            int upper = UpperBound(arr, queries[i]);

            System.out.println((lower == upper ? "0" : "1") + " " + lower + " " + upper);
        }
    }
    private static int LowerBound(int[] arr, int target){
        int low =0;
        int high = n;
        while(low<high){
            int mid = low + (high - low)/2;
            if(arr[mid]>=target){
                high = mid;
            }else{
              low = mid +1;
            }
        }
        return low;
    }
    private static int UpperBound(int[] arr, int target){
        int low =0;
        int high = n;
        while(low<high){
            int mid = low + (high - low)/2;
            if(arr[mid]<=target){
                low = mid + 1;
            }else{
                high= mid;
            }
        }
        return low;
    }
}
