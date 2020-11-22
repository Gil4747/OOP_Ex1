# OOP_Ex1
 WGraph algorithems
Author :Gil Thioni

university class project of an implementation of (undirectional) weighted graph.

in the project we implements three classes. each class represents a diffrent part of the graph structer.

(1) nodeInfo - my implemetiton of the interface node_info. the class represents the set of operations applicable on a node (vertex) in an (undirectional) weighted graph.

class data:
key - represent the node id.
edges - represent the node's neighbors as a Key and an hash map represent the edges between the nodes as an Hashmap (in the graph).
info -  remark (meta data) associated with this node.
Lis - Java List to be use in one of the algo.
tag - for algorithems use.
dist - for algorithems use.
id - static int var used to attach each node uniqe key.

 Methods:
 
*getKey() - This method Return the key (id) associated with this node.
   Note: each node_data have a unique key - by defining 
   static Integer "id" represent the current key value 
   which increasing by 1 in every time a new node created and added to the graph.
 time complicity - O(1)  (by hashmap method)
 

*getInfo() - this method return the remark (meta data) associated with this node.
 time complicity - O(1) (by hashmap method)

*setInfo() - this method allows changing the remark (meta data) associated with this node.
 time complicity - O(1) (by hashmap method)

*getTag() - geting Temporal data (aka color: e,g, white, gray, black) 
 which can be used be algorithms.
 time complicity - O(1) (by hashmap method)
 
 
(2) WGraph_DS - my implemetiton of the interface graph,represents an undirectional unweighted graph.

    class data:
    mapi - java HashMap represents the graph nodes.
    edg - represents the total amount of edges in the graph .
    ModeCount- represents Mode Count for testing changes in the graph.
    
  Methods:
    
 *getNode(int key) - this method returns the node_data by the node_id (key).
  time complicity - O(1) (by hashmap method)
 
 *hasEdge(int node1,int node2) - the method returns true if there is an edge
  between node1 and node2,otherwise returns false.
  time complicity - O(1) (by hashmap method)
 
 *addNode(node_data n) - method to add a new node to the graph with the given node_data.
  time complicity -  O(N) (to update all node n nighbers edges)
 
 *connect(int node1, int node2) - Connect an edge between node1 and node2.
  time complicity - O(1) (by hashmap method)

 *getV()- This method return a pointer (shallow copy) for the
      collection representing all the nodes in the graph.
    O(1) (by hashmap method)
    
 *getV(int node_id) - This method returns a collection containing all the
    nodes connected to node_id.
  O(1) (by hashmap method)
  
 *removeNode(int key) - This method Delete the node (with the given ID) from the graph
    and removes all edges which starts or ends at this node
  O(N) (to update all node n nighbers edges)

 *removeEdge(int node1, int node2) - This method Delete the edge from the graph.
 O(1) (by hashmap method)
 
 *nodeSize() - return the number of vertices (nodes) in the graph.
 O(1) (by hashmap method)
 
 *edgeSize() - return the number of edges.
 O(1) (updates during other methods runtime)
 
 *getMC() - this method return the Mode Count.
 O(1) (updates during other methods runtime)
 
 
(3) WGraph_Algo - this class represents the "regular" weighted Graph Theory algorithms.

  class data:
  
   Gds - WGraph_DS to apply this algorithms on.
   NIL,white,gray,black - enums style to represent node's status used in algorithms.
   
   Methods:
   
   *init(graph g) - Init the graph on which this set of algorithms operates on.
   O(1) (by hashmap method)
   
   *copy() - Compute a deep copy of this graph.
   O(N) (Deep copy required O(1) for every new node created and added to new graph)
   
   *isConnected() - my implemetiton of the BFS algo. this method Returns
    true if there is a valid path from EVREY node to each
    other node in undirectional graph.
    O(N) (get to all nodes in graph note:N=V)
    
   *shortestPathDist(int src,int dest) - my implemetiton of the dikstrka algo.
    returns the length of the shortest path between  node src to node dest.
    im using priority queue to get every node visit by its rank from node src to node dest. 
    note: i overrided compare function to make PQ to works like an MinHeap.
    O(V*E) (visit all nighbers of all nodes in the graph) 
Note: V = total nodes number, E = total edges number.
Note:true for both shortestPathDist methods!

thanks (;
