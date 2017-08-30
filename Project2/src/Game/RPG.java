package Game;
/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine
 * Author: Pei Yun Sun <peiyuns>
 */

import java.io.FileNotFoundException;
import java.io.IOException;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Font;

/** Main class for the Role-Playing Game engine.
 * Handles initialisation, input and rendering.
 */
public class RPG extends BasicGame
{
	// Height of the status panel
	public static final int PANEL_HEIGHT = 70;
	
	// Height of the Menu
	public static final int MENU_HEIGHT = 45;
	
    // Location of the "assets" directory
    public static final String ASSETS_PATH = "assets";
    
    // Screen width and height, in pixels
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;
    
    // World of the game
    private World world;
    
    // A nicer font
    private Font font;
    
    // Path of the nicer font
    private static final String FONT_PATH = "/DejaVuSans-Bold.ttf";
    
    /** Create a new RPG object. */
    public RPG()
    {
        super("Shadow Quest");
    }

    /** Initialise the game state
     * @param gc The Slick game container object */
    @Override
    public void init(GameContainer gc)
    throws SlickException
    {	
    	// Create a new world
		try {
			world = new World();
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Load a nicer font
		font = FontLoader.loadFont(ASSETS_PATH + FONT_PATH, 15);
    }

    /** Update the game state for a frame.
     * @param gc The Slick game container object.
     * @param delta Time passed since last frame (milliseconds) */
    @Override
    public void update(GameContainer gc, int delta)
    throws SlickException
    {       	
        // Let World.update decide what to do with this data.
        try {
			world.update(delta, gc);
		} 
        catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    /** Render the entire screen, so it reflects the current game state.
     * @param gc The Slick game container object
     * @param g The Slick graphics object, used for drawing
     * @throws SlickException */
    public void render(GameContainer gc, Graphics g)
    throws SlickException
    {
    	// Use a nicer font
    	g.setFont(font);
    	
        // Let World.render handle the rendering.
        world.render(g);
    }

    /** Start-up method. Creates the game and runs it
     * @param args Command-line arguments (ignored)
     * @throws SlickException */
    public static void main(String[] args)
    throws SlickException
    {
        AppGameContainer app = new AppGameContainer(new RPG());
        // setShowFPS(true), to show frames-per-second.
        app.setShowFPS(false);
        app.setDisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT, false);
        app.start();
    }
}
