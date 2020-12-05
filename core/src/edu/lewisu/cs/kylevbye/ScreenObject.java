/**
 * 
 */
package edu.lewisu.cs.kylevbye;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.utils.Disposable;

/**
 * This class represents an Image that can collide with other
 * ScreenObjects. It is an object that can be displayed on a
 * screen.
 * 
 * @author	Kyle V Bye
 * @see	Image
 * @see	Disposable
 * @see	Drawable
 *
 */
public class ScreenObject extends Image implements Collidable {
	
	///
	///	Fields
	///
	protected boolean flippedX, flippedY;
	protected Polygon boundingPolygon;
	
	///
	///	Getters
	///
	
	public boolean isFlippedX() { return flippedX; }
	public boolean isFlippedY() { return flippedY; }
	
	///
	///	Setters
	///
	
	public void setFlippedX(boolean flippedXFlag) { flippedX = flippedXFlag; }
	public void setFlippedY(boolean flippedYFlag) { flippedY = flippedYFlag; }
	
	///
	///	Collidable
	///
	
	public Polygon getBoundingPolygon() { return boundingPolygon; }
	public void setBoundingPolygon(Polygon boundingPolygonIn) { boundingPolygon = boundingPolygonIn; }
	
	///
	///	Functions
	///
	
	public void centerOrigin() {
		super.setOrigin(getOriginX()/2.f, getOriginY()/2.f);
	}
	
	///
	///	Constructors
	///
	
	public ScreenObject(Image imageIn) {
		
		this(imageIn.getTextureRegion().getTexture(), imageIn.getX(), imageIn.getY(), imageIn.getOriginX(), 
				imageIn.getOriginY(), imageIn.getScaleX(), imageIn.getScaleY(), imageIn.getRotation(), false, false);
		
	}
	
	public ScreenObject(Texture textureIn) {
		
		this(textureIn, 0, 0, 0, 0, 1, 1, 0, false, false);
		
	}
	
	public ScreenObject(Texture textureIn, float xIn, float yIn, boolean centerOrigin) {
		
		this(textureIn, xIn, yIn, 0, 0, 1, 1, 0, false, false);
		centerOrigin();
		
	}
	
	public ScreenObject(Texture textureIn, float xIn, float yIn, float originXIn, float originYIn, float scaleXIn, 
			float scaleYIn, float rotationAngleIn, boolean flippedX, boolean flippedY) {
		
		super(textureIn, xIn, yIn, originXIn, originYIn, scaleXIn, scaleYIn, rotationAngleIn);
		setFlippedX(flippedX); setFlippedY(flippedY);
		
	}

}
