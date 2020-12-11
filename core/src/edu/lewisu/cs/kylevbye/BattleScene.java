package edu.lewisu.cs.kylevbye;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;

public class BattleScene {
	
	private int counter;
	
	private OrthographicCamera cam;
	private Batch batch;
	
	private static int stage;
	private static AsgoreDialogue aDiag = new AsgoreDialogue();
	private int WIDTH, HEIGHT;
	private ArrayList<Drawable> drawables;
	
	private PlayerEntity player;
	
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
		
	}
	
	public void render() {
		
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		
		//	Handle input
		
		//	Game Logic
		switch (stage) {
		
		case BattleSceneConstants.INITIAL_DIALOGUE:
			break;
		case BattleSceneConstants.GAME_OVER:
			FinalProject.scene = FinalProject.SceneConstants.GAMEOVER;
			break;
		}
		
		//	Render Drawables
		for (Drawable d : drawables) AssetManager.getRenderQueue().add(d);
		
		++counter;
		
	}
	
	public void dispose() {
		
		player.dispose();
	}

}
