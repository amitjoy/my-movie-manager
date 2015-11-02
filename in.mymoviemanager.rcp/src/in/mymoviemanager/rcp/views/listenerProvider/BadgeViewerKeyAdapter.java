package in.mymoviemanager.rcp.views.listenerProvider;

import in.mymoviemanager.rcp.model.VideoFile;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
/**
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 *
 */
public class BadgeViewerKeyAdapter implements KeyListener {

	public TreeViewer viewer;

	public BadgeViewerKeyAdapter(TreeViewer viewer) {
		super();
		this.viewer = viewer;
	}

	@Override
	public void keyReleased(final KeyEvent e) {
		if (e.keyCode == SWT.DEL) {
			final IStructuredSelection selection = (IStructuredSelection) viewer
					.getSelection();
			if (selection.getFirstElement() instanceof VideoFile) {
				VideoFile o = (VideoFile) selection.getFirstElement();
			}

		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
