package edu.lewisu.cs.kylevbye;

import edu.lewisu.cs.kylevbye.input.PlayerInput;

/**
 * This class is mostly an extension of the BattleScene.
 * Holds functions that BattleScene would use to process 
 * game logic.
 * 
 * @author	Kyle V Bye
 */
public class BattleController {
	
	private PlayerEntity player;
	private BattleButtonUI battleUI;
	private BattleScene scene;
	
	///
	///	Functions
	///
	
	/**
	 * Runs asgore's attack
	 * @param	attackIn	attack to run.
	 */
	public void defend(AsgoreAttack attackIn) {
		
		AsgoreAttack attack = attackIn;
		attack.play();
		
	}
	
	/**
	 * Manages the controls and applies the appropriate actions
	 * in the menu battleUI.
	 */
	public void handleMenu() {
		
		
		if (PlayerInput.aWasPressed || PlayerInput.leftWasPressed) {
			battleUI.setSelection(battleUI.getSelection()-1);
		}
		if (PlayerInput.dWasPressed || PlayerInput.rightWasPressed) {
			battleUI.setSelection(battleUI.getSelection()+1);
		}
		if (PlayerInput.zWasPressed) {
			if (battleUI.getSelection() == BattleButtonUI.ButtonConstants.FIGHT) {
				battleUI.setSelection(-1);
				scene.setState(BattleScene.BattleSceneConstants.ATTACK);
				AssetManager.addToSoundQueue(scene.getAttackSlashSound());
			}
		}
		
		
	}
	
	/**
	 * Handles player movements when player is dodging.
	 */
	public void handleSoul() {
		
		float movementSpeed = 2.5f;
		
		//	Movement
		if (PlayerInput.w_down || PlayerInput.up_down) player.moveUp(movementSpeed);
		if (PlayerInput.a_down || PlayerInput.left_down) player.moveLeft(movementSpeed);
		if (PlayerInput.s_down || PlayerInput.down_down) player.moveDown(movementSpeed);
		if (PlayerInput.d_down || PlayerInput.right_down) player.moveRight(movementSpeed);
		
		
	}
	
	///
	///	Constructors
	///
	
	public BattleController(
			PlayerEntity playerIn, BattleButtonUI battleUIIn, BattleScene sceneIn
			) {
		player = playerIn;
		battleUI = battleUIIn;
		scene = sceneIn;
	}

}
