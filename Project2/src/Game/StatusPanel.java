package Game;
/* SWEN20003 Object Oriented Software Development
 * Status Panel
 * Author: Pei Yun Sun <peiyuns>
 */

import java.util.Vector;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import Character.Player;

public class StatusPanel {
	
		/** Constructor */
		public StatusPanel() {
			// Do nothing
		}
		
	    /** Renders the player's status panel.
	     * @param  g  The current Slick graphics context
	     * @param  player  player of the current world
	     */
	    public void renderPanel(Graphics g, Player player)
	    {
	        // Panel colours
	        Color LABEL = new Color(0.9f, 0.9f, 0.4f);          // Gold
	        Color VALUE = new Color(1.0f, 1.0f, 1.0f);          // White
	        Color BAR_BG = new Color(0.0f, 0.0f, 0.0f, 0.8f);   // Black, transp
	        Color BAR = new Color(0.8f, 0.0f, 0.0f, 0.8f);      // Red, transp

	        // Variables for layout
	        String text;                // Text to display
	        int text_x, text_y;         // Coordinates to draw text
	        int bar_x, bar_y;           // Coordinates to draw rectangles
	        int bar_width, bar_height;  // Size of rectangle to draw
	        int hp_bar_width;           // Size of red (HP) rectangle
	        int inv_x, inv_y;           // Coordinates to draw inventory item

	        float health_percent;       // Player's health, as a percentage

	        // Panel background image
	        player.getPanelImage().draw(0, RPG.SCREEN_HEIGHT - RPG.PANEL_HEIGHT);

	        // Display the player's health
	        text_x = 15;
	        text_y = RPG.SCREEN_HEIGHT - RPG.PANEL_HEIGHT + 25;
	        g.setColor(LABEL);
	        g.drawString("Health:", text_x, text_y);
	        text = "" + player.getHp() + " / " + player.getMaxHp();

	        bar_x = 90;
	        bar_y = RPG.SCREEN_HEIGHT - RPG.PANEL_HEIGHT + 20;
	        bar_width = 90;
	        bar_height = 30;
	        health_percent = (float)player.getHp()/player.getMaxHp();
	        hp_bar_width = (int) (bar_width * health_percent);
	        text_x = bar_x + (bar_width - g.getFont().getWidth(text)) / 2;
	        g.setColor(BAR_BG);
	        g.fillRect(bar_x, bar_y, bar_width, bar_height);
	        g.setColor(BAR);
	        g.fillRect(bar_x, bar_y, hp_bar_width, bar_height);
	        g.setColor(VALUE);
	        g.drawString(text, text_x, text_y);

	        // Display the player's damage and cooldown
	        text_x = 200;
	        g.setColor(LABEL);
	        g.drawString("Damage:", text_x, text_y);
	        text_x += 80;
	        text = "" + player.getDamage();
	        g.setColor(VALUE);
	        g.drawString(text, text_x, text_y);
	        text_x += 40;
	        g.setColor(LABEL);
	        g.drawString("Rate:", text_x, text_y);
	        text_x += 55;
	        text = "" + player.getMaxCd(); 
	        g.setColor(VALUE);
	        g.drawString(text, text_x, text_y);

	        // Display the player's inventory
	        g.setColor(LABEL);
	        g.drawString("Items:", 420, text_y);
	        bar_x = 490;
	        bar_y = RPG.SCREEN_HEIGHT - RPG.PANEL_HEIGHT + 10;
	        bar_width = 288;
	        bar_height = bar_height + 20;
	        g.setColor(BAR_BG);
	        g.fillRect(bar_x, bar_y, bar_width, bar_height);

	        inv_x = 490;
	        inv_y = RPG.SCREEN_HEIGHT - RPG.PANEL_HEIGHT + ((RPG.PANEL_HEIGHT - 72) / 2);
	        
	        Vector<Item> inventory = player.getInventory();
	        
	        for (int i = 0; i < inventory.size(); i++)
	        {
	            // Render the item to (inv_x, inv_y)
	        	inventory.elementAt(i).renderToPanel(inv_x, inv_y);
	            inv_x += 72;
	        }
	    }
}
