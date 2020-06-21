package util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptionByMD5 {

	/**
	 * MD5º”√‹
	 * 
	 * @param password
	 * @return
	 */
	public static String MD5(String password) {
		byte[] secretBytes = null;
		try {
			secretBytes = MessageDigest.getInstance("md5").digest(password.getBytes());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return new BigInteger(1, secretBytes).toString();
	}

}
