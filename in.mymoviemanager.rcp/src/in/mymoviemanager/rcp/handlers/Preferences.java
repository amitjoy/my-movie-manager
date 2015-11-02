package in.mymoviemanager.rcp.handlers;

import javax.inject.Named;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.PreferencesUtil;

/**
 * @deprecated Preferences Dialog
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
public class Preferences {

	@Optional
	@Named(IServiceConstants.ACTIVE_SHELL)
	Shell shell;

	@Execute
	public void execute(ExecutionEvent event) {

		final PreferenceDialog dialog = PreferencesUtil
				.createPreferenceDialogOn(shell, null, null, null);
		dialog.open();
	}

}