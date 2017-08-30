package Game;
/* SWEN20003 Object Oriented Software Development
 * Map
 * Author: Pei Yun Sun <peiyuns>
 */

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class Map {
	
	// The game map
	private TiledMap map;
	
	/** Constructor
	 * @param  MAP_PATH  path of the map under assets
	 * @throws SlickException */
	public Map(String MAP_PATH) throws SlickException {
		map = new TiledMap(RPG.ASSETS_PATH + MAP_PATH, RPG.ASSETS_PATH);
	}
    
    /** Render the map 
     * @param  camera  camera of the world (viewpoint) */
    public void render(Camera camera) {
    	// Render the map to fit the camera
    	map.render(-camera.getMinX()%getTileWidth(), -camera.getMinY()%getTileHeight(), camera.getMinX()/getTileWidth(), 
    				camera.getMinY()/getTileHeight(), RPG.SCREEN_WIDTH/getTileWidth()+2, RPG.SCREEN_HEIGHT/getTileHeight()+2);
    }
    
    /** Determines whether a particular map coordinate blocks movement 
     * @param  x coordinate on the map
     * @param  y coordinate on the map
     * @return    true if the tile of that position is blocked, false if not blocked */
    public boolean terrainBlocks(double x, double y)
    {
        // Check we are within the bounds of the map
        if (x < 0 || y < 0 || x > getMapWidth() || y > getMapHeight())
            return true;
        
        // Check the tile properties
        int tile_x = (int) x / getTileWidth();
        int tile_y = (int) y / getTileHeight();
        int tileid = map.getTileId(tile_x, tile_y, 0);
        String block = map.getTileProperty(tileid, "block", "0");
        return !block.equals("0");
    }
    
    /** Map width, in pixels
     * @return    map width, in pixels */
    public int getMapWidth()
    {
        return map.getWidth() * getTileWidth();
    }

    /** Map height, in pixels 
     * @return    map height, in pixels */
    public int getMapHeight()
    {
        return map.getHeight() * getTileHeight();
    }

    /** Tile width, in pixels
     * @return    tile width, in pixels */
    private int getTileWidth()
    {
        return map.getTileWidth();
    }

    /** Tile height, in pixels
     * @return    tile height, in pixels */
    private int getTileHeight()
    {
        return map.getTileHeight();
    }
}
