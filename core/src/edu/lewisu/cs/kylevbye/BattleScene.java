package edu.lewisu.cs.kylevbye;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

import edu.lewisu.cs.cpsc41000.common.cameraeffects.CameraEffect;
import edu.lewisu.cs.cpsc41000.common.cameraeffects.CameraShake;
import edu.lewisu.cs.kylevbye.input.PlayerInput;

/**
 * This class holds the scene that shows
 * the battle between the player and
 * Asgore.
 * 
 * @author	Kyle V Bye
 *
 */
public class BattleScene {
	
	private int counter;
	private int rotationCounter;
	private OrthographicCamera cam;
	private Batch batch;
	private BattleController battleController;
	private static int stage;
	private int WIDTH, HEIGHT;
	private ArrayList<Drawable> drawables;
	private int state;
	private AsgoreAttack currentAsgoreAttack;
	
	//
	//	Local Assets
	//
	
	private MobileScreenObject[] souls;
	private PlayerEntity player;
	private AsgoreEntity asgore;
	private BattleButtonUI buttonUI;
	private PlayerUI playerUI;
	private PNGAnimatedMobileScreenObject playerAttackAnim;
	private Sound attackSlashSound;
	private Sound asgoreHurtSound;
	private Sound hurtSound;
	private Music asgoreIntroMusic;
	private Music asgoreBattleMusic;
	private Label damageLabel;
	private Label cheatLabel;
	private Label skipLabel;
	private HealthBar asgoreHealthBar;
	
	private boolean cheatMode;
	private CameraEffect cameraShaker;
	private float asgoreDamage;
	private float asgoreTrans;
	private int playerImmunity;
	
	/**
	 * Constants used to control the stages and states
	 * of the scene
	 * @author	Kyle V Bye
	 *
	 */
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
		
		//	Game Win
		public static final int GAME_WIN = 7;
		
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
	
	/**
	 * Initializes the scene to be ready to be rendered.
	 * 
	 * @param	camIn	cam to play the scene
	 * @param	batchIn	batch to draw the scene on
	 */
	public void create(OrthographicCamera camIn, Batch batchIn) {
		
		cheatMode = false;

		counter = 0;
		asgoreDamage = 0f;
		asgoreTrans = 1f;
		playerImmunity = 0;
		
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
		
		//	Souls
		souls = new MobileScreenObject[6];
		for (int i = 0; i<souls.length; ++i) {
			souls[i] = loadSoul(WIDTH/2, HEIGHT/2, null);
			setSoulPosition(souls[i], i);
			setSoulColor(souls[i], i);
		}
		
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
		if (asgoreIntroMusic == null) {
			asgoreIntroMusic = AssetManager.loadMusic("asgorePreBattle.mp3");
		}
		if (asgoreBattleMusic == null) {
			asgoreBattleMusic = AssetManager.loadMusic("asgoreBattle.mp3");
			asgoreBattleMusic.setVolume(.3f);
		}
		
		//	Attack sound
		attackSlashSound = AssetManager.loadSound("slash.mp3");
		
		//	Hurt Sound
		hurtSound = AssetManager.loadSound("hurt.mp3");
		
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
		cameraShaker = new CameraShake(cam, 30, (SpriteBatch)batch, new ShapeRenderer(), 10, 3);
		
		//	Health Bar
		asgoreHealthBar = new HealthBar(WIDTH/2, playerAttackAnim.getY()+HEIGHT/10, 
				WIDTH/2, HEIGHT/40, asgore.getHealth(), asgore.getMaxHealth(), Color.GREEN, Color.GRAY
				);
		asgoreHealthBar.setPosition(asgoreHealthBar.getX()-asgoreHealthBar.getWidth()/2, asgoreHealthBar.getY());
		
		//	CheatLabel
		LabelStyle cheatStyle = new LabelStyle(new BitmapFont(), Color.WHITE);
		cheatLabel = new Label("CHEAT", cheatStyle);
		cheatLabel.setPosition(WIDTH-cheatLabel.getWidth(), HEIGHT-cheatLabel.getHeight());
		
		//	SkipLabel
		skipLabel = new Label("Z to Skip...", damageSty);
		skipLabel.setPosition(0, 0);
		
		AsgoreAttack.player = player;
		AsgoreAttackFactory.player = player;
		
		state = BattleSceneConstants.PLAN;
	}
	
	/**
	 * Renders the scene to the screen.
	 */
	public void render() {
		
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		
		//	Game Logic
		
		if (PlayerInput.mMiddleWasClicked) cheatMode = true;
		if (cheatMode) cheatLabel.draw(batch, .5f);
		
		switch (stage) {
		
		case BattleSceneConstants.INITIAL_DIALOGUE:
			
			for (int i = 0; i<souls.length; ++i) souls[i].draw(batch, asgoreTrans);
			asgore.draw(batch, asgoreTrans);
			skipLabel.draw(batch, 1f);
			
			if (counter == 0) { AssetManager.addToSoundQueue(asgoreIntroMusic); }
			if (counter == 1550 || PlayerInput.zWasPressed) {
				counter = 1550;
				asgoreIntroMusic.stop();
				stage = BattleSceneConstants.FIRST_STAGE;
			}
			
			asgore.setHealth(Float.MAX_VALUE);
			player.setHealth(Float.MAX_VALUE);
			
			break;
		
		case BattleSceneConstants.FIRST_STAGE:
			
			if (player.getHealth() == 0 && cheatMode == false) stage = BattleSceneConstants.GAME_OVER;
			if (asgore.getHealth() == 0) stage = BattleSceneConstants.GAME_WIN;
			
			for (int i = 0; i<souls.length; ++i) {
				
				float x = (float)Math.cos(Math.toRadians(rotationCounter+i*60f))*2f;
				float y = (float)Math.sin(Math.toRadians(rotationCounter+i*60f))*2f;
				
				souls[i].move(x,y);
				souls[i].draw(batch, asgoreTrans);
				
			}
			
			buttonUI.draw(batch, 1f);
			playerUI.update(player.getHealth());
			playerUI.draw(batch, 1f);
			asgore.draw(batch, asgoreTrans);
			
			if (!asgoreBattleMusic.isPlaying()) AssetManager.addToSoundQueue(asgoreBattleMusic);
			
			//	Handle Input
			if (state == BattleSceneConstants.DEFEND) {
				
				if (asgoreTrans > .1f) asgoreTrans -= .05f;
				
				if (currentAsgoreAttack == null) {
					System.out.println("Defend start");
					currentAsgoreAttack = AsgoreAttackFactory.generateRandomAsgoreAttack();
				}
				
				
				if (currentAsgoreAttack.isActive()) {
					battleController.defend(currentAsgoreAttack);
					battleController.handleSoul();
					currentAsgoreAttack.draw(batch, 1f);
					player.getColor().b = 1;
					if (playerImmunity == 0 || counter % 7 == 0) player.draw(batch, 1f);
					if (currentAsgoreAttack.isColliding(player) && playerImmunity == 0) {
						AssetManager.addToSoundQueue(hurtSound);
						player.absorbDamage(currentAsgoreAttack.getDamageValue());
						playerImmunity = 35;
					}
					if (playerImmunity != 0) --playerImmunity;
				}
				else {
					state = BattleSceneConstants.PLAN;
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
					if (cheatMode) asgoreDamage = 999999f;
					cameraShaker.start();
					AssetManager.addToSoundQueue(asgoreHurtSound);
				}
				
				if (counter < 70) {
					asgore.absorbDamage(asgoreDamage);
					asgoreHealthBar.setHealth(asgoreHealthBar.getHealth()-asgoreDamage);
				}
				
				if (counter == 120) {
					cameraShaker.play();
					state = BattleSceneConstants.DEFEND;
				}
				
				asgoreHealthBar.draw(batch, 1f);
				damageLabel.draw(batch, 1f);
				cameraShaker.play();
				cameraShaker.updateCamera();
				
				
			}
			
			break;
			
		case BattleSceneConstants.GAME_WIN:
			
			if (currentAsgoreAttack != null) currentAsgoreAttack.dispose();
			currentAsgoreAttack = null;
			asgoreBattleMusic.pause();
			asgoreIntroMusic.pause();
			FinalProject.scene = FinalProject.SceneConstants.GAMEWIN;
			break;
			
			
		case BattleSceneConstants.GAME_OVER:
			
			if (currentAsgoreAttack != null) currentAsgoreAttack.dispose();
			currentAsgoreAttack = null;
			AssetManager.getSoundQueue().clear(); 
			asgoreBattleMusic.pause();
			asgoreIntroMusic.pause();
			FinalProject.scene = FinalProject.SceneConstants.GAMEOVER;
			break;
			
		}
		
		//	Render Drawables
		for (Drawable d : drawables) AssetManager.getRenderQueue().add(d);
		
		++counter;
		++rotationCounter;
		
	}
	
	/**
	 * Resets the scene to be played again.
	 */
	public void reset() {
		
		cheatMode = false;
	
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		
		counter = 0;
		stage = BattleSceneConstants.INITIAL_DIALOGUE;
		state = BattleSceneConstants.PLAN;
		
		asgoreIntroMusic.setPosition(0f);
		asgoreBattleMusic.setPosition(0f);
		
		buttonUI.setX(WIDTH/20);
		buttonUI.setY(HEIGHT/20);
		buttonUI.setGap(WIDTH/15);
		
		asgore.setHealth(Float.MAX_VALUE);
		player.setHealth(Float.MAX_VALUE);
		asgoreHealthBar.setHealth(Float.MAX_VALUE);
		asgoreTrans = 1f;
		
		playerAttackAnim.resetFrameCount();
		
		if (currentAsgoreAttack != null) currentAsgoreAttack.dispose();
		currentAsgoreAttack = null;
		playerImmunity = 0;
		
		for (int i = 0; i<souls.length; ++i) {
			souls[i].setPosition(WIDTH/2-souls[i].getWidth()/2, HEIGHT/2-souls[i].getHeight()/2);
			setSoulPosition(souls[i], i);
		}
		
	}
	
	///
	///	Helpers
	///
	
	private MobileScreenObject loadSoul(float xIn, float yIn, Color colorIn) {
		MobileScreenObject soul = new MobileScreenObject(AssetManager.loadImage("monsterSoul.png"));
		
		if (colorIn != null) soul.setColor(colorIn);
		soul.setPosition(xIn-soul.getWidth()/2, yIn-soul.getHeight()/2);
		
		return soul;
	}
	
	private void setSoulPosition(MobileScreenObject soul, int soulIndex) {
		
		float x, y;
		
		for (int i = 0; i<60*soulIndex; ++i) {
			x = (float)Math.cos(Math.toRadians(i))*2f; 
			y = (float)Math.sin(Math.toRadians(i))*2f;
			soul.move(x, y);
		}
		
	}
	
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
	///	Destructor
	///
	
	/**
	 * Memory Cleanup
	 */
	public void dispose() {
		
		for (MobileScreenObject soul : souls) soul.dispose();
		player.dispose();
		asgore.dispose();
		buttonUI.dispose();
		playerUI.dispose();
		playerAttackAnim.dispose();
		attackSlashSound.dispose();
		asgoreHurtSound.dispose();
		hurtSound.dispose();
		//asgoreIntroMusic.dispose();
		//asgoreBattleMusic.dispose();
		asgoreHealthBar.dispose();
		
		AsgoreAttack.player = null;
		AsgoreAttackFactory.player = null;
		
		souls = null;
		player = null;
		asgore = null;
		buttonUI = null;
		playerUI = null;
		playerAttackAnim = null;
		attackSlashSound = null;
		asgoreHurtSound = null;
		hurtSound = null;
		//asgoreIntroMusic = null;
		//asgoreBattleMusic = null;
		asgoreHealthBar = null;
		
	}

}
