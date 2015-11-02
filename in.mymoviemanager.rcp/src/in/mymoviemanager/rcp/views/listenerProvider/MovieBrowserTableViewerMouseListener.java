package in.mymoviemanager.rcp.views.listenerProvider;

import in.mymoviemanager.events.EventConstants;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.TableItem;
/**
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 *
 */
public class MovieBrowserTableViewerMouseListener implements MouseListener {

	private IEventBroker broker;
	private MPart detailsPart;
	private MPart imdbPart;
	private MPart subtitlePart;
	private MPart tagPart;
	private MPart actionPart;
	private EPartService service;
	private TableViewer viewer;

	public MovieBrowserTableViewerMouseListener(IEventBroker broker,
			MPart detailsPart, MPart imdbPart, MPart subtitlePart,
			MPart tagPart, MPart actionPart, EPartService service,
			TableViewer viewer) {
		super();
		this.broker = broker;
		this.detailsPart = detailsPart;
		this.imdbPart = imdbPart;
		this.subtitlePart = subtitlePart;
		this.tagPart = tagPart;
		this.actionPart = actionPart;
		this.service = service;
		this.viewer = viewer;
	}

	@Override
	public void mouseDoubleClick(MouseEvent e) {
	}

	@Override
	public void mouseDown(MouseEvent e) {
		TableItem itemClick = viewer.getTable().getItem(new Point(e.x, e.y));
		if (broker != null && itemClick != null) {

			detailsPart.setVisible(true);
			imdbPart.setVisible(true);
			subtitlePart.setVisible(true);
			tagPart.setVisible(true);
			actionPart.setVisible(true);

			service.activate(tagPart);
			service.activate(detailsPart);
			
			broker.post(EventConstants.TAG_DETAILS_AT_MOUSE_CLICK,
					itemClick.getData());

			broker.post(EventConstants.MOVIE_DETAILS_AT_MOUSE_CLICK,
					itemClick.getData());

		}
	}

	@Override
	public void mouseUp(MouseEvent e) {
	}
}
