package edu.lewisu.cs.cpsc41000.common.motioncontrollers;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import edu.lewisu.cs.kylevbye.MobileScreenObject;
import edu.lewisu.cs.kylevbye.ScreenObject;

/**
 * Tracker allows a chaser to follow a target in a straight-line manner.
 * The chaser is a MobileImagedBasedScreenObject.
 * The target is an ImageBasedScreenObject
 */
public class Tracker {
    private MobileScreenObject chaser;
    private ScreenObject target;
    private float accuracy;  // how accurate the tracking is. 1 means perfect
    public Tracker() {
        chaser = null;
        target = null;
        accuracy = 1f;
    }
    public Tracker(MobileScreenObject chaser, ScreenObject target) {
        this.chaser = chaser;
        this.target = target;
        accuracy = 1f;
    }
    public Tracker(MobileScreenObject chaser, ScreenObject target, float accuracy) {
        this(chaser,target);
        this.accuracy = accuracy;
    }
    public float getAccuracy() {
        return accuracy;
    }
    public void setAccuracy(float accuracy) {
        if (accuracy > 1) {
            this.accuracy = 1f;
        } else if (accuracy < 0) {
            this.accuracy = 0f;
        } else {
            this.accuracy = accuracy;
        }
    }
    public MobileScreenObject getChaser() {
        return chaser;
    }
    public void setChaser(MobileScreenObject chaser) {
        this.chaser = chaser;
    }
    public ScreenObject getTarget() {
        return target;
    }
    public void setTarget(MobileScreenObject target) {
        this.target = target;
    }
    public float getFudgeFactor() {
        if (accuracy == 1) {
            return 1f;
        } else {
            return MathUtils.random(accuracy,2f-accuracy);
        }
    }
    /**
     * This function adjusts the motion of the chaser to approach the target.
     * It takes in the dt so that it can accelerate in the direction of
     * where the target is relative to the chaser.
     * @param dt
     */
    public void track(float dt) {
        float diffX = target.getX() - chaser.getX();
        float diffY = target.getY() - chaser.getY();
        float ang = MathUtils.atan2(getFudgeFactor()*diffY,getFudgeFactor()*diffX) * MathUtils.radiansToDegrees;
        chaser.accelerateAtAngle(ang);
        chaser.applyPhysics(dt);
    }

    public void avoid(Vector2 dir, float dt) {
        float diffX, diffY;
        if (dir.x > 0) {
            diffX = target.getX() - chaser.getX();
            if (diffX < 0) {
                chaser.accelerateAtAngle(-dir.angle());
            } else {
                chaser.accelerateAtAngle(dir.angle());
            }
        } else {
            diffY = target.getY() - chaser.getY();
            if (diffY < 0) {
                chaser.accelerateAtAngle(-dir.angle());
            } else {
                chaser.accelerateAtAngle(dir.angle());
            }
        }
        chaser.applyPhysics(dt);
    }
    
}
