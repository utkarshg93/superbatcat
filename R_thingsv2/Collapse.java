/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//package MetisFormatter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author Charu
 */
public class Collapse
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException
    {
        // TODO code application logic here
         File inputFile = new File("enron");
         FileInputStream inpStream = new FileInputStream(inputFile);
         Scanner scan = new Scanner (inpStream);
         
         HashMap<Integer, ArrayList<Integer>> coll = new HashMap<>();
         HashMap<Integer, Integer> map = new HashMap<>();
         HashMap<Integer, ArrayList<Integer>> neighbours = new HashMap<>();
         int source=-1, s,t, del=0;
	 int charu=scan.nextInt();
	 int prtaeek=scan.nextInt();
	 System.out.println(charu);
	 System.out.println(prtaeek);
         while (scan.hasNext())
         {
            
             s = scan.nextInt();
             t = scan.nextInt();
             
             if(!neighbours.containsKey(s))
             {
                 neighbours.put(s, new ArrayList<Integer>());
       
             }
             
//             if(!neighbours.containsKey(t))
//             {
//                 neighbours.put(t, new ArrayList<Integer>());
//       
//             }
//             neighbours.get(t).add(s);
             neighbours.get(s).add(t);
             
             source=s;
             
         
         }
         int flag=0;
         System.out.println(neighbours.size());
         do
         {
             ArrayList<Integer> a = new ArrayList<>();
          
           
         for(int n : neighbours.keySet())
         {
             flag=0;
            // if(!coll.containsKey(n))
             {
                 if(neighbours.get(n).size()==1)
                 {
                     a.add(n);
                     flag=1;
                     //correct this
                     coll.put( n,new ArrayList<Integer>());
                     if(coll.containsKey(neighbours.get(n).get(0)))
                             {
                               
                               coll.put(n,coll.get(neighbours.get(n).get(0)) );
                               coll.remove(neighbours.get(n).get(0));
                               
                             }
			try{
                     coll.get(n).add(neighbours.get(n).get(0));
			}catch (Exception e){
			System.out.println(n);	
		}
                 }
                
                // neighbours.remove(n);
                 
             }
         }
          for(int c : a)
             {
                 neighbours.remove(c);
                 del++;
             }
         
         }while(flag==1);
         
         
         int ctr=0, edgectr=0;;
         PrintWriter p1 = new PrintWriter("enron_undir_edgelist_collapse_map");
         for(int n : neighbours.keySet())
         {
             if(!map.containsKey(n))
                 {
                 map.put(n, ctr+1);
                 p1.println(n+ "\t"+ ++ctr);
                 }
             for(int m : neighbours.get(n))
             {
                 edgectr++;
                 if(!map.containsKey(m))
                 {
                 map.put(m, ctr+1);
                 p1.println(m+ "\t"+ ++ctr);
                 }
                 
             }
         }
         
         p1.close();
         
         System.out.println(map.size()+ "\t"+ edgectr);;
         PrintWriter p = new PrintWriter("enron_undir");
         p.println(map.size()+ "\t"+ edgectr);
         for(int n : neighbours.keySet())
         {
             for(int m : neighbours.get(n))
             {
                 p.println(map.get(n)+ "\t"+ map.get(m));
             }
         }
         
         p.close();
         System.out.println(coll);
        System.out.println(neighbours.size()); 
        System.err.println(del);
         
    }
}
