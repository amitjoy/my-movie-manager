package in.mymoviemanager.rcp.views.listenerProvider;

import in.mymoviemanager.events.EventConstants;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Text;
/**
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 *
 */
public class AddTagButtonSelectionAdapter extends SelectionAdapter {

	Text text;
	IEventBroker broker;
	
	public AddTagButtonSelectionAdapter(Text text, IEventBroker broker){
		this.text = text;
		this.broker = broker;
	}
	
	@Override
	public void widgetSelected(SelectionEvent e) {
		broker.send(EventConstants.NEW_TAG_ADD, text.getText());
		broker.send(EventConstants.TAG_ADDED_AND_UPDATE_TAG_BROWSER, text.getText());
	}
}
