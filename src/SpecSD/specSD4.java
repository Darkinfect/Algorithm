package SpecSD;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class specSD4 {
    private static void writeInList(List<Integer> list) {
        try (BufferedWriter out = new BufferedWriter(new FileWriter("output.txt"))) {
           for(Integer i: list){
               out.write(i.toString());
               out.newLine();
           }
        } catch (Exception exception) {
            exception.printStackTrace(System.err);
        }
    }
    public static void start(){
        try(BufferedReader reader = new BufferedReader(new FileReader("input.txt"))){
            String line1 = reader.readLine();
            String[] str = line1.split(" ");
            int n = Integer.parseInt(str[0]);
            int q = Integer.parseInt(str[1]);
            List<Integer> count = new ArrayList<>();
            List<Integer> res = new ArrayList<>();
            count.add(n);
            List<Integer> lead = new ArrayList<>(),size = new ArrayList<>();
            for(int i = 0; i < n; i++){
                size.add(1);
                lead.add(i);
            }
            for(int i =0; i < q; i++){
                String[] sl = reader.readLine().split("\\s+");
                int u = Integer.parseInt(sl[0]);
                int v = Integer.parseInt(sl[1]);
                make(count,lead,size,u-1,v-1);
                res.add(count.get(0));
            }
            writeInList(res);
        }catch (Exception e){
            e.printStackTrace(System.err);
        }
    }
    private static void make(List<Integer> count, List<Integer> lead, List<Integer> size, int x, int y){
        x = find(lead,x);
        y = find(lead,y);

        if(x != y){
            count.set(0,count.get(0)-1);
            if(size.get(x) < size.get(y)){
                int temp = x;
                x = y;
                y = temp;
            }
            lead.set(y,x);
            size.set(x,size.get(x) + size.get(y));
        }
    }
    private static int find(List<Integer> lead, int a){
      if(a == lead.get(a)){
          return a;
      }
      int root = find(lead,lead.get(a));
      lead.set(a,root);
      return root;
    }
    public static void main(String[] args) {
        start();
    }
}
