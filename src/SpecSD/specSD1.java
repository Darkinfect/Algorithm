package SpecSD;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class specSD1 {
    private static List<String> list = new ArrayList<>();
    private static void readFile(){
        try{
            Path path = Paths.get(System.getProperty("user.dir") +"//in.txt");
            list = Files.readAllLines(path);
        }catch (Exception e){
            e.printStackTrace(System.err);
        }
    }
    private static void writeInList( List<Integer> res) {
        try (PrintWriter out = new PrintWriter(new FileWriter("out.txt"))) {
            for (Integer a : res) {
                out.println(a);
            }
        } catch (Exception exception) {
            exception.printStackTrace(System.err);
        }
    }
    private static void start(){

    }
    public static void main(String[] args) {

    }
}
