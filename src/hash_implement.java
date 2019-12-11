 
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

//import org.bouncycastle.*;
//import org.bouncycastle.crypto.digests.SHA256Digest;
//import org.bouncycastle.util.encoders.Hex;



public class hash_implement {
	hash_implement(){
		
	}
	
	public void SHA_256(String fileInPath) throws NoSuchAlgorithmException, NoSuchProviderException, IOException {
		File filein = new File(fileInPath);
		byte[] contens = Files.readAllBytes(filein.toPath());
		
		long start = System.nanoTime(); 
		Security.addProvider(new BouncyCastleProvider());
		MessageDigest mda = MessageDigest.getInstance("SHA-256", "BC");
		byte [] digesta = mda.digest(contens);		
		System.out.print("Hash256 Value = ");
		System.out.println(new String(Hex.encode(digesta)));
        long end = System.nanoTime(); 
        long elapsedTime = end - start;
        double seconds = (double)elapsedTime / 1_000_000_000.0;
        double speed = (double)contens.length /seconds ;
		System.out.println("Hashing time: " + seconds + " seconds, " + "speed= " + speed + " bytes/s"); 

	}
	
	public void SHA_512(String fileInPath) throws NoSuchAlgorithmException, NoSuchProviderException, IOException {
		File filein = new File(fileInPath);
		byte[] contens = Files.readAllBytes(filein.toPath());
		
		long start = System.nanoTime(); 
		Security.addProvider(new BouncyCastleProvider());
		MessageDigest mda = MessageDigest.getInstance("SHA-512", "BC");
		byte [] digesta = mda.digest(contens);		
		System.out.print("Hash512 Value = ");
		System.out.println(new String(Hex.encode(digesta)));
        long end = System.nanoTime(); 
        long elapsedTime = end - start;
        double seconds = (double)elapsedTime / 1_000_000_000.0;
        double speed = (double)contens.length /seconds ;
		System.out.println("Hashing time: " + seconds + " seconds, " + "speed= " + speed + " bytes/s"); 

	}
	
	public void SHA_3_256(String fileInPath) throws NoSuchAlgorithmException, NoSuchProviderException, IOException {
		File filein = new File(fileInPath);
		byte[] contens = Files.readAllBytes(filein.toPath());
		
		long start = System.nanoTime(); 
		Security.addProvider(new BouncyCastleProvider());
		MessageDigest mda = MessageDigest.getInstance("SHA3-256", "BC");
		byte [] digesta = mda.digest(contens);		
		System.out.print("Hash3_256 Value = ");
		System.out.println(new String(Hex.encode(digesta)));
        long end = System.nanoTime(); 
        long elapsedTime = end - start;
        double seconds = (double)elapsedTime / 1_000_000_000.0;
        double speed = (double)contens.length /seconds ;
		System.out.println("Hashing time: " + seconds + " seconds, " + "speed= " + speed + " bytes/s"); 

	}
	
	
	
	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException, IOException {
		// TODO Auto-generated method stub
		hash_implement hash_test = new hash_implement();
		
		String basepath = "/home/bigbro/eclipse-workspace/SecurityHW/src/HW2";
		String path = basepath + "/data/grader_dict.txt";
		hash_test.SHA_256(path);
		hash_test.SHA_512(path);
		hash_test.SHA_3_256(path);
		
		System.out.println();
		System.out.println();
		
		path = basepath + "/data/dict.txt";
		hash_test.SHA_256(path);
		hash_test.SHA_512(path);
		hash_test.SHA_3_256(path);


	}

}
