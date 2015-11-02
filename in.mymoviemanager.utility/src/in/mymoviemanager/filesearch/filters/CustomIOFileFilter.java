package in.mymoviemanager.filesearch.filters;

import in.mymoviemanager.filesearch.MovieExtensions;

import java.io.File;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.IOFileFilter;

/**
 * File Filter for Aapche IO
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
public class CustomIOFileFilter implements IOFileFilter {

	@Override
	public boolean accept(File file, String arg1) {
		if (checkExtension(file))
			return true;
		return false;
	}

	@Override
	public boolean accept(File file) {
		if (checkExtension(file))
			return true;
		return false;
	}

	/**
	 * Checking extension for list of returned files which matches the extension
	 * list
	 * 
	 * @param file
	 * @return
	 */
	static private boolean checkExtension(File file) {
		boolean flag = false;
		String ext = FilenameUtils.getExtension(file.getPath());
		String[] extensions = MovieExtensions.getExtensions();
		for (String extension : extensions) {
			if (ext.equals(extension)) {
				flag = true;
			}
		}
		return flag;
	}

}
