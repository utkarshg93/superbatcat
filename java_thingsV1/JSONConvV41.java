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
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Charu
 */
public class JSONConvV41
{

    /**
     * @param args the command line arguments
     */
    public static PrintWriter p,p1;
    public static ArrayList<Integer> counters = new ArrayList<>();
    public static int nodes=0;
    
   

    public static void main(String[] args) throws FileNotFoundException, IOException
    {
        // TODO code application logic here

        p = new PrintWriter("clustering.JSON");
        p1 = new PrintWriter("EdgeObjects.JSON");
        counters.add(1);
        p.println("{\"name\": \"flare\", \"children\": [");


        printClusteringTree(args[0], 1);

        p.println("]");

        p1.close();
        File list = new File("EdgeObjects.JSON");
        FileInputStream inpStream = new FileInputStream(list);
        Scanner scan1 = new Scanner(inpStream);
        while (scan1.hasNext())
        {
            p.println(scan1.nextLine());
        }
        
        printSizeObject();
        System.out.println(counters);
        p.println("}");
        p.close();
    }

    public static List<File> listd(String directoryName)
    {
        File directory = new File(directoryName);

        List<File> resultList = new ArrayList<File>();

        // get all the files from a directory
       try{ 
       File[] fList = directory.listFiles();

        for (File file : fList)
        {
            if (file.isDirectory())
            {
                resultList.add(file);
            }
        }
}catch (Exception e){
	System.out.println(directoryName);	
	}

        return resultList;
        
    }
    

    public static List<File> listf(String directoryName)
    {
        File directory = new File(directoryName);

        List<File> resultList = new ArrayList<File>();

        // get all the files from a directory
	try{
        File[] fList = directory.listFiles();
	for (File file : fList)
        {
            if (file.isFile())
            {
                resultList.add(file);
            }
        }
}catch (Exception e){
	System.out.println(directoryName);	
	}
        
        return resultList;
    }

    public static void readLargeSubgraphs(String path, int prefix) throws IOException
    {
        if(counters.size()<prefix+1)
        {
            while((counters.size()<prefix+1))
            counters.add(0);
            //counters.add(prefix+1,0);
        }
        List<File> l = listd(path);
        boolean flag = false;
        for (int i = 0; i < l.size(); i++)
        {
            if (flag)
            {
                p.println(",");
            }

            p.println("{\"name\": \"" + l.get(i).getName() + "d" + prefix + "\", \"children\": [");
            counters.set(prefix,counters.get(prefix)+1);
            printClusteringTree(l.get(i).getAbsolutePath(), prefix + 1);


            p.println("]}");

            flag = true;
        }



    }

    public static void readSmallSubgraphs(String path, int prefix, boolean comma) throws IOException
    {
        
        if(counters.size()<prefix+1)
        {
            while((counters.size()<prefix+1))
            counters.add(0);
            //counters.add(prefix+1,0);
        }
        List<File> l = listd(path);

        boolean flag = false;
        if (comma)
        {
            flag = true;
        }

        for (int i = 0; i < l.size(); i++)
        {
            if (flag)
            {
                p.println(",");
            }

            p.println("{\"name\": \"" + l.get(i).getName() + "d" + prefix + "\", \"children\": [");

            List<File> l1 = listf(l.get(i).getPath());

            FileInputStream inpStream = new FileInputStream(l1.get(0));
            Scanner scan1 = new Scanner(inpStream);
            counters.set(prefix,counters.get(prefix)+1);
            nodes++;

            p.println("{\"name\":\"" + scan1.nextInt() + "\"}");

            p.println("]}");
            flag = true;
             scan1.close();

        }


    }

    public static void printClusteringTree(String path, int prefix) throws FileNotFoundException, IOException
    {
        File f = new File(path + "/clusterEdgeListl1");
        if (f.exists())
        {
            printEdgeObject(f, prefix);
        }
        //loadEdgelist(path, prefix);

        //System.err.println(prefix);
        File large = new File(path + "/largesubgraphs");

        if (large.exists() && large.isDirectory())
        {
            readLargeSubgraphs(path + "/largesubgraphs", prefix);
        }

        File small = new File(path + "/smallsubgraphs");

        if (small.exists() && small.isDirectory())
        {
            if (large.exists())
            {
                readSmallSubgraphs(path + "/smallsubgraphs", prefix, true);
            } else
            {
                readSmallSubgraphs(path + "/smallsubgraphs", prefix, false);
            }

        }


        if (!small.exists() && !large.exists())
        {

            loadmap(path, prefix);
            loadEdgelist(path, prefix);
        }

    }

    public static HashMap<Integer, Integer> loadmap(String path, int prefix) throws FileNotFoundException
    {
        List<File> l1 = listf(path);
        FileInputStream inpStream = null;
        if (l1.size() != 2)
        {
            System.err.println("ERROR");
            System.exit(1);
        }
        
        if(counters.size()<prefix+1)
        {
            while((counters.size()<prefix+1))
            counters.add(0);
            //counters.add(prefix+1,0);
        }

        if (l1.get(0).getName().startsWith("map"))
        {
            inpStream = new FileInputStream(l1.get(0));
        }

        if (l1.get(1).getName().startsWith("map"))
        {
            inpStream = new FileInputStream(l1.get(1));
        }
        Scanner scan1 = new Scanner(inpStream);

        HashMap<Integer, Integer> map1 = new HashMap<>();
        int a, b;
        boolean flag = false;
 
        File  parent = new File(l1.get(1).getParent());
 //       counters.set(prefix, counters.get(prefix)+1);
//        p.println("{\"name\": \" " + parent.getName() + "d" + prefix + " \", \"children\": [");
        while (scan1.hasNext())
        {

            if (flag)
            {
                p.println(",");
            }

            a = scan1.nextInt();
            b = scan1.nextInt();

            p.println("{\"name\":\"" + b + "\"}");
            nodes++;
            flag = true;

            map1.put(a, b);
        }

 //       p.println("]}");

       scan1.close();

        return map1;


    }

    public static HashMap< Integer, ArrayList<Integer>> loadEdgelist(String path, int prefix) throws FileNotFoundException
    {
        
        HashMap< Integer, ArrayList<Integer>> adj = new HashMap<>();
        List<File> l1 = listf(path);
        File parent = null;
        FileInputStream inpStream = null;
        if (l1.size() != 2)
        {
            System.err.println("ERROR");
            System.exit(1);
        }

        if (l1.get(0).getName().startsWith("subgraph"))
        {
            inpStream = new FileInputStream(l1.get(0));
            parent = new File(l1.get(0).getParent());
        }

        if (l1.get(1).getName().startsWith("subgraph"))
        {
            inpStream = new FileInputStream(l1.get(1));
            parent = new File(l1.get(1).getParent());
        }
        Scanner scan1 = new Scanner(inpStream);


        p1.println(",\"" + parent.getName() + "d" + (prefix-1) + "\":[");

        int s, t;
        boolean flag = false;


        while (scan1.hasNext())
        {

            if (flag)
            {
                p1.println(",");
            }
            s = scan1.nextInt();
            t = scan1.nextInt();

            p1.println("{\"source\":" + s + ", \"target\":" + t + ", \"value\":1}");
            flag = true;





        }
        p1.println("]");

	 scan1.close();
        return adj;

    }
    
   public static void printSizeObject()
   {
       p.println(",\"sizes\":[");
       boolean flag=false;
       int total=0;
       for(int ctr : counters)
       {
           if(flag)
           {
               p.print(",");
           }
           
           p.print(ctr);
           total+=ctr;
           flag=true;
           
       }
       p.println(","+nodes);
       System.out.println(total + "+ "+nodes);
        
       p.println("]");
       
   }

    public static void printEdgeObject(File f, int prefix) throws FileNotFoundException
    {
//        if(counters.size()<prefix+1)
//        {
//            while((counters.size()<prefix+1))
//            counters.add(0);
//            //counters.add(prefix+1,0);
//        }
        FileInputStream inpStream = new FileInputStream(f);
        Scanner scan1 = new Scanner(inpStream);
        File parent = new File(f.getParent());
        int suffix=0;
        if (prefix == 1)
        {
            p1.println(",\"clustlinks\":[");
            suffix=prefix;
        } else
        {
            p1.println(",\"" + parent.getName() + "d" + (prefix-1) + "\":[");
            suffix=prefix+1;
           // counters.set(prefix, counters.get(prefix)+1);
        }
        int s, t;
        boolean flag = false;
        while (scan1.hasNext())
        {

            if (flag)
            {
                p1.println(",");
            }

            s = scan1.nextInt();
            t = scan1.nextInt();
	    if (prefix == 1)
            	p1.println("{\"source\": \"cluster" + s + "d1\"" +", \"target\": \"cluster" + t + "d1\"" + ", \"value\":1}");
            
	    else		
            	p1.println("{\"source\": \"cluster" + s + "d" + (suffix-1) + "\", \"target\":\"cluster" + t + "d" + (suffix-1) + "\", \"value\":1}");
            
            flag = true;
        }

        scan1.close();
        p1.println("]");
    }
}
