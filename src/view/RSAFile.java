package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class RSAFile {
	private static Base64.Encoder encoder = Base64.getEncoder();
	private static Base64.Decoder decoder = Base64.getDecoder();
	static SecureRandom srandom = new SecureRandom();
	
	private static void processFile(Cipher ci, InputStream in, OutputStream out) throws IOException, IllegalBlockSizeException, BadPaddingException {
		byte[] ibuf = new byte[1024];
		int len;
		while((len = in.read(ibuf)) != -1) {
			byte[] obuf = ci.update(ibuf, 0, len);
			if(obuf != null) out.write(obuf);
		}
		byte[] obuf = ci.doFinal();
		if(obuf != null) out.write(obuf);
		out.close();
	}
	
	public static void doGenkey(String path, int keysize) throws IOException, NoSuchAlgorithmException {
		File des = new File(path);
		if(!des.exists()) {
			des.mkdirs();
		}
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
		LocalDateTime now = LocalDateTime.now();
		
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(keysize);
		KeyPair kp = kpg.generateKeyPair();
		try (FileOutputStream out = new FileOutputStream(path + "/private_" + dtf.format(now) + ".key")) {
			out.write(encoder.encodeToString(kp.getPrivate().getEncoded()).getBytes(StandardCharsets.UTF_8));
		}
		
		try (FileOutputStream out = new FileOutputStream(path + "/public_" + dtf.format(now) + ".pub")) {
			out.write(encoder.encodeToString(kp.getPublic().getEncoded()).getBytes(StandardCharsets.UTF_8));
		}
	}
	
	public static PrivateKey readPrivateKey(String path) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] bytes = Files.readAllBytes(Paths.get(path));
		String privateString = new String(bytes, StandardCharsets.UTF_8);
		bytes = decoder.decode(privateString);
		PKCS8EncodedKeySpec ks = new PKCS8EncodedKeySpec(bytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePrivate(ks);
	}
	
	public static PublicKey readPublicKey(String path) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] bytes = Files.readAllBytes(Paths.get(path));
		String publicString = new String(bytes, StandardCharsets.UTF_8);
		bytes = decoder.decode(publicString);
		X509EncodedKeySpec ks = new X509EncodedKeySpec(bytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePublic(ks);
	}
	
	public static void doEncryptRSAWithAES(PublicKey pub, String inputFile, String outputFile) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		kgen.init(128);
		SecretKey skey = kgen.generateKey();
		
		byte[] iv = new byte[128/8];
		srandom.nextBytes(iv);
		IvParameterSpec ivspec = new IvParameterSpec(iv);
		
		try(FileOutputStream out = new FileOutputStream(outputFile)) {
			{
				Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
				cipher.init(Cipher.ENCRYPT_MODE, pub);
				byte[] b = cipher.doFinal(skey.getEncoded());
				out.write(b);
				System.err.println("AES key length " + b.length);
			}
			out.write(iv);
			System.err.println("IV length " + iv.length);
			
			Cipher ci = Cipher.getInstance("AES/CBC/PKCS5Padding");
			ci.init(Cipher.ENCRYPT_MODE, skey, ivspec);
			try(FileInputStream in = new FileInputStream(inputFile)){
				processFile(ci, in, out);
			}
		}
	}
	
	public static void doDecryptRSAWithAES(PrivateKey pvt, String inputFile, String outputFile) throws FileNotFoundException, IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		try(FileInputStream in = new FileInputStream(inputFile)) {
			SecretKey skey = null;
			{
				Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
				cipher.init(Cipher.DECRYPT_MODE, pvt);
				byte[] b = new byte[256];
				in.read(b);
				byte[] keyb = cipher.doFinal(b);
				skey = new SecretKeySpec(keyb, "AES");
			}
			byte[] iv = new byte[128/8];
			in.read(iv);
			IvParameterSpec ivspec = new IvParameterSpec(iv);
			
			Cipher ci = Cipher.getInstance("AES/CBC/PKCS5Padding");
			ci.init(Cipher.DECRYPT_MODE, skey, ivspec);
			
			try(FileOutputStream out = new FileOutputStream(outputFile)){
				processFile(ci, in, out);
			}
		}
	}
}
