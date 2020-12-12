package edu.lewisu.cs.kylevbye;

import edu.lewisu.cs.kylevbye.input.PlayerInput;

public class BattleController {
	
	private PlayerEntity player;
	private BattleButtonUI battleUI;
	
	public void handleMenu() {
		
		
		if (PlayerInput.aWasPressed || PlayerInput.leftWasPressed) {
			battleUI.setSelection(battleUI.getSelection()-1);
		}
		if (PlayerInput.dWasPressed || PlayerInput.rightWasPressed) {
			battleUI.setSelection(battleUI.getSelection()+1);
		}
		
		
	}
	
	public void handleSoul() {
		
		float movementSpeed = 1f;
		
		//	Movement
		if (PlayerInput.w_down || PlayerInput.up_down) player.moveUp(movementSpeed);
		if (PlayerInput.a_down || PlayerInput.left_down) player.moveLeft(movementSpeed);
		if (PlayerInput.s_down || PlayerInput.down_down) player.moveDown(movementSpeed);
		if (PlayerInput.d_down || PlayerInput.right_down) player.moveRight(movementSpeed);
		
		
	}

	public BattleController(PlayerEntity playerIn, BattleButtonUI battleUIIn) {
		player = playerIn;
		battleUI = battleUIIn;
	}

}
