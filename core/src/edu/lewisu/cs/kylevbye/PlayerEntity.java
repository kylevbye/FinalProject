package edu.lewisu.cs.kylevbye;

import com.badlogic.gdx.math.Polygon;

/**
 * This class is the soul you control in the game.
 * 
 * @author	Kyle V Bye
 *
 */
public class PlayerEntity extends MobileScreenObject implements Entity {
	
	///
	///	Fields
	///
	
	/*
	 * Unused
	 */
	private Image[] soulSprites;
	
	
	private float health;
	private float maxHealth;
	
	///
	///	Getters
	///
	
	public Image[] getSoulSprites() { return soulSprites; }
	
	@Override
	public float getHealth() { return health; }
	
	@Override
	public float getMaxHealth() { return maxHealth; }
	
	///
	///	Setters
	///
	
	public void setSoulSprites(Image[] soulSpritesIn) { soulSprites = soulSpritesIn; }
	
	@Override
	public void setHealth(float healthIn) { 
		health = healthIn; 
		if (health < 0f) health = 0f;
		if (health > maxHealth) health = maxHealth;
	}
	
	public void setMaxHealth(float maxHealthIn) { maxHealth = maxHealthIn; }
	
	///
	///	Soul Sprites
	///
	
	/**
	 * Unused.
	 */
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
	public void initBoundingPolygon() {
		
		//	Since only one type of sprite is used
		//	Manually setting the polygon is fine.
		float[] vertices = new float[24];
		
		vertices[0] = 8;
        vertices[1] = 14;
        
        vertices[2] = 8;
        vertices[3] = 21;
        
        vertices[4] = 10;
        vertices[5] = 23;
        
        vertices[6] = 11;
        vertices[7] = 23;
        
        vertices[8] = 15;
        vertices[9] = 19;
        
        vertices[10] = 16;
        vertices[11] = 19;
        
        vertices[12] = 20;
        vertices[13] = 23;
        
        vertices[14] = 21;
        vertices[15] = 23;
        
        vertices[16] = 23;
        vertices[17] = 21;
        
        vertices[18] = 23;
        vertices[19] = 14;
        
        vertices[20] = 14;
        vertices[21] = 8;
        
        vertices[22] = 17;
        vertices[23] = 8;
        
		boundingPolygon = new Polygon(vertices);
		
	}
	
	@Override
	public void attack(Entity otherIn, float damageIn) {
		
		otherIn.absorbDamage(damageIn);

	}
	
	@Override
	public void absorbDamage(float damageIn) {
		
		setHealth(health-damageIn);
		
	}
	
	public void selectSoulSprite(int soulSpriteIn) { 
		
		Image selectedSprite = soulSprites[soulSpriteIn];
		setTextureRegion(selectedSprite.getTextureRegion()); 
		setWidth(selectedSprite.getWidth());
		setHeight(selectedSprite.getHeight());
		
	} 
	
	///
	///	Constructors
	///
	
	public PlayerEntity(Image[] soulSprites) {
		
		super(soulSprites[0]);
		
	}
	
	///
	///	Destructor
	///
	
	public void dispose() {
		
		if (soulSprites != null) for (Image i : soulSprites) i.dispose();
		setMaxHealth(100);
		setHealth(maxHealth);
		
	}
	

}
