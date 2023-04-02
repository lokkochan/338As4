// Date: 2018-03-28
/*part a
the slow way is approaching by check all nodes and find the node with the smallest distance from the source node
the fast way is using a priority queue to store the nodes that have been visited and the distance from the source node
*/

package assignment4.ex4_2;
import java.io.IOException;
import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.io.File;

public class DijkstraAlog {
    
    public static void main(String[] args) throws IOException, PythonExecutionException{
        
        File file = new File("random.dot");
        Graph graph=Graph.importFromFile(file);
        ArrayList<Long> slowTime = new ArrayList<Long>();
        ArrayList<Long> fastTime = new ArrayList<Long>();
        ArrayList<Integer> nodes = new ArrayList<Integer>();
        if (graph == null) {
            System.out.println("graph is null");
        }
        for(GraphNode node : graph.getNodes()){
            nodes.add(node.getNeighbors().size());
            long startTime = System.nanoTime();
            slowSP(node, graph);
            long endTime = System.nanoTime();
            slowTime.add(endTime - startTime);
            startTime = System.nanoTime();
            fastSP(node, graph);
            endTime = System.nanoTime();
            fastTime.add(endTime - startTime);
        }
        //plot the graph
        try {
            Plot plt = Plot.create();
            plt.plot().add(nodes, slowTime).label("slow").linestyle("--");
            plt.plot().add(nodes, fastTime).label("fast").linestyle("-");
            plt.ylabel("time");
            plt.title("time vs number of nodes");
            plt.legend();
            plt.show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (PythonExecutionException e){
            e.printStackTrace();
        }
    }

    private static HashMap<GraphNode,Integer> slowSP(GraphNode source, Graph graph){
        HashMap<GraphNode,Integer> distance = new HashMap<GraphNode,Integer>();
        ArrayList<GraphNode> unvisited = new ArrayList<GraphNode>();
        for(GraphNode node : graph.getNodes()){
            distance.put(node, Integer.MAX_VALUE);
            unvisited.add(node);
        }
        distance.put(source, 0);
        while(unvisited.size() > 0){
            GraphNode node = getMinDistanceNode(unvisited, distance);
            unvisited.remove(node);
            for(edges e : node.getNeighbors()){
                GraphNode neighbor = e.getNode1() == node ? e.getNode2() : e.getNode1();
                int weight = e.getWeight();
                if(distance.get(neighbor) > distance.get(node) + weight){
                    distance.put(neighbor, distance.get(node) + weight);
                }
            }
        }
        return distance;
    }

    private static GraphNode getMinDistanceNode(ArrayList<GraphNode> unvisited, HashMap<GraphNode,Integer> distance){
        GraphNode minNode = null;
        int minDistance = Integer.MAX_VALUE;
        for(GraphNode node : unvisited){
            int nodeDistance = distance.get(node);
            if(nodeDistance < minDistance){
                minDistance = nodeDistance;
                minNode = node;
            }
        }
        return minNode;
    }

    private static HashMap<GraphNode,Integer> fastSP(GraphNode source, Graph graph){
        HashMap<GraphNode,Integer> distance = new HashMap<GraphNode,Integer>();
        ArrayList<GraphNode> unvisited = new ArrayList<GraphNode>();
        for(GraphNode node : graph.getNodes()){
            distance.put(node, Integer.MAX_VALUE);
            unvisited.add(node);
        }
        //use the priority queue to store the nodes that have been visited
        PriorityQueue<GraphNode> queue = new PriorityQueue<GraphNode>();
        distance.put(source, 0);
        queue.add(source);
        while(unvisited.size() > 0){
            GraphNode node = queue.poll();
            unvisited.remove(node);
            for(edges e : node.getNeighbors()){
                GraphNode neighbor = e.getNode1() == node ? e.getNode2() : e.getNode1();
                int weight = e.getWeight();
                if(distance.get(neighbor) > distance.get(node) + weight){
                    distance.put(neighbor, distance.get(node) + weight);
                    queue.add(neighbor);
                }
            }
        }
        return distance;
    }

}
