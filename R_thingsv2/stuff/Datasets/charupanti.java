/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//package javaapplication14;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;
import static javaapplication14.BFSclusters.N;

/**
 *
 * @author Charu
 */
public class Sampler
{

    /**
     * @param args the command line arguments
     */
    static HashMap<Integer, Integer> degree = new HashMap<>();
    static HashMap<Integer, Integer> revdegree = new HashMap<>();
    static HashMap<Integer, Integer> realDegree = new HashMap<>();
    static ArrayList<Integer> list, component = new ArrayList<>();
    static int max_cum = 0;
    static int N; // number of vertices in the graph
    static boolean[][] G;
    static boolean[] V;
    
    TreeSet<Integer> t;
    
    static int assg = 0, degsiz, count = 0;
    static int clustid = 1;
    static int num = 1;
    static int diameter = 13;
    static double beta = 0.5;
    static int threshold = diameter / 2;
    static int[] map; 

    static int binary(int search, int n)
    {
        for (int i = 0; i < n; i++)
        {
            if (list.get(i) > search)
            {
                return i;
            }
        }

        return (n - 1);
    }

    static void read() throws FileNotFoundException
    {
        File inp = new File("/home/utkarsh/superbatcat/R_thingsv2/stuff/Datasets/withremoval");

        FileInputStream in = new FileInputStream(inp);
        int count = 0;
        int assg = 0, degsiz;
        Scanner scan = new Scanner(in);
        N = scan.nextInt();
        int m = scan.nextInt();
        V = new boolean[N]; // a visited array to mark which vertices have been visited while doing the BFS
        map = new int[N];   
        G = new boolean[N][N];

        for (int i = 0; i < N; i++)
        {
            V[i] = false;
        }

        int numComponets = 0; // the number of components in the graph

        // do the BFS from each node not already visited

        
        while (scan.hasNext())
        {
            int a = scan.nextInt();
            int b = scan.nextInt();
            G[a][b] = true;
            G[b][a] = true;
            count++;
        }
        // //System.out.println(count);

        int ctr = 0, flag = 0;
        for (int i = 0; i < N; i++)
        {
            degsiz = 0;
            flag = 0;
            for (int j = 0; j < N; j++)
            {
                if (G[i][j])
                {
                    ctr++;
                    degsiz++;
                    flag = 1;
                }
            }
            if (flag == 1)
            {
                degree.put(ctr, i);
                revdegree.put(i, ctr);
                realDegree.put(i, degsiz);
            }

        }
        max_cum = ctr;
    }

    static void sample(int ctr)
    {
        list = new ArrayList(degree.keySet());

        Random r = new Random();
        int index, res, res1, temp1, iter = 0;
        Collections.sort(list);
        while (list.size() > 0)
        {
            //System.out.println(iter++ + " " + list.size()+ " ctr="+ ctr);
            System.err.println("going into sampling with max index = " + Collections.max(degree.values()));
            int sample = (int) (Math.random() * ctr);

            // //System.out.println("random , ctr " + sample+" "+ Math.random()*ctr+ " "+ ctr);
            if (degree.containsKey(sample))
            {
                System.err.println(degree.get(sample));
                component = BFS(degree.get(sample), V);
                
                //System.out.println("removing sample " + sample + " at index " + degree.get(sample));
//                res = sample;
//                res1=realDegree.get(degree.get(sample));
//                index = list.indexOf(sample);

            } else
            {
                int temp = binary(sample, list.size());
//                res = list.get(temp);
//                //System.out.println("removing val"+ (list.get(temp))+ "temp="+temp );
//                res1=realDegree.get(degree.get(list.get(temp)));

                //System.out.println("removing bin sample " + list.get(temp) + " at index " + degree.get(list.get(temp)));
                 System.err.println("GOGIN INTO BFs "+degree.get(list.get(temp)));
                component = BFS(degree.get(list.get(temp)), V);
                System.err.println("BACK FROM BFS:");

            }
        }
    }

    public static void main(String[] args) throws FileNotFoundException
    {
        // TODO code application logic here
        read();
        
        int temp1, temp2, temp3;
        int size = 0;
        while (degree.size() > 0)
        {
               System.err.println("Component size" + component.size()+" "+size);
               size=size+component.size();
            component.clear();
            sample(max_cum);
            int residue = 0;
            for (int i = 0; i < N; i++)
            {
                if (component.contains(i))
                {
                    residue += realDegree.get(i);
                }
                if (revdegree.containsKey(i))
                {
                    temp1 = revdegree.get(i);
                    if (component.contains(i))
                    {
                        revdegree.remove(i);
                    }

                    temp2 = degree.get(temp1);
                    
                    if((component.contains(i)))
                    {
                        degree.remove(temp1);
                    }
                    else
                    {
                        degree.remove(temp1);
                        degree.put(temp1-residue, i);
                        revdegree.put(i, temp1-residue);
                    }

                }


            }

        }
    }
     static ArrayList BFS(int start, boolean[] V)
    {
        ArrayList<Integer> vinayak = new ArrayList<>();
        Queue<Integer> Q = new LinkedList<Integer>(); 
	int[] level = new int[N];
        Q.offer(start);
	level[start] = 0;
        V[start] = true;       
        
        while (!Q.isEmpty())
        {
            int at = Q.poll(); // get the head of the queue
	    count++;
            ;
	    if (level[at] == threshold)
            {
			V[at]=false;
			count--;
			while(!Q.isEmpty())
			{
		    		
				V[Q.poll()]=false;
		   	}
			clustid++;
			return vinayak;	
	    }
	    
            for (int i = 0; i < N; ++i)
            {
                if (G[at][i] && !V[i] && level[at] != threshold)
                {
                    level[i] = level[at] + 1;
                    Q.offer(i);
		    V[i]=true;
		   
             /*       ArrayList<Integer> list1 = new ArrayList(degree.keySet());
                    Collections.sort(list1);
                    int res = -1;
                    for (int l : degree.keySet())
                    {
                        if (degree.get(l) == i)
                        {
                            res = l;
                            break;
                        }
                    }
                   
                    int s1 = 0, temp1;
                    if (list1.lastIndexOf(res) != list1.size() - 1)
                    {
                        for (int l : list1.subList(list1.indexOf(res) + 1, list1.size() - 1))
                       {
                                temp1 = degree.get(l);
                                degree.remove(l);
                                degree.put(l - res, temp1);
                                s1 = l - res;
                        }
                    }
                    degree.remove(res);
                    list = new ArrayList(degree.keySet());
                    num++;
*/
                }
            }
	map[at] = clustid;
        vinayak.add(at);
        }
	clustid++;
        assg+=map.length;
        return vinayak;
        }
}



