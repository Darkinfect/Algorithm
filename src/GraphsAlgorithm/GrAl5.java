package GraphsAlgorithm;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class GrAl5 {
    public static void main(String[] args) throws IOException {
      GrAl4.FastScanner scanner = new GrAl4.FastScanner(new FileInputStream("input.txt"));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(new FileOutputStream("output.txt")));

        int n = scanner.nextInt();
        int[] resault = new int[n];
        Arrays.fill(resault,-1);
        int[][] matrix = new int[n][n];
        for(int i =0; i <n; i++){
            for(int j = 0; j < n; j++){
                int l = scanner.nextInt();
                matrix[i][j] = l;
            }
        }
        boolean[] visited = new boolean[n];

        Queue<Integer> queue = new LinkedList<>();
        queue.add(0);
        visited[0] = true;
        resault[0] =1;
        while(!(queue.isEmpty())){
            int curr = queue.poll();
            for(int i = 0; i < n;i++){
                if(matrix[curr][i] ==1 && !visited[i]){
                    visited[i] =true;
                    resault[i] = resault[curr] +2;
                    queue.add(i);
                }
            }
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
