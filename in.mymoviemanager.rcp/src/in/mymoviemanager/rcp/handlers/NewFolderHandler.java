package in.mymoviemanager.rcp.handlers;

import in.mymovemanager.imdb.service.annotation.Imdb;
import in.mymoviemanager.events.EventConstants;
import in.mymoviemanager.file.Utility;
import in.mymoviemanager.imdb.services.IMovieImdb;
import in.mymoviemanager.rcp.filerepository.VideoFileRepository;
import in.mymoviemanager.xml.service.IXMLManipulation;

import java.security.NoSuchAlgorithmException;

import javax.inject.Named;
import javax.xml.bind.JAXBException;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.internal.workbench.E4Workbench;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * New or Existing Folder Select Handler
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
public class NewFolderHandler {

	private String location;

	private String previousLocation;

	public NewFolderHandler() {
	}

	public NewFolderHandler(String favourite_folder_preference) {
		this.location = favourite_folder_preference;
	}

	@Execute
	public void execute(@Named(IServiceConstants.ACTIVE_SHELL) Shell shell,
			IEventBroker broker, @Imdb IMovieImdb imdb,
			IXMLManipulation manipulation) throws JAXBException,
			NoSuchAlgorithmException {

		@SuppressWarnings("restriction")
		IEclipseContext context = E4Workbench.getServiceContext();
		if (location == null) {
			DirectoryDialog fd = new DirectoryDialog(shell);
			fd.setText("Select Movie Folder");
			String loc = fd.open();
			if (loc == null)
				location = previousLocation;
			else
				location = loc;
		}
		context.set("folder_location", location);

		// Set context if Internet connection is present
		if (imdb.checkInternetConnection())
			context.set("internet_connection_alive", "true");

		if (!Utility.filePresence(location)) {
			broker.send(EventConstants.NEW_MOVIE_FOLDER, location);
		} else {
			VideoFileRepository repository = manipulation.convertToModel(
					location, 1);
			context.set(VideoFileRepository.class, repository);
			broker.send(EventConstants.EXISTING_MOVIE_FOLDER, location);
		}
		previousLocation = location;
		location = null;
	}

}