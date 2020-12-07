package edu.lewisu.cs.kylevbye;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;

public class GameOverScene {
	
	private int WIDTH, HEIGHT;
	private OrthographicCamera cam;
	private Batch batch;
	private ArrayList<Drawable> drawables;
	
	public void create(OrthographicCamera camIn, Batch batchIn) {
		
		drawables = new ArrayList<Drawable>();
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		cam = camIn;
		batch = batchIn;
		
		
		
		
		
	}
	
	public void render() {
		
	}
	
	public void dispose() {
		
	}

}
