package in.mymoviemanager.rcp.views.listenerProvider;

import in.mymoviemanager.rcp.model.Tag;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.nebula.jface.galleryviewer.GalleryTreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TableItem;
/**
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 *
 */
public class BadgeNameColumnBadgePartLabelProvider extends ColumnLabelProvider {

	private TableViewer tableViewer;
	private GalleryTreeViewer galleryTreeViewer;
	boolean even = true;

	public BadgeNameColumnBadgePartLabelProvider(TableViewer tableViewer, GalleryTreeViewer galleryTreeViewer) {
		super();
		this.tableViewer = tableViewer;
		this.galleryTreeViewer = galleryTreeViewer;
	}

	@Override
	public String getText(Object element) {
		if (element instanceof Tag) {
			return ((Tag) element).getName();
		}
		return super.getText(element);
	}

//	@Override
//	public Color getBackground(Object element) {
//		Display display = Display.getCurrent();
//		if (even) {
//			return null;
//		} else {
//			return new Color(display, 245, 245, 245);
//		}
//	}
//
//	@Override
//	public void update(ViewerCell cell) {
//		OptimizedIndexSearcher searcher = new OptimizedIndexSearcher();
//		even = searcher.isEven((TableItem) cell.getItem());
//		super.update(cell);
//	}

}
