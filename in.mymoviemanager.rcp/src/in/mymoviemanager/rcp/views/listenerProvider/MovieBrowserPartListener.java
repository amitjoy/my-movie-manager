package in.mymoviemanager.rcp.views.listenerProvider;

import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.IPartListener;
import org.eclipse.jface.viewers.TableViewer;
/**
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 *
 */
public class MovieBrowserPartListener implements IPartListener {

	MPart part;
	TableViewer viewer;

	public MovieBrowserPartListener(MPart part, TableViewer viewer) {
		super();
	}

	@Override
	public void partActivated(MPart part) {

	}

	@Override
	public void partBroughtToTop(MPart part) {
		this.part = part;

	}

	@Override
	public void partDeactivated(MPart part) {
	}

	@Override
	public void partHidden(MPart part) {

	}

	@Override
	public void partVisible(MPart part) {

	}

}
