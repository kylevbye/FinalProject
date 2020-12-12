package edu.lewisu.cs.kylevbye;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;

public class BattleScene {
	
	private int counter;
	
	private OrthographicCamera cam;
	private Batch batch;
	
	private BattleController battleController;
	
	private static int stage;
	private static AsgoreDialogue aDiag = new AsgoreDialogue();
	private int WIDTH, HEIGHT;
	private ArrayList<Drawable> drawables;
	private boolean defending;
	
	private PlayerEntity player;
	private BattleButtonUI buttonUI;
	private Music asgoreIntro;
	private Music asgoreBattle;
	
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
	
	}
	
	public void create(OrthographicCamera camIn, Batch batchIn) {

		counter = 0;
		defending = false;
		
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
		
		//	BattleController
		battleController = new BattleController(player, buttonUI);
		
		//	Music
		asgoreIntro = AssetManager.loadMusic("asgorePreBattle.mp3");
		asgoreIntro.setLooping(false);
		asgoreBattle = AssetManager.loadMusic("asgoreBattle.mp3");
		
	}
	
	public void render() {
		
		defending = false;
		
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		
		buttonUI.draw(batch, 1f);
		
		//	Game Logic
		switch (stage) {
		
		case BattleSceneConstants.INITIAL_DIALOGUE:
			
			if (counter == 0) { AssetManager.addToSoundQueue(asgoreIntro); }
			if (counter == 1500) stage = BattleSceneConstants.FIRST_STAGE;
			
			break;
		
		case BattleSceneConstants.FIRST_STAGE:
			
			//	Handle Input
			if (defending) {
				battleController.handleSoul();
				player.draw(batch, 1f);
			}
			else {
				if (buttonUI.getSelection() == -1) buttonUI.setSelection(0);
				battleController.handleMenu();
			}
			
			break;
			
		case BattleSceneConstants.GAME_OVER:
			
			asgoreBattle.pause();
			asgoreIntro.pause();
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
		defending = false;
		
		stage = BattleSceneConstants.INITIAL_DIALOGUE;
		
		asgoreIntro.setPosition(0);
		
		buttonUI.setX(WIDTH/20);
		buttonUI.setY(HEIGHT/20);
		buttonUI.setGap(WIDTH/15);
		
		// Music
		//asgoreIntro = AssetManager.loadMusic("asgorePreBattle.mp3");
		//asgoreIntro.setLooping(false);
		//asgoreBattle = AssetManager.loadMusic("asgoreBattle.mp3");
		
	}
	
	public void dispose() {
		
		player.dispose();
		
	}

}
