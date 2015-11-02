package in.mymoviemanager.rcp.dialogs;

import in.mymoviemanager.rcp.views.listenerProvider.BadgeLabelProvider;

import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.mihalis.opal.preferenceWindow.PWGroup;
import org.mihalis.opal.preferenceWindow.PWRow;
import org.mihalis.opal.preferenceWindow.PWTab;
import org.mihalis.opal.preferenceWindow.PreferenceWindow;
import org.mihalis.opal.preferenceWindow.enabler.EnabledIfEquals;
import org.mihalis.opal.preferenceWindow.enabler.EnabledIfTrue;
import org.mihalis.opal.preferenceWindow.widgets.PWButton;
import org.mihalis.opal.preferenceWindow.widgets.PWCheckbox;
import org.mihalis.opal.preferenceWindow.widgets.PWColorChooser;
import org.mihalis.opal.preferenceWindow.widgets.PWCombo;
import org.mihalis.opal.preferenceWindow.widgets.PWDirectoryChooser;
import org.mihalis.opal.preferenceWindow.widgets.PWFileChooser;
import org.mihalis.opal.preferenceWindow.widgets.PWFloatText;
import org.mihalis.opal.preferenceWindow.widgets.PWFontChooser;
import org.mihalis.opal.preferenceWindow.widgets.PWIntegerText;
import org.mihalis.opal.preferenceWindow.widgets.PWLabel;
import org.mihalis.opal.preferenceWindow.widgets.PWPasswordText;
import org.mihalis.opal.preferenceWindow.widgets.PWRadio;
import org.mihalis.opal.preferenceWindow.widgets.PWScale;
import org.mihalis.opal.preferenceWindow.widgets.PWSeparator;
import org.mihalis.opal.preferenceWindow.widgets.PWSpinner;
import org.mihalis.opal.preferenceWindow.widgets.PWStringText;
import org.mihalis.opal.preferenceWindow.widgets.PWTextarea;
import org.mihalis.opal.preferenceWindow.widgets.PWURLText;
import org.mihalis.opal.utils.SWTGraphicUtil;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * User Preference Dialog Window Implementation for opal
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
public class UserPreferenceDialog {

	private final Display display;

	public UserPreferenceDialog(Display display) {
		this.display = display;
	}

	public Control createDialogArea() {
		final Shell shell = new Shell(display);
		shell.setText("PreferenceWindow snippet");
		shell.setLayout(new FillLayout(SWT.VERTICAL));

		Locale.setDefault(Locale.ENGLISH);
		final Map<String, Object> data = fillData();
		final PreferenceWindow window = PreferenceWindow.create(shell, data);
		shell.setSize(100, 100);

		createDocumentTab(window);
		createInfoTab(window);
		createTerminalTab(window);
		createPrinterTab(window);
		createSystemTab(window);

		window.setSelectedTab(0);

		window.open();
		SWTGraphicUtil.centerShell(shell);
		return shell;
	}

	private static Map<String, Object> fillData() {
		final Map<String, Object> data = new HashMap<String, Object>();
		data.put("text", "A string");
		data.put("int", new Integer(42));
		data.put("float", new Float(3.14));
		data.put("url", "http://www.google.fr/");
		data.put("password", "password");
		data.put("directory", "");
		data.put("file", "");
		data.put("textarea", "long long\nlong long\nlong long\ntext...");
		data.put("comboReadOnly", "Value 1");
		data.put("combo", "Other Value");

		data.put("cb1", new Boolean(true));
		// cb2 is not initialized
		data.put("slider", new Integer(40));
		data.put("spinner", new Integer(30));
		data.put("color", new RGB(120, 15, 30));
		// font is not initialized

		data.put("radio", "Radio button 3");
		data.put("cb3", new Boolean(true));

		// cb4 to cb14 are not initialised

		data.put("cacheSizeUnit", "Megabytes");
		data.put("openMode", "Double click");

		return data;
	}

	protected static void createDocumentTab(final PreferenceWindow window) {
		final PWTab documentTab = window.addTab(searchImage("music.png"),
				"Document");

		documentTab
				.add(new PWLabel(
						"Let's start with Text, Separator, Combo and button")).//
				add(new PWStringText("String :", "text")
						.setAlignment(GridData.FILL)).//
				add(new PWIntegerText("Integer :", "int"));
		documentTab.add(new PWFloatText("Float :", "float"));
		documentTab.add(new PWURLText("URL :", "url"));
		documentTab.add(new PWPasswordText("Password :", "password"));
		documentTab.add(new PWDirectoryChooser("Directory :", "directory"));
		documentTab.add(new PWFileChooser("File :", "file"));
		documentTab.add(new PWTextarea("Textarea :", "textarea"));

		documentTab.add(new PWSeparator());

		documentTab.add(new PWCombo("Combo (read-only):", "comboReadOnly",
				"Value 1", "Value 2", "Value 3"));
		// documentTab.add(new PWCombo("Combo (editable):", "combo", true,
		// "Value 1", "Value 2", "Value 3"));

		documentTab.add(new PWSeparator("Titled separator"));
		documentTab.add(new PWButton("First button", new SelectionAdapter() {

			/**
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(final SelectionEvent e) {
				org.mihalis.opal.opalDialog.Dialog.inform("Hi",
						"You pressed the first button");
			}

		}).setAlignment(GridData.END));
	}

	protected static void createInfoTab(final PreferenceWindow window) {
		final PWTab infoTab = window.addTab(searchImage("pref1.png"), "Info");

		infoTab.add(new PWLabel(
				"Checkboxes, Slider,Spinner, Color chooser, Font chooser"));
		infoTab.add(new PWCheckbox("Checkbox 1", "cb1"));
		infoTab.add(new PWCheckbox("Checkbox 2", "cb2"));

		infoTab.add(new PWSeparator());

		infoTab.add(new PWScale("Slider : ", "slider", 0, 100, 10));
		infoTab.add(new PWSpinner("Spinner :", "spinner", 0, 100));

		infoTab.add(new PWSeparator());

		infoTab.add(new PWColorChooser("Color :", "color"));
		infoTab.add(new PWFontChooser("Font :", "font"));

	}

	protected static void createTerminalTab(final PreferenceWindow window) {
		final PWTab terminalTab = window.addTab(searchImage("pref1.png"),
				"Terminal");

		terminalTab.add(new PWLabel(
				"Group, radio, indentation and group of buttons in a row"));

		final PWGroup group = new PWGroup("Group of buttons");
		group.add(new PWRadio("Radio buttons:", "radio", "Radio button 1",
				"Radio button 2", "Radio button 3"));
		terminalTab.add(group);

		terminalTab.add(new PWCheckbox("Checkbox 3 (indented)", "cb3")
				.setIndent(30).setWidth(200));

		terminalTab.add(new PWRow()
				.add(new PWButton("First button", new SelectionAdapter() {
				})).add(new PWButton("Second button", new SelectionAdapter() {
				})).add(new PWButton("Third button", new SelectionAdapter() {
				})));

	}

	protected static void createPrinterTab(final PreferenceWindow window) {
		final PWTab printerTab = window.addTab(searchImage("pref1.png"),
				"Printer");

		printerTab.add(new PWLabel("Play <i>with</i> <b>checkboxes</b>"));

		final PWGroup group = new PWGroup(false);
		group.add(new PWRow().add(new PWCheckbox("First choice", "cb4")).add(
				new PWCheckbox("Second choice", "cb5")));
		group.add(new PWRow().add(new PWCheckbox("Third choice", "cb6")).add(
				new PWCheckbox("Fourth choice", "cb7")));
		group.add(new PWRow().add(new PWCheckbox("Fifth choice", "cb8")).add(
				new PWCheckbox("Sixth choice", "cb9")));
		group.add(new PWRow().add(new PWCheckbox("Seventh choice", "cb10"))
				.add(new PWCheckbox("Eighth choice", "cb11")));
		printerTab.add(group);

		printerTab.add(new PWRow().//
				add(new PWCheckbox("Automatically check for new versions",
						"cb12").setWidth(300)).//
				add(new PWButton("Check for updates...",
						new SelectionAdapter() {
						}).setWidth(250).setAlignment(GridData.END)));

		printerTab.add(new PWSeparator());

		final PWGroup group2 = new PWGroup(false);
		group2.add(new PWRow().add(new PWLabel("Aligned checkbox")).add(
				new PWCheckbox("Bla bla bla 1", "cb13")));
		group2.add(new PWRow().add(new PWLabel("")).add(
				new PWCheckbox("Bla bla bla 2", "cb14")));
		printerTab.add(group2);
	}

	protected static void createSystemTab(final PreferenceWindow window) {
		final PWTab systemTab = window.addTab(searchImage("pref1.png"),
				"System");

		systemTab.add(new PWLabel("Rows..."));

		// systemTab.add(new PWRow().add(new PWCombo("Cache size", "cacheSize",
		// true, "128", "256", "512", "1024")).//
		// add(new PWCombo(null, "cacheSizeUnit", "Bytes", "Kilobytes",
		// "Megabytes")));

		systemTab.add(new PWRow().//
				add(new PWCombo("Display:", "display", "10", "20", "30", "40",
						"50")).//
				add(new PWLabel("per page")));

		systemTab.add(new PWSeparator());

		systemTab.add(new PWLabel("Enabled/disabled..."));

		systemTab.add(new PWCheckbox("Show information", "show").setWidth(150));
		systemTab
				.add(new PWGroup("Open Mode")
						.setEnabler(new EnabledIfTrue("show"))
						.//
						add(new PWRadio(null, "openMode", "Double click",
								"Single click")).//
						add(new PWCheckbox("Select on hover", "selectonhover")
								.setIndent(10)
								.setWidth(200)
								.setEnabler(
										new EnabledIfEquals("openMode",
												"Single click"))).//
						add(new PWCheckbox("Open when using arrow keys",
								"openarrow")
								.setIndent(10)
								.setWidth(200)
								.setEnabler(
										new EnabledIfEquals("openMode",
												"Single click"))));
	}

	private static Image searchImage(String file) {
		Bundle bundle = FrameworkUtil.getBundle(BadgeLabelProvider.class);
		URL url = FileLocator.find(bundle, new Path("icons/" + file), null);
		ImageDescriptor image = ImageDescriptor.createFromURL(url);
		return image.createImage();

	}

}