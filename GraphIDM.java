import java.util.Vector;
@SuppressWarnings("unchecked")// uses to remove any unchecked during compilation
public class GraphIDM implements GraphInterface
{
   private int arraySize, initialSize;
   private double[][] adjMatrix;
   private KeyedItem[] items;
   private int numNodes;
   private Vector nodeSet;
   private int numPaths;

   public GraphIDM(int initial)
   {
      arraySize = initial;
      initialSize = initial;
      adjMatrix = new double[arraySize][arraySize];
      items = new KeyedItem[arraySize];
      numNodes = 0;
      numPaths = 0;
      nodeSet = new Vector();
      for (int x=0; x<arraySize; x++)
         for (int y=0; y<arraySize; y++)
            adjMatrix[x][y] = Double.POSITIVE_INFINITY;
   }

   public GraphIDM()
   {
      this(50); 
   }

   public void addVertex(KeyedItem newItem) throws GraphException
   {
      
      if (numNodes == arraySize)
         doubleArray();
      
      if (findItemIndex(newItem.getKey()) == -1)
      {
         int putIndex = getNullIndex();
         items[putIndex] = newItem;
         adjMatrix[putIndex][putIndex] = 0; 
         numNodes++;
      }
      else
         throw new GraphException("Elements exists!");
   }

   public KeyedItem removeVertex(Comparable searchKey)
   {
      int deleteIndex = findItemIndex(searchKey);
      KeyedItem removeItem;
      
      if (deleteIndex == -1)
         return null;

      for (int x=0; x<arraySize; x++)
      {
         adjMatrix[deleteIndex][x] = Double.POSITIVE_INFINITY;
         adjMatrix[x][deleteIndex] = Double.POSITIVE_INFINITY;
      }

      removeItem = items[deleteIndex];      
      items[deleteIndex] = null;
      numNodes--;

      return removeItem;
   }

   public void addEdge(Comparable startVert, Comparable endVert, double cost)
   {
      int startIndex = findItemIndex(startVert);
      int endIndex = findItemIndex(endVert);

      if (startIndex >= 0 && endIndex >= 0 && cost > 0 && startIndex != endIndex)
      {
         adjMatrix[startIndex][endIndex] = cost;
         numPaths++;
      }

   }

   public void addEdge(Comparable startVert, Comparable endVert)
   {
      addEdge(startVert, endVert, 1);
   }

   public void addUndirectedEdge(Comparable vert1, Comparable vert2, double cost)
   {
      addEdge(vert1, vert2, cost);
      addEdge(vert2, vert1, cost);

      if (findItemIndex(vert1) >= 0 && findItemIndex(vert2) >= 0 && vert1 != vert2 && cost > 0)
         numPaths--; 
   }

   public void removeEdge(Comparable startVert, Comparable endVert)
   {
      int startIndex = findItemIndex(startVert);
      int endIndex = findItemIndex(endVert);

      if (startIndex >=0 && endIndex >=0)
      {
         adjMatrix[startIndex][endIndex] = Double.POSITIVE_INFINITY;
         numPaths--;
      }
   }

   public void removeUndirectedEdge(Comparable vert1, Comparable vert2)
   {
      removeEdge(vert1, vert2);
      removeEdge(vert2, vert1);

      if (findItemIndex(vert1) >=0 && findItemIndex(vert2) >=0)
         numPaths++; 
   }

   public double getWeight(Comparable here, Comparable there)
   {
      int hereNum = findItemIndex(here);
      int thereNum = findItemIndex(there);

      if (hereNum >= 0 && thereNum >= 0)
         return adjMatrix[hereNum][thereNum];
      return 0;      
   }

   public boolean isEmpty()
   {
      return (numNodes == 0);
   }

   public int numPaths()
   {
      return numPaths;
   }

   public KeyedItem getVertex(Comparable searchKey)
   {
      int searchNum = findItemIndex(searchKey);

      if (searchNum > -1)
         return items[searchNum];
      else
         return null;
   }

   public KeyedItem getVertex(int index)
   {
      int counter = 0;

      if (index < 0 || index >= numNodes)
         return null;

      for (int x=0; x<arraySize; x++)
         if (items[x] != null)
         {
            if (counter == index)
               return items[x];
            counter++;
         }

      return null; 
   }

   public Vector shortestPath(Comparable start, Comparable finish)
  {
      int startIndex = findItemIndex(start);
      int finishIndex = findItemIndex(finish);
      if (startIndex == -1 || finishIndex == -1)
         return null;

      double[] weight = new double[arraySize];
      double[] oldWeight = new double[arraySize];
      KeyedItem[] path = new KeyedItem[arraySize];
      KeyedItem tempItem;
      int minItemIndex = -1;
      double minWeight = Double.POSITIVE_INFINITY;
      nodeSet = new Vector();
      nodeSet.add(items[startIndex]);

      for (int x=0; x<arraySize; x++)
      {
         weight[x] = adjMatrix[startIndex][x];
         path[x] = items[x];
         if (weight[x] != Double.POSITIVE_INFINITY)
            path[x] = (KeyedItem) nodeSet.lastElement();
      }

      for (int p=1; p<numNodes; p++)
      {
         minWeight = Double.POSITIVE_INFINITY;
         System.arraycopy(weight, 0, oldWeight, 0, weight.length);

         for (int j=0; j<arraySize; j++)
         {
            tempItem = items[j];
            if (!nodeSet.contains(tempItem) && minWeight > weight[j])
            {
              minWeight = weight[j];
              minItemIndex = j;
            }
         }

         if (minItemIndex >=0)
         {
            nodeSet.add(items[minItemIndex]);

            for (int i=0; i<arraySize; i++)
            {
               tempItem = items[i];
               if (!nodeSet.contains(tempItem) && weight[i] > minWeight + adjMatrix[minItemIndex][i])
                  weight[i] = minWeight + adjMatrix[minItemIndex][i];
            }
         }

         for (int v=0; v<arraySize; v++)
         {
            if (weight[v] < oldWeight[v])	
               path[v] = (KeyedItem) nodeSet.lastElement();
         }
      }

      nodeSet.clear(); 
      tempItem = items[finishIndex];

      while (tempItem.getKey().compareTo(path[findItemIndex(tempItem.getKey())].getKey()) != 0)
      {
         nodeSet.insertElementAt(tempItem, 0);
         tempItem = path[findItemIndex(tempItem.getKey())];
      }
      nodeSet.insertElementAt(tempItem, 0);

      if (tempItem.getKey().compareTo(start) != 0)
         return null;

      return nodeSet;
   }

   public int numNodes()
   {
      return numNodes;
   }

   public boolean isGraphEmpty()
   {
      return numNodes == 0;
   }

   public void makeEmpty()
   {
      arraySize = initialSize;
      adjMatrix = new double[arraySize][arraySize];
      items = new KeyedItem[arraySize];
      numNodes = 0;

      for (int x=0; x<arraySize; x++)
         for (int y=0; y<arraySize; y++)
            adjMatrix[x][y] = Double.POSITIVE_INFINITY;
   }

   private void doubleArray()
   {
      double[][] tempMatrix = new double[arraySize*2][arraySize*2];
      KeyedItem[] tempItems = new KeyedItem[arraySize*2];

      for (int x=0; x<arraySize; x++)
      {
         tempItems[x] = items[x];
         for (int y=0; y<arraySize; y++)
            tempMatrix[x][y] = adjMatrix[x][y];
      }

      for (int t=arraySize; t<arraySize*2; t++) 
         for (int q=0; q<arraySize*2; q++)
            tempMatrix[t][q] = Double.POSITIVE_INFINITY;
      for (int p=0; p<arraySize; p++) 
         for (int z=arraySize; z<arraySize*2; z++)
            tempMatrix[p][z] = Double.POSITIVE_INFINITY;


      adjMatrix = tempMatrix;
      items = tempItems;
      arraySize *= 2;
   }

   private int getNullIndex()
   {
      int checkForNullIndex = 0;

      while (checkForNullIndex < arraySize && items[checkForNullIndex] != null)
         checkForNullIndex++;

      return checkForNullIndex;
   }

   public int findItemIndex(Comparable searchKey)
   {
      int index = -1;
      int counter = 0;
      
      while (counter < numNodes)
      {
         index++;

         if (items[index] != null)
         {
            if (items[index].getKey().compareTo(searchKey) == 0) 
               return index;
            counter++;
         }
         
         if (counter == numNodes) 
            return -1;
      }

      return index; 
   }

  private void buildDepthFirst(KeyedItem start)
  {
     if (!nodeSet.contains(start))
        nodeSet.add(start);

     KeyedItem[] theRest = getAllAdjVertexNotInnodeSet(start);

     for (int x=0; x<theRest.length ;x++)
        buildDepthFirst(theRest[x]);
  }

   public Vector dfs(Comparable start)
   {
      if (findItemIndex(start) < 0)
         return null;

      nodeSet = new Vector();
      buildDepthFirst(items[findItemIndex(start)]);
      return nodeSet;
   }

   public Vector bfs(Comparable start)
   {
      Vector queue = new Vector();
      nodeSet = new Vector();
      KeyedItem temp;

      if (findItemIndex(start) < 0)
         return null;
      
      queue.insertElementAt(items[findItemIndex(start)], 0);
      nodeSet.add(items[findItemIndex(start)]);

      while (queue.size() != 0)
      {
         temp = (KeyedItem) queue.lastElement();
         queue.remove(queue.size()-1);
         KeyedItem[] adjacentVertex = getAllAdjVertexNotInnodeSet(temp);

         for (int x=0; x<adjacentVertex.length; x++)
         {
            nodeSet.add(adjacentVertex[x]);
            queue.insertElementAt(adjacentVertex[x], 0);
         }
      }

      return nodeSet;
   }

   private KeyedItem getAdjVertex(KeyedItem origin, int vertexNumber)
   {
      int originIndex = findItemIndex(origin.getKey());
      int returnIndex = -1;
      int loopX=0;

      if (originIndex < 0)
         return null;

      for (int y = 0; y<vertexNumber; y++)
      {
         returnIndex = -1;
         for ( ; loopX<arraySize; loopX++)
         {
            if (adjMatrix[originIndex][loopX] != Double.POSITIVE_INFINITY && originIndex != loopX)
            {
               returnIndex = loopX;
               break;
            }  
         }
         if (loopX >= arraySize)
            return null;

         loopX = returnIndex + 1;
      }

      return items[returnIndex];
   }

   private int getNumAdjVertex(KeyedItem origin)
   {
      int originIndex = findItemIndex(origin.getKey());
      int count = 0;
      
      if (originIndex < 0)
         return 0;

      for (int x=0; x<arraySize; x++)
         if (adjMatrix[originIndex][x] != Double.POSITIVE_INFINITY && originIndex != x)
            count++;

      return count;
   }

  public KeyedItem[] getAllAdjVertex(KeyedItem origin)
  {
     int numAdj = getNumAdjVertex(origin);
     KeyedItem[] allVertexes = new KeyedItem[numAdj];

     for (int x=1; x<=numAdj; x++)
        allVertexes[x-1] = getAdjVertex(origin, x);

     return allVertexes;
  }

   private KeyedItem getAdjVertexNotInnodeSet(KeyedItem origin)
   {
      KeyedItem returnItem = null;
      KeyedItem[] all = getAllAdjVertex(origin);

      for (int x=0; x<all.length; x++)
         if (!nodeSet.contains(all[x]))
         {
            returnItem = all[x];
            break;
         }

      return returnItem;
   }

   private int getNumAllAdjVertexNotInnodeSet(KeyedItem origin)
   {
      int counter = 0;
      KeyedItem[] allAdj = getAllAdjVertex(origin);

      for (int x=0; x<allAdj.length; x++)
         if (!nodeSet.contains(allAdj[x]))
            counter++;

      return counter;
   }

   private KeyedItem[] getAllAdjVertexNotInnodeSet(KeyedItem origin)
   {
      int size = getNumAllAdjVertexNotInnodeSet(origin);
      KeyedItem[] returnArray = new KeyedItem[size];
      KeyedItem[] allAdj = getAllAdjVertex(origin);
      int count = 0;

      for (int x=0; x<allAdj.length; x++)
         if (!nodeSet.contains(allAdj[x]))
         {
            returnArray[count] = allAdj[x];
            count++;
         }
      return returnArray;
   }
}