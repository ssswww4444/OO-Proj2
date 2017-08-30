package Character;
/* SWEN20003 Object Oriented Software Development
 * Player
 * Author: Pei Yun Sun <peiyuns>
 */

import java.util.Vector;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import Game.Camera;
import Game.Item;
import Game.RPG;
import Game.World;

public class Player extends Unit{
	
	// Moving speed of the player
	private static final double SPEED = 0.25;
	
	// Inventory
	private Vector<Item> inventory;
	
	// Path of the status panel image
	private static final String PANEL_IMAGE_PATH = "/panel.png";
	
	// Image for the panel
	Image PANEL_IMAGE;
	
	/** Constructor 
	 * @param  NAME  name of the player
	 * @param  IMAGE_PATH  path of the image
	 * @param  maxHp  maximum hit point (HP) 
	 * @param  damage  damage of attacking
	 * @param  maxCd  maximum cooldown (CD)
	 * @param  xPos  x coordinate of the position on the map 
	 * @param  yPos  y coordinate of the position on the map
	 * @throws SlickException */
	public Player(String NAME, String IMAGE_PATH, int maxHp, int damage, 
			int maxCd, int xPos, int yPos) throws SlickException {
		
		// Initialize the player's status
		super(NAME, IMAGE_PATH, maxHp, damage, maxCd, xPos, yPos);
    	
    	// Create a new inventory
    	inventory = new Vector<Item>();
    	
    	// Create the image for the panel
    	PANEL_IMAGE = new Image(RPG.ASSETS_PATH + PANEL_IMAGE_PATH);
    	
	}

	/** Update the player
	 * @param  delta  number of milliseconds in this frame
	 * @param  world  world that the player is in */
	@Override
	public void update(int delta, World world) {
        
        // Reduce CD
        cd -= delta;
        if(cd < 0)
        	cd = 0;
		
		// Check if the player is dead
    	if(hp <= 0)
    		die();
	}
	
	/** Render the player
	 * @param  world  world that the player is in 
	 * @param  camera  camera of the world (viewpoint) 
	 * @param  g The Slick graphics object, used for drawing (No need for player) */
	@Override
	public void render(World world, Camera camera, Graphics g) {
		// Render the player depending on the camera
		world.renderObject(image, xPos, yPos);
	}	

	/** Be healed by the prietess */
	public void beHealed() {
		hp = maxHp;
	}

	/** Check if the player's hp is full 
	 * @return    true if the player's hp is full, otherwise false */
	public boolean hpFull() {
		if(hp == maxHp)
			return true;
		else
			return false;
	}
	
	/** Move the player
	 * @param  dir_x  x direction movement of the player (-1 to 1)
	 * @param  dir_y  y direction movement of the player (-1 to 1)
	 * @param  delta  number of milliseconds in this frame
	 * @param  world  world that the player is in */
	public void move(double dir_x, double dir_y, int delta, World world) {
		// Update the position of the player on the map if the player is not teleporting or saving point
		dir_x = dir_x * SPEED * delta;
		dir_y = dir_y * SPEED * delta;
		moveUnit(dir_x, dir_y, world);
	}
	
	/** Pick up the item 
	 * @param  item  item that the player is picking up */
	public void pick(Item item) {
					
		// Pick up the item
		item.bePicked();
					
		// Add it to the inventory
		inventory.addElement(item);
						
		// Functions of the item
		switch(item.getName()) {
			case "Amulet of Vitality" :
				hp += 80;
				maxHp += 80;
				break;
					
			case "Sword of Strength" :
				damage += 30;
				break;
						
			case "Tome of Agility" :
				maxCd -= 300;
				break;
		}
	}
	
	/** Talk to the villagers 
	 * @param  villager  villager that the player is talking to */
	public void talk(Villager villager) {		
		villager.beTalked(this);
	}
	
	/** Attack the monsters 
	 * @param  monster  monster that the player is attacking */
	public void attack(Monster monster) {
		monster.beAttacked(damage);
	}
	
	/** Let the player die */
	private void die() {
		revive();
	}
		
	/** Revive the player */
	private void revive() {
		// Back to the village and stop teleporting
		xPos = 738;
		yPos = 549;
			
		// Full hp
		hp = maxHp;
	}
	
	/** Be attacked by the monsters 
	 * @param  damage  damage received from the monsters */
	public void beAttacked(int damage) {
		hp -= damage;
	}
	
	/** Restore cd */
	public void restoreCd() {
		cd = maxCd;
	}
	
	/** Update the player's inventory by loading the game 
	 * @param  item  item loading from the saved game */
	public void loadInvent(Item item) {
		inventory.addElement(item);
	}
	
	/** Clear the player's inventory for loading the game */
	public void clearInvent() {
		inventory.clear();
	}
	
	/** Get the player's inventory 
	 * @return    inventory of the player */
	public Vector<Item> getInventory() {
		return inventory;
	}
	
	/** Get the panel image 
	 * @return    image of the panel */
	public Image getPanelImage() {
		return PANEL_IMAGE;
	}
	
	/** Get the player's hp 
	 * @return    hit point (HP) of the player */
	public int getHp() {
		return hp;
	}
	
	/** Get the player's max hp 
	 * @return    maximum hit point (HP) of the player */
	public int getMaxHp() {
		return maxHp;
	}
	
	/** Get the player's damage 
	 * @return    damage of the player */
	public int getDamage() {
		return damage;
	}
	
	/** Get the player's CD
	 * @return    current cooldown (CD) of the player */
	public int getCd() {
		return cd;
	}
	
	/** Get the player's max CD 
	 * @return    maximum cooldown (CD) of the player */
	public int getMaxCd() {
		return maxCd;
	}
}
