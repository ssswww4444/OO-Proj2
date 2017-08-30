package Save;
/* SWEN20003 Object Oriented Software Development
 * Item Data
 * Author: Pei Yun Sun <peiyuns>
 */

import java.io.Serializable;

public class ItemData implements Serializable {
	
	// Whether the item is picked up
	private boolean picked;

	/** Constructor
	 * @param  picked  true if the item is picked up, otherwise false
	 */
    public ItemData(boolean picked) {
    	this.picked = picked;
    }
    
    /** Check if the item is picked up
     * @return    true if the item is picked up, otherwise false */
    public boolean pickedUp() {
    	return picked;
    }
}

