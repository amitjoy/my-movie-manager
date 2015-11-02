package in.mymoviemanager.rcp.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Badges Model
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
@XmlRootElement
public class Tag {

	private String name;
	private List<VideoFile> videoList;

	public List<VideoFile> getVideoList() {
		return videoList;
	}

	public void setVideoList(List<VideoFile> videoList) {
		this.videoList = videoList;
	}

	public String getName() {
		return name;
	}

	@XmlElement
	public void setName(String name) {
		this.name = name;
	}

}
