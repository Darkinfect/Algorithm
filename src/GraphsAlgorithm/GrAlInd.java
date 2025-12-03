package GraphsAlgorithm;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class GrAlInd {
    public static void main(String[] args) {

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
}
