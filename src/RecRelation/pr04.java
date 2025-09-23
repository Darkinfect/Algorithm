package RecRelation;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class pr04 {
    private static List<String> list = new ArrayList<>();
    private static List<Integer> iner = new ArrayList<>();
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
            StringTokenizer tokenizer = new StringTokenizer(list.get(i), " ");
            iner.add(Integer.valueOf(tokenizer.nextToken()));
            iner.add(Integer.valueOf(tokenizer.nextToken()));
        }
    }
    private static int power(int base, int exponent) {
        int result = 1;
        while (exponent > 0) {
            if ((exponent & 1) == 1) {
                result = (result * base);
            }
            base = (base * base);
            exponent >>= 1;
        }
        return result;
    }
    private static void start(){
        readFile("input.txt");
        int countMatrix = Integer.parseInt(list.get(0));
        parseMatrixSize();
        int[][] arr = new int[countMatrix][countMatrix];
        for(int i = 0; i < arr.length; i++){
            arr[i][i] = 0;
        }
    }
    public static void main(String[] args) {
        start();
    }
}
