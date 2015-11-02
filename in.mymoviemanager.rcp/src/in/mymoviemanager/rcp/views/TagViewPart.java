package in.mymoviemanager.rcp.views;

import in.mymoviemanager.events.EventConstants;
import in.mymoviemanager.rcp.filerepository.VideoFileRepository;
import in.mymoviemanager.rcp.model.Tag;
import in.mymoviemanager.rcp.model.VideoFile;
import in.mymoviemanager.rcp.views.listenerProvider.AddTagButtonSelectionAdapter;
import in.mymoviemanager.rcp.views.listenerProvider.BadgeNameColumnBadgePartLabelProvider;
import in.mymoviemanager.rcp.views.listenerProvider.ImageColumnBadgePartLabelProvider;
import in.mymoviemanager.rcp.views.listenerProvider.TagTextKeyListener;
import in.mymoviemanager.rcp.views.listenerProvider.TagViewerContentProvider;
import in.mymoviemanager.rcp.views.listenerProvider.TagViewerFocusListener;
import in.mymoviemanager.rcp.views.listenerProvider.TagViewerTableMouseListener;
import in.mymoviemanager.xml.service.IXMLManipulation;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBException;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.e4.ui.workbench.swt.modeling.EMenuService;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.mihalis.opal.opalDialog.Dialog;

/**
 * Assigned Badges View
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
public class TagViewPart {
	private Text text;

	@Inject
	MDirtyable dirtyable;

	@SuppressWarnings("restriction")
	@Inject
	EMenuService service;

	private Table table;
	private Button btnNewButton;
	private TableViewer tableViewer;

	@Inject
	IEclipseContext context;

	@Inject
	ESelectionService selectionService;

	@Inject
	IEventBroker broker;

	@Inject
	@Named(IServiceConstants.ACTIVE_SHELL)
	Shell shell;

	VideoFile file;
	VideoFileRepository repository, currentRepo;

	@Inject
	IXMLManipulation manipulation;

	List<Tag> list;

	public TagViewPart() {
	}

	/**
	 * Create contents of the view part.
	 */
	@SuppressWarnings("restriction")
	@PostConstruct
	public void createControls(Composite parent) {

		try {
			initialize();
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (JAXBException e1) {
			e1.printStackTrace();
		}

		parent.setLayout(new GridLayout(4, false));

		Label lblTags = new Label(parent, SWT.NONE);
		lblTags.setText("Badges");
		new Label(parent, SWT.NONE);

		text = new Text(parent, SWT.BORDER);
		GridData gd_text = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_text.widthHint = 137;
		gd_text.heightHint = 20;
		text.setLayoutData(gd_text);

		btnNewButton = new Button(parent, SWT.NONE);
		btnNewButton.setEnabled(false);
		btnNewButton.addSelectionListener(new AddTagButtonSelectionAdapter(
				text, broker));
		text.addKeyListener(new TagTextKeyListener(btnNewButton));

		btnNewButton.setText(" Add Badge ");
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);

		tableViewer = new TableViewer(parent, SWT.BORDER | SWT.FULL_SELECTION
				| SWT.SINGLE);
		table = tableViewer.getTable();

		tableViewer.setContentProvider(new TagViewerContentProvider());
		// tableViewer.setLabelProvider(new TagViewerLabelProvider());
		tableViewer.getTable().addMouseListener(
				new TagViewerTableMouseListener(tableViewer, service,
						selectionService, context));
		tableViewer.getTable().addFocusListener(
				new TagViewerFocusListener(tableViewer));

		TableViewerColumn imageColumnViewer = new TableViewerColumn(
				tableViewer, SWT.NONE);
		imageColumnViewer.getColumn().setWidth(230);
		imageColumnViewer.getColumn().setText("Badge Image");
		imageColumnViewer
				.setLabelProvider(new ImageColumnBadgePartLabelProvider(
						tableViewer));

		TableViewerColumn badgeNameColumnViewer = new TableViewerColumn(
				tableViewer, SWT.NONE);
		badgeNameColumnViewer.getColumn().setWidth(400);
		badgeNameColumnViewer.getColumn().setText("Badge Name");
		badgeNameColumnViewer
				.setLabelProvider(new BadgeNameColumnBadgePartLabelProvider(
						tableViewer, null));

		tableViewer.getTable().setHeaderVisible(true);

		GridData gd_table = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_table.heightHint = 300;
		table.setLayoutData(gd_table);
		new Label(parent, SWT.NONE);

		ContextInjectionFactory.make(ImdbViewPart.class, context);
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

	@Inject
	@Optional
	public void setTag(
			@UIEventTopic(EventConstants.NEW_TAG_ADD) final String tag)
			throws NoSuchAlgorithmException, JAXBException {
		Tag tag2 = new Tag();
		tag2.setName(tag);

		currentRepo = newRepositoryObjectCreation(repository, file, tag2);
		if (tagNotExistBefore(list, tag2)) {
			list.add(tag2);
			tableViewer.add(tag2);
		} else
			Dialog.error("Badge Addition", "Badge does already exist");
		tableViewer.refresh();

		manipulation.convertToXml(currentRepo,
				(String) context.get("folder_location"), 1);
		broker.send(EventConstants.TAG_ADDED_AND_UPDATE_TAG_BROWSER, tag2);
		text.setText("");
	}

	private boolean tagNotExistBefore(List<Tag> list, Tag tag) {
		for (Tag tg : list) {
			if (tg.getName().equalsIgnoreCase(tag.getName())) {
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
			tableViewer.setInput(list);
			context.set(VideoFile.class, file);
			context.set(VideoFileRepository.class, repository);
		}
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
			tableViewer.setInput(list);
			tableViewer.refresh();
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

}
