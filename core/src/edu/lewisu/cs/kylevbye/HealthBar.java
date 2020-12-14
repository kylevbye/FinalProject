package edu.lewisu.cs.kylevbye;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;

public class HealthBar extends Rectangle {
	
	private Color healthRectangleColor;
	private Rectangle healthRectangle;
	private float health, maxHealth;
	
	///
	///	Getters
	///
	
	public Color getHealthRectangleColor() { return healthRectangleColor; }
	public Rectangle getHealthRectangle() { return healthRectangle; }
	public float getHealth() { return health; }
	public float getMaxHealth() { return maxHealth; }
	
	///
	///	Setters
	///
	
	public void setHealthRectangleColor(Color healthRectangleColorIn) { 
		healthRectangleColor = healthRectangleColorIn;
		if (healthRectangle != null) generateHealthRectangle();
	}
	public void setHealthRectangle(Rectangle healthRectangleIn) { 
		healthRectangle = healthRectangleIn; 
	}
	public void setHealth(float healthIn) { 
		health = healthIn; 
		if (health > maxHealth) health = maxHealth;
		if (health < 0) health = 0;
		if (healthRectangle != null) generateHealthRectangle();
	}
	public void setMaxHealth(float maxHealthIn) { 
		maxHealth = maxHealthIn; 
		if (healthRectangle != null) generateHealthRectangle();
	}
	///
	///	Functions
	///
	
	@Override
	public void draw(Batch batchIn, float parentAlphaIn) {
		super.draw(batchIn, parentAlphaIn);
		healthRectangle.draw(batchIn, parentAlphaIn);
	}
	
	@Override
	protected void positionChanged() {
		super.positionChanged();
		if (healthRectangleColor != null) generateHealthRectangle();
	}
	
	///
	///	Constructors
	///
	
	public HealthBar(float xIn, float yIn, float widthIn, float heightIn, float healthIn, 
			float maxHealthIn, Color healthColorIn, Color barColorIn) {
		super(xIn, yIn, widthIn, heightIn, barColorIn);
		setHealthRectangleColor(healthColorIn);
		setHealth(healthIn);
		setMaxHealth(maxHealthIn);
		generateHealthRectangle();
	}
	
	///
	///	Helpers
	///
	
	public void generateHealthRectangle() {
		
		if (healthRectangle != null) healthRectangle.dispose();
		
		float width, height;
		
		width = (health/maxHealth)*getWidth();
		height = getHeight();
		
		if (width < 0) width = 0;
		
		Rectangle newRectangle= new Rectangle(getX(), getY(), width, height, healthRectangleColor);
		setHealthRectangle(newRectangle);
	}
	
	///
	///	Destructors
	///
	
	public void dispose() {
		
		super.dispose();
		healthRectangle.dispose();
		
	}

}
