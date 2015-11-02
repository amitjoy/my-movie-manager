package in.mymoviemanager.rcp.views.listenerProvider;

import in.mymoviemanager.events.EventConstants;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Combo;

public class ThemeChangeListener implements SelectionListener {

	private Combo combo;
	private IEventBroker broker;
	private static final String DEFAULT_THEME = "in.mymoviemanager.rcp.theme.default";
	private static final String FIRST_THEME = "in.mymoviemanager.rcp.theme.theme1";

	public ThemeChangeListener(Combo combo, IEventBroker broker) {
		this.combo = combo;
		this.broker = broker;
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		if (combo.getText().equals("Default Theme"))
			broker.send(EventConstants.CHANGE_THEME, DEFAULT_THEME);
		else if (combo.getText().equals("Theme 1"))
			broker.send(EventConstants.CHANGE_THEME, FIRST_THEME);
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
	}

}
