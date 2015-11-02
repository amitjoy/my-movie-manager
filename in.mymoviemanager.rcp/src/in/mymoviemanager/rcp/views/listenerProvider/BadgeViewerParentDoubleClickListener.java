package in.mymoviemanager.rcp.views.listenerProvider;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
/**
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 *
 */
public class BadgeViewerParentDoubleClickListener implements
		IDoubleClickListener {

	@Override
	public void doubleClick(DoubleClickEvent event) {
		TreeViewer viewer = (TreeViewer) event.getViewer();
		IStructuredSelection thisSelection = (IStructuredSelection) event
				.getSelection();
		Object selectedNode = thisSelection.getFirstElement();
		viewer.setExpandedState(selectedNode,
				!viewer.getExpandedState(selectedNode));
	}
}
