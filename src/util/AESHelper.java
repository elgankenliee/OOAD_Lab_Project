package util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AESHelper {

	private static SecretKeySpec secretKey;
	private static byte[] key;

	// Improved Key generation (SHA-256 instead of SHA-1)
	public static void setKey(final String myKey) {
		try {
			key = myKey.getBytes("UTF-8");
			MessageDigest sha = MessageDigest.getInstance("SHA-256");
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16); // AES uses 128-bit keys (16 bytes)
			secretKey = new SecretKeySpec(key, "AES");
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			System.out.println("Error generating key: " + e.toString());
			e.printStackTrace();
		}
	}

	// Encryption method using AES/CBC
	public static String encrypt(final String strToEncrypt, final String secret) {
		try {
			setKey(secret);
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			byte[] iv = new byte[16]; // 16-byte initialization vector (random)
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, new javax.crypto.spec.IvParameterSpec(iv));
			return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
		} catch (Exception e) {
			System.out.println("Error while encrypting: " + e.toString());
		}
		return null;
	}

	// Decryption method using AES/CBC ---> this method is faulty so it's unused in
	// the project
	public static String decrypt(final String strToDecrypt, final String secret) {
		try {
			setKey(secret);
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			byte[] iv = new byte[16]; // 16-byte initialization vector (same as during encryption)
			cipher.init(Cipher.DECRYPT_MODE, secretKey, new javax.crypto.spec.IvParameterSpec(iv));
			return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
		} catch (Exception e) {
			System.out.println("Error while decrypting: " + e.toString());
		}
		return null;
	}
}
