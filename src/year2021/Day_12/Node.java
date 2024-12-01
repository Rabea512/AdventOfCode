package year2021.Day_12;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node {
    public String name;
    public boolean multipleTimesAllowed;
    public List<Node> neighbourNodes;

    public Node(String name, boolean multipleTimesAllowed) {
        this.name = name;
        this.multipleTimesAllowed = multipleTimesAllowed;
        this.neighbourNodes = new ArrayList<Node>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(name, node.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, multipleTimesAllowed, neighbourNodes);
    }

    public String toStringFull() {
        return "Node{" +
                "name='" + name + '\'' +
                ", neighbourNodes=" + neighboursToString() +
                '}';
    }

    @Override
    public String toString() {
        return name;
    }

    private String neighboursToString() {
        final String[] ret = {""};
        neighbourNodes.forEach((n) -> ret[0] += n.name + ", ");
        return ret[0];
    }
}
