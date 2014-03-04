//package MetisFormatter;
//	A sample program to show working examples of Depth First Search (DFS) and Breadth First Search (BFS)
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeSet;

public class BFSclusters
{

    static HashMap<Integer, Integer> degree = new HashMap<>();
    TreeSet<Integer> t;
    ArrayList<Integer> list;
    static int N; // number of vertices in the graph
    boolean[][] G;
    static boolean[] V;
    int assg = 0, degsiz, count = 0;
    static int clustid = 1;
    int num = 1;
    int diameter = 13;
    double beta = 0.5;
    int threshold = diameter / 2;
    int[] map; 

    public static void main(String[] args) throws FileNotFoundException
    {
        new BFSclusters(); // I like doing things this way so I don't have to use static objects
    }

    BFSclusters() throws FileNotFoundException
    {
        Init();
    }

    int binary(int search, int n)
    {
        int c, first, last, middle;

        first = 0;
        last = n - 1;
        middle = (first + last) / 2;

        while (first <= last)
        {
            if (list.get(middle) < search)
            {
                first = middle + 1;
            } else if (list.get(middle) == search)
            {
                return middle;
            } else
            {
                last = middle - 1;
            }

            middle = (first + last) / 2;
        }

        return n - 1;
    }

    void Init() throws FileNotFoundException
    {
        File inp = new File("withremoval");
        FileInputStream in = new FileInputStream(inp);
        Scanner scan = new Scanner(in);
        N = scan.nextInt();
	int m = scan.nextInt();
	
	map = new int[N];   
        V = new boolean[N]; // a visited array to mark which vertices have been visited while doing the BFS
        G = new boolean[N][N];
        for (int i = 0; i < N; i++)
        {
            V[i] = false;
        }

        int numComponets = 0; // the number of components in the graph // do the BFS from each node not already visited

        while (scan.hasNext())
        {
            int a = scan.nextInt();
            int b = scan.nextInt();
            G[a][b] = true;
            G[b][a] = true;
            count++;
        }
	count = 0;

	//Do RANDOM SAMPLING HERE
	int temp = (int) (Math.random() * N);
	int countV = 0;
	System.err.println(temp);
	//temp=1;
	for(int i=0;i<N;i++){
		System.out.println(i+" "+G[temp][i]);
	}
	System.err.println(temp);
	
	BFS(temp,V);

	while(count<N)
		{
		do
		{
			 temp += 1;
			 temp = temp % (N);
		}while(V[temp] && count<N);
		
		BFS(temp,V);
		}/*
	for (int k = 1; k < N; k++)
        {
		if(!V[k])
			BFS(k,V);
  
        }*/
	//RANDOM SAMPLING OVER
	//PRINT MAP
	for (int k = 0; k < map.length; k++)
        {
            System.out.println(map[k]);
	  
        }
   
    } 

    void BFS(int start, boolean[] V)
    {
        Queue<Integer> Q = new LinkedList<Integer>(); 
	int[] level = new int[N];
        Q.offer(start);
	level[start] = 0;
        V[start] = true;       
        
        while (!Q.isEmpty())
        {
            int at = Q.poll(); // get the head of the queue
	    count++;	   
	    if (level[at] == threshold)
            {
			V[at]=false;
			count--;
			while(!Q.isEmpty())
			{
		    		
				V[Q.poll()]=false;
		   	}
			clustid++;
			return;	
	    }
	    
            for (int i = 0; i < N; ++i)
            {
                if (G[at][i] && !V[i] && level[at] != threshold)
                {
                    level[i] = level[at] + 1;
                    Q.offer(i);
		    V[i]=true;
		   
                }
            }

	map[at] = clustid;	
        }
	clustid++;
        assg+=map.length;
        }
}
