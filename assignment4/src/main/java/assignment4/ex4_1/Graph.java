package assignment4.ex4_1;
//Define a Graph class with the following characteristics:
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Graph{
    private ArrayList<GraphNode> nodes;

    public Graph(){
        this.nodes = new ArrayList<GraphNode>();
    }

    public Graph(ArrayList<GraphNode> nodes){
        this.nodes = nodes;
    }

    public GraphNode addNode(GraphNode node){
        nodes.add(node);
        return node;
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
    public GraphNode getNode(String data){
        for(GraphNode node : nodes){
            if(node.getValue().equals(data)){
                return node;
            }
        }
        return null;
    }
    public ArrayList<GraphNode> getNodes(){
        return nodes;
    }
    public static Graph importFromFile(File file){
        //read from graphiz file and return a Graph
        FileReader fr = null;
        BufferedReader br = null;
        try{
            fr= new FileReader(file);
            br = new BufferedReader(fr);
            String line = null;
            Graph graph = null;
            Pattern graphPattern = Pattern.compile("strict graph (.*) \\{");
            Pattern edgePattern = Pattern.compile("(.*) -- (.*) \\[(.*weight=(\\d+))?\\];");

            while ((line = br.readLine()) != null) {
                Matcher graphMatcher = graphPattern.matcher(line);
                Matcher edgeMatcher = edgePattern.matcher(line);

                if (graphMatcher.matches()) {
                    graph = new Graph();
                } else if (edgeMatcher.matches()) {
                    String node1Name = edgeMatcher.group(1).trim();
                    String node2Name = edgeMatcher.group(2).trim();
                    int weight = edgeMatcher.group(4) != null ? Integer.parseInt(edgeMatcher.group(4)) : 1;

                    GraphNode node1 = graph.getNode(node1Name);
                    if (node1 == null) {
                        node1 = new GraphNode(node1Name);
                        graph.addNode(node1);
                    }

                    GraphNode node2 = graph.getNode(node2Name);
                    if (node2 == null) {
                        node2 = new GraphNode(node2Name);
                        graph.addNode(node2);
                    }

                    graph.addEdge(node1, node2, weight);
                } else if (!line.trim().isEmpty()) {
                    if (br!=null) br.close();
                    if (fr!=null) fr.close();
                    // Invalid syntax
                    return null;
                }
            }
            if (br!=null) br.close();
            if (fr!=null) fr.close();  
            return graph;
        } catch (IOException e) {
            // File not found or error reading file
            return null;
        }
    }    
}