package in.mymoviemanager.rcp.views;

import in.mymoviemanager.events.EventConstants;
import in.mymoviemanager.rcp.filerepository.VideoFileRepository;
import in.mymoviemanager.rcp.model.Tag;
import in.mymoviemanager.rcp.model.VideoFile;
import in.mymoviemanager.rcp.viewerfilter.FilteredTable;
import in.mymoviemanager.rcp.viewerfilter.FilteredTable.FilterMatcher;
import in.mymoviemanager.rcp.views.listenerProvider.IDColumnSpecificBadgeLabelProvider;
import in.mymoviemanager.rcp.views.listenerProvider.MovieNameColumnSpecificBadgeLabelProvider;
import in.mymoviemanager.rcp.views.listenerProvider.MovieOpenListener;
import in.mymoviemanager.rcp.views.listenerProvider.ViewerMouseListener;
import in.mymoviemanager.rcp.views.listenerProvider.ViewerSelectionListener;
import in.mymoviemanager.xml.service.IXMLManipulation;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBException;

import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.core.commands.EHandlerService;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.e4.ui.workbench.swt.modeling.EMenuService;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;

public class SpecificBadgeCategoryListingView {

	private static TableViewer tableViewer;

	@Named(IServiceConstants.ACTIVE_SHELL)
	Shell shell;

	VideoFileRepository repository;

	IEclipseContext context;

	@Inject
	IEventBroker broker;

	@Inject
	EPartService service;

	@Inject
	ECommandService comService;

	@Inject
	EHandlerService handlerService;

	@Inject
	ESelectionService selectionService;

	@Inject
	EMenuService menuService;

	@Inject
	IXMLManipulation manipulation;

	@Inject
	UISynchronize synchronize;

	String tagName;
	String folder;

	// ALL PARTS
	MPart detailsPart;
	MPart imdbPart;
	MPart subtitlePart;
	MPart tagPart;
	MPart actionPart;
	MPart badgeBrowserPart;
	MPart specialBadgesInfoPart;
	MPart movieBrowserPart;
	MPart badgesPart;

	static List<VideoFile> listOfFiles;

	@Inject
	public SpecificBadgeCategoryListingView(IEclipseContext context) {
		this.context = context;
		folder = (String) context.get("folder_location");
		// tableViewer = new TableViewer(Display.getCurrent().getActiveShell(),
		// SWT.SINGLE | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL
		// | SWT.FULL_SELECTION);
		// tableViewer.setContentProvider(ArrayContentProvider.getInstance());
	}

	/**
	 * Create contents of the view part.
	 */
	@PostConstruct
	public void createControls(Composite parent) {
		movieBrowserPart = service
				.findPart("in.mymoviemanager.rcp.part.moviesCollection");
		detailsPart = service
				.findPart("in.mymoviemanager.rcp.part.movieFileDetails");
		imdbPart = service.findPart("in.mymoviemanager.rcp.part.imdbDetails");
		subtitlePart = service
				.findPart("in.mymoviemanager.rcp.part.movieSubtiltle");
		tagPart = service.findPart("in.mymoviemanager.rcp.part.assignedBadges");
		actionPart = service.findPart("in.mymoviemanager.rcp.part.actionsView");
		badgeBrowserPart = service
				.findPart("in.mymoviemanager.rcp.part.badgesList");
		specialBadgesInfoPart = service
				.findPart("in.mymoviemanager.rcp.part.specialBadgesInfo");
		badgesPart = service.findPart("in.mymoviemanager.rcp.part.badgesList");

		tagName = (String) context.get(Tag.class.getName());

		FilterMatcher matcher = new FilterMatcher();
		FilteredTable table = new FilteredTable(parent, SWT.SINGLE | SWT.MULTI
				| SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION, matcher,
				true);

		// tableViewer = new TableViewer(parent, SWT.SINGLE | SWT.MULTI
		// | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
		tableViewer = table.getViewer();
		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		tableViewer.getTable().addSelectionListener(
				new ViewerSelectionListener(context, selectionService,
						menuService, broker, detailsPart, imdbPart,
						subtitlePart, tagPart, actionPart,
						specialBadgesInfoPart, service, null, null,
						tableViewer, synchronize));

		tableViewer.addOpenListener(new MovieOpenListener());
		tableViewer.getTable().addMouseListener(
				new ViewerMouseListener(context, selectionService, menuService,
						broker, detailsPart, imdbPart, subtitlePart, tagPart,
						actionPart, specialBadgesInfoPart, service, null, null,
						tableViewer));

		TableViewerColumn idColumnViewer = new TableViewerColumn(tableViewer,
				SWT.NONE);
		idColumnViewer.getColumn().setWidth(50);
		idColumnViewer.getColumn().setText("#");
		idColumnViewer.setLabelProvider(new IDColumnSpecificBadgeLabelProvider(
				tableViewer));

		TableViewerColumn movieNameColumnViewer = new TableViewerColumn(
				tableViewer, SWT.NONE);
		movieNameColumnViewer.getColumn().setWidth(425);
		movieNameColumnViewer.getColumn().setText("Movie Name");
		movieNameColumnViewer
				.setLabelProvider(new MovieNameColumnSpecificBadgeLabelProvider(
						tableViewer));

		tableViewer.getTable().setHeaderVisible(true);
	}

	private List<VideoFile> checkForVideoFiles(String name, String folder)
			throws NoSuchAlgorithmException, JAXBException {
		List<VideoFile> repo = manipulation.convertToModel(folder, 1).getRepo();
		List<VideoFile> newList = new ArrayList<VideoFile>();
		for (VideoFile file : repo) {
			if (file.getTags() != null)
				for (Tag tag : file.getTags()) {
					if (tag.getName().equals(name))
						newList.add(file);
				}
		}
		return newList;
	}

	@PreDestroy
	public void dispose() {
	}

	@Focus
	public void setFocus() {
		tableViewer.getTable().setFocus();
	}

	@Inject
	@Optional
	public void refreshView(
			@UIEventTopic(EventConstants.UPDATE_SPECIFIC_BADGE_VIEW) String tagName) {
		try {
			listOfFiles = checkForVideoFiles(tagName, folder);
			tableViewer.setInput(listOfFiles);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	@Inject
	@Optional
	public void refreshOnRename(
			@UIEventTopic(EventConstants.REFRESH_BADGE_VIEW_ON_RENAME) Object[] data) {
		List<VideoFile> newList = new ArrayList<VideoFile>();
		VideoFile oldFile = (VideoFile) data[0];
		VideoFile newFile = (VideoFile) data[1];
		if (listOfFiles != null)
			for (VideoFile file : listOfFiles) {
				if (file.getFileName().equals(oldFile.getFileName()))
					if (newFile != null)
						newList.add(newFile);
					else
						;
				listOfFiles = newList;
			}
		TableItem[] items = null;
		if (tableViewer != null) {
			items = tableViewer.getTable().getItems();
			if (items.length > 0)
				tableViewer.remove(items);
			tableViewer.setInput(newList);
		}
	}

	// public void setValues(VideoFile oldFile, VideoFile newFile) {
	// List<VideoFile> newList = new ArrayList<VideoFile>();
	// if (listOfFiles != null)
	// for (VideoFile file : listOfFiles) {
	// if (file.getFileName().equals(oldFile.getFileName()))
	// if (newFile != null)
	// newList.add(newFile);
	// else
	// ;
	// listOfFiles = newList;
	// }
	// TableItem[] items = null;
	// if (tableViewer != null) {
	// items = tableViewer.getTable().getItems();
	// if (items.length > 0)
	// tableViewer.remove(items);
	// tableViewer.setInput(newList);
	// }
	// }
}
