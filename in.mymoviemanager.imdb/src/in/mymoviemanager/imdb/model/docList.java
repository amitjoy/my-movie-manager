package in.mymoviemanager.imdb.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class docList {

	String rating;
	String rating_count;
	String year;
	genres genres;
	String rated;
	String title;
	String imdb_url;
	Directors directors;
	Writers writers;
	Actors actors;
	String plot_simple;
	String type;
	Poster poster;
	
	String imdb_id;
	List<Name> also_known_as;
	List<Name> language;
	List<Name> country;
	String release_date;
	String filming_locations;
	List<Name> runtime;
	public String getRating() {
		return rating;
	}
	public void setLanguage(List<Name> language) {
		this.language = language;
	}
	public void setCountry(List<Name> country) {
		this.country = country;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getRating_count() {
		return rating_count;
	}
	public void setRating_count(String rating_count) {
		this.rating_count = rating_count;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public genres getGenres() {
		return genres;
	}
	public void setGenres(genres genres) {
		this.genres = genres;
	}
	public String getRated() {
		return rated;
	}
	public void setRated(String rated) {
		this.rated = rated;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImdb_url() {
		return imdb_url;
	}
	public void setImdb_url(String imdb_url) {
		this.imdb_url = imdb_url;
	}
	public Directors getDirectors() {
		return directors;
	}
	public void setDirectors(Directors directors) {
		this.directors = directors;
	}
	public Writers getWriters() {
		return writers;
	}
	public void setWriters(Writers writers) {
		this.writers = writers;
	}
	public Actors getActors() {
		return actors;
	}
	public void setActors(Actors actors) {
		this.actors = actors;
	}
	public String getPlot_simple() {
		return plot_simple;
	}
	public void setPlot_simple(String plot_simple) {
		this.plot_simple = plot_simple;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Poster getPoster() {
		return poster;
	}
	public void setPoster(Poster poster) {
		this.poster = poster;
	}
	public String getImdb_id() {
		return imdb_id;
	}
	public void setImdb_id(String imdb_id) {
		this.imdb_id = imdb_id;
	}
	public List<Name> getAlso_known_as() {
		return also_known_as;
	}
	public void setAlso_known_as(List<Name> also_known_as) {
		this.also_known_as = also_known_as;
	}
	
	public List<Name> getLanguage() {
		return language;
	}
	public List<Name> getCountry() {
		return country;
	}
	public String getRelease_date() {
		return release_date;
	}
	public void setRelease_date(String release_date) {
		this.release_date = release_date;
	}
	public String getFilming_locations() {
		return filming_locations;
	}
	public void setFilming_locations(String filming_locations) {
		this.filming_locations = filming_locations;
	}
	public List<Name> getRuntime() {
		return runtime;
	}
	public void setRuntime(List<Name> runtime) {
		this.runtime = runtime;
	}
	
	
}
