import java.util.Vector;

public interface GraphInterface
{
   public void makeEmpty();
   public boolean isEmpty();
   public int numNodes();
   public int numPaths();

   
   public void addVertex(KeyedItem myItem);

   
   public void addEdge(Comparable searchKey1, Comparable searchKey2, double weight);

   
   public void addEdge(Comparable searchKey1, Comparable searchKey2);

   
   public double getWeight(Comparable searchKey1, Comparable searchKey2);

   
   public void removeEdge(Comparable searchKey1, Comparable searchKey2);

   
   public KeyedItem removeVertex(Comparable searchKey);

   
   public KeyedItem getVertex(Comparable searchKey);
   public KeyedItem getVertex(int index);

   
   public Vector dfs(Comparable searchKey);

   
   public Vector bfs(Comparable searchKey);
}