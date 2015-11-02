package in.mymoviemanager.imdb.services;

import in.mymoviemanager.imdb.model.IMDBDocumentList;

/**
 * Service Interface for IMDB
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
public interface IMovieImdb {

	public IMDBDocumentList getMovieInfo(String name);
	public boolean checkInternetConnection();
}
