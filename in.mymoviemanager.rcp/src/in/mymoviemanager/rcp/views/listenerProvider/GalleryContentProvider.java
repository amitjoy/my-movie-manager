package in.mymoviemanager.rcp.views.listenerProvider;

import in.mymoviemanager.rcp.model.Tag;
import in.mymoviemanager.rcp.model.mock.BadgesListMockModel;

import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.nebula.jface.galleryviewer.GalleryTreeViewer;
/**
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 *
 */
public class GalleryContentProvider implements ITreeContentProvider {
	BadgesListMockModel badgeModel;

	public static final int NUM_GROUPS = 1;

	String[] groups = new String[NUM_GROUPS];
	String[][] items;

	List<Tag> tags;

	GalleryTreeViewer viewer;

	public GalleryContentProvider(GalleryTreeViewer viewer) {
		this.viewer = viewer;
	}

	public Object[] getChildren(Object parentElement) {
		int idx = Arrays.asList(groups).indexOf(parentElement);
		return items[idx];
	}

	public Object getParent(Object element) {
		return null;
	}

	public boolean hasChildren(Object element) {
		return ((String) element).startsWith("Badges");
	}

	public Object[] getElements(Object inputElement) {
		return groups;
	}

	public void dispose() {
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		tags = (List<Tag>) newInput;
		if (tags != null) {
			items = new String[NUM_GROUPS][tags.size()];
			for (int i = 0; i < NUM_GROUPS; i++) {
				groups[i] = "Badges";
				for (int j = 0; j < tags.size(); j++) {
					items[i][j] = tags.get(j).getName();
				}
			}
		}
	}
}
