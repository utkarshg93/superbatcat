/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//package javaapplication2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 *
 * @author utkarsh
 */
public class Step2 {

    static TreeMap<Integer, TreeSet<Integer>> adjlist = new TreeMap<Integer, TreeSet<Integer>>();
    static TreeMap<Integer, TreeSet<Integer>> adjListCluster = new TreeMap<Integer, TreeSet<Integer>>();
    static TreeMap<Integer, TreeSet<Integer>> newadjlist = new TreeMap<Integer, TreeSet<Integer>>();
    static TreeMap<Integer, TreeSet<Integer>> clusters = new TreeMap<Integer, TreeSet<Integer>>();
    static TreeMap<Integer, TreeSet<Integer>> linksRemoved = new TreeMap<Integer, TreeSet<Integer>>();
    static TreeSet<Integer> largeDegreeNodes = new TreeSet<Integer>();
    static Set<Integer> affectedNodes = new HashSet<Integer>();
    static TreeMap<Integer, Integer> nodeToClusterMap = new TreeMap<Integer, Integer>();
    static TreeMap<Integer, TreeMap<Integer, Integer>> clusterMap = new TreeMap<Integer, TreeMap<Integer, Integer>>();
    static TreeMap<Integer, TreeSet<Integer>> clustLinks = new TreeMap<Integer, TreeSet<Integer>>();
    static int k = 700, clustCount = 1;
    static int[] clusterDegree;
    static int ctr;
    static int printed=0;
    static void makeadjlist(String filename) throws FileNotFoundException, IOException {
        File inputFile = new File(filename);
        FileInputStream inpStream = new FileInputStream(inputFile);
        Scanner scan = new Scanner(inpStream);
        while (scan.hasNext()) {
            int src = scan.nextInt();
            int tar = scan.nextInt();
            if (adjlist.containsKey(src)) {
                adjlist.get(src).add(tar);
            } else {
                TreeSet<Integer> temp = new TreeSet<Integer>();
                temp.add(tar);
                adjlist.put(src, temp);
            }

            if (adjlist.containsKey(tar)) {
                adjlist.get(tar).add(src);
            } else {
                TreeSet<Integer> temp = new TreeSet<Integer>();
                temp.add(src);
                adjlist.put(tar, temp);
            }
        }

        newadjlist.putAll(adjlist);
        //System.out.println("listing " + adjlist);
    }

    public static void removeLargeDegreeNodes() {
        TreeSet<Integer> linksTemp = new TreeSet<Integer>();
        for (int i : adjlist.keySet()) {
            if (adjlist.get(i).size() >= k) {

                for (Integer x : adjlist.get(i)) {
                    if (!linksRemoved.containsKey(x)) {
                        linksTemp = new TreeSet<Integer>();
                        linksTemp.add(i);
                        linksRemoved.put(x, linksTemp);
                    } else {
                        linksTemp = new TreeSet<Integer>();
                        linksTemp.addAll(linksRemoved.get(x));
                        linksTemp.add(i);
                        linksRemoved.put(x, linksTemp);
                    }
                }

                newadjlist.remove(i);
            }
        }
    }

    public static void getCollapsedConnectedComponents() {
        int i = 0;
        TreeMap<Integer, TreeSet<Integer>> adjListTemp = new TreeMap<Integer, TreeSet<Integer>>();
        TreeSet<Integer> clusterTemp;
        LinkedList<Integer> valuesOfi = new LinkedList<Integer>();
        LinkedList<Integer> allValuesOfi = new LinkedList<Integer>();
        TreeSet<Integer> nodesMarked = new TreeSet<Integer>();

        adjListTemp.putAll(newadjlist);
        while (!adjListTemp.isEmpty()) {
            clusterTemp = new TreeSet<Integer>();
            valuesOfi.add(adjListTemp.firstKey());
            allValuesOfi.add(adjListTemp.firstKey());
            while (!valuesOfi.isEmpty()) {
                i = valuesOfi.poll();
                if (affectedNodes.contains(i)) {
                    clusterTemp.add(i);
                    nodesMarked.add(i);
                    if (!adjListCluster.containsKey(clustCount)) {
                        adjListCluster.put(clustCount, linksRemoved.get(i));
                    } else {
                        TreeSet<Integer> get = adjListCluster.get(clustCount);
                        get.addAll(linksRemoved.get(i));
                        adjListCluster.put(clustCount, get);
                    }
                }

                for (Integer x : adjListTemp.get(i)) {
                    
                    if (!largeDegreeNodes.contains(x)) {
                        if (!nodesMarked.contains(x)) {
                         
                            clusterTemp.add(x);
                            //nodesMarked.add(x);
                        }
                        if (affectedNodes.contains(x)) {
                            if (!adjListCluster.containsKey(clustCount)) {
                                adjListCluster.put(clustCount, linksRemoved.get(x));
                            } else {
                                TreeSet<Integer> get = adjListCluster.get(clustCount);
                                get.addAll(linksRemoved.get(x));
                                adjListCluster.put(clustCount, get);
                            }
                        }
                    }

                    if (!allValuesOfi.contains(x) && !largeDegreeNodes.contains(x) && !nodesMarked.contains(x)) {
                       
                        valuesOfi.add(x);
                        allValuesOfi.add(x);
                    }
                }

                //System.out.println("all:" + allValuesOfi);
                adjListTemp.remove(i);
            }
            //System.out.println("cluster" + clustCount + " ::" + clusterTemp.size());
            clusters.put(clustCount++, clusterTemp);
        }
    }

    public static void fuseLargeToClusters() {
        TreeMap<Integer, Integer> temp = new TreeMap<Integer, Integer>();
        Integer putIn;
        for (Integer i : adjListCluster.keySet()) {
            if (adjListCluster.get(i).size() == 1) {
                if (!temp.containsKey(adjListCluster.get(i).first())) {
                    temp.put(adjListCluster.get(i).first(), i);
                    TreeSet<Integer> get = clusters.get(i);
                    get.addAll(adjListCluster.get(i));
                    clusters.put(i, get);
                    largeDegreeNodes.remove(adjListCluster.get(i).first());
                } else {
                    putIn = temp.get(adjListCluster.get(i).first());
                    TreeSet<Integer> get = clusters.get(putIn);
                    get.addAll(adjListCluster.get(i));
                    get.addAll(clusters.get(i));
                    clusters.put(putIn, get);
                    clusters.put(i, null);
                    //clusters.remove(i);
                    //clustCount--;
                    /*for(int j=i+1; j<clusters.lastKey();j++){
                    TreeSet<Integer> toShift = clusters.get(j);
                    clusters.put(j-1, toShift);
                    clusters.remove(j);
                    toShift = adjListCluster.get(j);
                    adjListCluster.put(j-1, toShift);
                    adjListCluster.remove(j);
                    }*/
                    //System.out.println("Putin : " + putIn + " cluster: " + i + " final nodes here :" + clusters.get(putIn));
                }

            }
        }
        removeExtraClusters();
        clustCount = clusters.size() + 1;

        //   System.err.println("Large " + largeDegreeNodes.size());
        for (Integer node : largeDegreeNodes) {

            TreeSet<Integer> temp1 = new TreeSet<Integer>();
            temp1.add(node);
            clusters.put(clustCount++, temp1);
        }
        System.err.println("Total Clusters:" + clusters.size());
    }

    /*
     * COPY INDEPENDENT OF KEY BECAUSE CLUSTER_IDS IN BETWEEN ARE MISSING> WRITE SEQUENTIAL IDS.
     */
    public static void getNodeToClusterMap() {
        int clusterCount = 1;
        for (Integer cluster : clusters.keySet()) {
            for (Integer node : clusters.get(cluster)) {

                nodeToClusterMap.put(node, clusterCount);
            }
            clusterCount++;
        }
        
    }

    public static void writeLargeClusters(String path) throws FileNotFoundException, IOException {
        clusterDegree = new int[clusters.size() + 1];
        for (int i = 1; i < clusterDegree.length; i++) {
            clusterDegree[i] = 0;
        }
        
        File clustlinks = new File(path);
        clustlinks.mkdirs();
        clustlinks = new File(path + "/clusterEdgeListl1");
        PrintWriter clustLinksWriter = new PrintWriter(clustlinks);
        PrintWriter clusterWriter = new PrintWriter(path + "/enron_clustIds");
        PrintWriter inClusterWriter;

        for (Integer x : nodeToClusterMap.keySet()) {
            clusterWriter.println(nodeToClusterMap.get(x));
            for (Integer y : adjlist.get(x)) {
                if (nodeToClusterMap.get(x).equals(nodeToClusterMap.get(y))) {

                    File file = new File(path + "/largesubgraphs/cluster" + (nodeToClusterMap.get(x)+ctr));
                    
		   if(file.mkdirs()){
//			System.out.println(x);
	                   printed++;
	} 
                    int _x, _y, clustId = nodeToClusterMap.get(x);
                    clusterDegree[clustId]++;
                    if (clusterMap.containsKey(clustId)) {
                        TreeMap<Integer, Integer> nodeMap = clusterMap.get(clustId);
                        if (!nodeMap.containsKey(x)) {

                            nodeMap.put(x, nodeMap.size());
                            _x = nodeMap.size() - 1;
                            file = new File(path + "/largesubgraphs/cluster" + (nodeToClusterMap.get(x)+ctr) + "/map" + (nodeToClusterMap.get(x)+ctr));
                            inClusterWriter = new PrintWriter(new FileWriter(file, true));
                            inClusterWriter.println((_x+ctr) + "\t" + (x+ctr));
                            inClusterWriter.close();
                        } else {
                            _x = nodeMap.get(x);
                        }
                        if (!nodeMap.containsKey(y)) {
                            nodeMap.put(y, nodeMap.size());
                            _y = nodeMap.size() - 1;
                            file = new File(path + "/largesubgraphs/cluster" + (nodeToClusterMap.get(x)+ctr) + "/map" + (nodeToClusterMap.get(x)+ctr));
                            inClusterWriter = new PrintWriter(new FileWriter(file, true));
                            inClusterWriter.println((_y+ctr) + "\t" + (y+ctr));
                            inClusterWriter.close();
                        } else {
                            _y = nodeMap.get(y);
                        }
                    } else {
                        TreeMap<Integer, Integer> nodeMap = new TreeMap<Integer, Integer>();
                        _x = 0;
                        _y = 1;
                        nodeMap.put(x, _x);
                        nodeMap.put(y, _y);
                        clusterMap.put(clustId, nodeMap);
                        file = new File(path + "/largesubgraphs/cluster" + (nodeToClusterMap.get(x)+ctr) + "/map" + (nodeToClusterMap.get(x)+ctr));
                        inClusterWriter = new PrintWriter(new FileWriter(file, true));
                        inClusterWriter.println((_x+ctr) + "\t" + (x+ctr));
                        inClusterWriter.println((_y+ctr) + "\t" + (y+ctr));
                        inClusterWriter.close();
                    }

                    file = new File(path + "/largesubgraphs/cluster" + (nodeToClusterMap.get(x)+ctr) + "/subgraph" + (nodeToClusterMap.get(x)+ctr));
                    inClusterWriter = new PrintWriter(new FileWriter(file, true));
                    if(x<y)
                    inClusterWriter.println((ctr+x) + "\t" + (y+ctr));
                    inClusterWriter.close();

                } else {

                    Integer sCluster = nodeToClusterMap.get(x);
                    Integer tCluster = nodeToClusterMap.get(y);
                    if (tCluster == null) {
                        System.out.println("Null at node: " + y);
                    }
                    if (!clustLinks.containsKey(sCluster)) {
                        TreeSet<Integer> targets = new TreeSet<Integer>(); //Target clusters
                        targets.add(tCluster);

                        clustLinks.put(sCluster, targets);
                        if (sCluster < tCluster) {
                            clustLinksWriter.println(((sCluster )+ctr) + "\t" + ((tCluster )+ctr));
                        }
                    } else if (!(clustLinks.get(sCluster).contains(tCluster))) {
                        TreeSet<Integer> targets = clustLinks.get(sCluster);
                        targets.add(tCluster);
                        clustLinks.put(sCluster, targets);
                        if (sCluster < tCluster) {
                            clustLinksWriter.println(((sCluster )+ctr) + "\t" + ((tCluster )+ctr));
                        }
                    }

                }
            }
        }
        clusterWriter.close();
        clustLinksWriter.close();
       
       PrintWriter list = new PrintWriter(path + "/list");
       for (int i = 1; i <clusterDegree.length; i++) {
            //System.out.println(i+ " "+clusterDegree[i]);
            if(clusterDegree[i]>0){
               //System.out.println(path + "largesubgraphs/cluster" + i);
               list.println(path + "largesubgraphs/cluster" + (i+ctr) +" "+(i+ctr));
            }
        }
         list.close();
    }

    private static void removeExtraClusters() {
        TreeMap<Integer, TreeSet<Integer>> clustersTemp = new TreeMap<Integer, TreeSet<Integer>>();
        //System.out.println("lastkey" + clusters.lastKey());
        int count = 1;
        for (int i = 0; i <= clusters.lastKey(); i++) {
            if (clusters.get(i) != null) {
                clustersTemp.put(count++, clusters.get(i));

            }
        }
        clusters = new TreeMap<Integer, TreeSet<Integer>>();
        clusters.putAll(clustersTemp);

    }

    private static void writeSmallClusters(String path) throws FileNotFoundException {
        File file;

        PrintWriter smallClusterWriter;
        for (int i = 1; i <= clusters.size(); i++) {
	    
            if (clusters.get(i).size() <= 1) {
//		System.out.println(i);
                clusterDegree[i] = 0;
                file = new File(path + "/smallsubgraphs/cluster" + (i+ctr));
                file.mkdirs();
                file = new File(path + "/smallsubgraphs/cluster" + (i+ctr) + "/map" + (i+ctr));
                smallClusterWriter = new PrintWriter(file);
                smallClusterWriter.println(clusters.get(i).first());
                smallClusterWriter.close();
		printed++;
            }
        }
    }

    public static void runFirstLevelClustering(String file, String path, double alpha) throws FileNotFoundException, IOException {
        adjlist = new TreeMap<Integer, TreeSet<Integer>>();
        adjListCluster = new TreeMap<Integer, TreeSet<Integer>>();
        newadjlist = new TreeMap<Integer, TreeSet<Integer>>();
        clusters = new TreeMap<Integer, TreeSet<Integer>>();
        linksRemoved = new TreeMap<Integer, TreeSet<Integer>>();
        largeDegreeNodes = new TreeSet<Integer>();
        affectedNodes = new HashSet<Integer>();
        nodeToClusterMap = new TreeMap<Integer, Integer>();
        clusterMap = new TreeMap<Integer, TreeMap<Integer, Integer>>();
        clustLinks = new TreeMap<Integer, TreeSet<Integer>>();
        clustCount = 1;


        makeadjlist(file);
        
        int maxDegree = getMaxDegree();
        int minDegree = getMinDegree();
        System.out.println("max degree: " + maxDegree + "min degree: " + minDegree);
        do {
            k = (int) (maxDegree * alpha);
            alpha += alpha * 0.2;
        } while (k <= minDegree);
        if (k <= 1) {
            return;
        }
        System.out.println("running for k: " + k);
        removeLargeDegreeNodes();
        affectedNodes = linksRemoved.keySet();
        for (Integer x : linksRemoved.keySet()) {
            largeDegreeNodes.addAll(linksRemoved.get(x));
        }
       
        getCollapsedConnectedComponents();
        /*System.out.println("large degree nodes: " + largeDegreeNodes.size());
        System.out.println("Ajd List");
        for (Integer i : adjListCluster.keySet()) {
        System.out.println(i + " " + adjListCluster.get(i));
        }*/
        fuseLargeToClusters();
        getNodeToClusterMap();
        /*for (int i = 1; i < clusters.size() + 1; i++) {
        // t += clusters.get(i).size();
        if (clusters.get(i).contains((Integer)1918)) {
        System.out.println("cluster" + i + " " + clusters.get(i));
        }
        }*/
        writeLargeClusters(path);
        writeSmallClusters(path);
        writeClusterDegree(path);

    }

    public static void main(String args[]) throws FileNotFoundException, IOException {
        String path;

        path = "/home/utkarsh/superbatcat/java_thingsV1/clusters/";
        path =args[0];
        File f = new File(path);
        f.mkdir();
        String initial_file = "/home/utkarsh/superbatcat/R_thingsv2/stuff/Datasets/withoutremoval";
        
        ctr = Integer.parseInt(args[2]);
         
        runFirstLevelClustering(args[1], path, 0.5);

//        File globalcluster = new File(args[1]);
         
         PrintWriter globalcluster = new PrintWriter(args[3]);
         System.out.println(clusters.size() + " "+ctr+" "+printed);
        System.err.println(clusters.size() + " "+ctr+" "+printed);
         
         globalcluster.println(clusters.size() + ctr);
         globalcluster.close();
        
 
    }

    private static void writeClusterDegree(String path) throws FileNotFoundException {
        File file = new File(path + "/clusterDegrees");
        PrintWriter clusterDegreeWriter = new PrintWriter(file);
        for (int i = 1; i < clusterDegree.length; i++) {
            clusterDegreeWriter.println(clusterDegree[i]);
        }
        clusterDegreeWriter.close();
    }

    private static int getMaxDegree() {
        int max = Integer.MIN_VALUE;
        for (Integer x : adjlist.keySet()) {
            if (adjlist.get(x).size() > max) {
                max = adjlist.get(x).size();
            }
        }
        return max;
    }

    private static int getMinDegree() {
        int min = Integer.MAX_VALUE;
        for (Integer x : adjlist.keySet()) {
            if (adjlist.get(x).size() < min) {
                min = adjlist.get(x).size();
            }
        }
        return min;
    }
}
