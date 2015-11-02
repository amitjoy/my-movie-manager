package in.mymoviemanager.xml.service;

import in.mymoviemanager.rcp.filerepository.VideoFileRepository;

import java.security.NoSuchAlgorithmException;

import javax.xml.bind.JAXBException;

/**
 * XML Manipulation Service
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
public interface IXMLManipulation {

	public void convertToXml(Object object, String location, int flag)
			throws JAXBException, NoSuchAlgorithmException;

	public VideoFileRepository convertToModel(String location, int flag)
			throws JAXBException, NoSuchAlgorithmException;
	
	public boolean deleteProjectFile();
}
