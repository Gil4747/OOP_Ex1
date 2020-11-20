import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class WGraph_Algo extends WGraph_DS implements weighted_graph_algorithms,java.io.Serializable  {
	private WGraph_DS Gds;
	public static final int NIL = -1;
	 public static final int white = 0;
	 public static final int gray = 1;
	 public static final int black = 2;
	 ArrayList<node_info> array = new ArrayList<node_info>();

	 public WGraph_Algo() {
		 new WGraph_DS();
		 this.array=new ArrayList<node_info>();
	 }
	@Override
	public void init(weighted_graph g) {
		this.Gds=(WGraph_DS)g;	
	}

	@Override
	public weighted_graph getGraph() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public weighted_graph copy() {
		WGraph_DS cop=new WGraph_DS();
		Iterator<node_info> x=this.Gds.getV().iterator();
		while(x.hasNext()) {
			node_info n=x.next();
			cop.addNode(n.getKey());
			Iterator<Integer> nI=this.Gds.getMapI().get(n.getKey()).edges.keySet().iterator();
			while(nI.hasNext()) {
				int nII=nI.next();
				cop.getNodeI(n.getKey()).edges.put(nII, Gds.getEdge(n.getKey(), nII));
			}
			
		}
		return cop;
	}

	@Override
	public boolean isConnected() {
		if(Gds.getV().size()<2)
			return true;
	    boolean visited[]=new boolean[Gds.getV().size()*100];       
		LinkedList<Integer> queue=new LinkedList<Integer>(); 
	    node_info temp=Gds.getV().iterator().next();
		int src=temp.getKey();
	    visited[src]=true; 
	    queue.add(src); 
		while (queue.size()!=0) 
		{ 
		      src=queue.poll(); 
		      Iterator<Integer> i=Gds.getNodeI(src).edges.keySet().iterator(); 
		      while (i.hasNext()) 
		      { 
	               int n=i.next().intValue(); 
	               if (!visited[n]) 
		            { 
		                visited[n]=true; 
		                 queue.add(n); 
		            } 
		       } 
	   }
	    int k=0;
		 while(k<visited.length) {
		       if(!visited[k] && Gds.getMapI().containsKey(k))
		      		return false;
		    	k++;
	    }
	      return true;      
	 }
	

	@Override
	public double shortestPathDist(int src, int dest) {
		Gds.restDist();	
	    if(src==dest)
		    return 0;
	    if(!Gds.getMapI().containsKey(src) || !Gds.getMapI().containsKey(dest))
		   return -1;
	    PriorityQueue<nodeInfo> queue = new PriorityQueue<nodeInfo>(new Comparator<nodeInfo>() {
			 @Override
		public int compare(nodeInfo o1, nodeInfo o2) {
			return - Double.compare(o2.getDist(),o1.getDist());
		}
		});
	     nodeInfo temp=(nodeInfo)Gds.getNode(src);
	     temp.setTag(black);
		 queue.add(temp);
		 temp.setDist(0);
			   
		 while(!queue.isEmpty()) {
		      temp=queue.poll();
		      if(temp.getKey()==dest) {
			    	temp.setTag(black);
			    	  break;
			   }
			  Iterator<Integer> pathFirst=temp.edges.keySet().iterator();
			  
			  while(pathFirst.hasNext()) {
				  int t=pathFirst.next();
					  
				  if( Gds.getNodeI(t).getTag()!=black && Gds.getNodeI(t).getDist()>temp.getDist()+Gds.getEdge(temp.getKey(), Gds.getNodeI(t).getKey())) {
					  Gds.getNodeI(t).setTag(gray);
					  queue.add( Gds.getNodeI(t));
					  Gds.getNodeI(t).setDist(temp.getDist()+Gds.getEdge(temp.getKey(), Gds.getNodeI(t).getKey()));
				  }
						  
			  }
			  temp.setTag(black);
		 }
		 if(Gds.getNode(dest).getTag()!=black)
			 return -1;
		 nodeInfo ansD= (nodeInfo)Gds.getNode(dest);
		 nodeInfo ansS= (nodeInfo)Gds.getNode(src);
		 double ans=ansS.getDist()+ansD.getDist();
		 if(ans<0)
			 return -1;
		  return ansS.getDist()+ansD.getDist();
			  
	}

	@Override
	public List<node_info> shortestPath(int src, int dest) {
		if(!Gds.getMapI().containsKey(src) || !Gds.getMapI().containsKey(dest))
			  return null;
		  List<node_info> L=new LinkedList<node_info>();  
		  Gds.restDist();
				
		  if(src==dest) {
			 L.add(Gds.getNode(src));
			 return L;
		  }
		  if(!Gds.getMapI().containsKey(src) || !Gds.getMapI().containsKey(dest))
			  return null;
		  PriorityQueue<nodeInfo> queue = new PriorityQueue<nodeInfo>(new Comparator<nodeInfo>() {
		  @Override
		  public int compare(nodeInfo o1, nodeInfo o2) {
			 return - Double.compare(o2.getDist(),o1.getDist());
	      }
		  });
		  nodeInfo temp=(nodeInfo)Gds.getV().iterator().next();
		  temp.setTag(black);
		  queue.add(temp);
		  temp.setDist(0);
		  Gds.getNodeI(temp.getKey()).getLis().add(temp);
		  while(!queue.isEmpty()) {
			  temp=queue.poll();
	
			  Iterator<Integer> pathFirst=temp.edges.keySet().iterator();	  
			  while(pathFirst.hasNext()) {
				  int t=pathFirst.next();	  
				  if(Gds.getNodeI(t).getTag()!=gray &&Gds.getNodeI(t).getTag()!=black && Gds.getNodeI(t).getDist()>temp.getDist()+Gds.getEdge(temp.getKey(), Gds.getNodeI(t).getKey())) {
					  Gds.getNodeI(t).setTag(gray);
					  Gds.getNodeI(t).setDist(temp.getDist()+Gds.getEdge(temp.getKey(), Gds.getNodeI(t).getKey()));
					  queue.add(Gds.getNodeI(t));
					  Gds.getNodeI(t).setLis(copyList(temp.getLis()));
					  Gds.getNodeI(t).getLis().add(Gds.getNode(t));
						  
				  }		  
			  }
			  temp.setTag(black);
			   if(temp.getKey()==dest)
				  break;
		   }
		  if(Gds.getNode(dest).getTag()!=black)
				 return null;
		  nodeInfo ans= Gds.getNodeI(dest);
		  return ans.getLis();	 
	  }
	
private List<node_info> copyList( List<node_info> s) {
	List<node_info> ans= new LinkedList<node_info>();
	Iterator<node_info> x=s.iterator();
	while(x.hasNext()) {
		ans.add(x.next());
	}
	return ans;
}
	@Override
	public boolean save(String file){
		WGraph_Algo n=this;
	       try {
	           FileOutputStream fille = new FileOutputStream(file);
	           ObjectOutputStream out = new ObjectOutputStream(fille);
	           out.writeObject(n);
	                out.close();
	                fille.close();


	       }catch (IOException e){
	           e.printStackTrace();
	           return false;
	       }
	        return true;
	}

	@Override
	public boolean load(String file) {
		try{
		    FileInputStream fille= new FileInputStream(file);
		    ObjectInputStream input = new ObjectInputStream(fille);
		    WGraph_Algo newGr = (WGraph_Algo) input.readObject();
		    this.init(newGr.getGraph());
		}catch (IOException | ClassNotFoundException e){
		    return false;

		}
		        return true;
		    }

	public boolean equals(WGraph_Algo sec) {
		WGraph_DS second=sec.Gds;
		WGraph_DS first=this.Gds;
		boolean yah=true;
		for(node_info i: first.getV()) {
			for(node_info j: first.getV(i.getKey()) ) {
				yah&=second.hasEdge(i.getKey(), j.getKey());
			}
		}
		return yah;
	}
}
