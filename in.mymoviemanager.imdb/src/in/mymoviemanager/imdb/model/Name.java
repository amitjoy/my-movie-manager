package in.mymoviemanager.imdb.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Immediate Child Model for IMDb
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
@XmlRootElement
public class Name {

	private String item = new String();

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

}
