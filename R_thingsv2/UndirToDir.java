/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//package MetisFormatter;

/**
 *
 * @author Charu
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author charu
 */
public class UndirToDir {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        // TODO code application logic here
        System.out.println(args.length);
        File inp = new File("enron_undir");
            FileInputStream in = new FileInputStream(inp);
            Scanner scan = new Scanner(in);
             int n, m, j, k;
             int edgectr=0;
            n = scan.nextInt();
            m = scan.nextInt();
	System.out.println(n+ " " +m);
            ArrayList <ArrayList <Integer>> edges= new ArrayList<>(n);
            for(int i=0; i<n; i++)
            {
                 edges.add(i, new ArrayList<Integer>());
            }
           
            while(scan.hasNext())
            {
                 j = scan.nextInt();
                 k = scan.nextInt();
                 
                 if (j<k)
                 {
                     if(!edges.get(j).contains(k))
                     {
                         edges.get(j).add(k);
                         edgectr++;
                     }
                 }
                 else
                 {
                     if(!edges.get(k).contains(j))
                     {
                         edges.get(k).add(j);
                         edgectr++;
                     }
                 }
            }
            
            PrintWriter outFile = new PrintWriter("enron_undir_edgelist");
            int ctr=0;
            outFile.println(n+ "\t" + edgectr);
            for(int i=0; i<n; i++)
            {
                 if (edges.get(i).size()!=0)
                 for(j=0; j<edges.get(i).size(); j++)
                     
                    {
                        outFile.println(i+"\t" + edges.get(i).get(j));
                        ctr++;
                    }
            }
            System.out.println(ctr + " edges found "+edgectr);
            outFile.close();
            
        
    }
}
