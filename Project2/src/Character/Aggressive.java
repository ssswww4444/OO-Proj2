package Character;
/* SWEN20003 Object Oriented Software Development
 * Aggressive Monster
 * Author: Pei Yun Sun <peiyuns>
 */

import org.newdawn.slick.SlickException;

import Game.World;

public class Aggressive extends Monster{
	
	// Speed of the aggressive monsters
	private static
final double SPEED_INIT = 0.25;

	/** Constructor
	 * @param  NAME  name of the aggressive monster
	 * @param  IMAGE_PATH  path of the image
	 * @param  maxHp  maximum hit point (HP) 
	 * @param  damage  damage of attacking
	 * @param  maxCd  maximum cooldown (CD)
	 * @param  xPos  x coordinate of the position on the map 
	 * @param  yPos  y coordinate of the position on the map
	 * @throws SlickException */
	public Aggressive(String NAME, String IMAGE_PATH, int maxHp, int damage, 
				int maxCd, int xPos, int yPos) throws SlickException {
		
		// Initialize the aggressive monster
		super(NAME, IMAGE_PATH, maxHp, damage, maxCd, xPos, yPos, SPEED_INIT);
	}

	/** Move the aggressive monster 
	 * @param  delta  number of milliseconds in this frame
	 * @param  world  world that the aggressive monster is in */
	@Override
	protected void move(int delta, World world) {	
		// If the monster need to move towards the player
		if(world.distPlayer(xPos, yPos) <= 150 && world.distPlayer(xPos, yPos) > 50)
			moveMonster(world, delta, true);
	}

	/** Update the aggressive monster 
	 * @param  delta  number of milliseconds in this frame
	 * @param  world  world that the aggressive monster is in */
	@Override
	public void update(int delta, World world) {
		// Update the monster only if alive
		if(hp > 0) {
			Player player = world.getPlayer();
        
			// Reduce the monster's CD
			if(cd > 0) {
				cd -= delta;
				if(cd < 0)
					cd = 0;
			}
        
			// Attack if the player is within 50 pixels and the monster's cd = 0
			if(world.distPlayer(xPos, yPos) <= 50 && (cd == 0)) {
        	
				// Attack the player
				attack(player);
        	
				// Restore CD
				cd = maxCd;
			}
			
			// Move the monster
			move(delta, world);
		}
	}
	
	/** Attack the player 
	 * @param  player  the player which the aggressive monster is attacking */
	private void attack(Player player) {
		player.beAttacked(damage);
	}

	/** Be attacked by the player
	 * @param  damage  damage received from the player */
	@Override
	protected void beAttacked(int damage) {
		// Reduce the monster's hp
		hp -= damage;
	}
}
