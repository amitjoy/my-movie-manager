package in.mymoviemanager.imdb.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
/**
 * Directors Model
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 *
 */
@XmlRootElement
public class Directors {
 List<String> item;

public List<String> getItem() {
	return item;
}

public void setItem(List<String> item) {
	this.item = item;
}


}
