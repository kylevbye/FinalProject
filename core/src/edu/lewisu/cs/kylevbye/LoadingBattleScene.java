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
 * @author byekv
 *
 */
public class LoadingBattleScene {
	
	private int counter;
	
	private OrthographicCamera cam;
	private Batch batch;
	
	private int WIDTH, HEIGHT;
	
	private MobileScreenObject soulSprite;
	private Sound battleStartSound;
	private PNGAnimatedMobileScreenObject knifeAnimation;
	
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
	
	public void render() {
		
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		
		
		if (counter < 30) {
			if (counter % 5 == 0) {
				//int fC = knifeAnimation.getFrameCount();
				//if (fC == knifeAnimation.getFrames().size()-1) fC = 0;
				//else fC += 1;
				//knifeAnimation.setFrameCount(fC);
			}
			knifeAnimation.play();
			knifeAnimation.draw(batch, 1f);
			
		}
		
		if (counter == 120) AssetManager.addToSoundQueue(battleStartSound);
		
		if ((counter > 120 && counter < 150) && counter%10 == 0) {
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
	
	public void dispose() {
		
		soulSprite.dispose();
		battleStartSound.dispose();
		
	}

}
