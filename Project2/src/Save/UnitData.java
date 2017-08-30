package Save;
/* SWEN20003 Object Oriented Software Development
 * Unit Data
 * Author: Pei Yun Sun <peiyuns>
 */

import java.io.Serializable;

public class UnitData implements Serializable {
	
	// Position of the unit on the map
	private double xPos;
	private double yPos;
	
	// HP and Max Hp of the unit
	private int hp;
	private int maxHp;
	
	// Damage of the unit
	private int damage;
	
	// CoolDown of the unit
	private int cd;
	private int maxCd;

	/** Constructor
	 * @param  xPos  x coordinate of the position on the map
	 * @param  yPos  y coordinate of the position on the map
	 * @param  hp  current hit point (HP) of the unit
	 * @param  maxHp  maximum hit point (HP) of the unit
	 * @param  damage  damage of the unit
	 * @param  cd  current cooldown (CD) of the unit
	 * @param  maxCd maximum cooldown (CD) of the unit */
    public UnitData(double xPos, double yPos, int hp, int maxHp, int damage, int cd, int maxCd) {
    	this.xPos = xPos;
    	this.yPos = yPos;
    	this.hp = hp;
    	this.maxHp = maxHp;
    	this.damage = damage;
    	this.cd = cd;
    	this.maxCd = maxCd;
    }
    
    /** Get xPos of the unit 
     * @return    x coordinate of the position on the map */
    public double getXPos() {
    	return xPos;
    }
    
    /** Get yPos of the unit 
     * @return    y coordinate of the position on the map */
    public double getYPos() {
    	return yPos;
    }
    
    /** Get hp of the unit 
     * @return    hit point (HP) of the unit */
    public int getHp() {
    	return hp;
    }
    
    /** Get max hp of the unit 
     * @return    maximum hit point (HP) of the unit */
    public int getMaxHp() {
    	return maxHp;
    }
    
    /** Get damage of the unit 
     * @return    damage of the unit */
    public int getDamage() {
    	return damage;
    }
    
    /** Get cd of the unit 
     * @return    cooldown (CD) of the unit */
    public int getCd() {
    	return cd;
    }
    
    /** Get max cd of the unit 
     * @return    maximum cooldown (CD) of the unit */
    public int getMaxCd() {
    	return maxCd;
    }    
}
