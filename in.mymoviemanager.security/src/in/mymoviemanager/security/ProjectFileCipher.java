package in.mymoviemanager.security;

import in.mymoviemanager.security.key.KEY;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Encrypting/Decrypting the movie project file
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
public class ProjectFileCipher {

	private static byte[] KEY = new byte[1024];
	static String filename = "abc.txt";
	private KEY k;

	private static byte[] encrypt(final byte[] text, int x) {
		return xor(text, x);
	}

	private static byte[] decrypt(final byte[] hash, int x) {
		return xor(hash, x);
	}

	private File file;

	public ProjectFileCipher(File file, KEY key, int flag) {
		this.file = file;
		this.k = key;
		if (flag == 1)
			KEY = key.getKey();
	}

	private static byte[] xor(final byte[] input, int x) {
		if (x == 0)
			x = input.length;
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

	public File EncryptFile() {
		try {
			File f1 = new File(file.getPath() + "_enc");
			FileInputStream fis = new FileInputStream(file);
			FileOutputStream fos = new FileOutputStream(f1);
			byte[] buffer = new byte[1024];
			if (file.exists() && file.canRead()) {
				int x = fis.read(buffer);
				while (true) {
					if (x == -1)
						break;
					fos.write(encrypt(buffer, x));
					buffer = new byte[1024];
					x = fis.read(buffer);
				}
			} else {
				throw new Exception("Can not read from file");
			}
			fis.close();
			fos.close();
			fis = new FileInputStream(f1);
			fos = new FileOutputStream(file);
			int x = fis.read(buffer);
			fos.write(KEY);
			while (true) {
				if (x == -1)
					break;
				fos.write(buffer, 0, x);
				buffer = new byte[1024];
				x = fis.read(buffer);
			}
			fis.close();
			fos.close();
			f1.delete();
			return file;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public File DecryptFile() {
		try {
			File f2 = new File(file.getPath() + "_dec");
			FileInputStream fis = new FileInputStream(file);
			FileOutputStream fos = new FileOutputStream(f2);
			byte[] buffer = new byte[1024];
			fis.read(KEY);
			if (file.exists() && file.canRead()) {
				int x = fis.read(buffer);
				while (true) {
					if (x == -1)
						break;
					fos.write(decrypt(buffer, x));
					buffer = new byte[1024];
					x = fis.read(buffer);
				}
			} else {
				new Exception("Can not read from file");
			}

			fis.close();
			fos.close();
			return f2;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
