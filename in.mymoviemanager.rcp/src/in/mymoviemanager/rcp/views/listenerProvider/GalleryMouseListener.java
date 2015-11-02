package in.mymoviemanager.rcp.views.listenerProvider;

import in.mymoviemanager.events.EventConstants;
import in.mymoviemanager.rcp.handlers.RemoveTagHandler;
import in.mymoviemanager.rcp.model.Tag;
import in.mymoviemanager.rcp.views.SpecificBadgeCategoryListingView;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.e4.ui.workbench.swt.modeling.EMenuService;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.nebula.jface.galleryviewer.GalleryTreeViewer;
import org.eclipse.nebula.widgets.gallery.GalleryItem;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Point;

/**
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
public class GalleryMouseListener implements MouseListener {

	EMenuService service;

	EPartService partService;

	MPart badgesPart;

	IEventBroker broker;

	@Inject
	ESelectionService selectionService;

	IEclipseContext context;
	GalleryTreeViewer galleryTreeViewer;

	public GalleryMouseListener(EPartService partService, EMenuService service,
			ESelectionService selectionService, IEclipseContext context,
			IEventBroker broker, GalleryTreeViewer galleryTreeViewer) {
		super();
		this.service = service;
		this.selectionService = selectionService;
		this.context = context;
		this.galleryTreeViewer = galleryTreeViewer;
		this.partService = partService;
		this.broker = broker;
	}

	@Override
	public void mouseDoubleClick(MouseEvent e) {
		GalleryItem item = galleryTreeViewer.getGallery().getItem(
				new Point(e.x, e.y));
		if (item != null) {
			IStructuredSelection selection = (IStructuredSelection) galleryTreeViewer
					.getSelection();
			String tagName = (String) selection.getFirstElement();

			badgesPart = partService
					.findPart("in.mymoviemanager.rcp.part.selectedBadgeCategory");

			boolean flag = badgesPart.isVisible() ? true : false;
			if (!flag) {
				badgesPart.setVisible(true);
			}
			partService.activate(badgesPart);
			badgesPart.setLabel(tagName);
			badgesPart.setCloseable(true);
			context.set("selected_tag", tagName);
			broker.send(EventConstants.UPDATE_SPECIFIC_BADGE_VIEW, tagName);
		}

	}

	@Override
	public void mouseDown(MouseEvent e) {

		GalleryItem item = galleryTreeViewer.getGallery().getItem(
				new Point(e.x, e.y));

		if (item != null) {
			IStructuredSelection selection = (IStructuredSelection) galleryTreeViewer
					.getSelection();
			service.registerContextMenu(galleryTreeViewer.getGallery(),
					"in.mymoviemanager.rcp.popupmenu.badge");
			context.set(Tag.class.getName(), selection.getFirstElement());
			ContextInjectionFactory.make(RemoveTagHandler.class, context);
		}

	}

	@Override
	public void mouseUp(MouseEvent e) {

	}

}
