package in.mymoviemanager.imdb.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Poster {
 List<String> cover;

public List<String> getCover() {
	return cover;
}

public void setCover(List<String> cover) {
	this.cover = cover;
}

}