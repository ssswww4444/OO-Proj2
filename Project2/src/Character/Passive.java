package Character;
/* SWEN20003 Object Oriented Software Development
 * Passive Monster
 * Author: Pei Yun Sun <peiyuns>
 */

import java.util.Random;

import org.newdawn.slick.SlickException;

import Game.World;

public class Passive extends Monster{
	
	// Speed of the aggressive monster
	private static final double SPEED_INIT = 0.2;
	
	// Moving timer
	private int moveTimer;
	
	// Wander around time
	private static final int WANDERING_TIME_MAX = 3000;
	
	// Run away time if hitted by player
	private static final int RUN_AWAY_TIME_MAX = 5000;
	
	// Check if the monster is hitted by the player
	private boolean hitted;
	
	// x, y direction movement
	private double distX;
	private double distY;

	/** Constructor 
	 * @param  NAME  name of the passive monster
	 * @param  IMAGE_PATH  path of the image
	 * @param  maxHp  maximum hit point (HP) 
	 * @param  damage  damage of attacking
	 * @param  maxCd  maximum cooldown (CD)
	 * @param  xPos  x coordinate of the position on the map 
	 * @param  yPos  y coordinate of the position on the map
	 * @throws SlickException */
	public Passive(String NAME, String IMAGE_PATH, int maxHp, int damage, 
			int maxCd, int xPos, int yPos) throws SlickException {
		
		// Initialize the aggressive monster
		super(NAME, IMAGE_PATH, maxHp, damage, maxCd, xPos, yPos, SPEED_INIT);
		
		// Ready to move
		moveTimer = 0;
		
		// Not hitted initially
		hitted = false;
	}

	/** Move the passive monster 
	 * @param  delta  number of milliseconds in this frame
	 * @param  world  world that the passive monster is in */
	@Override
	protected void move(int delta, World world) {
		
		// If hitted by the player
		if(hitted) {
			
			// Run away
			moveMonster(world, delta, false);
			
			// Stop running away after 5s
			if(moveTimer == 0)
				hitted = false;	
		}
		
		// Wander around if not hitted by the player
		else {
			
			// Ready to change the direction
			if(moveTimer == 0) {
			
				// Generate a random direction
				
				Random rand = new Random();
			
				// From -1 to 1;
				distX = rand.nextInt(3) - 1;
				distY = rand.nextInt(3) - 1;
			
				// Restore the timer
				moveTimer = WANDERING_TIME_MAX;
			}
			 
			// Total moving distance
			double distTotal = Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2));
			
			// Total amount move in this frame
			double amount = SPEED * delta;
			
			// Movement of the monster in this frame
			double dir_x = distX / distTotal * amount;
			double dir_y = distY / distTotal * amount;
			
			// Move the passive monster
	    	moveUnit(dir_x, dir_y, world);
		}
    	
    	// Decrease the move time
    	moveTimer -= delta;
    	if(moveTimer < 0)
    		moveTimer = 0;
	}

	/** Update the passive monster 
	 * @param  delta  number of milliseconds in this frame
	 * @param  world  world that the passive monster is in */
	@Override
	public void update(int delta, World world) {
        // Update the monster only if alive
		if(hp > 0) {
        	// Decrease the monster's CD
			cd -= delta;
			if(cd < 0)
				cd = 0;
        
			// Move the monster
			move(delta, world);
		}
	}

	/** Be attacked by the player 
	 * @param  damage  damage received from the player */
	@Override
	protected void beAttacked(int damage) {
		
		// Reduce the monster's hp
		hp -= damage;
		
		// Hitted by the player
		hitted = true;
		
		// Start to run away
		moveTimer = RUN_AWAY_TIME_MAX;
	}
}
