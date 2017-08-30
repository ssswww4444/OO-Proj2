package Game;
/* 433-294 Object Oriented Software Development
 * World
 * Author: Pei Yun Sun <peiyuns>
 */

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import Character.Monster;
import Character.Player;
import Character.Unit;
import Character.Villager;
import Game.Camera;
import Save.SaveHandler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

/** Represents the entire game world.
 * (Designed to be instantiated just once for the whole game).
 */
public class World {	
	
	// Map of the world
	private Map map;
	
	// Path of the map
	private static final String MAP_PATH = "/map.tmx";
	
	// Camera of the world
	private Camera camera;
	
	// Used to generate the items and units, and create vectors for them
	private UnitItemGenerator generator;
	
	// Status panel for the player
	private StatusPanel panel;
	
	// Menu
	private Menu menu;
	
	// Check if showing or hiding menu
	boolean showMenu;
	
	// Check if should show menu
	private boolean menuOn;
	
	// Help window
	private boolean helpOn;
	
	// Record the delta to render the menu
	private int delta;
	
	// Save data file name
	private static final String DAT_FILE_PATH = "/game_save.dat";
	
	// Game saving handler
	private SaveHandler saveHandler;
	
	// Timer for saved/loaded message
	private static final int SAVE_LOAD_TIME = 2000;
	private int saveTimer;
	private int loadTimer;
	private boolean saving;
	private boolean loading;
    
    /** Create a new World object
     * @throws SlickException
     * @throws FileNotFoundException
     */
    public World()
    throws SlickException, FileNotFoundException {
    	
    	// Load in map
    	map = new Map(MAP_PATH);
    	
    	// Create camera
    	camera = new Camera(RPG.SCREEN_WIDTH, RPG.SCREEN_HEIGHT - RPG.PANEL_HEIGHT);
    	
    	// Initialize items and units and their vector
    	generator = new UnitItemGenerator();
    	
    	// Initialize the panel
    	panel = new StatusPanel();
    	
    	// Initialize the menu
    	menu = new Menu();
    	
    	// Help window off initially
    	helpOn = true;
    	
    	// Menu off initially
    	menuOn = false;
    	
    	// Initialize delta
    	delta = 0;
    	
    	// Initialize showMenu
    	showMenu = false;
    	
    	// New save game handler
    	saveHandler = new SaveHandler(DAT_FILE_PATH);
    	
    	// Not saving or loading initially
    	saving = false;
    	loading = false;
    }
    
    /** Update the world 
     * @param  delta  number of milliseconds in this frame
     * @param  gc  The Slick game container object
     * @throws SlickException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void update(int delta, GameContainer gc)
    throws SlickException, IOException, ClassNotFoundException {
    	this.delta = delta;
    	
    	// Get data about the current input (keyboard state).
		Input input = gc.getInput();
		
		// Press Q to show/hide the menu
		if(input.isKeyPressed(Input.KEY_Q) && menu.menuComplete()) {
			// Alternate show or hide menu
			menuOn = !menuOn;
			showMenu = !showMenu;
			menu.startTimer();		
		}
		
		// Update the player's movement direction based on keyboard presses.
		double dir_x = 0;
		double dir_y = 0;
		if(input.isKeyDown(Input.KEY_DOWN)) {
			dir_y += 1;
			// Close the help window as the player start to move
			helpOn = false;
			// Close the menu close as the player start to move
			if(menuOn) {
				menuOn = false;
				showMenu = false;
				menu.startTimer();
			}
		}
		if(input.isKeyDown(Input.KEY_UP)) {
			dir_y -= 1;
			// Close the help window close as the player start to move
			helpOn = false;
			// Close the menu close as the player start to move
			if(menuOn) {
				menuOn = false;
				showMenu = false;
				menu.startTimer();
			}
		}
		if(input.isKeyDown(Input.KEY_LEFT)) {
			dir_x -= 1;
			// Close the help window close as the player start to move
			helpOn = false;
			// Close the menu close as the player start to move
			if(menuOn) {
				menuOn = false;
				showMenu = false;
				menu.startTimer();
			}
		}
		if(input.isKeyDown(Input.KEY_RIGHT)) {
			dir_x += 1;
			// Close the help window close as the player start to move
			helpOn = false;
			// Close the menu close as the player start to move
			if(menuOn) {
				menuOn = false;
				showMenu = false;
				menu.startTimer();
			}
		}
		
		// Get the vectors from the generator
		Player player = generator.getPlayer();
		Vector<Item> item_vec = generator.getItemVector();
		Vector<Villager> villager_vec = generator.getVillagerVector();
		Vector<Monster> monster_vec = generator.getMonsterVector();
		Vector<Unit> unit_vec = generator.getUnitVector();
		
    
		// Move the player
		player.move(dir_x, dir_y, delta, this);
		
		// Pick up the items
		for(Item item : item_vec) {
			// If the item is nearby and not picked up yet
			if(distPlayer(item.getXPos(), item.getYPos()) <= 50  && !item.pickedUp())
				player.pick(item);
		}
		
		// Talk to the villagers if key "T" is pressed
		if(input.isKeyPressed(Input.KEY_T)) {
			// Check through each villager
			for(Villager villager: villager_vec) {
				// If the villager is within 50 pixels
				if(distPlayer(villager.getXPos(), villager.getYPos()) <= 50)
					player.talk(villager);
			}
			
			// Close the help window close as the player talk to the villager
			helpOn = false;
			// Close the menu close as the player talk to the villager
			if(menuOn) {
				menuOn = false;
				showMenu = false;
				menu.startTimer();
			}
		}
		
		// Attack the monsters if A is down and the player is ready to attack
		if(input.isKeyDown(Input.KEY_A) && player.getCd() == 0) {
    	
			// Attack the monsters
			for(Monster monster: monster_vec) {
				// If the passive monster within 50 pixels
				if(distPlayer(monster.getXPos(), monster.getYPos()) <= 50)
					player.attack(monster);
			}
    	
			// Restore the player's CD
			player.restoreCd();
			
			// Close the help window close as the player attack the monsters
			helpOn = false;
			// Close the menu close as the player attack the monsters
			if(menuOn) {
				menuOn = false;
				showMenu = false;
				menu.startTimer();
			}
		}
    	
    	// Freeze the game if the menu is on
    	if(!menuOn) {	
    		// Update all the units
    		for(int i=0; i<unit_vec.size(); i++)
    			unit_vec.elementAt(i).update(delta, this);
    	}
    	
    	// Update the camera
		camera.update(player, map);
    	
    	// Check if the buttons of the menu are pressed if the menu is shown completely
    	if(menuOn && menu.menuComplete()) {
    		if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
    			int x = input.getMouseX();
    			int y = input.getMouseY();
    		
    			if(y<=RPG.MENU_HEIGHT) {
    				if(x<=RPG.SCREEN_WIDTH/4) {
    					// Save
    					saveHandler.save(item_vec, unit_vec);
    					saving = true;
    					saveTimer = SAVE_LOAD_TIME;
	    			}
	    			else if (x<=RPG.SCREEN_WIDTH/2) {
    					// Load
	    				saveHandler.load(item_vec, unit_vec, player);
	    				loading = true;
    					loadTimer = SAVE_LOAD_TIME;
    				}
    			
	    			else if(x>RPG.SCREEN_WIDTH/2 && x<=RPG.SCREEN_WIDTH*3/4) {
    					// Help
    					helpOn = !helpOn;
    				}
    			
	    			else{
    					// Exit the game
    					gc.exit();
    				}
    			}
    		}
    	}
    	
    	// Reduce the save/load timer
    	saveTimer -= delta;
    	if(saveTimer < 0)
    		saveTimer = 0;
    	loadTimer -= delta;
    	if(loadTimer < 0) 
    		loadTimer = 0;
    }

    /** Render the entire screen, so it reflects the current game state.
     * @param g The Slick graphics object, used for drawing
     * @throws SlickException
     */
    public void render(Graphics g)
    throws SlickException {
    	
    	// Get the vectors from the generator
    	Player player = generator.getPlayer();
    	Vector<Item> item_vec = generator.getItemVector();
    	Vector<Unit> unit_vec = generator.getUnitVector();
    	
    	// Render the map to fit the camera
    	map.render(camera);
    	
    	// Render the items
    	for(Item item : item_vec)
    		item.render(this);
    	
    	// Render the units
    	for(Unit unit : unit_vec)
    		unit.render(this, camera, g);
    	
    	// Render the status panel
    	panel.renderPanel(g, player);
    	
    	// Render the menu
    	menu.renderMenu(g, player, delta, showMenu);
    	
    	// Render the help window
    	if(helpOn)
    		renderHelp(g);
    	
    	// Render the saved message
    	if(saving && saveTimer != 0)
    		saveHandler.renderMessage(g, true);
    	
    	// Render the loaded message
    	if(loading && loadTimer != 0)
    		saveHandler.renderMessage(g, false);
    }
    
    /** Check the distance of the input coordinates from the player
     * @param  xPos  x coordinate of the position on the map
     * @param  yPos  y coordinate of the position on the map
     * @return    distance from/to the given position to/from the player */
 	public double distPlayer(double xPos, double yPos) {
 		double distX = xPos - generator.getPlayer().getXPos();
 		double distY = yPos - generator.getPlayer().getYPos();
 		double distance = Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2));	
 		return distance;
 	}
 	
 	/** Render the object (unit or item) based on camera (draw Centered)
 	 * @param  image  image of the unit/item
 	 * @param  xPos  x coordinate of the position on the map
     * @param  yPos  y coordinate of the position on the map
 	 */
 	public void renderObject(Image image, double xPos, double yPos) {
 		xPos = RPG.SCREEN_WIDTH/2 + xPos - camera.getXPos();
 		yPos = (RPG.SCREEN_HEIGHT - RPG.PANEL_HEIGHT)/2 + yPos - camera.getYPos();
 		image.drawCentered((int)xPos, (int)yPos);
 	}
 	
 	/** render the help window
 	 * @param  g The Slick graphics object, used for drawing */
 	private void renderHelp(Graphics g) {
 		// Colors
 		Color white = new Color(1.0f, 1.0f, 1.0f);          // White
        Color black = new Color(0.0f, 0.0f, 0.0f, 0.5f);   // Black, transp
        
        // Draw the help window
        g.setColor(black);
        int window_width = RPG.SCREEN_WIDTH/2;
        int window_height = (int)(RPG.SCREEN_HEIGHT*2/5);
        int window_x = (RPG.SCREEN_WIDTH - window_width)/2;
        int window_y = (RPG.SCREEN_HEIGHT - window_height)/2;
        g.fillRect(window_x, window_y, window_width, window_height);
        
        // Draw the title of the help window
        g.setColor(white);
        int text_width = g.getFont().getWidth("Help");
        int text_x = window_x + window_width/2 - text_width/2;
        int text_y = window_y + 20;
        g.drawString("Help", text_x, window_y + 20);
        
        // Line space
        int linespace = 30;
        
        // Draw the words of the help window
        text_x = window_x + 50;
        text_y += linespace;
        g.drawString("Press Key 'A' to attack the Monsters", text_x, text_y);
        text_y += linespace;
        g.drawString("Press Key 'T' to talk to the Villagers", text_x, text_y);
        text_y += linespace;
        g.drawString("Press Key 'Q' to open the menu", text_x, text_y);
        text_y += linespace;
        g.drawString("The game pause when the menu is on", text_x, text_y);
        text_y += linespace;
        g.drawString("The Prietess Elvira can heal your wound", text_x, text_y);
        text_y += linespace;
        g.drawString("Aim: Find Elixir of Life to cure the king", text_x, text_y);
 	}
 	
 	/** Get the map 
 	 * @return    map of the current world */
    public Map getMap() {
    	return map;
    }
    
    /** Get the player 
     * @return    player of the current world */
    public Player getPlayer() {
    	return generator.getPlayer();
    }
}
