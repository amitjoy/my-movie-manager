package in.mymoviemanager.file;

import in.mymoviemanager.security.MovieProjectNameEncryption;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * Utility class
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
public class Utility {

	/**
	 * Returns size of a file
	 * 
	 * @param file
	 * @return
	 */
	public static String getSize(File file) {
		double bytes = file.length();
		double kilobytes = (bytes / 1024);
		double megabytes = (kilobytes / 1024);
		double gigabytes = (megabytes / 1024);
		double terabytes = (gigabytes / 1024);
		return Double.toString(Math.round(megabytes));
	}

	/**
	 * Rename Forefully
	 * 
	 * @param source
	 * @param target
	 * @return
	 * @throws IOException
	 */
	public static boolean forceRename(File source, File target)
			throws IOException {
		if (target.exists())
			target.delete();
		boolean flag = source.renameTo(target);
		return flag;
	}

	/**
	 * Checking a folder whether movie project file is present or not
	 * 
	 * @param location
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static boolean filePresence(String location)
			throws NoSuchAlgorithmException {
		boolean flag = false;
		MovieProjectNameEncryption encryption = new MovieProjectNameEncryption();
		String fileName = encryption.encrypt(location);
		File file = new File(location + File.separatorChar + fileName
				+ ".moviemanager");
		if (file.exists()) {
			flag = true;
		}
		return flag;
	}

}
