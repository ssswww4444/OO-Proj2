package Game;
/* SWEN20003 Object Oriented Software Development
 * Menu
 * Author: Pei Yun Sun <peiyuns>
 */

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import Character.Player;

public class Menu {
	
	// Menu timer
	private int menuTimer;
	private static final int MENU_TIME = 500;
	
	
	/** Constructor */
	public Menu() {
		menuTimer = MENU_TIME;
	}
	
	/** Start timer */
	public void startTimer() {
		menuTimer = MENU_TIME;
	}
		
    /** Renders the menu.
     * @param  g  the current Slick graphics context
     * @param  player  player in the current world
     * @param  delta  number of milliseconds in this frame
     * @param  show  true for showing the menu, false for hiding the menu */
    public void renderMenu(Graphics g, Player player, int delta, boolean show)
    {
        // Panel colours
        Color LABEL = new Color(0.9f, 0.9f, 0.4f);          // Gold

        // Use the flipped panel image as the menu background image
        if(show)
        	player.getPanelImage().getFlippedCopy(false, true).draw(0, -142 + RPG.MENU_HEIGHT*(1 - (float)menuTimer/MENU_TIME));
        else
        	player.getPanelImage().getFlippedCopy(false, true).draw(0, -142 + RPG.MENU_HEIGHT*(float)menuTimer/MENU_TIME);
	        
        // Variables for layout
        float text_x, text_y, text_width;         // Coordinates to draw text
	        
        // Display the buttons
	    
        if(show)
        	text_y = (RPG.MENU_HEIGHT*(1 - (float)menuTimer/MENU_TIME)) - RPG.MENU_HEIGHT*3/4;
        else
        	text_y = (RPG.MENU_HEIGHT*(float)menuTimer/MENU_TIME) - RPG.MENU_HEIGHT*3/4;

        // Display the Save button
        text_width = g.getFont().getWidth("Save");
        text_x = RPG.SCREEN_WIDTH/8 - text_width/2;
        g.setColor(LABEL);
        g.drawString("Save", text_x, text_y);
	        
        // Display the Load button
        text_width = g.getFont().getWidth("Load");
        text_x = RPG.SCREEN_WIDTH*3/8 - text_width/2;
        g.drawString("Load", text_x, text_y);
        
        // Display the Help button
	    text_width = g.getFont().getWidth("Help");
	    text_x = RPG.SCREEN_WIDTH*5/8 - text_width/2;
	    g.drawString("Help", text_x, text_y);
	        
	    // Display the Exit button
	    text_width = g.getFont().getWidth("Exit");
	    text_x = RPG.SCREEN_WIDTH*7/8 - text_width/2;
	    g.drawString("Exit", text_x, text_y);   
	        
	    // Separate the buttons
	    if(show) {
	    	g.fillRect(RPG.SCREEN_WIDTH/4, 0, 1, RPG.MENU_HEIGHT*(1 - (float)menuTimer/MENU_TIME)-4);
	    	g.fillRect(RPG.SCREEN_WIDTH/2, 0, 1, RPG.MENU_HEIGHT*(1 - (float)menuTimer/MENU_TIME)-4);
	    	g.fillRect(RPG.SCREEN_WIDTH*3/4, 0, 1, RPG.MENU_HEIGHT*(1 - (float)menuTimer/MENU_TIME)-4);
	    }
	    else {
	    	g.fillRect(RPG.SCREEN_WIDTH/4, 0, 1, RPG.MENU_HEIGHT*(float)menuTimer/MENU_TIME-4);
	    	g.fillRect(RPG.SCREEN_WIDTH/2, 0, 1, RPG.MENU_HEIGHT*(float)menuTimer/MENU_TIME-4);
	    	g.fillRect(RPG.SCREEN_WIDTH*3/4, 0, 1, RPG.MENU_HEIGHT*(float)menuTimer/MENU_TIME-4);
	    }
	    
	    // Reduce the menu timer
	    menuTimer -= delta;
	    if(menuTimer < 0)
	    	menuTimer = 0;
	}
    
    /** Check if the menu is completely shown or hidden 
     * @return    true if the menu is completely shown or hidden, otherwise false */
    public boolean menuComplete() {
    	if(menuTimer == 0)
    		return true;
    	else
    		return false;
    }
}
