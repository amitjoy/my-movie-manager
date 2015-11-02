package in.mymoviemanager.events;

/**
 * All Application Related Events
 * 
 * @author AMIT KUMAR MONDAL (admin@amitinside.com)
 * 
 */
public interface EventConstants {

	/**
	 * Event is Fired When New Movie Folder is selected ie which doesn't have
	 * any previous project file
	 */
	public static final String NEW_MOVIE_FOLDER = "movie/new/folder";
	/**
	 * Event is Fired When Existing Movie Folder is selected ie which does have
	 * a .moviemanager project file inside
	 */
	public static final String EXISTING_MOVIE_FOLDER = "movie/exist/folder";
	/**
	 * Event is Fired when existing movie folder is rescanned
	 */
	public static final String RESCAN_FOLDER = "movie/rescan/folder";
	/**
	 * Event is Fired when user click on movie name in Movies Collection View
	 * and Movie File Details (Size, Location) are returned
	 */
	public static final String MOVIE_DETAILS_AT_MOUSE_CLICK = "movie/details/view";
	/**
	 * Event is Fired when user click on movie name in Movies Collection View
	 * and assigned badges are returned
	 */
	public static final String TAG_DETAILS_AT_MOUSE_CLICK = "movie/details/tag";
	/**
	 * Event is Fired when user wants to assign new badge to the selected movie
	 */
	public static final String NEW_TAG_ADD = "movie/tag/add";
	/**
	 * Event is Fired when new badge is assigned successfully to the selected
	 * movie and saved to project file
	 */
	public static final String TAG_SAVED_IN_XML = "movie/tag/saved";
	/**
	 * Event is Fired when user removes assigned badge from the selected movie
	 */
	public static final String TAG_REMOVED = "movie/tag/removed";
	/**
	 * Event is Fired after assigned badge is removed and BADGE BROWSER view
	 * needs to be updated along with
	 */
	public static final String TAG_REMOVED_AND_UPDATE_TAG_BROWSER = "movie/tag/browser/update/on/remove";
	/**
	 * Event is Fired after assigning badge and BADGE BROWSER view needs to be
	 * updated along with
	 */
	public static final String TAG_ADDED_AND_UPDATE_TAG_BROWSER = "movie/tag/browser/update/on/add";
	/**
	 * Event is Fired when user removes a badge from the selected movie
	 */
	public static final String REMOVE_TAG = "movie/tag/remove";
	/**
	 * Event is Fired when user deals with badges and specific badge view need
	 * to be updated along with
	 */
	public static final String UPDATE_SPECIFIC_BADGE_VIEW = "movie/badge/view/update";
	/**
	 * Event is Fired when user click on movie name in Movies Collection View
	 * and subtitle detail is returned
	 */
	public static final String SUBTITLES_DETAILS_AT_MOUSE_CLICK = "movie/details/subs";
	/**
	 * Event is Fired when user renames selected movie file name
	 */
	public static final String RENAME_FILE = "movie/file/rename";
	/**
	 * Event is Fired BADGE VIEW needs to be updated
	 */
	public static final String REFRESH_BADGE_VIEW = "movie/badge/fresh/list";
	/**
	 * Event is Fired BADGE VIEW needs to be updated once the rename action of a
	 * movie succeeds
	 */
	public static final String REFRESH_BADGE_VIEW_ON_RENAME = "movie/badge/refresh/on/rename";
	/**
	 * Event is Fired when user deletes the movie
	 */
	public static final String DELETE_MOVIE = "movie/file/delete";
	/**
	 * Event is Fired when user views imdb details
	 */
	public static final String IMDB_DETAILS = "movie/imdb/details";
	/**
	 * Event is Fired for statusbar tool control
	 */
	public static final String UPDATE_PROGRESS_BAR = "movie/imdb/details";
	/**
	 * Event is Fired when user changes a theme
	 */
	public static final String CHANGE_THEME = "application/theme/change";
	/**
	 * Event is Fired when internet connectivity is checked and it is found to
	 * be inactive and moreover it incorporates imdb not to us any library
	 */
	public static final String NO_INTERNET_CONNECTION_FOR_IMDB = "application/no/internet";
	/**
	 * Event is Fired when user views imdb details and genre gets automatically
	 * added to the selected movie as a badge
	 */
	public static final String NEW_TAG_ADD_FROM_IMDB = "movie/genre/tag/add";
}
