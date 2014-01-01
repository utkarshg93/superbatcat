//package jsonconvv2;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author charu
 */
public class JSONConvV2 {

    static HashMap<Integer, ArrayList<Integer>> cluster = new HashMap<Integer, ArrayList<Integer>>();
    static HashMap<Integer, ArrayList<ArrayList<Integer>>> edges = new HashMap<Integer, ArrayList<ArrayList<Integer>>>();
    static HashMap<Integer, Integer>  node= new HashMap<Integer, Integer>();
    static HashMap <Integer, Integer> tempid= new HashMap<Integer, Integer>();
    static HashMap <Integer, Integer> clustertocluster = new HashMap<Integer, Integer>();
   static  int max;
   static int [][] wt;
    public static void map(String file1, String file2) {
	
        int nodes = 0;
        try {
	    //36692 183831
            PrintWriter out = new PrintWriter(file1+"_l3");
            File inputFile = new File(file1);
            FileInputStream inpStream = null;
            int c=0;
            inpStream = new FileInputStream(inputFile);
            Scanner scan = new Scanner(inpStream);
            while (scan.hasNext()) {
                c = scan.nextInt();
                if (c>max)
                    max=c;
                node.put(nodes, c);
                if (!cluster.containsKey(c)) {
                    cluster.put(c, new ArrayList());
                }
		if(!edges.containsKey(c))
                edges.put(c, new ArrayList());
                cluster.get(c).add(nodes);
		nodes++;
		
            }
          //  System.out.println(nodes);
        //    System.exit(0);
            File inp = new File(file2);
            FileInputStream in = null;

            in = new FileInputStream(inp);
            Scanner scan1 = new Scanner(in);
            
            wt= new int[max+1][max+1];
            for (int i=0; i< max; i++)
                for(int j=0; j<max; j++)
                    wt[i][j]=0;
            int n, m, j, k;
            n = nodes;
            m = 0;
		
            while(scan1.hasNext())
            {
                m++;
                 j = scan1.nextInt();
                 k = scan1.nextInt();
		     
                 if(node.get(k).equals(node.get(j)))
                 {
                     ArrayList a = new ArrayList(2);
                     a.add(j);
                     a.add(k);
                     edges.get(node.get(j)).add(a);
                 }
                
                 else
                 {
                     
//                 out.write((node.get(k)-1)+"\t"+(node.get(j)-1)+"\n");
                    // clustertocluster.put(node.get(j), max)
                     wt[node.get(j)][node.get(k)]++;
                     wt[node.get(k)][node.get(j)]++;
                     if(wt[node.get(j)][node.get(k)]==1 && wt[node.get(k)][node.get(j)]==1){
                     out.write((node.get(k)-1)+"\t"+(node.get(j)-1)+"\n");
                     }
  
                 }
		}
            
	//System.out.println(m);
        
	out.close();	
       // System.exit(0);        
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JSONConvV2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void subgraphs()
    {
	try{
            File out5= new File("labeling_enron_subgraphs/largesubgraphs");
            File out6 = new File("labeling_enron_subgraphs/smallsubgraphs");
            out5.mkdirs();
	    out6.mkdirs();
	PrintWriter chotu = new PrintWriter("labeling_enron_subgraphs/list");	
       PrintWriter out2 = new PrintWriter(out5+"/list");	
       PrintWriter out3 = new PrintWriter(out6+"/list"); 
       for (int i=1; i<=cluster.size(); i++)
        {
          //  System.out.println(cluster.get(i).size());
		//System.exit(0);
            if(cluster.get(i).size() > 50)
            {
                int ctr=0;
                int temp1, temp2;
                chotu.println(i);
                tempid= new HashMap<Integer, Integer>();
                
                PrintWriter out = null, out1=null;
                try {
		    File out7= new File(out5+"/cluster"+i);
		    out7.mkdirs();
                    out = new PrintWriter(out7+"/subgraph"+i+"");
                    out1= new PrintWriter(out7+"/map"+i+"");
		   
		    int count = 0;
		    for(int j=0; j<cluster.get(i).size();j++){
			if(!tempid.containsKey(cluster.get(i).get(j)))
                            tempid.put(cluster.get(i).get(j), j);			
			}		
                    for(int j=0; j<edges.get(i).size(); j++)
                    {
                        temp1=edges.get(i).get(j).get(0);
                        temp2=edges.get(i).get(j).get(1);
                        /*if(!tempid.containsKey(temp1))
                            tempid.put(temp1, ctr++);
                        if(!tempid.containsKey(temp2))
                            tempid.put(temp2, ctr++);*/
                        out.println(tempid.get(temp1)+ "\t" + tempid.get(temp2));
                    }
                    
                   for (Integer key : tempid.keySet()) {	
				out1.println(tempid.get(key)+ "\t" + key);
				count++;
			}
		
		out2.println("labeling_enron_subgraphs/largesubgraphs/cluster"+i+"/subgraph"+i+" "+cluster.get(i).size()+" "+"labeling_enron_subgraphs/largesubgraphs/cluster"+i+"/map"+i+" "+"labeling_enron_subgraphs/largesubgraphs/cluster"+i+"_1");
		out1.close();
                } catch (Exception ex) {
                    Logger.getLogger(JSONConvV2.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    out.close();
                }
            }
	else{
		File out8 = new File(out6+"/cluster"+i);
		    out8.mkdirs();
		PrintWriter out = new PrintWriter(out8+"/subgraph"+i+"");
                out3.println(out8+"/subgraph"+i);
		PrintWriter outb = new PrintWriter(out8+"/map"+i+"");
                //outb.println("BOOM");
                int temp1;
                int temp2;
		//System.out.println(cluster.get(i) + " " + cluster.get(i).size() +" "+i);
//System.out.println(out8+"/map"+i);
                for(int charu=0;charu<cluster.get(i).size();charu++){
                //    System.out.println(cluster.get(i).get(charu));
		
                    outb.println(cluster.get(i).get(charu));
                }
               //System.exit(0);
                
                for(int j=0; j<edges.get(i).size(); j++)
                    {
                        temp1=edges.get(i).get(j).get(0);
                        temp2=edges.get(i).get(j).get(1);
//			System.out.println(temp1 +" "+ temp2+ " " +tempid.get(temp1));
                     //   out.println("ikebniknbev");
                        out.println(temp1+ "\t" + temp2);
			//outb.println(temp1);
                    }
//		out8.close();
                out.close();
		outb.close();
		
	}
        }
	out2.close();
        out3.close();
	chotu.close();
	}catch (Exception ex) {
                    Logger.getLogger(JSONConvV2.class.getName()).log(Level.SEVERE, null, ex);
                }
	
    }
    public static void main(String[] args) {
        FileInputStream inpStream = null;

        try {

            String input, output="enron1.json";
            map(args[0], args[1]); 
	    subgraphs();
        } catch (Exception ex) {
            Logger.getLogger(JSONConvV2.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    }
}
