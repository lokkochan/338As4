package ex4_2;
import java.util.LinkedList;

public class GraphNode{
    private String value;
    private LinkedList<edges> neighbors;

    public GraphNode(String value){
        this.value = value;
        this.neighbors = new LinkedList<edges>();
    }

    public void addNeighbor(GraphNode node, int weight){
        edges neighbor = new edges(node, weight);
        neighbors.add(neighbor);
    }

    public void removeNeighbor(GraphNode node){
        for(edges e : neighbors){
            if(e.getNode() == node){
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

}
