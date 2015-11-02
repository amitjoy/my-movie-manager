package in.mymovemanager.imdb.service.annotation.internal;

import in.mymoviemanager.imdb.services.IMovieImdb;
import in.mymoviemanager.imdb.services.impl.MovieImdb;

import org.eclipse.e4.core.di.suppliers.ExtendedObjectSupplier;
import org.eclipse.e4.core.di.suppliers.IObjectDescriptor;
import org.eclipse.e4.core.di.suppliers.IRequestor;

/**
 * Intermediary class for feeding imdb annotation to Eclipse 4 DI Framework
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
public class ImdbObjectSupplier extends ExtendedObjectSupplier {
	@Override
	public Object get(IObjectDescriptor descriptor, IRequestor requestor,
			boolean track, boolean group) {

		IMovieImdb imdb = new MovieImdb();
		return imdb;
	}
}
