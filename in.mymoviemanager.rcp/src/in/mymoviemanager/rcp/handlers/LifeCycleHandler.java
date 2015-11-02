package in.mymoviemanager.rcp.handlers;

import java.util.Locale;

import javax.inject.Inject;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.lifecycle.PostContextCreate;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.ResourceManager;
import org.mihalis.opal.login.LoginDialog;
import org.mihalis.opal.login.LoginDialogVerifier;
import org.mihalis.opal.tipOfTheDay.TipOfTheDay;
import org.mihalis.opal.tipOfTheDay.TipOfTheDay.TipStyle;
import org.mihalis.opal.utils.SWTGraphicUtil;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.osgi.service.prefs.Preferences;

/**
 * Application Lifecycle Handler Class
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
public class LifeCycleHandler {

	@Inject
	private IEclipseContext context;
	private String login_username;
	private String login_password;
	private String isLogin;
	private String isTOTD;

	@SuppressWarnings("restriction")
	@PostContextCreate
	void postContextCreate(final IEventBroker eventBroker) {
		final Shell shell = new Shell(SWT.INHERIT_NONE);
		Rectangle rectBounds = shell.getDisplay().getBounds();
		int minWidth = 600;
		int minHeight = 400;
		int x = 400;
		int y = 100;

		if (rectBounds.x > 0) {
			x = (rectBounds.width - minWidth) / 2;
			y = (rectBounds.height - minHeight) / 2;
		} else {
			x = (rectBounds.width / 2 - minWidth) / 2;
			y = (rectBounds.height - minHeight) / 2;
		}

		shell.setBounds(x, y, minWidth, minHeight);

		eventBroker.subscribe(UIEvents.UILifeCycle.ACTIVATE,
				new EventHandler() {
					@Override
					public void handleEvent(Event event) {
						eventBroker.unsubscribe(this);
					}
				});
		if (checkLoginPreferences())
			openLoginDialog(shell);
		// if (checkTOTDPreferences())
		// openTipOfTheDay(shell);
	}

	/**
	 * Tip of the day for movie suggestions
	 * 
	 * @param shell
	 */
	private void openTipOfTheDay(Shell shell) {
		final TipOfTheDay tip = new TipOfTheDay();
		tip.addTip("This is the first tip<br/> "
				+ "<b>This is the first tip</b> "
				+ "<u>This is the first tip</u> "
				+ "<i>This is the first tip</i> " + "This is the first tip "
				+ "This is the first tip<br/>" + "This is the first tip "
				+ "This is the first tip");
		tip.addTip("This is the second tip<br/> "
				+ "<b>This is the second tip</b> "
				+ "<u>This is the second tip</u> <br/>"
				+ "<i>This is the second tip</i> " + "This is the second tip "
				+ "This is the second tip <br/>" + "This is the second tip "
				+ "This is the second tip");

		tip.addTip("This is the third tip<br/> "
				+ "<b>This is the third tip</b> "
				+ "<u>This is the third tip</u> <br/>"
				+ "<i>This is the third tip</i> ");
		tip.setStyle(TipStyle.TWO_COLUMNS_LARGE);
		tip.setDisplayShowOnStartup(false);
		tip.open(shell);
	}

	/**
	 * Show Username and Password Dialog to login to application Default
	 * username/password: default/admin
	 * 
	 * @param shell
	 */
	private void openLoginDialog(final Shell shell) {
		Locale.setDefault(Locale.ENGLISH);

		shell.setText("Login To My Movie Manager Beta");
		shell.setLayout(new GridLayout(2, false));

		final LoginDialogVerifier verifier = new LoginDialogVerifier() {

			@Override
			public void authenticate(final String login, final String password)
					throws Exception {
				if ("".equals(login)) {
					throw new Exception("Please enter a login.");
				}

				if ("".equals(password)) {
					throw new Exception("Please enter a password.");
				}

				if (!login.equalsIgnoreCase("DEFAULT")) {
					if (!login.equalsIgnoreCase(login_username)) {
						throw new Exception("Login unknown.");
					}

					if (!password.equalsIgnoreCase(login_password)) {
						throw new Exception(
								"Authentication failed, please check your password.");
					}
				} else {
					if (!login.equalsIgnoreCase("DEFAULT")) {
						throw new Exception("Login unknown.");
					}

					if (!password.equalsIgnoreCase("admin")) {
						throw new Exception(
								"Authentication failed, please check your password.");
					}
				}

			}
		};

		final LoginDialog dialog = new LoginDialog();
		dialog.setDescription("Please login to My Movie Manager Beta...\nIf in case you forget the password, contact support");
		dialog.setAutorizedLogin(login_username.toUpperCase(), "DEFAULT");
		dialog.setLogin(login_username.toUpperCase());
		dialog.setImage(ResourceManager.getPluginImage("in.mymoviemanager.rcp",
				"icons/login_banner.png"));
		dialog.setVerifier(verifier);
		dialog.setDisplayRememberPassword(false);

		final boolean result = dialog.open();
		if (result) {
			System.out.println("Login confirmed : " + dialog.getLogin());
		} else {
			System.exit(-1);
		}

		SWTGraphicUtil.centerShell(shell);

	}

	/**
	 * Check Login Username and Password for Login to the application
	 * 
	 * @return
	 */
	private boolean checkLoginPreferences() {
		Preferences preferences = InstanceScope.INSTANCE
				.getNode("in.mymoviemanager.rcp");
		login_username = preferences.get("login_username", "");
		login_password = preferences.get("login_password", "");
		isLogin = preferences.get("isLogin", "");
		boolean flag = false;
		try {
			if (Boolean.parseBoolean(isLogin) == true) {
				flag = true;
			}
		} catch (Exception e) {
			flag = false;
		}
		return flag;

	}

	/**
	 * Check Tip of the day preference
	 * 
	 * @return
	 */
	private boolean checkTOTDPreferences() {
		Preferences preferences = InstanceScope.INSTANCE
				.getNode("in.mymoviemanager.rcp");
		isTOTD = preferences.get("isTOTD", "");
		boolean flag = false;
		try {
			if (Boolean.parseBoolean(isTOTD) == true) {
				flag = true;
			}
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

}
