/**
 * 
 */
package edu.lewisu.cs.kylevbye;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * @author byekv
 *
 */
public class BattleButtonUI extends ScreenObject {
	
	///
	/// Fields
	///
	
	private int selection;
	private int gap;
	private Image battleButton;
	private Image battleButtonH;
	private Image actButton;
	private Image actButtonH;
	private Image itemButton;
	private Image itemButtonH;
	
	///
	///	Getters
	///
	
	public int getSelection() { return selection; }
	public int getGap() { return gap; }
	public Image getBattleButton() { return battleButton; }
	public Image getBattleButtonH() { return battleButtonH; }
	public Image getActButton() { return actButton; }
	public Image getActButtonH() { return actButtonH; }
	public Image getItemButton() { return itemButtonH; }
	public Image getItemButtonH() { return itemButtonH; }	
	
	///
	///	Setters
	///
	
	public void setSelection(int selectionIn) { 
		if (selectionIn >= 0 && selectionIn <= 2) selection = selectionIn; 
	}
	public void setGap(int gapIn) { gap = gapIn; }
	
	///
	///	Constants
	///
	public class ButtonConstants {
		
		public static final int FIGHT = 0;
		public static final int ACT = 1;
		public static final int ITEM = 2;
		
	}
	
	///
	///	Functions
	///
	
	@Override
	public void draw(Batch batchIn, float parentAlphaIn) {
		
		placeButtons();
		
		if (battleButton.isVisible()) {
			
			if (selection == ButtonConstants.FIGHT) {
				battleButtonH.draw(batchIn, parentAlphaIn);
			}
			else battleButton.draw(batchIn, parentAlphaIn);
			
		}
		
		if (actButton.isVisible()) {
			
			if (selection == ButtonConstants.ACT) {
				actButtonH.draw(batchIn, parentAlphaIn);
			}
			else actButton.draw(batchIn, parentAlphaIn);
			
		}
		
		if (itemButton.isVisible()) {
			
			if (selection == ButtonConstants.ITEM) {
				itemButtonH.draw(batchIn, parentAlphaIn);
			}
			else itemButton.draw(batchIn, parentAlphaIn);
			
		}
		
	}
	
	public void placeButtons() {
		
		float UIx = getX();
		float UIy = getY();
		
		battleButton.setPosition(UIx, UIy);
		battleButtonH.setPosition(battleButton.getX(), battleButton.getY());
		
		actButton.setPosition(battleButton.getX() + gap + battleButton.getWidth(), UIy);
		actButtonH.setPosition(actButton.getX(), actButton.getY());
		
		itemButton.setPosition(actButton.getX() + gap + actButton.getWidth(), UIy);
		itemButtonH.setPosition(itemButton.getX(), itemButton.getY());
		
		calculateBounds();
	}
	
	public void calculateBounds() {
		
		float width = itemButton.getX() + itemButton.getWidth();
		float height = battleButton.getHeight();
		
		setWidth(width);
		setHeight(height);
		
	}
	
	///
	///	Constructors
	///

	/**
	 * @param imageIn
	 */
	public BattleButtonUI(Image[] imagesIn) {
		
		super(imagesIn[0]);
		selection = -1;
		gap = 0;
			
		battleButton = imagesIn[0];
		battleButtonH = imagesIn[1];
		actButton = imagesIn[2];
		actButtonH = imagesIn[3];
		itemButton = imagesIn[4];
		itemButtonH = imagesIn[5];
		
		placeButtons();
		
	}

	
}
