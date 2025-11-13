package SpecSD;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class specSD4 {
    private static void writeInList(List<Integer> list) {
        try (BufferedWriter out = new BufferedWriter(new FileWriter("output.txt"))) {
           for(Integer i: list){
               out.write(i.toString());
               out.newLine();
           }
        } catch (Exception exception) {
            exception.printStackTrace(System.err);
        }
    }
    public static void start() throws IOException {
            FastScanner scanner= new FastScanner("input.txt");
            int n = scanner.nextInt();
            int q = scanner.nextInt();
            List<Integer> count = new ArrayList<>();
            List<Integer> res = new ArrayList<>();
            count.add(n);
            List<Integer> lead = new ArrayList<>(),size = new ArrayList<>();
            for(int i = 0; i < n; i++){
                size.add(1);
                lead.add(i);
            }
            for(int i =0; i < q; i++){
                int u = scanner.nextInt();
                int v = scanner.nextInt();
                make(count,lead,size,u-1,v-1);
                res.add(count.get(0));
            }
            writeInList(res);
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
