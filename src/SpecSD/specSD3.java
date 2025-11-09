package SpecSD;

import java.io.*;
import java.util.*;

public class specSD3 {
    private static int n;
    private static void readFile(Queue<Long> leaves){
        try(BufferedReader reader = new BufferedReader(new FileReader("huffman.in"),262144)){
            String line1 = reader.readLine().trim();
            n = Integer.parseInt(line1);
            String line2 = reader.readLine().trim();
            StringTokenizer st = new StringTokenizer(line2);  // Быстрее split
            while (st.hasMoreTokens()) {
                leaves.offer(Long.parseLong(st.nextToken()));
            }
        }catch (Exception e){
            e.printStackTrace(System.err);
        }
    }
    private static void writeInList(Long in) {
        try (PrintWriter out = new PrintWriter(new BufferedOutputStream(new FileOutputStream("huffman.out"),262144))) {
            out.println(in);
        } catch (Exception exception) {
            exception.printStackTrace(System.err);
        }
    }
    private static long min(Deque<Long> leaves,Deque<Long> parents){
        if (leaves.isEmpty()) {
            return parents.poll();
        } else if (parents.isEmpty()) {
            return leaves.poll();
        } else {
            long a = leaves.peek(), b = parents.peek();
            if (a < b) {
                return leaves.poll();
            } else {
                return parents.poll();
            }
        }
    }
    public static void start(){
        Deque<Long> leaves = new ArrayDeque<>();
        Deque<Long> parent = new ArrayDeque<>();
        readFile(leaves);
        long lok = 0;
        for(int i =0; i < n-1; i++){
            long l = min(leaves,parent);
            long r = min(leaves,parent);
            lok += l + r;
            parent.offer(l+r);
        }
        writeInList(lok);
    }
    public static void main(String[] args) {
        start();
    }
}
