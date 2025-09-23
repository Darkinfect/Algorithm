package RecRelation;
import java.util.Scanner;

public class pr03 {
    private static final int module = 1000000007;
    private static long[] fact = null;
    private static long[] invFact = null;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int k = scanner.nextInt();
        if(k == 0){
            System.out.println("1");
            return;
        }
        fact = new long[n+1];
        invFact = new long[n+1];
        precomputeFactorials(Math.max(n, 1));

        long result = (fact[n] * invFact[k]) % module;
        result = (result * invFact[n - k]) % module;
        System.out.println(result);
    }
    private static void precomputeFactorials(int n) {

        fact[0] = 1;
        for (int i = 1; i <= n; i++) {
            fact[i] = (fact[i - 1] * i) % module;
        }

        invFact[n] = modInverse(fact[n]);
        for (int i = n - 1; i >= 0; i--) {
            invFact[i] = (invFact[i + 1] * (i + 1)) % module;
        }
    }
    private static long modInverse(long a) {
        return power(a, module - 2);
    }
    private static long power(long base, long exponent) {
        long result = 1;
        base %= module;
        while (exponent > 0) {
            if ((exponent & 1) == 1) {
                result = (result * base) % module;
            }
            base = (base * base) % module;
            exponent >>= 1;
        }
        return result;
    }
}