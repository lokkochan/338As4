// Date: 2018-03-28
/*part a
the slow way is approaching by check all nodes and find the node with the smallest distance from the source node
the fast way is using a priority queue to store the nodes that have been visited and the distance from the source node
*/

package ex4_2;
import matplotlib4j.Plot;
import java.util.ArrayList;
import java.util.HashMap;

public class DijkstraAlog {
    private Graph graph;
    public ArrayList<String> DijkstraAlgorithm(Graph graph){
        ArrayList<String> path = new ArrayList<String>();



        return path;
    }

    private HashMap<GraphNode,Integer> slowSP(GraphNode source,ArrayList<Integer> time){
        HashMap<GraphNode,Integer> distance = new HashMap<GraphNode,Integer>();
        ArrayList<GraphNode> unvisited = new ArrayList<GraphNode>();
        for(GraphNode node : this.graph.getNodes()){
            distance.put(node, Integer.MAX_VALUE);
            unvisited.add(node);
        }
        distance.put(source, 0);
        while(unvisited.size() > 0){
            GraphNode node = getMinDistanceNode(unvisited, distance);
            unvisited.remove(node);
            for(edges e : node.getNeighbors()){
                GraphNode neighbor = e.getNode();
                int weight = e.getWeight();
                if(distance.get(neighbor) > distance.get(node) + weight){
                    distance.put(neighbor, distance.get(node) + weight);
                }
            }
        }
        return distance;
    }

    private GraphNode getMinDistanceNode(ArrayList<GraphNode> unvisited, HashMap<GraphNode,Integer> distance){
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
}
