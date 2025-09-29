package RecRelation;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class pr07 {
    private static int n = 0;
    private static List<Integer> integerList = new ArrayList<>();
    private static List<String> list = new ArrayList<>();
    private static void readFile(){
        try{
            Path path = Paths.get(System.getProperty("user.dir") +"//input.txt");
            list = Files.readAllLines(path);
            n = Integer.parseInt(list.get(0));
            list.remove(0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private static void start(){
        readFile();
        for(String s: list.get(0).split(" ")){
            integerList.add(Integer.parseInt(s));
        }
        int[] dp = new int[n+1];
        Arrays.fill(dp,Integer.MAX_VALUE);
        for(int i = 0; i < n; i++){
            
        }
    }
    public static void main(String[] args) {
        start();
    }
}
