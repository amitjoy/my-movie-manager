package in.mymoviemanager.rcp.handlers;

import in.mymoviemanager.rcp.dialogs.AddBadgeDialog;
import in.mymoviemanager.rcp.model.VideoFile;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.swt.widgets.Shell;

/**
 * Add Badge Handler
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
public class AddBadgeHandler {

	@Inject
	@Named(IServiceConstants.ACTIVE_SHELL)
	Shell shell;

	@Inject
	ESelectionService selectionService;

	@Inject
	EPartService partService;

	@Inject
	IEventBroker broker;

	@Inject
	IEclipseContext context;

	@Execute
	public void execute(
			@Named(IServiceConstants.ACTIVE_SELECTION) VideoFile file) {
		// VideoFile file = (VideoFile) context.get("active_selection");
		AddBadgeDialog dialog = new AddBadgeDialog(shell, file, broker, context);
		if (file != null)
			dialog.open();

	}

}