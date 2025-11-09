package SpecSD;

import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class specSD3 {
    private static long n;
    private static void readFile(Deque<Long> leaves){
        try(BufferedReader reader = new BufferedReader(new FileReader("huffman.in"),262144)){
            String line1 = reader.readLine();
            n = Long.parseLong(line1);
            String line2 = reader.readLine();
            StringTokenizer st = new StringTokenizer(line2);
            while (st.hasMoreTokens()) {
                leaves.offer(Long.parseLong(st.nextToken()));
            }
        }catch (Exception e){
            e.printStackTrace(System.err);
        }
    }
    private static void writeInList(Long in) {
        try (PrintWriter out = new PrintWriter(new FileWriter("huffman.in"))) {
            out.println(in);
        } catch (Exception exception) {
            exception.printStackTrace(System.err);
        }
    }
    private static long min(Deque<Long> leaves,Deque<Long> parents){
        if (leaves.isEmpty()) {
            return parents.poll();
        } else if (parents.isEmpty()) {
            return leaves.poll();
        } else {
            long a = leaves.peek(), b = parents.peek();
            if (a < b) {
                return leaves.poll();
            } else {
                return parents.poll();
            }
        }
    }
//    public static void start(){
//        Deque<Long> leaves = new ArrayDeque<>();
//        Deque<Long> parent = new ArrayDeque<>();
//        readFile(leaves);
//        long lok = 0;
//        for(int i =0; i < n-1; i++){
//            long l = min(leaves,parent);
//            long r = min(leaves,parent);
//            lok += l + r;
//            parent.offer(l+r);
//        }
//        writeInList(lok);
//    }
    public static void start() throws IOException {
        Instant start1 = Instant.now();

        BufferedReader br = new BufferedReader(new FileReader("huffman.in"));
        int n = Integer.parseInt(br.readLine());
        long[] freq = new long[n];

        String[] tokens = br.readLine().split(" ");
        for (int i = 0; i < n; i++) {
            freq[i] = Long.parseLong(tokens[i]);
        }
        br.close();
        Instant end1 = Instant.now();
        Duration duration1 = Duration.between(start1,end1);
        System.out.println(duration1.toMillis() + "мс");
        Instant start2 = Instant.now();

        // Специальный случай: один символ кодируется одним битом
        if (n == 1) {
            PrintWriter pw = new PrintWriter(new FileWriter("huffman.out"));
            pw.println(freq[0]);
            pw.close();
            return;
        }

        // Алгоритм O(n) с двумя указателями
        long result = 0;

        int left = 0;           // Указатель на исходные отсортированные частоты
        int right = 0;          // Указатель на новые созданные узлы
        long[] created = new long[n - 1];  // Массив для новых узлов (их будет n-1)

        // Объединяем n-1 раз (пока не останется один корень)
        for (int i = 0; i < n - 1; i++) {
            // Ищем первый минимум
            long first;
            if (left < n && (right == 0 || freq[left] <= created[right - 1])) {
                first = freq[left];
                left++;
            } else {
                first = created[right];
                right++;
            }

            // Ищем второй минимум
            long second;
            if (left < n && (right == 0 || freq[left] <= created[right - 1])) {
                second = freq[left];
                left++;
            } else {
                second = created[right];
                right++;
            }

            // Создаём новый узел
            long sum = first + second;
            created[i] = sum;

            // Добавляем сумму к результату
            result += sum;
        }
        Instant end2 = Instant.now();
        Duration duration2 = Duration.between(start2,end2);
        System.out.println(duration2.toMillis() + "мс");
        Instant start = Instant.now();

        PrintWriter pw = new PrintWriter(new FileWriter("huffman.out"));
        pw.println(result);
        pw.close();
        Instant end = Instant.now();
        Duration duration = Duration.between(start,end);
        System.out.println(duration.toMillis() + "мс");
    }
    public static void main(String[] args) throws IOException {
        start();
    }
}
