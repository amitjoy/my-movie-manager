package in.mymoviemanager.rcp.views.listenerProvider;

import in.mymoviemanager.rcp.model.VideoFile;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.OpenEvent;
/**
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 *
 */
public class MovieOpenListener implements IOpenListener {

	@Override
	public void open(OpenEvent event) {
		IStructuredSelection selection = (IStructuredSelection) event
				.getSelection();
		File fileHandle = new File("");;
		if (selection.getFirstElement() instanceof VideoFile) {
			VideoFile file = (VideoFile) selection.getFirstElement();
			fileHandle = new File(file.getLocation());
		}

		if (Desktop.isDesktopSupported()) {
			Desktop desktop = Desktop.getDesktop();
			if (desktop.isSupported(Desktop.Action.OPEN)) {
				try {
					desktop.open(fileHandle);
				} catch (Exception e) {
				}
			}
		}

	}

}
