package First;

import java.lang.invoke.StringConcatFactory;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

public class Par {
    public static List<String> list = new ArrayList<>();
    private static void readFile(String name, List<String> lines){
        try{
            Path path =Paths.get(System.getProperty("user.dir") +"\\input.txt");
            lines = Files.readAllLines(path);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private static void writeinlist(Path path,Long res) {
        try {
            Files.write(path, String.valueOf(res).getBytes(StandardCharsets.UTF_8));
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }
    private static void writeinlist(Path path,Integer res) {
        try {
            Files.write(path, String.valueOf(res).getBytes(StandardCharsets.UTF_8));
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }
    public static void start(){
        readFile("input.txt",list);
        Long res = list.stream().distinct().mapToLong(Long::parseLong).boxed().reduce(0L, Long::sum);
        writeinlist(Paths.get(System.getProperty("user.dir") +"\\output.txt"),res);
    }
    public static void main(String[] args){
        start();
    }
}
