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
        System.out.println("Average of slow time: "+average(slowTime));
        System.out.println("Average of fast time: "+average(fastTime));
        System.out.println("the max time of slow way is: "+max(slowTime));
        System.out.println("the max time of fast way is: "+max(fastTime));
        System.out.println("the min time of slow way is: "+min(slowTime));
        System.out.println("the min time of fast way is: "+min(fastTime));

        //plot the graph
        try {
            Plot plt = Plot.create();
            plt.plot().add(slowTime).label("slow").linestyle("--");
            plt.plot().add(fastTime).label("fast").linestyle("-");
            plt.ylabel("time");
            plt.title("time vs nodes");
            plt.legend();
            plt.show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (PythonExecutionException e){
            e.printStackTrace();
        }
    }

    private static void slowSP(GraphNode source, Graph graph){
        HashMap<GraphNode,Integer> distance = new HashMap<GraphNode,Integer>();
        ArrayList<GraphNode> unvisited = new ArrayList<GraphNode>();
        for(GraphNode node : graph.getNodes()){
            distance.put(node, Integer.MAX_VALUE);
            unvisited.add(node);
        }
        distance.put(source, 0);
        // main loop
        for (int i = 0; i < graph.getNodes().size(); i++) {
            // find the unvisited node with the lowest cost (inefficient)
            double lowestCost = Double.POSITIVE_INFINITY;
            GraphNode currentNode = null;
            for (GraphNode node : graph.getNodes()) {
                if (unvisited.contains(node) && distance.get(node) < lowestCost) {
                    lowestCost = distance.get(node);
                    currentNode = node;
                }
            }
            if (currentNode == null) {
                // there is no path to the remaining unvisited nodes
                break;
            }
            // remove the current node from the unvisited list
            unvisited.remove(currentNode);
            // update the costs of the neighbors
            for (edges edge : currentNode.getNeighbors()) {
                GraphNode neighbor = edge.getNode1() == currentNode ? edge.getNode2() : edge.getNode1();
                double newCost = distance.get(currentNode) + edge.getWeight();
                if (newCost < distance.get(neighbor)) {
                    distance.put(neighbor, (int) newCost);
                }
            }
        }
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

    private static void fastSP(GraphNode source, Graph graph){
        HashMap<GraphNode,Integer> distance = new HashMap<GraphNode,Integer>();
        ArrayList<GraphNode> unvisited = new ArrayList<GraphNode>();
        // initialization
        for (GraphNode node : graph.getNodes()) {
            distance.put(node, Integer.MAX_VALUE);
            unvisited.add(node);
        }
        distance.put(source, 0);
        PriorityQueue<GraphNode> pq = new PriorityQueue<>((n1, n2) -> Double.compare(distance.get(n1), distance.get(n2)));
        pq.offer(source);
        // main loop
        while (!pq.isEmpty()) {
            // extract the node with the lowest cost (efficient)
            GraphNode currentNode = pq.poll();
            if (!unvisited.contains(currentNode)) {
                continue;
            }
            // remove the current node from the unvisited list
            unvisited.remove(currentNode);
            // update the costs of the neighbors
            for (edges edge : currentNode.getNeighbors()) {
                GraphNode neighbor = edge.getNode1() == currentNode ? edge.getNode2() : edge.getNode1();
                double newCost = distance.get(currentNode) + edge.getWeight();
                if (newCost < distance.get(neighbor)) {
                    distance.put(neighbor, (int) newCost);
                    pq.offer(neighbor);
                }
            }
        }
    }

    private static double average(ArrayList<Long> list){
        double sum = 0;
        for(long i : list){
            sum += i;
        }
        return sum/list.size();
    }

    private static long max(ArrayList<Long> list){
        long max = 0;
        for(long i : list){
            if(i > max){
                max = i;
            }
        }
        return max;
    }

    private static long min(ArrayList<Long> list){
        long min = list.get(0);
        for(long i : list){
            if(i < min){
                min = i;
            }
        }
        return min;
    }
}
