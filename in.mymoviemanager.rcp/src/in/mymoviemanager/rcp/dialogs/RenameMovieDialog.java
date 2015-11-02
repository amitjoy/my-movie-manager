package in.mymoviemanager.rcp.dialogs;

import in.mymoviemanager.events.EventConstants;
import in.mymoviemanager.rcp.model.VideoFile;

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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * Rename Movie Dialog Box Implementation
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
public class RenameMovieDialog extends TitleAreaDialog {
	private Text text;
	private VideoFile file;
	private IEventBroker broker;
	private Label lblPleaseGiveInput;

	@Override
	public void create() {
		super.create();
		setTitle("Rename Movie");
		setMessage("Rename movie to your choice", IMessageProvider.INFORMATION);

	}
	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public RenameMovieDialog(Shell parentShell, VideoFile file,
			IEventBroker broker) {
		super(parentShell);
		this.file = file;
		this.broker = broker;
		setShellStyle(SWT.APPLICATION_MODAL);
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setSize(300, 300);
		GridLayout gl_container = new GridLayout(1, false);
		gl_container.verticalSpacing = 9;
		container.setLayout(gl_container);

//		lblPleaseGiveInput = new Label(container, SWT.NONE);
//		lblPleaseGiveInput.setText("Please give new name");

		text = new Text(container, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		text.setText(file.getFileNameWithoutExtension());
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
				IDialogConstants.CANCEL_LABEL, false);
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
		Object[] data = new Object[] { file, text.getText() };
		broker.send(EventConstants.RENAME_FILE, data);
		super.okPressed();
	}

}
