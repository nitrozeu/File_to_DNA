
//package compression;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class Compression {
/**
 *
 * 
 */
    
    static String FileName = "text.txt";
    static String CompressFileName = "compText.txt";
    static String BinaryFileName = "BinText.txt";
    
    static String toBinary(String s){
            // String s = "@a";
        byte[] bytes = s.getBytes();
        StringBuilder binary = new StringBuilder();
        for (int j = 0; j < bytes.length; j++) {
            int val = bytes[j];
            for (int i = 0; i < 7; i++) {
                val <<= 1;
                binary.append((val & 128) == 0 ? 0 : 1);
            }
        }
        return binary.toString();
    }
    
    
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
     
       
    //1. create freq table list
     freqTab tab = new freqTab();
     
     Map<Integer, String> freq = tab.createTab(FileName);
        
  
     //2. crete min heap from freq table
      minHeapTree enc   = new minHeapTree(freq) ;
      enc.display();
   //   System.out.println("-----------------");
      
      //extracting all to view if functionality working poperly or not
      //enc.extractingAll(); //test with all possible data
    
    //3. process heap for huffman compress tree 
     while(enc.size > 1) {
       System.out.println("-----------------");
        enc.extractAndSet();
         enc.display();
    }  
    
    //4. Traverse thru tree till leaf characters and assign it his path (DFS traversal)   
    
    HashMap charPath = new HashMap();     //THIS TABLE CONTAINS ATGC CODE OF A CHARACTER
    
    //enc.dfsTraverseLeaf( charPath , enc.elements[0]  , ""  ) ; 
    //node n=
    new node().dfsTraverseLeaf(charPath, enc.elements[0]  , ""  ) ; 
   
            Iterator it = charPath.entrySet().iterator();
    
    while(it.hasNext()){
        Map.Entry res = (Map.Entry) it.next();       
        System.out.println(res.getKey()+" "+res.getValue());
    }
    
    
  //5. Reading input file char bt char and creating new file with atgc code  and binary file
   
     InputStream in=null;
     OutputStream compout=null;  
     OutputStream binout=null;  
     
    try {
        
        in = new FileInputStream(  new File(FileName));     // READING
        binout = new FileOutputStream(  new File(BinaryFileName));     // WRITING 
        compout = new FileOutputStream(  new File(CompressFileName));     // WRITING    
    }
    catch(Exception ex){
       System.out.println("Error: "+ex);
    }
      
   int cnt;

   while((cnt=in.read())!=-1){
                    
      if (charPath.containsKey((char)cnt)){
             
              String key =""+(char)cnt;
              binout.write( toBinary(key).getBytes() ); //WRINTING IN BINARY FILE WHILE CONVERTING KEY TO BINARY
             
               String val = (String) charPath.get((char)cnt); //WRINTING IN COMPRESS FILE DETAILS IN ATGC 
               System.out.println(val );
               byte b[]=val.getBytes();
               compout.write(b);
               
         }
      else
            System.out.println("empty");
   }
 
  
   //6. CREATW ENCODER FILE BY SERIALIZING ROOT NODE
   
   node storeNode = enc.elements[0]; //THE ROOT TO BE STORE AS IT CONTAINS CHILD NODE WHICH THEMSELVES CONTAIN MORE CHILD
   
   try {
        FileOutputStream f = new FileOutputStream(new File("encoder.txt"));
	ObjectOutputStream o = new ObjectOutputStream(f);
        // Write objects to file
	o.writeObject(storeNode);
	
	o.close();
	f.close();
   }catch(Exception ex){}
   
   
   
  
        
    }
    
}
