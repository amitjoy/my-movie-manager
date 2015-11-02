package in.mymoviemanager.xml.convert;

import in.mymoviemanager.rcp.filerepository.VideoFileRepository;
import in.mymoviemanager.security.MovieProjectNameEncryption;
import in.mymoviemanager.security.ProjectFileCipher;
import in.mymoviemanager.security.key.KEY;
import in.mymoviemanager.xml.service.IXMLManipulation;

import java.io.File;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.eclipse.e4.core.di.annotations.Creatable;

@Creatable
public class XMLManipulation implements IXMLManipulation {

	File file = null;

	// Flag = Determines whether to call rescan or new movie folder
	@Override
	public void convertToXml(Object object, String location, int flag)
			throws JAXBException, NoSuchAlgorithmException {
		MovieProjectNameEncryption encryption = new MovieProjectNameEncryption();
		String fileName = encryption.encrypt(location);
		if (flag == 1) {
			file = new File(location + File.separatorChar + fileName
					+ ".moviemanager");
		} else if (flag == 2)
			file = new File(location + File.separatorChar + fileName
					+ "temp.moviemanager");
		else
			throw new IllegalArgumentException();
		// if (file.exists())
		// file.delete();

		if (object instanceof VideoFileRepository) {
			JAXBContext jaxbContext = JAXBContext
					.newInstance(VideoFileRepository.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			VideoFileRepository repository = (VideoFileRepository) object;

			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(repository, file);
			encrypt(file);
		}
	}

	private File encrypt(File decryptedFile) {
		KEY key = new KEY();
		key.initKey(decryptedFile);
		ProjectFileCipher cipher = new ProjectFileCipher(decryptedFile, key, 1);
		return cipher.EncryptFile();
	}

	@Override
	public boolean deleteProjectFile() {
		if (file.delete())
			return true;
		return false;
	}

	// Flag = Determines whether to call rescan or new movie folder
	@Override
	public VideoFileRepository convertToModel(String location, int flag)
			throws JAXBException, NoSuchAlgorithmException {
		MovieProjectNameEncryption encryption = new MovieProjectNameEncryption();
		String fileName = encryption.encrypt(location);
		File file = null;
		if (flag == 1) {
			file = decrypt(new File(location + File.separatorChar + fileName
					+ ".moviemanager"));
		} else if (flag == 2)
			file = decrypt(new File(location + File.separatorChar + fileName
					+ "temp.moviemanager"));
		else
			throw new IllegalArgumentException();
		JAXBContext jaxbContext = JAXBContext
				.newInstance(VideoFileRepository.class);

		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		VideoFileRepository repo = (VideoFileRepository) jaxbUnmarshaller
				.unmarshal(file);
		file.delete();
		return repo;
	}

	private File decrypt(File encryptedFile) {
		ProjectFileCipher cipher = new ProjectFileCipher(encryptedFile, null, 2);
		File newFile = cipher.DecryptFile();
		return newFile;
	}

}
