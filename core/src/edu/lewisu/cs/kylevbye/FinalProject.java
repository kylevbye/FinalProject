package edu.lewisu.cs.kylevbye;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FinalProject extends ApplicationAdapter {
	AssetManager assetManager;
	SpriteBatch batch;
	Image i;
	
	@Override
	public void create () {
		assetManager = new AssetManager();
		batch = new SpriteBatch();
		Texture text = new Texture("badlogic.jpg");
		i = new Image(text, 0, 0, text.getWidth()/2, text.getHeight()/2, 1, 1, 0);
		System.out.println(i);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		i.draw(batch, 1);
		i.rotateBy(1);
		batch.end();
	}
	
	@Override
	public void dispose () {
		assetManager.dispose();
		batch.dispose();
	}
}
