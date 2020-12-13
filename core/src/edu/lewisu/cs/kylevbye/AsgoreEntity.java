package edu.lewisu.cs.kylevbye;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Batch;

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
	///	Soul Sprites
	///
	
	public class SoulSprites {
		
		public static final int DETERMINATION = 0;
		public static final int BRAVERY = 1;
		public static final int JUSTICE = 2;
		public static final int KINDNESS = 3;
		public static final int PATIENCE = 4;
		public static final int INTEGRITY = 5;
		public static final int PERSEVERANCE = 6;
		
	}
	
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
	
	public AsgoreEntity(Image imageIn) {
		
		this(0f, 0f, imageIn);
		
	}
	
	public AsgoreEntity(float xIn, float yIn, Image imageIn) {
		
		super(imageIn);
		setPosition(xIn, yIn);
		
	}
	
	///
	///	Destructor
	///
	
	public void dispose() {
		
		setMaxHealth(100);
		setHealth(maxHealth);
		
	}


}
