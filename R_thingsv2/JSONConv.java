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
 * @author charu
 */
public class JSONConv {

    /**
     * @param args the command line arguments
     */
    static HashMap<Integer, ArrayList<Integer>> cluster = new HashMap<Integer, ArrayList<Integer>>();
    static HashMap<Integer, ArrayList<ArrayList<Integer>>> edges = new HashMap<Integer, ArrayList<ArrayList<Integer>>>();
    static HashMap<Integer, Integer>  node= new HashMap<Integer, Integer>();
   static  int max;
   static int [][] wt;

    public static void map(String file1, String file2) {
        int nodes = 0;
        try {
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
                edges.put(c, new ArrayList());
                cluster.get(c).add(nodes);
              nodes++;
            }

            File inp = new File(file2);
            FileInputStream in = null;

            in = new FileInputStream(inp);
            Scanner scan1 = new Scanner(in);
            
            wt= new int[max+1][max+1];
            System.out.println(max);
            for (int i=0; i< max; i++)
                for(int j=0; j<max; j++)
                    wt[i][j]=0;
            int n, m, j, k;
           // n = scan1.nextInt();
           // m = scan1.nextInt();
           // for(int i=0; i<m; i++)
            while(scan1.hasNext())
            {
                 j = scan1.nextInt();
                 k = scan1.nextInt();
                 if(node.get(k)==node.get(j))
                 {
                     ArrayList a = new ArrayList(2);
                     a.add(j);
                     a.add(k);
                     edges.get(node.get(j)).add(a);
                    // System.out.println("-");
                 }
                
                 else
                 {
                     //System.out.println("!");
                     wt[node.get(j)][node.get(k)]++;
                     wt[node.get(k)][node.get(j)]++;
                     
                 }    
            }
                
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JSONConv.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        FileInputStream inpStream = null;

        try {
            // TODO code application logic hereFile inputFile = new File(input);

            String input, output="enron_eigen_1.json";
            map( "enron_undir_edgelist_connected","enron_undir_edgelist");
           PrintWriter outFile = null;
           outFile = new PrintWriter(output);
           outFile.printf("{\"name\": \"flare\", \"children\": [\n");
           for(int i=0;i<max;i++){
	   outFile.printf("{\"name\": \"cluster%d\", \"children\": [ \n",i);
	if(cluster.get(i)==null){

	 outFile.printf("\n]} ,\n");
		continue;
}
	   for(int j=0;j<cluster.get(i).size();j++){
		
		if(j!=0)
		outFile.printf(",\n");
                
	        outFile.printf("{\"name\": \"%d\" }",cluster.get(i).get(j));
        }
        if(i!=max-1)
		outFile.printf("] },\n");
 }

 outFile.printf("\n]}] ,\n");
 
 outFile.printf( "\"clustlinks\": [");
 int temp=0;
            System.out.println("!"+max);
 for(int i=0;i<=max;i++)
 {
  for(int j=0;j<=max;j++)
  {
   if (wt[i][j]>0  && i!=j)
   {
    if (temp==1)
     outFile.printf( ",\n");
    
    if(i> max || j> max)
           System.err.println("oops");
     outFile.printf( "{\"source\":%d, \"target\":%d, \"value\":%d}", i, j, wt[i][j]); 
     temp=1;
   }
  }
 }
 outFile.printf("\n]\n,");

 int temp1=0;temp=0;
 
 for(int i=0; i< max; i++)
 {
     if(i!=0)
       outFile.printf("],\n");
     outFile.printf( "\"cluster%d\": [", i);

	if(edges.get(i)==null){
//		outFile.printf("\n] },");
		continue;
}

     for (int j= 0; j<edges.get(i).size(); j++)
     {
       if(j!=0)
       outFile.printf(",\n");
       outFile.printf( "{\"source\":%d, \"target\":%d, \"value\":%d}", edges.get(i).get(j).get(0), edges.get(i).get(j).get(1), 1);
       
     }
 }
 outFile.printf("\n] }");
 outFile.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JSONConv.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    }
}
