package RecRelation;

import java.nio.charset.StandardCharsets;
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
        List<Integer> dp = new ArrayList<>();
        for(int s: integerList){
            int left = UpperBound(dp,s);
            if(left == dp.size()){
                dp.add(s);
            }else{
                dp.set(left,s);
            }
        }
        writeinlist(Paths.get(System.getProperty("user.dir") + "\\output.txt"),dp.size());
    }
    public static void main(String[] args) {
        start();
    }
    private static int UpperBound(List<Integer> arr, int target){
        int low =0;
        int high = arr.size();
        while(low<high){
            int mid = low + (high - low)/2;
            if(arr.get(mid)<target){
                low = mid + 1;
            }else{
                high= mid;
            }
        }
        return low;
    }
    private static void writeinlist(Path path,Integer res) {
        try {
            Files.write(path, String.valueOf(res).getBytes(StandardCharsets.UTF_8));
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }
}
