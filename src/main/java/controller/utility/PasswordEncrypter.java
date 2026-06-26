package controller.utility;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncrypter {
	public static String toHash(String password, String email) throws NoSuchAlgorithmException {
		String hashString = null;
		
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-512");
			String passwordDaCifrare = password + email;
			byte[] hash = digest.digest(passwordDaCifrare.getBytes(StandardCharsets.UTF_8));
			
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < hash.length; i++) {
				sb.append(Integer.toHexString((hash[i] & 0xFF) | 0x100).substring(1, 3));
			}
			
			hashString = sb.toString();
		}
		catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw e;
		}
		
		return hashString;
	}
}