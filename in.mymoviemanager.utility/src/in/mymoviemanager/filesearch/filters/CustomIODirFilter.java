package in.mymoviemanager.filesearch.filters;

import java.io.File;

import org.apache.commons.io.filefilter.IOFileFilter;

/**
 * Directory Filter for apache IO
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
public class CustomIODirFilter implements IOFileFilter {

	@Override
	public boolean accept(File arg0, String arg1) {
		return true;
	}

	@Override
	public boolean accept(File arg0) {
		return true;
	}

}
