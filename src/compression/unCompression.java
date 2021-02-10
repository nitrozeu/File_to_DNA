
//package compression;

//import static compression.Compression.FileName;
//import static compression.Compression.toBinary;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;

/**
 *
 *
 */
public class unCompression {

    public static String decoder="encoder.txt";
    public static String compFile="compText.txt";
    public static String decompFile="newfile.txt";
    
    
    public static int getCode(char c){
        switch(c){
            case 'A':
                return 0;
                
            case 'T':
                return 1;
                
            case 'G':
                return 2;
                
            case 'C':
                return 3;
                             
        }
         
       return 0; 
    }
    
    
    public static void main(String args[]) throws FileNotFoundException, IOException, ClassNotFoundException{
        
    // 1. UNSERIALIZING THE ENCODER TO GET ROOT NODE
    
     FileInputStream fi = new FileInputStream(new File(decoder));
     ObjectInputStream oi = new ObjectInputStream(fi);

     // Read objects
     node rootNode = (node) oi.readObject(); //WE GOT OUR IMPORTANT ENCODE TREE

     System.out.println(rootNode.toString());

     oi.close();
     fi.close();
     
     
    //2. READ COMP FILE AND EVALUATE IT USING ROOTNODE (HUFFMAN TREE) 
     InputStream compin=null;
     OutputStream readout=null;   
     
    try {
        compin = new FileInputStream(  new File(compFile));     // READING       
        readout = new FileOutputStream(  new File(decompFile));     // WRITING    
    }
    catch(Exception ex){
       System.out.println("Error: "+ex);
    }
    
    int cnt;
    node routing = rootNode;
    
   while((cnt=compin.read())!=-1){
       if (routing.isLeaf){          
           readout.write(routing.key);
           routing = rootNode.childNode[ getCode((char)cnt) ];
       }
       else
           routing = routing.childNode[ getCode((char)cnt) ];              
   }   
    if (routing.isLeaf)         
           readout.write(routing.key);
     
   
    }
    
    

    
}
