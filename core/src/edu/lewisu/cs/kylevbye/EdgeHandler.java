package edu.lewisu.cs.kylevbye;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;

public class EdgeHandler {
	
	///
	///	Fields
	///
	
	private float minX, maxX;
	private float minY, maxY;
	private float border;
	private OrthographicCamera cam;
	private Batch batch;
	private ScreenObject controlledObject;
	private int verticalStrategy;
	private int horizontalStrategy;
	
	///
	///	Getters
	///
	
	public float getMinX() { return minX; }
	public float getMaxX() { return maxX; }
	public float getMinY() { return minY; }
	public float getMaxY() { return minY; }
	public float getBorder() { return border; }
	public OrthographicCamera getCam() { return cam; }
	public Batch getBatch() { return batch; }
	public ScreenObject getControlledObject() { return controlledObject; } 
	public int getVerticalStrategy() { return verticalStrategy; }
	public int getHorizontalStrategy() { return horizontalStrategy; }
	
	///
	///	Setters
	///
	public void setMinX(float minXIn) { minX = minXIn; }
	public void setMaxX(float maxXIn) { maxX = maxXIn; }
	public void setMinY(float minYIn) { minY = minYIn; }
	public void setMaxY(float maxYIn) { maxY = maxYIn; }
	public void setBorder(float borderIn) { border = borderIn; }
	public void setCam(OrthographicCamera camIn) { cam = camIn; }
	public void setBatch(Batch batchIn) { batch = batchIn; }
	public void setControlledObject(ScreenObject controlledObjectIn) { controlledObject = controlledObjectIn; }
	public void setVerticalStrategy(int verticalStrategyIn) { verticalStrategy = verticalStrategyIn; }
	public void setHorizontalStrategy(int horizontalStrategyIn) { horizontalStrategy = horizontalStrategyIn; }
	
	///
	///	Constants
	///
	
	public final class EdgeConstants {
		
		///
		///	Strategy
		///
		
		public static final int LOCK = 0;
		public static final int WRAP = 1;
		public static final int PAN = 2;
		
		///
		///	Direction
		///
		
		public static final int X_AXIS = 0;
		public static final int Y_AXIS = 1;
		
		///	Prevent Instantiation
		private EdgeConstants() {}
		
	}
	
	///
	///	Functions
	///
	
	public void lock(int axisIn) {
		
		float x = controlledObject.getX();
		float y = controlledObject.getY();
		float width = controlledObject.getWidth();
		float height = controlledObject.getHeight();
		
		switch (axisIn) {
		
		case EdgeConstants.X_AXIS:
			if (x > maxX - width) controlledObject.setX(maxX-width);
			else if (x < minX) controlledObject.setX(minX);
			break;
			
		case EdgeConstants.Y_AXIS:
			if (y > maxY - height) controlledObject.setY(maxY-height);
			else if (y < minY) controlledObject.setY(minY);
			break;
			
		}
		
	}
	
	public void wrap(int axisIn) {
		
		float x = controlledObject.getX();
		float y = controlledObject.getY();
		float width = controlledObject.getWidth();
		float height = controlledObject.getHeight();
		
		switch (axisIn) {
		
		case EdgeConstants.X_AXIS:
			if (x > maxX) controlledObject.setX(minX - width);
			else if (x < minX - width) controlledObject.setX(maxX);
			break;
			
		case EdgeConstants.Y_AXIS:
			if (y > maxY) controlledObject.setY(minY - height);
			else if (y < minY - height) controlledObject.setY(maxY);
			break;
			
		}
		
	}
	
	public void pan(int axisIn) {
		
		float x = controlledObject.getX();
		float y = controlledObject.getY();
		float screenWidth = Gdx.graphics.getWidth();
		float screenHeight = Gdx.graphics.getHeight();
		
		float screenX = x - (cam.position.x - screenWidth/2);
		float screenY = y - (cam.position.y - screenHeight/2);
		
		switch (axisIn) {
		
		case EdgeConstants.X_AXIS:
			if (screenX > screenWidth - controlledObject.getWidth() - border) {
				cam.position.x += screenX - screenWidth + controlledObject.getX() + border;
				updateCamera();
			}
			else if (screenX < border) {
				cam.position.x -= border-screenX;
				updateCamera();
			}
			lock(EdgeConstants.X_AXIS);
			break;
			
		case EdgeConstants.Y_AXIS:
			if (screenY > screenHeight - controlledObject.getHeight() - border) {
				cam.position.y += screenY - screenHeight + controlledObject.getY() + border;
				updateCamera();
			}
			else if (screenY < border) {
				cam.position.y -= border-screenY;
				updateCamera();
			}
			lock(EdgeConstants.Y_AXIS);
			break;
			
		}
		
	}
	
	public void handleEdges() {
		
		switch (horizontalStrategy) {
		
		case EdgeConstants.LOCK:
			lock(EdgeConstants.X_AXIS);
			break;
			
		case EdgeConstants.WRAP:
			wrap(EdgeConstants.X_AXIS);
			break;
			
		case EdgeConstants.PAN:
			pan(EdgeConstants.X_AXIS);
			break;
			
		}
		
		switch (verticalStrategy) {
		
		case EdgeConstants.LOCK:
			lock(EdgeConstants.Y_AXIS);
			break;
			
		case EdgeConstants.WRAP:
			wrap(EdgeConstants.Y_AXIS);
			break;
			
		case EdgeConstants.PAN:
			pan(EdgeConstants.Y_AXIS);
			break;
			
		}
		
	}
	
	public void updateCamera() {
		
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		
	}

	///
	///	Contructors
	///
	
	public EdgeHandler(ScreenObject controlledObjectIn, OrthographicCamera camIn, Batch batchIn) {
		
		this(controlledObjectIn, camIn, batchIn, 0, Gdx.graphics.getWidth(), 0, Gdx.graphics.getHeight(), 
				20, EdgeConstants.LOCK, EdgeConstants.LOCK);
		
	}
	
	public EdgeHandler(ScreenObject controlledObjectIn, OrthographicCamera camIn, Batch batchIn, 
			int horizontalStrategyIn, int verticalStrategyIn) {
		
		this(controlledObjectIn, camIn, batchIn, 0, Gdx.graphics.getWidth(), 0, Gdx.graphics.getHeight(),
				20, horizontalStrategyIn , verticalStrategyIn);
		
	}
	
	public EdgeHandler(ScreenObject controlledObjectIn, OrthographicCamera camIn,
		    Batch batchIn, int horizontalStrategyIn, int verticalStrategyIn, float minXIn, 
		    float maxXIn, float minYIn, float maxYIn, float borderIn) {
		
		setControlledObject(controlledObjectIn);
		setCam(camIn);
		setBatch(batchIn);
		setHorizontalStrategy(horizontalStrategyIn);
		setVerticalStrategy(verticalStrategyIn);
		setMinX(minXIn); setMaxX(maxXIn); 
		setMinY(minYIn); setMaxY(maxYIn);
		setBorder(borderIn);
		
	}
}

