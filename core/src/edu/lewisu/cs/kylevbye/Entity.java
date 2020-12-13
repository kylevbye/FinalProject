package edu.lewisu.cs.kylevbye;

public interface Entity {
	
	///
	///	Force Getters
	///
	
	public float getHealth();
	public float getMaxHealth();
	
	///
	///	Force Setters
	///
	
	public void setHealth(float healthIn);
	public void setMaxHealth(float healthIn);
	
	///
	/// Functions
	///
	
	/**
	 * Takes the "other" instance and reduces the health points
	 * by the provided damage value.
	 * 
	 * @param	otherIn	entity to damage
	 * @param	damageIn	how much to damage by
	 */
	public void attack(Entity otherIn, float damageIn);
	
	/**
	 * Takes this instance and reduces the health points by 
	 * the provided damage value.
	 * 
	 * @param	damageIn	damage that will be taken.
	 */
	public void absorbDamage(float damageIn);

}
