package SpecSD;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class specSD2 {
    private static long n;
    private static void readFile(){
        try(BufferedReader reader = new BufferedReader(new FileReader("input.txt"))){
            String line1 = reader.readLine().trim();
            n = Long.parseLong(line1);
        }catch (Exception e){
            e.printStackTrace(System.err);
        }
    }
    private static void writeInList(List<Integer> integerList) {
        try (PrintWriter out = new PrintWriter(new FileWriter("output.txt"))) {
            for(Integer i: integerList){
                out.println(i);
            }
        } catch (Exception exception) {
            exception.printStackTrace(System.err);
        }
    }

    private static void start(){
        readFile();
        List<Integer> list = new ArrayList<>();
        if(n == 0) {
            list.add(-1);
        }else {
            int i = 0;
            while(n>0){
                long a = n & 1;
                if(a ==1){
                    list.add(i);
                }
                n /= 2;
                i++;
            }
        }
        writeInList(list);
    }
    public static void main(String[] args) {
        start();
    }
}
