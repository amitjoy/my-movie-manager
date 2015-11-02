package in.mymoviemanager.rcp.views;

import in.mymovemanager.imdb.service.annotation.Imdb;
import in.mymoviemanager.events.EventConstants;
import in.mymoviemanager.imdb.model.IMDBDocumentList;
import in.mymoviemanager.imdb.services.IMovieImdb;
import in.mymoviemanager.rcp.model.VideoFile;
import in.mymoviemanager.utility.ImageUtil;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.commons.io.FilenameUtils;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;
import org.mihalis.opal.starRating.StarRating;
import org.mihalis.opal.starRating.StarRating.SIZE;

/**
 * IMDB View
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
public class ImdbViewPart {

	@Inject
	@Imdb
	IMovieImdb imdb;

	@Inject
	UISynchronize synchronize;

	@Inject
	IEventBroker broker;

	@Inject
	IEclipseContext context;

	@Inject
	EPartService service;

	Composite topComposite;
	IMDBDocumentList imdbInfo;
	Composite composite;
	Label lblNewLabel;
	Composite composite_1;
	Composite composite_2;
	GridData gd_composite_2;
	Label lblNewLabel_1;
	Composite composite_3;
	GridData gd_composite_3;
	Composite composite_4;
	FormData fd_composite_4;
	Label lblRating;
	Label label;
	Composite composite_5;
	FormData fd_composite_5;
	Composite composite_6;
	GridData gd_composite_6;
	Label lblSummonedToJapan;
	Composite composite_7;
	GridData gd_composite_7;
	Label lblDirectors;
	Label lblJamesMangold;
	Label lblWriters;
	Label lblMarkBombackScott;
	Label lblStars;
	Label lblHughJackmanTao;
	GridData gd_lblHughJackmanTao;
	StarRating sr;
	private Label lblWriter;

	MPart detailsPart;
	MPart imdbPart;
	MPart subtitlePart;
	MPart tagPart;
	MPart actionPart;
	MPart badgeBrowserPart;
	MPart specialBadgesPart;
	MPart movieBrowserPart;

	VideoFile file;
	Composite imdbWholeComposite;

	public ImdbViewPart() {
	}

	/**
	 * Create contents of the view part.
	 */
	@PostConstruct
	public void createControls(Composite parent) {
		imdbWholeComposite = parent;
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
		specialBadgesPart = service
				.findPart("in.mymoviemanager.rcp.part.specialBadgesInfo");

		topComposite = parent;

		topComposite.setLayout(new RowLayout(SWT.HORIZONTAL));

		composite = new Composite(topComposite, SWT.NONE);
		composite.setLayoutData(new RowData(238, 351));

		lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setBounds(10, 10, 228, 329);

		composite_1 = new Composite(topComposite, SWT.NONE);
		composite_1.setLayout(new GridLayout(1, false));
		composite_1.setLayoutData(new RowData(444, 384));

		composite_2 = new Composite(composite_1, SWT.NONE);
		gd_composite_2 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_composite_2.heightHint = 33;
		gd_composite_2.widthHint = 289;
		composite_2.setLayoutData(gd_composite_2);
		composite_2.setBounds(0, 0, 64, 64);

		lblNewLabel_1 = new Label(composite_2, SWT.NONE);
		lblNewLabel_1.setFont(SWTResourceManager.getFont("Verdana", 17,
				SWT.BOLD));
		lblNewLabel_1.setBounds(10, 10, 269, 33);
		composite_3 = new Composite(composite_1, SWT.NONE);
		composite_3.setLayout(new FormLayout());
		gd_composite_3 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_composite_3.widthHint = 437;
		gd_composite_3.heightHint = 335;
		composite_3.setLayoutData(gd_composite_3);
		composite_3.setBounds(0, 0, 64, 64);

		composite_4 = new Composite(composite_3, SWT.NONE);
		composite_4.setLayout(new GridLayout(3, false));
		fd_composite_4 = new FormData();
		fd_composite_4.top = new FormAttachment(0, 10);
		fd_composite_4.left = new FormAttachment(0, 10);
		fd_composite_4.right = new FormAttachment(0, 437);
		composite_4.setLayoutData(fd_composite_4);

		lblRating = new Label(composite_4, SWT.NONE);

		Font font = JFaceResources.getTextFont();

		lblRating.setFont(SWTResourceManager.getFont("Verdana", 17, SWT.BOLD
				| SWT.ITALIC));
		lblRating.setText("Rating:");
		new Label(composite_4, SWT.NONE);
		sr = new StarRating(composite_4, SWT.NONE);
		GridData gd_sr = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_sr.widthHint = 333;
		sr.setLayoutData(gd_sr);
		sr.setSizeOfStars(SIZE.BIG);

		label = new Label(composite_4, SWT.NONE);
		label.setFont(SWTResourceManager.getFont("Verdana", 17, SWT.BOLD));
		composite_5 = new Composite(composite_3, SWT.NONE);
		fd_composite_4.bottom = new FormAttachment(composite_5, -6);
		new Label(composite_4, SWT.NONE);
		new Label(composite_4, SWT.NONE);
		fd_composite_5 = new FormData();
		fd_composite_5.bottom = new FormAttachment(100, -10);
		fd_composite_5.top = new FormAttachment(0, 85);
		fd_composite_5.left = new FormAttachment(0, 10);
		fd_composite_5.right = new FormAttachment(0, 322);
		composite_5.setLayoutData(fd_composite_5);
		composite_5.setLayout(new GridLayout(1, false));

		composite_6 = new Composite(composite_5, SWT.NONE);
		gd_composite_6 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_composite_6.heightHint = 81;
		gd_composite_6.widthHint = 304;
		composite_6.setLayoutData(gd_composite_6);
		composite_6.setBounds(0, 0, 64, 64);

		lblSummonedToJapan = new Label(composite_6, SWT.WRAP);
		lblSummonedToJapan.setBounds(0, 0, 300, 70);
		new Label(composite_5, SWT.NONE);
		composite_7 = new Composite(composite_5, SWT.NONE);
		composite_7.setLayout(new GridLayout(2, false));
		gd_composite_7 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_composite_7.widthHint = 303;
		gd_composite_7.heightHint = 120;
		composite_7.setLayoutData(gd_composite_7);

		Label lblGenre = new Label(composite_7, SWT.NONE);
		lblGenre.setFont(SWTResourceManager.getFont("Lucida Grande", 13,
				SWT.BOLD));
		lblGenre.setText("Genre:");
		lblJamesMangold = new Label(composite_7, SWT.NONE);

		lblDirectors = new Label(composite_7, SWT.NONE);
		lblDirectors.setFont(SWTResourceManager.getFont("Lucida Grande", 13,
				SWT.BOLD));
		lblDirectors.setText("Director:");

		lblMarkBombackScott = new Label(composite_7, SWT.NONE);
		lblWriters = new Label(composite_7, SWT.NONE);
		lblWriters.setFont(SWTResourceManager.getFont("Lucida Grande", 13,
				SWT.BOLD));
		lblWriters.setText("Writer:");

		lblWriter = new Label(composite_7, SWT.NONE);
		lblWriter.setText("");
		lblStars = new Label(composite_7, SWT.NONE);
		lblStars.setFont(SWTResourceManager.getFont("Lucida Grande", 13,
				SWT.BOLD));
		lblStars.setText("Stars:");

		lblHughJackmanTao = new Label(composite_7, SWT.WRAP);
		gd_lblHughJackmanTao = new GridData(SWT.LEFT, SWT.CENTER, false, false,
				1, 1);
		gd_lblHughJackmanTao.widthHint = 221;
		gd_lblHughJackmanTao.heightHint = 43;
		lblHughJackmanTao.setLayoutData(gd_lblHughJackmanTao);
	}

	@PreDestroy
	public void dispose() {
	}

	@Inject
	@Optional
	public void getVideoFileNameSelection(
			@Named(IServiceConstants.ACTIVE_SELECTION) Object data) {
		this.file = (VideoFile) data;
	}

	@Focus
	public void setFocus() {
		broker.send(EventConstants.IMDB_DETAILS, file);
	}

	@Inject
	@Optional
	public void imdbDetailsView(
			@UIEventTopic(EventConstants.IMDB_DETAILS) final Object data,
			MApplication application, EModelService modelService,
			@Named(IServiceConstants.ACTIVE_SHELL) Shell shell) {
		if (data instanceof VideoFile) {
			BusyIndicator.showWhile(shell.getDisplay(), new Runnable() {

				@Override
				public void run() {
					try {
						imdbInfo = imdb.getMovieInfo(FilenameUtils
								.getBaseName(((VideoFile) data).getFileName()));
					} catch (Exception e) {

					}
					if (imdbInfo != null) {

						Image image = null;
						if (imdbInfo.getItem().getPoster() != null) {
							try {
								URL url = new URL(imdbInfo.getItem()
										.getPoster().getCover().get(0));
								image = ImageIO.read(url);
							} catch (IOException e) {
							}
							if (image != null) {
								lblNewLabel
										.setImage(new org.eclipse.swt.graphics.Image(
												topComposite.getDisplay(),
												ImageUtil
														.convertAWTImageToSWT(image)));
							}
						} else {
							lblNewLabel.setImage(ResourceManager
									.getPluginImage("in.mymoviemanager.rcp",
											"icons/imdb_no_poster.png"));
						}
						if (imdbInfo.getItem().getTitle() != null)
							lblNewLabel_1
									.setText(imdbInfo.getItem().getTitle());

						if (imdbInfo.getItem().getRating() != null) {
							label.setText(imdbInfo.getItem().getRating());
							sr.setMaxNumberOfStars((int) Math.round((Double
									.parseDouble(imdbInfo.getItem().getRating()))));
							sr.setToolTipText(imdbInfo.getItem().getRating());
							sr.setCurrentNumberOfStars((int) Math.round((Double
									.parseDouble(imdbInfo.getItem().getRating()))));
							sr.setEnabled(true);
						}
						if (imdbInfo.getItem().getPlot_simple() != null)
							lblSummonedToJapan.setText(imdbInfo.getItem()
									.getPlot_simple());
						if (imdbInfo.getItem().getDirectors() != null)
							if (imdbInfo.getItem().getDirectors().getItem()
									.get(0) != null)

								lblMarkBombackScott.setText(imdbInfo.getItem()
										.getDirectors().getItem().get(0));

						if (imdbInfo.getItem().getWriters() != null)
							if (imdbInfo.getItem().getWriters().getItem()
									.get(0) != null)

								lblWriter.setText(imdbInfo.getItem()
										.getWriters().getItem().get(0));
						if (imdbInfo.getItem().getActors() != null)
							lblHughJackmanTao.setText(imdbInfo.getItem()
									.getActors().getItem().get(0)
									+ ", "
									+ imdbInfo.getItem().getActors().getItem()
											.get(1)
									+ ", "
									+ imdbInfo.getItem().getActors().getItem()
											.get(2));
						if (imdbInfo.getItem().getGenres() != null) {
							lblJamesMangold.setText(imdbInfo.getItem()
									.getGenres().getItem().get(0));
							broker.send(EventConstants.NEW_TAG_ADD_FROM_IMDB,
									new Object[] {
											imdbInfo.getItem().getGenres()
													.getItem().get(0),
											(VideoFile) data });
						}

					} else {
						broker.send(
								EventConstants.NO_INTERNET_CONNECTION_FOR_IMDB,
								new Object[] {});
					}
					imdbWholeComposite.layout();
				}
			});
			// JOB Start
			// Job job = new Job("Update IMDB View") {
			// @Override
			// protected IStatus run(IProgressMonitor monitor) {
			//
			// monitor.beginTask("Connection to Internet Status", 100);
			//
			// try {
			// TimeUnit.SECONDS.sleep(1);
			// monitor.worked(100);
			// monitor.done();
			// } catch (InterruptedException e1) {
			// e1.printStackTrace();
			// return Status.CANCEL_STATUS;
			// }
			//
			// synchronize.asyncExec(new Runnable() {
			//
			// @Override
			// public void run() {
			// try {
			// imdbInfo = imdb.getMovieInfo(FilenameUtils
			// .getBaseName(((VideoFile) data)
			// .getFileName()));
			// } catch (Exception swtException) {
			//
			// }
			// if (imdbInfo != null) {
			//
			// Image image = null;
			// if (imdbInfo.getItem().getPoster() != null) {
			// try {
			// URL url = new URL(imdbInfo.getItem()
			// .getPoster().getCover().get(0));
			// image = ImageIO.read(url);
			// } catch (IOException e) {
			// }
			// if (image != null)
			// lblNewLabel
			// .setImage(new org.eclipse.swt.graphics.Image(
			// topComposite
			// .getDisplay(),
			// ImageUtil
			// .convertAWTImageToSWT(image)));
			// } else
			// lblNewLabel.setImage(ResourceManager
			// .getPluginImage(
			// "com.mymoviemanager.rcp",
			// "icons/imdb_no_poster.png"));
			// if (imdbInfo.getItem().getTitle() != null)
			// lblNewLabel_1.setText(imdbInfo.getItem()
			// .getTitle());
			//
			// if (imdbInfo.getItem().getRating() != null) {
			// label.setText(imdbInfo.getItem()
			// .getRating());
			// sr.setMaxNumberOfStars((int) Math
			// .round((Double.parseDouble(imdbInfo
			// .getItem().getRating()))));
			// sr.setToolTipText(imdbInfo.getItem()
			// .getRating());
			// sr.setCurrentNumberOfStars((int) Math
			// .round((Double.parseDouble(imdbInfo
			// .getItem().getRating()))));
			// sr.setEnabled(true);
			// }
			// if (imdbInfo.getItem().getPlot_simple() != null)
			// lblSummonedToJapan.setText(imdbInfo
			// .getItem().getPlot_simple());
			// if (imdbInfo.getItem().getDirectors() != null)
			// if (imdbInfo.getItem().getDirectors()
			// .getItem().get(0) != null)
			//
			// lblMarkBombackScott.setText(imdbInfo
			// .getItem().getDirectors()
			// .getItem().get(0));
			//
			// if (imdbInfo.getItem().getWriters() != null)
			// if (imdbInfo.getItem().getWriters()
			// .getItem().get(0) != null)
			//
			// lblWriter.setText(imdbInfo.getItem()
			// .getWriters().getItem().get(0));
			// if (imdbInfo.getItem().getActors() != null)
			// lblHughJackmanTao.setText(imdbInfo
			// .getItem().getActors().getItem()
			// .get(0)
			// + ", "
			// + imdbInfo.getItem().getActors()
			// .getItem().get(1)
			// + ", "
			// + imdbInfo.getItem().getActors()
			// .getItem().get(2));
			// if (imdbInfo.getItem().getGenres() != null) {
			// lblJamesMangold.setText(imdbInfo.getItem()
			// .getGenres().getItem().get(0));
			// broker.send(
			// EventConstants.NEW_TAG_ADD_FROM_IMDB,
			// new Object[] {
			// imdbInfo.getItem()
			// .getGenres()
			// .getItem().get(0),
			// (VideoFile) data });
			// }
			//
			// } else {
			// broker.send(
			// EventConstants.NO_INTERNET_CONNECTION_FOR_IMDB,
			// data);
			// }
			// }
			//
			// });
			// return Status.OK_STATUS;
			// }
			//
			// };
			//
			// IJobManager manager = job.getJobManager();
			//
			// MToolControl element = (MToolControl) modelService.find(
			// "in.mymoviemanager.rcp.trim.toolcontrol.bottomProgressBar",
			// application);
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
		}
	}

	@Inject
	@Optional
	public void noInternetConnectionOrNoDataFromIMDB(
			@UIEventTopic(EventConstants.NO_INTERNET_CONNECTION_FOR_IMDB) Object[] arr) {
		lblNewLabel_1.setText("NA");

		label.setText("NA");

		lblSummonedToJapan.setText("NA");

		lblJamesMangold.setText("NA");

		lblMarkBombackScott.setText("NA");

		lblHughJackmanTao.setText("NA");

		lblWriter.setText("NA");

		lblNewLabel.setImage(ResourceManager.getPluginImage(
				"in.mymoviemanager.rcp", "icons/imdb_poster.png"));

		sr.setMaxNumberOfStars(0);
	}
}
