package in.mymoviemanager.rcp.views.listenerProvider;

import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.*;

/**
 * Text Box Input Validator for Numbers
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
public class TextBoxNumberFormatterListener implements VerifyListener {

	@Override
	public void verifyText(VerifyEvent event) {
		event.doit = false;

		char myChar = event.character;
		String text = ((Text) event.widget).getText();

		if (Character.isDigit(myChar))
			event.doit = true;

		if (myChar == '\b') {
			event.doit = true;

		}

	}
}
