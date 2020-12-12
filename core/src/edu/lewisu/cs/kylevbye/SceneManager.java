package edu.lewisu.cs.kylevbye;

public class SceneManager {
	
	private Scene[] scenes;
	
	///
	///	Getters
	///
	
	public Scene[] getScenes() { return scenes; }
	
	///
	/// Setters
	///
	
	public void setScenes(Scene[] scenesIn) { scenes = scenesIn; }
	
	///
	///	Functions
	///
	
	public void create() {
		
		
	}
	
	public void render() {
		
	}
	
	///
	///	Constants
	///
	
	public class SceneConstants {
		
		public static final int TITLE = 0;
		public static final int LOADING_BATTLE = 1;
		public static final int BATTLE = 2;
		public static final int GAMEOVER = 3;
		
	}
	
	///
	///	Constructor
	///
	
	public SceneManager() {
		
		create();
		
	}
	
	///
	///	Destructor
	///
	
	public void dispose() {
		
		for (Scene scene: scenes) {
			scene.dispose();
		}
		
	}

}
