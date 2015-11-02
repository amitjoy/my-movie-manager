package in.mymoviemanager.utility;

import in.mymoviemanager.badges.AllGenresListWithSynonym;
import in.mymoviemanager.badges.Badges;
import in.mymoviemanager.badges.Genre;
import in.mymoviemanager.badges.Synonym;
import in.mymoviemanager.rcp.filerepository.VideoFileRepository;
import in.mymoviemanager.rcp.model.Tag;
import in.mymoviemanager.rcp.model.VideoFile;
import in.mymoviemanager.xml.convert.XMLManipulation;
import in.mymoviemanager.xml.service.IXMLManipulation;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

/**
 * Class for manipulating autocomplete for adding badges
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
public class BadgesListing {

	/**
	 * The main text autocomplete
	 * 
	 * @param folder_location
	 * @return
	 */
	public static String[] getBadgesListForAutoComplete(String folder_location) {
		NoDuplicateList<String> tagNamesList = new NoDuplicateList<String>();
		VideoFileRepository repository = null;
		try {
			IXMLManipulation manipulation = new XMLManipulation();
			repository = manipulation.convertToModel(folder_location, 1);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}

		for (VideoFile vfile : repository.getRepo()) {
			if (vfile.getTags() != null)
				for (Tag tag : vfile.getTags()) {
					tagNamesList.add(tag.getName());
				}
		}

		return tagNamesList.toArray(new String[tagNamesList.size()]);
	}

	/**
	 * Returns all predefined badges
	 * 
	 * @return
	 */
	public static String[] getPredefinedBadges() {
		List<String> tagNamesList = new ArrayList<String>();
		AllGenresListWithSynonym listWithSynonym = new AllGenresListWithSynonym();
		Badges badges = listWithSynonym.getBadges();
		if (badges != null)
			for (Genre genre : badges.getGenres()) {
				tagNamesList.add(genre.getName());
				for (Synonym synonym : genre.getSynonyms()) {
					tagNamesList.add(synonym.getName());
				}
			}
		return tagNamesList.toArray(new String[tagNamesList.size()]);
	}
}
