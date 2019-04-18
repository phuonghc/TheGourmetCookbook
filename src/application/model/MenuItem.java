package application.model;

/**
 * This class contains attributes and behaviors related to object MenuItem.
 * 
 * @author Sayontani Ray (pek684)
 * UTSA CS 3443 - Team Project
 * Spring 2019
 */
public class MenuItem {
	
	// Attributes or properties of a MenuItem
	private String image;
	private String id;
	private String title;
	
	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}
	
	/**
	 * @param image the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

}
