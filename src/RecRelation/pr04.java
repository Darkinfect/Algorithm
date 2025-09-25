package RecRelation;

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
        for(int i = 0; i < arr.length; i++){
            arr[i][i] = 0;
            if(i +1 == arr.length) break;
            arr[i][i+1] = iner.get(i).get(0) * iner.get(i).get(1) * iner.get(i+1).get(1);
        }
    }
    public static void main(String[] args) {
        start();
    }
}
