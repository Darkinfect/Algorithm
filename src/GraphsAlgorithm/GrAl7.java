package GraphsAlgorithm;

import java.io.*;
import java.util.Comparator;
import java.util.PriorityQueue;

public class GrAl7 {
    public static void main(String[] args) throws FileNotFoundException {
        FastScanner scanner = new FastScanner(new FileInputStream("input.txt"));
        PrintWriter out = new PrintWriter(new FileOutputStream("output.txt"));

        int n, m;
        n = scanner.nextInt();
        m = scanner.nextInt();

        int edgeCapacity = 2 * m;

        int[] head = new int[n + 1];
        int[] to = new int[edgeCapacity];
        long[] w = new long[edgeCapacity];
        int[] next = new int[edgeCapacity];

        for (int i = 1; i <= n; i++) head[i] = -1;

        int edgePtr = 0;
        for (int i = 0; i < m; i++) {
            int u = scanner.nextInt();
            int v = scanner.nextInt();
            long cost = scanner.nextLong();

            to[edgePtr] = v;
            w[edgePtr] = cost;
            next[edgePtr] = head[u];
            head[u] = edgePtr++;

            to[edgePtr] = u;
            w[edgePtr] = cost;
            next[edgePtr] = head[v];
            head[v] = edgePtr++;
        }

        int start = 1;
        long[] dist = dijkstra(n, head, to, w, next, start);

        final long INF = Long.MAX_VALUE / 4;
        if (dist[n] >= INF) {
            out.println(-1);
        } else {
            out.println(dist[n]);
        }

        out.flush();
        out.close();
    }

    private static long[] dijkstra(int n, int[] head, int[] to, long[] w, int[] next, int start) {
        final long INF = Long.MAX_VALUE / 4;
        long[] dist = new long[n + 1];

        for (int i = 1; i <= n; i++) dist[i] = INF;
        dist[start] = 0;

        PriorityQueue<Top> pq = new PriorityQueue<>(Comparator.comparingLong(t -> t.distance));
        pq.add(new Top(start, 0L));

        while (!pq.isEmpty()) {
            Top cur = pq.poll();
            int v = cur.num;
            long d = cur.distance;

            if (d > dist[v]) continue;

            for (int e = head[v]; e != -1; e = next[e]) {
                int u = to[e];
                long nd = d + w[e];
                if (nd < dist[u]) {
                    dist[u] = nd;
                    pq.add(new Top(u, nd));
                }
            }
        }

        return dist;
    }

    static final class FastScanner {
        private final InputStream in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;

        FastScanner(InputStream is) {
            this.in = is;
        }

        private int read() {
            try {
                if (ptr >= len) {
                    len = in.read(buffer);
                    ptr = 0;
                    if (len <= 0) return -1;
                }
            } catch (Exception e) {
                e.printStackTrace(System.err);
            }
            return buffer[ptr++];
        }

        long nextLong() {
            try {
                int c;
                do {
                    c = read();
                } while (c <= 32);
                boolean neg = false;
                if (c == '-') {
                    neg = true;
                    c = read();
                }
                long x = 0;
                while (c > 32) {
                    x = x * 10 + (c - '0');
                    c = read();
                }
                return neg ? -x : x;
            } catch (Exception e) {
                e.printStackTrace(System.err);
            }
            return -1;
        }

        int nextInt() {
            return (int) nextLong();
        }
    }

    static class Top {
        int num;
        long distance;

        public Top(int top, long dist) {
            num = top;
            distance = dist;
        }
    }
}
