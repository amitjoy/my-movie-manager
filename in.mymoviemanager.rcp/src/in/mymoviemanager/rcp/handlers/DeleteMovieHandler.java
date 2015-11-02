package in.mymoviemanager.rcp.handlers;

import in.mymoviemanager.events.EventConstants;
import in.mymoviemanager.rcp.model.VideoFile;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.mihalis.opal.opalDialog.Dialog;

/**
 * Deleting Movie Handler
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
public class DeleteMovieHandler {

	@Inject
	IEventBroker broker;

	@Execute
	public void execute(IEclipseContext context) {
		VideoFile file = (VideoFile) context.get(VideoFile.class.getName());
		Display display = Display.getCurrent();
		boolean answer = false;
		if (file != null)
			answer = Dialog.isConfirmed("Delete " + file.getFileName() + " ?",
					"Are you sure you want to delete this movie?");

		Object[] data = new Object[] { file, answer };
		if (answer == true) {
			broker.send(EventConstants.DELETE_MOVIE, data);
		}
	}

}