
//package compression;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.*;


public class freqTab {
    
   // public static void main(String args[]) throws IOException{
  public  Map<Integer, String> createTab(String filename) throws IOException{   
         InputStream in=null;
        
        // System.out.println("Enter file :");
         
         String fname=filename;//"text.txt";//(new Scanner(System.in)).next();
         
          try {
                     File file=new File(fname);
                      in =new FileInputStream(file);     // PUTTING DATA INTO FILE
           
             }
           catch(Exception ex){
                    System.out.println("Error: "+ex);
             }
      
         int cnt;

    HashMap charFeq = new HashMap();
 
   while((cnt=in.read())!=-1){
                   
       //  System.out.println(" "+cnt);
         if (charFeq.containsKey(cnt))
             charFeq.put(cnt, Integer.parseInt(charFeq.get(cnt).toString())+1 );
         else
            charFeq.put(cnt,1);    
     }
 

     
       Map<Integer, String> map = sortByValues(charFeq); 
      
     
   in.close();    
     
   return map;
          
         
    }
    
    
 public static char objectToChar(Object ob){
     
        try{    
         return (char)Integer.parseInt(ob.toString()) ;
        }
        catch(Exception err){
            return ' ';
        }
     } 
 
 public static int objectToInteger(Object ob){
        return Integer.parseInt(ob.toString()) ;
 }
    
    
    
  private static HashMap sortByValues(HashMap map) { 
      
      List list = new LinkedList(map.entrySet());
       // Defined Custom Comparator here
       Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
               return ((Comparable) ((Map.Entry) (o1)).getValue())
                  .compareTo(((Map.Entry) (o2)).getValue());
            }
       });

       // Here I am copying the sorted list in HashMap
       // using LinkedHashMap to preserve the insertion order
       HashMap sortedHashMap = new LinkedHashMap();
       for (Iterator it = list.iterator(); it.hasNext();) {
              Map.Entry entry = (Map.Entry) it.next();
              sortedHashMap.put(entry.getKey(), entry.getValue());
       } 
       return sortedHashMap;
  }
    
}
