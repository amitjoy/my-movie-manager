package in.mymoviemanager.rcp.dialogs;

import in.mymoviemanager.rcp.handlers.StoreUserPreferencesHandler;
import in.mymoviemanager.rcp.views.listenerProvider.TextBoxNumberFormatterListener;
import in.mymoviemanager.rcp.views.listenerProvider.ThemeChangeListener;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.core.commands.EHandlerService;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.ResourceManager;
import org.osgi.service.prefs.Preferences;

/**
 * User Preferences Dialog Box Implementation
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
public class UserPreferenceDialog2 extends Dialog {
	private Text text;
	private Text text_1;
	private Text text_2;
	private Text text_3;
	private Text text_4;
	Button btnUseProxy;
	Button btnUseLogin;
//	Button btnShowTipOf;
	Combo combo;

	EHandlerService handlerService;
	ECommandService commandService;
	IEclipseContext context;
	IEventBroker broker;

	String favourite_folder = "";
	String isProxyEnabled = "";
	String proxy_address = "";
	String proxy_port = "";
	String username = "";
	String password = "";
	String themeId = "";
	String login_username = "";
	String login_password = "";
	private Text text_6;
	private Text text_5;
	private String isLoginEnabled;
	private String isTOTD;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public UserPreferenceDialog2(Shell parentShell,
			ECommandService commandService, EHandlerService handlerService,
			IEventBroker broker, IEclipseContext context) {
		super(parentShell);
		this.commandService = commandService;
		this.handlerService = handlerService;
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

		TabFolder tabFolder = new TabFolder(container, SWT.NONE);
		GridData gd_tabFolder = new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 1, 1);
		gd_tabFolder.widthHint = 440;
		gd_tabFolder.heightHint = 150;
		tabFolder.setLayoutData(gd_tabFolder);

		TabItem tbtmC = new TabItem(tabFolder, SWT.NONE);

		if (System.getProperty("os.name").toLowerCase().contains("windows"))
			tbtmC.setImage(ResourceManager.getPluginImage(
					"in.mymoviemanager.rcp", "icons/user_pref.png"));
		tbtmC.setText("Favourite");

		final Composite composite = new Composite(tabFolder, SWT.NONE);
		tbtmC.setControl(composite);
		composite.setLayout(new org.eclipse.swt.layout.GridLayout(3, false));
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		Label lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setText("Favourite Folder");

		text = new Text(composite, SWT.BORDER);
		text.setEnabled(false);
		GridData gd_text = new GridData(SWT.FILL, SWT.CENTER, false, false, 1,
				1);
		gd_text.widthHint = 205;
		text.setLayoutData(gd_text);

		Button btnChoose = new Button(composite, SWT.NONE);
		GridData gd_btnChoose = new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 1, 1);
		gd_btnChoose.widthHint = 81;
		btnChoose.setLayoutData(gd_btnChoose);
		btnChoose.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				DirectoryDialog dialog = new DirectoryDialog(composite
						.getShell());
				String directoryLocation = dialog.open();
				if (directoryLocation != null)
					text.setText(directoryLocation);
			}
		});
		btnChoose.setText("Choose");

		Label label = new Label(composite, SWT.NONE);
		label.setText("Theme");
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false,
				1, 1));

		combo = new Combo(composite, SWT.NONE);
		GridData gd_combo = new GridData(SWT.FILL, SWT.CENTER, true, false, 1,
				1);
		gd_combo.widthHint = 201;
		combo.setLayoutData(gd_combo);
		String themes[] = new String[] { "-- Select Theme --", "Default Theme",
				"Theme 1" };
		combo.setItems(themes);
		combo.addSelectionListener(new ThemeChangeListener(combo, broker));

		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

//		btnShowTipOf = new Button(composite, SWT.CHECK);
//		btnShowTipOf.setText("Show Tip of The Day At Startup");
//		btnShowTipOf.setSelection(false);
//		new Label(composite, SWT.NONE);

		TabItem tbtmAccount = new TabItem(tabFolder, SWT.NONE);
		if (System.getProperty("os.name").toLowerCase().contains("windows"))
			tbtmAccount.setImage(ResourceManager.getPluginImage(
					"in.mymoviemanager.rcp", "icons/user_acc.png"));
		tbtmAccount.setText("Account");

		Composite composite_3 = new Composite(tabFolder, SWT.NONE);
		tbtmAccount.setControl(composite_3);
		composite_3.setLayout(new GridLayout(2, false));
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);

		btnUseLogin = new Button(composite_3, SWT.CHECK);
		btnUseLogin.setText("Use Login");
		btnUseLogin.setSelection(false);
		btnUseLogin.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				Button button = (Button) e.widget;
				if (button.getSelection()) {
					text_5.setEnabled(true);
					text_6.setEnabled(true);
				} else {
					text_5.setEnabled(false);
					text_6.setEnabled(false);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		Label lblUsername_1 = new Label(composite_3, SWT.NONE);
		lblUsername_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblUsername_1.setText("Username");
		lblUsername_1.setBounds(0, 0, 106, 17);

		text_5 = new Text(composite_3, SWT.BORDER);
		text_5.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1,
				1));

		Label lblPassword_1 = new Label(composite_3, SWT.NONE);
		lblPassword_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblPassword_1.setAlignment(SWT.RIGHT);
		lblPassword_1.setText("Password");
		lblPassword_1.setBounds(0, 0, 47, 17);

		text_6 = new Text(composite_3, SWT.BORDER | SWT.PASSWORD);
		text_6.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1,
				1));

		TabItem tbtmB = new TabItem(tabFolder, SWT.NONE);
		if (System.getProperty("os.name").toLowerCase().contains("windows"))
			tbtmB.setImage(ResourceManager.getPluginImage(
					"in.mymoviemanager.rcp", "icons/net_pref.png"));
		tbtmB.setText("Network");

		final Composite composite_1 = new Composite(tabFolder, SWT.NONE);

		tbtmB.setControl(composite_1);
		composite_1.setLayout(new org.eclipse.swt.layout.GridLayout(5, false));
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);

		btnUseProxy = new Button(composite_1, SWT.CHECK);

		btnUseProxy.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Button button = (Button) e.widget;
				if (button.getSelection()) {
					showWidgets();
				} else
					hideWidgets();
			}

		});
		btnUseProxy.setText("Use Proxy");
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);

		Label lblAddress = new Label(composite_1, SWT.NONE);
		lblAddress.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblAddress.setText("Address");

		text_1 = new Text(composite_1, SWT.BORDER);
		GridData gd_text_1 = new GridData(SWT.FILL, SWT.CENTER, false, false,
				1, 1);
		gd_text_1.widthHint = 151;
		text_1.setLayoutData(gd_text_1);

		Label lblPort = new Label(composite_1, SWT.NONE);
		lblPort.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false,
				1, 1));
		lblPort.setText("Port");

		text_2 = new Text(composite_1, SWT.BORDER);
		text_2.addVerifyListener(new TextBoxNumberFormatterListener());
		GridData gd_text_2 = new GridData(SWT.FILL, SWT.CENTER, false, false,
				1, 1);
		gd_text_2.widthHint = 67;
		text_2.setLayoutData(gd_text_2);
		new Label(composite_1, SWT.NONE);

		Label lblUsername = new Label(composite_1, SWT.NONE);
		lblUsername.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblUsername.setText("Username");

		text_3 = new Text(composite_1, SWT.BORDER);
		text_3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1,
				1));
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);

		Label lblPassword = new Label(composite_1, SWT.NONE);
		lblPassword.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblPassword.setText("Password");

		text_4 = new Text(composite_1, SWT.BORDER);
		text_4.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1,
				1));
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);

		TabItem tbtmApplicationSettings = new TabItem(tabFolder, SWT.NONE);
		if (System.getProperty("os.name").toLowerCase().contains("windows"))
			tbtmApplicationSettings.setImage(ResourceManager.getPluginImage(
					"in.mymoviemanager.rcp", "icons/app_pref.png"));
		tbtmApplicationSettings.setText("Update");

		Composite composite_2 = new Composite(tabFolder, SWT.NONE);
		tbtmApplicationSettings.setControl(composite_2);
		composite_2.setLayout(new org.eclipse.swt.layout.GridLayout(20, false));
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		
		Label lblComingSoon = new Label(composite_2, SWT.NONE);
		lblComingSoon.setText("Coming Soon");

		// Fill up fields from Preferences Store
		checkPreferencesStore();
		setValuesIfPreferencesSet();

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
		return new Point(480, 300);
	}

	@Override
	@SuppressWarnings({ "restriction", "rawtypes", "unchecked" })
	protected void okPressed() {
		Map prefValues = new HashMap();
		prefValues.put("favFolder", text.getText());
		prefValues.put("isProxy", btnUseProxy.getSelection());
		prefValues.put("proxyURL", text_1.getText());
		prefValues.put("port", text_2.getText());
		prefValues.put("username", text_3.getText());
		prefValues.put("password", text_4.getText());
		prefValues.put("theme", combo.getText());
		prefValues.put("login_username", text_5.getText());
		prefValues.put("login_password", text_6.getText());
		prefValues.put("isLogin", btnUseLogin.getSelection());
//		prefValues.put("isTOTD", btnShowTipOf.getSelection());

		StoreUserPreferencesHandler handler = new StoreUserPreferencesHandler(
				prefValues);
		Command command = commandService
				.getCommand("in.mymoviemanager.rcp.command.prefStore");
		handlerService.activateHandler(
				"in.mymoviemanager.rcp.command.prefStore", handler);
		ParameterizedCommand cmd = commandService.createCommand(
				"in.mymoviemanager.rcp.command.prefStore", null);
		handlerService.executeHandler(cmd);
		super.okPressed();
	}

	/**
	 * Check if preferences values are found
	 */
	public void checkPreferencesStore() {
		Preferences preferences = InstanceScope.INSTANCE
				.getNode("in.mymoviemanager.rcp");

		username = preferences.get("username", "");
		isProxyEnabled = preferences.get("isProxy", "false");
		proxy_address = preferences.get("proxyURL", "");
		proxy_port = preferences.get("port", "");
		favourite_folder = preferences.get("favFolder", "");
		password = preferences.get("password", "");
		themeId = preferences.get("theme", "");
		login_username = preferences.get("login_username", "");
		login_password = preferences.get("login_password", "");
		isLoginEnabled = preferences.get("isLogin", "false");
		isTOTD = preferences.get("isTOTD", "false");
	}

	/**
	 * If preferences values are found, set it in dialog box
	 */
	public void setValuesIfPreferencesSet() {
		text.setText(favourite_folder);
		btnUseProxy.setSelection(Boolean.parseBoolean(isProxyEnabled));
		text_1.setText(proxy_address);
		text_2.setText(proxy_port);
		text_3.setText(username);
		text_4.setText(password);
		if (btnUseProxy.getSelection() == false) {
			hideWidgets();
		}
		if (btnUseLogin.getSelection() == false) {
			text_5.setEnabled(false);
			text_6.setEnabled(false);
		}
		combo.setText(themeId);
		text_5.setText(login_username);
		text_6.setText(login_password);
		btnUseLogin.setSelection(Boolean.parseBoolean(isLoginEnabled));
//		btnShowTipOf.setSelection(Boolean.parseBoolean(isTOTD));
	}

	private void hideWidgets() {
		text_1.setEnabled(false);
		text_2.setEnabled(false);
		text_3.setEnabled(false);
		text_4.setEnabled(false);
	}

	private void showWidgets() {
		text_1.setEnabled(true);
		text_2.setEnabled(true);
		text_3.setEnabled(true);
		text_4.setEnabled(true);

	}
}
