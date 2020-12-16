package edu.lewisu.cs.kylevbye;

import java.awt.DisplayMode;
import java.awt.font.GraphicAttribute;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Scaling;

import edu.lewisu.cs.kylevbye.input.InputHandler;
import edu.lewisu.cs.kylevbye.input.PlayerInput;

public class FinalProject extends ApplicationAdapter {
	
	static final int SETWIDTH = 640;
	static final int SETHEIGHT = 480;
	
	int WIDTH, HEIGHT;
	
	Camera cam;
	SpriteBatch batch;
	Rectangle viewPort;
	
	static int scene;
	static int lastScene;
	
	TitleScene titleScene;
	LoadingBattleScene loadingBattleScene;
	BattleScene battleScene;
	GameOverScene gameOverScene;
	GameWinScene gameWinScene;
	
	
	public final class SceneConstants {
		
		public static final int TITLE = 0;
		public static final int LOADING_BATTLE = 1;
		public static final int BATTLE = 2;
		public static final int GAMEOVER = 3;
		public static final int GAMEWIN = 4;
		
	}
	
	@Override
	public void create () {
		
		Gdx.graphics.setWindowedMode(640, 480);
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		cam = new OrthographicCamera(WIDTH, HEIGHT);
		((OrthographicCamera)cam).translate(WIDTH/2, HEIGHT/2);
		scene = SceneConstants.TITLE;
		batch = new SpriteBatch();
		
		//	Input Handler
		Gdx.input.setInputProcessor(new InputHandler());
		
		//	Title Scene
		titleScene = new TitleScene();
		titleScene.create((OrthographicCamera)cam, batch);
		
		loadingBattleScene = new LoadingBattleScene();
		loadingBattleScene.create((OrthographicCamera)cam, batch);
		
		//	Battle Scene
		battleScene = new BattleScene();
		battleScene.create((OrthographicCamera)cam, batch);
		
		//	GameOver Scene
		gameOverScene = new GameOverScene();
		gameOverScene.create((OrthographicCamera)cam, batch);
		
		gameWinScene = new GameWinScene();
		gameWinScene.create((OrthographicCamera)cam, batch);
		
		
	}

	@Override
	public void render () {
		
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		
		lastScene = scene;
		
		switch (scene) {
		
		case SceneConstants.TITLE:
			titleScene.render();
			break;
			
		case SceneConstants.LOADING_BATTLE:
			loadingBattleScene.render();
			break;
			
		case SceneConstants.BATTLE:
			battleScene.render();
			break;
			
		case SceneConstants.GAMEOVER:
			gameOverScene.render();
			break;
		
		case SceneConstants.GAMEWIN:
			gameWinScene.render();
			break;
			
		}
		
		AssetManager.playSounds();
		AssetManager.renderImages(batch);
		batch.end();
		
		if (scene != lastScene) {
			titleScene.reset();
			loadingBattleScene.reset();
			battleScene.reset();
			gameOverScene.reset();
			gameWinScene.reset();
		}
		
		PlayerInput.resetTempControls();
		
		
		
	}
	
	
	@Override
	public void dispose () {
		
		AssetManager.dispose();
		batch.dispose();
		
	}
}
