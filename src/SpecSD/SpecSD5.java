package SpecSD;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SpecSD5 {
    private static FastScanner scanner;
    private static void start() throws IOException {
        scanner = new FastScanner("input.txt");
        int n,m,q;
        n = scanner.nextInt();
        m = scanner.nextInt();
        q = scanner.nextInt();
        HashMap<Integer, Integer> roads = new HashMap<>(m);
        for(int i = 0; i < m; i++){
            roads.put(scanner.nextInt(), scanner.nextInt());
        }
        List<Integer> destroyedRoads = new ArrayList<>(q);
        for(int i = 0; i < q; i++){
            destroyedRoads.add(scanner.nextInt());
        }
        List<Boolean> rem = new ArrayList<>(m);
        rem.forEach(sm->{sm=false;});
        for(int i =0; i < m; i++){
            if(!rem.get(i)){
                
            }
        }
    }
    private static void make(List<Integer> count, List<Integer> lead, List<Integer> size, int x, int y){
        x = find(lead,x);
        y = find(lead,y);

        if(x != y){
            count.set(0,count.get(0)-1);
            if(size.get(x) < size.get(y)){
                int temp = x;
                x = y;
                y = temp;
            }
            lead.set(y,x);
            size.set(x,size.get(x) + size.get(y));
        }
    }
    private static int find(List<Integer> lead, int a){
        if(a == lead.get(a)){
            return a;
        }
        int root = find(lead,lead.get(a));
        lead.set(a,root);
        return root;
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
