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
	private Label dialogue1, dialogue2;
	private Label dialogue3, dialogue4;
	private Rectangle dR1, dR2, dR3, dR4;
	private float finalOpaq;
	
	///
	///	Local Assets
	///
	
	private ScreenObject soul;
	private ScreenObject soulShatter;
	private MobileScreenObject[] soulShards;
	private Sound soulbreakSound;
	private Sound soulShatterSound;
	private Sound asgoreDialogue;
	private Music gameOverMusic;
	
	///
	///	Functions
	///
	
	public void create(OrthographicCamera camIn, Batch batchIn) {
		
		counter = 0;
		finalOpaq = 1.0f;
		
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
		
		//	Dialog Text
		LabelStyle dialogueLabelStyle = new LabelStyle();
		dialogueLabelStyle.fontColor = new Color(1,1,1,1);
		dialogueLabelStyle.font = new BitmapFont(Gdx.files.internal("fonts/undertale.fnt"));
		dialogue1 = new Label("You cannot give", dialogueLabelStyle);
		dialogue2 = new Label("up just yet...", dialogueLabelStyle);
		dialogue3 = new Label("Chara!", dialogueLabelStyle);
		dialogue4 = new Label("Stay determined...", dialogueLabelStyle);
		dR1 = new Rectangle(0, 0, dialogue1.getWidth()+3, dialogue1.getHeight(), new Color(0f,0f,0f,1));
		dR2 = new Rectangle(0, 0, dialogue1.getWidth()+3, dialogue1.getHeight(), new Color(0f,0f,0f,1));
		dR3 = new Rectangle(0, 0, dialogue3.getWidth()+3, dialogue1.getHeight(), new Color(0f,0f,0f,1));
		dR4 = new Rectangle(0, 0, dialogue4.getWidth()+3, dialogue1.getHeight(), new Color(0f,0f,0f,1));
		
		//	Local Assets
		soul = new ScreenObject(AssetManager.loadImage("soul.png"));
		soulShatter = new ScreenObject(AssetManager.loadImage("gameOverSoulShatter.png"));
		soul.setPosition(WIDTH/2-soul.getWidth()/2, HEIGHT/2 - HEIGHT/4);
		soulShatter.setPosition(WIDTH/2-soul.getWidth()/2, HEIGHT/2 - HEIGHT/4);
		soulShards = new MobileScreenObject[6];
		soulbreakSound = AssetManager.loadSound("soulBreak.mp3");
		soulShatterSound = AssetManager.loadSound("soulShatter.mp3");
		asgoreDialogue = AssetManager.loadSound("asgoreDialog.mp3");
		gameOverMusic = AssetManager.loadMusic("determination.mp3");
		gameOverMusic.setVolume(.2f);
		
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
		
		//	Dialogue Labels
		dialogue1.setPosition(WIDTH/2 - dialogue1.getWidth()/2, HEIGHT - 3.2f*HEIGHT/4 + dialogue1.getHeight());
		dialogue2.setPosition(WIDTH/2 - dialogue2.getWidth()/2, HEIGHT - 3.2f*HEIGHT/4);
		dialogue3.setPosition(WIDTH/2 - dialogue1.getWidth()/2, HEIGHT - 3.2f*HEIGHT/4 + dialogue1.getHeight());
		dialogue4.setPosition(WIDTH/2 - dialogue1.getWidth()/2, HEIGHT - 3.2f*HEIGHT/4);
		if (counter < 500) {
			dR1.setPosition(dialogue1.getX()-2, dialogue1.getY());
			dR2.setPosition(dialogue2.getX()-2, dialogue2.getY());
			dR3.setPosition(dialogue3.getX()-2, dialogue1.getY());
			dR4.setPosition(dialogue4.getX()-2, dialogue2.getY());
		}
		
		if (counter > 250 && counter < 900 || counter > 900) dR1.moveRight(3f);
		if (counter > 610 && counter < 900 || counter > 1300) dR2.moveRight(3f);
		if (counter > 900) dR3.moveRight(3f);
		if (counter > 1000) dR4.moveRight(3f);
		
		// Apply Fade in the end
		if (counter > 1300) {
			finalOpaq -= .005f ;
			gameOverLabel.setColor(1,1,1, finalOpaq);
		}
		
		///	Drawing
		for (Drawable d : drawables) AssetManager.getRenderQueue().add(d);
		gameOverLabel.draw(batch, 1.f); 
		
		if (counter > 200 && counter < 900) {
			dialogue1.draw(batch, finalOpaq);
			dialogue2.draw(batch, finalOpaq);
			dR1.draw(batch, finalOpaq);
			dR2.draw(batch, finalOpaq);
		}
		else if (counter >= 900) {
			dialogue3.draw(batch, finalOpaq);
			dialogue4.draw(batch, finalOpaq);
			dR3.draw(batch, finalOpaq);
			dR4.draw(batch, finalOpaq);
		}
		
		
		
		++counter;
		
		//	Check End
		if (counter == 1600) FinalProject.scene = FinalProject.SceneConstants.TITLE;
		
	}
	
	public void dispose() {
		
		soul.dispose();
		soulShatter.dispose();
		for (MobileScreenObject m : soulShards) m.dispose();
		soulbreakSound.dispose();
		soulShatterSound.dispose();
		asgoreDialogue.dispose();
		gameOverMusic.dispose();
		
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
					gameOverMusic.setVolume(volumeIn*gameOverMusic.getVolume()); 
					gameOverMusic.play(); 
				}
				public void play(float volumeIn, float pitchIn, float panIn) { 
					gameOverMusic.setVolume(volumeIn); 
					gameOverMusic.setPan(panIn, volumeIn);
					gameOverMusic.play();
				}
			});
			
		}
		
		if (((counter > 480 && counter < 680) || (counter > 890 && counter < 930) || 
				(counter > 1000 && counter < 1100)) && counter % 16 == 0) {
			AssetManager.getSoundQueue().add(new Playable() {
				public void play() { asgoreDialogue.play(1.9f); }
				public void play(float volumeIn) { 
					asgoreDialogue.play(volumeIn*1.9f);
				}
				public void play(float volumeIn, float pitchIn, float panIn) { 
					asgoreDialogue.play(volumeIn*1.9f, pitchIn, panIn);
				}
			});
		}
		
		if (counter > 1300) gameOverMusic.setVolume(gameOverMusic.getVolume()-.0008f);
		
		
	}

}
