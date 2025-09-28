package RecRelation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class pr06 {
    private static String str;
    private static StringBuilder result = new StringBuilder();
    private static void readFile(){
        try{
            Path path = Paths.get(System.getProperty("user.dir") +"//input.txt");
            str = Files.readAllLines(path).get(0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private static void start() {
        readFile();
        int n = str.length();
        char[] arr = str.toCharArray();

        int[][] sol = new int[n][n];

        for (int i = 0; i < n; i++) {
            sol[i][i] = 1;
        }

        for (int i = 0; i < n - 1; i++) {
            if (str.charAt(i) == str.charAt(i + 1)) {
                sol[i][i + 1] = 2;
            } else {
                sol[i][i + 1] = 1;
            }
        }

        for (int L = 3; L <= n; L++) {
            for (int i = 0; i <= n - L; i++) {
                int j = i + L - 1;
                if (arr[i] == arr[j]) {
                    sol[i][j] = 2 + sol[i + 1][j - 1];
                } else {
                    sol[i][j] = Math.max(sol[i + 1][j], sol[i][j - 1]);
                }
            }
        }
        List<String> sss = new ArrayList<>();
        sss.add(String.valueOf(sol[0][n-1]));
        sss.add(build(str,sol));
        writeinlist(Paths.get(System.getProperty("user.dir") + "//output.txt"),sss);
    }
    private static void writeinlist(Path path,List<String> res) {
        try {
            Files.write(path,res,StandardCharsets.UTF_8);
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }
    private static String build(String s, int[][] sol) {
        int n = s.length();
        StringBuilder sb = new StringBuilder();
        int i = 0, j = n - 1;

        while (i <= j) {
            if (i == j) {
                sb.append(s.charAt(i));
                break;
            }

            if (s.charAt(i) == s.charAt(j)) {
                sb.append(s.charAt(i));
                i++;
                j--;
            } else {
                if (sol[i + 1][j] >= sol[i][j - 1]) {
                    i++;
                } else {
                    j--;
                }
            }
        }
        String left = sb.toString();
        String right = sb.reverse().toString();
        sb.reverse();

        if (sol[0][n - 1] % 2 == 1) {
            return left + right.substring(1);
        } else {
            return left + right;
        }
    }
    public static void main(String[] args) {
        start();
    }
}
