package Treees;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class tre3 {
    private static int ver;
    private static void writeInList( String string) {
        try (PrintWriter out = new PrintWriter(new FileWriter("bst.out"))) {
            out.println(string);
        } catch (Exception exception) {
            exception.printStackTrace(System.err);
        }
    }
    public static void start() {
        try(BufferedReader reader = new BufferedReader(new FileReader("bst.in"))){
            ver = Integer.parseInt(reader.readLine());
            Node[] nodes = new Node[ver];
            long num1 = Long.parseLong(reader.readLine());
            nodes[0] = new Node(num1,Long.MIN_VALUE,Long.MAX_VALUE);
            int i = 1;
            String line;
            while((line =reader.readLine()) != null) {
                StringTokenizer tokenizer = new StringTokenizer(line, " ");
                long left,right;
                long num = Long.parseLong(tokenizer.nextToken());
                int cil = Integer.parseInt(tokenizer.nextToken()) - 1;
                String let = tokenizer.nextToken();
                Node parent = nodes[cil];
                if(let.equals("L")){
                    left = parent.minVal;
                    right = parent.numberInCoor;
                }else {
                    left = parent.numberInCoor;
                    right = parent.maxVal;
                }

                if(num < left || num >= right){
                    writeInList("NO");
                    return;
                }
                nodes[i] = new Node(num,left,right);
                i++;
            }
        writeInList("YES");
        }catch(Exception e){
            e.printStackTrace(System.err);
        }
    }
    static class Node{
        private long numberInCoor;
        private long minVal,maxVal;
        Node(long number,long minVal,long maxVal){
            this.numberInCoor = number;
            this.minVal = minVal;
            this.maxVal =maxVal;
        }
    }
    public static void main(String[] args) {
        start();
    }
}