package in.mymoviemanager.rcp.views;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

/**
 * @deprecated
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
public class SideWiserDataView {

	public SideWiserDataView() {
	}

	/**
	 * Create contents of the view part.
	 */
	private Shell shell;

	@PostConstruct
	public void createControls(Composite parent) {
		shell = new Shell();
		shell.setLayout(new GridLayout(1, true));

		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		shell.setLayoutData(gridData);

		shell.setText("Side By Side");

		createDialogArea(shell);

	}

	@PreDestroy
	public void dispose() {
	}

	@Focus
	public void setFocus() {
		// TODO Set the focus to control
	}

	protected Control createDialogArea(Composite parent) {
		final Composite area = new Composite(parent, SWT.NONE);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.marginWidth = 15;
		gridLayout.marginHeight = 10;
		area.setLayout(gridLayout);

		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		shell.setLayoutData(gridData);
		// area.setLayoutData(gridData);

		createTopButtons(area);
		createTableViewer(area);
		return area;
	}

	private void createTableViewer(Composite area) {
		Table table = new Table(area, SWT.BORDER | SWT.V_SCROLL
				| SWT.FULL_SELECTION);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		table.setLayoutData(gridData);

		table.setLinesVisible(true);
		table.setHeaderVisible(true);

		TableColumn column = new TableColumn(table, SWT.LEFT);
		column.setWidth(320);
		column.setText("Column 1");

		column = new TableColumn(table, SWT.LEFT);
		column.setWidth(320);
		column.setText("Column 2");
	}

	protected void createTopButtons(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		gridLayout.verticalSpacing = 0;
		gridLayout.horizontalSpacing = 0;
		composite.setLayout(gridLayout);

		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		composite.setLayoutData(gridData);

		gridData = new GridData(SWT.DEFAULT, SWT.FILL, false, false);

		Button pdfButton = new Button(composite, SWT.PUSH);
		pdfButton.setText("Create PDF");
		pdfButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});

		pdfButton.setLayoutData(gridData);

		Button plotButton = new Button(composite, SWT.PUSH);
		plotButton.setText("Plot");
		plotButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});
		// plotButton.setLayoutData(gridData);
	}

}
