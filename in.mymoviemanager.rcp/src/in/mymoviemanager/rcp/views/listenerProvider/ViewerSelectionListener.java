package in.mymoviemanager.rcp.views.listenerProvider;

import in.mymoviemanager.events.EventConstants;
import in.mymoviemanager.rcp.model.Tag;
import in.mymoviemanager.rcp.model.VideoFile;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.e4.ui.workbench.swt.modeling.EMenuService;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;

/**
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
public class ViewerSelectionListener implements SelectionListener {

	private IEventBroker broker;
	private MPart detailsPart;
	private MPart imdbPart;
	private MPart subtitlePart;
	private MPart tagPart;
	private MPart actionPart;
	private MPart specialBadgesPart;
	private EPartService service;
	private TreeViewer viewer;
	private TableViewer specificBadgestableViewer, movieMrowserTableViewer;
	private ESelectionService selectionService;
	private IEclipseContext context;
	private EMenuService menuService;
	private UISynchronize synchronize;
	Object item;

	public ViewerSelectionListener(IEclipseContext context,
			ESelectionService selectionService, EMenuService menuService,
			IEventBroker broker, MPart detailsPart, MPart imdbPart,
			MPart subtitlePart, MPart tagPart, MPart actionPart,
			MPart specialBadgesPart, EPartService service, TreeViewer viewer,
			TableViewer specificBadgestableViewer,
			TableViewer movieMrowserTableViewer, UISynchronize synchronize) {
		super();
		this.broker = broker;
		this.detailsPart = detailsPart;
		this.imdbPart = imdbPart;
		this.subtitlePart = subtitlePart;
		this.tagPart = tagPart;
		this.actionPart = actionPart;
		this.service = service;
		this.viewer = viewer;
		this.selectionService = selectionService;
		this.specificBadgestableViewer = specificBadgestableViewer;
		this.movieMrowserTableViewer = movieMrowserTableViewer;
		this.specialBadgesPart = specialBadgesPart;
		this.context = context;
		this.menuService = menuService;
		this.synchronize = synchronize;
	}

	@Override
	public void widgetSelected(SelectionEvent e) {

		if (e.item instanceof TreeItem) {
			item = (TreeItem) e.item;
		} else if (e.item instanceof TableItem) {
			item = (TableItem) e.item;
		}

		if (broker != null && item != null) {
			try {
				if (((Widget) item).getData() instanceof VideoFile) {
					context.set("active_selection", ((Widget) item).getData());

					selectionService.setSelection(((Widget) item).getData());

					// AddBadgeHandler handler = new AddBadgeHandler();

					// ContextInjectionFactory.inject(handler, context);
					// handler.getContext(context);

					detailsPart.setVisible(true);
					imdbPart.setVisible(true);
					subtitlePart.setVisible(true);
					tagPart.setVisible(true);
					// actionPart.setVisible(true);
					// specialBadgesPart.setVisible(true);

					service.activate(detailsPart);
					service.activate(tagPart);
					// service.activate(imdbPart);
					service.activate(tagPart);
					service.activate(subtitlePart);
					service.activate(detailsPart);

					broker.post(EventConstants.TAG_DETAILS_AT_MOUSE_CLICK,
							((Widget) item).getData());

					broker.post(EventConstants.MOVIE_DETAILS_AT_MOUSE_CLICK,
							((Widget) item).getData());

					broker.post(
							EventConstants.SUBTITLES_DETAILS_AT_MOUSE_CLICK,
							((Widget) item).getData());

					// synchronize.asyncExec(new Runnable() {
					//
					// @Override
					// public void run() {
					// if (context.get("internet_connection_alive") != null) {
					// if (Boolean.parseBoolean((String) context
					// .get("internet_connection_alive"))) {
					// broker.post(EventConstants.IMDB_DETAILS,
					// ((Widget) item).getData());
					// }
					// } else {
					// }
					// broker.send(
					// EventConstants.NO_INTERNET_CONNECTION_FOR_IMDB,
					// ((Widget) item).getData());
					//
					// }
					// });

				}
			} catch (SWTException exception) {

			}

			try {
				if (((Widget) item).getData() instanceof Tag) {
					detailsPart.setVisible(false);
					imdbPart.setVisible(false);
					subtitlePart.setVisible(false);
					tagPart.setVisible(false);
					actionPart.setVisible(false);
				}
			} catch (SWTException exception) {

			}
		}

	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {

	}

}
