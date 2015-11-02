package in.mymoviemanager.rcp.views.listenerProvider;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
/**
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 *
 */
public class TagViewerFocusListener implements FocusListener {

	TableViewer tableViewer;

	public TagViewerFocusListener(TableViewer tableViewer) {
		super();
		this.tableViewer = tableViewer;
	}

	@Override
	public void focusGained(FocusEvent e) {
		tableViewer.refresh();

	}

	@Override
	public void focusLost(FocusEvent e) {

	}

}
