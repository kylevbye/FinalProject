package edu.lewisu.cs.kylevbye.input;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;

public class InputHandler extends InputAdapter {
	
	///
	///	Functions
	///
	
	@Override
	public boolean mouseMoved (int screenX, int screenY) {
		
		super.mouseMoved(screenX, screenY);
		
		PlayerInput.mouseX = screenX;
		PlayerInput.mouseY = screenY;
		
		return true;
		
	}
	
	@Override 
	public boolean touchDown (int screenX, int screenY, int pointer, int button) {
		
		super.touchDown(screenX, screenY, pointer, button);
		
		switch(button) {
		
		case Buttons.LEFT:
			PlayerInput.mLeft_down = true;
			PlayerInput.mLeftWasClicked = true;
			PlayerInput.lastLeftClickLocation = new Vector2(screenX, screenY);
			break;
			
		case Buttons.MIDDLE:
			PlayerInput.mMiddle_down = true;
			PlayerInput.mMiddleWasClicked = true;
			PlayerInput.lastMiddleClickLocation = new Vector2(screenX, screenY);
			break;
			
		case Buttons.RIGHT:
			PlayerInput.right_down = true;
			PlayerInput.mRightWasClicked = true;
			PlayerInput.lastRightClickLocation = new Vector2(screenX, screenY);
			break;
			
		}
		
		return true;
		
	}
	
	@Override 
	public boolean touchUp (int screenX, int screenY, int pointer, int button) {
		
		super.touchUp(screenX, screenY, pointer, button);
		
		switch(button) {
		
		case Buttons.LEFT:
			PlayerInput.mLeft_down = false;
			
		case Buttons.RIGHT:
			PlayerInput.mright_down = false;
			
		case Buttons.MIDDLE:
			PlayerInput.mMiddle_down = false;
			
		}
		
		return true;
		
	}
	
	
	
	@Override
	public boolean keyDown(int keyCode) {
		
		super.keyDown(keyCode);
			
		switch (keyCode) {
		
		//	Shift
		case Keys.SHIFT_LEFT:
			PlayerInput.shift_down = true;
			PlayerInput.shiftWasPressed = true;
			break;
			
		case Keys.SHIFT_RIGHT:
			PlayerInput.shift_down = true;
			PlayerInput.shiftWasPressed = true;
			break;
		
		//	WASD
		case Keys.W:
			PlayerInput.w_down = true;
			PlayerInput.wWasPressed = true;
			break;
		
		case Keys.A: 
			PlayerInput.a_down = true;
			PlayerInput.aWasPressed = true;
		
		case Keys.S:
			PlayerInput.s_down = true;
			PlayerInput.sWasPressed = true;
			
		case Keys.D:
			PlayerInput.d_down = true;
			PlayerInput.dWasPressed = true;
			
		//	Arrow
		case Keys.UP:
			PlayerInput.up_down = true;
			PlayerInput.upWasPressed = true;
		
		case Keys.LEFT:
			PlayerInput.left_down = true;
			PlayerInput.leftWasPressed = true;
			
		case Keys.DOWN:
			PlayerInput.down_down = true;
			PlayerInput.downWasPressed = true;
			
		case Keys.RIGHT:
			PlayerInput.right_down = true;
			PlayerInput.rightWasPressed = true;
			
		}
		
		
		return true;
			
	}

	@Override 
	public boolean keyUp(int keyCode) {
		
		super.keyUp(keyCode);
		
		switch (keyCode) {
		
		//	Shift
		case Keys.SHIFT_LEFT:
			PlayerInput.shift_down = false;
			break;
			
		case Keys.SHIFT_RIGHT:
			PlayerInput.shift_down = false;
			break;
		
		//	WASD
		case Keys.W:
			PlayerInput.w_down = false;
			break;
		
		case Keys.A: 
			PlayerInput.a_down = false;
		
		case Keys.S:
			PlayerInput.s_down = false;
			
		case Keys.D:
			PlayerInput.d_down = false;
			
		//	Arrow
		case Keys.UP:
			PlayerInput.up_down = false;
		
		case Keys.LEFT:
			PlayerInput.left_down = false;
			
		case Keys.DOWN:
			PlayerInput.down_down = false;
			
		case Keys.RIGHT:
			PlayerInput.right_down = false;
		
		}
		
		return true;
		
	}
	
}
