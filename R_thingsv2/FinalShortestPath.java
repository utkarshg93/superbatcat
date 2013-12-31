/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//package javaapplication9;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
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
     clusters = new ArrayList<>();
    clustermapping = new ArrayList<>();
    nodetocluster = new ArrayList<>();
                File inp = new File("enron_undir_edgelist_connected");
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
        File inp1 = new File("enron_undir_edgelist");
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
        for(int i=0;i<clusters.size();i++){
          //  System.out.println(clusters.get(i).name +" "+clusters.get(i).nodes.size()+" " + clusters.get(i).edges.size());
            for(int j=0;j<clusters.get(i).edges.size();j++){
          //   System.out.println(clusters.get(i).edges.get(j).target.name + " " + clusters.get(i).edges.get(j).weight);   
          clusters.get(i).edges.get(j).weight/=clusters.get(i).nodes.size() ;
        //  System.out.println(clusters.get(i).edges.get(j).target.name + " " + clusters.get(i).edges.get(j).weight); 
             
            }
        }
    }
    static void l3_1(int topen) throws FileNotFoundException{
        int number = clusters.size();
        clusters= new ArrayList<>();
        clustermapping = new ArrayList<>();
        nodetocluster = new ArrayList<>();
        //System.out.println(number);
        int flag=0;
        File inp = new File("enron_undir_edgelist_connected");
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
              Vertex toadd = new Vertex(cluster,nodeid,1);
              clusters.add(toadd);
            }
            else{
                clusters.get(clustermapping.indexOf(cluster)).nodes.add(nodeid);
            }
            nodeid++;
        }
    //    System.out.println(clusters.size());
        File inp1 = new File("enron_undir_edgelist");
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
        for(int i=0;i<clusters.size();i++){
    //        System.out.println(clusters.get(i).name +" "+clusters.get(i).nodes.size()+" " + clusters.get(i).edges.size());
            for(int j=0;j<clusters.get(i).edges.size();j++){
      //       System.out.println(clusters.get(i).edges.get(j).target.name + " " + clusters.get(i).edges.get(j).weight);   
          clusters.get(i).edges.get(j).weight/=clusters.get(i).nodes.size() ;
        //  System.out.println(clusters.get(i).edges.get(j).target.name + " " + clusters.get(i).edges.get(j).weight); 
             
            }
        }
    }
   static void compute(int source, int dest) throws FileNotFoundException{
          djkarasta shortest = new djkarasta();
    
     shortest.computePaths(clusters.get(clustermapping.indexOf(source)),clusters.get(clustermapping.indexOf(dest)));
     List<Vertex> path = shortest.getShortestPathTo(clusters.get(clustermapping.indexOf(dest)));
      PrintWriter out = new PrintWriter("shortest.json");
      out.print("{\"name\":\"sp\",\"level1\":[],\"level2\":[");
      if(path.size()>1)
       for(int i=0;i<path.size();i++){
                  out.print("\"cluster1d\":"+path.get(i).name);
                  if(i<path.size()-1){
                      out.print(",");
                  }
       }
       out.print("]}");
       out.close();
    }
     static void computeo(int source, int dest, int topen) throws FileNotFoundException{
          l3_1(topen);
          djkarasta shortest = new djkarasta();
    
     shortest.computePaths(clusters.get(clustermapping.indexOf(source)),clusters.get(clustermapping.indexOf(dest)));
     List<Vertex> path = shortest.getShortestPathTo(clusters.get(clustermapping.indexOf(dest)));
      PrintWriter out = new PrintWriter("shortest.json"); 
     if(path.size()<2){
          out.print("{\"name\":\"sp\",\"level1\":[],\"level2\":[]}");
          return;
      }
       else{
         out.print("{\"name\":\"sp\",\"level1\":[");
      for(int i=0;i<path.size();i++){
          //  System.out.println(path.get(i).tp);
            if(path.get(i).tp!=-1){
               out.print(path.get(i).real_name);
             if(i<path.size()-1){
                      out.print(",");
                  }
            }
        }
      out.print("],\"level2\":[");
      for(int i=0;i<path.size();i++){
          //  System.out.println(path.get(i).tp);
            if(path.get(i).tp==-1)
              out.print("\"cluster1d\":"+path.get(i).name);
                  if(i<path.size()-1){
                      out.print(",");
                  }
        }
       out.print("]}");
       }
     out.close();
    }
    public static void main(String[] args) throws FileNotFoundException {
        // TODO code application logic here
     int source=Integer.parseInt(args[0]);
     int dest=Integer.parseInt(args[1]);   
     int flag=Integer.parseInt(args[2]);
     int topen=Integer.parseInt(args[3]);
     l3();
     if(flag==1){
         source = nodetocluster.indexOf(source);
         dest = nodetocluster.indexOf(dest);
     }
     if(flag==2){
         source = nodetocluster.indexOf(source);
     }
     if(flag==3){
         dest = nodetocluster.indexOf(dest);
     }
     if(topen==-1){
      compute(source,dest);
     }
     else{
      computeo(source,dest,topen);   
     }  
    }
    
}
class Vertex implements Comparable<Vertex>
{
 int name;
 int real_name;
 int tp=-1;
 ArrayList<Integer> nodes = new ArrayList<>();
 ArrayList<Edge> edges = new ArrayList<>();
 ArrayList<Integer> edgesmap = new ArrayList<>();
 public double minDistance = Double.POSITIVE_INFINITY;
 public Vertex previous;
 
 Vertex(int _name,int _node)
 {
     name=_name;
     nodes.add(_node);
     real_name=_node;
 }
 Vertex(int _name, int _node, int tp){
     name=_name;
     nodes.add(_node);
     real_name=_node;
     tp=1;
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
                  //   System.out.println("THIS"+v.name);
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
