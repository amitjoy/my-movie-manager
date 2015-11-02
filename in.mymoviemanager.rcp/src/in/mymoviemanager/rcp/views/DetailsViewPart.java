package in.mymoviemanager.rcp.views;

import in.mymoviemanager.events.EventConstants;
import in.mymoviemanager.rcp.model.VideoFile;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.internal.workbench.E4Workbench;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * File Details View
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
public class DetailsViewPart {

	Label fileName;
	Label fileNameDetails;
	Label fileSize;
	Label fileSizeDetails;
	Label location;
	Label locationDetails;

	@Inject
	EPartService service;

	@Inject
	IEclipseContext context;

	Composite composite;

	public DetailsViewPart() {
	}

	/**
	 * Create contents of the view part.
	 */
	@PostConstruct
	public void createControls(Composite parent) {
		composite = parent;
		parent.setLayout(new GridLayout(3, false));
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);

		fileName = new Label(parent, SWT.NONE);
		fileName.setText("Filename: ");

		fileNameDetails = new Label(parent, SWT.NONE);

		new Label(parent, SWT.NONE);

		fileSize = new Label(parent, SWT.NONE);
		fileSize.setText("Size: ");

		fileSizeDetails = new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);

		location = new Label(parent, SWT.NONE);
		// location.setText("Location: ");

		locationDetails = new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);

	}

	@PreDestroy
	public void dispose() {
	}

	@Focus
	public void setFocus() {
	}

	@SuppressWarnings("restriction")
	@Inject
	@Optional
	public void viewMovieDetails(
			@UIEventTopic(EventConstants.MOVIE_DETAILS_AT_MOUSE_CLICK) Object data) {
		if (data instanceof VideoFile) {
			fileNameDetails.setText(((VideoFile) data).getFileName());
			fileSizeDetails.setText(((VideoFile) data).getFileSize() + " MB");
			// locationDetails.setText(((VideoFile) data).getLocation());
			composite.layout();
			context = E4Workbench.getServiceContext();
			context.set(VideoFile.class.getName(), data);
		}
	}

}
