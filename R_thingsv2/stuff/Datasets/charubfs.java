/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//package MetisFormatter;

/**
 *
 * @author Charu
 */
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

public class charubfs
{

    static HashMap<Integer, Integer> degree = new HashMap<>();
    TreeSet<Integer> t;
    ArrayList<Integer> list;
    static int N; // number of vertices in the graph
    boolean[][] G;
    static boolean[] V;

    public static void main(String[] args) throws FileNotFoundException
    {
        new charubfs(); // I like doing things this way so I don't have to use static objects
    }

    charubfs() throws FileNotFoundException
    {


        System.out.println("------------------------------");
        System.out.println();

        BFS();

        System.out.println();
        System.out.println("------------------------------");
        System.out.println();
        System.out.println("All done - have a good day!");
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
                //printf("%d found at location %d.\n", search, middle + 1);
                return middle;
            } else
            {
                last = middle - 1;
            }

            middle = (first + last) / 2;
        }

        return n - 1;
    }

    void BFS() throws FileNotFoundException
    {
        File inp = new File("withoutremoval");

        FileInputStream in = new FileInputStream(inp);
        int count = 0;

        Scanner scan = new Scanner(in);
        N = scan.nextInt();
        int m = scan.nextInt();
        V = new boolean[N]; // a visited array to mark which vertices have been visited while doing the BFS

        for (int i = 0; i < N; i++)
        {
            V[i] = false;
        }

        int numComponets = 0; // the number of components in the graph

        // do the BFS from each node not already visited

        G = new boolean[N][N];


        while (scan.hasNext())
        {
            int a = scan.nextInt();
            int b = scan.nextInt();
            G[a][b] = true;
            G[b][a] = true;
            count++;
        }
        // System.out.println(count);

        int ctr = 0, flag = 0;
        for (int i = 0; i < N; i++)
        {
            flag = 0;
            for (int j = 0; j < N; j++)
            {
                if (G[i][j])
                {
                    ctr++;
                    flag = 1;
                }
            }
            if (flag == 1)
            {
                degree.put(ctr, i);
            }

        }

        System.err.println("length of V : " + V.length);
        System.err.println("max degree : " + ctr);
        System.err.println(" degree list size : " + degree.size());
        list = new ArrayList(degree.keySet());

        Collections.sort(list);

        Random r = new Random();
        int index, res, temp1, iter = 0;
        while (list.size() > 0)
        {
            System.out.println(iter++ + " " + list.size());
            int sample = (int) (Math.random() * ctr);
            if (sample > ctr)
            ///System.exit(7);
            {
                System.out.println("here1");
            }
            // System.out.println("random , ctr " + sample+" "+ Math.random()*ctr+ " "+ ctr);
            if (degree.containsKey(sample))
            {
		
                BFS(degree.get(sample), V);
                System.out.println("removing sample " + sample + " at index " + degree.get(sample));
                res = sample;
                index = list.indexOf(sample);

            } else
            {
                int temp = binary(sample, list.size());
                if (temp > list.size() - 1)
                {
                    System.exit(8);
                }
                System.out.println("removing bin sample " + list.get(temp) + " at index " + degree.get(list.get(temp)));
                BFS(degree.get(list.get(temp)), V);
                res = list.get(temp);
                if (res > ctr)
                                  {
                    System.out.println("here");
                }
                index = list.indexOf(temp);

            }
            
            ArrayList<Integer> list1 = new ArrayList(degree.keySet());
            ArrayList<Integer> conflicts = new ArrayList();
            Collections.sort(list1);
            int s1 = 0;
            if(list1.lastIndexOf(res)!=list1.size()-1)
            for (int l : list1.subList(list1.indexOf(res)+1, list1.size()-1))
            {
                //if (l > res)
                {
                    //s1=degree.size();
                    temp1 = degree.get(l);
                    degree.remove(l);
                    if (degree.containsKey(l - res))
                    {
                        //System.err.println(degree.get(i-res)+"conflict at "+ (i-res)+ ":"+ temp1);
                        conflicts.add(degree.get(l - res));
                    }
                    degree.put(l - res, temp1);
                    s1 = l - res;
                }
            }
            System.err.println(res + " " + conflicts.size() + " " + (ctr - s1));
            degree.remove(res);
            list = new ArrayList(degree.keySet());
            ctr = s1;

        }
        System.out.println(N);
//        for (int i = 0; i < N; ++i)
//        {
//            if (!V[i])
//            {
//                ++numComponets;
//                System.out.printf("Starting a BFS for component %d starting at node %d%n", numComponets, i);
//                
//                BFS(i, V);
//            }
//        }
//        
        System.out.println();
        System.out.printf("Finished with BFS - found %d components.%n\n\n\n\n\n\n", numComponets);
    }

    // perform a BFS starting at node start
    void BFS(int start, boolean[] V)
    {

        Queue<Integer> Q = new LinkedList<Integer>(); // A queue to process nodes

        int[] map = new int[N];
        // start with only the start node in the queue and mark it as visited
        Q.offer(start);
        V[start] = true;
        int num = 1;
        int diameter = 13;
        double beta = 0.5;
        int threshold = diameter / 2;
        int count = 0;
        int clustid = 1;
        // continue searching the graph while still nodes in the queue
        while (!Q.isEmpty())
        {
            int at = Q.poll(); // get the head of the queue
            //	System.out.printf("At node %d in the BFS%n",at);
            if (count == threshold)
            {
                clustid++;
                count = 0;
            }

            map[at] = clustid;
            count++;
            // add any unseen nodes to the queue to process, then mark them as visited so they don't get re-added
            for (int i = 0; i < N; ++i)
            {

                if (G[at][i] && !V[i])
                {
                    Q.offer(i);
                    V[i] = true;

                    num++;

                }
            }

        }

//		System.out.printf("Finished with the BFS from start node %d%n", start);
        System.out.println("Number of components: " + num);
        for (int k = 0; k < map.length; k++)
        {
            System.out.println(map[k]);
        }
    }
}
