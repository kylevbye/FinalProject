package edu.lewisu.cs.kylevbye;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
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
	
	public void create(OrthographicCamera camIn, Batch batchIn) {
		
		counter = 0;
		
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
		
		
		
		
	}
	
	public void render() {
		
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		
		gameOverLabel.setPosition(WIDTH/2 - gameOverLabel.getWidth()/2, HEIGHT - HEIGHT/3);
		float labelOpaq;
		if (counter < 200) labelOpaq = ((float)counter)/100;
		else labelOpaq = 1;
		gameOverLabel.setColor(1,1,1, labelOpaq);
		
		for (Drawable d : drawables) AssetManager.getRenderQueue().add(d);
		gameOverLabel.draw(batch, 1.f); 
		
		++counter;
		
	}
	
	public void dispose() {
		
	}

}
