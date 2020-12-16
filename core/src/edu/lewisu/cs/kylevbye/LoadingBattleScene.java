/**
 * 
 */
package edu.lewisu.cs.kylevbye;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * This scene is simulating Undertale's battle start
 * sequence.
 * 
 * @author Kyle V Bye
 */
public class LoadingBattleScene {
	
	private int counter;
	private OrthographicCamera cam;
	private Batch batch;
	private int WIDTH, HEIGHT;
	
	///
	///	Local Assets
	///
	
	private MobileScreenObject soulSprite;
	private Sound battleStartSound;
	private PNGAnimatedMobileScreenObject knifeAnimation;
	
	///
	///	Functions
	///
	
	/**
	 * Sets the scene up.
	 * @param	camIn	cam to play the scene
	 * @param	batchIn	batch to draw the scene on
	 */
	public void create(OrthographicCamera camIn, Batch batchIn) {
		
		counter = 0;
		
		cam = camIn;
		batch = batchIn;
		
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		
		soulSprite = new MobileScreenObject(AssetManager.loadImage("soultypes/determination.png"));
		soulSprite.setPosition(WIDTH/2-soulSprite.getWidth()/2, HEIGHT/2 - 7*HEIGHT/16);
		
		battleStartSound = AssetManager.loadSound("battleStart.mp3");
		
		knifeAnimation = AssetManager.loadPNGAnimatedSprite("knifeslash", "knife", 6);
		knifeAnimation.setPosition(WIDTH/2-knifeAnimation.getWidth()/2, HEIGHT/2);
		knifeAnimation.setScale(3f);
		knifeAnimation.setFrameDelay(5);
		
	}
	
	/**
	 * Draws the scene to the screen.
	 */
	public void render() {
		
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		
		
		if (counter < 30) {
			
			knifeAnimation.play();
			knifeAnimation.draw(batch, 1f);
			
		}
		
		if (counter == 120) AssetManager.addToSoundQueue(battleStartSound);
		
		if ((counter > 120 && counter < 150) && counter % 10 == 0) {
			soulSprite.draw(batch, 1f);
		}
		
		if (counter >= 150 && counter < 160) {
			soulSprite.moveUp(15f);
		}
		
		if (counter >= 150 && counter < 200) {
			soulSprite.draw(batch, 1f);
		}
		
		
		if (counter == 205) FinalProject.scene = FinalProject.SceneConstants.BATTLE;
		
		
		++counter;

	}
	
	/**
	 * Resets the scene to be played again.
	 */
	public void reset() {
		
		counter = 0;
		
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		
		soulSprite.setPosition(WIDTH/2-soulSprite.getWidth()/2, HEIGHT/2 - 7*HEIGHT/16);
		knifeAnimation.setPosition(WIDTH/2-knifeAnimation.getWidth()/2, HEIGHT/2);
		
	}
	
	///
	///	Destructor
	///
	
	/**
	 * Clean up
	 */
	public void dispose() {
		
		soulSprite.dispose();
		battleStartSound.dispose();
		
		soulSprite.setPosition(WIDTH/2-soulSprite.getWidth()/2, HEIGHT/2 - 7*HEIGHT/16);
		
		knifeAnimation.setPosition(WIDTH/2-knifeAnimation.getWidth()/2, HEIGHT/2);
		knifeAnimation.setScale(3f);
		knifeAnimation.setFrameDelay(5);
		
	}

}
