package assignment4.ex4_2;

public class edges{
    private GraphNode node1,node2;
    private int weight;

    public edges(GraphNode node1, GraphNode node2, int weight){
        this.node1 = node1;
        this.node2 = node2;
        this.weight = weight;
    }

    public GraphNode getNode1(){
        return node1;
    }
    public GraphNode getNode2(){
        return node2;
    }

    public int getWeight(){
        return weight;
    }
}
