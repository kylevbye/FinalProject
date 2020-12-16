/**
 * 
 */
package edu.lewisu.cs.kylevbye;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * This class holds a rectangle inside this instance. Thickness describes how
 * much smaller the inner rectangle would be. Larger thickness value means
 * smaller inner rectangle.
 * 
 * Inner Rectangle is set to be black color only.
 * 
 * @author Kyle V Bye
 */
public class HollowedRectangle extends Rectangle {

	///
	/// Fields
	///

	private Rectangle innerRectangle;
	private float thickness;

	///
	/// Getters
	///

	public float getThickness() {
		return thickness;
	}

	///
	/// Setters
	///

	public void setThickness(float thicknessIn) {
		thickness = thicknessIn;
		generateInnerRectangle();
	}

	///
	///	Functions
	///

	@Override
	public void draw(Batch batchIn, float parentAlphaIn) {
		super.draw(batchIn, parentAlphaIn);
		innerRectangle.draw(batchIn, parentAlphaIn);
	}

	@Override
	protected void positionChanged() {
		super.positionChanged();
		generateInnerRectangle();
	}

	/**
	 * Sets rectangle based on the parameters.
	 * @param	xIn	x cooridnate
	 * @param	yIn	y coordinate
	 * @param	widthIn	width of rect
	 * @param 	heightIn	height of rect
	 * @param 	colorIn	color of rectangle (outer)
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
	/// Helpers
	///
	
	/**
	 * Resize inner rectangle if needed.
	 */
	private void generateInnerRectangle() {

		if (innerRectangle != null)
			innerRectangle.dispose();

		float x, y, width, height;

		x = getX() + thickness;
		y = getY() + thickness;
		width = getWidth() - (2 * thickness);
		height = getHeight() - (2 * thickness);

		Rectangle newInnerRectangle = new Rectangle(x, y, width, height, new Color(0, 0, 0, 1));
		innerRectangle = newInnerRectangle;

	}
}
