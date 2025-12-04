package GraphsAlgorithm;

import java.io.*;
import java.util.*;

public class GrAlInd {

    public static void main(String[] args) throws FileNotFoundException {
        FastScanner scanner = new FastScanner(new FileInputStream("walk.in"));
        PrintWriter out = new PrintWriter(new FileOutputStream("walk.out"));

        int m = scanner.nextInt();
        int n = scanner.nextInt();

        char[][] lab = new char[m][n];
        int startRow = -1, startCol = -1;
        int exitRow = -1, exitCol = -1;

        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                lab[i][j] = scanner.nextChar();
                if(lab[i][j] == 'M'){
                    startRow = i;
                    startCol = j;
                } else if(lab[i][j] == 'G'){
                    exitRow = i;
                    exitCol = j;
                }
            }
        }

        int result = solve(lab, m, n, startRow, startCol, exitRow, exitCol);
        out.println(result);
        out.close();
    }

    private static int solve(char[][] lab, int M, int N,
                     int startR, int startC, int keyR, int keyC) {
        if (startR == keyR && startC == keyC) {
            return 0;
        }

        int V = M * N;
        int s = startR * N + startC;
        int t = keyR * N + keyC;

        MinCostMaxFlow mcmf = new MinCostMaxFlow(V);

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        for (int r = 0; r < M; r++) {
            for (int c = 0; c < N; c++) {
                if (lab[r][c] == '*') continue;
                int u = r * N + c;
                for (int k = 0; k < 4; k++) {
                    int nr = r + dr[k];
                    int nc = c + dc[k];
                    if (nr < 0 || nr >= M || nc < 0 || nc >= N) continue;
                    if (lab[nr][nc] == '*') continue;
                    int v = nr * N + nc;
                    mcmf.addEdge(u, v);
                }
            }
        }

        long cost = mcmf.minCostMaxFlow(s, t);

        if (cost == Long.MAX_VALUE) {
            return -1;
        } else {
            return (int) cost;
        }
    }

    static class MinCostMaxFlow {
        static class Edge {
            int to, rev;
            int cap;
            int cost;
            Edge(int to, int rev, int cap, int cost) {
                this.to = to;
                this.rev = rev;
                this.cap = cap;
                this.cost = cost;
            }
        }

        private int n;
        private List<Edge>[] g;

        MinCostMaxFlow(int n) {
            this.n = n;
            g = new ArrayList[n];
            for (int i = 0; i < n; i++) {
                g[i] = new ArrayList<>();
            }
        }

        private void addEdge(int u, int v) {
            Edge a = new Edge(v, g[v].size(), 1, 1);
            Edge b = new Edge(u, g[u].size(), 0, -1);
            g[u].add(a);
            g[v].add(b);
        }

        private long minCostMaxFlow(int s, int t) {
            final int INF = Integer.MAX_VALUE / 4;
            int[] dist = new int[n];
            int[] pot = new int[n];
            int[] prevV = new int[n];
            int[] prevE = new int[n];

            int flow = 0;
            long cost = 0;

            Arrays.fill(pot, 0);

            while (flow < 2) {
                Arrays.fill(dist, INF);
                dist[s] = 0;
                PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
                pq.offer(new int[]{s, 0});

                while (!pq.isEmpty()) {
                    int[] cur = pq.poll();
                    int v = cur[0];
                    int d = cur[1];
                    if (d != dist[v]) continue;
                    for (int i = 0; i < g[v].size(); i++) {
                        Edge e = g[v].get(i);
                        if (e.cap <= 0) continue;
                        int nd = d + e.cost + pot[v] - pot[e.to];
                        if (nd < dist[e.to]) {
                            dist[e.to] = nd;
                            prevV[e.to] = v;
                            prevE[e.to] = i;
                            pq.offer(new int[]{e.to, nd});
                        }
                    }
                }

                if (dist[t] == INF) {
                    return Long.MAX_VALUE;
                }

                for (int v = 0; v < n; v++) {
                    if (dist[v] < INF) pot[v] += dist[v];
                }

                int addFlow = 2 - flow;
                int v = t;
                while (v != s) {
                    Edge e = g[prevV[v]].get(prevE[v]);
                    addFlow = Math.min(addFlow, e.cap);
                    v = prevV[v];
                }

                v = t;
                while (v != s) {
                    Edge e = g[prevV[v]].get(prevE[v]);
                    e.cap -= addFlow;
                    g[v].get(e.rev).cap += addFlow;
                    v = prevV[v];
                }

                flow += addFlow;
                cost += (long) addFlow * pot[t];
            }

            return cost;
        }
    }

    static final class FastScanner {
        private final InputStream in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;

        FastScanner(InputStream is){
            this.in = is;
        }

        private int read() {
            try {
                if (ptr >= len) {
                    len = in.read(buffer);
                    ptr = 0;
                    if (len <= 0) return -1;
                }
            }catch (Exception e){
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
            }catch (Exception e){
                e.printStackTrace(System.err);
            }
            return -1;
        }

        char nextChar() {
            try {
                int c;
                do {
                    c = read();
                } while (c <= 32);
                return (char) c;
            } catch (Exception e) {
                e.printStackTrace(System.err);
            }
            return '\0';
        }

        int nextInt() {
            return (int) nextLong();
        }
    }
}
