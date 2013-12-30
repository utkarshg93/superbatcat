package javaapplication10;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 *
 * @author utkarsh
 */
public class ConnectedChecker {

    /**
     * @param args the command line arguments
     */
    static ArrayList<Vertex> clusters = new ArrayList<>();
    static ArrayList<Integer> clustermapping = new ArrayList<>();
    static ArrayList<Integer> nodetocluster = new ArrayList<>();
    static ArrayList<Integer> nodetocluster2;// = new ArrayList<>();
    static int clustern = 1;
    static boolean G[][];
    static int N;
    static HashMap<Integer,Integer> map;
    static HashMap<Integer,Integer> map2;
    
    static void makeGraph() throws FileNotFoundException{
        File inp = new File("/home/utkarsh/R_things/labeling_enron_subgraphs/enron_undir_edgelist_cluster");
        FileInputStream in = null;
        int nodeid=0;
        in = new FileInputStream(inp);
        Scanner scan1 = new Scanner(in);
        while(scan1.hasNext()){
            
            int cluster = scan1.nextInt();
            nodetocluster.add(cluster);
            if(!clustermapping.contains(cluster)){
              clustermapping.add(cluster);
              Vertex toadd = new Vertex(cluster,nodeid);
              clusters.add(toadd);
            }
            else{
                clusters.get(clustermapping.indexOf(cluster)).nodes.add(nodeid);
            }
            nodeid++;
        }
        nodetocluster2 = new ArrayList<>();
        for (int i = 0; i < nodeid; i++) {
         nodetocluster2.add(0);
}
        
       System.out.println(nodetocluster2.size());
      // System.exit(0);
        File inp1 = new File("/home/utkarsh/R_things/labeling_enron_subgraphs/enron_undir_edgelist");
        FileInputStream in1 = null;
        in1 = new FileInputStream(inp1);
        Scanner scan2 = new Scanner(in1);
        while(scan2.hasNext()){
            int source = scan2.nextInt();
            int target = scan2.nextInt();
            if(!nodetocluster.get(source).equals(nodetocluster.get(target))){
              //  System.out.println("here");
                continue;              
            }
            else{
                Edge toadd= new Edge(target,source, 1);
                clusters.get(clustermapping.indexOf(nodetocluster.get(source))).edges.add(toadd);
            }
            
        }
    }
    
     static void bfs(int i){
         map=new HashMap<>();
         map2=new HashMap<>();
        System.out.println(clusters.get(i).name+ " "+clusters.get(i).nodes.size());
        N =clusters.get(i).nodes.size();
        G=new boolean[N][N];
        int ctr=0;
        
        for(int j=0;j<clusters.get(i).nodes.size();j++){
            if(!map.containsKey(clusters.get(i).nodes.get(j))){
                map.put(clusters.get(i).nodes.get(j), ctr);
                map2.put(ctr,clusters.get(i).nodes.get(j));
                ctr++;
            }   
        }
        
        for(int j=0;j<clusters.get(i).edges.size();j++){
            G[map.get(clusters.get(i).edges.get(j).source)][map.get(clusters.get(i).edges.get(j).target)]=true;
            G[map.get(clusters.get(i).edges.get(j).target)][map.get(clusters.get(i).edges.get(j).source)]=true;
        }
        System.out.println(ctr);
       // System.exit(0);
        BFS(map2);
    }
   static void BFS(HashMap<Integer,Integer> map2)
	{
		boolean[] V=new boolean[N]; // a visited array to mark which vertices have been visited while doing the BFS
		
		int numComponets=0; // the number of components in the graph
		
		// do the BFS from each node not already visited
		for (int i=0; i<N; ++i)
			if (!V[i])
			{
				++numComponets;
				System.out.printf("\n\n\nStarting a BFS for component %d starting at node %d%n",numComponets,i);
				
				BFS(i,V,map2);
                                 clustern++;
			}
		
		System.out.println();
               
		System.out.printf("Finished with BFS - found %d components.%n", numComponets);
	}
	
	// perform a BFS starting at node start
	static void BFS(int start, boolean[] V,HashMap<Integer,Integer> map)
	{
		Queue<Integer> Q=new LinkedList<Integer>(); // A queue to process nodes
		
		// start with only the start node in the queue and mark it as visited
		Q.offer(start);
		V[start]=true;
                System.out.println(map.get(start));
                nodetocluster2.set(map.get(start),clustern);
		// continue searching the graph while still nodes in the queue
		while (!Q.isEmpty())
		{
			int at=Q.poll(); // get the head of the queue
			System.out.printf("At node %d in the BFS%n",at);
                        
        System.out.println(map.get(at));
               nodetocluster2.set(map.get(at),clustern);
                        // add any unseen nodes to the queue to process, then mark them as visited so they don't get re-added
			for (int i=0; i<N; ++i)
				if (G[at][i] && !V[i])
				{
					Q.offer(i);
					V[i]=true;
					
					System.out.printf("Adding node %d to the queue in the BFS%n", i);
				}
			
			System.out.printf("Done processing node %d%n", at);
		}
		
		System.out.printf("Finished with the BFS from start node %d%n", start);
	}

    public static void main(String[] args) throws FileNotFoundException {
        // TODO code application logic here
        makeGraph();
        for(int i=0;i<clusters.size();i++){
  //          System.out.println(clusters.get(i).name +" "+clusters.get(i).edges.size() +" " + clusters.get(i).nodes);
    //        for(int j=0;j<clusters.get(i).edges.size();j++){
        //         System.out.println(clusters.get(i).edges.get(j).source +" "+ clusters.get(i).edges.get(j).target);
      //      }
             bfs(i);
        }
        
        PrintWriter out = new PrintWriter("/home/utkarsh/R_things/boo.txt");
        
      
       for(int i=0;i<nodetocluster2.size();i++){
           
           out.println(nodetocluster2.get(i));
       }
       
        out.close();
    }
}
class Vertex implements Comparable<Vertex>
{
 int name;
 ArrayList<Integer> nodes = new ArrayList<>();
 ArrayList<Edge> edges = new ArrayList<>();
 ArrayList<Integer> edgesmap = new ArrayList<>();
 public double minDistance = Double.POSITIVE_INFINITY;
 public Vertex previous;
 
 Vertex(int _name,int _node)
 {
     name=_name;
     nodes.add(_node);
 }
  public int compareTo(Vertex other)
    {
        return Double.compare(minDistance, other.minDistance);
    }
}
class Edge
{
    public final int target;
    public final int source;
    public double weight;
    public Edge(int argTarget,int argSource, double argWeight)
    { target = argTarget; source=argSource; weight = argWeight; }
}
