package edu.lewisu.cs.kylevbye;

public class PlayerEntity extends MobileScreenObject {
	
	///
	///	Field
	///
	
	private Image[] soulSprites;
	
	///
	///	Getters
	///
	
	public Image[] getSoulSprites() { return soulSprites; }
	
	///
	///	Setters
	///
	
	public void setSoulSprites(Image[] soulSpritesIn) { soulSprites = soulSpritesIn; }
	
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
		
		for (Image i : soulSprites) i.dispose();
		
	}
	

}
