package in.mymoviemanager.rcp.views.listenerProvider;

import in.mymoviemanager.rcp.model.Tag;

import org.eclipse.jface.viewers.LabelProvider;
/**
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 *
 */
public class TagViewerLabelProvider extends LabelProvider {

	@Override
	public String getText(Object element) {
		if (element instanceof Tag) {
			return ((Tag) element).getName();
		}
		return super.getText(element);
	}
}
