package in.mymoviemanager.rcp.filerepository;

import in.mymoviemanager.rcp.model.VideoFile;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Video File Repository Model
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
@XmlRootElement
public class VideoFileRepository {

	private List<VideoFile> repo = new ArrayList<VideoFile>();

	public List<VideoFile> getRepo() {
		return repo;
	}

	@XmlElement
	public void setRepo(List<VideoFile> repo) {
		this.repo = repo;
	}
}
