/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//package MetisFormatter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author enron.igraph
 */
public class JSONCOnvV3 {

    /**
     * @param args the command line arguments
     */
    static HashMap<Integer, HashMap<Integer, Integer>> unmap = new HashMap<Integer, HashMap<Integer, Integer>>();
    static HashMap<String, String> map1d = new HashMap<String, String>();
    static HashMap<Integer, ArrayList<ArrayList<Integer>>> clusttoedge = new HashMap<Integer, ArrayList<ArrayList<Integer>>>();
    static int l1=0;
    static int l2=0;
    static int l3=0;
    
    
    public static void unmap(String map, Integer clusterid) throws FileNotFoundException {
        FileInputStream inpStream = null;
        inpStream = new FileInputStream(map);

        try {

            unmap.put(clusterid, new HashMap<Integer, Integer>());
            Scanner scan2 = new Scanner(inpStream);
            while (scan2.hasNext()) {
                unmap.get(clusterid).put(scan2.nextInt(), scan2.nextInt());
            }
            //System.out.println(unmap.get(clusterid));
            inpStream.close();

        } catch (Exception ex) {
            Logger.getLogger(JSONCOnvV3.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                inpStream.close();
            } catch (IOException ex) {
                Logger.getLogger(JSONCOnvV3.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        FileInputStream inpStream = null;
        try {
            // TODO code application logic here
//System.out.println("here");
            HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> level1clusttonode = new HashMap<>();
            HashMap<Integer, HashMap<Integer, Integer>> level1nodetoclust = new HashMap<>();
            HashMap<Integer, Integer> level1 = new HashMap<>();
            HashMap<Integer, Integer> l3nodetoclust = new HashMap<>();
            HashMap<Integer, ArrayList<Integer>> l3clusttonode = new HashMap<>();
            HashMap<Integer, Integer> l2nodetoclust = new HashMap<>();
            HashMap<Integer, ArrayList<Integer>> l2clusttonode = new HashMap<>();
            HashMap<Integer, Integer> l1nodetoclust = new HashMap<>();
            HashMap<Integer, ArrayList<Integer>> l1clusttonode = new HashMap<>();
            HashMap<Integer, ArrayList<Integer>> l1clusttonode1 = new HashMap<>();
            File supergraph = new File("labeling_enron_subgraphs/enron_undir_edgelist_cluster_l3_cluster");

            inpStream = new FileInputStream(supergraph);
            Scanner scan = new Scanner(inpStream);
		
            PrintWriter out = new PrintWriter("enron.igraph.json");
            int n = 1;
            int cluster, clusterl1 = 0;
            int numclust = 0, numclustl1 = 0, numnodes = 0, numnodesl1 = 0, numnodesl2 = 0, numnodesl3 = 0;

            out.println("{\"name\": \"flare\", \"children\": [");


            while (scan.hasNext()) {
                cluster = scan.nextInt();


                if (cluster > numclust) {
                    numclust = cluster;
                }

                if (!l3clusttonode.containsKey(cluster)) {
                    l3clusttonode.put(cluster, new ArrayList<Integer>());
                }
                l3nodetoclust.put(n, cluster);
                l3clusttonode.get(cluster).add(n);

                n++;

            }
            inpStream.close();
            //System.out.println(n);
            // //System.out.println(l3clusttonode);
            // //System.out.println(l3nodetoclust);

            int nodes = n;

            File graph = new File("labeling_enron_subgraphs/enron_undir_edgelist_cluster");
            inpStream = new FileInputStream(graph);
            Scanner scan6 = new Scanner(inpStream);
            int numclustl2 = 0, clusterl2 = 0;
            n = 0;

            while (scan6.hasNext()) {
                clusterl2 = scan6.nextInt();
                if (clusterl2 > numclustl2) {
                    numclustl2 = clusterl2;
                }

                if (!l2clusttonode.containsKey(clusterl2)) {
                    l2clusttonode.put(clusterl2, new ArrayList<Integer>());
                }
                l2nodetoclust.put(n, clusterl2);
                l2clusttonode.get(clusterl2).add(n);

                n++;

            }
            //System.out.println("enron clust" + numclustl2 + "clustl2" + clusterl2);
            numnodesl2 = n;
            inpStream.close();

            ArrayList<Integer> large = new ArrayList();
            File list = new File("labeling_enron_subgraphs/list");
            inpStream = new FileInputStream(list);
            Scanner scan1 = new Scanner(inpStream);

            while (scan1.hasNext()) {
                large.add(scan1.nextInt());

            }
            inpStream.close();

            int ctr1d=1;
            int flag3 = 0;
            for (int i = 1; i <= numclust; i++) {
                if (flag3 != 0) {
                    out.println(",");
                }
                flag3=1;
                int flag2 = 0;

                out.println("{\"name\": \"cluster3d" + i + "\", \"children\": [");
                l3++;
                // //System.out.println(l3clusttonode);
                for (int j : l3clusttonode.get(i)) {
                    if (flag2 != 0) {
                       // System.err.println("boo" + j);
                        out.println(",");
                    }
                    
                   
                    out.println("{\"name\": \"cluster2d" + j + "\", \"children\": [");
                    l2++;
                    flag2 = 1;
                    File clustFile;
                    if (large.contains(j)) {


                        System.err.println(j+" "+ l1clusttonode1.size());
                        unmap("labeling_enron_subgraphs/largesubgraphs/cluster" + j + "/map" + j, j);
                        //System.out.println(unmap.get(j));
                        
                        {
                            clustFile = new File("labeling_enron_subgraphs/largesubgraphs/cluster" + j + "/subgraph" + j + "_cluster");



                            inpStream = new FileInputStream(clustFile);
                            Scanner scan2 = new Scanner(inpStream);

                            clusterl1 = 0;
                            numclustl1 = 0;
                            numnodesl1 = 0;
                            n = 0;
                            l1clusttonode.clear();
                            l1nodetoclust.clear();
                            while (scan2.hasNext()) {
                                clusterl1 = scan2.nextInt();

                                if (clusterl1 > numclustl1) {
                                    numclustl1 = clusterl1;
                                }

                                if (!l1clusttonode.containsKey(clusterl1)) {
                                    l1clusttonode.put(clusterl1, new ArrayList<Integer>());
                                }
                                l1nodetoclust.put(n, clusterl1);
                                l1clusttonode.get(clusterl1).add(n);
                                level1.put(unmap.get(j).get(n), clusterl1);

                                n++;

                            }
                            numnodesl1 = n;
                            level1clusttonode.put(j, l1clusttonode);
                            level1nodetoclust.put(j, l1nodetoclust);
                            int flag1 = 0;

                           //System.out.println("\n" + l1clusttonode.keySet());
                           // System.out.println(l1nodetoclust.keySet().size());
                            //System.out.println("\n num of clusters"+numclustl1 +"\n nodes= "+ l1nodetoclust.size() +"\n clust to node "+ l1clusttonode.keySet() +"\n map"+ unmap.get(j));
                            for (int k = 1; k <= numclustl1; k++) {
                                int flag = 0;
                                if (flag1 != 0) {
                                    out.println(",");
                                }
				System.out.println("cluster2d"+j+"cluster1d"+k+ " cluster1d"+ctr1d);
                                map1d.put("cluster2d"+j+"cluster1d"+k, "cluster1d"+ctr1d);
                               // System.err.println(ctr1d);
                                l1clusttonode1.put(ctr1d, l1clusttonode.get(k));
                               // l1clusttonode.remove(k);
                                out.println("{\"name\": \"cluster1d" + ctr1d++ + "\", \"children\": [");
                                flag1 = 1;
                              //  //System.out.println(j);
                              //  //System.out.println(l1clusttonode);
                              //  //System.out.println(l1nodetoclust);
                              //  System.err.println(ctr1d+ " "+ (ctr1d-1)+ " "+ l1clusttonode.keySet());
                                for (int l : l1clusttonode.get(k)) {
                                    {
                                    if (flag != 0) {
                                        out.println(",");
                                    }
                                    //if(unmap.get(j).get(l)==null);
                                   // System.err.println(j +" " +unmap.get(j).get(l) );
                                    out.print("{\"name\":\"" + unmap.get(j).get(l) + "\"}");
                                    if(!level1.containsKey(unmap.get(j).get(l)))
                                            System.err.println("whoops");
                                    level1.put(unmap.get(j).get(l), ctr1d-1);

                                    flag = 1;

                                }
                                }

                                out.print("]}");
                            }
                            out.print("]}");
                        }
                        
                    } 
                    else {
                        clustFile = new File("labeling_enron_subgraphs/smallsubgraphs/cluster" + j + "/map" + j);
                        inpStream = new FileInputStream(clustFile);
                        Scanner scan2 = new Scanner(inpStream);
                        int flag = 0;
                        while (scan2.hasNext()) {
                            if (flag != 0) {
                                out.println(",");
                            }
                          int nodename=scan2.nextInt();
//				System.out.println(nodename);
                          map1d.put(nodename+"", "cluster1d"+ctr1d);
                          out.println("{\"name\": \"cluster1d" + ctr1d++ + "\", \"children\": [");
                               
                            out.print("{\"name\":\"" + nodename + "\"}");
                            out.println("]}");

                            flag = 1;

                        }
                     out.print("]}");

                    }
                    ////System.out.println("BOO");

                 
                }
                out.println("]}");
            }
            out.print("],");

      //  System.out.println(map1d);
        


        //printing edge objects
 /*       File clustLinks = new File("labeling_enron_subgraphs/enron_undir_edgelist_cluster_l3");
         inpStream = new FileInputStream(clustLinks);
         Scanner scan3 = new Scanner(inpStream);

         flag3 = 0;
         out.println("\"clustlinks\": [");
         while (scan3.hasNext()) {
         if (flag3 != 0) {
         out.println(",");
         }
         out.print("{\"source\":" + scan3.hasNext() + ", \"target\":" + scan3.hasNext() + "}");
         }

         out.println("],");
         */
        File edgefilel3 = new File("labeling_enron_subgraphs/enron_undir_edgelist_cluster_l3");
        inpStream = new FileInputStream(edgefilel3);
        Scanner scan4 = new Scanner(inpStream);

        int s, t;
        ArrayList<Integer> edge = new ArrayList<>();

        while (scan4.hasNext()) {
            s = scan4.nextInt();
            t = scan4.nextInt();
            if (l3nodetoclust.get(t) == l3nodetoclust.get(s)) {
                edge = new ArrayList<>();
                edge.add(s);
                edge.add(t);

                if (!clusttoedge.containsKey(l3nodetoclust.get(t))) {
                    clusttoedge.put(l3nodetoclust.get(t), new ArrayList<ArrayList<Integer>>());
                }

                clusttoedge.get(l3nodetoclust.get(t)).add(edge);

            }
        }

        inpStream.close();
        flag3 = 0;
        for (int i = 1; i <= numclust; i++) {
            if (flag3 != 0) {
                out.println(",");
            }
            out.println("\"cluster3d" + i + "\": [");
            if (clusttoedge.containsKey(i)) {
                System.out.println(clusttoedge.get(i));
                int flag2 = 0;
                for (int j = 0; j < clusttoedge.get(i).size(); j++) {
                    if (flag2 != 0) {
                        out.println(",");
                    }
                    flag2=1;
                    out.print("{\"source\":\"cluster2d" + clusttoedge.get(i).get(j).get(0) + "\", \"target\":\"cluster2d" + clusttoedge.get(i).get(j).get(1) + "\", \"value\":1}");
                }
            }

            out.println("],");

        }
        int edgedtr=0;
        File edgefilel2 = new File("labeling_enron_subgraphs/enron_undir_edgelist");
        inpStream = new FileInputStream(edgefilel2);
        Scanner scan8 = new Scanner(inpStream);
        clusttoedge = new HashMap<>();
        
        while (scan8.hasNext()) {
            s = scan8.nextInt();
            t = scan8.nextInt();
           
          //  System.out.print(s +" "+ t+ ";");
            
           // System.out.println(l2nodetoclust.get(s)+" "+ l2nodetoclust.get(t)  +" "+ level1nodetoclust.keySet());
            //int temps=level1nodetoclust.get(l2nodetoclust.get(s)).get(s);
            //int tempt=level1nodetoclust.get(l2nodetoclust.get(t)).get(t);
           // System.out.println(temps +" "+ tempt);
           // System.out.println(level1.get(s)+ " "+ level1.get(t));
            if (l2nodetoclust.get(t) == l2nodetoclust.get(s)) {
                edge = new ArrayList<>();
                
              //  System.out.println(s+" "+t);
               
                edge.add(level1.get(s));
                edge.add(level1.get(t));

                if (!clusttoedge.containsKey(l2nodetoclust.get(t))) {
			//System.out.println("Here");
                    clusttoedge.put(l2nodetoclust.get(t), new ArrayList<ArrayList<Integer>>());
                }

                if(!clusttoedge.get(l2nodetoclust.get(t)).contains(edge)){
//System.out.println("Heretoo");
                clusttoedge.get(l2nodetoclust.get(t)).add(edge);
                }

            }
        }

        inpStream.close();


        flag3 = 0;
        for (int i = 1; i <= numclustl2; i++) {
            
            if(large.contains(i))
            {
            if (flag3 != 0) {
                out.println(",");
            }
            out.println("\"cluster2d" + i + "\": [");
          
            if (clusttoedge.containsKey(i)) {
               // System.out.println(clusttoedge.get(i));
                int flag2 = 0;
                for (int j = 0; j < clusttoedge.get(i).size(); j++) {
		if(map1d.get("cluster2d"+i+"cluster1d"+clusttoedge.get(i).get(j).get(1))==null){
System.out.println("boo3"+i+ " "+ j);
		continue;
}
	if(map1d.get("cluster2d"+i+"cluster1d"+clusttoedge.get(i).get(j).get(0))==null){
	System.out.println("boo"+i+ " "+ j);
		continue;
}
//		System.out.println("cluster2d"+i+"cluster1d"+clusttoedge.get(i).get(j).get(0)+" "+i+" "+j+ ""+map1d.get("cluster2d"+i+"cluster1d"+clusttoedge.get(i).get(j).get(0)));
//		System.out.println(i+" "+j+ ""+map1d.get("cluster2d"+i+"cluster1d"+clusttoedge.get(i).get(j).get(1)));
                    if(!map1d.get("cluster2d"+i+"cluster1d"+clusttoedge.get(i).get(j).get(0)).equals(map1d.get("cluster2d"+i+"cluster1d"+clusttoedge.get(i).get(j).get(1)))){
                    if (flag2 != 0) {
                        out.println(",");
                    }
                    
                 //if(map1d.get("cluster2d"+i + "cluster1d"+ level1nodetoclust.get(i).get(clusttoedge.get(i).get(j).get(0)))==null)
                     //System.out.println("!"+level1nodetoclust.get(i).get(clusttoedge.get(i).get(j).get(0)));
             //       System.out.println(clusttoedge.get(i).get(j).get(0));
                    
               //     System.out.println(map1d.get("cluster2d"+i+"cluster1d"+clusttoedge.get(i).get(j).get(0)));
                    
                    out.print("{\"source\":\"" + map1d.get("cluster2d"+i+"cluster1d"+clusttoedge.get(i).get(j).get(0))+ "\", \"target\":\""+map1d.get("cluster2d"+i+"cluster1d"+ clusttoedge.get(i).get(j).get(1)) + "\", \"value\":1}");
                    flag2 = 1;
                    }
                }

            }

            out.println("],");
        }
        }
        //System.out.println("IN BOOM!");
        // System.exit(0);

        //System.out.println(numclustl2);

        for (int i = 1; i <= numclustl2; i++) {
           // //System.out.println(i);
            if (!large.contains(i)) {
                File smallgraph = new File("labeling_enron_subgraphs/smallsubgraphs/cluster" + i + "/subgraph" + i);
                inpStream = new FileInputStream(smallgraph);
                Scanner scan9 = new Scanner(inpStream);

                flag3 = 0;
                // //System.out.println("labeling_enron_subgraphs/smallsubgraphs/cluster" + i + "/subgraph" + i);
                out.println("\"cluster2d" + i + "\": [");
                //    //System.out.println("enron.igraph");
//                    //System.out.println(scan9.nextInt());
                while (scan9.hasNext()) {
                     if (flag3 != 0) {
                     out.println(",");
                       //  System.err.println("foo");
                     }
                    out.print("{\"source\":\"" + map1d.get(scan9.nextInt()+"") + "\", \"target\":\"" + map1d.get(scan9.nextInt()+"") + "\", \"value\":1}");
                    flag3=1;
                }
                out.println("],");
            }
        }
        //System.out.println("out BOOM!");
            System.err.println(level1);
       clusttoedge = new HashMap<>();
        for (int k : large) {
            System.out.println("k="+k);
            
            File edgefilel1 = new File("labeling_enron_subgraphs/largesubgraphs/cluster" + k + "/subgraph" + k);
            inpStream = new FileInputStream(edgefilel1);
            Scanner scan5 = new Scanner(inpStream);


            while (scan5.hasNext()) {
                s = scan5.nextInt();
                t = scan5.nextInt();
                if (level1.get(unmap.get(k).get(s)) == level1.get(unmap.get(k).get(t))) {
                    edge = new ArrayList<>();
                    edge.add(unmap.get(k).get(s));
                    edge.add(unmap.get(k).get(t));

                    if (!clusttoedge.containsKey(level1.get(unmap.get(k).get(t)))) {
                        clusttoedge.put(level1.get(unmap.get(k).get(t)), new ArrayList<ArrayList<Integer>>());
                    }

                    clusttoedge.get(level1.get(unmap.get(k).get(t))).add(edge);
                    edgedtr++;

                }
            }
            
            inpStream.close();
        }
            flag3 = 0;
            
           // System.out.println(clusttoedge.keySet());
            
            for (int i : l1clusttonode1.keySet()) {
                
            // System.out.println(map1d.get("cluster2d"+k+"cluster1d"+i));
               /* if (flag3 != 0) {
                    out.println(",");
                }*/
               // System.out.println("boo");
               // if(i>137)
               // System.out.println(i+" "+ l1clusttonode1.get(i));
                out.println("\"cluster1d"+i+ "\": [");
                l1++;
                flag3=1;
                if (clusttoedge.containsKey(i)) {
                    int flag2 = 0;
                    for (int j = 0; j < clusttoedge.get(i).size(); j++) {
                        if (flag2 != 0) {
                            out.println(",");
                        }
                       // System.err.println(unmap.get(k).get(clusttoedge.get(i).get(j).get(0)));
                        if(level1.get(clusttoedge.get(i).get(j).get(0))!=i)
                            System.err.println("whoops again");
                        out.print("{\"source\":" + clusttoedge.get(i).get(j).get(0) + ", \"target\":" + clusttoedge.get(i).get(j).get(1) + ", \"value\":1}");
                        flag2=1;
                    }
                    
                }

                out.println("],");

            }
         //out.println("},");
        
           out.println("\"hierarchy\": ["+l3+", "+l2+", "+ctr1d+"]");
            out.println("}");
            out.close();
            System.err.println(l1clusttonode1.size());
            System.out.println(edgedtr);
    }
    catch (FileNotFoundException ex

    
        ) {
            Logger.getLogger(JSONCOnvV3.class.getName()).log(Level.SEVERE, null, ex);
    }

    
        finally {
            try {
            inpStream.close();
        } catch (IOException ex) {
            Logger.getLogger(JSONCOnvV3.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
           
    }
}/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//package MetisFormatter;
