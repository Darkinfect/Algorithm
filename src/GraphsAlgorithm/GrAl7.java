package GraphsAlgorithm;

import java.io.*;
import java.util.Comparator;

public class GrAl7 {
    public static void main(String[] args) throws FileNotFoundException {
        FastScanner scanner = new FastScanner(new FileInputStream("input.txt"));
        PrintWriter out = new PrintWriter(new FileOutputStream("output.txt"));
        int n,m;
        n = scanner.nextInt();
        m = scanner.nextInt();

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

        int nextInt() {
            return (int) nextLong();
        }

    }
    static class Top implements Comparator<Top> {
        private int num;
        private long distance;

        public Top(int top, int dist){
            num = top;
            distance = dist;
        }
        @Override
        public int compare(Top top1,Top top2){
            if(top1.distance>top2.distance){
                return 1;
            }else if( top1.distance< top2.distance){
                return -1;
            }else{
                return 0;
            }
        }

    }
}
