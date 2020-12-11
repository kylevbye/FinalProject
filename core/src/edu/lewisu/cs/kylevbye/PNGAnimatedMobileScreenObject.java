/**
 * 
 */
package edu.lewisu.cs.kylevbye;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * @author byekv
 *
 */
public class PNGAnimatedMobileScreenObject extends MobileScreenObject {
	
	private int counter;
	private int frameCount;
	private int frameDelay;
	private ArrayList<Image> frames;
	
	///
	///	Getters
	///
	
	public int getCounter() { return counter; }
	public int getFrameCount() { return frameCount; }
	public ArrayList<Image> getFrames() { return frames; }
	
	///
	///	Setters
	///
	
	public void setCounter(int counterIn) { counter = counterIn; }
	public void setFrameCount(int frameCountIn) { frameCount = frameCountIn; }
	public void setFrameDelay(int frameDelayIn) { frameDelay = frameDelayIn; }
	public void setFrames(ArrayList<Image> framesIn) { frames = framesIn; }
	
	///
	///	Setters - Image - Actor
	///
	
	@Override 
	public void setX(float xIn) {
		super.setX(xIn);
		if (frames != null) for (Image frame : frames) { frame.setX(xIn); }
	}
	
	@Override 
	public void setY(float yIn) {
		super.setY(yIn);
		if (frames != null) for (Image frame : frames) { frame.setY(yIn); }
	}
	
	@Override 
	public void setWidth(float widthIn) {
		super.setWidth(widthIn);
		if (frames != null) for (Image frame : frames) {frame.setWidth(widthIn);}
	}
	
	@Override 
	public void setHeight(float heightIn) {
		super.setHeight(heightIn);
		if (frames != null) for (Image frame : frames) {frame.setHeight(heightIn);}
	}
	
	@Override
	public void setPosition(float xIn, float yIn) {
		super.setPosition(xIn, yIn);
		if (frames != null) for (Image frame : frames) {frame.setPosition(xIn, yIn);}
	}
	
	@Override
	public void setPosition(float xIn, float yIn, int alignmentIn) {
		super.setPosition(xIn, yIn, alignmentIn);
		if (frames != null) for (Image frame : frames) {frame.setPosition(xIn, yIn, alignmentIn);}
	}
	
	@Override
	public void setBounds(float xIn, float yIn, float widthIn, float heightIn) {
		super.setBounds(xIn, yIn, widthIn, heightIn);
		if (frames != null) for (Image frame : frames) {frame.setBounds(xIn, yIn, widthIn, heightIn); }
	}
	
	@Override
	public void setColor(Color colorIn) {
		super.setColor(colorIn);
		if (frames != null) for (Image frame : frames) { frame.setColor(colorIn); }
	}
	
	@Override
	public void setColor(float rIn, float gIn, float bIn, float aIn) {
		Color newColor = new Color(rIn, gIn, bIn, aIn);
		super.setColor(newColor);
		if (frames != null) for (Image frame : frames) { frame.setColor(newColor); }
	}
	
	@Override
	public void setOriginX(float originXIn) {
		super.setOriginX(originXIn);
		if (frames != null) for (Image frame : frames) { frame.setOriginX(originXIn); }
	}
	
	@Override
	public void setOriginY(float originYIn) {
		super.setOriginY(originYIn);
		if (frames != null) for (Image frame : frames) { frame.setOriginY(originYIn); }
	}
	
	@Override
	public void setOrigin(float originXIn, float originYIn) {
		super.setOrigin(originXIn, originYIn);
		if (frames != null) for (Image frame : frames) { frame.setOrigin(originXIn, originYIn); }
	}
	
	@Override
	public void setOrigin(int alignmentIn) {
		super.setOrigin(alignmentIn);
		if (frames != null) for (Image frame : frames) { frame.setOrigin(alignmentIn); }
	}
	
	@Override
	public void setRotation(float degreesIn) {
		super.setRotation(degreesIn);
		if (frames != null) for (Image frame : frames) { frame.setRotation(degreesIn); }
	}
	
	@Override
	public void setScaleX(float scaleXIn) {
		super.setScaleX(scaleXIn);
		if (frames != null) for (Image frame : frames) { frame.setScaleX(scaleXIn); }
	}
	
	@Override
	public void setScaleY(float scaleYIn) {
		super.setScaleY(scaleYIn);
		if (frames != null) for (Image frame : frames) { frame.setScaleY(scaleYIn); }
	}
	
	@Override
	public void setScale(float scaleXIn, float scaleYIn) {
		super.setScale(scaleXIn, scaleYIn);
		if (frames != null) for (Image frame : frames) { frame.setScale(scaleXIn, scaleYIn); }
	}
	
	@Override
	public void setScale(float scaleXYIn) {
		super.setScale(scaleXYIn);
		if (frames != null) for (Image frame : frames) { frame.setScale(scaleXYIn); }
	}
	
	@Override
	public void setSize(float widthIn, float heightIn) {
		super.setSize(widthIn, heightIn);
		if (frames != null) for (Image frame : frames) { frame.setSize(widthIn, heightIn); }
	}
	
	
	///
	///	Functions
	///
	
	public void incrementCounter() { ++counter; }
	
	public void incrementFrameCount() { ++frameCount; }
	
	public void resetFrameCount() { setFrameCount(0); }
	
	@Override
	public void draw(Batch batchIn, float parentAlphaIn) {
		
		frames.get(frameCount).draw(batchIn, parentAlphaIn);
		
	}
	
	public void play() {
		
		if (counter % frameDelay == 0) {
			
			if (frameCount == frames.size()-1) frameCount = 0;
			else incrementFrameCount();
		}
		
		incrementCounter();
	}
	
	///
	///	Constructors
	///
	
	public PNGAnimatedMobileScreenObject(ArrayList<Image> framesIn) {
		this(0f, 0f, framesIn);
	}
	
	public PNGAnimatedMobileScreenObject(float xIn, float yIn, ArrayList<Image> framesIn) {
		super(framesIn.get(0));
		setFrames(framesIn);
		setFrameDelay(0);
		setPosition(xIn, yIn);
		setCounter(0);
		setFrameCount(0);
	}


}
