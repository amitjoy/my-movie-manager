package in.mymoviemanager.rcp.views.listenerProvider;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
/**
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 *
 */
public class ProxyPreferencesSelectionListener extends SelectionAdapter {

	private Text text1;
	private Text text2;
	private Text text3;
	private Text text4;

	public ProxyPreferencesSelectionListener(Text text1, Text text2,
			Text text3, Text text4) {
		this.text1 = text1;
		this.text2 = text2;
		this.text3 = text3;
		this.text4 = text4;
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		Button button = (Button) e.widget;
		if (button.getSelection()) {
			showWidgets();
		} else
			hideWidgets();
	}

	private void hideWidgets() {
		text1.setEnabled(false);
		text2.setEnabled(false);
		text3.setEnabled(false);
		text4.setEnabled(false);
	}

	private void showWidgets() {
		text1.setEnabled(true);
		text2.setEnabled(true);
		text3.setEnabled(true);
		text4.setEnabled(true);

	}

}
