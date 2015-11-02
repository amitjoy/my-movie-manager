package in.mymoviemanager.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Encrypting .moviemanager file name
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
public class MovieProjectNameEncryption {

	/**
	 * Encryption of folder name which is set as a filename for the movie
	 * project
	 * 
	 * @param foldername
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public String encrypt(String foldername) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		if (foldername == null)
			return "";
		md.update(foldername.getBytes());

		byte byteData[] = md.digest();

		// convert the byte to hex format method 1
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
					.substring(1));
		}
		return sb.toString().substring(0, 6);
	}
}
