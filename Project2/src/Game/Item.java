package Game;
/* SWEN20003 Object Oriented Software Development
 * Item
 * Author: Pei Yun Sun <peiyuns>
 */

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import Save.ItemData;

public class Item {
	// Image of the item
	private final Image IMAGE;
	
	// Name of the item
	private final String NAME;
	
	// Position of the item
	private final int X_POS;
	private final int Y_POS;
	
	// Whether the item is picked up
	private boolean picked;
	
	// Location of the "items" directory
	private static final String ITEMS_PATH = "/items/";
	
	/** Constructor 
	 * @param  NAME  name of the item
	 * @param  X_POS  x coordinate of the position on the map 
	 * @param  Y_POS  y coordinate of the position on the map
	 * @param  IMAGE_PATH  path of the image
	 * @throws SlickException */
	public Item(String NAME, int X_POS, int Y_POS, String IMAGE_PATH) throws SlickException {
		IMAGE = new Image(RPG.ASSETS_PATH + ITEMS_PATH + IMAGE_PATH);
		this.NAME = NAME;
		this.X_POS = X_POS;
		this.Y_POS = Y_POS;
		
		// Not picked up initially
		picked = false;
	}
	
	/** Render the item 
	 * @param  world  world that the item is in */
	public void render(World world) {
		if(!picked) {
			// render the player depending on the camera
			world.renderObject(IMAGE, X_POS, Y_POS);
		}
	}
	
	/** Render the item to the panel 
	 * @param  xPos  x coordinate of the item on the panel
	 * @param  yPos  y coordinate of the item on the panel */
	public void renderToPanel(int xPos, int yPos) {
		IMAGE.draw(xPos, yPos);
	}
	
	/** Pick up the item */
	public void bePicked() {
		picked = true;
	}
	
	/** Check if the item is picked up
	 * @return    true if the item is picked up, otherwise false */
	public boolean pickedUp() {
		return picked;
	}
	
	/** Get the x position of the item 
	 * @return    x coordinate of position on the map */
	public int getXPos() {
		return X_POS;
	}
	
	/** Get the y position of the item 
	 * @return    y coordinate of position on the map */
	public int getYPos() {
		return Y_POS;
	}
	
	/** Get the name of the item 
	 * @return    name of the item */
	public String getName() {
		return NAME;
	}
	
	/** Update the item data 
	 * @param  data  object that contains all the data of the item */
	public void updateData(ItemData data) {
		picked = data.pickedUp();
	}
	
	/** Get the item data object 
	 * @return    object that contains all the data of the item */
	public ItemData getDataObj() {
		ItemData data = new ItemData(picked); 
		return data;
	}
}
