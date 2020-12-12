/**
 * 
 */
package edu.lewisu.cs.kylevbye;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * @author byekv
 *
 */
public class HollowedRectangle extends Rectangle {
	
	private Rectangle innerRectangle;
	private float thickness;
	
	///
	///
	///
	
	public float getThickness() { return thickness; }
	
	///
	///	Setters
	///
	
	public void setThickness(float thicknessIn) { 
		thickness = thicknessIn;
		generateInnerRectangle();
	}
	
	@Override
	public void draw(Batch batchIn, float parentAlphaIn) {
		super.draw(batchIn, parentAlphaIn);
		innerRectangle.draw(batchIn, parentAlphaIn);
	}

	/**
	 * @param xIn
	 * @param yIn
	 * @param widthIn
	 * @param heightIn
	 * @param colorIn
	 */
	public HollowedRectangle(float xIn, float yIn, float widthIn, float heightIn, Color colorIn) {
		super(xIn, yIn, widthIn, heightIn, colorIn);
		generateInnerRectangle();
	}
	
	public void dispose() {
		super.dispose();
		innerRectangle.dispose();
	}
	
	///
	///	Helpers
	///
	
	private void generateInnerRectangle() {
		
		if (innerRectangle != null) innerRectangle.dispose();
		
		float x, y, width, height;
		
		x = getX()+thickness;
		y = getY()+thickness;
		width = getWidth()- (2*thickness);
		height = getHeight() - (2*thickness);
		
		Rectangle newInnerRectangle = new Rectangle(x, y, width, height, new Color(0,0,0,1));
		innerRectangle = newInnerRectangle;
		
	}
}
