package Treees;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class IndTree {
    private static List<Node> targetNodes = new ArrayList<>();
    private static Node rootOfTree;
    private static int balancedCount;
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
        balancedCount = 0;
        computeHeights(rootOfTree);
        clrFindMiddle(rootOfTree);
        if(targetNodes.size() % 2 ==0){
            clr(rootOfTree);
            writeInList(list);
        }else {
            for(Node node : targetNodes){
                deleteRight(node);
            }
        }
    }
    public static void main(String[] args) {
        start();
    }

    private static void checkNode(Node target){
        if(target.left != null && target.right != null) {
            if (target.left.height != target.right.height) {
                int counleft = calcCount(target.left);
                int countright = calcCount(target.right);
                if (countright == counleft) {
                    targetNodes.add(target);
                }
            }
        }
    }
    private static void deleteRight(Node targetNode){
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
    private static int calcCount(Node node){
        if(node !=null){
            int count = 0;
            Deque<Node> st = new ArrayDeque<>();
            st.push(rootOfTree);
            while (!st.isEmpty()){
                Node curr = st.pop();
                count++;
                if(curr.right != null) st.push(curr.right);
                if(curr.left != null) st.push(curr.left);
            }
        }
        return -1;
    }
    public static void clrFindMiddle(Node node){
        if(node !=null){
            Deque<Node> st = new ArrayDeque<>();
            st.push(rootOfTree);
            while (!st.isEmpty()){
                Node curr = st.pop();
                checkNode(curr);
                if(curr.right != null) st.push(curr.right);
                if(curr.left != null) st.push(curr.left);
            }
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
    private static int computeHeights(Node v) {
        if (v == null) {
            return -1;
        }

        int hLeft = computeHeights(v.left);
        int hRight = computeHeights(v.right);

        v.height = 1 + Math.max(hLeft, hRight);

        if (hLeft == hRight) {
            balancedCount++;
        }

        return v.height;
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
    private static Node findCurr(int target){
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
