package edu.lewisu.cs.kylevbye;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * This scene is for when the player beats asgore.
 * 
 * @author Kyle V Bye
 *
 */
public class GameWinScene {
	
	private int counter;
	private int WIDTH, HEIGHT;
	private OrthographicCamera cam;
	private Batch batch;
	
	///
	///	Local Assets
	///
	
	private ScreenObject[] souls;
	private ScreenObject[] soulBreaks;
	private MobileScreenObject[][] soulsShards;
	private Sound soulbreakSound;
	private Sound soulShatterSound;
	
	/**
	 * Sets the scene and scripts.
	 * 
	 * @param	camIn	cam to play the scene
	 * @param	batchIn	batch to draw the scene on
	 */
	public void create(OrthographicCamera camIn, Batch batchIn) {
		
		counter = 0;
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		cam = camIn;
		batch = batchIn;
		
		//	Local Assets
		soulbreakSound = AssetManager.loadSound("soulBreak.mp3");
		soulShatterSound = AssetManager.loadSound("soulShatter.mp3");
		souls = new ScreenObject[7];
		soulBreaks = new ScreenObject[souls.length];
		soulsShards = new MobileScreenObject[souls.length][souls.length];
		for (int i = 0; i<souls.length; ++i) {
			souls[i] = loadSoul(WIDTH/2, HEIGHT/2 + HEIGHT/4, null);
			setSoulPosition(souls[i], i);
			setSoulColor(souls[i], i);
			soulBreaks[i] = new ScreenObject(AssetManager.loadImage("monsterSoulShatter.png"));
			soulBreaks[i].setColor(souls[i].getColor());
			soulBreaks[i].setPosition(souls[i].getX(), souls[i].getY());
			soulsShards[i]= loadSoulShards(souls[i].getX(), souls[i].getY(), null);
			for (int j = 0; j<soulsShards.length-1; ++j) soulsShards[i][j].setColor(souls[i].getColor());
		}
		
	}
	
	/**
	 * Draws the scene
	 */
	public void render() {
		
		for (int i = 0; i<souls.length-1; ++i) manageSoul(i, 60);
		manageSoul(souls.length-1, 80);
		
		if (counter == 1000) FinalProject.scene = FinalProject.SceneConstants.TITLE;
			
		++counter;
		
	}
	
	/**
	 * Resets the scene to be played again.
	 */
	public void reset() {

		counter = 0;
		dispose();
		create(cam,batch);
		
	}
	
	///
	///	Helpers
	///
	
	/*
	 * Loads the soul into memory.
	 */
	private ScreenObject loadSoul(float xIn, float yIn, Color colorIn) {
		ScreenObject soul = new ScreenObject(AssetManager.loadImage("monsterSoul.png"));
		
		if (colorIn != null) soul.setColor(colorIn);
		soul.setPosition(xIn-soul.getWidth()/2, yIn-soul.getHeight()/2);
		
		return soul;
	}
	
	private MobileScreenObject[] loadSoulShards(float x, float y, Color color) {
		
		MobileScreenObject[] soulShards = new MobileScreenObject[6];
		
		for (int i = 0; i<soulShards.length; ++i) {
			soulShards[i] = new MobileScreenObject(AssetManager.loadImage("monsterSoulShard.png"));
			soulShards[i].setPosition(x, y);
			soulShards[i].centerOrigin();
			soulShards[i].setAcceleration(.9f);
			soulShards[i].setDeceleration(.8f);
			if (color != null) soulShards[i].setColor(color);
		}
		
		return soulShards;
		
	}
	
	private void applySoulPhysics(MobileScreenObject[] soulShards, int breakCount, int shatterCount ) {
		
		if (counter < shatterCount && counter >= breakCount) {
			
			float soulSpeed = 3f;
			
			//	Script angles of each shard.
			soulShards[0].accelerateAtAngle(235);
			soulShards[1].accelerateAtAngle(135);
			soulShards[2].accelerateAtAngle(90);
			soulShards[3].accelerateAtAngle(82);
			soulShards[4].accelerateAtAngle(45);
			soulShards[5].accelerateAtAngle(35);
			
			
		}
	
		if (counter > shatterCount && counter < 1200) {
			
			for (int i = 0; i<soulShards.length; ++i) {
				
				soulShards[i].applyPhysics(1f);
				soulShards[i].rotate(3f);
				soulShards[i].moveDown(2.6f);
				soulShards[i].draw(batch, 1f);
				
			}
			
		}
		
	}
	
	/*
	 * Runs the script of the soul.
	 */
	private void manageSoul(int soulIndex, int dt) {
		
		if (counter < 15 + dt*soulIndex) souls[soulIndex].draw(batch, 1f);
		else if (counter < 100 + dt*soulIndex) soulBreaks[soulIndex].draw(batch, 1f);
		if (counter == 15 + dt*soulIndex) AssetManager.addToSoundQueue(soulbreakSound);
		if (counter == 100 + dt*soulIndex) AssetManager.addToSoundQueue(soulShatterSound);
		
		applySoulPhysics(soulsShards[soulIndex], 80 + dt*soulIndex, 100 + dt*soulIndex);
		
	}
	
	/*
	 * Sets the soul to its scripted position.
	 */
	private void setSoulPosition(ScreenObject soul, int soulIndex) {
		
		float x, y;
		
		x = soul.getX(); y = soul.getY();
		
		switch (soulIndex) {
		
		case 0:
			x += soul.getWidth()/2 + WIDTH/8;
			break;
			
		case 1:
			x -= soul.getWidth()/2 + WIDTH/8;
			break;
			
		case 2:
			x -= soul.getWidth()/2 + WIDTH/16;
			y += HEIGHT/12;
			break;
			
		case 3:
			x += soul.getWidth()/2 + WIDTH/16;
			y += HEIGHT/12;
			break;
			
		case 4:
			x += soul.getWidth()/2 + WIDTH/16;
			y -= HEIGHT/12;
			break;
			
		case 5:
			x -= soul.getWidth()/2 + WIDTH/16;
			y -= HEIGHT/12;
			break;
			
		}
		
		soul.setPosition(x, y);
		
	}
	
	/*
	 * Sets the souls scripted color.
	 */
	private void setSoulColor(ScreenObject soul, int soulIndex) {
		
		switch (soulIndex) {
		
		case 0:
			soul.setColor(Color.ORANGE);
			break;
			
		case 1:
			soul.setColor(Color.BLUE);
			break;
			
		case 2:
			soul.setColor(Color.CYAN);
			break;
			
		case 3:
			soul.setColor(Color.PURPLE);
			break;
			
		case 4:
			soul.setColor(Color.YELLOW);
			break;
			
		case 5:
			soul.setColor(0,100f/255f,0,1);
			break;
			
		}
		
	}
	
	
	///
	///	Destructors
	///
	/**
	 * Clean up
	 */
	public void dispose() {
		
		soulbreakSound.dispose();
		soulShatterSound.dispose();
		
		soulbreakSound = null;
		soulShatterSound = null;
		
		for (int i = 0; i< souls.length; ++i) {
			souls[i].dispose();
			soulBreaks[i].dispose();
			for (int j = 0; j<soulsShards[i].length; ++j) {
				soulsShards[i][j].dispose();
			}
		}
		
	}

}
