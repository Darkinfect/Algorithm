package SpecSD;

import java.io.*;
import java.util.Arrays;

public class specSD7 {
    private static void start() throws IOException {
        FastScanner scanner = new FastScanner(new FileInputStream("input.txt"));
        PrintWriter out = new PrintWriter(new FileWriter("output.txt"));

        int m = scanner.nextInt();
        int c = scanner.nextInt();
        int n = scanner.nextInt();

        int[] table = new int[m];
        Arrays.fill(table,-1);
        for(int i =0; i< n;i++){
            int x = scanner.nextInt();
            int base = x % m;
            if (base < 0) base += m;

            int pos = base;
            for(int j = 0; j< m; j++){
                int cur = table[pos];
                if(cur ==x) break;
                if(cur ==-1){
                    table[pos] =x;
                    break;
                }
                pos+=c;
                if(pos>=m) pos -= m;
            }
        }
        for (int i = 0; i < m; i++) {
            if (i > 0) out.print(' ');
            out.print(table[i]);
        }
        out.println();
        out.flush();
    }
    public static void main(String[] args) throws IOException {
        start();
    }
    static final class FastScanner {
        private final InputStream in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;

        FastScanner(InputStream is) throws FileNotFoundException {
            this.in = is;
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
