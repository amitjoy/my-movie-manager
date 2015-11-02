package in.mymoviemanager.rcp.handlers;

import in.mymoviemanager.events.EventConstants;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

/**
 * Rescan existing folder handler
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
public class RescanFolderHandler {

	@Inject
	IEventBroker broker;

	@Inject
	IEclipseContext context;

	@Inject
	EPartService service;

	MPart detailsPart;
	MPart imdbPart;
	MPart subtitlePart;
	MPart tagPart;
	MPart actionPart;
	MPart specialBadgesInfoPart;;

	@Execute
	public void execute() {
		String location = (String) context.get("folder_location");
		// Don't show the parts at first
		detailsPart = service
				.findPart("in.mymoviemanager.rcp.part.movieFileDetails");
		imdbPart = service.findPart("in.mymoviemanager.rcp.part.imdbDetails");
		subtitlePart = service
				.findPart("in.mymoviemanager.rcp.part.movieSubtiltle");
		tagPart = service.findPart("in.mymoviemanager.rcp.part.assignedBadges");
		actionPart = service.findPart("in.mymoviemanager.rcp.part.actionsView");
		specialBadgesInfoPart = service
				.findPart("in.mymoviemanager.rcp.part.specialBadgesInfo");

		detailsPart.setVisible(false);
		imdbPart.setVisible(false);
		subtitlePart.setVisible(false);
		tagPart.setVisible(false);
		actionPart.setVisible(false);
		specialBadgesInfoPart.setVisible(false);

		// MPart tempPart = service
		// .createPart("com.mymoviemanager.rcp.partdescriptor.temp");
		// service.showPart(tempPart, PartState.ACTIVATE);

		broker.send(EventConstants.RESCAN_FOLDER, location);
	}

}