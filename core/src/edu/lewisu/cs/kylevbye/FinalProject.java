package edu.lewisu.cs.kylevbye;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import edu.lewisu.cs.kylevbye.input.InputHandler;

public class FinalProject extends ApplicationAdapter {
	
	static final int SETWIDTH = 640;
	static final int SETHEIGHT = 480;
	
	int WIDTH, HEIGHT;
	
	OrthographicCamera cam;
	SpriteBatch batch;
	Rectangle viewPort;
	
	static int scene;
	
	TitleScene titleScene;
	BattleScene battleScene;
	GameOverScene gameOverScene;
	
	public final class SceneConstants {
		
		public static final int TITLE = 0;
		public static final int LOADING_BATTLE = 1;
		public static final int BATTLE = 2;
		public static final int GAMEOVER = 3;
		
	}
	
	@Override
	public void create () {
		
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		cam = new OrthographicCamera(WIDTH, HEIGHT);
		cam.translate(WIDTH/2, HEIGHT/2);
		scene = SceneConstants.TITLE;
		batch = new SpriteBatch();
		
		//	Input Handler
		Gdx.input.setInputProcessor(new InputHandler());
		
		//	Title Scene
		titleScene = new TitleScene();
		titleScene.create(cam, batch);
		
		//	Battle Scene
		battleScene = new BattleScene();
		battleScene.create(cam, batch);
		
		//	GameOver Scene
		gameOverScene = new GameOverScene();
		gameOverScene.create(cam, batch);
		
		
	}

	@Override
	public void render () {
		
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		Gdx.gl.glViewport((int)viewPort.x, (int)viewPort.y, (int)viewPort.width, (int)viewPort.height);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		
		switch (scene) {
		
		case SceneConstants.TITLE:
			titleScene.render();
			break;
			
		case SceneConstants.BATTLE:
			battleScene.render();
			break;
			
		case SceneConstants.GAMEOVER:
			gameOverScene.render();
			break;
			
		}
		
		AssetManager.playSounds();
		AssetManager.renderImages(batch);
		batch.end();
		
	}
	
	@Override
	public void resize(int width, int height) {
		
		float newAspectRatio = (float)width/(float)height;
		
		float viewX, viewY;
		viewX = 0f; viewY = 0f;
		
		float scale = 1.f;
		
		if (newAspectRatio > 4f/3f) {
			
			scale = (float)height/(float)SETHEIGHT;
			viewX = width-(SETWIDTH*scale)/2f;
			
		}
		else if (newAspectRatio < 4f/3f) {
			
			scale = (float)width/(float)SETWIDTH;
			viewY = height-(SETHEIGHT*scale)/2f;
			
		}
		else scale = (float)WIDTH/(float)SETWIDTH;
		
		viewPort = new Rectangle(viewX, viewY, (float)SETWIDTH*scale, (float)SETHEIGHT*scale);
		
	}
	
	@Override
	public void dispose () {
		
		AssetManager.dispose();
		batch.dispose();
		
	}
}
