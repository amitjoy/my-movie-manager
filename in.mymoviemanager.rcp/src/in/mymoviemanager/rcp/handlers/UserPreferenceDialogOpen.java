package in.mymoviemanager.rcp.handlers;

import in.mymoviemanager.rcp.dialogs.UserPreferenceDialog2;

import javax.inject.Inject;

import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.core.commands.EHandlerService;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.css.swt.theme.IThemeEngine;
import org.eclipse.swt.widgets.Display;

/**
 * Open User Preferences Dialog
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
public class UserPreferenceDialogOpen {

	@Inject
	ECommandService commandService;

	@Inject
	EHandlerService handlerService;

	@Inject
	IEclipseContext context;
	
	@Inject
	IEventBroker broker;

	@Execute
	public void execute() {
		Display display = Display.getCurrent();
		UserPreferenceDialog2 dialog = new UserPreferenceDialog2(
				display.getActiveShell(), commandService, handlerService, broker,
				context);
		dialog.open();
	}

}