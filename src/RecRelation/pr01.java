package RecRelation;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class pr01 {
    public static List<String> list = new ArrayList<>();
    private static void readFile(String name, List<String> lines){
        try{
            Path path = Paths.get(System.getProperty("user.dir") +"\\input.txt");
            lines = Files.readAllLines(path);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void start(){
        readFile("input.txt",list);
        StringTokenizer tokenizer = new StringTokenizer(list.get(1)," ");

        List<Integer> integers = new ArrayList<>();
        while(tokenizer.hasMoreTokens()) {
            integers.add(Integer.parseInt(tokenizer.nextToken()));
        }
    }

    public static void main(String[] args) {
        start();
    }
    private static int frog(int n, List<Integer> arr){
        if(n ==0){
            return arr.get(n);
        }
        if(arr.get(n-3) >= arr.get(n-4)){
            return arr.get(n-3);
        }else if(arr.get(n-3) <= arr.get(n-4)){
            return arr.get(n-4);
        }else{
            return 0;
        }
    }
}
