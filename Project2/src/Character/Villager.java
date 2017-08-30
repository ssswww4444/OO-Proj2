package Character;
/* SWEN20003 Object Oriented Software Development
 * Villager
 * Author: Pei Yun Sun <peiyuns>
 */

import java.util.Vector;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import Game.Camera;
import Game.Item;
import Game.RPG;
import Game.World;

public class Villager extends Unit{
	
	// Check if the villager is talking to the player
	private boolean talking;
	
	// Current "dialogue" of the villager
	private String dialogue;
	
	// Talk Timer
	private int talkTimer;
	private static final int TALK_TIME = 4000;

	/** Constructor
	 * @param  NAME  name of the villager
	 * @param  IMAGE_PATH  path of the image
	 * @param  maxHp  maximum hit point (HP) 
	 * @param  damage  damage of attacking
	 * @param  maxCd  maximum cooldown (CD)
	 * @param  xPos  x coordinate of the position on the map 
	 * @param  yPos  y coordinate of the position on the map
	 * @throws SlickException
	 */
	public Villager(String NAME, String IMAGE_PATH, int maxHp, int damage, 
			int maxCd, int xPos, int yPos) throws SlickException {
		
		// Initialize the villager
		super(NAME, IMAGE_PATH, maxHp, damage, maxCd, xPos, yPos);
		
		// Not talking initially
		talking = false;
		talkTimer = 0;	
	}
	
	/** Talk to the player 
	 * @param  player  player that the villager is talking to */
	public void beTalked(Player player) {
		
		// The villager is talking to the player
		talking = true;
		talkTimer = TALK_TIME;

		// Get the inventory of the player
		Vector<Item> inventory = player.getInventory();
		
		boolean got_amulet = false;
		boolean got_sword = false;
		boolean got_tome = false;
		boolean got_elixir = false;
		
		// Check if the player got the items
		for(int i=0; i < inventory.size(); i++) {
			switch(((Item) inventory.elementAt(i)).getName()) {
				case "Amulet of Vitality" :
					got_amulet = true;
					break;
				
				case "Sword of Strength" :
					got_sword = true;
					break;
					
				case "Tome of Agility" :
					got_tome = true;
					break;
					
				case "Elixir of Life" :
					got_elixir = true;
			}
		}
		
		switch(NAME) {
	
			// If the player is talking to garth
			case "Garth" :
				if(!got_amulet)
					dialogue = "Find the Amulet of Vitality, across the river to the west.";
			
				else if(!got_sword)
					dialogue = "Find the Sword of Strength - cross the river and back, on the east side.";
			
				else if(!got_tome)
					dialogue = "Find the Tome of Agility, in the Land of Shadows.";
				else
					dialogue = "You have found all the treasure I know of.";
				break;
			
			// if the player is talking to the priestess
			case "Elvira" :
				if(player.hpFull())
					dialogue = "Return to me if you ever need healing.";
				
				else {
					heal(player);
					dialogue = "You're looking much healthier now.";
				}
				break;
			
			// if the player is talking to the prince
			case "Prince Aldric" :
				if(!got_elixir)
					dialogue = "Please seek out the Elixir of Life to cure the king.";
				
				else
					dialogue = "The elixir! My father is cured! Thankyou!";
				break;
		}
	}

	
	/** Heal the player (priestess) 
	 * @param  player  player that the priestess is healing */
	private void heal(Player player) {
		player.beHealed();
	}

	/** Update the villager
	 * @param  delta  number of milliseconds in this frame
	 * @param  world  world that the villager is in */
	@Override
	public void update(int delta, World world) {
        
        // End the talk after 4 seconds
        if(talkTimer==0)
        	talking = false;
        
        // Reduce talk timer
        talkTimer -= delta;
        if(talkTimer < 0)
        	talkTimer = 0;
	}

	/** Render the villager
	 * @param  world  world that the villager is in 
	 * @param  camera  camera of the world (viewpoint) 
	 * @param  g The Slick graphics object, used for drawing */
	@Override
	public void render(World world, Camera camera, Graphics g) {
		
		// Render the villager depending on the camera
		world.renderObject(image, xPos, yPos);
		
		// Render the dialogue if the villager is talking to the player
		if(talking)
			renderDialogue(camera, g);
		
		// Render the health bar
		renderHealthBar(camera, g);
	}
	
	/** Draw the dialogue 
	 * @param  camera  camera of the world (viewpoint) 
	 * @param  g The Slick graphics object, used for drawing */
	private void renderDialogue(Camera camera, Graphics g) {
		
		// Colors
		Color white = new Color(1.0f, 1.0f, 1.0f);
        Color black = new Color(0.0f, 0.0f, 0.0f);
		
        // Position of the black rectangle
		int bar_width = g.getFont().getWidth(dialogue) + 20;
        int bar_height = 20;
		int bar_x = (int)(RPG.SCREEN_WIDTH/2 + xPos - camera.getXPos()) - bar_width/2;
        int bar_y = (int)((RPG.SCREEN_HEIGHT - RPG.PANEL_HEIGHT)/2 + yPos - camera.getYPos()) - 70;
        
        // Draw the rectangle (Background)
        g.setColor(black);
        g.fillRect(bar_x, bar_y, bar_width, bar_height);

        // Position of the dialogue
        int text_x = (int)(RPG.SCREEN_WIDTH/2 + xPos - camera.getXPos()) - bar_width/2 + 10;
        int text_y = (int)((RPG.SCREEN_HEIGHT - RPG.PANEL_HEIGHT)/2 + yPos - camera.getYPos()) - 70;      
        
        // Draw dialogue
        g.setColor(white);
        g.drawString(dialogue, text_x, text_y);
	}
}
