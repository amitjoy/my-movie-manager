package in.mymoviemanager.imdb.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Root Model from IMDB
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
@XmlRootElement(name = "IMDBDocumentList")
public class IMDBDocumentList {

	docList item;

	public docList getItem() {
		return item;
	}

	public void setItem(docList item) {
		this.item = item;
	}
}
