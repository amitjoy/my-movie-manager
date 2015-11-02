package in.mymoviemanager.rcp.views;

import in.mymoviemanager.events.EventConstants;
import in.mymoviemanager.file.Utility;
import in.mymoviemanager.filesearch.MovieSearch;
import in.mymoviemanager.rcp.filerepository.VideoFileRepository;
import in.mymoviemanager.rcp.handlers.NewFolderHandler;
import in.mymoviemanager.rcp.model.Tag;
import in.mymoviemanager.rcp.model.VideoFile;
import in.mymoviemanager.rcp.viewerfilter.FilteredTable;
import in.mymoviemanager.rcp.viewerfilter.FilteredTable.FilterMatcher;
import in.mymoviemanager.rcp.views.listenerProvider.IDColumnMovieBrowserLabelProvider;
import in.mymoviemanager.rcp.views.listenerProvider.MovieBrowserPartListener;
import in.mymoviemanager.rcp.views.listenerProvider.MovieNameColumnMovieBrowserLabelProvider;
import in.mymoviemanager.rcp.views.listenerProvider.MovieOpenListener;
import in.mymoviemanager.rcp.views.listenerProvider.ViewerMouseListener;
import in.mymoviemanager.rcp.views.listenerProvider.ViewerSelectionListener;
import in.mymoviemanager.security.MovieProjectNameEncryption;
import in.mymoviemanager.xml.service.IXMLManipulation;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.core.commands.EHandlerService;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.css.swt.theme.IThemeEngine;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.e4.ui.workbench.swt.modeling.EMenuService;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.mihalis.opal.opalDialog.Dialog;
import org.mihalis.opal.tipOfTheDay.TipOfTheDay;
import org.mihalis.opal.tipOfTheDay.TipOfTheDay.TipStyle;
import org.osgi.service.prefs.Preferences;

/**
 * Movies Browser Part
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
public class MovieBrowserPart {

	private TableViewer tableViewer;

	@Named(IServiceConstants.ACTIVE_SHELL)
	Shell shell;

	VideoFileRepository repository;

	@Inject
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
	EModelService modelService;

	@Inject
	EMenuService menuService;

	@Inject
	IXMLManipulation manipulation;

	@Inject
	UISynchronize synchronize;

	// @Inject
	// IWindowTrim trim;

	MPart detailsPart;
	MPart imdbPart;
	MPart subtitlePart;
	MPart tagPart;
	MPart actionPart;
	MPart badgeBrowserPart;
	MPart specialBadgesInfoPart;
	MPart movieBrowserPart;
	MPart badgesPart;

	MWindow window;

	private String favourite_folder;

	static String location;

	@PostConstruct
	@SuppressWarnings("restriction")
	public void createComposite(Composite parent, MApplication application,
			MWindow window, @Named(IServiceConstants.ACTIVE_SHELL) Shell shell) {

		checkPreferencesStore();
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

		service.addPartListener(new MovieBrowserPartListener(movieBrowserPart,
				tableViewer));
		// Hide Parts
		hidePart();

		// Filter
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
						specialBadgesInfoPart, service, null, tableViewer,
						null, synchronize));
		tableViewer.addOpenListener(new MovieOpenListener());
		tableViewer.getTable().addMouseListener(
				new ViewerMouseListener(context, selectionService, menuService,
						broker, detailsPart, imdbPart, subtitlePart, tagPart,
						actionPart, specialBadgesInfoPart, service, null,
						tableViewer, null));

		TableViewerColumn idColumnViewer = new TableViewerColumn(tableViewer,
				SWT.NONE);
		idColumnViewer.getColumn().setWidth(50);
		idColumnViewer.getColumn().setText("#");
		idColumnViewer.setLabelProvider(new IDColumnMovieBrowserLabelProvider(
				tableViewer));

		TableViewerColumn movieNameColumnViewer = new TableViewerColumn(
				tableViewer, SWT.NONE);
		movieNameColumnViewer.getColumn().setWidth(425);
		movieNameColumnViewer.getColumn().setText("Movie Name");
		movieNameColumnViewer
				.setLabelProvider(new MovieNameColumnMovieBrowserLabelProvider(
						tableViewer));
		// System.out.println(trim.isCloseable());
		tableViewer.getTable().setHeaderVisible(true);

		/**
		 * Check if Preference has favorite folder. If set, open it
		 */
		if (new File(favourite_folder).isDirectory()) {
			try {
				if (Utility.filePresence(favourite_folder)) {
					NewFolderHandler handler = new NewFolderHandler(
							favourite_folder);
					Command command = comService
							.getCommand("in.mymoviemanager.rcp.command.newFolder");
					handlerService.activateHandler(
							"in.mymoviemanager.rcp.command.newFolder", handler);
					ParameterizedCommand cmd = comService.createCommand(
							"in.mymoviemanager.rcp.command.newFolder", null);
					handlerService.executeHandler(cmd);
				}
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Tip of the day for movie suggestions
	 * 
	 * @param shell
	 */
	private void openTipOfTheDay(Shell shell) {
		final TipOfTheDay tip = new TipOfTheDay();
		tip.addTip("This is the first tip<br/> "
				+ "<b>This is the first tip</b> "
				+ "<u>This is the first tip</u> "
				+ "<i>This is the first tip</i> " + "This is the first tip "
				+ "This is the first tip<br/>" + "This is the first tip "
				+ "This is the first tip");
		tip.addTip("This is the second tip<br/> "
				+ "<b>This is the second tip</b> "
				+ "<u>This is the second tip</u> <br/>"
				+ "<i>This is the second tip</i> " + "This is the second tip "
				+ "This is the second tip <br/>" + "This is the second tip "
				+ "This is the second tip");

		tip.addTip("This is the third tip<br/> "
				+ "<b>This is the third tip</b> "
				+ "<u>This is the third tip</u> <br/>"
				+ "<i>This is the third tip</i> ");
		tip.setStyle(TipStyle.TWO_COLUMNS);
		tip.open(shell);
	}

	private void hidePart() {
		detailsPart.setVisible(false);
		imdbPart.setVisible(false);
		subtitlePart.setVisible(false);
		tagPart.setVisible(false);
		actionPart.setVisible(false);
		badgeBrowserPart.setVisible(false);
		specialBadgesInfoPart.setVisible(false);
		badgesPart.setVisible(false);
	}

	@Focus
	public void setFocus() {
		// badgesPart
		tableViewer.getTable().setFocus();
	}

	@Inject
	@Optional
	public void newFolderSelectEvent(
			@UIEventTopic(EventConstants.NEW_MOVIE_FOLDER) final String topic,
			final MWindow window) throws InvocationTargetException,
			InterruptedException {

		if (topic != null) {
			hidePart();
			TableItem[] items = tableViewer.getTable().getItems();
			if (items.length > 0)
				tableViewer.remove(items);

			ProgressMonitorDialog dialog = new ProgressMonitorDialog(shell);

			dialog.run(true, true, new IRunnableWithProgress() {
				@Override
				public void run(IProgressMonitor monitor)
						throws InvocationTargetException, InterruptedException {
					monitor.beginTask("Checking Video Files In " + topic
							+ " For Indexing", 100);
					VideoFileRepository filez = null;

					for (int i = 1; i < 11; i++) {
						try {
							if (i == 9) {
								// MAIN WORK
								File startingDirectory = new File(topic);
								List<File> files = null;
								List<File> fileWithSubs = null;
								files = MovieSearch
										.getFileListing(startingDirectory);
								fileWithSubs = MovieSearch
										.FilesListHavingSubtitles(files);

								filez = wrap(files, fileWithSubs);
								try {
									manipulation.convertToXml(filez, topic, 1);
								} catch (NoSuchAlgorithmException e) {
									e.printStackTrace();
								} catch (JAXBException e) {
									e.printStackTrace();
								}
								// END
							}
							TimeUnit.SECONDS.sleep(2);
							monitor.subTask("Indexing " + (i * 10) + "%");
							if (monitor.isCanceled()) {
								try {
									manipulation.deleteProjectFile();
								} catch (Exception e) {

								}
								monitor.done();
								monitor.subTask("Cancelling");
								return;
							}
							monitor.worked(10);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					}
					context.set(VideoFileRepository.class, filez);
					// service.showPart(badgeBrowserPart,
					// PartState.VISIBLE);

					badgeBrowserPart.setVisible(true);

					broker.send(EventConstants.EXISTING_MOVIE_FOLDER, topic);
					window.setLabel("My Movie Manager Beta - " + topic);
				}

				private VideoFileRepository wrap(List<File> files,
						List<File> filesWithSubs) {
					VideoFileRepository repository = new VideoFileRepository();
					List<VideoFile> filez = new ArrayList<VideoFile>();
					VideoFile file2 = null;
					for (File file : files) {
						file2 = new VideoFile();
						if (!FilenameUtils.getExtension(file.getName()).equals(
								"srt"))
							for (File subFoundFile : filesWithSubs) {
								if (FilenameUtils.removeExtension(
										file.getName()).equals(
										FilenameUtils
												.removeExtension(subFoundFile
														.getName()))) {
									file2.setSubtitlePresent(true);
									file2.setSubtitleLocation(FilenameUtils
											.getFullPathNoEndSeparator(subFoundFile
													.getAbsolutePath()));
								}
							}
						file2.setFileName(file.getName());
						file2.setFileNameWithoutExtension(FilenameUtils
								.getBaseName(file.getName()));
						file2.setFileSize(Long.toString(FileUtils.sizeOf(file)
								/ FileUtils.ONE_MB));
						file2.setLocation(file.getPath());
						if (!(FilenameUtils.getExtension(file2.getFileName())
								.equals("srt")))
							filez.add(file2);
						file2 = null;
					}
					repository.setRepo(filez);
					return repository;
				}

			});
		}
	}

	@Inject
	@Optional
	public void existingFolderEvent(
			@UIEventTopic(EventConstants.EXISTING_MOVIE_FOLDER) String location,
			final MWindow window, final MApplication application) {
		badgesPart.setVisible(false);
		detailsPart.setVisible(false);
		tagPart.setVisible(false);
		imdbPart.setVisible(false);
		subtitlePart.setVisible(false);
		repository = (VideoFileRepository) context
				.get(VideoFileRepository.class);
		List<VideoFile> list = new ArrayList<VideoFile>();
		for (VideoFile file : repository.getRepo()) {
			list.add(file);
		}
		TableItem[] items = tableViewer.getTable().getItems();
		if (items.length > 0)
			tableViewer.remove(items);

		tableViewer.setInput(list);

		badgeBrowserPart.setVisible(true);
		this.location = location;
		window.setLabel("My Movie Manager Beta - " + location);
	}

	@Inject
	@Optional
	public void rescanFolderEvent(
			@UIEventTopic(EventConstants.RESCAN_FOLDER) final String topic,
			final MWindow window) throws InvocationTargetException,
			InterruptedException {

		MovieProjectNameEncryption encryption = new MovieProjectNameEncryption();
		String fileName = null;
		try {
			fileName = encryption.encrypt(topic);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		if (topic != null
				&& new File(topic + File.separator + fileName + ".moviemanager")
						.isFile()) {
			ProgressMonitorDialog dialog = new ProgressMonitorDialog(shell);

			badgeBrowserPart.setVisible(false);
			badgesPart.setVisible(false);
			dialog.run(true, false, new IRunnableWithProgress() {
				@Override
				public void run(IProgressMonitor monitor)
						throws InvocationTargetException, InterruptedException {
					monitor.beginTask("Rescanning Video Files In " + topic
							+ " For Indexing", 100);

					for (int i = 1; i < 11; i++) {
						if (i == 9) {
							// THE MAIN WORK
							File startingDirectory = new File(topic);
							List<File> files = null;
							List<File> fileWithSubs = null;
							try {
								files = MovieSearch
										.getFileListing(startingDirectory);
							} catch (IllegalArgumentException exception) {
								Dialog.error("Directory Invalid",
										"Check Your Current Movie Directory");
								return;
							}
							fileWithSubs = MovieSearch
									.FilesListHavingSubtitles(files);

							VideoFileRepository filezNewAfterScan = wrap(files,
									fileWithSubs);

							VideoFileRepository repoOld = null;
							try {
								repoOld = manipulation.convertToModel(topic, 1);
							} catch (NoSuchAlgorithmException e2) {
								e2.printStackTrace();
							} catch (JAXBException e2) {
								e2.printStackTrace();
							}

							List<VideoFile> toBeAddedOrRemovedToOldRepo = new ArrayList<VideoFile>();

							int oldSize = repoOld.getRepo().size();
							int newSize = filezNewAfterScan.getRepo().size();

							if (newSize >= oldSize) {
								for (VideoFile file2 : filezNewAfterScan
										.getRepo()) {
									boolean flag = false;
									for (VideoFile file1 : repoOld.getRepo()) {
										if (file2.getFileName().equals(
												file1.getFileName())) {
											flag = true;
										}
									}
									if (flag == false)
										toBeAddedOrRemovedToOldRepo.add(file2);
								}
								repoOld.getRepo().addAll(
										toBeAddedOrRemovedToOldRepo);
							} else {
								for (VideoFile file1 : repoOld.getRepo()) {
									boolean flag = false;
									for (VideoFile file2 : filezNewAfterScan
											.getRepo()) {
										if (file1.getFileName().equals(
												file2.getFileName())) {
											flag = true;
										}
									}
									if (flag == false)
										toBeAddedOrRemovedToOldRepo.add(file1);
								}

								repoOld.getRepo().removeAll(
										toBeAddedOrRemovedToOldRepo);
							}

							context.set(VideoFileRepository.class, repoOld);

							try {
								manipulation.convertToXml(repoOld, topic, 1);
							} catch (NoSuchAlgorithmException e2) {
								e2.printStackTrace();
							} catch (JAXBException e2) {
								e2.printStackTrace();
							}

							broker.send(EventConstants.EXISTING_MOVIE_FOLDER,
									topic);
							broker.send(EventConstants.REFRESH_BADGE_VIEW,
									topic);
							window.setLabel("My Movie Manager Beta - " + topic);
							// END

						}
						try {
							TimeUnit.SECONDS.sleep(2);
							monitor.subTask("Indexing " + (i * 10) + "%");

							monitor.worked(10);

						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					}
				}

				private VideoFileRepository wrap(List<File> files,
						List<File> filesWithSubs) {
					VideoFileRepository repository = new VideoFileRepository();
					List<VideoFile> filez = new ArrayList<VideoFile>();
					VideoFile file2 = null;

					for (File file : files) {
						file2 = new VideoFile();
						if (!FilenameUtils.getExtension(file.getName()).equals(
								"srt"))
							for (File subFoundFile : filesWithSubs) {
								if (FilenameUtils.removeExtension(
										file.getName()).equals(
										FilenameUtils
												.removeExtension(subFoundFile
														.getName()))) {
									file2.setSubtitlePresent(true);
									file2.setSubtitleLocation(FilenameUtils
											.getFullPathNoEndSeparator(subFoundFile
													.getAbsolutePath()));
								}
							}
						file2.setFileName(file.getName());
						file2.setFileSize(Long.toString(FileUtils.sizeOf(file)
								/ FileUtils.ONE_MB));
						file2.setFileNameWithoutExtension(FilenameUtils
								.getBaseName(file.getName()));
						file2.setLocation(file.getPath());
						if (!(FilenameUtils.getExtension(file2.getFileName())
								.equals("srt")))
							filez.add(file2);
						file2 = null;
					}
					repository.setRepo(filez);
					return repository;
				}

			});
		} else
			broker.send(EventConstants.NEW_MOVIE_FOLDER, topic);
	}

	public static String getLocation() {
		return location;
	}

	@Inject
	@Optional
	public void renameMovieFile(
			@UIEventTopic(EventConstants.RENAME_FILE) Object[] data)
			throws NoSuchAlgorithmException, JAXBException, IOException {
		File fileObject = null;
		VideoFile file = (VideoFile) data[0];
		String newName = (String) data[1];
		String location = (String) context.get("folder_location");
		if (file != null) {
			fileObject = new File(file.getLocation());
			if (fileObject != null) {
				String absolutePath = fileObject.getAbsolutePath();
				File newFile = new File(absolutePath.substring(0,
						absolutePath.lastIndexOf(File.separator))
						+ "/"
						+ newName
						+ "."
						+ FilenameUtils.getExtension(fileObject.getName()));

				if (Utility.forceRename(fileObject, newFile)) {

					VideoFileRepository fileRepository = manipulation
							.convertToModel(location, 1);
					VideoFileRepository newFileRepository = new VideoFileRepository();
					List<VideoFile> newListOfVideoFiles = new ArrayList<VideoFile>();
					VideoFile theRenamedVideoFile = null;
					for (VideoFile vfile : fileRepository.getRepo()) {
						List<Tag> newListOfTags = vfile.getTags();
						if (vfile.getFileName().equals(file.getFileName())) {
							theRenamedVideoFile = new VideoFile();
							theRenamedVideoFile.setFileName(newFile.getName());
							theRenamedVideoFile
									.setFileSize(vfile.getFileSize());
							theRenamedVideoFile.setLocation(newFile.getPath());
							theRenamedVideoFile.setSubtitleLocation(vfile
									.getSubtitleLocation());
							theRenamedVideoFile.setTags(vfile.getTags());
							theRenamedVideoFile
									.setFileNameWithoutExtension(FilenameUtils
											.getBaseName(newFile.getName()));

							newListOfVideoFiles.add(theRenamedVideoFile);
							continue;
						}
						newListOfVideoFiles.add(vfile);
					}
					newFileRepository.setRepo(newListOfVideoFiles);

					manipulation.convertToXml(newFileRepository, location, 1);
					context.set(VideoFileRepository.class, newFileRepository);
					// Now send updates to viewers to refresh
					broker.send(EventConstants.REFRESH_BADGE_VIEW, file);
					broker.send(EventConstants.EXISTING_MOVIE_FOLDER, location);
					broker.send(EventConstants.REFRESH_BADGE_VIEW_ON_RENAME,
							new Object[] { file, theRenamedVideoFile });

				}
			}
		}
	}

	@Inject
	@Optional
	public void deleteMovie(
			@UIEventTopic(EventConstants.DELETE_MOVIE) Object[] data)
			throws NoSuchAlgorithmException, JAXBException {
		VideoFile file = (VideoFile) data[0];
		boolean flag = (Boolean) data[1];
		File fileObject = new File(file.getLocation());
		if (fileObject != null && flag == true) {
			if (FileUtils.deleteQuietly(fileObject)) {
				VideoFileRepository fileRepository = manipulation
						.convertToModel(location, 1);
				VideoFileRepository newFileRepository = new VideoFileRepository();
				List<VideoFile> newListOfVideoFiles = new ArrayList<VideoFile>();
				for (VideoFile vfile : fileRepository.getRepo()) {
					if (!vfile.getFileName().equals(file.getFileName())) {
						newListOfVideoFiles.add(vfile);
					}
				}
				newFileRepository.setRepo(newListOfVideoFiles);
				manipulation.convertToXml(newFileRepository, location, 1);
				context.set(VideoFileRepository.class, newFileRepository);

				broker.send(EventConstants.REFRESH_BADGE_VIEW, file);
				broker.send(EventConstants.EXISTING_MOVIE_FOLDER, location);
				broker.send(EventConstants.REFRESH_BADGE_VIEW_ON_RENAME,
						new Object[] { file, null });
				broker.send(EventConstants.MOVIE_DETAILS_AT_MOUSE_CLICK, file);
				detailsPart.setVisible(false);
				imdbPart.setVisible(false);
				subtitlePart.setVisible(false);
				tagPart.setVisible(false);
			}
		}

	}

	public void checkPreferencesStore() {
		Preferences preferences = InstanceScope.INSTANCE
				.getNode("in.mymoviemanager.rcp");

		favourite_folder = preferences.get("favFolder", "");
	}

	// @Inject
	// @Optional
	// public void getUpdate(
	// @UIEventTopic(EventConstants.UPDATE_PROGRESS_BAR) String data,
	// MApplication application) {
	// Job job = new Job("Update") {
	// @Override
	// protected IStatus run(IProgressMonitor monitor) {
	// monitor.beginTask("Doing something time consuming here", 100);
	// for (int i = 0; i < 5; i++) {
	// try {
	// TimeUnit.SECONDS.sleep(1);
	//
	// monitor.subTask("I'm doing something here " + i);
	//
	// monitor.worked(20);
	// } catch (InterruptedException e1) {
	// e1.printStackTrace();
	// return Status.CANCEL_STATUS;
	// }
	// }
	// System.out.println("Called save");
	// return Status.OK_STATUS;
	// }
	// };
	// IJobManager manager = job.getJobManager();
	//
	// MToolControl element = (MToolControl) modelService.find(
	// "in.mymoviemanager.rcp.toolcontrol.progress", application);
	//
	// Object widget = element.getObject();
	// final IProgressMonitor p = (IProgressMonitor) widget;
	// ProgressProvider provider = new ProgressProvider() {
	// @Override
	// public IProgressMonitor createMonitor(Job job) {
	// return p;
	// }
	// };
	//
	// manager.setProgressProvider(provider);
	// job.schedule();
	// }

	@Inject
	@Optional
	public void changeTheme(
			@UIEventTopic(EventConstants.CHANGE_THEME) String themeID,
			IThemeEngine engine) {
		engine.setTheme(themeID, true);
	}

}
