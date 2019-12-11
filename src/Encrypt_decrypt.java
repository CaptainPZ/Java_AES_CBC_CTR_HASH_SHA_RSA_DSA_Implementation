 
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Encrypt_decrypt {
	
	Encrypt_decrypt(){
		
	}
	
	ArrayList<String> readFile(String path) {
		ArrayList<String> contents = new ArrayList<String>();
		
		File file = new File(path);
		Scanner sc;

		try {
			sc = new Scanner(file);
			while(sc.hasNextLine()) {				
				//System.out.println(counter); // for debug
				String tmp = sc.nextLine();
				contents.add(tmp);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return contents;
	}
	
	
	void printFile(ArrayList<String> contents) {
		for (String elm:contents) {
			System.out.println(elm);
		}
	}


	
	void saveResults(String filename, String contents) {
		File file = new File(filename);
        FileWriter fr = null;
        BufferedWriter br = null;
        String dataWithNewLine=contents; //+System.getProperty("line.separator");
        try{
            fr = new FileWriter(file);
            br = new BufferedWriter(fr);            
            br.write(dataWithNewLine);
            
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                br.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
	
	
	
	
	public static void main(String[] args) throws Exception  {
		
		Utilis helps = new Utilis();
		String key = "ABCDEFGHIJKLMNOP";
		AES_Utility AES_new = new AES_Utility();
		
		String basepath = "/home/bigbro/eclipse-workspace/SecurityHW/src/HW2";
		
		//small file
		System.out.println("Small file ---");
		String path = basepath + "/data/grader_dict.txt";
		AES_new.encrypt_128bit_oneShot(path, basepath + "/output/encypt_oneshot.txt", key, "1242");
		AES_new.decrypt_128bit_oneShot(basepath + "/output/encypt_oneshot.txt", basepath + "/output/decypt_oneshot.txt", key, "1242");
		System.out.println(helps.verificationFile(path, basepath + "/output/decypt_oneshot.txt"));
		
		AES_new.encrypt_CTR_oneShot(path, basepath + "/output/encypt_oneshot_ctr.txt", key, "1242",16);
		AES_new.decrypt_CTR_oneShot(basepath + "/output/encypt_oneshot_ctr.txt", basepath + "/output/decypt_oneshot_ctr.txt", key, "1242",16);
		System.out.println(helps.verificationFile(path, basepath + "/output/decypt_oneshot_ctr.txt"));
		
		key = key+key;
		AES_new.encrypt_CTR_oneShot(path, basepath + "/output/encypt_oneshot_ctr_256.txt", key, "1242",32);
		AES_new.decrypt_CTR_oneShot(basepath + "/output/encypt_oneshot_ctr_256.txt", basepath + "/output/decypt_oneshot_ctr_256.txt", key, "1242",32);
		System.out.println(helps.verificationFile(path, basepath + "/output/decypt_oneshot_ctr_256.txt"));		
		System.out.println();
		//big file
		System.out.println("big file ---");
		path = basepath + "/data/dict.txt";
		AES_new.encrypt_128bit_oneShot(path, basepath + "/output/encypt_oneshot.txt", key, "1242");
		AES_new.decrypt_128bit_oneShot(basepath + "/output/encypt_oneshot.txt", basepath + "/output/decypt_oneshot.txt", key, "1242");
		System.out.println(helps.verificationFile(path, basepath + "/output/decypt_oneshot.txt"));
		
		AES_new.encrypt_CTR_oneShot(path, basepath + "/output/encypt_oneshot_ctr.txt", key, "1242",16);
		AES_new.decrypt_CTR_oneShot(basepath + "/output/encypt_oneshot_ctr.txt", basepath + "/output/decypt_oneshot_ctr.txt", key, "1242",16);
		System.out.println(helps.verificationFile(path, basepath + "/output/decypt_oneshot_ctr.txt"));
		
		key = key+key;
		AES_new.encrypt_CTR_oneShot(path, basepath + "/output/encypt_oneshot_ctr_256.txt", key, "1242",32);
		AES_new.decrypt_CTR_oneShot(basepath + "/output/encypt_oneshot_ctr_256.txt", basepath + "/output/decypt_oneshot_ctr_256.txt", key, "1242",32);
		System.out.println(helps.verificationFile(path, basepath + "/output/decypt_oneshot_ctr_256.txt"));

		
	}

}




class AES_Utility{
//	private String algorithm ="AES/CBC/PKCS5Padding";
	AES_Utility(){
		
	}
	
	private SecretKeySpec set_key(String pwd, int blockSize) {
		long start = System.nanoTime(); 
		StringBuffer buffer = new StringBuffer(blockSize);
		buffer.append(pwd);
		while (buffer.length() < blockSize) {
            buffer.append("0");
        }
        if (buffer.length() > blockSize) {
            buffer.setLength(blockSize);
        }		
		byte[] key_bytes = buffer.toString().getBytes();		
		SecretKeySpec finalKey = new SecretKeySpec(key_bytes, "AES");
		long end = System.nanoTime(); 
		long elapsedTime = end - start;
		double seconds = (double)elapsedTime / 1_000_000_000.0;
		System.out.println("Ken_Gen time: " + seconds + " seconds"); 
		return finalKey;
	}
	
	private IvParameterSpec set_128bit_IV(String iv) {
		StringBuffer buffer = new StringBuffer(16);
		buffer.append(iv);
		while (buffer.length() < 16) {
            buffer.append("0");
        }
        if (buffer.length() > 16) {
            buffer.setLength(16);
        }		
		byte[] iv_bytes = buffer.toString().getBytes();	
		
		IvParameterSpec finalIV = new IvParameterSpec(iv_bytes);
		return finalIV;
	}
	
	public byte[] encrypt_128bit(byte[] contents, String pwd, String iv) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		SecretKeySpec key = set_key(pwd,16);
		IvParameterSpec ivPara = set_128bit_IV(iv);
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, ivPara);
        byte[] encrypted = cipher.doFinal(contents);
        return encrypted;
	}
	
	void encrypt_128bit_oneShot(String fileInPath, String fileOutPath, String pwd, String iv) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, IOException {
		// File process
		File filein = new File(fileInPath);
		byte[] fileContents = Files.readAllBytes(filein.toPath());
		
		
		SecretKeySpec key = set_key(pwd,16);
		IvParameterSpec ivPara = set_128bit_IV(iv);
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, ivPara);
        
        long start = System.nanoTime(); 
        byte[] encryptedBytes = cipher.doFinal(fileContents);
        long end = System.nanoTime(); 
        long elapsedTime = end - start;
        double seconds = (double)elapsedTime / 1_000_000_000.0;
        double speed = (double)fileContents.length /seconds ;
		System.out.println("Encrypting time: " + seconds + " seconds, " + "speed= " + speed + " bytes/s"); 
		try (FileOutputStream fos = new FileOutputStream(fileOutPath)) {
		   fos.write(encryptedBytes);
		}
	}
	
	void encrypt_CTR_oneShot(String fileInPath, String fileOutPath, String pwd, String iv, int keysize) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, IOException, NoSuchProviderException {
		// File process
		File filein = new File(fileInPath);
		byte[] fileContents = Files.readAllBytes(filein.toPath());
		
		
		SecretKeySpec key = set_key(pwd,keysize);
		IvParameterSpec ivPara = set_128bit_IV(iv);
		Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, key, ivPara);
        
        long start = System.nanoTime(); 
        byte[] encryptedBytes = cipher.doFinal(fileContents);
        long end = System.nanoTime(); 
        long elapsedTime = end - start;
        double seconds = (double)elapsedTime / 1_000_000_000.0;
        double speed = (double)fileContents.length /seconds ;
		System.out.println("Encrypting time: " + seconds + " seconds, " + "speed= " + speed + " bytes/s"); 
		try (FileOutputStream fos = new FileOutputStream(fileOutPath)) {
		   fos.write(encryptedBytes);
		}
	}
	
	
	public byte[] decrypt_128bit(byte[] contents, String pwd, String iv) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		SecretKeySpec key = set_key(pwd,16);
		IvParameterSpec ivPara = set_128bit_IV(iv);
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, ivPara);
        byte[] decrypted = cipher.doFinal(contents);
        return decrypted;
	}
	
	void decrypt_128bit_oneShot(String fileInPath, String fileOutPath, String pwd, String iv) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, IOException {
		// File process
		File filein = new File(fileInPath);
		byte[] fileContents = Files.readAllBytes(filein.toPath());
		
		
		SecretKeySpec key = set_key(pwd,16);
		IvParameterSpec ivPara = set_128bit_IV(iv);
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, ivPara);
        
        long start = System.nanoTime(); 
        byte[] encryptedBytes = cipher.doFinal(fileContents);
        long end = System.nanoTime(); 
        long elapsedTime = end - start;
        double seconds = (double)elapsedTime / 1_000_000_000.0;
        double speed = (double)fileContents.length /seconds ;
		System.out.println("Decrypting time: " + seconds + " seconds, " + "speed= " + speed + " bytes/s"); 
		try (FileOutputStream fos = new FileOutputStream(fileOutPath)) {
		   fos.write(encryptedBytes);
		}
	}

	void decrypt_CTR_oneShot(String fileInPath, String fileOutPath, String pwd, String iv, int keysize) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, IOException {
		// File process
		File filein = new File(fileInPath);
		byte[] fileContents = Files.readAllBytes(filein.toPath());
		
		
		SecretKeySpec key = set_key(pwd,keysize);
		IvParameterSpec ivPara = set_128bit_IV(iv);
		Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, key, ivPara);
        
        long start = System.nanoTime(); 
        byte[] encryptedBytes = cipher.doFinal(fileContents);
        long end = System.nanoTime(); 
        long elapsedTime = end - start;
        double seconds = (double)elapsedTime / 1_000_000_000.0;
        double speed = (double)fileContents.length /seconds ;
		System.out.println("Decrypting time: " + seconds + " seconds, " + "speed= " + speed + " bytes/s"); 
		try (FileOutputStream fos = new FileOutputStream(fileOutPath)) {
		   fos.write(encryptedBytes);
		}
	}
	
	
	void encrypt_byFile(String fileInPath, String fileOutPath, String pwd, String iv) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, IOException, ShortBufferException {
		SecretKeySpec key = set_key(pwd,16);
		IvParameterSpec ivPara = set_128bit_IV(iv);
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key, ivPara);
		
		byte[] inBuf = new byte[16];
		byte[] OutBuf = new byte[cipher.getOutputSize(inBuf.length)];
		File fileIn = new File(fileInPath);
		FileInputStream fileInS = new FileInputStream(fileIn);
		File fileOut = new File(fileOutPath);
		FileOutputStream fileOutS = new FileOutputStream(fileOut);
		
		int no_byte=0;
		int cipherBlockSize;
		while((no_byte = fileInS.read(inBuf))!=-1) {
			cipherBlockSize = cipher.update(inBuf, 0, no_byte,OutBuf);
			fileOutS.write(OutBuf, 0, cipherBlockSize);
		}
		cipherBlockSize = cipher.doFinal(OutBuf,0);
		fileOutS.write(OutBuf, 0, cipherBlockSize);
		fileOutS.close();
		fileInS.close();
		
	}
	
	void decrypt_byFile(String fileInPath, String fileOutPath, String pwd, String iv) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, IOException, ShortBufferException {
		SecretKeySpec key = set_key(pwd,16);
		IvParameterSpec ivPara = set_128bit_IV(iv);
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, key, ivPara);
		
		byte[] inBuf = new byte[16];
		byte[] OutBuf = new byte[cipher.getOutputSize(inBuf.length)];
		File fileIn = new File(fileInPath);
		FileInputStream fileInS = new FileInputStream(fileIn);
		File fileOut = new File(fileOutPath);
		FileOutputStream fileOutS = new FileOutputStream(fileOut);
		
		int no_byte=0;
		int cipherBlockSize;
		while((no_byte = fileInS.read(inBuf))!=-1) {
			cipherBlockSize = cipher.update(inBuf, 0, no_byte,OutBuf);
			fileOutS.write(OutBuf, 0, cipherBlockSize);
		}
		cipherBlockSize = cipher.doFinal(OutBuf,0);
		fileOutS.write(OutBuf, 0, cipherBlockSize);
		fileOutS.close();
		fileInS.close();
		
	}
	

	
	
	

	void saveResults(String filename, String contents) {
		File file = new File(filename);
        FileWriter fr = null;
        BufferedWriter br = null;
        String dataWithNewLine=contents; //+System.getProperty("line.separator");
        try{
            fr = new FileWriter(file);
            br = new BufferedWriter(fr);            
            br.write(dataWithNewLine);
            
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                br.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
	
	
}
	
	

