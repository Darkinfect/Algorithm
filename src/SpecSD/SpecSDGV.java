package SpecSD;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class SpecSDGV {

        private static List<Integer> list = new ArrayList<>();
        private static Node rootOfTree = null;
        private static int n;
        private static void readFile(){
            try(BufferedReader reader = new BufferedReader(new FileReader("input.txt"))){
                String line1 = reader.readLine().trim();
                n = Integer.parseInt(line1);
                String line2 = reader.readLine().trim();
                if (line2 == null) return;
                String[] str = line2.split("\\s+");
                for(String slr : str){
                    if(!slr.isEmpty()){
                        list.add(Integer.parseInt(slr.trim()));
                    }
                }
            }catch (Exception e){
                e.printStackTrace(System.err);
            }
        }
        private static void writeInList( String line) {
            try (PrintWriter out = new PrintWriter(new FileWriter("output.txt"))) {
                out.println(line);
            } catch (Exception exception) {
                exception.printStackTrace(System.err);
            }
        }
        private static void start(){
            readFile();
            rootOfTree = createTree(0);
            if (rootOfTree == null) {
                writeInList("NO");
                return;
            }
            createBinHeap(rootOfTree);
            writeInList(isHeap(rootOfTree) ? "YES" : "NO");
        }
        private static boolean isHeap(Node root){
            if(root == null) return true;
            if(root.left != null && root.a > root.left.a ){
                return false;
            }
            if(root.right != null && root.a > root.right.a){
                return false;
            }
            return isHeap(root.left) && isHeap(root.right);
        }
        private static void minHeapify(Node root){
            if(root == null) return;
            Node small =root;
            if(root.left != null && root.left.a < small.a){
                small = root.left;
            }
            if(root.right != null && root.right.a < small.a){
                small = root.right;
            }
            if(small != root){
                int temp = root.a;
                root.a= small.a;
                small.a = temp;
                minHeapify(small);
            }
        }
        private static void createBinHeap(Node node){
            if(node == null) return;
            createBinHeap(node.left);
            createBinHeap(node.right);
            minHeapify(node);
        }
        public static void main(String[] args) {
            start();
        }
        private static Node createTree(int i){
            if (i >= list.size()) {
                return null;
            }

            Node node = new Node(list.get(i));
            node.left = createTree( 2 * i + 1);
            node.right = createTree( 2 * i + 2);

            return node;
        }
        static class Node{
            private int a;
            private Node left,right;
            Node(int a){
                this.a = a;
                left = null;
                right = null;
            }
        }
}
