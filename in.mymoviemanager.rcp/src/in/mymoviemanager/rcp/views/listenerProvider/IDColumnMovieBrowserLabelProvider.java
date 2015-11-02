package in.mymoviemanager.rcp.views.listenerProvider;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TableItem;

/**
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
public class IDColumnMovieBrowserLabelProvider extends ColumnLabelProvider {

	private TableViewer tableViewer;
	private int index;

	public IDColumnMovieBrowserLabelProvider(TableViewer tableViewer) {
		super();
		this.tableViewer = tableViewer;
	}

	@Override
	public String getText(Object element) {
		if (tableViewer != null) {
			return (" " + (++index));
		}
		return null;
	}

	@Override
	public void update(ViewerCell cell) {
		cell.setText(String.valueOf(tableViewer.getTable().indexOf(
				(TableItem) cell.getItem())));
		index = tableViewer.getTable().indexOf((TableItem) cell.getItem());
		super.update(cell);
	}

	@Override
	public Color getBackground(Object element) {
		if (index % 2 == 0)
			return new Color(Display.getCurrent(), 255, 229, 204);
		else
			return new Color(Display.getCurrent(), 255, 255, 255);
	}
}
