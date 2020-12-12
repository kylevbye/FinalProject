package edu.lewisu.cs.kylevbye;

import com.badlogic.gdx.utils.Disposable;

public abstract class Scene implements Disposable {
	
	public abstract void create();
	
	public abstract void render();
	
	public abstract void dispose();

	public Scene() {
		// TODO Auto-generated constructor stub
	}

}
