package in.mymoviemanager.imdb.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Actors Model
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
@XmlRootElement
public class Actors {
	List<String> item;

	public List<String> getItem() {
		return item;
	}

	public void setItem(List<String> item) {
		this.item = item;
	}

}
