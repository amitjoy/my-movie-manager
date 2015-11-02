package in.mymoviemanager.rcp.views;

import in.mymoviemanager.events.EventConstants;
import in.mymoviemanager.rcp.model.mock.BadgesMockModel;
import in.mymoviemanager.rcp.views.listenerProvider.BadgeContentProvider;
import in.mymoviemanager.rcp.views.listenerProvider.BadgeLabelProvider;
import in.mymoviemanager.rcp.views.listenerProvider.BadgeViewerKeyAdapter;
import in.mymoviemanager.rcp.views.listenerProvider.BadgeViewerParentDoubleClickListener;
import in.mymoviemanager.rcp.views.listenerProvider.MovieOpenListener;
import in.mymoviemanager.rcp.views.listenerProvider.ViewerMouseListener;
import in.mymoviemanager.rcp.views.listenerProvider.ViewerSelectionListener;
import in.mymoviemanager.xml.service.IXMLManipulation;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.e4.ui.workbench.swt.modeling.EMenuService;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

/**
 * Badge Browser View
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
public class CategoryBrowserPart {
	private TreeViewer viewer;

	@Inject
	IEclipseContext context;

	@Inject
	IEventBroker broker;

	@Inject
	EPartService service;

	@Inject
	ESelectionService selectionService;

	@Inject
	EMenuService menuService;

	@Inject
	IXMLManipulation manipulation;

	@Inject
	UISynchronize synchronize;

	MPart detailsPart;
	MPart imdbPart;
	MPart subtitlePart;
	MPart tagPart;
	MPart actionPart;
	MPart badgeBrowserPart;
	MPart specialBadgesPart;
	MPart movieBrowserPart;

	@PostConstruct
	public void createPartControl(Composite parent) {

		movieBrowserPart = service
				.findPart("in.mymoviemanager.rcp.part.moviesCollection");
		detailsPart = service
				.findPart("in.mymoviemanager.rcp.part.movieFileDetails");
		imdbPart = service.findPart("in.mymoviemanager.rcp.part.imdbDetails");
		subtitlePart = service
				.findPart("in.mymoviemanager.rcp.part.movieSubtiltle");
		tagPart = service.findPart("in.mymoviemanager.rcp.part.assignedBadges");
		actionPart = service.findPart("in.mymoviemanager.rcp.part.actionsView");
		badgeBrowserPart = service
				.findPart("in.mymoviemanager.rcp.part.badgesList");
		specialBadgesPart = service
				.findPart("in.mymoviemanager.rcp.part.specialBadgesInfo");

		viewer = new TreeViewer(parent, SWT.SINGLE | SWT.H_SCROLL
				| SWT.V_SCROLL);
		viewer.setContentProvider(new BadgeContentProvider(manipulation));
		viewer.setLabelProvider(new BadgeLabelProvider());
		viewer.addOpenListener(new MovieOpenListener());
		viewer.setAutoExpandLevel(0);
		viewer.setInput(new BadgesMockModel());

		viewer.addDoubleClickListener(new BadgeViewerParentDoubleClickListener());

		viewer.getTree().addKeyListener(new BadgeViewerKeyAdapter(viewer));

		viewer.getTree().addSelectionListener(
				new ViewerSelectionListener(context, selectionService,
						menuService, broker, detailsPart, imdbPart,
						subtitlePart, tagPart, actionPart, specialBadgesPart,
						service, viewer, null, null, synchronize));
		viewer.getTree().addMouseListener(
				new ViewerMouseListener(context, selectionService, menuService,
						broker, detailsPart, imdbPart, subtitlePart, tagPart,
						actionPart, specialBadgesPart, service, viewer, null,
						null));
	}

	@Focus
	public void setFocus() {
		broker.send(EventConstants.REFRESH_BADGE_VIEW, new Object());
		viewer.getControl().setFocus();
	}

	@Inject
	@Optional
	public void updateBadgeBrowserOnTagRemoved(
			@UIEventTopic(EventConstants.TAG_REMOVED_AND_UPDATE_TAG_BROWSER) Object data) {
		viewer.refresh();
		// viewer.expandAll();
	}

	@Inject
	@Optional
	public void updateBadgeBrowserOnTagAdded(
			@UIEventTopic(EventConstants.TAG_ADDED_AND_UPDATE_TAG_BROWSER) Object data) {
		viewer.refresh();
		// viewer.expandAll();
	}

	@Inject
	@Optional
	public void refreshBadgeView(
			@UIEventTopic(EventConstants.REFRESH_BADGE_VIEW) Object data) {
		viewer.refresh();
		// viewer.expandAll();
	}
}