package in.mymoviemanager.rcp.views.listenerProvider;

import in.mymoviemanager.rcp.model.Tag;
import in.mymoviemanager.rcp.model.VideoFile;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.e4.ui.workbench.swt.modeling.EMenuService;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;

/**
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
public class ViewerMouseListener implements MouseListener {

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
	Object item;

	public ViewerMouseListener(IEclipseContext context,
			ESelectionService selectionService, EMenuService menuService,
			IEventBroker broker, MPart detailsPart, MPart imdbPart,
			MPart subtitlePart, MPart tagPart, MPart actionPart,
			MPart specialBadgesPart, EPartService service, TreeViewer viewer,
			TableViewer movieMrowserTableViewer,
			TableViewer specificBadgestableViewer) {
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
	}

	@Override
	public void mouseDoubleClick(MouseEvent e) {

	}

	@SuppressWarnings("restriction")
	@Override
	public void mouseDown(MouseEvent e) {

		if (specificBadgestableViewer != null) {
			item = (TableItem) specificBadgestableViewer.getTable().getItem(
					new Point(e.x, e.y));
			if (item != null)
				menuService.registerContextMenu(
						specificBadgestableViewer.getTable(),
						"in.mymoviemanager.rcp.popupmenu.deleteRenameActions3");
		}
		if (movieMrowserTableViewer != null) {
			item = (TableItem) movieMrowserTableViewer.getTable().getItem(
					new Point(e.x, e.y));
			if (item != null)
				menuService.registerContextMenu(
						movieMrowserTableViewer.getTable(),
						"in.mymoviemanager.rcp.popupmenu.deleteRenameActions1");
		}
		if (viewer != null) {
			item = (TreeItem) viewer.getTree().getItem(new Point(e.x, e.y));

			if (item != null && ((Widget) item).getData() instanceof VideoFile)
				menuService.registerContextMenu(viewer.getTree(),
						"in.mymoviemanager.rcp.popupmenu.deleteRenameActions2");
			if (item != null && ((Widget) item).getData() instanceof Tag)
				menuService.registerContextMenu(viewer.getTree(),
						"in.mymoviemanager.rcp.popupmenu.dummyPopUp");
		}

	}

	@Override
	public void mouseUp(MouseEvent e) {

	}

}
