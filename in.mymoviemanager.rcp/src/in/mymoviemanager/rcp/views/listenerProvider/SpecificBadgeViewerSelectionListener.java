package in.mymoviemanager.rcp.views.listenerProvider;

import in.mymoviemanager.events.EventConstants;
import in.mymoviemanager.rcp.handlers.AddBadgeHandler;
import in.mymoviemanager.rcp.model.VideoFile;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Widget;
/**
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 *
 */
public class SpecificBadgeViewerSelectionListener implements SelectionListener {

	private IEventBroker broker;
	private IEclipseContext context;
	private EPartService service;

	MPart detailsPart;
	MPart imdbPart;
	MPart subtitlePart;
	MPart tagPart;
	MPart actionPart;
	MPart badgeBrowserPart;
	MPart movieBrowserPart;
	MPart badgesPart;

	public SpecificBadgeViewerSelectionListener(EPartService service,
			IEventBroker broker, IEclipseContext context) {
		super();
		this.broker = broker;
		this.context = context;
		this.service = service;
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		Object item = null;
		if (e.item instanceof TableItem) {
			item = (TableItem) e.item;
		}
		if (broker != null && item != null) {
			if (((Widget) item).getData() instanceof VideoFile) {
				context.set("active_selection", ((Widget) item).getData());

				AddBadgeHandler handler = new AddBadgeHandler();

//				ContextInjectionFactory.inject(handler, context);
//				handler.getContext(context);

				broker.post(EventConstants.TAG_DETAILS_AT_MOUSE_CLICK,
						((Widget) item).getData());

				broker.post(EventConstants.MOVIE_DETAILS_AT_MOUSE_CLICK,
						((Widget) item).getData());
				showParts();
			}
		}
	}

	private void showParts() {
		detailsPart = service.findPart("com.mymoviemanager.rcp.part.1");
		imdbPart = service.findPart("com.mymoviemanager.rcp.part.2");
		subtitlePart = service.findPart("com.mymoviemanager.rcp.part.3");
		tagPart = service.findPart("com.mymoviemanager.rcp.part.4");
		actionPart = service.findPart("com.mymoviemanager.rcp.part.5");
		badgesPart = service.findPart("com.mymoviemanager.rcp.part.8");
		detailsPart.setVisible(true);
		imdbPart.setVisible(true);
		subtitlePart.setVisible(true);
		tagPart.setVisible(true);
		actionPart.setVisible(true);
		badgesPart.setVisible(true);
		detailsPart.setOnTop(true);
		badgesPart.setOnTop(true);
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {

	}

}
