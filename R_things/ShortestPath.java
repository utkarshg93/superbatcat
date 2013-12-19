/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author utkarsh
 */
public class ShortestPath {

    /**
     * @param args the command line arguments
     */
   static HashMap<Integer, Integer> nodetocluster =new HashMap<>(); 
   static HashMap<Integer, Set<Integer>> nodeedges =new HashMap<>(); 
   static ArrayList<Vertex> cluster = new ArrayList<>();
   static ArrayList<Integer> mapping = new ArrayList<>();
   public static void l0(String file2) throws FileNotFoundException
   {
            File inp = new File(file2);
            FileInputStream in = null;
          
            in = new FileInputStream(inp);
            Scanner scan1 = new Scanner(in);
            int n, m, j, k;
           // n = node;
            m = 0;
            while(scan1.hasNext())
            {
                m++;
               
                 j = scan1.nextInt();
                 k = scan1.nextInt();
                // System.out.println(j+" "+k);
                 if(!mapping.contains(j)){
                     mapping.add(j);
                     cluster.add(new Vertex(j));
                     cluster.get(mapping.indexOf(j)).adjacencies = new ArrayList<>();
                 }
                 if(!mapping.contains(k)){
                     mapping.add(k);
                     cluster.add(new Vertex(k));
                     cluster.get(mapping.indexOf(k)).adjacencies = new ArrayList<>();
                 }
                 Edge toadd1 = new Edge(cluster.get(mapping.indexOf(k)),1);
                 //Edge toadd2 = new Edge(cluster.get(mapping.indexOf(j)),1);
                 cluster.get(mapping.indexOf(j)).adjacencies.add(toadd1);
                 //cluster.get(mapping.indexOf(k)).adjacencies.add(toadd2);
            }
   }
   public static void map(String file1, String file2) throws FileNotFoundException{
            File inputFile = new File(file1);
            FileInputStream inpStream = null;
         
            int c=0;
            int max=0;
            int node=0;
            inpStream = new FileInputStream(inputFile);
            Scanner scan = new Scanner(inpStream);
            int count=0;
            while (scan.hasNext()) {
                c = scan.nextInt();
           
                if (c>max)
                    max=c;
                Vertex toadd =new Vertex(c);
                if(!mapping.contains(c)){
                 cluster.add(toadd);
                 mapping.add(c);
                 cluster.get(cluster.indexOf(toadd)).adjacencies=new ArrayList<>();
                 cluster.get(cluster.indexOf(toadd)).adj=new ArrayList<>();
                 cluster.get(cluster.indexOf(toadd)).inside=new HashMap<>();
                 cluster.get(cluster.indexOf(toadd)).insidecluster=new HashSet<>();
                 cluster.get(mapping.indexOf(c)).size=0;
                 
                }
                nodetocluster.put(node,c);
                Set<Integer> temp = new HashSet<>();
                nodeedges.put(node, temp);
                cluster.get(mapping.indexOf(c)).size++;
                node++;
                cluster.get(mapping.indexOf(c)).insidecluster.add(node);
                
            }
//            System.out.println(node);
            File inp = new File(file2);
            FileInputStream in = null;
          
            in = new FileInputStream(inp);
            Scanner scan1 = new Scanner(in);
            int n, m, j, k;
            n = node;
            m = 0;
            while(scan1.hasNext())
            {
                m++;
               
                 j = scan1.nextInt();
                 k = scan1.nextInt();
                 
                 if(!(nodetocluster.get(j).equals(nodetocluster.get(k)))){
                
                     nodeedges.get(j).add(nodetocluster.get(k));
                     

                  cluster.get(nodetocluster.get(j)-1).adj.add(mapping.get(nodetocluster.get(k)-1));
                
                  if(cluster.get(nodetocluster.get(j)-1).inside.containsKey(mapping.get(nodetocluster.get(k)-1))){
                      cluster.get(nodetocluster.get(j)-1).inside.put(mapping.get(nodetocluster.get(k)-1), cluster.get(nodetocluster.get(j)-1).inside.get(mapping.get(nodetocluster.get(k)-1))+1);
                  }
                  else{
                      cluster.get(nodetocluster.get(j)-1).inside.put(mapping.get(nodetocluster.get(k)-1),1);
                  }
                 }
                 
            }
   //       System.out.println(m);
  //        System.out.println(cluster.size());
          /*  for(int i=0;i<nodeedges.size();i++){
                System.out.println(i+ " "+nodeedges.get(i));
            }*/
     for(int i=0;i<cluster.size();i++){
       
         
           for (Integer key : cluster.get(i).inside.keySet()) {
               double distance1 = cluster.get(i).inside.get(key)/(cluster.get(i).size + cluster.get(mapping.indexOf(key)).size);
               double distance = 1/distance1;
               Edge toadd1 = new Edge(cluster.get(mapping.indexOf(key)),distance);
               Edge toadd2 = new Edge(cluster.get(i),distance);
              // System.out.println(cluster.get(i)+" "+ cluster.get(mapping.indexOf(key)));
               cluster.get(i).adjacencies.add(toadd1);
               cluster.get(mapping.indexOf(key)).adjacencies.add(toadd2);
			}
           
     }        
   
   }
   public static void main(String[] args) throws FileNotFoundException {
      
       int level;
       level = Integer.parseInt(args[0]);
       if(level ==0){
       l0("/home/utkarsh/R_things/labeling_enron_subgraphs/"+args[1]);
       }
       else if(level==2){
       map("/home/utkarsh/R_things/labeling_enron_subgraphs/"+args[1]+"_cluster", "/home/utkarsh/R_things/labeling_enron_subgraphs/"+args[1]);
       }
       else if(level==3){
       map("/home/utkarsh/R_things/labeling_enron_subgraphs/"+args[1]+"_cluster_l3_cluster", "/home/utkarsh/R_things/labeling_enron_subgraphs/"+args[1]+"_cluster_l3");
       }
            djkarasta shortest = new djkarasta();
            
            int source =Integer.parseInt(args[2]);
           
            int target = Integer.parseInt(args[3]);
           
            System.out.println(mapping.indexOf(source));
          
            shortest.computePaths(cluster.get(mapping.indexOf(source)),cluster.get(mapping.indexOf(target)));
    
	    List<Vertex> path = shortest.getShortestPathTo(cluster.get(mapping.indexOf(target)));
            PrintWriter sp = new PrintWriter("/home/utkarsh/R_things/shortest.json");
            sp.print("{\"name\":\"sp\",\"level"+level+"\":[");
            int boom=-1;
	  
            if(path.size()>1){
                  System.out.println("Path: " + path);
            for(int i=0;i<path.size();i++){
                   System.out.println(path.get(i));
                   
               // System.out.println(path.get(i).insidecluster.contains(36677));
                //System.out.println(path.get(i).insidecluster);
                
                sp.print(path.get(i));
                if(i!=path.size()-1){
                    sp.print(",");
                }
                
            }
   }
            sp.print("]}");
            sp.close();
            //}
  }
}
class Vertex implements Comparable<Vertex>
{
    public final int name;
    public ArrayList<Integer> adj;
    public ArrayList<Edge> adjacencies;
    public HashMap<Integer, Integer> inside;
    public Set<Integer> insidecluster;
    public double minDistance = Double.POSITIVE_INFINITY;
    public Vertex previous;
    public double size=0;
    public Vertex(int argName) { name = argName; }
    @Override
    public String toString() { return String.valueOf(name); }
    public int compareTo(Vertex other)
    {
        return Double.compare(minDistance, other.minDistance);
    }
    
}
class Edge
{
    public final Vertex target;
    public final double weight;
    public Edge(Vertex argTarget, double argWeight)
    { target = argTarget; weight = argWeight; }
}
class djkarasta
{
    public static void computePaths(Vertex source, Vertex target)
    {
        source.minDistance = 0.;
        PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
      	vertexQueue.add(source);
        System.out.println(source);

	while (!vertexQueue.isEmpty()) {
	    Vertex u = vertexQueue.poll();
              if(u==target){
                        break;
                    }
            // Visit each edge exiting u
            for (Edge e : u.adjacencies)
            {
                Vertex v = e.target;
                double weight = e.weight;
                double distanceThroughU = u.minDistance + weight;
		if (distanceThroughU < v.minDistance) {
		    vertexQueue.remove(v);
		    v.minDistance = distanceThroughU ;
		    v.previous = u;
		    vertexQueue.add(v);
                    
		}
            }
        }
    }

    public static List<Vertex> getShortestPathTo(Vertex target)
    {
        List<Vertex> path = new ArrayList<Vertex>();
        for (Vertex vertex = target; vertex != null; vertex = vertex.previous)
            path.add(vertex);
        Collections.reverse(path);
       // System.out.println(path);
        return path;
    }
}

