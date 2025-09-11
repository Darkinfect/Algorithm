package Second;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BinartSearch {
    private static int valuenumbers;
    private static List<String> list = new ArrayList<>();
    private static void readFile(String name, List<String> lines){
        try{
            Path path = Paths.get(System.getProperty("user.dir") +"\\input.txt");
            lines = Files.readAllLines(path);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private static int[] getArray(List<Integer> massive, int first, int last){
        for (int i = 0; i < massive.size(); i++){
            if(massive.get(i).equals(first)){
            }
        }
        return null;
    }
    private static int binSearch(List<Integer> massive, int target){
        int min, max, index;
        max = massive.getLast();
        min = massive.getFirst();
        if(target > max || target < min){
            return valuenumbers;
        }
        while(true){
            index = max+min / 2;
            if(massive.get(index) == target){
                return index;
            }
            if(target >= massive.get(index)){;
                min = massive.get(index);
                continue;
            }
            if(target<= massive.get(index)){
                max = massive.get(index);
                continue;
            }
        }
    }
    public static void start(){
        readFile("input.txt",list);
        valuenumbers = Integer.parseInt(list.get(0));
        String ignore = new String(" ");
        List<Integer> list1 = Arrays.asList(list.get(1).split(ignore)).stream().mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
        int k = Integer.parseInt(list.get(2));
        List<Integer> objforsearch = Arrays.asList(list.get(3).split(ignore)).stream().mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
        System.out.println(binSearch(list1,1));
    }
}
