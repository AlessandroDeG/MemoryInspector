import java.util.*;
import java.io.File;
import java.nio.*;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.util.concurrent.atomic.AtomicLong;
import java.io.IOException;
import java.util.Random;
import java.lang.*;

class TestSort{

public static void main(String[] args){
    Random random = new Random();
    
    ArrayList<String> results = new ArrayList<String>();
    
    int size = 5000;
    
    int percent = size/100;
    
    double overheadTime=0;
    
   
    
    for(int i=0; i<size;i++){
    double startOH = System.nanoTime();
    //System.out.println("not ordered:");
    
     ArrayList<Integer> folderList = new ArrayList<Integer>();
    
    
    for(int j=1;j<i;j++){
    folderList.add(random.nextInt(100));
    }
    
      for(Integer n : folderList){   
        //System.out.print(n+" ");
    }
    //System.out.println("");
    
    double endOH = System.nanoTime();
    
    double start = System.nanoTime();
    folderList = sortBySize(folderList);
    double end = System.nanoTime();
    
    results.add("N = "+i+"   Time="+String.format("%.2f",(end-start)/1000000) + "   Overhead Time="+ String.format("%.2f",(endOH-startOH)/1000000));
    
    //String.format("%.5f", 
     
   //System.out.println("");
   //System.out.println("ordered");
    
    //for(Integer n : folderList){   
        //System.out.print(n+" ");
    //}
    
    //System.out.println("");
    //System.out.println("");
    //System.out.println("");

     if(i%percent==0){
         System.out.print((i/percent) +"%  complete ...\r");
     }
    }
    
    for(String result : results){
         System.out.println(result);
    }


}








public static ArrayList<Integer> sortBySize(ArrayList<Integer> folderList){ 
        
        ArrayList<Integer> orderedFolderList = new ArrayList<Integer>();
        ArrayList<Boolean> checked = new ArrayList<Boolean>();
        
         for(int i =0; i<folderList.size();i++){
             checked.add(false);
         }
        
        
        for(int i =0; i<folderList.size();i++){
        Integer max = null;
        int maxIndex=0;
        
        for(int j =0; j<folderList.size();j++){
            
            if(!checked.get(j)){
                if(max==null){
                    //System.out.print("max null index:"+ j);
                  max=folderList.get(j);      
                    //System.out.println("max set to :"+ max);
                    maxIndex=j;
                }
                else{
                    //System.out.println("max not null index"+ j);
                    if(folderList.get(j) >= max){                
                       // System.out.println(folderList.get(j)+" >="+ max);
                     max = folderList.get(j);
                     maxIndex=j;
                     //System.out.println("max="+max);
                    // System.out.println("maxIndex="+maxIndex);
                    }           
                }
            }
            else{
                //System.out.println("alredy checked folderList "+ j + ":"+ folderList.get(j));
            }                
        }
        
        //System.out.println("adding "+max +" at index "+maxIndex);
        //System.out.println("");
        orderedFolderList.add(max);
        checked.set(maxIndex,true);
        }
        
        
          return orderedFolderList;   
}

}