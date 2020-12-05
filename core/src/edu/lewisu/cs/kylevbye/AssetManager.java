/**
 * 
 */
package edu.lewisu.cs.kylevbye;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Disposable;

/**
 * @author	Kyle V Bye
 *
 */
public class AssetManager implements Disposable {
	
	///
	///	Fields
	///
	
	private static ArrayList<Drawable> renderQueue;
	private static ArrayList<Playable> soundQueue;
	
	///
	///	Getters
	///
	
	public static ArrayList<Drawable> getRenderQueue() { return renderQueue; }
	public static ArrayList<Playable> getSoundQueue() { return soundQueue; }
	
	///
	///	Setters
	///
	
	public static void setRenderQueue(ArrayList<Drawable> renderQueueIn) { renderQueue = renderQueueIn; }
	public static void setSoundQueue(ArrayList<Playable> soundQueueIn) { soundQueue = soundQueueIn; }
	
	///
	///	Functions
	///
	
	public Image loadImage(String fileNameIn) {
		
		Image loadedImage;
		Texture texture =  new Texture("images/" + fileNameIn);
		loadedImage = new Image(texture, 0.f, 0.f, 0.f, 0.f, 1.f, 1.f, 0.f);
		
		return loadedImage;
		
	}
	
	public Image loadedImage(String fileNameIn, float xIn, float yIn, float originXIn, 
			float originYIn, float scaleXIn, float scaleYIn, float rotationAngleIn) {
		
		Image loadedImage;
		Texture texture =  new Texture("images/" + fileNameIn);
		loadedImage = new Image(
				texture, xIn, yIn, originXIn, originYIn, scaleXIn, scaleYIn, rotationAngleIn
				);
		
		return loadedImage;
		
	}
	
	public void renderImages(Batch batchIn) {
		
		for (Drawable image : renderQueue) { if (image != null) image.draw(batchIn, 1); }
		
	}
	
	///
	///	Constructors
	///
	
	public AssetManager() {
		
		setRenderQueue(new ArrayList<Drawable>());
		setSoundQueue(new ArrayList<Playable>());
		
	}
	
	///
	///	Destructors
	///
	
	@Override
	public void dispose() {
		
		//	Clear
		renderQueue.clear();
		
		//	Nullify
		renderQueue = null;
		
	}

}
