import java.util.*;
public class Graph{
  private int Vertex, Edge;
  private List<Edge> edgeList;
  private class Edge implements Comparable<Edge>{
    private int u,v,weight;
    public Edge(int u, int v, int weight){
      this.u = u;
      this.v = v;
      this.weight = weight;
    }
    @Override
    public int compareTo(Edge that){
      return this.weight-that.weight;
    }}
  public Graph(int Vertex, int Edge){
    this.Vertex = Vertex;
    this.Edge = Edge;
    edgeList=new ArrayList<>();}
  public void addEdge(int u, int v, int weight){
    edgeList.add(new Edge(u, v, weight));}
  private int find(int i, int[] parent){
    if(parent[i]==-1){
      return i;
    }
      
    return find(parent[i],parent);}
  private void union(int x, int y, int[] parent){
    int xset =find(x,parent);
    int yset =find(y,parent);
    parent[xset]=yset;}
  public Graph mst(){
    Edge[] edges =edgeList.toArray(new Edge[edgeList.size()]);
    Arrays.sort(edges);
    int[] parent = new int[Vertex];
    for(int i=0;i<Vertex;i++){
      parent[i]=-1;}
    Graph mst =new Graph(Vertex,Vertex-1);
    int i=0;
    int j=0;
    while(i<Vertex-1 && j<Edge){
      Edge edge =edges[j++];
      int u=edge.u;
      int v=edge.v;
      int weight=edge.weight;

      int x =find(u,parent);
      int y =find(v,parent);

      if(x!=y){
        mst.addEdge(u,v,weight);
        union(x,y,parent);
      }
    }
    return mst;}
    public static void main(String[] args){
      Graph g =new Graph(4,5);
      g.addEdge(0,1,10);
      g.addEdge(0,2,6);
      g.addEdge(0,3,5);
      g.addEdge(1,3,15);
      g.addEdge(2,3,4);
      
      Graph mst =g.mst();
      for(int i=0;i<mst.Edge;i++){
        System.out.println(mst.edgeList.get(i).u+" "+mst.edgeList.get(i).v+" "+mst.edgeList.get(i).weight);
      }
    }
  


      
    
    

}