package in.mymoviemanager.rcp.views.listenerProvider;

import in.mymoviemanager.rcp.model.Tag;
import in.mymoviemanager.rcp.model.mock.BadgesMockModel;
import in.mymoviemanager.xml.service.IXMLManipulation;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
public class BadgeContentProvider implements ITreeContentProvider {

	private BadgesMockModel model;
	private IXMLManipulation manipulation;

	public BadgeContentProvider(IXMLManipulation manipulation) {
		this.manipulation = manipulation;
	}

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		this.model = (BadgesMockModel) newInput;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return model.getTags(manipulation).toArray();
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof Tag) {
			Tag tag = (Tag) parentElement;
			if (tag.getVideoList() != null)
				return tag.getVideoList().toArray();
		}
		return null;
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof Tag) {
			return true;
		}
		return false;
	}

}
