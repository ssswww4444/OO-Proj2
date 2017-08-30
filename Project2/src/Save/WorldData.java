package Save;
/* SWEN20003 Object Oriented Software Development
 * World Data
 * Author: Pei Yun Sun <peiyuns>
 */

import java.io.Serializable;
import java.util.Vector;

public class WorldData implements Serializable{
	
	// Vector of the item data
	private Vector<ItemData> itemData_vec;
	
	// Vector of the unit data
	private Vector<UnitData> unitData_vec;
	
	/** Constructor */
	public WorldData() {
		itemData_vec = new Vector<ItemData>();
		unitData_vec = new Vector<UnitData>();
	}
	
	/** Get item data vector 
	 * @return    vector of the item data */
	public Vector<ItemData> getItemDataVec() {
		return itemData_vec;
	}
	
	/** Get unit data vector 
	 * @return    vector of the unit data */
	public Vector<UnitData> getUnitDataVec() {
		return unitData_vec;
	}
}
