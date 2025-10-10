package RecRelation;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class pr08 {
    private static List<String> list = new ArrayList<>();
    private static void readFile(){
        try{
            Path path = Paths.get(System.getProperty("user.dir") +"//input.txt");
            list = Files.readAllLines(path);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        start();
    }
    private static void start() {
        readFile();
        int D,I,R;
        D = Integer.parseInt(list.get(0));
        I = Integer.parseInt(list.get(1));
        R = Integer.parseInt(list.get(2));
        char[] str1 = list.get(3).toCharArray();
        char[] str2 = list.get(4).toCharArray();
        int[][] sol = new int[str1.length+1][str2.length+1];
        sol[0][0] = 0;
        for(int i =0; i < str1.length+1;i++){
            sol[i][0] =i*D;
        }
        for(int i =0; i < str2.length+1;i++){
            sol[0][i] =i*I;
        }
        for(int i =1; i < str1.length+1; i++){
            for(int j = 1; j < str2.length+1; j++){
                sol[i][j] = Math.min(sol[i-1][j-1] + b(str1[i-1],str2[j-1])*R, Math.min(sol[i-1][j]+D,sol[i][j-1]+I));
            }
        }
        writeinlist(Paths.get(System.getProperty("user.dir") + "out.txt"),sol[str1.length][str2.length]);
    }
    private static int b(char a, char b){
        if(a ==b){
            return 0;
        }else{
            return 1;
        }
    }
    private static void writeinlist(Path path,Integer res) {
        try {
            Files.write(path, String.valueOf(res).getBytes(StandardCharsets.UTF_8));
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }
}
