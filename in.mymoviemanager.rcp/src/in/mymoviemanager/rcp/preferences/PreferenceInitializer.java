package in.mymoviemanager.rcp.preferences;

import in.mymoviemanager.rcp.Activator;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * Class used to initialize default preference values.
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault("MySTRING1", "http://www.amitinside.com");
	}

}
