package in.mymoviemanager.rcp.model.mock;

import in.mymoviemanager.rcp.filerepository.VideoFileRepository;
import in.mymoviemanager.rcp.model.Tag;
import in.mymoviemanager.rcp.model.VideoFile;
import in.mymoviemanager.rcp.views.MovieBrowserPart;
import in.mymoviemanager.utility.NoDuplicateList;
import in.mymoviemanager.xml.service.IXMLManipulation;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

/**
 * Mock Model for Badges Tree Viewer Browser
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
public class BadgesMockModel {

	public List<Tag> getTags(IXMLManipulation manipulation) {
		List<Tag> list = new ArrayList<Tag>();
		List<Tag> finalList = new ArrayList<Tag>();
		List<VideoFile> fileListUnderOneCategory = null;
		VideoFileRepository repository = null;
		try {
			repository = manipulation.convertToModel(
					MovieBrowserPart.getLocation(), 1);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		for (VideoFile file : repository.getRepo()) {
			if (file.getTags() != null)
				for (Tag tag : file.getTags()) {
					list.add(tag);
				}
		}

		List<Tag> newList = filterOutSameTags(list);

		for (Tag tag : newList) {
			fileListUnderOneCategory = new ArrayList<VideoFile>();
			for (VideoFile file : repository.getRepo()) {
				if (file.getTags() != null)
					if (checkAndAddTag(file.getTags(), tag))
						fileListUnderOneCategory.add(file);
			}
			tag.setVideoList(fileListUnderOneCategory);
			fileListUnderOneCategory = null;
			finalList.add(tag);
		}

		return finalList;

	}

	/*
	 * Check Badge to be added
	 */
	private boolean checkAndAddTag(List<Tag> tags, Tag tag) {
		for (Tag tg : tags) {
			if (tg.getName().equals(tag.getName()))
				return true;
		}
		return false;

	}

	/*
	 * Filtering out Badges on account of addition
	 */
	private List<Tag> filterOutSameTags(List<Tag> list) {
		List<Tag> nlist = new ArrayList<Tag>();
		NoDuplicateList<String> stringTagNames = new NoDuplicateList<String>();

		for (Tag tagName : list) {
			if (stringTagNames.add(tagName.getName()))
				nlist.add(tagName);
		}
		return nlist;
	}

}
