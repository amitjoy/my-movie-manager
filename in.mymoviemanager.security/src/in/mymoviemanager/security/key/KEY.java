package in.mymoviemanager.security.key;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.security.SecureRandom;

/**
 * The private key for the encryption/decryption of movie project file
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
public class KEY implements Serializable {
	private static SecureRandom random = new SecureRandom();
	private static byte[] KEY = new byte[1024];

	private static byte[] xor(final byte[] input, int x) {
		x = 1024;
		final byte[] output = new byte[x];
		final byte[] secret = KEY;
		int spos = 0;
		for (int pos = 0; pos < x; ++pos) {
			output[pos] = (byte) (input[pos] ^ secret[spos]);
			spos += 1;
			if (spos >= secret.length) {
				spos = 0;
			}
		}
		return output;
	}

	public byte[] initKey(File file) {
		try {
			FileInputStream fis = new FileInputStream(file);
			byte[] buffer = new byte[1024];

			random.setSeed(new byte[] { 10, 20, 30 });
			random.nextBytes(KEY);

			if (file.exists() && file.canRead()) {
				int x = fis.read(buffer);
				while (true) {
					if (x == -1)
						break;
					KEY = xor(buffer, x);
					buffer = new byte[1024];
					x = fis.read(buffer);
				}
			} else {
				new Exception("Can not read from file");
			}
			return KEY;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public byte[] getKey() {
		return KEY;
	}
}
