package edu.lewisu.cs.kylevbye;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;

/**
 * This Class is responsible for creating AsgoreAttack instances
 * for use the BattleScene.
 * 
 * @author	Kyle Bye
 */
public class AsgoreAttackFactory {
	
	public static PlayerEntity player;
	
	private static float WIDTH, HEIGHT;
	
	private static Random random = new Random();
	
	/**
	 * Returns a random type of AsgoreAttack.
	 * 
	 * @return	AsgoreAttack
	 */
	public static AsgoreAttack generateRandomAsgoreAttack() {

		
		return generateAsgoreAttack(random.nextInt(4));
		
	}
	
	/**
	 * Returns a certain type of AsgoreAttack based on the
	 * ID you provided.
	 * 
	 * Any value not within [0,4) will return the first
	 * generated attack.
	 * 
	 * @param	asgoreAttackID	attack indentification
	 * @return	AsgoreAttack
	 */
	public static AsgoreAttack generateAsgoreAttack(int asgoreAttackID) {
		
		AsgoreAttack attack;
		
		switch (asgoreAttackID) {
		
		case 0:
			attack = generateAttack1();
			break;
			
		case 1:
			attack = generateAttack2();
			break;
		
		case 2:
			attack = generateAttack3();
			break;
		
		case 3:
			attack = generateAttack4();
			break;
			
		default:
			attack = generateAttack1();
			break;
			
		}
		
		
		return attack;
		
	}
	
	///
	///	Attack Generators
	///
	
	/*
	 * Generates a instance of Asgore's 1st Attack
	 */
	private static AsgoreAttack generateAttack1() {
		
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		
		AsgoreAttack attack;
		float boundWidth, boundHeight;
		int length;
		boundWidth = 150; boundHeight = 150; length = 300;
		attack = new AsgoreAttack(boundWidth, boundHeight, length);
		
		attack.setDamageValue(20f);
		
		float targetX, targetY;
		
		targetX = AsgoreAttack.middleX - player.getWidth()/2;
		targetY = AsgoreAttack.middleY - player.getWidth()/2;
		
		float displacement = WIDTH/5;
		
		
		//	Load
		ArrayList<MobileScreenObject> attackParticles = new ArrayList<MobileScreenObject>();
		for (int i = 0; i<8; ++i) {
			
			MobileScreenObject attackParticle = loadAsgoreParticle2();
			
			switch (i) {
			
			case 0:
				attackParticle.setPosition(AsgoreAttack.middleX-displacement, AsgoreAttack.middleY);
				break;
				
			case 1:
				attackParticle.setPosition(AsgoreAttack.middleX+displacement, AsgoreAttack.middleY);
				break;
				
			case 2:
				attackParticle.setPosition(AsgoreAttack.middleX, AsgoreAttack.middleY+displacement);
				break;
				
			case 3:
				attackParticle.setPosition(AsgoreAttack.middleX, AsgoreAttack.middleY-displacement);
				break;
				
			case 4:
				attackParticle.setPosition(AsgoreAttack.middleX-displacement, AsgoreAttack.middleY-displacement);
				break;
				
			case 5:
				attackParticle.setPosition(AsgoreAttack.middleX+displacement, AsgoreAttack.middleY+displacement);
				break;
				
			case 6:
				attackParticle.setPosition(AsgoreAttack.middleX-displacement, AsgoreAttack.middleY+displacement);
				break;
				
			case 7:
				attackParticle.setPosition(AsgoreAttack.middleX+displacement, AsgoreAttack.middleY-displacement);
				break;
				
			}
			
			//attackParticle.moveX(attackParticle.getWidth()/2);
			
			//	Set Trajectory to Center
			float angle = MathUtils.radiansToDegrees*MathUtils.atan2(
					targetY-attackParticle.getY(), targetX-attackParticle.getX()
					);
			attackParticle.setAcceleration(1f);
			attackParticle.setMaxSpeed(50);
			attackParticle.accelerateAtAngle(angle);
			
			attackParticles.add(attackParticle);
		}
		
		attack.setAttackParticles(attackParticles);
		
		//	Set Trajectories
		
		
		return attack;
		
	}
	
	/*
	 * Generates a instance of Asgore's 2nd Attack
	 */
	private static AsgoreAttack generateAttack2() {
		
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		
		AsgoreAttack attack;
		float boundWidth, boundHeight;
		int length;
		boundWidth = 150; boundHeight = 150; length = 600;
		attack = new AsgoreAttack(boundWidth, boundHeight, length);
		
		attack.setDamageValue(10f);
		
		float targetX, targetY;
		
		targetX = AsgoreAttack.middleX - player.getWidth()/2;
		targetY = AsgoreAttack.middleY - player.getWidth()/2;
		
		float displacement = WIDTH/5;
		
		
		//	Load
		ArrayList<MobileScreenObject> attackParticles = new ArrayList<MobileScreenObject>();
			
		for (int j = 0; j<20; ++j) {
			MobileScreenObject attackParticle = loadAsgoreParticle2();
			float x = WIDTH/10 - j*attackParticle.getWidth()*2.5f;
			float y = AsgoreAttack.middleY - player.getHeight()/2;
			attackParticle.setPosition(x, y);
			attackParticle.setAcceleration(2f);
			attackParticle.setMaxSpeed(200);
			attackParticle.accelerateAtAngle(0);
			attackParticles.add(attackParticle);
		}
			
		for (int j = 0; j<15; ++j) {
			MobileScreenObject attackParticle = loadAsgoreParticle2();
			float x = 2.5f*WIDTH - j*attackParticle.getWidth()*2.5f;
			float y = AsgoreAttack.middleY - player.getHeight()/2 + WIDTH/10;
			attackParticle.setPosition(x, y);
			attackParticle.setAcceleration(2f);
			attackParticle.setMaxSpeed(200);
			attackParticle.accelerateAtAngle(-180);
			attackParticles.add(attackParticle);
		}
		
		for (int j = 0; j<10; ++j) {
			MobileScreenObject attackParticle = loadAsgoreParticle2();
			float x = AsgoreAttack.middleX - player.getWidth()/2;
			float y = AsgoreAttack.middleY - HEIGHT - j*attackParticle.getHeight()*2;
			attackParticle.setPosition(x, y);
			attackParticle.setAcceleration(2f);
			attackParticle.setMaxSpeed(200);
			attackParticle.accelerateAtAngle(90);
			attackParticles.add(attackParticle);
		}
		
		for (int j = 0; j<10; ++j) {
			MobileScreenObject attackParticle = loadAsgoreParticle2();
			float x = AsgoreAttack.middleX - player.getWidth() + WIDTH/10;
			float y = 2*HEIGHT + j*attackParticle.getHeight()*2.5f;
			attackParticle.setPosition(x, y);
			attackParticle.setAcceleration(2f);
			attackParticle.setMaxSpeed(200);
			attackParticle.accelerateAtAngle(270);
			attackParticles.add(attackParticle);
		}
		
		for (int j = 0; j<10; ++j) {
			MobileScreenObject attackParticle = loadAsgoreParticle2();
			float x = AsgoreAttack.middleX - player.getWidth();
			float y = 2*HEIGHT + j*attackParticle.getHeight()*2.5f;
			attackParticle.setPosition(x, y);
			attackParticle.setAcceleration(2f);
			attackParticle.setMaxSpeed(250);
			attackParticle.accelerateAtAngle(270);
			attackParticles.add(attackParticle);
		}
		
		for (int j = 0; j<15; ++j) {
			MobileScreenObject attackParticle = loadAsgoreParticle2();
			float x = AsgoreAttack.middleX - player.getWidth() + WIDTH/8;
			float y = 2*HEIGHT + j*attackParticle.getHeight()*2.5f;
			attackParticle.setPosition(x, y);
			attackParticle.setAcceleration(3f);
			attackParticle.setMaxSpeed(350);
			attackParticle.accelerateAtAngle(270);
			attackParticles.add(attackParticle);
		}
		
		for (int j = 0; j<15; ++j) {
			MobileScreenObject attackParticle = loadAsgoreParticle2();
			float x = AsgoreAttack.middleX + player.getWidth() - WIDTH/8;
			float y = 2*HEIGHT + j*attackParticle.getHeight()*2.5f;
			attackParticle.setPosition(x, y);
			attackParticle.setAcceleration(4f);
			attackParticle.setMaxSpeed(350);
			attackParticle.accelerateAtAngle(270);
			attackParticles.add(attackParticle);
		}
			
		
		
		attack.setAttackParticles(attackParticles);		
		
		return attack;
		
	}
	
	/*
	 * Generates a instance of Asgore's 3rd Attack
	 */
	private static AsgoreAttack generateAttack3() {
		
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		
		AsgoreAttack attack;
		float boundWidth, boundHeight;
		int length;
		boundWidth = 300; boundHeight = 150; length = 600;
		attack = new AsgoreAttack(boundWidth, boundHeight, length);
		
		attack.setDamageValue(10f);
		
		float targetX, targetY;
		
		targetX = AsgoreAttack.middleX - player.getWidth()/2;
		targetY = AsgoreAttack.middleY - player.getWidth()/2;
		
		float dX = WIDTH/5;
		float dY = HEIGHT/5;
		
		
		//	Load
		ArrayList<MobileScreenObject> attackParticles = new ArrayList<MobileScreenObject>();
		
		for (int i = 0; i<20; ++i) {
			MobileScreenObject attackParticle = loadAsgoreParticle2();
			float x = -WIDTH/200 + dX*(i-10);
			float y = HEIGHT + HEIGHT/400 + dY*(i-10);
			attackParticle.setPosition(x, y);
			attackParticles.add(attackParticle);
		}
		
		for (int i = 0; i<20; ++i) {
			MobileScreenObject attackParticle = loadAsgoreParticle2();
			float x = -WIDTH/200 + dX*(i-10);
			float y = -HEIGHT - HEIGHT/400 + dY*(i-10);
			attackParticle.setPosition(x, y);
			attackParticles.add(attackParticle);
		}
		
		for (int i = 0; i<20; ++i) {
			MobileScreenObject attackParticle = loadAsgoreParticle2();
			float x = WIDTH + WIDTH/50 + dX*(i-10);
			float y = HEIGHT + HEIGHT/100 + dY*(i-10);
			attackParticle.setPosition(4*x, 3*y);
			attackParticles.add(attackParticle);
		}
		
		for (int i = 0; i<20; ++i) {
			MobileScreenObject attackParticle = loadAsgoreParticle2();
			float x = -WIDTH/200 + dX*(i-10);
			float y = HEIGHT + HEIGHT/100 + dY*(i-10);
			attackParticle.setPosition(4*x, 3*y);
			attackParticles.add(attackParticle);
		}
		
		for (MobileScreenObject attackParticle : attackParticles) {
		//	Set Trajectory to Center
			float angle = MathUtils.radiansToDegrees*MathUtils.atan2(
					targetY-attackParticle.getY(), targetX-attackParticle.getX()
				);
			attackParticle.setAcceleration(5f);
			attackParticle.setMaxSpeed(50);
			attackParticle.accelerateAtAngle(angle);
		}
			
		attack.setAttackParticles(attackParticles);		
		
		return attack;
		
	}
	
	/*
	 * Generates a instance of Asgore's 4th Attack
	 */
	private static AsgoreAttack generateAttack4() {
		
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		
		AsgoreAttack attack;
		float boundWidth, boundHeight;
		int length;
		boundWidth = 70; boundHeight = 150; length = 600;
		attack = new AsgoreAttack(boundWidth, boundHeight, length);
		
		attack.setDamageValue(15f);
		
		float targetX, targetY;
		
		targetX = AsgoreAttack.middleX - player.getWidth()/2;
		targetY = AsgoreAttack.middleY - player.getWidth()/2;
		
		float dX = WIDTH/120;
		float dY = (.48f)*HEIGHT;
		MobileScreenObject temp = loadAsgoreParticle2();
		float pWidth = temp.getWidth();
		float pHeight = temp.getHeight();
		temp.dispose();
		
		ArrayList<MobileScreenObject> attackParticles = new ArrayList<MobileScreenObject>();
		
		float x;
		float xLeft = AsgoreAttack.middleX - pWidth/2*dX;
		float xRight = AsgoreAttack.middleX + dX;
		
		
		for (int k = 0; k < 20; ++k) {
			
			if (generateRandomSideVal() == 1) x = xLeft;
			else x = xRight;
			
			for (int i = 0; i<3; ++i) {
				float y = AsgoreAttack.middleY + HEIGHT + dY*k;
				for (int j = 0; j<5; ++j) {
					MobileScreenObject attackParticle = loadAsgoreParticle2();
					attackParticle.setPosition(x+i*pWidth, y+j*pHeight);
					attackParticles.add(attackParticle);
				}
			}
		}
		
		for (MobileScreenObject attackParticle : attackParticles) {
			attackParticle.setAcceleration(8f);
			attackParticle.setMaxSpeed(50);
			attackParticle.accelerateAtAngle(270);
		}
		
			
		attack.setAttackParticles(attackParticles);		
		
		return attack;
		
	}
	
	///
	///	Helpers
	///
	
	private static MobileScreenObject loadAsgoreParticle1() {
		
		return new MobileScreenObject(AssetManager.loadImage("asgoreAttacks/asgoreParticle1.png"));
		
	}
	
	private static MobileScreenObject loadAsgoreParticle2() {
		
		return new MobileScreenObject(AssetManager.loadImage("asgoreAttacks/asgoreParticle2.png"));
		
	}
	
	/*
	 * Returns 1 or -1 randomly.
	 */
	private static int generateRandomSideVal() {
		
		int i = random.nextInt(2);
		
		if (i == 1) return 1;
		else return -1;
		
	}

}
