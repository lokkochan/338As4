package assignment4.ex4_2;
//Define a Graph class with the following characteristics:
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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
        edges edge = new edges(node1, node2, weight);
        node1.addNeighbor(edge);
        node2.addNeighbor(edge);
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
    public static Graph importFromFile(File file) {
        FileReader fr = null;
        BufferedReader br = null;
    
        if (!file.exists()) {
            System.out.println("File does not exist");
            return null;
        }
    
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
    
            HashMap<String, GraphNode> nodes = new HashMap<String, GraphNode>(); // HashMap to store nodes
            ArrayList<edges> edges = new ArrayList<edges>(); // ArrayList to store edges
    
            String line = null;
            boolean isGraphLine = false;
    
            while ((line = br.readLine()) != null) {
                // Check for graph line
                if (line.startsWith("strict graph")) {
                    isGraphLine = true;
                    continue;
                }
    
                // Check for edge line
                if (isGraphLine && line.contains("--")) {
                    String[] parts = line.split("--");
                    String node1Name = parts[0].trim();
                    String node2Weight = parts[1].trim();
    
                    int weightStart = node2Weight.indexOf('[') + 1;
                    int weightEnd = node2Weight.indexOf(']');
                    String node2Name = node2Weight.substring(0, weightStart - 2).trim();
                    int weight = Integer.parseInt(node2Weight.substring(weightStart+7, weightEnd).trim());
    
                    GraphNode node1 = nodes.get(node1Name);
                    if (node1 == null) {
                        node1 = new GraphNode(node1Name);
                        nodes.put(node1Name, node1);
                    }
    
                    GraphNode node2 = nodes.get(node2Name);
                    if (node2 == null) {
                        node2 = new GraphNode(node2Name);
                        nodes.put(node2Name, node2);
                    }
    
                    edges.add(new edges(node1, node2, weight));
                }
            }
    
            // Create graph object
            Graph graph = new Graph();
            // Iterate over nodes HashMap and add nodes to graph
            for (HashMap.Entry<String, GraphNode> entry : nodes.entrySet()) {
                graph.addNode(entry.getValue());
            }
            // Iterate over edges ArrayList and add edges to graph
            for (edges edge : edges) {
                GraphNode node1 = edge.getNode1();          
                GraphNode node2 = edge.getNode2();
                int weight = edge.getWeight();
                graph.addEdge(node1, node2, weight);
            }        
            return graph;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
                if (fr != null) fr.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }
}    