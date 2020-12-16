package edu.lewisu.cs.kylevbye;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Intersector.MinimumTranslationVector;
import com.badlogic.gdx.math.Polygon;

/**
 * This class holds the entities and values to
 * represent an attack Asgore makes. Use its
 * factory <code>AsgoreAttackFactory</code> to
 * create the attacks.
 * 
 * @author	Kyle Bye
 *
 */
public class AsgoreAttack {
	
	public static OrthographicCamera cam;
	public static Batch batch;
	public static PlayerEntity player;
	public static float middleX, middleY;
	public static float borderThickness;
	
	private ArrayList<MobileScreenObject> attackParticles;
	
	private HollowedRectangle restriction;
	private EdgeHandler restrictor;
	private boolean isActive;
	private float damageValue;
	private int counter;
	private int length;
	
	///
	///	Getters
	///
	
	public float getDamageValue() { return damageValue; }
	public HollowedRectangle getRestriction() { return restriction; }
	public ArrayList<MobileScreenObject> getAttackParticles() { return attackParticles; }
	
	///
	///	Setters
	///
	
	public void setDamageValue(float damageValueIn) { damageValue = damageValueIn; }
	public void setRestriction(HollowedRectangle restrictionIn) { restriction = restrictionIn; }
	public void setAttackParticles(ArrayList<MobileScreenObject> attackParticlesIn) { attackParticles = attackParticlesIn; }
	
	///
	///	Functions
	///
	
	/**
	 * Returns true if this attack is active.
	 * @return	true/false
	 */
	public boolean isActive() {
		
		return isActive;
		
	}
	
	/**
	 * Returns true if this instance is colliding with
	 * the provided.
	 * @param	playerIn	playerOjbect
	 * @return	true/false
	 */
	public boolean isColliding(ScreenObject playerIn) {
		
		boolean collide = false;
		
		for (ScreenObject attackParticle : attackParticles) {
			Polygon p1 = attackParticle.getBoundingPolygon();
	        Polygon p2 = playerIn.getBoundingPolygon();
	        MinimumTranslationVector mtv = new MinimumTranslationVector();
	        if (Intersector.overlapConvexPolygons(p1, p2, mtv)) {
	        	collide = true;
	        }
		}
        
        return collide;
	}
	
	/**
	 * Restricts the player model between the restriction boundaries along
	 * with applying the physics of this attack's particles.
	 */
	public void play() {
		
		restrictor.handleEdges();
		
		if (counter == length) {
			isActive = false;
		}
		
		for (MobileScreenObject attackParticle : attackParticles) {
			
			attackParticle.applyPhysics(1f);
		
		}
		
		++counter;
		
	}
	
	/**
	 * Draws the attack and the mini-arena for the player
	 * to dodge.
	 * 
	 * @param batchIn
	 * @param parentAlphaIn
	 */
	public void draw(Batch batchIn, float parentAlphaIn) {
		
		restriction.draw(batchIn, parentAlphaIn);
		for (MobileScreenObject attackParticle : attackParticles) {
			attackParticle.draw(batchIn, parentAlphaIn);
		}
		
	}
	
	///
	///	Constructors
	///
	
	/**
	 * Constructs the attack boundaries for the player with the
	 * provided parameters. Length of attack means how many frames
	 * before the attack ends.
	 * 
	 * You must organize the attack particles yourself or construct
	 * an attack using a factory.
	 * 
	 * @param	widthR	width of restriction
	 * @param	heightR	height of restriction
	 * @param	lengthIn	length of attack in frames
	 */
	public AsgoreAttack(float widthR, float heightR, int lengthIn) {
		
		attackParticles = new ArrayList<MobileScreenObject>();
		
		HollowedRectangle rest = new HollowedRectangle(0, 0, widthR, heightR, new Color(1,1,1,1));
		rest.setPosition(middleX-widthR/2, middleY-heightR/2);
		rest.setThickness(borderThickness);
		setRestriction(rest);
		setDamageValue(1f);
		
		restrictor = new EdgeHandler(
				player, cam, batch, EdgeHandler.EdgeConstants.LOCK, 
				EdgeHandler.EdgeConstants.LOCK, rest.getX(), rest.getWidth()+rest.getX(), rest.getY(),
				rest.getHeight()+rest.getY(), borderThickness
				);
		
		isActive = true;
		counter = 0;
		length = lengthIn;
		player.setPosition(middleX-player.getWidth()/2, middleY-player.getHeight()/2);
		
	}
	
	///
	///	Destructors
	///
	
	/**
	 * Cleans up memory
	 */
	public void dispose() {
		
		restriction.dispose();
		for (MobileScreenObject attackParticle : attackParticles) attackParticle.dispose();
		
	}

}
