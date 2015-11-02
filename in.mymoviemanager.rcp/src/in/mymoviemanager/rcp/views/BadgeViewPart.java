package in.mymoviemanager.rcp.views;

import in.mymoviemanager.events.EventConstants;
import in.mymoviemanager.rcp.filerepository.VideoFileRepository;
import in.mymoviemanager.rcp.model.Tag;
import in.mymoviemanager.rcp.model.VideoFile;
import in.mymoviemanager.rcp.views.listenerProvider.BadgeLabelProvider;
import in.mymoviemanager.rcp.views.listenerProvider.GalleryContentProvider;
import in.mymoviemanager.rcp.views.listenerProvider.GalleryLabelProvider;
import in.mymoviemanager.rcp.views.listenerProvider.GalleryMouseListener;
import in.mymoviemanager.xml.service.IXMLManipulation;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBException;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.e4.ui.workbench.swt.modeling.EMenuService;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.nebula.jface.galleryviewer.GalleryTreeViewer;
import org.eclipse.nebula.widgets.gallery.DefaultGalleryGroupRenderer;
import org.eclipse.nebula.widgets.gallery.DefaultGalleryItemRenderer;
import org.eclipse.nebula.widgets.gallery.Gallery;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.mihalis.opal.opalDialog.Dialog;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * Tree Viewer Representation for Badges
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
public class BadgeViewPart {

	private Text text;

	@Inject
	MDirtyable dirtyable;

	@SuppressWarnings("restriction")
	@Inject
	EMenuService service;

	private GalleryTreeViewer galleryTreeViewer;

	@Inject
	IEclipseContext context;

	@Inject
	ESelectionService selectionService;

	@Inject
	IEventBroker broker;

	@Inject
	EPartService partService;

	@Inject
	@Named(IServiceConstants.ACTIVE_SHELL)
	Shell shell;

	VideoFile file;
	VideoFileRepository repository, currentRepo;

	@Inject
	IXMLManipulation manipulation;

	Gallery gallery;

	List<Tag> list;

	Image itemImage;

	public BadgeViewPart() {
	}

	/**
	 * Create contents of the view part.
	 */
	@PostConstruct
	public void createControls(Composite parent) {

		try {
			initialize();
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (JAXBException e1) {
			e1.printStackTrace();
		}
		GridLayoutFactory.fillDefaults().applyTo(parent);

		itemImage = new Image(parent.getDisplay(), Program.findProgram("png")
				.getImageData());
		itemImage = searchImage("music.png");
		DefaultGalleryGroupRenderer gr = new DefaultGalleryGroupRenderer();
		gr.setMinMargin(2);
		gr.setItemHeight(56);
		gr.setItemWidth(72);
		gr.setAutoMargin(true);

		DefaultGalleryItemRenderer ir = new DefaultGalleryItemRenderer();
		parent.setLayout(new FillLayout(SWT.HORIZONTAL));

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));

		gallery = new Gallery(composite, SWT.SINGLE | SWT.V_SCROLL);
		DefaultGalleryGroupRenderer renderer = new DefaultGalleryGroupRenderer();
		renderer.setItemSize(240, 140);
		gallery.setItemRenderer(new DefaultGalleryItemRenderer());
		gallery.setGroupRenderer(renderer);

		galleryTreeViewer = new GalleryTreeViewer(gallery);
		GridDataFactory.fillDefaults().grab(true, true)
				.applyTo(galleryTreeViewer.getGallery());
		galleryTreeViewer.setContentProvider(new GalleryContentProvider(
				galleryTreeViewer));
		galleryTreeViewer.setLabelProvider(new GalleryLabelProvider());
		galleryTreeViewer.getGallery().addMouseListener(
				new GalleryMouseListener(partService, service,
						selectionService, context, broker, galleryTreeViewer));

	}

	private void initialize() throws NoSuchAlgorithmException, JAXBException {
		repository = manipulation.convertToModel(
				(String) context.get("folder_location"), 1);
	}

	@PreDestroy
	public void dispose() {
	}

	@Focus
	public void setFocus() {
	}

	private static Image searchImage(String file) {
		Bundle bundle = FrameworkUtil.getBundle(BadgeLabelProvider.class);
		URL url = FileLocator.find(bundle, new Path("icons/" + file), null);
		ImageDescriptor image = ImageDescriptor.createFromURL(url);
		return image.createImage();

	}

	@Inject
	@Optional
	public void setTag(
			@UIEventTopic(EventConstants.NEW_TAG_ADD) final Object[] data)
			throws NoSuchAlgorithmException, JAXBException {
		Tag tag2 = new Tag();
		tag2.setName(((String) data[0]).toUpperCase());
		VideoFile vfile = (VideoFile) data[1];
		String noErrorDialog = (String) data[2];

		if (tagNotExistBefore(list, tag2) && tagValidate(tag2)) {
			// Check for watched and unwatched both for a movie, if present
			// remove the other one and add the new one, showing an information
			// message
			if (tagValidateForWatchedUnwatched(tag2, list)) {
				Dialog.inform(
						"Category Conflict",
						"You just tried to add "
								+ tag2.getName()
								+ " which conflicts with the opposite category already added to it. That's why the older one is removed.");
			}
			list.add(tag2);
			currentRepo = newRepositoryObjectCreation(repository, vfile, tag2);
		} else {
			if (noErrorDialog == null)
				Dialog.error("Badge Addition", "Error in adding Badge");
			return;
		}
		galleryTreeViewer.setInput(list);
		galleryTreeViewer.refresh();

		manipulation.convertToXml(currentRepo,
				(String) context.get("folder_location"), 1);
		broker.send(EventConstants.TAG_ADDED_AND_UPDATE_TAG_BROWSER, tag2);
		broker.send(EventConstants.UPDATE_SPECIFIC_BADGE_VIEW, tag2.getName());
	}

	private boolean tagValidateForWatchedUnwatched(Tag tag2, List list) {
		if (tag2.getName().equalsIgnoreCase("watched")) {
			Tag tag3 = new Tag();
			tag3.setName("UNWATCHED");
			if (tagNotExistBefore(list, tag3) == false) {
				broker.send(EventConstants.REMOVE_TAG, tag3);
				return true;
			}
		}

		if (tag2.getName().equalsIgnoreCase("unwatched")) {
			Tag tag3 = new Tag();
			tag3.setName("WATCHED");
			if (tagNotExistBefore(list, tag3) == false) {
				broker.send(EventConstants.REMOVE_TAG, tag3);
				return true;
			}
		}

		return false;
	}

	private boolean tagValidate(Tag tag2) {
		if (!tag2.getName().equals(""))
			return true;
		return false;
	}

	private boolean tagNotExistBefore(List<Tag> list, Tag tag) {
		for (Tag tg : list) {
			if (tg.getName().equals(tag.getName())) {
				return false;
			}
		}
		return true;
	}

	private VideoFileRepository newRepositoryObjectCreation(
			VideoFileRepository oldRepository, VideoFile fileToBeModified,
			Tag tagsToBeAdded) {
		List<Tag> tags = null;
		VideoFileRepository newRepository = new VideoFileRepository();
		List<VideoFile> files = new ArrayList<VideoFile>();
		for (VideoFile vfile : oldRepository.getRepo()) {
			if (vfile.getFileName().equals(fileToBeModified.getFileName())) {
				if (vfile.getTags() != null) {
					tags = vfile.getTags();
				} else {
					tags = new ArrayList<Tag>();
				}
				tags.add(tagsToBeAdded);
				vfile.setTags(tags);
			}
			files.add(vfile);
		}
		newRepository.setRepo(files);

		return newRepository;
	}

	@Inject
	@Optional
	public void showTagTable(
			@UIEventTopic(EventConstants.TAG_DETAILS_AT_MOUSE_CLICK) Object data)
			throws NoSuchAlgorithmException, JAXBException {
		if (data instanceof VideoFile) {
			String location = (String) context.get("folder_location");
			repository = manipulation.convertToModel(location, 1);
			for (VideoFile file : repository.getRepo()) {
				if (file.getFileName().equals(((VideoFile) data).getFileName()))
					data = file;
			}

			list = new ArrayList<Tag>();
			file = (VideoFile) data;
			if (file != null) {
				if (file.getTags() != null)
					for (Tag tagName : file.getTags()) {
						if (!tagName.getName().equals(""))
							list.add(tagName);
					}
			}

			galleryTreeViewer.setInput(list);

			context.set(VideoFile.class, file);
			context.set(VideoFileRepository.class, repository);
		}
	}

	private void tagWiseImageSet(Tag tag) {
		itemImage = searchImage(tag.getName() + ".png");

	}

	@Inject
	@Optional
	public void afterTagRemoved(
			@UIEventTopic(EventConstants.TAG_REMOVED) Object data) {
		if (data instanceof VideoFile) {
			list = new ArrayList<Tag>();
			file = (VideoFile) data;
			if (file != null) {
				if (file.getTags() != null)
					for (Tag tagName : file.getTags()) {
						if (!tagName.getName().equals(""))
							list.add(tagName);
					}
			}
			galleryTreeViewer.setInput(list);
			galleryTreeViewer.refresh();
		}
	}

	@Inject
	@Optional
	public void removeTag(@UIEventTopic(EventConstants.REMOVE_TAG) Object data)
			throws NoSuchAlgorithmException, JAXBException {
		if (data instanceof Tag) {
			currentRepo = newRepositoryObjectCreationForRemovingTags(
					repository, file, (Tag) data);
			manipulation.convertToXml(currentRepo,
					(String) context.get("folder_location"), 1);
			broker.send(EventConstants.TAG_REMOVED_AND_UPDATE_TAG_BROWSER, data);
			broker.send(EventConstants.TAG_REMOVED, file);
			broker.send(EventConstants.UPDATE_SPECIFIC_BADGE_VIEW,
					((Tag) data).getName());
		}
	}

	private VideoFileRepository newRepositoryObjectCreationForRemovingTags(
			VideoFileRepository oldRepository, VideoFile fileToBeModified,
			Tag tagsToBeRemoved) {
		List<Tag> tags = null;
		VideoFileRepository newRepository = new VideoFileRepository();
		List<VideoFile> files = new ArrayList<VideoFile>();
		List<Tag> tagList = new ArrayList<Tag>();
		for (VideoFile vfile : oldRepository.getRepo()) {
			if (vfile.getFileName().equals(fileToBeModified.getFileName())) {
				if (vfile.getTags() != null) {
					tags = vfile.getTags();
					for (Tag tag : tags) {
						if (!(tag.getName().equals(tagsToBeRemoved.getName()))) {
							tagList.add(tag);
						}
					}

					vfile.setTags(tagList);
				}
			}
			files.add(vfile);
		}
		newRepository.setRepo(files);

		return newRepository;
	}

	@Inject
	@Optional
	public void addTagIfGenreFoundFromImdb(
			@UIEventTopic(EventConstants.NEW_TAG_ADD_FROM_IMDB) Object[] data) {
		String imdbGenre = (String) data[0];
		VideoFile file = (VideoFile) data[1];
		boolean flag = true;
		Object[] newData = new Object[3];
		newData[0] = imdbGenre;
		newData[1] = file;

		if (imdbGenre != null) {
			if (flag) {
				newData[2] = "noErrorDialog";
				broker.post(EventConstants.NEW_TAG_ADD, newData);
			}
		}
	}

}
