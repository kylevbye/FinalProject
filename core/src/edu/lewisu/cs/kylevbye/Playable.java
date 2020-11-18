package edu.lewisu.cs.kylevbye;

/**
 * This interface describes an object that
 * is able to play a sound.
 * 
 * @author	Kyle V Bye
 */
public interface Playable {
	
	/**
	 * Play a sound.
	 */
	public void play();
	
	/**
	 * Play a sound at a certain volume.
	 * @param	volumeIn	volume to play the sound
	 */
	public void play(float volumeIn);
	
	/**
	 * Play a sound at a certain volume, pitch, and pan.
	 * 
	 * @param	volumeIn	volume to play the sound
	 * @param	pitchIn	pitch to play the sound at
	 * @param	panIn	pan to play the sound at
	 */
	public void play(float volumeIn, float pitchIn, float panIn);
	
}
