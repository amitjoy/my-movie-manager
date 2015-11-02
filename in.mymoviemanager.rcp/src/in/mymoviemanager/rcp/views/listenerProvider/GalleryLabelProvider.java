package in.mymoviemanager.rcp.views.listenerProvider;

import in.mymoviemanager.wordguess.GuessBadgeName;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.IFontProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.nebula.widgets.gallery.GalleryItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
public class GalleryLabelProvider extends LabelProvider implements
		IColorProvider, IFontProvider {

	@Override
	public Image getImage(Object element) {
		return searchImage((String) element);
	}

	// public Color getBackground(Object element) {
	// String label = (String) element;
	// if (Integer.parseInt(label.substring(label.indexOf(' ') + 1)) % 2 > 0) {
	// return Display.getCurrent().getSystemColor(SWT.COLOR_YELLOW);
	// } else {
	// return null;
	// }
	// }
	//
	// public Color getForeground(Object element) {
	// String label = (String) element;
	// if (Integer.parseInt(label.substring(label.indexOf(' ') + 1)) % 2 > 0) {
	// return null;
	// } else {
	// return Display.getCurrent().getSystemColor(SWT.COLOR_BLUE);
	// }
	// }
	//
	// public Font getFont(Object element) {
	// String label = (String) element;
	// if (Integer.parseInt(label.substring(label.indexOf(' ') + 1)) % 2 > 0) {
	// return null;
	// } else {
	// FontData sysFontData =
	// Display.getCurrent().getSystemFont().getFontData()[0];
	// sysFontData.setStyle(SWT.BOLD | SWT.ITALIC);
	// return new Font(Display.getCurrent(), sysFontData);
	// }
	// }

	@Override
	public Font getFont(Object element) {
		return null;
	}

	@Override
	public Color getForeground(Object element) {
		return null;
	}

	@Override
	public Color getBackground(Object element) {
		return null;
	}

	private static Image searchImage(String file) {
		ImageDescriptor image = null;
		Bundle bundle = FrameworkUtil.getBundle(GalleryLabelProvider.class);
		URL url = null;
		if (!file.startsWith("Badges")) {
			url = FileLocator.find(bundle, new Path("icons/genres/"
					+ new GuessBadgeName().getImageNameFromBadgeName(file)
					+ ".png"), null);
		} else if (file.startsWith("Badges")) {
			url = FileLocator.find(bundle, new Path("icons/genres/Badges.png"),
					null);
		}
		image = ImageDescriptor.createFromURL(url);
		return image.createImage();

	}

	private static Image resize(Image image, int width, int height) {
		Image scaled = new Image(Display.getDefault(), width, height);
		GC gc = new GC(scaled);
		gc.setAntialias(SWT.ON);
		gc.setInterpolation(SWT.HIGH);
		gc.drawImage(image, 0, 0, image.getBounds().width,
				image.getBounds().height, 0, 0, width, height);
		gc.dispose();
		image.dispose();
		return scaled;
	}

}
