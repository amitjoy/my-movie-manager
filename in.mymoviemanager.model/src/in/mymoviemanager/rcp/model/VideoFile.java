package in.mymoviemanager.rcp.model;

import java.io.File;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Video Files Model
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
@XmlRootElement
public class VideoFile {

	private String fileName;
	private String fileSize;
	private String location;
	private boolean isSubtitlePresent;
	private String subtitleLocation;
	private String fileNameWithoutExtension;

	public String getFileNameWithoutExtension() {
		return fileNameWithoutExtension;
	}

	public void setFileNameWithoutExtension(String fileNameWithoutExtension) {
		this.fileNameWithoutExtension = fileNameWithoutExtension;
	}

	public String getSubtitleLocation() {
		return subtitleLocation;
	}

	public void setSubtitleLocation(String subtitleLocation) {
		this.subtitleLocation = subtitleLocation;
	}

	private List<Tag> tags;

	public String getFileName() {
		return fileName;
	}

	public boolean isSubtitlePresent() {
		return isSubtitlePresent;
	}

	public void setSubtitlePresent(boolean isSubtitlePresent) {
		this.isSubtitlePresent = isSubtitlePresent;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public File getFileObject(VideoFile file) {
		return new File(file.getLocation());
	}

}
