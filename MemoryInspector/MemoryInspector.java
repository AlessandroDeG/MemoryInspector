import java.util.*;
import java.io.File;
import java.nio.*;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.util.concurrent.atomic.AtomicLong;
import java.io.IOException;
import java.io.*;


class MemoryInspector{

static ArrayList<String> drives = new ArrayList<String>();

public static void main(String[] args){
    
    Scanner in = new Scanner(System.in);
    
    /////////////////////////////////////////////////////////initialize loggerFile
    System.out.println("***Initializing log file***");
    FileRW.write("Memory Inspector Log:\n\n");
    System.out.println("\nResults will be logged in:  "+ FileRW.path.getAbsolutePath());
    
    
    
    
    
    ///////////////////////////////////////////////////////
    System.out.println("\nDetected drives:\n");
    FileRW.write("\nDetected drives:\n\n");
    
    detectDrives(); 
     for(String drive : drives){
         System.out.println(drive);
         //System.out.println(new File(drive).length());
         System.out.println("Total Space : "+String.format("%.2f",((float)new File(drive).getTotalSpace())/1024/1024/1024) + " GB");
         System.out.println("Available Space : " +String.format("%.2f",((float)new File(drive).getFreeSpace())/1024/1024/1024) + " GB");
         System.out.println(String.format("%.2f", 100f - 100f/((float)new File(drive).getTotalSpace()/(float)new File(drive).getFreeSpace()))   + "% FULL");
         System.out.println();
         
         FileRW.write(drive+"\n");
         FileRW.write("Total Space : "+String.format("%.2f",((float)new File(drive).getTotalSpace())/1024/1024/1024) + " GB\n");
         FileRW.write("Available Space : " +String.format("%.2f",((float)new File(drive).getFreeSpace())/1024/1024/1024) + " GB\n");
         FileRW.write(String.format("%.2f", 100f - 100f/((float)new File(drive).getTotalSpace()/(float)new File(drive).getFreeSpace()))   + "% FULL\n\n");
         
     }
     
     /////////////////////////////////////////////////////////   
    System.out.println("\nSelect drives (es: C) and type end to finish selection, or type all to check all drives:");
     
    ArrayList<String> validInputs = new ArrayList<String>();
     for(String drive : drives){
         validInputs.add(drive.replace(":/",""));
     }
     
      validInputs.add("all");
      validInputs.add("end");
     
     for(String validInput : validInputs){
         System.out.println(validInput);
     }
     
    ArrayList<String> selectedDrives = new ArrayList<String>();
    String selected="";
    
    while(  (!selectedDrives.contains("all")) && (!selectedDrives.contains("end")) ){
    while(!validInputs.contains(selected)){
         System.out.print("\nSelect: "); 
         selected = in.nextLine();
         if(!validInputs.contains(selected)){
              System.out.println("wrong input"); 
         }
         else{
            selectedDrives.add(selected);
         }
    }
    selected="";
    }
     
    
    if(selectedDrives.contains("all")){
        System.out.println("\nChecking all drives:");
        FileRW.write("\nChecking all drives:\n");
        for(String drive : drives){
           System.out.println(drive);
           FileRW.write(drive+"\n");
        }
         System.out.println("\n\n");
         FileRW.write("\n\n\n");
       
    }
    else{
        
         ArrayList<String> removed = new ArrayList<String>();  
             for(String drive : drives){
                 if(!selectedDrives.contains(drive.replace(":/",""))){
                     removed.add(drive);
                 }
             }
             drives.removeAll(removed);  

         System.out.println("\nChecking drives:");
         FileRW.write("\nChecking drives:\n");
        for(String drive : drives){
           System.out.println(drive);
           FileRW.write(drive+"\n");
        }             
        System.out.println("\n\n");
        FileRW.write("\n\n\n");
        
         
         
    }
    
    
    ////////////////////////////Settings
    validInputs.clear();
    validInputs.add("default");
    validInputs.add("custom");
    
    
   
    System.out.println("\n**SETTINGS**\n");
      System.out.println("\nChoose scanning method:\n");
      System.out.println("default        (no depth limit, limit to size >  ~1GB)");
      System.out.println("custom         (choose scanning depth and min size)");
      
      FileRW.write("\n**SETTINGS**\n");
    
      
      
      selected="";
      while(!validInputs.contains(selected)){
         System.out.print("\nChoose: "); 
         selected = in.nextLine();
         if(!validInputs.contains(selected)){
              System.out.println("wrong input"); 
         }
      }
    
    
    /////////////////////////DEFAULT    
        int depth = 0;
        boolean limitDepth=false;
        long minSize = 1000000000;
        boolean limitSize=true;
    
    /////////////////////////CUSTOM
    if(selected.equals("custom")){
        
        
        validInputs.clear();
        validInputs.add("true");
        validInputs.add("false");
        selected="";
        
      while(!validInputs.contains(selected)){
         System.out.print("\nlimit depth? (true/false): "); 
         selected = in.nextLine();
         if(!validInputs.contains(selected)){
              System.out.println("wrong input"); 
         }
      }
      if(selected.equals("true")){
          limitDepth=true;
      }
      else{
          limitDepth=false;
      }
      
      
      
        selected="";
        
      while(!validInputs.contains(selected)){
         System.out.print("\nlimit size? (true/false): "); 
         selected = in.nextLine();
         if(!validInputs.contains(selected)){
              System.out.println("wrong input"); 
         }
      }
      
      if(selected.equals("true")){
          limitSize=true;
      }
      else{
          limitSize=false;
          minSize=0;
      }
      
      boolean valid = false;
      if(limitDepth){
          while(!valid){
              
              try{
              System.out.print("\n Choose depth:   scann will search N folders deep:  N= "); 
              depth = in.nextInt();
              valid=true;
              }catch(Exception e){valid=false;}
              
              
              
              if(!valid){
              System.out.println("wrong input"); 
              }
          }
          
          
      }
      
      valid=false;
      if(limitSize){
          
          try{
              System.out.print("\n choose size limit(bytes): scann will show only folders and files >  : "); 
              minSize = in.nextLong();
              valid=true;
              }catch(Exception e){valid=false;}
          
          
          if(!valid){
              System.out.println("wrong input"); 
          }
          
      }
        
        
        System.out.println("\n***Selected custom search:***");
        FileRW.write("\n***Custom search:***\n");
        
        if(limitDepth){
            System.out.println("depth = "+depth);
            FileRW.write("depth = "+depth+"\n");
            
            }
            else{
                System.out.println("no depth limit");
                FileRW.write("no depth limit\n");
                }
        if(limitSize){
            System.out.println("size > "+minSize+" bytes");
            FileRW.write("size > "+minSize+" bytes\n");
            
            }
            else{
                System.out.println("no size limit");
                FileRW.write("no size limit\n");
                }
                
        System.out.println("");
        FileRW.write("\n");        
        
  
        
        
        
    }
    else{
        FileRW.write("default        (no depth limit, limit to size >  ~1GB)");
    }
    
    
    ////check drives
    
    System.out.println("\n***SCANNING***\n\n");
    FileRW.write("\n***SCANNING***\n\n\n");
    
    long totalTime=0;
    
    for(String drive : drives){
                System.out.println(drive);
                FileRW.write(drive+"\n");
                
                long start = System.currentTimeMillis();
                inspect(new File(drive), limitDepth ,depth , 0,minSize, ""); 
                long end = System.currentTimeMillis();
                totalTime=end-start;
                System.out.println("\n\n\n\n");
                FileRW.write("\n\n\n\n\n");
                             
    }
    
    
    ////
        System.out.println("\nTotal scanning time = "+totalTime/1000+"s\n");
        FileRW.write("\nTotal scanning time = "+totalTime/1000+"s");
        
        System.out.println("\nResults logged in:  "+ FileRW.path.getAbsolutePath());
    
    
    
    ////
    
    
    
     
}//endMain

public static void inspect(File parentFile, boolean limitDepth ,int depth,  int depthCounter,long minSize, String spacing){
    
    if(depthCounter<depth || !limitDepth){
    
     
     String childList[] = parentFile.list();
     if(childList.length >0){
         
                 ArrayList<FolderData> childDataList = new ArrayList<FolderData>();
                 
                 //System.out.println("\n"+drive+":\n");
                  /*
                  for(String name : childList){
                      System.out.println(name);            
                  }
                  */
                  
                  
                  for(String name : childList){
                      childDataList.add(new FolderData(new File(parentFile , name)));                                                         
                  }
                  
                  //bubbleSortBySize(childDataList);
                  
                  /*
                  System.out.println("\ntest size\n\n");
                  for(FolderData folder : childDataList){
                       System.out.println(folder.name+" : "+ folder.size +"  "+ folder.sizeString);
                      
                  }
                  */
                  
                  //System.out.println("\n ***Sorted***\n\n");
                  
                  
                  for(int i=0;i<=depthCounter;i++){
                      if(i<depthCounter){
                        spacing+="--";
                      }
                      else{
                        spacing+="|----";
                      }
                      
                  }
                  
                  
                  childDataList = sortBySize(childDataList);
                  
                    for(FolderData folder : childDataList){
                       if(folder.size>= minSize){
                           
                           /*
                           if(!(depthCounter==depth-1)){
                               System.out.println("|");
                              // System.out.print("|");
                           }
                           */
                           
                           if(depthCounter==0){
                               System.out.println("|");
                               FileRW.write("|\n");
                              // System.out.print("|");
                           }
                           
                           //System.out.println(spacing+folder.name+" : "+ folder.size +"  "+ folder.sizeString);
                           System.out.println(spacing+folder.name+" : "+"  "+ folder.sizeString);
                               FileRW.write(spacing+folder.name+" : "+"  "+ folder.sizeString+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+folder.fullPath+"\n");
                         try{
                           if(folder.file.isDirectory()){
                               depthCounter++;
                               inspect(folder.file, limitDepth, depth, depthCounter, minSize, spacing);
                               depthCounter--;
                           }
                         }catch(Exception e){System.out.println("error :"  +e); FileRW.write("error:" + e+"\n");   }
                        }
                       
                     
                    }
     }
                            
    }             
}


public static ArrayList<FolderData> sortBySize(ArrayList<FolderData> folderList){ 
        
        ArrayList<FolderData> orderedFolderList = new ArrayList<FolderData>();
        ArrayList<Boolean> checked = new ArrayList<Boolean>();
        
         for(int i =0; i<folderList.size();i++){
             checked.add(false);
         }
        
        
        for(int i =0; i<folderList.size();i++){
        FolderData max = null;
        int maxIndex=0;
        
        for(int j =0; j<folderList.size();j++){
            
            if(!checked.get(j)){
                if(max==null){
                  max=folderList.get(j);  
                  maxIndex=j;                                   
                }
                else{
                    if(folderList.get(j).size >= max.size){
                     max = folderList.get(j);
                     maxIndex=j;
                    }           
                }
            }                         
        }
        
        orderedFolderList.add(max);
        checked.set(maxIndex,true);
        }
        
        
          return orderedFolderList;   
}

public static boolean checkForDrive(String dir) {
        return new File(dir).exists();
}

public static void detectDrives() {
        for (int i = 0; i < 26; i++) {
            if (checkForDrive((char) (i + 'A') + ":/")) {
				drives.add((char)(i + 'A') + ":/");              
            }
        }
}


//////////////////////////////////////////////////FROM STACKOVERFLOW

public static long size(Path path) {

    final AtomicLong size = new AtomicLong(0);

    try {
        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {

                size.addAndGet(attrs.size());
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) {

                //System.out.println("skipped: " + file + " (" + exc + ")");
                // Skip folders that can't be traversed
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) {

                if (exc != null)
                    System.out.println("had trouble traversing: " + dir + " (" + exc + ")");
                // Ignore errors traversing a folder
                return FileVisitResult.CONTINUE;
            }
        });
    } catch (IOException e) {
        throw new AssertionError("walkFileTree will not throw IOException if the FileVisitor does not");
    }

    return size.get();
}

/////////////////////////////////////////////////////////////////



}


class FolderData{
    String name="";
    String fullPath="";
    long size=0;
    String sizeString="";
    
    File file;
    
    public FolderData(File file){
        this.file=file;
        name = file.getName();
        fullPath = file.getAbsolutePath();
        size= folderSize(file);   
        //sizeString= String.format("%.2f", ((float)(size/1024/1024))/1024)  + " GB";
        sizeString = formatSizeString();
    }
    
    
    public String formatSizeString(){
        float s = this.size;
        List<String> units = Arrays.asList(" Bytes" , " KB" , " MB" , " GB");
        
        int i=0;
        
        while(s>1024){
        s = s/1024;
        if(s>1){
        i++;
        }
        }
          
        return String.format("%.2f", s )+ units.get(i);
           
    }
    
    
    
    public long folderSize(File file) {
      return MemoryInspector.size(file.toPath());
    }
    
   
}



class FileRW{
	
	static String mainDirPath = MemoryInspector.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	static String resDirName="\\MemoryInspectorResources";
	static String logFileName="\\MemoryLog-"+(java.time.LocalDateTime.now()).toString().replace(".","-").replace(":","-")+".txt";
	
	static File path = new File(mainDirPath+resDirName+logFileName);
	static boolean fixPathForJar=true;
	//static boolean runningFromJar=false;
	static String dirString="";
	
	
	static boolean isRunningFromJar(){
		boolean runningFromJar=false;
		
		String s = MemoryInspector.class.getResource("MemoryInspector.class").toString();
		//System.out.println(s);
		if(s.startsWith("jar:")){
			runningFromJar=true;
		}
		
		return runningFromJar;
		
	}

	
	public static void write(String message){
        
        if(isRunningFromJar() && fixPathForJar){
			System.out.println("isRunningFromJar ...");
			String s = path.toString();
			path = path.getParentFile().getParentFile().getParentFile();
			System.out.println(path.toString());
			dirString= path.toString();
			path = new File(path.toString()+resDirName+logFileName);
			System.out.println(path.toString());
			System.out.println(".. isRunningFromJar fixed path");
			fixPathForJar=false;
		}
		
		try{
	       FileWriter writer = new FileWriter(path,true);
		   writer.append(message);
		   writer.close();
	    }catch(Exception e){
		System.out.println(e);
		if(e instanceof FileNotFoundException){
			try{
				if(isRunningFromJar()){
					File newDir = new File(dirString+resDirName);
					boolean success=false;
					//try{
					success = newDir.mkdirs();
					//}catch(Exception mkdirex){System.out.println("mkdir EXC"+mkdirex);}
					System.out.println("CREATED FILE "+ success + " : " + newDir.toString());
				}else{
					File newDir = new File(mainDirPath+resDirName);
					boolean success=false;
					//try{
					success = newDir.mkdirs();
					//}catch(Exception mkdirex){System.out.println("mkdir EXC"+mkdirex);}
					System.out.println("CREATED FILE "+ success + " : " + newDir.toString());
					
				}
			path.createNewFile();
			write(message);
			System.out.println("Created File: "+ path.toString());
			}catch(Exception e1){System.out.println("mkdir: "+e1);}
		}
        			
	    }	
    }
            
}
