package SpecSD;

import java.io.*;
import java.util.*;

public class specSD3 {
    private static int n;
    private static void readFile(Queue<Long> leaves){
        try{
            FastScanner scanner = new FastScanner("huffman.in");
            n = scanner.nextInt();
            for(int i =0; i < n; i++){
                leaves.offer(scanner.nextLong());
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
    public static void main(String[] args) throws IOException {
        start();
    }
    static final class FastScanner {
        private final InputStream in;
        private final byte[] buffer = new byte[1 << 20];
        private int ptr = 0, len = 0;

        FastScanner(String file) throws FileNotFoundException {
            this.in = new BufferedInputStream(new FileInputStream(file), 1 << 20);
        }

        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;
                if (len <= 0) return -1;
            }
            return buffer[ptr++];
        }

        long nextLong() throws IOException {
            int c;
            do { c = read(); } while (c <= 32);
            boolean neg = false;
            if (c == '-') { neg = true; c = read(); }
            long x = 0;
            while (c > 32) {
                x = x * 10 + (c - '0');
                c = read();
            }
            return neg ? -x : x;
        }

        int nextInt() throws IOException {
            return (int) nextLong();
        }
    }
}
