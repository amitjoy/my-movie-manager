package in.mymoviemanager.rcp.handlers;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.swt.widgets.Shell;
import org.mihalis.opal.opalDialog.Dialog;

/**
 * Application Quit Handler
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
public class QuitHandler {
	@Execute
	public void execute(IWorkbench workbench,
			@Named(IServiceConstants.ACTIVE_SHELL) Shell shell) {
		if (Dialog.isConfirmed("Quit My Movie Manager?",
				"Are you sure you want to quit My Movie Manager?")) {
			workbench.close();
		}
	}
}
