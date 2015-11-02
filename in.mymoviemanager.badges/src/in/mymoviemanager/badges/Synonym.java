package in.mymoviemanager.badges;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Synonym Model Class
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
@XmlRootElement
public class Synonym {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
