package ex4_2;

public class edges{
    private GraphNode node;
    private int weight;

    public edges(GraphNode node, int weight){
        this.node = node;
        this.weight = weight;
    }

    public GraphNode getNode(){
        return node;
    }

    public int getWeight(){
        return weight;
    }
}
