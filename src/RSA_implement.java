 
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class RSA_implement {
	RSA_implement(){
		
	}
	
	public KeyPair generate_keyPair(int keySize, SecureRandom random) throws NoSuchAlgorithmException, NoSuchProviderException {
		long start = System.nanoTime(); 
		Security.addProvider(new BouncyCastleProvider());
		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "BC");
		generator.initialize(keySize);
		KeyPair pair = generator.generateKeyPair();
		long end = System.nanoTime(); 
        long elapsedTime = end - start;
        double seconds = (double)elapsedTime / 1_000_000_000.0;
        System.out.println(keySize + "-bit key generated in: " + seconds + "seconds");
		return pair;
	}
	
	public void RSA_Encrypt(String fileInpath,String fileOutpath, Key pubKey, SecureRandom random) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		File filein = new File(fileInpath);
		byte[] fileContents = Files.readAllBytes(filein.toPath());
		int inputLength = fileContents.length;
		long start = System.nanoTime(); 
	    Cipher cipher = Cipher.getInstance("RSA/None/OAEPWithSHA1AndMGF1Padding", "BC");
	    cipher.init(Cipher.ENCRYPT_MODE, pubKey);
	    

	    
		int blockSize = cipher.getBlockSize();
		int max_Block_Size = 117;
		float a = (float) (inputLength*1.0/max_Block_Size);
		ByteBuffer encryptBuff;
		
		if ((a>(int)a)) {
			encryptBuff = ByteBuffer.allocate(((int)a+1)*cipher.getOutputSize(blockSize));
		}else{
			encryptBuff = ByteBuffer.allocate(((int)a)*cipher.getOutputSize(blockSize));
		}
		if(inputLength<=blockSize){
			byte[] result = cipher.doFinal(fileContents);
			encryptBuff.put(result);
		}else{
			ByteBuffer buff = ByteBuffer.allocate(fileContents.length);
			buff.put(fileContents);
			buff.flip();
			
			while (buff.remaining()>=blockSize) {
				byte[] buffbyte = new byte[blockSize];
				buff.get(buffbyte);
				
				byte[] result = cipher.doFinal(buffbyte);
				encryptBuff.put(result);
			}
			
			if (buff.remaining()>0) {
				byte[] buffbyte = new byte[buff.remaining()];
				buff.get(buffbyte);
				
				byte[] result = cipher.doFinal(buffbyte);
				encryptBuff.put(result);
			}
		}
		
		encryptBuff.flip();
		int limit = encryptBuff.limit();
		byte[] resultbuff= new byte[limit];
		encryptBuff.get(resultbuff);
	

	    
        long end = System.nanoTime(); 
        long elapsedTime = end - start;
        double seconds = (double)elapsedTime / 1_000_000_000.0;
        double speed = (double)fileContents.length /seconds ;
		System.out.println("Encrypting time: " + seconds + " seconds, " + "speed= " + speed + " bytes/s"); 
		try (FileOutputStream fos = new FileOutputStream(fileOutpath)) {
		   fos.write(resultbuff);
		}
	}
	
	
	public void RSA_Decrypt(String fileInpath,String fileOutpath, Key priKey, SecureRandom random) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		File filein = new File(fileInpath);
		byte[] fileContents = Files.readAllBytes(filein.toPath());
		int inputLength = fileContents.length;
//    	System.out.println(inputLength);

		long start = System.nanoTime(); 
	    Cipher cipher = Cipher.getInstance("RSA/None/OAEPWithSHA1AndMGF1Padding", "BC");
	    cipher.init(Cipher.DECRYPT_MODE, priKey);

		int blockSize = cipher.getBlockSize();
		int max_Block_Size = 117;
		float a = (float) (inputLength*1.0/max_Block_Size);
		ByteBuffer encryptBuff;
		
		if ((a>(int)a)) {
			encryptBuff = ByteBuffer.allocate(((int)a+1)*cipher.getOutputSize(blockSize));
		}else{
			encryptBuff = ByteBuffer.allocate(((int)a)*cipher.getOutputSize(blockSize));
		}
		if(inputLength<=blockSize){
			byte[] result = cipher.doFinal(fileContents);
			encryptBuff.put(result);
		}else{
			ByteBuffer buff = ByteBuffer.allocate(fileContents.length);
			buff.put(fileContents);
			buff.flip();
			
			while (buff.remaining()>=blockSize) {
				byte[] buffbyte = new byte[blockSize];
				buff.get(buffbyte);
				
				byte[] result = cipher.doFinal(buffbyte);
				encryptBuff.put(result);
			}
			
			if (buff.remaining()>0) {
				byte[] buffbyte = new byte[buff.remaining()];
				buff.get(buffbyte);
				
				byte[] result = cipher.doFinal(buffbyte);
				encryptBuff.put(result);
			}
		}
		
		encryptBuff.flip();
		int limit = encryptBuff.limit();
		byte[] resultbuff= new byte[limit];
		encryptBuff.get(resultbuff);
	

	    
        long end = System.nanoTime(); 
        long elapsedTime = end - start;
        double seconds = (double)elapsedTime / 1_000_000_000.0;
        double speed = (double)fileContents.length /seconds ;
		System.out.println("Decrypting time: " + seconds + " seconds, " + "speed= " + speed + " bytes/s"); 
		try (FileOutputStream fos = new FileOutputStream(fileOutpath)) {
		   fos.write(resultbuff);
		}
	}
	
	
	
	public static void main(String[] args) throws Exception {
		RSA_implement rsaSample = new RSA_implement();
		Utilis helps = new Utilis();
		Security.addProvider(new BouncyCastleProvider());	
		
	       
	    SecureRandom random = new SecureRandom();
	    KeyPair pair = rsaSample.generate_keyPair(3072, random);
	    Key pubKey = pair.getPublic();
	    Key privKey = pair.getPrivate();
	    
	    //small file
		String basepath = "/home/bigbro/eclipse-workspace/SecurityHW/src/HW2";
		String path = basepath + "/data/grader_dict.txt";
		rsaSample.RSA_Encrypt(path, basepath + "/output/encypt_RSA.txt",pubKey, random);
		rsaSample.RSA_Decrypt(basepath + "/output/encypt_RSA.txt", basepath + "/output/Decypt_RSA.txt",privKey, random);
		
		System.out.println(helps.verificationFile(path, basepath + "/output/Decypt_RSA.txt"));
		
		System.out.println();
		System.out.println();
		//big file
		path = basepath + "/data/dict.txt";
		rsaSample.RSA_Encrypt(path, basepath + "/output/encypt_RSA.txt",pubKey, random);
		rsaSample.RSA_Decrypt(basepath + "/output/encypt_RSA.txt", basepath + "/output/Decypt_RSA.txt",privKey, random);
		System.out.println(helps.verificationFile(path, basepath + "/output/Decypt_RSA.txt"));

		

	  }
	
	
}
