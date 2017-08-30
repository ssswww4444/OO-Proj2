package Game;
/* SWEN20003 Object Oriented Software Development
 * Unit & Item generator
 * Author: Pei Yun Sun <peiyuns>
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Vector;

import org.newdawn.slick.SlickException;

import Character.Aggressive;
import Character.Monster;
import Character.Passive;
import Character.Player;
import Character.Unit;
import Character.Villager;

public class UnitItemGenerator {
	
	// Location of the "data" directory
	private static final String DATA_PATH = "data";
		
	// Location of the items' file
	private static final String ITEMS_FILE = "/items.txt";
		
	// Location of the units' data file
	private static final String UNITS_DATA_FILE = "/unitsData.txt";
		
	// Location of the unit's location file
	private static final String UNITS_LOC_FILE = "/unitsLoc.txt";
	
	// Player of the game
	private Player player;
	
	// A vector to store the items
	private Vector<Item> item_vec = new Vector<Item>();
			
	// A vector to store all the units
	private Vector<Unit> unit_vec = new Vector<Unit>();
	
	// Separate the units into different types
	private Vector<Monster> monster_vec = new Vector<Monster>();
	private Vector<Villager> villager_vec = new Vector<Villager>(); 

    /** Constructor 
     * @throws FileNotFoundException
     * @throws SlickException */
    public UnitItemGenerator() throws FileNotFoundException, SlickException {
    	init_item();
    	init_unit();
    }
    
    /** Inner class to store the units' data */
    class UnitData {
        private String dataName;
        private String dataImagePath;
        private String dataType;
        private String dataMonsterType;
        private int dataMaxHp;
        private int dataDamage;
        private int dataMaxCd;
        
        /** Constructor 
         * @param  name  name of the unit
         * @param  imagePath  path of the image of the unit
         * @param  type  type of the unit
         * @param  monsterType  aggressive/passive, N/A for non monster units
         * @param  maxHp  maximum hit point (HP) of the unit
         * @param  damage  damage of the unit
         * @param  maxCd  maximum cooldown (CD) of the unit */
        public UnitData(String name, String imagePath, String type, String monsterType,
        		int maxHp, int damage, int maxCd) {
        	
        	// Store the unit's data
        	dataName = name;
        	dataImagePath = imagePath;
        	dataType = type;
        	dataMonsterType = monsterType;
        	dataMaxHp = maxHp;
        	dataDamage = damage;
        	dataMaxCd = maxCd;	
        	}
	}
	
	/** Initialize the units
	 * @throws FileNotFoundException
	 * @throws SlickException */
    private void init_unit() throws FileNotFoundException, SlickException {
    	
    	// Create a hash table to store the units data
    	Hashtable<String, UnitData> unitData = new Hashtable<String, UnitData>();
    	
    	// File storing the units informations
    	File file = new File(DATA_PATH + UNITS_DATA_FILE);
   
    	// Create a new scanner to scan the file
    	Scanner scan = new Scanner(file);
  
    	// A string to store a line of the file
    	String s;
    	
    	// A string array to store the data after splitting, 7 data for each item
    	String[] s_arr = new String[7];
    	
    	// Scan iF it has next line
    	while(scan.hasNextLine()) {
    		
    		s = scan.nextLine();
    		s_arr = s.split(":");
    		
    		// Read the data
    		String name = s_arr[0];
    		String imagePath = s_arr[1];
    		String type = s_arr[2];
    		String monsterType = s_arr[3];
    		int maxHp = (int)Double.parseDouble(s_arr[4]);
    		int damage = (int)Double.parseDouble(s_arr[5]);
    		int maxCd = (int)Double.parseDouble(s_arr[6]);
    		
    		// Make the data into an object
    		UnitData data = new UnitData(name, imagePath, type, monsterType, maxHp, damage, maxCd);
    		
    		// Put the data object into the hash table
    		unitData.put(name, data);
    		
    	}
    	
    	scan.close();
    	
    	// File storing the units locations
    	File file2 = new File(DATA_PATH + UNITS_LOC_FILE);
    	
    	// Create a new scanner to scan the file
    	Scanner scan2 = new Scanner(file2);
  
    	// A string to store a line of the file
    	String s2;
    	
    	// A string array to store the data after splitting, 3 data for each item
    	String[] s2_arr = new String[3];
    	
    	// Scan iF it has next line
    	while(scan2.hasNextLine()) {
    		
    		s2 = scan2.nextLine();
    		s2_arr = s2.split(":");
    		
    		// Read the data
    		String name = s2_arr[0];
    		int xPos = (int)Double.parseDouble(s2_arr[1]);
    		int yPos = (int)Double.parseDouble(s2_arr[2]);
    		
    		// Find the unit data in the hash table
    		UnitData data = unitData.get(name);
    		
    		// Create the unit
    		Unit unit = null;
    		switch(data.dataType) {
    			case "Player" :
    				unit = new Player(data.dataName, data.dataImagePath, data.dataMaxHp,
    									data.dataDamage, data.dataMaxCd, xPos, yPos);
    				// Store the player
    				player = (Player) unit;
    				break;
    			
    			case "Villager" :
    				unit = new Villager(data.dataName, data.dataImagePath, data.dataMaxHp,
										data.dataDamage, data.dataMaxCd, xPos, yPos);
    				// Put into the villager vector
    				villager_vec.addElement((Villager)unit);
    				break;
    				
    			case "Monster" :
    				if(data.dataMonsterType.equals("Aggressive")) {
    					unit = new Aggressive(data.dataName, data.dataImagePath, data.dataMaxHp,
												data.dataDamage, data.dataMaxCd, xPos, yPos);
    					// Put into the aggressive vector
    					monster_vec.addElement((Monster) unit);
    				}
    				
    				else {
    					unit = new Passive(data.dataName, data.dataImagePath, data.dataMaxHp,
											data.dataDamage, data.dataMaxCd, xPos, yPos);
    					// Put into the passive vector
    					monster_vec.addElement((Monster)unit);
    				}
    				break;
    		}
    		
    		// Put the object into the unit vector
    		unit_vec.addElement(unit);
    	}
    	scan2.close();
    }
    
    /** Initialize the items 
     * @throws FileNotFoundException 
     * @throws SlickException */
    private void init_item() throws FileNotFoundException, SlickException {
    	
    	// File storing the item data
    	File file = new File(DATA_PATH + ITEMS_FILE);
   
    	// Create a new scanner to scan the file
    	Scanner scan = new Scanner(file);
  
    	// A string to store a line of the file
    	String s;
    	
    	// A string array to store the data after splitting, 4 data for each item
    	String[] s_arr = new String[4];
    	
    	// Scan if it has next line
    	while(scan.hasNextLine()) {
    		
    		s = scan.nextLine();
    		s_arr = s.split(":");
    		
    		// Read the data
    		String name = s_arr[0];
    		String imagePath = s_arr[1];
    		int xPos = (int)Double.parseDouble(s_arr[2]);
    		int yPos = (int)Double.parseDouble(s_arr[3]);
    		
    		// Create the new item
    		Item item = new Item(name, xPos, yPos, imagePath);
    		
    		// Put the item into the new item vector
    		item_vec.addElement(item);
    	}
    	scan.close();
    }
    
    /** Get the item vector
     * @return    vector of items */
    public Vector<Item> getItemVector() {
    	return item_vec;
    }
    
    /** Get the unit vector
     * @return    vector of units */
    public Vector<Unit> getUnitVector() {
    	return unit_vec;
    }
    
    /** Get the player
     * @return    player of the current world */
    public Player getPlayer() {
    	return player;
    }
    
    /** Get the villager vector
     * @return    vector of the villagers */
    public Vector<Villager> getVillagerVector() {
    	return villager_vec;
    }
    
    /** Get the monster vector
     * @return vector of the monsters */
    public Vector<Monster> getMonsterVector() {
    	return monster_vec;
    }
}

