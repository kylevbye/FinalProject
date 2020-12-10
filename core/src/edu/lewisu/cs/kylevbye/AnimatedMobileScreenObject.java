/**
 * 
 */
package edu.lewisu.cs.kylevbye;

import com.badlogic.gdx.graphics.Texture;

/**
 * @author byekv
 *
 */
public class AnimatedMobileScreenObject extends MobileScreenObject {

	/**
	 * @param imageIn
	 */
	public AnimatedMobileScreenObject(Image imageIn) {
		super(imageIn);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param textureIn
	 */
	public AnimatedMobileScreenObject(Texture textureIn) {
		super(textureIn);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param textureIn
	 * @param xIn
	 * @param yIn
	 * @param centerOrigin
	 */
	public AnimatedMobileScreenObject(Texture textureIn, float xIn, float yIn, boolean centerOrigin) {
		super(textureIn, xIn, yIn, centerOrigin);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param textureIn
	 * @param xIn
	 * @param yIn
	 * @param originXIn
	 * @param originYIn
	 * @param scaleXIn
	 * @param scaleYIn
	 * @param rotationAngleIn
	 * @param flippedX
	 * @param flippedY
	 */
	public AnimatedMobileScreenObject(Texture textureIn, float xIn, float yIn, float originXIn, float originYIn,
			float scaleXIn, float scaleYIn, float rotationAngleIn, boolean flippedX, boolean flippedY) {
		super(textureIn, xIn, yIn, originXIn, originYIn, scaleXIn, scaleYIn, rotationAngleIn, flippedX, flippedY);
		// TODO Auto-generated constructor stub
	}

}
