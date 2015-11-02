package in.mymoviemanager.rcp.views.listenerProvider;

import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Button;
/**
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 *
 */
public class TagTextKeyListener implements KeyListener {


	private Button btnNewButton;
	
	public TagTextKeyListener(Button btn){
		this.btnNewButton = btn;
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		btnNewButton.setEnabled(true);

	}

}
