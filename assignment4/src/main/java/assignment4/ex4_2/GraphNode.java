package assignment4.ex4_2;
import java.util.LinkedList;

public class GraphNode implements Comparable<GraphNode>{
    private String value;
    private LinkedList<edges> neighbors;

    public GraphNode(String value){
        this.value = value;
        this.neighbors = new LinkedList<edges>();
    }

    public void addNeighbor(edges edge){
        neighbors.add(edge);
    }

    public void removeNeighbor(GraphNode node){
        for(edges e : neighbors){
            if(e.getNode1() == node||e.getNode2() == node){
                neighbors.remove(e);
                break;
            }
        }
    }
    public LinkedList<edges> getNeighbors(){
        return neighbors;
    }
    public String getValue(){
        return value;
    }
    public int compareTo(GraphNode node){
        return this.value.compareTo(node.getValue());
    }
}
