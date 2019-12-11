 
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


public class Utilis {
	Utilis(){
		
	}
	
	
	public boolean verificationFile(String fileOne, String fileTwo) throws FileNotFoundException, IOException {
		File file1 = new File(fileOne);
		File file2 = new File(fileTwo);

		if(file1.length() != file2.length()){
		}

		try(InputStream in1 =new BufferedInputStream(new FileInputStream(file1));
		    InputStream in2 =new BufferedInputStream(new FileInputStream(file2));
		 ){
	
		      int value1,value2;
		      do{
		           //since we're buffered read() isn't expensive
		           value1 = in1.read();
		           value2 = in2.read();
		           if(value1 !=value2){
		           return false;
		           }
		      }while(value1 >=0);
	
		 //since we already checked that the file sizes are equal 
		 //if we're here we reached the end of both files without a mismatch
		 return true;
		}
	}	
	

}
