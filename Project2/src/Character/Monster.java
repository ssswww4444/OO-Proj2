package Character;
/* SWEN20003 Object Oriented Software Development
 * Monster
 * Author: Pei Yun Sun <peiyuns>
 */

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import Game.Camera;
import Game.World;

public abstract class Monster extends Unit{
	
	// Moving speed of the monster
	protected final double SPEED;
	
	/** Move the passive monster 
	 * @param  delta  number of milliseconds in this frame
	 * @param  world  world that the monster is in */
	abstract protected void move(int delta, World world);
	
	/** Be attacked by the player 
	 * @param  damage  damage received from the player */
	abstract protected void beAttacked(int damage);
	
	/** Constructor 
	 * @param  NAME  name of the monster
	 * @param  IMAGE_PATH  path of the image
	 * @param  maxHp  maximum hit point (HP) 
	 * @param  damage  damage of attacking
	 * @param  maxCd  maximum cooldown (CD)
	 * @param  xPos  x coordinate of the position on the map 
	 * @param  yPos  y coordinate of the position on the map 
	 * @param  SPEED  moving speed of the monster
	 * @throws SlickException */
	public Monster(String NAME, String IMAGE_PATH, int maxHp, int damage, 
			int maxCd, int xPos, int yPos, double SPEED)
			throws SlickException {
		
		// Initialize the Monster
		super(NAME, IMAGE_PATH, maxHp, damage, maxCd, xPos, yPos);
		
		// Initialize the speed
		this.SPEED = SPEED;	
	}

	/** Render the monster 
	 * @param  world  world that the monster is in 
	 * @param  camera  camera of the world (viewpoint) 
	 * @param  g The Slick graphics object, used for drawing */
	@Override
	public void render(World world, Camera camera, Graphics g) {
		// Render the monster only if alive
		if(hp > 0) {
			// Render the monster depending on the camera
			world.renderObject(image, xPos, yPos);
		
			// Render the health bar
			renderHealthBar(camera, g);
		}
	}
	
	/** Move the monster by Algorithm 1 
	 * @param  world  world that the monster is in
	 * @param  delta  number of milliseconds in this fram
	 * @param  aggressiveMode  check if the monster is aggressive or passive, true for aggressive, false for passive */
	protected void moveMonster(World world, int delta, boolean aggressiveMode) {
		
		// Distance from the monster to the player (move towards player : aggressive)
		double distX = world.getPlayer().getXPos() - xPos;
		double distY = world.getPlayer().getYPos() - yPos;
		
		// If it's passive monster (move away from player : passive)
		if(!aggressiveMode) {
			// Reverse direction
			distX = -distX;
			distY = -distY;
		}
		
		// Total distance to/from the player
		double distTotal = Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2));
		
		// Total amount move in this frame
		double amount = SPEED * delta;
		
		// Movement of the monster
		double dir_x = distX / distTotal * amount;
		double dir_y = distY / distTotal * amount;
					
		// Move the monster
		moveUnit(dir_x, dir_y, world);
	}
}
