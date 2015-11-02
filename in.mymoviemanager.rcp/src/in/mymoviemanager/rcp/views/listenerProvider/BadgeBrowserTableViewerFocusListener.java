package in.mymoviemanager.rcp.views.listenerProvider;

import in.mymoviemanager.events.EventConstants;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
/**
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 *
 */
public class BadgeBrowserTableViewerFocusListener implements FocusListener {

	
	private IEventBroker broker;
	private MPart detailsPart;
	private MPart imdbPart;
	private MPart subtitlePart;
	private MPart tagPart;
	private MPart actionPart;
	private EPartService service;
	private TreeViewer viewer;

	public BadgeBrowserTableViewerFocusListener(IEventBroker broker,
			MPart detailsPart, MPart imdbPart, MPart subtitlePart,
			MPart tagPart, MPart actionPart, EPartService service,
			TreeViewer viewer) {
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
	public void focusGained(FocusEvent e) {
		
		Tree itemClickTree = (Tree) e.widget;
//		itemClickTree.
		Point loc = itemClickTree.toDisplay(itemClickTree.getLocation());
		TreeItem itemClick = viewer.getTree().getItem(loc);
		
		itemClick = (TreeItem) e.getSource();
		
		

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
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub

	}

}
