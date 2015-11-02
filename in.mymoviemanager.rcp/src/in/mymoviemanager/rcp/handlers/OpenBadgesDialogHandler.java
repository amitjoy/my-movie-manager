package in.mymoviemanager.rcp.handlers;

import in.mymoviemanager.rcp.dialogs.BadgesDialog;

import org.eclipse.e4.core.di.annotations.Execute;

/**
 * Predefined Badges Dialog Open Handler
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
public class OpenBadgesDialogHandler {
	@Execute
	public void execute() {
		BadgesDialog dialog = new BadgesDialog();
		dialog.setVisible(true);
	}

}