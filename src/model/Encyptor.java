package model;

import java.security.SecureRandom;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class Encyptor {
	public static void main(String[] args) throws Exception {
		
		String valueToEnc = "Cgy+Cp4/YIN9EDg3uQQwwouMbv3skE+CCSxvfGg7/eAxAYtWvbWt7iokTl4sNn8U";
		
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);

		KeySpec spec = new PBEKeySpec("123456789123456712345678".toCharArray(), salt, 65536, 256); // AES-256
		SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		byte[] key = f.generateSecret(spec).getEncoded();
		SecretKeySpec keySpec = new SecretKeySpec(key, "AES");

		byte[] ivBytes = new byte[16];
		random.nextBytes(ivBytes);
		IvParameterSpec iv = new IvParameterSpec(ivBytes);

		Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
		c.init(Cipher.ENCRYPT_MODE, keySpec, iv);
		byte[] encValue = c.doFinal(valueToEnc.getBytes());

		byte[] finalCiphertext = new byte[encValue.length+2*16];
		System.arraycopy(ivBytes, 0, finalCiphertext, 0, 16);
		System.arraycopy(salt, 0, finalCiphertext, 16, 16);
		System.arraycopy(encValue, 0, finalCiphertext, 32, encValue.length);

		System.out.println(finalCiphertext); ;
		
		
	}

}
