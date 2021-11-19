package view;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

public class RSA {
	private KeyPair keypair;
	private PublicKey publicKey;
	private PrivateKey privateKey;
	
	public byte[] encrypt(String text) throws Exception{
		if(publicKey==null)
			genKey();
		
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] plainText = text.getBytes("UTF-8");
		byte[] cipherText = cipher.doFinal(plainText);
		return cipherText;
	}
	
	public String decrypt(byte[] text, Key key) throws Exception{
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] plainText = cipher.doFinal(text);
		String result = new String(plainText, "UTF-8");
		return result;
	}
	
	public void genKey() {
		KeyPairGenerator keyGenerator = null;
		try {
			keyGenerator = KeyPairGenerator.getInstance("RSA");
			keyGenerator.initialize(2048);
			keypair = keyGenerator.generateKeyPair();
			publicKey = keypair.getPublic();
			privateKey = keypair.getPrivate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public KeyPair getKeypair() {
		return keypair;
	}

	public void setKeypair(KeyPair keypair) {
		this.keypair = keypair;
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(PublicKey publicKey) {
		this.publicKey = publicKey;
	}

	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(PrivateKey privateKey) {
		this.privateKey = privateKey;
	}
	
	
}
