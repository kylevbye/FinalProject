package edu.lewisu.cs.kylevbye;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;

public class AsgoreAttack {
	
	public static OrthographicCamera cam;
	public static Batch batch;
	public static PlayerEntity player;
	public static float middleX, middleY;
	public static float borderThickness;
	
	private HollowedRectangle restriction;
	private EdgeHandler restrictor;
	private boolean isActive;
	private int counter;
	private int length;
	
	///
	///	Getters
	///
	
	public HollowedRectangle getRestriction() { return restriction; }
	
	///
	///	Setters
	///
	
	public void setRestriction(HollowedRectangle restrictionIn) { restriction = restrictionIn; }
	
	///
	///	Functions
	///
	
	public boolean isActive() {
		
		return isActive;
		
	}
	
	public void play() {
		
		restrictor.handleEdges();
		
		if (counter == length) {
			isActive = false;
		}
		
		++counter;
		
	}
	
	public void draw(Batch batchIn, float parentAlphaIn) {
		
		restriction.draw(batchIn, parentAlphaIn);
		
	}
	
	///
	///	Constructors
	///
	
	/**
	 * 
	 * @param widthR
	 * @param heightR
	 */
	public AsgoreAttack(float widthR, float heightR, int lengthIn) {
		
		HollowedRectangle rest = new HollowedRectangle(0, 0, widthR, heightR, new Color(1,1,1,1));
		rest.setPosition(middleX-widthR/2, middleY-heightR/2);
		rest.setThickness(borderThickness);
		setRestriction(rest);
		
		restrictor = new EdgeHandler(
				player, cam, batch, EdgeHandler.EdgeConstants.LOCK, 
				EdgeHandler.EdgeConstants.LOCK, rest.getX(), rest.getWidth()+rest.getX(), rest.getY(),
				rest.getWidth()+rest.getY(), borderThickness
				);
		
		isActive = true;
		counter = 0;
		length = lengthIn;
		player.setPosition(middleX-player.getWidth()/2, middleY-player.getHeight()/2);
		
	}
	
	///
	///
	///
	
	public void dispose() {
		
		restriction.dispose();
		
	}

}
