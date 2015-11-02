package in.mymoviemanager.rcp.views;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wb.swt.ResourceManager;
import org.mihalis.opal.obutton.GreenButtonRenderer;
import org.mihalis.opal.obutton.OButton;
import org.mihalis.opal.obutton.RedButtonRenderer;

/**
 * Actions View Part
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
public class ActionsViewPart {

	public ActionsViewPart() {
	}

	/**
	 * Create contents of the view part.
	 */
	@PostConstruct
	public void createControls(Composite parent) {
		parent.setLayout(new GridLayout(1, false));

		final OButton button1 = new OButton(parent, SWT.NONE);
		button1.setText("Rename File");
		button1.setImage(ResourceManager.getPluginImage(
				"in.mymoviemanager.rcp", "icons/rename.png"));
		button1.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER,
				false, false));
		button1.setButtonRenderer(GreenButtonRenderer.getInstance());

		final OButton button2 = new OButton(parent, SWT.NONE);
		button2.setText("Delete File");
		button2.setImage(ResourceManager.getPluginImage(
				"in.mymoviemanager.rcp", "icons/delete.png"));
		button2.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER,
				false, false));
		button2.setButtonRenderer(RedButtonRenderer.getInstance());

	}

	@PreDestroy
	public void dispose() {
	}

	@Focus
	public void setFocus() {
		// TODO Set the focus to control
	}
}
