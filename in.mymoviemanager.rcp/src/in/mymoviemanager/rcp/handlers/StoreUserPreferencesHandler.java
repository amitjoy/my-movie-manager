package in.mymoviemanager.rcp.handlers;

import in.mymovemanager.imdb.service.annotation.Imdb;
import in.mymoviemanager.imdb.services.IMovieImdb;

import java.util.Map;

import javax.inject.Named;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.mihalis.opal.opalDialog.Dialog;
import org.osgi.service.prefs.BackingStoreException;

/**
 * Store User Preferences Handler
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
public class StoreUserPreferencesHandler {

	private Map<String, Object> map;

	public StoreUserPreferencesHandler() {

	}

	public StoreUserPreferencesHandler(Map<String, Object> map) {
		this.map = map;
	}

	@SuppressWarnings("restriction")
	@Execute
	public void execute(
			@Preference(nodePath = "in.mymoviemanager.rcp") IEclipsePreferences preferences,
			IEclipseContext context, @Imdb IMovieImdb imdb,
			@Named(IServiceConstants.ACTIVE_SHELL) Shell shell) {
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			preferences.put(entry.getKey(), entry.getValue().toString());
		}
		try {
			preferences.flush();
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}

		// set global identifier whether net is present or not
		if (imdb.checkInternetConnection())
			context.set("internet_connection_alive", "true");

		Dialog.inform("User Preference Change Request",
				"Please restart to apply changes");
	}

}