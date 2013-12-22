/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication9;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 *
 * @author utkarsh
 */
public class FinalShortestPath {

    /**
     * @param args the command line arguments
     */
    static ArrayList<Vertex> clusters = new ArrayList<>();
    static ArrayList<Integer> clustermapping = new ArrayList<>();
    static ArrayList<Integer> nodetocluster = new ArrayList<>();
    static void l3() throws FileNotFoundException{
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
        
        /*for(int i=0;i<clusters.size();i++){
            System.out.println(clusters.get(i).name +" "+clusters.get(i).nodes);
        }*/
       // System.exit(0);
        File inp1 = new File("/home/utkarsh/R_things/labeling_enron_subgraphs/enron_undir_edgelist");
        FileInputStream in1 = null;
        in1 = new FileInputStream(inp1);
        Scanner scan2 = new Scanner(in1);
        while(scan2.hasNext()){
            int source = scan2.nextInt();
            int target = scan2.nextInt();
            //System.out.println(source + " " +target);
            //System.out.println(nodetocluster.get(source) +" "+nodetocluster.get(target));
            if(nodetocluster.get(source).equals(nodetocluster.get(target))){
              //  System.out.println("here");
                continue;              
            }
            else{
                Edge toadd = new Edge(clusters.get(clustermapping.indexOf(nodetocluster.get(target))),1);
                //Edge toadd2 = new Edge(source,1);
                try{
                if(!clusters.get(clustermapping.indexOf(nodetocluster.get(source))).edgesmap.contains(nodetocluster.get(target))){   
                clusters.get(clustermapping.indexOf(nodetocluster.get(source))).edges.add(toadd);
                clusters.get(clustermapping.indexOf(nodetocluster.get(source))).edgesmap.add(nodetocluster.get(target));
                }
                else{
                  clusters.get(clustermapping.indexOf(nodetocluster.get(source))).edges.get(clusters.get(clustermapping.indexOf(nodetocluster.get(source))).edgesmap.indexOf(nodetocluster.get(target))).weight+=1;
                }
                
                }
                catch (Exception e){
               //     System.out.println(clustermapping.indexOf(source)+ " "+source);
                    System.exit(0);
                }
             //   System.out.println("adding");
            }
        }
     /*   for(int i=0;i<clusters.size();i++){
            System.out.println(clusters.get(i).name +" "+clusters.get(i).nodes.size()+" " + clusters.get(i).edges.size());
            for(int j=0;j<clusters.get(i).edges.size();j++){
             System.out.println(clusters.get(i).edges.get(j).target.name + " " + clusters.get(i).edges.get(j).weight);   
          clusters.get(i).edges.get(j).weight/=clusters.get(i).nodes.size() ;
          System.out.println(clusters.get(i).edges.get(j).target.name + " " + clusters.get(i).edges.get(j).weight); 
             
            }
        }*/
    }
    static void l3_1(int topen) throws FileNotFoundException{
        int number = clusters.size();
        clusters= new ArrayList<>();
        clustermapping = new ArrayList<>();
        nodetocluster = new ArrayList<>();
        //System.out.println(number);
        int flag=0;
                File inp = new File("/home/utkarsh/R_things/labeling_enron_subgraphs/enron_undir_edgelist_cluster");
        FileInputStream in = null;
        int nodeid=0;
        in = new FileInputStream(inp);
        Scanner scan1 = new Scanner(in);
        while(scan1.hasNext()){
            
            int cluster = scan1.nextInt();
            
            if(cluster==topen && flag!=0){
                number++;
                cluster=number;
            }
            nodetocluster.add(cluster);
            
            if(!clustermapping.contains(cluster)){
                if(cluster==topen){
                    flag++;
                }
              clustermapping.add(cluster);
              Vertex toadd = new Vertex(cluster,nodeid);
              clusters.add(toadd);
            }
            else{
                clusters.get(clustermapping.indexOf(cluster)).nodes.add(nodeid);
            }
            nodeid++;
        }
    //    System.out.println(clusters.size());
        File inp1 = new File("/home/utkarsh/R_things/labeling_enron_subgraphs/enron_undir_edgelist");
        FileInputStream in1 = null;
        in1 = new FileInputStream(inp1);
        Scanner scan2 = new Scanner(in1);
        while(scan2.hasNext()){
            int source = scan2.nextInt();
            int target = scan2.nextInt();
            //System.out.println(source + " " +target);
            //System.out.println(nodetocluster.get(source) +" "+nodetocluster.get(target));
            if(nodetocluster.get(source).equals(nodetocluster.get(target))){
              //  System.out.println("here");
                continue;              
            }
            else{
                Edge toadd = new Edge(clusters.get(clustermapping.indexOf(nodetocluster.get(target))),1);
                //Edge toadd2 = new Edge(source,1);
                try{
                if(!clusters.get(clustermapping.indexOf(nodetocluster.get(source))).edgesmap.contains(nodetocluster.get(target))){   
                clusters.get(clustermapping.indexOf(nodetocluster.get(source))).edges.add(toadd);
                clusters.get(clustermapping.indexOf(nodetocluster.get(source))).edgesmap.add(nodetocluster.get(target));
                }
                else{
                  clusters.get(clustermapping.indexOf(nodetocluster.get(source))).edges.get(clusters.get(clustermapping.indexOf(nodetocluster.get(source))).edgesmap.indexOf(nodetocluster.get(target))).weight+=1;
                }
                
                }
                catch (Exception e){
               //     System.out.println(clustermapping.indexOf(source)+ " "+source);
                    System.exit(0);
                }
             //   System.out.println("adding");
            }
        }
    /*    for(int i=0;i<clusters.size();i++){
            System.out.println(clusters.get(i).name +" "+clusters.get(i).nodes.size()+" " + clusters.get(i).edges.size());
            for(int j=0;j<clusters.get(i).edges.size();j++){
             System.out.println(clusters.get(i).edges.get(j).target.name + " " + clusters.get(i).edges.get(j).weight);   
          clusters.get(i).edges.get(j).weight/=clusters.get(i).nodes.size() ;
          System.out.println(clusters.get(i).edges.get(j).target.name + " " + clusters.get(i).edges.get(j).weight); 
             
            }
        }*/
    }
    public static void main(String[] args) throws FileNotFoundException {
        // TODO code application logic here
        l3();
        djkarasta shortest = new djkarasta();
        shortest.computePaths(clusters.get(clustermapping.indexOf(1067)),clusters.get(clustermapping.indexOf(1068)));
        List<Vertex> path = shortest.getShortestPathTo(clusters.get(clustermapping.indexOf(1)));
     
        for(int i=0;i<path.size();i++){
                   System.out.println(path.get(i).name);}
    //    System.out.println(clusters.get(clustermapping.indexOf(1068)).nodes.size());
        l3_1(1068);
        System.out.println("Path 2");
         shortest.computePaths(clusters.get(clustermapping.indexOf(1067)),clusters.get(clustermapping.indexOf(1068)));
        path = shortest.getShortestPathTo(clusters.get(clustermapping.indexOf(1)));
     
        for(int i=0;i<path.size();i++){
                   System.out.println(path.get(i).name);}
        System.out.println(clusters.get(clustermapping.indexOf(1068)).nodes.size());
        System.out.println("Path 2 ends");
        
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
    public final Vertex target;
    public double weight;
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
        //System.out.println(source);

	while (!vertexQueue.isEmpty()) {
	    Vertex u = vertexQueue.poll();
            // Visit each edge exiting u
            for (Edge e : u.edges)
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

