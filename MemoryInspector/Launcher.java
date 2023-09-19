/*
C:\>cmd /?

*/

class Launcher{

    public static void main(String[] args){
        
        try{
        String path = Launcher.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String filename= "MemoryInspector";
        Runtime.getRuntime().exec("cmd /c start cmd /k java "+filename);
        }catch(Exception e){System.err.println(e);}
       
        }
}