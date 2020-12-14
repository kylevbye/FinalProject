package edu.lewisu.cs.kylevbye;

import java.awt.color.CMMException;
import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

import edu.lewisu.cs.cpsc41000.common.cameraeffects.CameraEffect;
import edu.lewisu.cs.cpsc41000.common.cameraeffects.CameraShake;

public class BattleScene {
	
	private int counter;
	
	private OrthographicCamera cam;
	private Batch batch;
	
	private BattleController battleController;
	
	private static int stage;
	private static AsgoreDialogue aDiag = new AsgoreDialogue();
	private int WIDTH, HEIGHT;
	private ArrayList<Drawable> drawables;
	private int state;
	private AsgoreAttack currentAsgoreAttack;
	
	private PlayerEntity player;
	private AsgoreEntity asgore;
	private BattleButtonUI buttonUI;
	private PlayerUI playerUI;
	private PNGAnimatedMobileScreenObject playerAttackAnim;
	private Sound attackSlashSound;
	private Sound asgoreHurtSound;
	private Music asgoreIntroMusic;
	private Music asgoreBattleMusic;
	
	private Label damageLabel;
	private HealthBar asgoreHealthBar;
	
	private CameraEffect cameraShaker;
	
	private float asgoreDamage;
	private float asgoreTrans;
	private boolean played;
	
	public class BattleSceneConstants {
		
		//	During Battle
		public static final int INITIAL_DIALOGUE = 0;
		public static final int FIRST_STAGE = 1;
		public static final int SECOND_STAGE = 2;
		public static final int THIRD_STAGE = 3;
		public static final int LAST_STAND_DIALOGUE = 4;
		public static final int LAST_STAND = 5;
		
		//	Game Over
		public static final int GAME_OVER = 6;
		
		//	States
		public static final int ATTACK = 0;
		public static final int DAMAGE = 1;
		public static final int DEFEND = 2;
		public static final int PLAN = 3;
	
	}
	
	///
	///	Getters
	///
	
	public Sound getAttackSlashSound() { return attackSlashSound; }
	
	///
	///	Setters
	///
	
	public void setState(int stateIn) { state = stateIn; }
	
	public void create(OrthographicCamera camIn, Batch batchIn) {

		counter = 0;
		state = BattleSceneConstants.PLAN;
		asgoreDamage = 0f;
		asgoreTrans = 1f;
		
		cam = camIn;
		batch = batchIn;
		
		stage = BattleSceneConstants.INITIAL_DIALOGUE;
		drawables = new ArrayList<Drawable>();
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		
		//	Load Player
		Image[] soulSprites = new Image[7];
		soulSprites[0] = AssetManager.loadImage("soultypes/determination.png");
		soulSprites[1] = AssetManager.loadImage("soultypes/bravery.png");
		soulSprites[2] = AssetManager.loadImage("soultypes/justice.png");
		soulSprites[3] = AssetManager.loadImage("soultypes/kindness.png");
		soulSprites[4] = AssetManager.loadImage("soultypes/integrity.png");
		soulSprites[5] = AssetManager.loadImage("soultypes/patience.png");
		soulSprites[6] = AssetManager.loadImage("soultypes/perseverance.png");
		player = new PlayerEntity(soulSprites);
		player.setMaxHealth(100f);
		
		//	Load Asgore
		asgore = new AsgoreEntity(AssetManager.loadImage("asgore.png"));
		asgore.setScale(.6f);
		asgore.setPosition(
				WIDTH/2-(asgore.getWidth()*asgore.getScaleX())/2-(28*asgore.getScaleX()), 
				HEIGHT/2
				);
		asgore.setMaxHealth(19999980f);
		asgore.setHealth(Float.MAX_VALUE);
		
		//	Button UI
		Image[] buttons = new Image[6];
		buttons[0] = AssetManager.loadImage("battlebuttons/fight.png");
		buttons[1] = AssetManager.loadImage("battlebuttons/fightH.png");
		buttons[2] = AssetManager.loadImage("battlebuttons/act.png");
		buttons[3] = AssetManager.loadImage("battlebuttons/actH.png");
		buttons[4] = AssetManager.loadImage("battlebuttons/item.png");
		buttons[5] = AssetManager.loadImage("battlebuttons/itemH.png");
		buttonUI = new BattleButtonUI(buttons);
		buttonUI.setX(WIDTH/20);
		buttonUI.setY(HEIGHT/20);
		buttonUI.setGap(WIDTH/15);
		
		//	Player UI
		playerUI = new PlayerUI(
				WIDTH/2, buttonUI.getY() + buttonUI.getHeight() + HEIGHT/100 , 
				200, 15, player.getMaxHealth()
				);
		playerUI.setPosition(playerUI.getX()-playerUI.getWidth()/2, playerUI.getY());
		
		//	BattleController
		battleController = new BattleController(player, buttonUI, this);
		AsgoreAttack.middleX = WIDTH/2;
		AsgoreAttack.middleY = HEIGHT/3 + HEIGHT/10;
		AsgoreAttack.borderThickness = 4f;
		AsgoreAttack.player = player;
		
		//	Music
		asgoreIntroMusic = AssetManager.loadMusic("asgorePreBattle.mp3");
		asgoreIntroMusic.setLooping(false);
		asgoreBattleMusic = AssetManager.loadMusic("asgoreBattle.mp3");
		asgoreBattleMusic.setVolume(.3f);
		
		//	Attack sound
		attackSlashSound = AssetManager.loadSound("slash.mp3");
		
		//	Asgore hurt
		asgoreHurtSound = AssetManager.loadSound("asgoreDamaged.mp3");
		
		//	Player Attack
		playerAttackAnim = AssetManager.loadPNGAnimatedSprite("knifeslash", "knife", 6);
		playerAttackAnim.setPosition(WIDTH/2-playerAttackAnim.getWidth()/2, asgore.getY()+HEIGHT/4f);
		playerAttackAnim.setScale(2.3f);
		playerAttackAnim.setFrameDelay(9);
		
		//	Dmg Label
		LabelStyle damageSty = new LabelStyle(new BitmapFont(), Color.WHITE);
		damageSty.font = new BitmapFont(Gdx.files.internal("fonts/undertaleDamage.fnt"));
		damageLabel = new Label("999999",damageSty);
		damageLabel.setPosition(WIDTH/2-damageLabel.getWidth()/2, playerAttackAnim.getY()+HEIGHT/8);
		
		//	Cmera Effects
		cameraShaker = new CameraShake(cam, 50, (SpriteBatch)batch, new ShapeRenderer(), 10, 3);
		
		//	Health Bar
		asgoreHealthBar = new HealthBar(WIDTH/2, playerAttackAnim.getY()+HEIGHT/10, 
				WIDTH/2, HEIGHT/40, asgore.getHealth(), asgore.getMaxHealth(), Color.GREEN, Color.GRAY
				);
		asgoreHealthBar.setPosition(asgoreHealthBar.getX()-asgoreHealthBar.getWidth()/2, asgoreHealthBar.getY());
	}
	
	public void render() {
		
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		
		buttonUI.draw(batch, 1f);
		playerUI.update(player.getHealth());
		playerUI.draw(batch, 1f);
		
		//	Game Logic
		asgore.draw(batch, asgoreTrans);
		
		if (player.getHealth() == 0) stage = BattleSceneConstants.GAME_OVER;
		if (asgore.getHealth() == 0) stage = BattleSceneConstants.GAME_OVER;
		
		switch (stage) {
		
		case BattleSceneConstants.INITIAL_DIALOGUE:
			
			if (counter == 0) { AssetManager.addToSoundQueue(asgoreIntroMusic); }
			if (counter == 1500) stage = BattleSceneConstants.FIRST_STAGE;
			
			break;
		
		case BattleSceneConstants.FIRST_STAGE:
			
			if (!asgoreBattleMusic.isPlaying()) AssetManager.addToSoundQueue(asgoreBattleMusic);
			
			//	Handle Input
			if (state == BattleSceneConstants.DEFEND) {
				
				if (asgoreTrans > .1f) asgoreTrans -= .05f;
				
				if (currentAsgoreAttack == null) {
					System.out.println("Defend start");
					currentAsgoreAttack = new AsgoreAttack(150, 150, 300);
				}
				
				
				if (currentAsgoreAttack.isActive()) {
					battleController.defend(currentAsgoreAttack);
					battleController.handleSoul();
					currentAsgoreAttack.draw(batch, 1f);
					player.draw(batch, 1f);
				}
				else {
					state = BattleSceneConstants.PLAN;
					currentAsgoreAttack.dispose();
					currentAsgoreAttack = null;
				}
				
			}
			else if (state == BattleSceneConstants.PLAN) {
				
				if (asgoreTrans <= 1f) asgoreTrans += .05f;
				
				if (buttonUI.getSelection() == -1) buttonUI.setSelection(0);
				battleController.handleMenu();
				
			}
			else if (state == BattleSceneConstants.ATTACK) {
				
				if (playerAttackAnim.isOver()) { 
					playerAttackAnim.resetFrameCount();
					state = BattleSceneConstants.DAMAGE;
					counter = 0;
				}
				else {
					playerAttackAnim.play();
					playerAttackAnim.draw(batch, 1f);
				}
					
			}
			
			else if (state == BattleSceneConstants.DAMAGE) {
				 
				if (counter == 1) {
					damageLabel.setText("999999");
					asgoreDamage = 999999f/68f;
					cameraShaker.start();
					AssetManager.addToSoundQueue(asgoreHurtSound);
				}
				
				if (counter < 70) {
					asgore.absorbDamage(asgoreDamage);
					asgoreHealthBar.setHealth(asgoreHealthBar.getHealth()-asgoreDamage);
				}
				
				if (counter == 120) {
					cameraShaker.play();
					cameraShaker.updateCamera();
					state = BattleSceneConstants.DEFEND;
				}
				
				asgoreHealthBar.draw(batch, 1f);
				damageLabel.draw(batch, 1f);
				cameraShaker.play();
				cameraShaker.updateCamera();
				
				
			}
			
			break;
			
		case BattleSceneConstants.GAME_OVER:
			
			asgoreBattleMusic.pause();
			asgoreIntroMusic.pause();
			FinalProject.scene = FinalProject.SceneConstants.GAMEOVER;
			break;
			
		}
		
		//	Render Drawables
		for (Drawable d : drawables) AssetManager.getRenderQueue().add(d);
		
		++counter;
		
	}
	
	public void reset() {
		
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		
		counter = 0;
		state = BattleSceneConstants.PLAN;
		
		stage = BattleSceneConstants.FIRST_STAGE;
		
		asgoreIntroMusic.setPosition(0f);
		asgoreBattleMusic.setPosition(0f);
		
		buttonUI.setX(WIDTH/20);
		buttonUI.setY(HEIGHT/20);
		buttonUI.setGap(WIDTH/15);
		
		asgore.setHealth(Float.MAX_VALUE);
		player.setHealth(Float.MAX_VALUE);
		asgoreHealthBar.setHealth(Float.MAX_VALUE);
		asgoreTrans = 1f;
		
	}
	
	public void dispose() {
		
		player.dispose();
		
	}

}
