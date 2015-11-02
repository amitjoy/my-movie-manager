package in.mymoviemanager.rcp.handlers;

import in.mymoviemanager.events.EventConstants;
import in.mymoviemanager.rcp.model.Tag;
import in.mymoviemanager.rcp.model.VideoFile;

import java.security.NoSuchAlgorithmException;

import javax.inject.Inject;
import javax.xml.bind.JAXBException;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.mihalis.opal.opalDialog.Dialog;

/**
 * Remove badges handler
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
public class RemoveTagHandler {

	@Inject
	IEventBroker broker;

	@Inject
	ESelectionService selectionService;

	@Execute
	public void execute(IEclipseContext context)
			throws NoSuchAlgorithmException, JAXBException {
		final String tag = (String) context.get(Tag.class.getName());
		Tag tag2 = new Tag();
		tag2.setName(tag);
		VideoFile file = (VideoFile) context.get(VideoFile.class);
		if (tag != null)
			if (Dialog.isConfirmed("Delete Badge",
					"Are you sure you want to delete " + tag + " badge from "
							+ file.getFileNameWithoutExtension() + "?"))
				broker.send(EventConstants.REMOVE_TAG, tag2);

	}
}