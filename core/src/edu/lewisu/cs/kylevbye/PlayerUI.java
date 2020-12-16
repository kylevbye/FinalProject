package edu.lewisu.cs.kylevbye;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

/**
 * This class holds the healthbar you see in the game.
 * @author	Kyle V Bye
 */
public class PlayerUI extends Actor {

	private Label hpLabel, healthLabel;
	private HealthBar healthBar;

	///
	/// Getters
	///
	
	public Label getHPLabel() { return hpLabel; }
	public Label getHealthLabel() { return healthLabel; }
	public HealthBar getHealthBar() { return healthBar; }

	///
	/// Setters
	///
	
	public void setHPLabel(Label hpLabelIn) { hpLabel = hpLabelIn; }
	public void setHealthLabel(Label healthLabelIn) { healthLabel = healthLabelIn; }
	public void setHealthBar(HealthBar healthBarIn) { healthBar = healthBarIn; }

	///
	/// Functions
	///
	
	@Override
	public void draw(Batch batchIn, float parentAlphaIn) {
		
		hpLabel.draw(batchIn, parentAlphaIn);
		healthLabel.draw(batchIn, parentAlphaIn);
		healthBar.draw(batchIn, parentAlphaIn);
		
	}
	
	@Override
	protected void positionChanged() {
		
		super.positionChanged();
		
		if (hpLabel != null) {
			hpLabel.setX(getX());
			hpLabel.setY(getY()+(hpLabel.getWidth()/3));
		}
		
		if (healthBar != null) {
			healthBar.setPosition(hpLabel.getX()+3*hpLabel.getWidth()/2, getY());
		}
		
		if (healthLabel != null) {
			healthLabel.setX(healthBar.getX()+healthBar.getWidth()+getWidth()/20);
			healthLabel.setY(getY());
		}
		
	}
	
	/**
	 * Updates the health bar based on the health values provided
	 * @param	newHealth	new health value
	 */
	public void update(float newHealth) {
		healthBar.setHealth(newHealth);
		String labelStr = String.format("%d/%d", (int)healthBar.getHealth(), (int)healthBar.getMaxHealth());
		healthLabel.setText(labelStr); 
	}
	
	
	///
	///	Constructors
	///
	
	public PlayerUI(float xIn, float yIn, float widthIn, float heightIn, float maxHealthIn) {
		
		super();
		
		setX(xIn); setY(yIn);
		setWidth(widthIn); setHeight(heightIn);
		
		BitmapFont font = new BitmapFont(Gdx.files.internal("fonts/undertale.fnt"));
		LabelStyle style = new LabelStyle(font, Color.WHITE);
		hpLabel = new Label("HP", style);
		hpLabel.setFontScale(.4f);
		hpLabel.setHeight(heightIn*.4f);
		hpLabel.setWidth(hpLabel.getWidth()*.4f);
		hpLabel.setX(xIn);
		hpLabel.setY(yIn+(hpLabel.getWidth()/3));
		
		HealthBar bar = new HealthBar(hpLabel.getX()+3*hpLabel.getWidth()/2, getY(), widthIn/2, heightIn, 0, maxHealthIn, Color.YELLOW, Color.RED);
		setHealthBar(bar);
		
		healthLabel = new Label("", style);
		healthLabel.setHeight(heightIn);
		healthLabel.setFontScale(.7f);
		healthLabel.setX(bar.getX()+bar.getWidth()+widthIn/20);
		healthLabel.setY(yIn);
		
		update(Float.MAX_VALUE);
		
	}
	
	///
	///	Destructors
	///
	
	public void dispose() {
		
		healthBar.dispose();
		
	}

}
