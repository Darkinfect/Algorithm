package Treees;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class IndTree {
    private static Node rootOfTree;
    private static List<Integer> list = new ArrayList<>();
    private static void readFile(){
        try{
            Path path = Paths.get(System.getProperty("user.dir") +"//in.txt");
            list = Files.readAllLines(path).stream().map(Integer::parseInt).distinct().collect(Collectors.toList());
        }catch (Exception e){
            e.printStackTrace(System.err);
        }
    }
    private static void writeInList( List<Integer> res) {
        try (PrintWriter out = new PrintWriter(new FileWriter("out.txt"))) {
            for (Integer a : res) {
                out.println(a);
            }
        } catch (Exception exception) {
            exception.printStackTrace(System.err);
        }
    }

    public static void start(){
        readFile();
        createTree();
        list.clear();
        computeHeights(rootOfTree);
        Node somenode = clrFindMiddle();
        if(somenode !=null){
            deleteRight(somenode);
        }
        clr(rootOfTree);
        writeInList(list);
    }
    public static void main(String[] args) {
        start();
    }

    private static boolean checkNode(Node target){
        if(target == null) return false;
        int heightLeft = (target.left != null) ? target.left.height : -1;
        int heightRight = (target.right != null) ? target.right.height : -1;
        if (heightLeft == heightRight) {
            return false;
        }
        int countLeft = (target.left != null) ? calcCount(target.left) : 0;
        int countRight = (target.right != null) ? calcCount(target.right) : 0;
        return countRight == countLeft;
    }
    private static int calcCount(Node node) {
        if (node != null) {
            int count = 0;
            Deque<Node> st = new ArrayDeque<>();
            st.push(node);
            while (!st.isEmpty()) {
                Node curr = st.pop();
                count++;
                if (curr.right != null) st.push(curr.right);
                if (curr.left != null) st.push(curr.left);
            }
            return count;
        }
        return 0;
    }
    public static Node clrFindMiddle(){
        int count =0;
        if(rootOfTree !=null){
            Deque<Node> st = new ArrayDeque<>();
            st.push(rootOfTree);
            while (!st.isEmpty()){
                Node curr = st.pop();
                if(checkNode(curr)) {
                    count++;
                }
                if(curr.right != null) st.push(curr.right);
                if(curr.left != null) st.push(curr.left);
            }
            if(count % 2 == 0 ) return null;
            int val = (count-1)/2 +1;
            st.push(rootOfTree);
            while (!st.isEmpty()){
                Node curr = st.pop();
                if(checkNode(curr)) val--;
                if(val == 0) {
                    if(checkNode(curr)) {
                        return curr;
                    }else return null;
                }
                if(curr.left != null) st.push(curr.left);
                if(curr.right != null) st.push(curr.right);
            }
        }
        return null;
    }
    public static void clr(Node node){
        if(node !=null){
            Deque<Node> st = new ArrayDeque<>();
            st.push(node);
            while (!st.isEmpty()){
                Node curr = st.pop();
                list.add(curr.a);
                if(curr.right != null) st.push(curr.right);
                if(curr.left != null) st.push(curr.left);
            }
        }
    }

    private static int computeHeights(Node v) {
        if (v == null) {
            return -1;
        }

        int hLeft = computeHeights(v.left);
        int hRight = computeHeights(v.right);

        v.height = 1 + Math.max(hLeft, hRight);

        return v.height;
    }
    private static void deleteRight(Node targetNode){
        if(targetNode == null) {
            return;
        }
        Node res;
        if(targetNode.left == null){
            res = targetNode.right;
            replChild(targetNode.parent,targetNode,res);
        }else if(targetNode.right == null){
            res = targetNode.left;
            replChild(targetNode.parent,targetNode,res);
        }else{
            Node minNodePar = targetNode;
            Node minNode = targetNode.right;
            while(minNode.left != null){
                minNodePar = minNode;
                minNode = minNode.left;
            }
            targetNode.a = minNode.a;
            replChild(minNodePar,minNode,minNode.right);
        }
    }

    private static void replChild(Node par, Node old, Node newCh){
        if(par == null){
            rootOfTree = newCh;
            newCh.parent  =null;
        }else if(par.left== old){
            par.left = newCh;
            newCh.parent = par;
        }else if(par.right == old){
            par.right = newCh;
            newCh.parent = par;
        }
    }
    private static void createTree(){
        rootOfTree = new Node(list.get(0));
        for(int i = 1; i < list.size(); i++){
            int num = list.get(i);
            Node currNode = rootOfTree;
            while(true){
                if(currNode.a <=num && currNode.right == null){
                    Node node1 = new Node(num);
                    node1.parent = currNode;
                    currNode.right = node1;
                    break;
                }else if(currNode.a > num && currNode.left == null){
                    Node node1 = new Node(num);
                    node1.parent = currNode;
                    currNode.left = node1;
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
        private int  height;
        private Node left,right,parent;
        Node(int num){
            this.a = num;
            left =null;
            right =  null;
            height =-1;
        }
    }
}
