package year_2021.Day_12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day_12 {
    public static void main(String[] args) {
        System.out.println(part1());

        System.out.println(part2());
    }

    private static List<Node> getSystem() {
        BufferedReader reader;
        List<Node> allNodes = new ArrayList<Node>();
        Node startNode = null;
        Node endNode = null;

        try {
            reader = new BufferedReader(new FileReader("/Users/degroot/Privat/AdventOfCode/src/year_2021.Day_12/input.txt"));
            String line = reader.readLine();
            int i = 0;
            while(line != null) {
                final String[] split = line.split("-");

                Node nodeA = getNode(split[0], allNodes);
                if(nodeA == null) {
                    nodeA = new Node(split[0], !Character.isLowerCase(split[0].charAt(0)));
                    allNodes.add(nodeA);
                }
                Node nodeB = getNode(split[1], allNodes);
                if(nodeB == null) {
                    nodeB = new Node(split[1], !Character.isLowerCase(split[1].charAt(0)));
                    allNodes.add(nodeB);
                }

                if(nodeA.name.equals("start")) {
                    if(startNode == null) {
                        startNode = nodeA;
                    }
                }
                if(nodeB.name.equals("start")) {
                    if(startNode == null) {
                        startNode = nodeB;
                    }
                }
                if(nodeA.name.equals("end")) {
                    if(endNode == null) {
                        endNode = nodeA;
                    }
                }
                if(nodeB.name.equals("end")) {
                    if(endNode == null) {
                        endNode = nodeB;
                    }
                }

                nodeA.neighbourNodes.add(nodeB);
                nodeB.neighbourNodes.add(nodeA);

                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return allNodes;
    }

    private static Node getNode(String name, List<Node> allNodes) {
        for (int i = 0; i < allNodes.size(); i++) {
            if(allNodes.get(i).name.equals(name)) {
                return allNodes.get(i);
            }
        }
        return null;
    }

    private static int part2() {
        final List<Node> system = getSystem();
        List<List<Node>> paths = new ArrayList<>();

        Node startNode = getNode("start", system);
        Node endNode = getNode("end", system);

        getPath2(paths, new ArrayList<>(), startNode, endNode);

        return paths.size();
    }


    private static int part1() {
        final List<Node> system = getSystem();
        List<List<Node>> paths = new ArrayList<>();

        Node startNode = getNode("start", system);
        Node endNode = getNode("end", system);

        getPath(paths, new ArrayList<>(), startNode, endNode);

        return paths.size();
    }

    private static void getPath2(List<List<Node>> paths, List<Node> path, Node startNode, Node endNode) {
        path.add(startNode);

        if(startNode == endNode) {
            paths.add(List.copyOf(path));
            return;
        }

        for (int i = 0; i < startNode.neighbourNodes.size(); i++) {
            final Node nextNode = startNode.neighbourNodes.get(i);

            if(isAllowed(path, nextNode)) {
                List<Node> freshPath = new ArrayList<Node>(path);
                getPath2(paths, freshPath, nextNode,endNode);
            } else {
                if(canBeAllowed(path, nextNode)) {
                    List<Node> freshPath = new ArrayList<Node>(path);
                    getPath2(paths, freshPath, nextNode,endNode);
                }
            }
        }

    }

    private static boolean canBeAllowed(List<Node> path, Node nextNode) {
        boolean isOneTwice = false;

        if(nextNode.name.equals("start")) {
            return false;
        }

        if(nextNode.multipleTimesAllowed) {
            return true;
        }

        for (Node n : path) {
            if(!n.multipleTimesAllowed) {
                final List<Node> copy = new ArrayList<Node>(path);
                copy.remove(n);
                if (copy.contains(n)) {
                    isOneTwice = true;
                }
            }
        }

        return !isOneTwice;
    }

    private static void getPath(List<List<Node>> paths, List<Node> path, Node startNode, Node endNode) {
        path.add(startNode);
        if(startNode == endNode) {
            paths.add(List.copyOf(path));
            return;
        }

        for (int i = 0; i < startNode.neighbourNodes.size(); i++) {
            final Node nextNode = startNode.neighbourNodes.get(i);
            if(isAllowed(path, nextNode)) {
                List<Node> freshPath = new ArrayList<Node>(path);
                getPath(paths, freshPath, nextNode,endNode);
            }
        }
    }

    private static boolean isAllowed(List<Node> path, Node node) {
        return node.multipleTimesAllowed || !path.contains(node);
    }
}
