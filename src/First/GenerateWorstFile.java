package First;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class GenerateWorstFile {
    public static void main(String[] args) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter( System.getProperty("user.dir") + "\\input.txt"))) {
            // 3. Еще больше чисел для переполнения
            for (int i = 0; i < 10000; i++) {
                writer.write(String.valueOf(i));
                writer.newLine();
            }

        }
    }
}
//readFile(Paths.get(System.getProperty("user.dir") +"\\input.txt"));
//        Long res = 0L;
//        List<Integer> list = lines.stream().distinct().mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
//        for(int i = 0; i < list.size(); i++){
//            res += list.get(i);
//        }