package in.mymoviemanager.rcp.tool.controls;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ProgressBar;

/**
 * Progress Monitor Class for bottom
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
public class UpdateStatusBarJobToolControl implements IProgressMonitor {

	@Inject
	IEventBroker eventBroker;

	@Inject
	UISynchronize synchronize;

	private ProgressBar progressBar;

	@Override
	public void beginTask(final String name, final int totalWork) {
		synchronize.syncExec(new Runnable() {
			@Override
			public void run() {
				progressBar.setMaximum(totalWork);
				progressBar.setToolTipText(name);
			}
		});
	}

	@Override
	public void done() {
	}

	@Override
	public void internalWorked(double work) {
	}

	@Override
	public boolean isCanceled() {
		return false;
	}

	@Override
	public void setCanceled(boolean value) {
	}

	@Override
	public void setTaskName(String name) {
	}

	@Override
	public void subTask(String name) {
	}

	@Override
	public void worked(final int work) {
		synchronize.syncExec(new Runnable() {
			@Override
			public void run() {
				progressBar.setSelection(progressBar.getSelection() + work);
			}
		});
	}

	@PostConstruct
	public void createControls(Composite parent) {
		progressBar = new ProgressBar(parent, SWT.NONE);
		// progressBar.setBounds(1, 2, 3, 4);
		progressBar.setVisible(false);
	}
}
