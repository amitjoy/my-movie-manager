package in.mymoviemanager.wordguess;

import in.mymoviemanager.badges.AllGenresListWithSynonym;
import in.mymoviemanager.badges.Badges;
import in.mymoviemanager.badges.Genre;
import in.mymoviemanager.badges.Synonym;

/**
 * Class to manipulate extra badge names for text autocomplete
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
public class GuessBadgeName {

	public String getImageNameFromBadgeName(String badgeName) {

		Badges badges = AllGenresListWithSynonym.getBadges();
		for (Genre genre : badges.getGenres()) {
			if (genre.getName().equalsIgnoreCase(badgeName))
				return genre.getImageFileName();
			for (Synonym synonym : genre.getSynonyms()) {
				if (synonym.getName().equalsIgnoreCase(badgeName))
					return genre.getImageFileName();
			}
		}

		return "Other";
	}

}
