package RecRelation;

import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class pr01 {
    public static void start(){
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int[] arr = new int[n];
        for(int i = 0; i < n; i++){
            arr[i] = scanner.nextInt();
        }
        int[] d = new int[n];
        int[] way = new int[n];
        Arrays.fill(d,-1);
        Arrays.fill(way,-1);
        d[0] = arr[0];

        for(int i = 0; i < n; i++){
            if(d[i] == -1) continue;
            if(i+2 < n && d[i+2] < d[i] + arr[i+2]){
                d[i+2] = d[i] + arr[i+2];
                way[i+2] = i;
            }

            if(i+3 < n && d[i+3] < d[i] + arr[i+3]){
                d[i+3] = d[i] + arr[i+3];
                way[i+3] = i;
            }
        }
        if(d[n-1] == -1){
            System.out.println(-1);
            return;
        }
        System.out.println(d[n-1]);
        List<Integer> path = new ArrayList<>();
        int cur = n - 1;
        while (cur != -1) {
            path.add(cur + 1);
            cur = way[cur];
        }

        Collections.reverse(path);
        for (int p : path) System.out.print(p + " ");
    }

    public static void main(String[] args) {
        start();
    }
}
