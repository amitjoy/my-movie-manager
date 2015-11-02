package in.mymoviemanager.filesearch;

import in.mymoviemanager.filesearch.filters.CustomIODirFilter;
import in.mymoviemanager.filesearch.filters.CustomIOFileFilter;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.IOFileFilter;

/**
 * Searching iteratively for movie files in a folder
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
public final class MovieSearch {

	/**
	 * Listing all movie files under specified directory
	 * 
	 * @param startDir
	 * @return
	 */
	static public List<File> getFileListing(File startDir)
			throws IllegalArgumentException {
		IOFileFilter fileFilter = new CustomIOFileFilter();
		IOFileFilter dirFilter = new CustomIODirFilter();
		List<File> listOfFiles = (List<File>) FileUtils.listFiles(startDir,
				fileFilter, dirFilter);
		Collections.sort(listOfFiles);
		return listOfFiles;

	}

	/**
	 * Returns files which have subtitles (name of subtitle file must be same as
	 * of the main video file to be returned true)
	 * 
	 * @param filesList
	 * @return
	 */
	static public List<File> FilesListHavingSubtitles(List<File> filesList) {
		List<File> files = new ArrayList<File>();
		List<String> names = new ArrayList<String>();
		List<String> finalNames = new ArrayList<String>();
		HashMap<String, Integer> count = new HashMap<String, Integer>();
		for (File file : filesList) {
			names.add(FilenameUtils.removeExtension(file.getName()));
		}
		Set<String> uniqueSet = new HashSet<String>(names);
		for (String temp : uniqueSet) {
			count.put(temp, Collections.frequency(names, temp));
		}
		for (Map.Entry<String, Integer> entry : count.entrySet()) {
			if (entry.getValue() > 1) {
				finalNames.add(entry.getKey());
			}
		}

		for (String fileName : finalNames) {
			for (File file : filesList) {
				if ((fileName.equals(FilenameUtils.removeExtension(file
						.getName())) && (FilenameUtils.getExtension(file
						.getName()).contains("srt")))) {
					files.add(file);
				}
			}
		}

		return files;
	}
}