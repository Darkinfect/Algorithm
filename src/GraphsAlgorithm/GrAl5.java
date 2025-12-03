package GraphsAlgorithm;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class GrAl5 {
    public static void main(String[] args) throws IOException {
        FastScanner scanner = new FastScanner(new FileInputStream("input.txt"));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(new FileOutputStream("output.txt")));

        int n = scanner.nextInt();
        int[][] matrix = new int[n][n];
        for(int i =0; i <n; i++){
            for(int j = 0; j < n; j++){
                int l = scanner.nextInt();
                matrix[i][j] = l;
            }
        }
        int[] res = bfsMatrix(matrix,n);
        for(int i =1; i <= n; i++){
                out.print(res[i]);
                out.print(" ");

        }
        out.flush();
    }
    private static int[] bfsMatrix(int[][] matrix,int n){
        int[] resault = new int[n+1];
        Arrays.fill(resault,0);
        int counter = 0;

        Queue<Integer> queue = new LinkedList<>();
        for(int start =1;start<=n; start++) {
            if(resault[start] ==0) {
                queue.offer(start);
                resault[start] = ++counter;
                while (!queue.isEmpty()) {
                    int curr = queue.poll();
                    for (int i = 1; i <= n; i++) {
                        if (matrix[curr - 1][i - 1] == 1 && resault[i] == 0) {
                            resault[i] = ++counter;
                            queue.offer(i);
                        }
                    }
                }
            }
        }
        return resault;
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
