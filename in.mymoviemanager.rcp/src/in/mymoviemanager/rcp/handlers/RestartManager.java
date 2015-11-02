package in.mymoviemanager.rcp.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.mihalis.opal.opalDialog.Dialog;

/**
 * Restart application Handler
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
public class RestartManager {
	@Execute
	public void execute(IWorkbench workbench, Shell shell) {
		boolean status = Dialog.isConfirmed("Restart My Movie Manager?",
				"Do you want to restart My Movie Manager?");
		if (status == true) {
			workbench.restart();
		}
	}

}