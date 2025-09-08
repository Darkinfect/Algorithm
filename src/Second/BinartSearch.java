package Second;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BinartSearch {
    private static List<String> list = new ArrayList<>();
    private static void readFile(String name, List<String> lines){
        try{
            Path path = Paths.get(System.getProperty("user.dir") +"\\input.txt");
            lines = Files.readAllLines(path);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void start(){
        readFile("input.txt",list);
        Integer value = Integer.parseInt(list.get(0));
        String ignore = new String(" ");
        List<Integer> list1 = Arrays.asList(list.get(1).split(ignore)).stream().mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());

    }
}
