package edu.lewisu.cs.kylevbye;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FinalProject extends ApplicationAdapter {
	
	int WIDTH, HEIGHT;
	
	OrthographicCamera cam;
	SpriteBatch batch;
	
	static int scene;
	
	TitleScene titleScene;
	
	public final class SceneConstants {
		public static final int TITLE = 0;
		public static final int BATTLE = 1;
	}
	
	@Override
	public void create () {
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		cam = new OrthographicCamera(WIDTH, HEIGHT);
		cam.translate(WIDTH/2, HEIGHT/2);
		scene = SceneConstants.TITLE;
		batch = new SpriteBatch();
		titleScene = new TitleScene();
		titleScene.create(cam, batch);
	}

	@Override
	public void render () {
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		
		switch (scene) {
		
		case SceneConstants.TITLE:
			titleScene.render();
			break;
			
		case SceneConstants.BATTLE:
			break;
			
		}
		
		AssetManager.playSounds();
		AssetManager.renderImages(batch);
		batch.end();
		
	}
	
	@Override
	public void dispose () {
		AssetManager.dispose();
		batch.dispose();
	}
}
