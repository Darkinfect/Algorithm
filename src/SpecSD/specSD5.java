package SpecSD;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class specSD5 {
    private static int[] parent;
    private static int[] rank;
    private static int components;
    public static int find(int i) {
        if (parent[i] == i) {
            return i;
        }
        return parent[i] = find(parent[i]);
    }

    public static void unite(int i, int j) {
        int root_i = find(i);
        int root_j = find(j);
        if (root_i != root_j) {
            if (rank[root_i] > rank[root_j]) {
                parent[root_j] = root_i;
            } else if (rank[root_i] < rank[root_j]) {
                parent[root_i] = root_j;
            } else {
                parent[root_j] = root_i;
                rank[root_i]++;
            }
            components--;
        }
    }
    private static void start() throws IOException {
        FastScanner s = new FastScanner("input.txt");
        PrintWriter out = new PrintWriter("output.txt");

        int n = s.nextInt();
        int m = s.nextInt();
        int q = s.nextInt();

        Edge[] edges = new Edge[m + 1];
        for (int i = 1; i <= m; i++) {
            edges[i] = new Edge(s.nextInt(), s.nextInt());
        }
        parent = new int[n + 1];
        rank = new int[n + 1];
        components = n;
        for (int i = 1; i <= n; i++) {
            parent[i] = i;
            rank[i] = 0;
        }

        int[] earthquakes = new int[q];
        boolean[] isDestroyed = new boolean[m + 1];
        for (int i = 0; i < q; i++) {
            earthquakes[i] = s.nextInt();
            isDestroyed[earthquakes[i]] = true;
        }
        for (int i = 1; i <= m; i++) {
            if (!isDestroyed[i]) {
                unite(edges[i].u, edges[i].v);
            }
        }
        StringBuilder res = new StringBuilder();
        for(int i = q-1; i>=0; i--){
            res.append((components==1) ? '1' : '0');
            int index = earthquakes[i];
            unite(edges[index].u,edges[index].v);
        }
        res.reverse();
        out.println(res.toString());
        out.close();
    }
    public static void main(String[] args) throws IOException {
        start();
    }
    static class Edge {
        int u, v;
        public Edge(int u, int v) {
            this.u = u;
            this.v = v;
        }
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
