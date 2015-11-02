package in.mymoviemanager.imdb.model;

import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;


public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
	IMDBDocumentList IMDBDocumentList = new IMDBDocumentList();
		
		docList item = new docList();
		item.setRating("6.8");
		item.setRating_count("47123");
		item.setYear("2012");
		{
			genres dirs = new genres();	
			ArrayList<String> directors = new ArrayList<String>();
			directors.add("Action");
			directors.add("Adventure");
			directors.add("Mystery");
			directors.add("Thriller");
			dirs.setItem(directors);
			item.setGenres(dirs);
		}
		item.setRated("PG_13");
		item.setTitle("The Bourne Legacy");
		item.setImdb_url("http://www.item.com/title/tt1194173/");
		item.setImdb_id("tt1194173");
			{
			Directors dirs = new Directors();	
			ArrayList<String> directors = new ArrayList<String>();
			directors.add("Tony Gilroy");
			dirs.setItem(directors);
			item.setDirectors(dirs);	
			}
			{
				Writers dirs = new Writers();	
				ArrayList<String> directors = new ArrayList<String>();
				directors.add("Tony Gilroy");
				directors.add("Dan Gilroy");
				dirs.setItem(directors);
				item.setWriters(dirs);
			}
			{
				Actors dirs = new  Actors();	
				ArrayList<String> directors = new ArrayList<String>();
				directors.add("Jeremy Renner");
				directors.add("Scott Glenn");
				directors.add("Stacy Keach");
				directors.add("Edward Norton");
				directors.add("Donna Murphy");
				directors.add("Michael Chernus");
				directors.add("Corey Stoll");
				directors.add("Alice Gainer");
				directors.add("Prue Lewarne");
				directors.add("Howard Leader");
				directors.add("James Joseph O'Neil");
				directors.add("Rachel Weisz");
				directors.add("Tony Guida");
				directors.add("Sonnie Brown");
				dirs.setItem(directors);
				item.setActors(dirs);
			}
		item.setPlot_simple("An expansion of the universe from Robert " +
				"Ludlum's novels, centered on a new hero whose stakes have been " +
				"triggered by the events of the previous three films.");
		item.setType("M");
		Poster poster = new Poster();
		ArrayList<String> list = new ArrayList<String>();
		
			list.add("http://ia.media-item.com/images/M/" +
				"MV5BMTc4Njk3MDM1Nl5BMl5BanBnXkFtZTcwODgyOTMxOA@@._V1._SY317_.jpg");
		poster.setCover(list);		
		item.setPoster(poster);
			ArrayList<Name> also_known_as = new ArrayList<Name>();
			{
				Name name = new Name();
				name.setItem("El legado Bourne");
				also_known_as.add(name);
			}
		item.setAlso_known_as(also_known_as);
		ArrayList<Name> language = new ArrayList<Name>();
		{
			Name name = new Name();
			name.setItem("English");
			language.add(name);
		}
		item.setLanguage(language);
		ArrayList<Name> country = new ArrayList<Name>();
		{
			Name name = new Name();
			name.setItem("USA");
			country.add(name);
		}
		item.setCountry(country);
		item.setRelease_date("20121025");
		item.setFilming_locations("Kaufman Astoria Studios - 3412 36th Street, " +
				"Astoria, Queens, New York City, New York, USA");
		ArrayList<Name> runtime = new ArrayList<Name>();
		{
			Name name = new Name();
			name.setItem("135 min");
			runtime.add(name);
		}
		item.setRuntime(runtime);
	IMDBDocumentList.setItem(item);
		try {
			JAXBContext context = JAXBContext.newInstance(IMDBDocumentList.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(IMDBDocumentList, System.out);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
