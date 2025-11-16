package SpecSD;

import java.io.*;

public class specSD6 {
    private static int n;
    private static long[] fentree;

    private static void start() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        n = Integer.parseInt(reader.readLine());
        fentree = new long[n+1];
        String string = reader.readLine();
        String[] strings = string.split(" ");
        int i = 0;
        for(String str : strings){
            long val = Long.parseLong(str);
            update(i+1,val);
            i++;
        }
        int q = Integer.parseInt(reader.readLine());
        StringBuilder builder = new StringBuilder();

        for(int j = 0; j < q; j++){
            String[] strings1 = reader.readLine().split(" ");
            String target = strings1[0];

            if (target.equals("FindSum")) {
                int l = Integer.parseInt(strings1[1]);
                int r = Integer.parseInt(strings1[2]);
                long sum = query(r) - query(l);
                builder.append(sum).append("\n");
            } else if (target.equals("Add")) {
                int index = Integer.parseInt(strings1[1]);
                long value = Long.parseLong(strings1[2]);
                update(index+1,value);
            }
        }
        System.out.println(builder.toString());
    }
    public static long query(int index) {
        long sum = 0;
        while (index > 0) {
            sum += fentree[index];
            index -= index & (-index);
        }
        return sum;
    }
    public static void update(int index, long delta) {
        while (index <= n) {
            fentree[index] += delta;
            index += index & (-index);
        }
    }
    public static void main(String[] args) throws IOException {
        start();
    }

}
