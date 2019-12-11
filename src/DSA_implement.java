 
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class DSA_implement {
	
	public KeyPair key_generate(int keySize) throws NoSuchAlgorithmException {
		long start = System.nanoTime(); 
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("DSA"); 
		keyPairGen.initialize(keySize); 
		long end = System.nanoTime(); 
        long elapsedTime = end - start;
        double seconds = (double)elapsedTime / 1_000_000_000.0;
        System.out.println(keySize + "-bit key generated in: " + seconds + "seconds");
		return keyPairGen.generateKeyPair();

	}
	
	public byte[] sign_document(String fileInpath, byte[] privateKey) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException {
		File filein = new File(fileInpath);
		byte[] fileContents = Files.readAllBytes(filein.toPath());
		int inputLength = fileContents.length;
		long start = System.nanoTime(); 
		PKCS8EncodedKeySpec KeySpec = new PKCS8EncodedKeySpec(privateKey);
		KeyFactory keyFactory = KeyFactory.getInstance("DSA");
		PrivateKey priKey = keyFactory.generatePrivate(KeySpec);
		Signature signature = Signature.getInstance("SHA256withDSA");
		signature.initSign(priKey);
        signature.update(fileContents);
        long end = System.nanoTime(); 
        long elapsedTime = end - start;
        double seconds = (double)elapsedTime / 1_000_000_000.0;
        double speed = (double)fileContents.length /seconds ;
		System.out.println("Signing time: " + seconds + " seconds, " + "speed= " + speed + " bytes/s"); 
        return signature.sign();	

	}
	
	public boolean verify_signature(String fileInpath, byte[] pubKey, byte[] sign) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException {
		File filein = new File(fileInpath);
		byte[] fileContents = Files.readAllBytes(filein.toPath());
		int inputLength = fileContents.length;
		long start = System.nanoTime(); 
		
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(pubKey); 
		KeyFactory keyFactory = KeyFactory.getInstance("DSA"); 
		PublicKey pub_Key = keyFactory.generatePublic(keySpec); 
		Signature signature = Signature.getInstance("SHA256withDSA"); 
		signature.initVerify(pub_Key); 
		signature.update(fileContents); 
		
        long end = System.nanoTime(); 
        long elapsedTime = end - start;
        double seconds = (double)elapsedTime / 1_000_000_000.0;
        double speed = (double)fileContents.length /seconds ;
		System.out.println("Verifing time: " + seconds + " seconds, " + "speed= " + speed + " bytes/s"); 
		return signature.verify(sign);	

	}	
	
	
	public static void main(String[] args) throws Exception {
//	    Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		DSA_implement dsaSample = new DSA_implement();
		Utilis helps = new Utilis();
		
		//AES_new.encrypt_128bit_oneShot(path, basepath + "/output/encypt_oneshot.txt", key, "1242");
		
		KeyPair pair = dsaSample.key_generate(3072);
		Key pubKey = pair.getPublic();
	    Key privKey = pair.getPrivate();
	    byte[] privKey_bytes = privKey.getEncoded();
	    byte[] pubKey_bytes = pubKey.getEncoded();
	    
		String basepath = "/home/bigbro/eclipse-workspace/SecurityHW/src/HW2";
		String path = basepath + "/data/grader_dict.txt";		
	    byte[] signature = dsaSample.sign_document(path, privKey_bytes);
	    boolean results = dsaSample.verify_signature(path,pubKey_bytes,signature);
	    System.out.println("Verifing results = " + results);
	    System.out.println();
	    
		path = basepath + "/data/dict.txt";		
	    byte[] signature2 = dsaSample.sign_document(path, privKey_bytes);
	    boolean results2 = dsaSample.verify_signature(path,pubKey_bytes,signature2);
	    System.out.println("Verifing results = " + results2);
	    

	}

}
