package RecRelation;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class pr04 {
    private static List<String> list = new ArrayList<>();
    private static List<List<Integer>> iner = new ArrayList<>();
    private static int[][] ss;
    private static void readFile(String name){
        try{
            Path path = Paths.get(System.getProperty("user.dir") +"//input.txt");
            list = Files.readAllLines(path);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private static void parseMatrixSize(){
        for(int i =1; i < list.size(); i++){
            List<Integer> arr = new ArrayList<>(2);
            StringTokenizer tokenizer = new StringTokenizer(list.get(i), " ");
            arr.add(Integer.parseInt(tokenizer.nextToken()));
            arr.add(Integer.parseInt(tokenizer.nextToken()));
            iner.add(arr);
        }
    }
    private static void start(){
        readFile("input.txt");
        int countMatrix = Integer.parseInt(list.get(0));
        parseMatrixSize();
        int[][] arr = new int[countMatrix][countMatrix];
        for(int i = 0; i < arr.length-1; i++) {
            arr[i][i] = 0;
            arr[i][i + 1] = iner.get(i).get(0) * iner.get(i).get(1) * iner.get(i + 1).get(1);
        }
        for(int len = 2; len < countMatrix; len++) {
            for(int i = 0; i < countMatrix - len; i++) {
                int j = i + len;
                arr[i][j] = Integer.MAX_VALUE;

                for(int k = i; k < j; k++) {
                    int cost = arr[i][k] + arr[k + 1][j] +
                            iner.get(i).get(0) * iner.get(k).get(1) * iner.get(j).get(1);
                    arr[i][j] = Math.min(arr[i][j], cost);
                }
            }
        }
        writeinlist(Paths.get(System.getProperty("user.dir") +"//output.txt"), arr[0][arr.length-1]);
    }
    private static void writeinlist(Path path,int res) {
        try {
            Files.write(path, String.valueOf(res).getBytes(StandardCharsets.UTF_8));
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }
    public static void main(String[] args) {
        start();
    }
}
