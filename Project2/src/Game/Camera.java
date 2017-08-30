package Game;
/* SWEN20003 Object Oriented Software Development
 * Camera
 * Author: Pei Yun Sun <peiyuns>
 */

import org.newdawn.slick.SlickException;

import Character.Player;

/** Represents the camera that controls our viewpoint */
public class Camera
{
    // The unit this camera is following
    private Player unitFollow;
    
    // The camera's position in the world, in x and y coordinates, in pixels
    private int xPos;
    private int yPos;
    
    // Screen height / Screenwidth
    private final int SCREEN_WIDTH;
    private final int SCREEN_HEIGHT;
    
    /** Constructor
     * @param  SCREEN_WIDTH  screen width of the game
     * @param  SCREEN_HEIGHT screen height of the game */
    public Camera(int SCREEN_WIDTH, int SCREEN_HEIGHT) {
    	this.SCREEN_WIDTH = SCREEN_WIDTH;
    	this.SCREEN_HEIGHT = SCREEN_HEIGHT;
    }

    /** Update the game camera to recentre it's viewpoint around the player 
     * @param  player  player that the camera is following 
     * @param  map  map used in the current world
     * @throws SlickException */
    public void update(Player player, Map map)
    throws SlickException {
    	
    	followUnit(player);
    	
    	// Stop the camera from geting out of the y direction boundary
    	if((int)(unitFollow.getYPos() + SCREEN_HEIGHT/2) <= map.getMapHeight() 
    			&& (int)(unitFollow.getYPos() - SCREEN_HEIGHT/2) >= 0) {
    		
    		// Move the camera in y direction
    		yPos = (int)unitFollow.getYPos();
    	}
    	
    	// Stop the camera from geting out of the x direction boundary
    	if((int)(unitFollow.getXPos() + SCREEN_WIDTH/2) <= map.getMapWidth()
    			&& (int)(unitFollow.getXPos() - SCREEN_WIDTH/2) >= 0) {
    		
    		// Move the camera in y direction
    		xPos = (int)unitFollow.getXPos();
    	}
    }
    
    /** Returns the minimum x value on screen 
     * @return    the minimum x value on the screen */
    public int getMinX() {
        return (int)(xPos - SCREEN_WIDTH/2);
    }
    
    /** Returns the maximum x value on screen 
     * @return    the maximum x value on the screen */ 
    public int getMaxX() {
    	return (int)(xPos + SCREEN_WIDTH/2);
    }
    
    /** Returns the minimum y value on screen 
     * @return    the minimum y value on the screen */
    public int getMinY() {
    	return (int)(yPos - SCREEN_HEIGHT/2);
    }
    
    /** Returns the maximum y value on screen 
     * @return    the maximum y value on the screen */
    public int getMaxY() {
    	return (int)(yPos + SCREEN_HEIGHT/2);
    }

    /** Tells the camera to follow a given unit
     * @param  unit  unit that the camera is following
     * @throws SlickException */
    private void followUnit(Object unit)
    throws SlickException {
    	// Follow the player
    	unitFollow = (Player) unit;
    }
    
    /** Get the x-coordinate of the camera 
     * @return    x coordinate of the camera on the map */
    public int getXPos() {
    	return xPos;
    }
    
    /** Get the y-coordinate of the camera 
     * @return    y coordinate of the camera on the map */
    public int getYPos() {
        return yPos;
    }   
}