package Character;
/* SWEN20003 Object Oriented Software Development
 * Unit
 * Author: Pei Yun Sun <peiyuns>
 */

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import Game.Camera;
import Game.Map;
import Game.RPG;
import Game.World;
import Save.UnitData;

public abstract class Unit {
	
	// Facing direction of the unit
	protected boolean facing_right;
	
	// Name of the Unit
	protected final String NAME;
	
	// Image of the unit
	protected Image image;
	
	// Position of the unit on the map
	protected double xPos;
	protected double yPos;
	
	// HP and Max Hp of the unit
	protected int hp;
	protected int maxHp;
	
	// Damage of the unit
	protected int damage;
	
	// CoolDown of the unit
	protected int cd;
	protected int maxCd;
	
	// Location of the "units" directory
	private final static String UNITS_PATH = "/units/";
	
	/** Update the unit
	 * @param  delta  number of milliseconds in this frame
	 * @param  world  world that the unit is in */
	abstract public void update(int delta, World world);
	
	/** Render the unit
	 * @param  world  world that the monster is in 
	 * @param  camera  camera of the world (viewpoint) 
	 * @param  g The Slick graphics object, used for drawing */
	abstract public void render(World world, Camera camera, Graphics g);
	
	/** Constructor
	 * @param  NAME  name of the unit
	 * @param  IMAGE_PATH  path of the image
	 * @param  maxHp  maximum hit point (HP) 
	 * @param  damage  damage of attacking
	 * @param  maxCd  maximum cooldown (CD)
	 * @param  xPos  x coordinate of the position on the map 
	 * @param  yPos  y coordinate of the position on the map
	 * @throws SlickException */
	public Unit(String NAME, String IMAGE_PATH, int maxHp, int damage, 
			int maxCd, int xPos, int yPos) throws SlickException {
		
		// Initialize the name
		this.NAME = NAME;
		
		// Initialize the image
		image = new Image(RPG.ASSETS_PATH + UNITS_PATH + IMAGE_PATH);
		
		// Initialize hp and max hp
		this.maxHp = maxHp;
		hp = maxHp;
		
		// Initialize the damage
		this.damage = damage;
		
		// Initialize cd and max cd
		this.maxCd = maxCd;
		cd = 0;
		
		// Initialize the position
		this.xPos = xPos;
		this.yPos = yPos;
		
		// The unit is initially facing to the right
    	facing_right = true;
	}
	
	/** Move the player
	 * @param  dir_x  x direction movement of the unit (already multiplied the delta and speed)
	 * @param  dir_y  y direction movement of the unit (already multiplied the delta and speed)
	 * @param  world  world that the unit is in */
	protected void moveUnit(double dir_x, double dir_y, World world) {

		Map map = world.getMap();
		
		// Move in x direction only or y direction only
		if(dir_x == 0 || dir_y == 0) {
			if(!map.terrainBlocks(xPos + dir_x, yPos + dir_y)) {
				xPos += dir_x;
				yPos += dir_y;
			}
		}
		
		// Move diagonally
		else {
			
			// True if blocked, false if not blocked
			boolean destBlocked = true;
			boolean xBlocked = true;
			boolean yBlocked = true;
			
			// Check the destination is blocked
			if(!map.terrainBlocks(xPos + dir_x, yPos + dir_y))
				destBlocked = false;
			
			// Check if move in x direction only is blocked
			if(!map.terrainBlocks(xPos + dir_x, yPos))
				xBlocked = false;
			
			// Check if move in y direction only is blocked
			if(!map.terrainBlocks(xPos, yPos + dir_y))
				yBlocked = false;
			
			// If the destination is blocked, check if can slide
			if(destBlocked) {
				
				// Slide in x direction
        		if(!xBlocked)
        			xPos += dir_x;
        		
        		// Slide in y direction
        		if(!yBlocked)
        			yPos += dir_y;
			}
			
			// If the destination is not blocked, check if blocked by the surrounding
			else {
				// Move if at least one of x and y direction is not blocked
				if(!(xBlocked && yBlocked)) {
					xPos += dir_x;
					yPos += dir_y;
				}
			}
		}
		
		// Update the facing direction
		updateFacing(dir_x);
	}
	
	/** Render unit's health bar
	 * @param  camera  camera of the world (viewpoint) 
	 * @param  g The Slick graphics object, used for drawing */
	protected void renderHealthBar(Camera camera, Graphics g) {
		
		// Colors
		Color white = new Color(1.0f, 1.0f, 1.0f);
        Color black = new Color(0.0f, 0.0f, 0.0f, 0.8f);
        Color red = new Color(0.8f, 0.0f, 0.0f, 0.8f);
		
		// Position of the black rectangle
		int bar_width = g.getFont().getWidth(NAME) + 20;
        int bar_height = 20;
		int bar_x = (int)(RPG.SCREEN_WIDTH/2 + xPos - camera.getXPos()) - bar_width/2;
        int bar_y = (int)((RPG.SCREEN_HEIGHT - RPG.PANEL_HEIGHT)/2 + yPos - camera.getYPos()) - 50;
        
        // Draw the rectangle (Background)
        g.setColor(black);
        g.fillRect(bar_x, bar_y, bar_width, bar_height);
        
        // Unit's health, as a percentage
     	float health_percent;
     	health_percent = (float) hp / maxHp;
     		
        // Length of the hp bar
        int hp_bar_width = (int) (bar_width * health_percent);
        
        // Draw the hp bar
        g.setColor(red);
        g.fillRect(bar_x, bar_y, hp_bar_width, bar_height);
        
        // Position of the name
        int text_x = bar_x + 10;
        int text_y = bar_y;
        
        // Draw the name of the unit
        g.setColor(white);
        g.drawString(NAME, text_x, text_y);    
	}
	
	/** Update the facing direction of the unit
	 * @param  dir_x  x coordinate movement of the unit */
	private void updateFacing(double dir_x) {
		
		// Let the unit face to the left
    	if(dir_x<0 && facing_right) {
    		image = image.getFlippedCopy(true, false);
    		// face to left
    		facing_right = false;
    	}
    	
    	// Let the unit face to the right
    	if(dir_x>0 && (!facing_right)) {
    		image = image.getFlippedCopy(true, false);
    		// face to the right
    		facing_right = true;
    	}
	}
	
	/** Get the xPos of the unit 
	 * @return    x coordinate of the position on the map */
	public double getXPos() {
		return xPos;
	}
	
	/** Get the yPos of the unit 
	 * @return    y coordinate of the position on the map */
	public double getYPos() {
		return yPos;
	}
	
	/** Update the unit data 
	 * @param  data  object that contains all the data for the unit */
	public void updateData(UnitData data) {
		xPos = data.getXPos();
		yPos = data.getYPos();
		hp = data.getHp();
		maxHp = data.getMaxHp();
		damage = data.getDamage();
		cd = data.getCd();
		maxCd = data.getMaxCd();
	}
	
	/** Get the unit data object 
	 * @return    object that contains all the data for the unit */
	public UnitData getDataObj() {
		UnitData data = new UnitData(xPos, yPos, hp, maxHp, damage, cd, maxCd); 
		return data;
	}
}
