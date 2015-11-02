package in.mymoviemanager.filesearch;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FilenameUtils;

/**
 * Searching iteratively for movie files in a folder
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
public final class MovieSearch2 {

	/**
	 * Returns list of files in a folder (iteratively)
	 * 
	 * @param aStartingDir
	 * @return
	 * @throws FileNotFoundException
	 */
	static public List<File> getFileListing(File aStartingDir)
			throws FileNotFoundException {
		List<File> finalResult = new ArrayList<File>();
		validateDirectory(aStartingDir);
		List<File> result = getFileListingNoSort(aStartingDir);
		for (File file : result) {
			if (!file.isDirectory() && checkExtension(file)) {
				finalResult.add(file);
			}
		}
		Collections.sort(finalResult);
		return finalResult;
	}

	static private List<File> getFileListingNoSort(File aStartingDir)
			throws FileNotFoundException {
		List<File> result = new ArrayList<File>();
		File[] filesAndDirs = aStartingDir.listFiles();
		List<File> filesDirs = Arrays.asList(filesAndDirs);
		for (File file : filesDirs) {
			result.add(file);
			if (!file.isFile()) {
				List<File> deeperList = getFileListingNoSort(file);
				result.addAll(deeperList);
			}
		}
		return result;
	}

	static private void validateDirectory(File aDirectory)
			throws FileNotFoundException {
		if (aDirectory == null) {
			throw new IllegalArgumentException("Directory should not be null.");
		}
		if (!aDirectory.exists()) {
			throw new FileNotFoundException("Directory does not exist: "
					+ aDirectory);
		}
		if (!aDirectory.isDirectory()) {
			throw new IllegalArgumentException("Is not a directory: "
					+ aDirectory);
		}
		if (!aDirectory.canRead()) {
			throw new IllegalArgumentException("Directory cannot be read: "
					+ aDirectory);
		}
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