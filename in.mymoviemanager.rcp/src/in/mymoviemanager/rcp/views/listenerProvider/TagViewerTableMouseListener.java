package in.mymoviemanager.rcp.views.listenerProvider;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.e4.ui.workbench.swt.modeling.EMenuService;
import org.eclipse.jface.viewers.IStructuredSelection;
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
public class TagViewerTableMouseListener implements MouseListener {

	TableViewer tableViewer;
	EMenuService service;
	ESelectionService selectionService;

	IEclipseContext context;

	public TagViewerTableMouseListener(TableViewer viewer,
			EMenuService service, ESelectionService selectionService, IEclipseContext context) {
		this.tableViewer = viewer;
		this.service = service;
		this.context = context;
		this.selectionService = selectionService;
	}

	@Override
	public void mouseUp(MouseEvent e) {

	}

	@SuppressWarnings("restriction")
	@Override
	public void mouseDown(MouseEvent e) {

		 if (e.button == 1)
		 return;

		TableItem item = tableViewer.getTable().getItem(new Point(e.x, e.y));

		if (item != null) {
			service.registerContextMenu(tableViewer.getTable(),
					"com.mymoviemanager.rcp.popupmenu.tag");
			IStructuredSelection selection = (IStructuredSelection) 
			        tableViewer.getSelection();
//			    selectionService.setSelection(selection.getFirstElement());
		}
	}

	@Override
	public void mouseDoubleClick(MouseEvent e) {
	}

}
