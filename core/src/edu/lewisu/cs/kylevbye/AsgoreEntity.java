package edu.lewisu.cs.kylevbye;

/**
 * This class holds the health values, along with the position of
 * Asgore himself.
 * 
 * @author	Kyle V Bye
 */
public class AsgoreEntity extends MobileScreenObject implements Entity {
	
	private float health;
	private float maxHealth;
	
	///
	///	Getters
	///
	
	@Override
	public float getHealth() { return health; }
	
	@Override
	public float getMaxHealth() { return maxHealth; }
	
	///
	///	Setters
	///
	
	@Override
	public void setHealth(float healthIn) { 
		health = healthIn; 
		if (health < 0f) health = 0f;
		if (health > maxHealth) health = maxHealth;
	}
	
	@Override
	public void setMaxHealth(float maxHealthIn) { maxHealth = maxHealthIn; }
	
	///
	///	Functions
	///
	
	
	@Override
	public void attack(Entity otherIn, float damageIn) {
		
		otherIn.absorbDamage(damageIn);

	}
	
	@Override
	public void absorbDamage(float damageIn) {
		
		setHealth(health-damageIn);
		
	}
	
	
	///
	///	Constructors
	///
	
	/**
	 * Sets asgore at 0,0 with the provided image.
	 * @param	imageIn	Asgore Sprite
	 */
	public AsgoreEntity(Image imageIn) {
		
		this(0f, 0f, imageIn);
		
	}
	
	/**
	 * Sets Asgore at the provided coordinates with
	 * the provided Image as the sprite
	 * @param	xIn	x coordinate
	 * @param	yIn	y coordinate
	 * @param	imageIn	Asgore Sprite
	 */
	public AsgoreEntity(float xIn, float yIn, Image imageIn) {
		
		super(imageIn);
		setPosition(xIn, yIn);
		
	}
	
	///
	///	Destructor
	///
	
	/**
	 * Memory clean up
	 */
	public void dispose() {
		
		setMaxHealth(100);
		setHealth(maxHealth);
		
	}


}
