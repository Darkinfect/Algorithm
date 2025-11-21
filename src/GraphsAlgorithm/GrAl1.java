package GraphsAlgorithm;

import java.io.*;
import java.util.Arrays;

public class GrAl1 {
    public static void main(String[] args) throws IOException {
        FastScanner scanner = new FastScanner(new FileInputStream("input.txt"));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(new FileOutputStream("output.txt")));

        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int[][] matrix = new int[n][n];
        for(int i = 0; i < n; i++){
            Arrays.fill(matrix[i],0);
        }

        for(int i = 0; i < m; i++){
            int k = scanner.nextInt()-1;
            int l = scanner.nextInt()-1;
            matrix[k][l] = 1;
            matrix[l][k] = 1;
        }
        for(int i =0; i < n; i++){
            for(int j =0; j < n; j++){
                out.print(matrix[i][j]);
                out.print(" ");
            }
            out.println();
        }
        out.flush();
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
