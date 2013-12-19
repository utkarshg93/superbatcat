/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//package toplevel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author utkarsh
 */
public class topl {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        // TODO code application logic here
        File file =new File("labeling_enron_subgraphs/"+args[0]);
        File file2 =new File("labeling_enron_subgraphs/"+args[1]);
        PrintWriter clustlinks = new PrintWriter("labeling_enron_subgraphs/clustlinks");
        HashMap<Integer,Integer> nodetocluster= new HashMap<Integer,Integer>();
    Scanner charu = new Scanner(new FileInputStream(file2));
	System.out.println(file2);
    int nodes=0;
    int ctr=1;
    while(charu.hasNext()){
        nodes++;
        int val = charu.nextInt();
    nodetocluster.put(ctr, val);
    ctr++;
    }
   
    Scanner panda = new Scanner(new FileInputStream(file));
    int j;
    int k;
   // System.out.println("vwjbvikweb");
    while(panda.hasNext()){
        j=panda.nextInt();
        k=panda.nextInt();
       // System.out.println(j + " "+k);
       // System.out.println(k + " "+nodetocluster.get(k+1));
       if(!nodetocluster.get(k+1).equals(nodetocluster.get(j+1))){
            clustlinks.println(nodetocluster.get(j) +" "+nodetocluster.get(k));
        }
    }
    clustlinks.close();
    }
}

