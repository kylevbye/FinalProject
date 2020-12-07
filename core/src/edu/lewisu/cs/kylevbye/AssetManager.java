/**
 * 
 */
package edu.lewisu.cs.kylevbye;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Disposable;

/**
 * @author	Kyle V Bye
 *
 */
public class AssetManager {
	
	///
	///	Volumne
	///
	
	public static float volume = 1.f;
	
	///
	///	Fields
	///
	
	private static ArrayList<Drawable> renderQueue = new ArrayList<Drawable>();
	private static ArrayList<Playable> soundQueue = new ArrayList<Playable>();
	
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
	
	public static Image loadImage(String fileNameIn) {
		
		Image loadedImage;
		Texture texture =  new Texture("images/" + fileNameIn);
		loadedImage = new Image(texture, 0.f, 0.f, 0.f, 0.f, 1.f, 1.f, 0.f);
		
		return loadedImage;
		
	}
	
	public static Image loadImage(String fileNameIn, float xIn, float yIn, float originXIn, 
			float originYIn, float scaleXIn, float scaleYIn, float rotationAngleIn) {
		
		Image loadedImage;
		Texture texture =  new Texture("images/" + fileNameIn);
		loadedImage = new Image(
				texture, xIn, yIn, originXIn, originYIn, scaleXIn, scaleYIn, rotationAngleIn
				);
		
		return loadedImage;
		
	}
	
	public static Sound loadSound(String soundFileNameIn) {
		
		Sound newSound = Gdx.audio.newSound(Gdx.files.internal(soundFileNameIn));
		
		return newSound;
		
	}
	
	public static void renderImages(Batch batchIn) {
		
		while (!renderQueue.isEmpty()) {
			Drawable d = renderQueue.remove(0);
			if (d != null) d.draw(batchIn, 1.f);
		}
		
	}
	
	public static void playSounds() {
		while (!soundQueue.isEmpty()) {
			Playable p = soundQueue.remove(0);
			p.play(volume);
		}
	}
	
	///
	///	Constructors
	///
	
	private AssetManager() {}
	
	///
	///	Destructors
	///
	
	public static void dispose() {
		
		//	Clear
		renderQueue.clear();
		
	}

}
