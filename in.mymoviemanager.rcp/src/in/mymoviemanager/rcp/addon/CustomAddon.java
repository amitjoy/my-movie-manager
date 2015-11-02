package in.mymoviemanager.rcp.addon;

import javax.inject.Inject;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;

/**
 * Custom Model Addon
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
public class CustomAddon {
	@Inject
	IEventBroker eventBroker;
	@Inject
	IEclipseContext context;

	@PostConstruct
	void hookListeners() {
		context.set("developer", "AMIT KUMAR MONDAL");
		System.out.println("Fired in custom addon");
	}

	@PreDestroy
	void unhookListeners() {
	}
}