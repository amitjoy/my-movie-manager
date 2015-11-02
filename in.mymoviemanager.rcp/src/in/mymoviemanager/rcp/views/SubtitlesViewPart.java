package in.mymoviemanager.rcp.views;

import in.mymoviemanager.events.EventConstants;
import in.mymoviemanager.rcp.model.VideoFile;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * Subtitle View
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
public class SubtitlesViewPart {

	Composite composite;

	@Inject
	IEventBroker broker;
	private Label subtitlePresence;
	private Label subtitlePresenceDetails;
	private Label subLocation;
	private Label subLocationDetails;

	public SubtitlesViewPart() {
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

		subtitlePresence = new Label(parent, SWT.NONE);
		subtitlePresence.setText("Subtitle: ");

		subtitlePresenceDetails = new Label(parent, SWT.NONE);

		new Label(parent, SWT.NONE);

		subLocation = new Label(parent, SWT.NONE);
		subLocation.setText("File Location: ");

		subLocationDetails = new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);

	}

	@PreDestroy
	public void dispose() {
	}

	@Focus
	public void setFocus() {
	}

	@Inject
	@Optional
	public void viewMovieDetails(
			@UIEventTopic(EventConstants.SUBTITLES_DETAILS_AT_MOUSE_CLICK) Object data) {
		if (data instanceof VideoFile) {
			VideoFile file = (VideoFile) data;
			if (file.isSubtitlePresent()) {
				subtitlePresence.setText("Yes");
				subLocation.setText("File Location: ");
				subLocationDetails.setText(file.getSubtitleLocation());
			} else {
				subtitlePresence.setText("No");
				subLocation.setText("");
				subLocationDetails.setText("");
			}
		}
		composite.layout();
	}

}
