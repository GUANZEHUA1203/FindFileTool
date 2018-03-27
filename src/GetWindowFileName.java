  
import java.io.File;    
  
/**  
 * @author yinxm  
 * @version 1.0 2005/06/17  
 *   
 * This class can take file's path and file's name;  
 * you must give the path where you want to take the file.  
 */  
public class GetWindowFileName {    
  
    public static void main(String[] args) {   
        // This is the path where the file's name you want to take.   
        String path = "D:\\img\\jarpackage";   
        getFile(path);   
    }   
       
    private static void getFile(String path){   
        File file = new File(path);   
        File[] array = file.listFiles();   
          
        for(int i=0;i<array.length;i++){   
            File eachFile = array[i];
			if(eachFile.isFile()){   
//                System.out.println("^^^^^" + array[i].getName());   
//                System.out.println("#####" + array[i]);   
//                System.out.println("*****" + array[i].getPath());   
				String fileName=eachFile.getName();
				if(fileName.substring(fileName.lastIndexOf(".")+1).equals("jpg")){
				System.out.println(fileName.substring(0,fileName.lastIndexOf(".")));
				}
				
				
            }else if(eachFile.isDirectory()){   
                getFile(eachFile.getPath());   
            }   
        }   
    }   
}   
  