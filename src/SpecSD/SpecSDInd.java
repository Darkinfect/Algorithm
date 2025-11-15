package SpecSD;

import java.io.*;
import java.util.PriorityQueue;

public class SpecSDInd {
    static class Cell implements Comparable<Cell> {
        int row, col, height;
        Cell(int row, int col, int height) {
            this.row = row;
            this.col = col;
            this.height = height;
        }
        public int compareTo(Cell other) {
            return Integer.compare(this.height, other.height);
        }
    }

    private static void start() throws IOException {
        FastScanner scanner = new FastScanner("in.txt");
        PrintWriter out = new PrintWriter("out.txt");

        int n = scanner.nextInt();
        int m = scanner.nextInt();

        int[][] matrix = new int[n][m];
        boolean[][] visited = new boolean[n][m];
        for(int i =0; i < n; i++){
            for(int j =0; j < m; j++){
                matrix[i][j] = scanner.nextInt();
            }
        }
        PriorityQueue<Cell> cells = new PriorityQueue<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (i == 0 || i == n - 1 || j == 0 || j == m - 1) {
                    cells.offer(new Cell(i, j, matrix[i][j]));
                    visited[i][j] = true;
                }
            }
        }

        int[][] dir = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        int total =0;
        int maxHeight = 0;
        while (!cells.isEmpty()) {
            Cell current = cells.poll();
            maxHeight = Math.max(maxHeight, current.height);

            for (int[] dire : dir) {
                int newRow = current.row + dire[0];
                int newCol = current.col + dire[1];

                if (newRow >= 0 && newRow < n && newCol >= 0 && newCol < m &&
                        !visited[newRow][newCol]) {
                    visited[newRow][newCol] = true;

                    int cellHeight = matrix[newRow][newCol];
                    if (cellHeight < maxHeight) {
                        total += maxHeight - cellHeight;
                    }

                    cells.offer(new Cell(newRow, newCol, cellHeight));
                }
            }
        }
        out.println(total);
        out.flush();
        out.close();
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
