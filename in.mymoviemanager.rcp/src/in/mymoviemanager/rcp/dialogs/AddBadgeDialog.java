package in.mymoviemanager.rcp.dialogs;

import in.mymoviemanager.events.EventConstants;
import in.mymoviemanager.rcp.model.VideoFile;
import in.mymoviemanager.utility.BadgesListing;
import net.sf.swtaddons.autocomplete.text.AutocompleteTextInput;

import org.apache.commons.lang3.ArrayUtils;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * Dialog Box Implementation for adding badge
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
public class AddBadgeDialog extends TitleAreaDialog {
	private Text text;

	IEventBroker broker;
	IEclipseContext context;

	VideoFile file;

	@Override
	public void create() {
		super.create();
		setTitle("Add Badge");
		setMessage("Add badge of your choice", IMessageProvider.INFORMATION);

	}

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public AddBadgeDialog(Shell parentShell, VideoFile file,
			IEventBroker broker, IEclipseContext context) {
		super(parentShell);
		setShellStyle(SWT.APPLICATION_MODAL);
		this.file = file;
		this.context = context;
		this.broker = broker;
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new GridLayout(1, false));

		// Label lblPleaseGiveInput = new Label(container, SWT.NONE);
		// lblPleaseGiveInput.setText("Please give input for new Badge Name");

		String[] selections1 = BadgesListing
				.getBadgesListForAutoComplete((String) context
						.get("folder_location"));

		String[] selections2 = BadgesListing.getPredefinedBadges();
		String[] finalSelections = ArrayUtils.addAll(selections1, selections2);

		text = new Text(container, SWT.BORDER);
		new AutocompleteTextInput(text, finalSelections);
		GridData gd_text = new GridData(SWT.LEFT, SWT.CENTER | SWT.ICON_CANCEL, true, false, 1, 1);
		gd_text.heightHint = 23;
		gd_text.widthHint = 310;
		text.setLayoutData(gd_text);
		return container;
	}

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, true);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(327, 155);
	}

	@Override
	protected void okPressed() {
		Object[] values = new Object[] { text.getText(), file, null };
		broker.send(EventConstants.NEW_TAG_ADD, values);
		super.okPressed();
	}

}
