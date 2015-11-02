package in.mymoviemanager.badges;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Badges Model Class
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
@XmlRootElement
public class Badges {

	List<Genre> genres;

	public List<Genre> getGenres() {
		return genres;
	}

	public void setGenres(List<Genre> genres) {
		this.genres = genres;
	}

}
