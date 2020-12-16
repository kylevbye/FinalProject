package edu.lewisu.cs.kylevbye;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Disposable;

/**
 * This scene is the first thing you are exposed to when
 * you boot up the game.
 * @author	Kyle V Bye
 *
 */
public class TitleScene implements Disposable {
	
	private int WIDTH, HEIGHT;
	private OrthographicCamera cam;
	private Batch batch;
	private ArrayList<Drawable> drawables;
	private Label titleLabel;
	private LabelStyle titleStyle;
	private SoundLabel startLabel;
	private Label insLabel;
	
	///
	///	Functions
	///
	/**
	 * Set up the scene.
	 * @param	camIn	cam to play the scene
	 * @param	batchIn	batch to draw the scene on
	 */
	public void create(OrthographicCamera camIn, Batch batchIn) {
		
		drawables = new ArrayList<Drawable>();
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		cam = camIn;
		batch = batchIn;
		
		//	Asgore Sprite
		Image asgoreSprite = AssetManager.loadImage("asgore.png");
		asgoreSprite.setBounds(0, 20, 400, 400);
		drawables.add(asgoreSprite);
		
		//	Title
		titleStyle = new LabelStyle();
		titleStyle.fontColor = new Color(1,1,1,1);
		titleStyle.font = new BitmapFont(Gdx.files.internal("fonts/undertale.fnt"));
		titleLabel = new Label("Asgore Genocide Fight", titleStyle);
		
		//	Start Button
		startLabel = new SoundLabel(450, 205, 2f, 2f, null, "Start", titleStyle);
		startLabel.setSound(AssetManager.loadSound("slash.mp3"));
		startLabel.addEvent(MouseEventConstants.LEFTMOUSE, new Event() {
			public void run() { 
				FinalProject.scene = FinalProject.SceneConstants.LOADING_BATTLE; 
				AssetManager.getSoundQueue().add(startLabel);
				System.out.println("STARTING BATTLE");
				}
			}
		);
		
		///	Instructions
		insLabel = new Label("WASD/Arrow Keys to move\nZ to fight\nClick \"Start\" to start.", titleStyle);
		insLabel.setColor(Color.RED);
		
		
	}
	
	/**
	 * Draw the scene.
	 */
	public void render() {
		
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		
		//	Handle Input
		if (Gdx.input.isButtonPressed(Buttons.LEFT) && 
				startLabel.wasClicked(Gdx.input.getX(), Gdx.input.getY())) {
			startLabel.onClicked(MouseEventConstants.LEFTMOUSE);
		}
		
		titleLabel.setPosition(WIDTH/2 - titleLabel.getWidth()/2, HEIGHT - HEIGHT/7);
		
		for (Drawable d : drawables) d.draw(batch, 1f);
		titleLabel.draw(batch, 1.f); 
		startLabel.draw(batch, 1.f);
		insLabel.draw(batch, 1f);
		
	}
	
	/**
	 * Doesn't really do anything here since
	 * everything is static.
	 */
	public void reset() {
		
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		
	}
	
	///
	///	Destructor
	///
	
	/**
	 * Clean up
	 */
	public void dispose() {
		startLabel.dispose();
	}

}
