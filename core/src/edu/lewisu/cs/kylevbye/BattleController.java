package edu.lewisu.cs.kylevbye;

import edu.lewisu.cs.kylevbye.input.PlayerInput;

public class BattleController {
	
	private PlayerEntity player;
	private BattleButtonUI battleUI;
	private BattleScene scene;
	
	public void defend(AsgoreAttack attackIn) {
		
		AsgoreAttack attack = attackIn;
		attack.play();
		
	}
	
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
	
	public void handleSoul() {
		
		float movementSpeed = 2.5f;
		
		//	Movement
		if (PlayerInput.w_down || PlayerInput.up_down) player.moveUp(movementSpeed);
		if (PlayerInput.a_down || PlayerInput.left_down) player.moveLeft(movementSpeed);
		if (PlayerInput.s_down || PlayerInput.down_down) player.moveDown(movementSpeed);
		if (PlayerInput.d_down || PlayerInput.right_down) player.moveRight(movementSpeed);
		
		
	}

	public BattleController(
			PlayerEntity playerIn, BattleButtonUI battleUIIn, BattleScene sceneIn
			) {
		player = playerIn;
		battleUI = battleUIIn;
		scene = sceneIn;
	}

}
