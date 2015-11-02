package in.mymoviemanager.imdb.services.impl;

import in.mymoviemanager.imdb.model.IMDBDocumentList;
import in.mymoviemanager.imdb.services.IMovieImdb;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URLEncoder;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.osgi.service.prefs.Preferences;
import org.restlet.data.Protocol;
import org.restlet.data.Status;
import org.restlet.resource.ClientResource;

/**
 * Service Implementation for IMDB
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
public class MovieImdb implements IMovieImdb {

	private String username;
	private String isProxyEnabled;
	private String proxy_address;
	private String proxy_port;
	private String password;

	/**
	 * Returns Object from XML Response of IMDB API
	 */
	@Override
	public IMDBDocumentList getMovieInfo(String movieName) {
		String url = "http://mymovieapi.com/?title="
				+ URLEncoder.encode(movieName) + "&type=xml";
		File file = null;
		ClientResource client = null;
		try {
			file = File.createTempFile("imdbDetails", ".moviemanager");
			client = new ClientResource(url);
			client.setProtocol(Protocol.HTTP);
			client.setRetryOnError(false);
			if (client.getStatus() == Status.SUCCESS_OK) {
				client.get().write(new FileOutputStream(file));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		IMDBDocumentList repo = null;

		try {
			JAXBContext jaxbContext = JAXBContext
					.newInstance(IMDBDocumentList.class);

			Unmarshaller jaxbUnmarshaller;
			jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			repo = (IMDBDocumentList) jaxbUnmarshaller.unmarshal(file);
			file.delete();
		} catch (JAXBException e) {
			return null;
		}
		return repo;
	}

	/**
	 * Checking preference if proxy is needed
	 */
	private void checkPreferenceStore() {
		Preferences preferences = InstanceScope.INSTANCE
				.getNode("in.mymoviemanager.rcp");

		username = preferences.get("username", "").trim();
		isProxyEnabled = preferences.get("isProxy", "false");
		proxy_address = preferences.get("proxyURL", "").trim();
		proxy_port = preferences.get("port", "").trim();
		password = preferences.get("password", "").trim();
	}

	/**
	 * Check if Internet Connection is present or not
	 */
	@Override
	public boolean checkInternetConnection() {
		checkPreferenceStore();
		if (Boolean.parseBoolean(isProxyEnabled))
			if (proxy_address != null && proxy_port != null) {
				setProxy(proxy_address, proxy_port, username, password);
			}
		boolean flag = false;
		ClientResource client = null;
		try {
			client = new ClientResource(
					"http://mymovieapi.com/?title=seven&type=xml");
			flag = (client.getStatus() == Status.SUCCESS_OK);
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	private static void setProxy(String proxyAddress, String proxyPort,
			String username, String password) {
		System.setProperty("http.proxyHost", proxyAddress);
		System.setProperty("http.proxyPort", proxyPort);
		System.setProperty("http.proxyUser", username);
		System.setProperty("http.proxyPassword", password);
	}
}
