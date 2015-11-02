package in.mymoviemanager.rcp.handlers;

import in.mymoviemanager.rcp.model.VideoFile;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.services.IServiceConstants;

/**
 * Play Movie Locally Handler
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
public class PlayMovieHandler {

	public PlayMovieHandler() {
	}

	@Execute
	public void execute(
			@Optional @Named(IServiceConstants.ACTIVE_SELECTION) VideoFile file) {
		if (file != null) {
			File fileHandle = new File(file.getLocation());

			if (Desktop.isDesktopSupported()) {
				Desktop desktop = Desktop.getDesktop();
				if (desktop.isSupported(Desktop.Action.OPEN)) {
					try {
						desktop.open(fileHandle);
					} catch (IOException e) {
					}
				}
			}
		}
	}

}