package in.mymoviemanager.rcp.handlers;

import in.mymoviemanager.rcp.dialogs.RenameMovieDialog;
import in.mymoviemanager.rcp.model.VideoFile;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.swt.widgets.Display;

/**
 * Rename Movie Handler
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
public class RenameMovieHandler {

	@Inject
	IEventBroker broker;

	@Execute
	public void execute(IEclipseContext context, IWorkbench workbench) {
		VideoFile file = (VideoFile) context.get(VideoFile.class.getName());
		Display display = Display.getCurrent();
		RenameMovieDialog dialog = new RenameMovieDialog(
				display.getActiveShell(), file, broker);
		if (file != null)
			dialog.open();
	}

}