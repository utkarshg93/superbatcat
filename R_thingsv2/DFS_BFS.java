// Stephen Fulwider
//	A sample program to show working examples of Depth First Search (DFS) and Breadth First Search (BFS)

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
	

public class DFS_BFS
{

	public static void main(String[] args) throws FileNotFoundException
	{
		new DFS_BFS(); // I like doing things this way so I don't have to use static objects
	}
	
	int N; // number of vertices in the graph
	boolean[][] G; // the graph as an adjacency matrix
					// G[i][j] is true if there is an edge from i to j
	
	
	DFS_BFS() throws FileNotFoundException
	{
		setupGraph();
				
		System.out.println("------------------------------");
		System.out.println();
		
		// perform a DFS on the graph
	/*	DFS();
		
		System.out.println();
		System.out.println("------------------------------");
		System.out.println();
	*/	
		
		// perform a BFS on the graph
		BFS();
		
		System.out.println();
		System.out.println("------------------------------");
		System.out.println();
		System.out.println("All done - have a good day!");
	}
	
	// initial setup of the graph
	void setupGraph() throws FileNotFoundException
	{
		// set up a graph with 8 vertices that looks like:
		/*
			0 --- 1        5---6
			| \    \       |  /
			|  \    \      | /
			2   3----4     7
			
			Notice this graph has 2 components
		*/
			File inp = new File("withoutremoval");
FileInputStream in = new FileInputStream(inp);

        int count = 0;

        Scanner scan = new Scanner(in);
        N = scan.nextInt();
        int m = scan.nextInt();
		G=new boolean[N][N];

		  
	
//            Scanner scan = new Scanner(in);
		while(scan.hasNext()){
		int a= scan.nextInt();
		int b=scan.nextInt();
		G[a][b]=true;
		G[b][a]=true;
	count++;
}		
 System.out.println(count);
}
	
	// perform a DFS on the graph G
	void DFS()
	{
		boolean[] V=new boolean[N]; // a visited array to mark which vertices have been visited while doing the DFS
		
		int numComponets=0; // the number of components in the graph
		
		// do the DFS from each node not already visited
		for (int i=0; i<N; ++i)
			if (!V[i])
			{
				++numComponets;
				System.out.printf("Starting a DFS for component %d starting at node %d%n",numComponets,i);
				
				DFS(i,V);
			}
		
		System.out.println();
		System.out.printf("Finished with DFS - found %d components.%n", numComponets);
	}
	
	// perform a DFS starting at node at (works recursively)
	void DFS(int at, boolean[] V)
	{
		System.out.printf("At node %d in the DFS%n",at);
		
		// mark that we are visiting this node
		V[at]=true;
		
		// recursively visit every node connected to this that we have not already visited
		for (int i=0; i<N; ++i)
			if (G[at][i] && !V[i])
			{
				System.out.printf("Going to node %d...",i);
				DFS(i,V);
			}
		
		System.out.printf("Done processing node %d%n", at);
	}
	
	// perform a BFS on the graph G 
	void BFS()
	{
		boolean[] V=new boolean[N]; // a visited array to mark which vertices have been visited while doing the BFS
		
		int numComponets=0; // the number of components in the graph
		
		// do the BFS from each node not already visited
		System.out.println(N);
		for (int i=0; i<N; ++i)
			if (!V[i])
			{
				++numComponets;
				System.out.printf("Starting a BFS for component %d starting at node %d%n",numComponets,i);
				
				BFS(i,V);
			}
		
		System.out.println();
		System.out.printf("Finished with BFS - found %d components.%n\n\n\n\n\n\n", numComponets);
	}
	
	// perform a BFS starting at node start
	void BFS(int start, boolean[] V)
	{
		Queue<Integer> Q=new LinkedList<Integer>(); // A queue to process nodes
		
		int [] map =new int[N];	
		// start with only the start node in the queue and mark it as visited
		Q.offer(start);
		V[start]=true;
		int num =1 ;
		int diameter=13;
		double beta=0.5;
		int threshold=diameter/2;
		int count =0;	
		int clustid=1;
		// continue searching the graph while still nodes in the queue
		while (!Q.isEmpty())
		{
			int at=Q.poll(); // get the head of the queue
		//	System.out.printf("At node %d in the BFS%n",at);
			if(count==threshold){
					clustid++;
					count=0;
					}

					map[at]=clustid;
					count++;
			// add any unseen nodes to the queue to process, then mark them as visited so they don't get re-added
			for (int i=0; i<N; ++i){
				 
				if (G[at][i] && !V[i])
				{
					Q.offer(i);
					V[i]=true;
						
					
				//	System.out.printf("Adding node %d to the queue in the BFS%n", i);
					num++;
					
				}
			}
			//System.out.printf("Done processing node %d%n", at);
		
			
		//	System.out.println("\n\n\n\n");
		}
		
//		System.out.printf("Finished with the BFS from start node %d%n", start);
	System.out.println("Number of components: "+num);
	for(int k=0;k<map.length;k++){
		System.out.println(map[k]);
	}
	}

}
