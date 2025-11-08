package SpecSD;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class specSD1 {
    private static List<Integer> list = new ArrayList<>();
    private static int n;
    private static void readFile(){
        try(BufferedReader reader = new BufferedReader(new FileReader("input.txt"))){
            String line1 = reader.readLine().trim();
            n = Integer.parseInt(line1);
            String line2 = reader.readLine().trim();
            if (line2 == null) return;
            String[] str = line2.split("\\s+");
            for(String slr : str){
                if(!slr.isEmpty()){
                    list.add(Integer.parseInt(slr.trim()));
                }
            }
        }catch (Exception e){
            e.printStackTrace(System.err);
        }
    }
    private static void writeInList( String line) {
        try (PrintWriter out = new PrintWriter(new FileWriter("output.txt"))) {
            out.println(line);
        } catch (Exception exception) {
            exception.printStackTrace(System.err);
        }
    }
    private static void start(){
        readFile();
        writeInList(isMinHeap(1) ? "YES" : "NO");
    }

    private static boolean isMinHeap(int index){
        int index1= index *2;
        int index2 = index *2 +1;
        boolean left = index1 > list.size() || (list.get(index1 - 1) >= list.get(index - 1) && isMinHeap(index1));
        boolean right = index2 > list.size() || (list.get(index2-1)) >= list.get(index -1) && isMinHeap(index2);
        return left && right;
    }
    public static void main(String[] args) {
        start();
    }
}
