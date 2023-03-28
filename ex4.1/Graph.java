//Define a Graph class with the following characteristics:
//a. Can be used to describe an undirected graph
//b. Internally stores edges as an adjacency list for each node
//c. Allow weight for each edge
import javafx.util.Pair;

public class GraphNode{
    private String value;
    private ArrayList<Pair<GraphNode, Integer>> neighbors;

    public GraphNode(String value){
        this.value = value;
        neighbors = new ArrayList<Pair<GraphNode, Integer>>();
    }

    public void addNeighbor(GraphNode node, int weight){
        Pair neighbor = new Pair(node, weight);
        neighbors.add(neighbor);
    }

    public void removeNeighbor(GraphNode node){
        for(int i = 0; i < neighbors.size(); i++){
            if(neighbors.get(i).getKey() == node){
                neighbors.remove(i);
                break;
            }
        }
    }

}


public class Graph{
    private ArrayList<GraphNode> nodes;

    public Graph(GraphNode[] nodes){
        this.nodes = nodes;
    }

    public GraphNode addNode(String data){
        GraphNode node = new GraphNode(data);
        nodes.add(node);
        return node;
    }

    public void addEdge(GraphNode node1, GraphNode node2, int weight){
        node1.addNeighbor(node2, weight);
        node2.addNeighbor(node1, weight);
    }

    public void remoevEdge(GraphNode node1, GraphNode node2){
        node1.removeNeighbor(node2);
        node2.removeNeighbor(node1);
    }

    public void removeNode(GraphNode node){
        for(GraphNode n : nodes){
            n.removeNeighbor(node);
        }
        nodes.remove(node);
    }
}