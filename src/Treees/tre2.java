package Treees;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

public class tre2 {
    private static Node rootOfTree;
    private static List<Integer> list = new ArrayList<>();
    private static int target;
    private static void readFile(){
        try{
            Path path = Paths.get(System.getProperty("user.dir") +"//input.txt");
            List<String> currFile = new ArrayList<>();
            currFile = Files.readAllLines(path);
            target = Integer.parseInt(currFile.get(0));
            currFile.remove(0);
            currFile.remove(0);
            list = currFile.stream().map(Integer::parseInt).distinct().collect(Collectors.toList());
        }catch (Exception e){
            e.printStackTrace(System.err);
        }
    }
    private static void writeInList( List<Integer> res) {
        try (PrintWriter out = new PrintWriter(new FileWriter("output.txt"))) {
            for (Integer a : res) {
                out.println(a);
            }
        } catch (Exception exception) {
            exception.printStackTrace(System.err);
        }
    }
    private static void start(){
        readFile();
        createTree();
        list.clear();
        deleteRight();
        writeInList(list);
    }
    private static void deleteRight(){
        Node targetNode = findCurr();
        if(targetNode == null) {
            clr(rootOfTree);
            return;
        }
        Node res;
        if(targetNode.left == null){
            res = targetNode.right;
        }else if(targetNode.right == null){
            res = targetNode.left;
        }else{
            Node minNodePar = targetNode;
            Node minNode = targetNode.right;
            while(minNode.left != null){
                minNodePar = minNode;
                minNode = minNode.left;
            }
            targetNode.a = minNode.a;
            replChild(minNodePar,minNode,minNode.right);
            clr(rootOfTree);
            return;
        }
        replChild(targetNode.parent,targetNode,res);
        clr(rootOfTree);
    }
    private static void replChild(Node par, Node old, Node newCh){
        if(par == null){
           rootOfTree = newCh;
        }else if(par.left== old){
            par.left = newCh;
        }else if(par.right == old){
            par.right = newCh;
        }
    }
    public static void clr(Node node){
        if(node !=null){
            Deque<Node> st = new ArrayDeque<>();
            st.push(rootOfTree);
            while (!st.isEmpty()){
                Node curr = st.pop();
                list.add(curr.a);
                if(curr.right != null) st.push(curr.right);
                if(curr.left != null) st.push(curr.left);
            }
        }
    }
    private static Node findCurr(){
        Node currNode = rootOfTree;
        while(currNode != null){
            if(currNode.a == target){
                return currNode;
            }else if(currNode.a < target){
                currNode = currNode.right;
            }else {
                currNode = currNode.left;
            }
        }
        return null;
    }
    private static void createTree(){
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
    static class Node{
        private int a;
        private Node left,right,parent;
        Node(int a){
            this.a = a;
            this.right =null;
            this.left = null;
        }
    }
    public static void main(String[] args) {
        start();
    }
}
