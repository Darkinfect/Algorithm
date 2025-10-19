package Treees;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class tre1 {
    private static Node rootOfTree;
    private static List<Integer> list = new ArrayList<>();
    private static void readFile(){
        try{
            Path path = Paths.get(System.getProperty("user.dir") +"//input.txt");
            list = Files.readAllLines(path).stream().map(Integer::parseInt).distinct().collect(Collectors.toList());
        }catch (Exception e){
            e.printStackTrace(System.err);
        }
    }
    private static void writeInList( List<Integer> res) {
        PrintWriter out = null;
        try {
            out = new PrintWriter(new FileWriter("output.txt"));
            for(Integer a: res){
                out.println(a);
            }
        }catch (Exception exception){
            exception.printStackTrace(System.err);
        }finally {
            if(out != null){
                out.close();
            }
        }
    }
    private static void start(){
        readFile();
        createTree();
        list.clear();
        clr(rootOfTree);
        writeInList(list);
    }
    public static void clr(Node node){
        if(node !=null){
            list.add(node.a);
            clr(node.left);
            clr(node.right);
        }
    }
    public static void createTree(){
        rootOfTree = new Node(list.get(0));
        for(int i = 1; i < list.size(); i++){
            int num = list.get(i);
            Node currNode = rootOfTree;
            while(true){
                if(currNode.a <=num && currNode.right == null){
                    Node node = new Node(num);
                    node.parent = currNode;
                    currNode.right = node;
                    break;
                }else if(currNode.a > num && currNode.left == null){
                    Node node = new Node(num);
                    node.parent = currNode;
                    currNode.left = node;
                    break;
                }
                if(currNode.right != null && currNode.a <= num){
                    currNode = currNode.right;
                    continue;
                }else if(currNode.left != null && currNode.a > num){
                    currNode = currNode.left;
                    continue;
                }
            }
        }
    }
    public static void main(String[] args) {
        start();
    }
    static class Node{
        private int a;
         private Node left,right,parent;
        Node(int a){
            this.a = a;
            this.right =null;
            this.left = null;
        }
    }
}
