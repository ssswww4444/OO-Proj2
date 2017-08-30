package Save;
/* SWEN20003 Object Oriented Software Development
 * Saving/Loading handler
 * Author: Pei Yun Sun <peiyuns>
 */

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import Character.Player;
import Character.Unit;
import Game.Item;
import Game.RPG;

public class SaveHandler{
	
	// Save world data
	private WorldData worldData;
	
	// The dat file path
	private final String DAT_FILE_PATH;
	
	/** Constructor
	 * @param  DAT_FILE_PATH  path of the '.dat' file for saving/loading game */
	public SaveHandler(String DAT_FILE_PATH) {
		this.DAT_FILE_PATH = DAT_FILE_PATH;
	}
	
	/** Save the game 
	 * @param  item_vec  vector of the items
	 * @param  unit_vec  vector of the units
	 * @throws IOException */
 	public void save(Vector<Item> item_vec, Vector<Unit> unit_vec) throws IOException {
 		// Create a new world data
 		worldData = new WorldData();
 		
        // Put the data object into the itemData vector in world data
 		for(Item item : item_vec)
        	worldData.getItemDataVec().addElement(item.getDataObj());
 		
 		// Put the object data into the unitData vector
 		for(Unit unit : unit_vec)
         	worldData.getUnitDataVec().addElement(unit.getDataObj());
 		
 		// Output streams
 		FileOutputStream fo = new FileOutputStream(RPG.ASSETS_PATH + DAT_FILE_PATH);   
 		ObjectOutputStream so = new ObjectOutputStream(fo); 
 		
 		// Write the world object to the file
 		so.writeObject(worldData);
 		
 		// Close the output streams
 		so.close();
 		fo.close();
 	}
	
	/** Load the game
	 * @param  item_vec  vector of the items
	 * @param  unit_vec  vector of the units
	 * @param  player  player of the current world
	 * @throws IOException
	 * @throws ClassNotFoundException */
 	public void load(Vector<Item> item_vec, Vector<Unit> unit_vec, Player player) 
 			throws IOException, ClassNotFoundException {
 		// Input streams
 		FileInputStream fi = new FileInputStream(RPG.ASSETS_PATH + DAT_FILE_PATH);   
        ObjectInputStream si = new ObjectInputStream(fi);
 
        // Read the world object from the file
		worldData = (WorldData) si.readObject();
		
		// Close the input streams
        si.close();
        fi.close();
        
        // Set the object data from the unitData vector
	    for (int i=0; i<worldData.getUnitDataVec().size(); i++)
	    	unit_vec.elementAt(i).updateData(worldData.getUnitDataVec().elementAt(i));
	    
	    // Clear the player's inventory
	    player.clearInvent();
        
        // Set the object data from the itemData vector
        for (int i=0; i<worldData.getItemDataVec().size(); i++) {
        	
        	// Update the object data
        	item_vec.elementAt(i).updateData(worldData.getItemDataVec().elementAt(i));
        	
        	// Update the player's inventory
        	if(item_vec.elementAt(i).pickedUp())
        		player.loadInvent(item_vec.elementAt(i));
        }
 	} 
 	
 	/** Render the save/load message, 'save' true for saving, false for loading
 	 * @param  g The Slick graphics object, used for drawing
 	 * @param  save  true if saving, false if loading
 	 */
 	public void renderMessage(Graphics g, boolean save) {
 		
 		// Colors
 		Color white = new Color(1.0f, 1.0f, 1.0f);
 		Color black = new Color(0.0f, 0.0f, 0.0f);
 		
 		// Saved or loaded message
 		String message;
 		
 		if(save)
 			message = "Game Saved";
 		else
 			message = "Game Loaded";
 			
 		// Position of the rectangle (background)
 		int bar_width = g.getFont().getWidth(message) + 20;
 		int bar_height = 20;
 		int bar_x = RPG.SCREEN_WIDTH/2 - bar_width/2;
 		int bar_y = RPG.SCREEN_HEIGHT/3;
 	        
 	    // Draw the rectangle (Background)
 	    g.setColor(black);
 	    g.fillRect(bar_x, bar_y, bar_width, bar_height);

 	    // Position of the message
 	    int text_x = bar_x + 10;
 	    int text_y = bar_y;      
 	        
 	    // Draw message
 	    g.setColor(white);
 	    g.drawString(message, text_x, text_y);
 	}
}
