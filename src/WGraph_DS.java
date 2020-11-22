import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class WGraph_DS implements weighted_graph,java.io.Serializable{
	private HashMap<Integer,node_info> mapi;
	private HashMap<Integer,nodeInfo> mapI;
	private static int ModeCount=0;
    private static int edg=0;
    public static int id=0;
	
    public WGraph_DS(){
        this.mapI=new HashMap<Integer,nodeInfo>();
        this.mapi=new HashMap<Integer,node_info>();
         ModeCount=0;
         edg=0;
    }  
    public WGraph_DS(WGraph_DS other){
    	this.mapI=other.mapI;
    	this.mapi=other.mapi;
    	
    }  
    public WGraph_DS(HashMap<Integer,nodeInfo> mapI){
    	  this.mapi=new HashMap<Integer,node_info>();
    	  this.mapI=mapI;
    	  ModeCount=0;
          edg=0;
    }
    /**
     * return the node_data by the node_id,
     * @param key - the node_id
     * @return the node_data by the node_id, null if none.
     */  
    public node_info getNode(int key) {
    	if(mapi.containsKey(key))
    		return mapi.get(key);
    	return null;
    }
    /**
     * return the node_data with the new function by the node_id,
     * @param key - the node_id
     * @return the node_data by the node_id, null if none.
     */  
    public nodeInfo getNodeI(int key) {
    	if(mapI.containsKey(key))
    		return mapI.get(key);
    	return null;
    }
    public HashMap<Integer,nodeInfo> getMapI(){
    	return this.mapI;
    }
    /**
     * return true iff there is an edge between node1 and node2
     * @param node1
     * @param node2
     * @return
     */
    public boolean hasEdge(int node1, int node2) {
   	 if(getEdge(node1, node2)>0.0)
   		 return true;
   	 return false;
   }
    /**
     * return the weight if the edge (node1, node1). In case
     * there is no such edge - return -1
     * @param node1
     * @param node2
     * @return
     */
    public double getEdge(int node1, int node2) {
    	if(!mapI.containsKey(node2)||!mapI.containsKey(node1)||!mapI.get(node1).edges.containsKey(node2)||!mapI.get(node2).edges.containsKey(node1))
    		return -1;
    	return mapI.get(node1).edges.get(node2);
    }
    /**
     * add a new node to the graph with the given key.
     * if there is already a node with such a key -> no action should be performed.
     * @param key
     */
    @Override
    public void addNode(int key) {
    	if(!mapi.containsKey(key)) {
    	nodeInfo New=new nodeInfo(key);
    	  mapI.put(key,New);
    	  mapi.put(key,New);
    	  ModeCount++;	
    }
    }
    /**
     * Connect an edge between node1 and node2, with an edge with weight >=0.
     *if the edge node1-node2 already exists - the method simply updates the weight of the edge.
     */
    @SuppressWarnings("static-access")
	@Override
    public void connect(int node1, int node2, double w) {
    	if(hasEdge(node1,node2) && getEdge(node1, node2)!=w) {
    	    mapI.get(node1).edges.put(node2,w);
       	    mapI.get(node2).edges.put(node1,w);
       	    ModeCount++;
    	}
    	else {
    	if(!hasEdge(node1, node2)&&mapI.containsKey(node1) && mapI.containsKey(node2) && w>=0) {
    	if(node1!=node2) {
    	    mapI.get(node1).edges.put(node2,w);
    	    mapI.get(node2).edges.put(node1,w);
    	    edg++;
    	    ModeCount++;
    	}
    	}
    	}
    }
    /**
     * This method return a pointer (shallow copy) for a
     * Collection representing all the nodes in the graph.
     * @return Collection<node_data>
     */
    @Override
    public Collection<node_info> getV() {
    	   return mapi.values();
    }
    /**
    *
    * This method returns a Collection containing all the
    * nodes connected to node_id
    * @return Collection<node_data>
    */
    @Override
    public Collection<node_info> getV(int node_id) {
    	if(mapi.isEmpty() || mapI.get(node_id).edges.isEmpty())
    		return null;
    	Collection<node_info> ans=new ArrayList<node_info>();
        Iterator<Integer> x=mapI.get(node_id).edges.keySet().iterator();
    	while(x.hasNext()) {
    		ans.add(mapi.get(x.next())); 
    	 }
    	return ans;
    }
    /**
     * Delete the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     * @return the data of the removed node (null if none).
     * @param key
     */
    @Override
    public node_info removeNode(int key) {
    	if(!mapi.containsKey(key))
    		return null;
    	node_info ans=mapi.get(key);
    	if(!mapI.get(key).edges.isEmpty()) {
    	Iterator<Integer> x=mapI.get(key).edges.keySet().iterator();
         while(x.hasNext()) {
    	    mapI.get(x.next()).edges.remove(key);
    	    edg--;
    	    ModeCount++;
    	 }
    	}
    	mapi.remove(key);
    	mapI.remove(key);
    	ModeCount++;
    	return ans;
    }
    /**
     * Delete the edge from the graph,
     * @param node1
     * @param node2
     */
    @Override
    public void removeEdge(int node1, int node2) {
    	if(hasEdge(node1, node2)) {
    		mapI.get(node1).edges.remove(node2);
    		mapI.get(node2).edges.remove(node1);
    		edg--;
    		ModeCount++;
    	}	
    }
    /** return the number of vertices in the graph.
     * @return
     */
    @Override
    public int nodeSize() {
    	return mapi.size();
    }
    /**
     * return the number of edges (undirectional graph).
     * @return
     */
    @Override
    public int edgeSize() {
    	return edg;
    }
    /**
     * return the Mode Count.
     * Any change in the inner state of the graph should cause an increment in the ModeCount
     * @return
     */
    @Override
    public int getMC() {
    	return ModeCount;
    }
    /**
     * Goes over the entire graph and inserts into each vertex tag 0 and infinite distance.
     */
    public void restDist() {
    	Iterator<nodeInfo> it=mapI.values().iterator();
    	while(it.hasNext()) {
    		nodeInfo n=(nodeInfo)it.next();
    		n.setDist(Integer.MAX_VALUE);
    		n.setTag(0);
    	}
    }
    
public class nodeInfo implements node_info,java.io.Serializable{
	public HashMap<Integer,Double> edges;
	 private int key;
	 private String info="";
	 private double tag=0;
	 private double dist=Integer.MAX_VALUE;
	 private List<node_info> Lis;
	 
	 public nodeInfo(int key) {
		 this.key=key;
		 this.tag=0;
		 this.edges=new HashMap<Integer,Double>();
		 this.info="key="+key+" tag="+tag+" edges="+edges;
		 List<node_info> l=new ArrayList<node_info>();
		 this.Lis=l;
	 }
	 public nodeInfo(node_info x) {
		 this.key=x.getKey();
		 this.tag=0;
		 this.info="key="+key+" tag="+tag+" edges="+edges;
		 HashMap<Integer,Double> e=new HashMap<Integer,Double>();
		 this.edges=e;
		 List<node_info> l=new ArrayList<node_info>();
		 this.Lis=l;
	 }
	 public nodeInfo(int key, HashMap<Integer,Double> edges) {
		 this.key=key;
		 this.tag=0;
		 this.edges=edges;
		 this.info="key="+key+" tag="+tag+" edges="+edges;
		 List<node_info> l=new ArrayList<node_info>();
		 this.Lis=l;
	 }
	 public nodeInfo() {
		 this.key=id++;
		 this.tag=0;
		 this.edges=new HashMap<Integer,Double>();
		 this.info="key="+key+" tag="+tag+" edges="+edges;
		 List<node_info> l=new ArrayList<node_info>();
		 this.Lis=l;
	 }
	 /**
	     * Return the key (id) associated with this node.
	     * each node_data have a unique key.
	     * @return
	     */ 
	 public int getKey() {
		 return this.key;
	 }
	 /**
	     * return the remark (meta data) associated with this node.
	     * @return
	     */
	 public String getInfo() {
		 return this.info;
	 }
	 /**
	     * Allows changing the remark (meta data) associated with this node.
	     * @param s
	     */
	 public void setInfo(String s) {
		 this.info=s;
	 }
	 /**
	     * Temporal data (aka distance, color, or state)
	     * which can be used be algorithms
	     * @return
	     */
	 public double getTag() {
		 return this.tag;
	 }
	 /**
	     * Allow setting the "tag" value for temporal marking an node - common
	     * practice for marking by algorithms.
	     * @param t - the new value of the tag
	     */
	 public void setTag(double t) {
		 this.tag=t;
	 }
	 /**
	     * Set the distance from the current vertex to the vertex from which we start the trajectory.
	     * @param t - the new value of the tag.
	     */	 
	 public void setDist(double t) {
		   this.dist=t;
	 }
	 /**
	     * Gives the distance from the current vertex to the vertex from which we start the trajectory.
	     * @return
	     */	 
	 public double getDist() {
		   return this.dist;
	}
	 /**
	     * Gives the list of vertices from the start vertex of any trajectory to it.
	     * @return
	     */	 
	 public List<node_info> getLis() {
			return Lis;
		 }
	 /**
	     * Inserts a list of vertices from the vertex of the start of a particular trajectory to the vertex itself.
	     * @param lis - The list of vertices.
	     */	 
		  public void setLis(List<node_info> lis) {
			Lis = lis;
		  }
}
/**
 * Tests equality between two weighted graphs.
 * @return Returns true if the graphs are equal
 * @param second
 */	 
public boolean equals(Object second) {
	WGraph_DS sec=(WGraph_DS)second;
	boolean yah=true;
	if(this.getMapI().size()!=sec.getMapI().size()) {
		return false;
	}
	for(node_info i: this.getV()) {
		for(node_info j: this.getV(i.getKey()) ) {
			yah&=sec.hasEdge(i.getKey(), j.getKey());
		}
	}
	return yah;
}
}
