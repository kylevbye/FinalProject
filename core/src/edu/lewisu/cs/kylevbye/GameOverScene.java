package edu.lewisu.cs.kylevbye;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class GameOverScene {
	
	private int counter;
	
	private int WIDTH, HEIGHT;
	private OrthographicCamera cam;
	private Batch batch;
	private ArrayList<Drawable> drawables;
	private Label gameOverLabel;
	
	///
	///	Local Assets
	///
	
	private ScreenObject soul;
	private ScreenObject soulShatter;
	private MobileScreenObject[] soulShards;
	private Sound soulbreakSound;
	private Sound soulShatterSound;
	private Music gameOverMusic;
	
	///
	///	Functions
	///
	
	public void create(OrthographicCamera camIn, Batch batchIn) {
		
		counter = 0;
		
		drawables = new ArrayList<Drawable>();
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		cam = camIn;
		batch = batchIn;
		
		//	Gameover Text
		LabelStyle gameOverLabelStyle = new LabelStyle();
		gameOverLabelStyle.fontColor = new Color(1,1,1,1);
		gameOverLabelStyle.font = new BitmapFont(Gdx.files.internal("fonts/undertaleGameOver.fnt"));
		gameOverLabel = new Label("GAME\nOVER", gameOverLabelStyle);
		gameOverLabel.setFontScale(2.5f, 2.5f);
		gameOverLabel.setAlignment(Align.center);
		
		//	Local Assets
		soul = new ScreenObject(AssetManager.loadImage("soul.png"));
		soulShatter = new ScreenObject(AssetManager.loadImage("gameOverSoulShatter.png"));
		soul.setPosition(WIDTH/2-soul.getWidth()/2, HEIGHT/2 - HEIGHT/4);
		soulShatter.setPosition(WIDTH/2-soul.getWidth()/2, HEIGHT/2 - HEIGHT/4);
		soulShards = new MobileScreenObject[6];
		soulbreakSound = AssetManager.loadSound("soulBreak.mp3");
		soulShatterSound = AssetManager.loadSound("soulShatter.mp3");
		gameOverMusic = AssetManager.loadMusic("determination.mp3");
		
		//	Manually set soul shards
		float soulCenterX = soul.getX(); float soulCenterY = soul.getY();
		for (int i = 0; i<soulShards.length; ++i) {
			soulShards[i] = new MobileScreenObject(AssetManager.loadImage("soulShard.png"));
			soulShards[i].setPosition(soulCenterX, soulCenterY);
			soulShards[i].centerOrigin();
			soulShards[i].setAcceleration(.9f);
			soulShards[i].setDeceleration(.8f);
		}
		
		
	}
	
	public void render() {
		
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		
		//	Sounds
		processSounds(counter);
		
		//	Soul
		soul.setPosition(WIDTH/2-soul.getWidth()/2, HEIGHT/2 - HEIGHT/4);
		soulShatter.setPosition(WIDTH/2-soul.getWidth()/2, HEIGHT/2 - HEIGHT/4);
		if (counter < 15) soul.draw(batch, 1f);
		else if (counter < 100) soulShatter.draw(batch, 1f);
		
		if (counter < 100 && counter >= 80) {
			float soulSpeed = 3f;
			
			//	Script angles of each shard.
			soulShards[0].accelerateAtAngle(235);
			soulShards[1].accelerateAtAngle(135);
			soulShards[2].accelerateAtAngle(90);
			soulShards[3].accelerateAtAngle(82);
			soulShards[4].accelerateAtAngle(45);
			soulShards[5].accelerateAtAngle(35);
			
			
		}
		
		if (counter > 100 && counter < 1200) {
			for (int i = 0; i<soulShards.length; ++i) {
				soulShards[i].applyPhysics(1f);
				soulShards[i].rotate(3f);
				soulShards[i].moveDown(2.6f);
				soulShards[i].draw(batch, 1f);
			}
		}
		
		
		//	GameOverLabel
		gameOverLabel.setPosition(WIDTH/2 - gameOverLabel.getWidth()/2, HEIGHT - HEIGHT/3);
		
		float labelOpaq;
		if (counter > 200 && counter < 800) labelOpaq = ((float)counter-200)/100;
		else if (counter >= 800) labelOpaq = 1f;
		else labelOpaq = 0f;
		
		gameOverLabel.setColor(1,1,1, labelOpaq);
		
		for (Drawable d : drawables) AssetManager.getRenderQueue().add(d);
		gameOverLabel.draw(batch, 1.f); 
		
		++counter;
		
	}
	
	public void dispose() {
		
	}
	
	///
	///	Helpers
	///
	
	private void processSounds(int counterIn) {
		
		if (counter == 13) { 
			
			System.out.println("Soul Break");
			
			AssetManager.getSoundQueue().add(new Playable() {
				public void play() { soulbreakSound.play(); }
				public void play(float volumeIn) { soulbreakSound.play(volumeIn); }
				public void play(float volumeIn, float pitchIn, float panIn) {soulbreakSound.play(volumeIn, pitchIn, panIn);}
			});
			
		}
		
		if (counter == 100) {
			
			System.out.println("Soul Shatter");
			
			AssetManager.getSoundQueue().add(new Playable() {
				public void play() { soulShatterSound.play(); }
				public void play(float volumeIn) { soulShatterSound.play(volumeIn); }
				public void play(float volumeIn, float pitchIn, float panIn) { soulShatterSound.play(volumeIn, pitchIn, panIn);}
			});
		}
		
		if (counter == 190) {
			
			System.out.println("GAMEOVER");
			
			AssetManager.getSoundQueue().add(new Playable() {
				public void play() { gameOverMusic.play(); }
				public void play(float volumeIn) { 
					gameOverMusic.setVolume(volumeIn); 
					gameOverMusic.play(); 
				}
				public void play(float volumeIn, float pitchIn, float panIn) { 
					gameOverMusic.setVolume(volumeIn); 
					gameOverMusic.setPan(panIn, volumeIn);
					gameOverMusic.play();
				}
			});
			
		}
		
	}

}
