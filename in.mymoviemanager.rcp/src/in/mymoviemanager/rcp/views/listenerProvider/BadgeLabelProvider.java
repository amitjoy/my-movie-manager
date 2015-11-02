package in.mymoviemanager.rcp.views.listenerProvider;

import in.mymoviemanager.rcp.model.Tag;
import in.mymoviemanager.rcp.model.VideoFile;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
/**
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 *
 */
public class BadgeLabelProvider extends LabelProvider {
	private static final Image FOLDER = getImage("folder.png");
	private static final Image FILE = getImage("file.png");

	@Override
	public String getText(Object element) {
		if (element instanceof Tag) {
			Tag file = (Tag) element;
			return file.getName();
		}
		return ((VideoFile) element).getFileNameWithoutExtension();
	}

	@Override
	public Image getImage(Object element) {
		if (element instanceof Tag) {
			return FOLDER;
		}
		return FILE;
	}

	private static Image getImage(String file) {
		Bundle bundle = FrameworkUtil.getBundle(BadgeLabelProvider.class);
		URL url = FileLocator.find(bundle, new Path("icons/" + file), null);
		ImageDescriptor image = ImageDescriptor.createFromURL(url);
		return image.createImage();

	}
}