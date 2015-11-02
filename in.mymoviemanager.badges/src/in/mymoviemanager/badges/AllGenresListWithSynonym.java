package in.mymoviemanager.badges;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * All Genres List with probable synonyms
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */

public class AllGenresListWithSynonym {

	static Badges badge = null;

	public static void initialize() {

		Genre a = new Genre();
		a.setName("Action");
		{
			a.setImageFileName("Action");
			Synonym s = new Synonym();
			s.setName("Activeness");
			a.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Drive");
			a.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Process");
			a.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("State");
			a.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Work");
			a.getSynonyms().add(s);
		}

		Genre a1 = new Genre();
		a1.setName("Adventure");
		{
			a1.setImageFileName("Adventure");
			Synonym s = new Synonym();
			s.setName("Escapade");
			a1.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Gamble");
			a1.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Chance");
			a1.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Risk");
			a1.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Hazard");
			a1.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Attempt");
			a1.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Venture");
			a1.getSynonyms().add(s);
		}

		Genre a2 = new Genre();
		a2.setName("Animation");
		{
			a2.setImageFileName("Animation");
			Synonym s = new Synonym();
			s.setName("Motion-picture");
			a2.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Anime");
			a2.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Cartoon");
			a2.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Children");
			a2.getSynonyms().add(s);
		}

		Genre a3 = new Genre();
		a3.setName("Biography");
		{
			a3.setImageFileName("Biography");
			Synonym s = new Synonym();
			s.setName("Bio");
			a3.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Journal");
			a3.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Letter");
			a3.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Autobiography");
			a3.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Experience");
			a3.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Memoir");
			a3.getSynonyms().add(s);
		}

		Genre a4 = new Genre();
		a4.setName("Comedy");
		{
			a4.setImageFileName("Comedy");
			Synonym s = new Synonym();
			s.setName("Comic");
			a4.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Fun");
			a4.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Funny");
			a4.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Hilarious");
			a4.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Humor");
			a4.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Joke");
			a4.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Satire");
			a4.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Irony");
			a4.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Witty");
			a4.getSynonyms().add(s);
		}

		Genre a5 = new Genre();
		a5.setName("Crime");
		{
			a5.setImageFileName("Crime");
			Synonym s = new Synonym();
			s.setName("Atrocity");
			a5.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Breach");
			a5.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Felony");
			a5.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Outrage");
			a5.getSynonyms().add(s);
		}

		Genre a6 = new Genre();
		a6.setName("Documentary");
		{
			a6.setImageFileName("Documentary");
			Synonym s = new Synonym();
			s.setName("narrative");
			a6.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Document");
			a6.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Report");
			a6.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Documentation");
			a6.getSynonyms().add(s);
		}

		Genre a7 = new Genre();
		a7.setName("Drama");
		{
			a7.setImageFileName("Drama");
			Synonym s = new Synonym();
			s.setName("Climax");
			a7.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Melodrama");
			a7.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Play");
			a7.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Show");
			a7.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Theatre");
			a7.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Act");
			a7.getSynonyms().add(s);
		}

		Genre a8 = new Genre();
		a8.setName("Family");
		{
			a8.setImageFileName("Family");
			Synonym s = new Synonym();
			s.setName("Family-movie");
			a8.getSynonyms().add(s);
		}

		Genre a9 = new Genre();
		a9.setName("Fantasy");
		{
			a9.setImageFileName("Fantasy");
			Synonym s = new Synonym();
			s.setName("Creativity");
			a9.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Illusion");
			a9.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Dream");
			a9.getSynonyms().add(s);
		}

		Genre a10 = new Genre();
		a10.setName("Game show");
		{
			a10.setImageFileName("");
			Synonym s = new Synonym();
			s.setName("Quiz");
			a10.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Question answer");
			a10.getSynonyms().add(s);
		}

		Genre a11 = new Genre();
		a11.setName("History");
		{
			a11.setImageFileName("History");
			Synonym s = new Synonym();
			s.setName("Ancient");
			a11.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Past");
			a11.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Historical");
			a11.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Old");
			a11.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Golden days");
			a11.getSynonyms().add(s);
		}

		Genre a12 = new Genre();
		a12.setName("Horror");
		{
			a12.setImageFileName("Horror");
			Synonym s = new Synonym();
			s.setName("Fear");
			a12.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Revulsion");
			a12.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Dread");
			a12.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Terror");
			a12.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Dead");
			a12.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Panic");
			a12.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Evil");
			a12.getSynonyms().add(s);
		}

		Genre a13 = new Genre();
		a13.setName("Musical");
		{
			a13.setImageFileName("Musical");
			Synonym s = new Synonym();
			s.setName("Song");
			a13.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Tune");
			a13.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Music");
			a13.getSynonyms().add(s);
		}

		Genre a14 = new Genre();
		a14.setName("Mystery");
		{
			a14.setImageFileName("Mystery");
			Synonym s = new Synonym();
			s.setName("Puzzle");
			a14.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Secret");
			a14.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Treasure");
			a14.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Brainteaser");
			a14.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Crux");
			a14.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Cryptic");
			a14.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Problem");
			a14.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Riddle");
			a14.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Sphinx");
			a14.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Twister");
			a14.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Paradox");
			a14.getSynonyms().add(s);
		}

		Genre a15 = new Genre();
		a15.setName("News");
		{
			a15.setImageFileName("News");
			Synonym s = new Synonym();
			s.setName("Bulletin");
			a15.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Knowledge");
			a15.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Intelligence");
			a15.getSynonyms().add(s);
		}

		Genre a16 = new Genre();
		a16.setName("Dance");
		{
			a16.setImageFileName("Dance");
			Synonym s = new Synonym();
			s.setName("Party");
			a16.getSynonyms().add(s);
		}

		Genre a17 = new Genre();
		a17.setName("TV Shows");
		{
			a17.setImageFileName("TV-Shows");
			Synonym s = new Synonym();
			s.setName("TV");
			a17.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Entertainment");
			a17.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Television");
			a17.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Idiot box");
			a17.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Performance");
			a17.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Tube");
			a17.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("TV series");
			a17.getSynonyms().add(s);
		}

		Genre a18 = new Genre();
		a18.setName("Romance");
		{
			a18.setImageFileName("Romance");
			Synonym s = new Synonym();
			s.setName("Love");
			a18.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Love Story");
			a18.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Affair");
			a18.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("True Love");
			a18.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Romantic");
			a18.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Flirt");
			a18.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Passion");
			a18.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Fling");
			a18.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Relationship");
			a18.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Sentiment");
			a18.getSynonyms().add(s);
		}

		Genre a19 = new Genre();
		a19.setName("Sci-Fi");
		{
			a19.setImageFileName("Sci-Fi");
			Synonym s = new Synonym();
			s.setName("Science");
			a19.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Scientific");
			a19.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Experimental");
			a19.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Systemetic");
			a19.getSynonyms().add(s);
		}

		Genre a20 = new Genre();
		a20.setName("Sport");
		{
			a20.setImageFileName("Sport");
			Synonym s = new Synonym();
			s.setName("Game");
			a20.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Picnic");
			a20.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Athletic");
			a20.getSynonyms().add(s);
		}

		Genre a21 = new Genre();
		a21.setName("Thriller");
		{
			a21.setImageFileName("Thriller");
			Synonym s = new Synonym();
			s.setName("Suspense");
			a21.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Shocker");
			a21.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Thrilling");
			a21.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Anxiety");
			a21.getSynonyms().add(s);
		}

		Genre a22 = new Genre();
		a22.setName("War");
		{
			a22.setImageFileName("War");
			Synonym s = new Synonym();
			s.setName("Battle");
			a22.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Combat");
			a22.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Contest");
			a22.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Fight");
			a22.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Conflict");
			a22.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Strike");
			a22.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Struggle");
			a22.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Warfare");
			a22.getSynonyms().add(s);
		}

		Genre a23 = new Genre();
		a23.setName("Western");
		{
			a23.setImageFileName("Western");
			Synonym s = new Synonym();
			s.setName("Cowboy");
			a23.getSynonyms().add(s);
		}

		Genre a24 = new Genre();
		a24.setName("Tragedy");
		{
			a24.setImageFileName("Tragedy");
			Synonym s = new Synonym();
			s.setName("Disaster");
			a24.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Rotten");
			a24.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Calamity");
			a24.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Cataclysm");
			a24.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Curse");
			a24.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Plague");
			a24.getSynonyms().add(s);
		}
		Genre a25 = new Genre();
		a25.setName("Trailer");
		{
			a25.setImageFileName("Trailers");
			Synonym s = new Synonym();
			s.setName("Preview");
			a25.getSynonyms().add(s);
		}
		Genre a26 = new Genre();
		a26.setName("XXX");
		{
			a26.setImageFileName("XXX");
			Synonym s = new Synonym();
			s.setName("Porn");
			a26.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Pornography");
			a26.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Adult");
			a26.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Sex");
			a26.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("BF");
			a26.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Blue Film");
			a26.getSynonyms().add(s);
		}
		Genre a27 = new Genre();
		a27.setName("Favourites");
		{
			a27.setImageFileName("Favourites");
			Synonym s = new Synonym();
			s.setName("Preferences");
			a27.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Like");
			a27.getSynonyms().add(s);
		}
		Genre a28 = new Genre();
		a28.setName("Watched");
		{
			a28.setImageFileName("watched");
			Synonym s = new Synonym();
			s.setName("Viewed");
			a28.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Seen");
			a28.getSynonyms().add(s);
		}
		Genre a29 = new Genre();
		a29.setName("Unwatched");
		{
			a29.setImageFileName("unwatched");
			Synonym s = new Synonym();
			s.setName("Not Viewed");
			a29.getSynonyms().add(s);
		}
		{
			Synonym s = new Synonym();
			s.setName("Unseen");
			a29.getSynonyms().add(s);
		}

		badge = new Badges();
		ArrayList<Genre> al = new ArrayList<Genre>();
		al.add(a29);
		al.add(a28);
		al.add(a27);
		al.add(a26);
		al.add(a25);
		al.add(a24);
		al.add(a23);
		al.add(a22);
		al.add(a21);
		al.add(a20);
		al.add(a19);
		al.add(a18);
		al.add(a17);
		al.add(a16);
		al.add(a15);
		al.add(a14);
		al.add(a13);
		al.add(a12);
		al.add(a11);
		al.add(a10);
		al.add(a9);
		al.add(a8);
		al.add(a7);
		al.add(a6);
		al.add(a5);
		al.add(a4);
		al.add(a3);
		al.add(a2);
		al.add(a1);
		al.add(a);
		badge.setGenres(al);

	}

	/**
	 * Getter for object returned by marshalling genres.moviemanager
	 * 
	 * @return
	 */
	public static Badges getBadges() {
		initialize();
		try {
			return badge;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] arg) throws JAXBException {
		try {
			JAXBContext context = JAXBContext.newInstance(Badges.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(getBadges(), new File("genres.moviemanager"));
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns object from genres.moviemanager file
	 * 
	 * @return
	 * @throws JAXBException
	 * @throws MalformedURLException
	 * @throws URISyntaxException
	 */
	private Badges getSynonymsFromModel() throws JAXBException,
			MalformedURLException, URISyntaxException {
		JAXBContext jaxbContext = JAXBContext.newInstance(Badges.class);
		URL resource = getClass().getResource("genres.moviemanager").toURI()
				.toURL();
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Badges repo = (Badges) jaxbUnmarshaller.unmarshal(resource);
		return repo;
	}

}
