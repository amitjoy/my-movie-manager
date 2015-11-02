package in.mymoviemanager.rcp.views.listenerProvider;

import in.mymoviemanager.rcp.model.Tag;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TableItem;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
/**
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 *
 */
public class ImageColumnBadgePartLabelProvider extends ColumnLabelProvider {

	private static final Image DEFAULT = getImage("file.png");

	boolean even = true;

	private TableViewer tableViewer;

	public ImageColumnBadgePartLabelProvider(TableViewer tableViewer) {
		super();
		this.tableViewer = tableViewer;
	}

	@Override
	public Image getImage(Object element) {
		if (element instanceof Tag) {
			return getImage(((Tag) element).getName() + ".png");
		}
		return DEFAULT;
	}

	@Override
	public String getText(Object element) {
		return "";
	}

	private static Image getImage(String file) {
		Bundle bundle = FrameworkUtil.getBundle(BadgeLabelProvider.class);
		URL url = FileLocator.find(bundle, new Path("icons/" + file), null);
		ImageDescriptor image = ImageDescriptor.createFromURL(url);
		return image.createImage();

	}

	@Override
	public Color getBackground(Object element) {
		Display display = Display.getCurrent();
		if (even) {
			return null;
		} else {
			return new Color(display, 245, 245, 245);
		}
	}

	@Override
	public void update(ViewerCell cell) {
		OptimizedIndexSearcher searcher = new OptimizedIndexSearcher();
		even = searcher.isEven((TableItem) cell.getItem());
		super.update(cell);
	}

}
