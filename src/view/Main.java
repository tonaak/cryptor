package view;

import java.security.Key;

public class Main {

	public static void main(String[] args) throws Exception {

			RSA rsa = new RSA();
			rsa.genKey();
			byte[] cipherText = rsa.encrypt("Wtf is this shit");
			String text = new String(cipherText);
			
			System.out.println(text);
			Key key = rsa.getPrivateKey();
			String plainText = rsa.decrypt(cipherText, key);
			System.out.println(plainText);
	}

}
